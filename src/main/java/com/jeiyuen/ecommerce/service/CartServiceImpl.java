package com.jeiyuen.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.transaction.Transactional;

import com.jeiyuen.ecommerce.exceptions.ApiException;
import com.jeiyuen.ecommerce.exceptions.ResourceNotFoundException;
import com.jeiyuen.ecommerce.model.Cart;
import com.jeiyuen.ecommerce.model.CartItem;
import com.jeiyuen.ecommerce.model.Product;
import com.jeiyuen.ecommerce.payload.CartDTO;
import com.jeiyuen.ecommerce.payload.CartItemDTO;
import com.jeiyuen.ecommerce.payload.ProductDTO;
import com.jeiyuen.ecommerce.repository.CartItemRepository;
import com.jeiyuen.ecommerce.repository.CartRepository;
import com.jeiyuen.ecommerce.repository.ProductRepository;
import com.jeiyuen.ecommerce.utility.AuthUtil;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    ProductRepository productRepository;
    CartRepository cartRepository;
    CartItemRepository cartItemRepository;
    ModelMapper modelMapper;
    AuthUtil authUtil;

    @Autowired
    public CartServiceImpl(ProductRepository productRepository, CartRepository cartRepository,
            CartItemRepository cartItemRepository, ModelMapper modelMapper, AuthUtil authUtil) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.modelMapper = modelMapper;
        this.authUtil = authUtil;
    }

    // Helper for existing cart verification for each users
    private Cart createCart() {
        Cart userCart = cartRepository.findCartByEmail(authUtil.loggedInEmail());
        if (userCart != null) {
            return userCart;
        }
        Cart cart = new Cart();
        cart.setTotalPrice(0.0);
        cart.setUser(authUtil.loggedInUser());
        return cartRepository.save(cart);
    }

    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        // Find existing cart or create one
        Cart cart = createCart();

        // Retrieve product details
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product", "productId", productId));

        // Validate
        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(productId, cart.getCartId());
        if (cartItem != null) {
            throw new ApiException("Product " + product.getProductName() + " already exist in the cart!");
        }
        if (product.getQuantity() == 0) {
            throw new ApiException(product.getProductName() + " is currently out of stock");
        }
        if (quantity > product.getQuantity()) {
            throw new ApiException(product.getProductName()
                    + " Cannot be added. Please make an order within the product quantity " + product.getQuantity());
        }

        // Create Cart Item
        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(product);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductPrice(product.getSpecialPrice());

        // Save to repository
        cartItemRepository.save(newCartItem);
        // Refresh cart
        cart.getCartItems().add(newCartItem);
        // Keep the current stock, only deduct quantity when order is placed
        product.setQuantity(product.getQuantity());
        // Calculate the total price
        cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice() * quantity));
        cartRepository.save(cart);

        // Return updated cart
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        List<CartItem> cartItems = cart.getCartItems();
        Stream<ProductDTO> productDTOStream = cartItems.stream().map(item -> {
            ProductDTO map = modelMapper.map(item.getProduct(), ProductDTO.class);
            map.setQuantity(item.getQuantity());
            return map;
        });
        cartDTO.setProducts(productDTOStream.toList());
        return cartDTO;

    }

    @Override
    public List<CartDTO> getAllCarts() {
        // Find all carts
        List<Cart> carts = cartRepository.findAll();

        if (carts.isEmpty()) {
            throw new ApiException("No Cart Exist!");
        }

        // Convert list to DTO
        List<CartDTO> cartDTOs = carts.stream()
                .map(cart -> {
                    CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

                    // List of products are also needed in CartDTO
                    List<ProductDTO> products = cart.getCartItems()
                            .stream().map(cartItem -> {
                                ProductDTO productDTO = modelMapper.map(cartItem.getProduct(), ProductDTO.class);
                                productDTO.setQuantity(cartItem.getQuantity());
                                return productDTO;
                            }).toList();

                    // Set the mapped products
                    cartDTO.setProducts(products);

                    return cartDTO;
                }).collect(Collectors.toList());
        return cartDTOs;
    }

    @Override
    public CartDTO getCart(String emailId, Long cartId) {
        // Fetch cart by Email and Cart Id
        Cart cart = cartRepository.findCartByEmailAndCartId(emailId, cartId);
        // Validate null
        if (cart == null) {
            throw new ResourceNotFoundException("Cart", "cartId", cartId);
        }
        // Map to DTO
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        // Set quantity to cart item quantity instead of full product stock
        cart.getCartItems().forEach(c -> c.getProduct().setQuantity(c.getQuantity()));
        // Set Product list to DTO
        List<ProductDTO> products = cart.getCartItems().stream()
                .map(p -> modelMapper.map(p.getProduct(), ProductDTO.class))
                .toList();
        cartDTO.setProducts(products);
        return cartDTO;
    }

    @Override
    @Transactional
    public CartDTO updateProductQuantityInCart(Long productId, Integer i) {
        // Check if user's cart exists
        String emailId = authUtil.loggedInEmail();
        Cart userCart = cartRepository.findCartByEmail(emailId);
        Long cartId = userCart.getCartId();
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));
        // Validate product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        if (product.getQuantity() == 0) {
            throw new ApiException(product.getProductName() + " is currently out of stock");
        }
        if (i > product.getQuantity()) {
            throw new ApiException(product.getProductName()
                    + " Cannot be added. Please make an order within the product quantity " + product.getQuantity());
        }
        // Validate if product exists in the cart
        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(productId, cartId);
        if (cartItem == null) {
            throw new ApiException("Product with ID " + productId + " is not available in the cart!");
        }
        int newQuantity = cartItem.getQuantity() + i;
        if (newQuantity < 0) {
            throw new ApiException("The resulting quantity cannot be negative");
        }
        if (newQuantity == 0) {
            deleteProductFromCart(cartId, productId);
        } else {
            // Update
            cartItem.setProductPrice(product.getSpecialPrice());
            cartItem.setQuantity(cartItem.getQuantity() + i);
            cartItem.setDiscount(product.getDiscount());
            cart.setTotalPrice(cart.getTotalPrice() + (cartItem.getProductPrice() * i));
            cartRepository.save(cart);
        }
        CartItem updatedItem = cartItemRepository.save(cartItem);
        if (updatedItem.getQuantity() == 0) {
            cartItemRepository.deleteById(updatedItem.getCartItemId());
        }
        // Map changes to DTO
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        List<CartItem> cartItems = cart.getCartItems();
        Stream<ProductDTO> productStream = cartItems.stream()
                .map(item -> {
                    ProductDTO prd = modelMapper.map(item.getProduct(), ProductDTO.class);
                    prd.setQuantity(item.getQuantity());
                    return prd;
                });
        cartDTO.setProducts(productStream.toList());
        return cartDTO;
    }

    @Transactional
    @Override
    public String deleteProductFromCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));
        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(productId, cartId);

        // Validate
        if (cartItem == null) {
            throw new ResourceNotFoundException("Product", "productId", productId);
        }
        // Update Cart total price then delete the item
        cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity()));
        cartItemRepository.deleteCartItemByCartIdAndProductId(cartId, productId);
        return "Product " + cartItem.getProduct().getProductName() + " successfully removed from the cart";
    }

    @Override
    public void updateProductInCarts(Long cartId, Long productId) {
        // Validate if the cart exists
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));
        // Validate product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        // Validate if the product exists within the cart
        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(productId, cartId);
        if (cartItem == null) {
            throw new ApiException("Product with ID " + productId + " is not available in the cart!");
        }
        // Revert the product costs
        double cartPrice = cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity());
        // Update the product price & recompute total price based on updated product
        // price
        cartItem.setProductPrice(product.getSpecialPrice());
        cart.setTotalPrice(cartPrice + (cartItem.getProductPrice() * cartItem.getQuantity()));
        cartItemRepository.save(cartItem);
    }

    @Override
    public String createOrUpdateCartWithItems(List<CartItemDTO> cartItems) {
        String emailId = authUtil.loggedInEmail();
        Cart existingCart = cartRepository.findCartByEmail(emailId);
        if (existingCart == null) {
            existingCart = new Cart();
            existingCart.setTotalPrice(0.00);
            existingCart.setUser(authUtil.loggedInUser());
            existingCart = cartRepository.save(existingCart);
        } else {
            cartItemRepository.deleteAllByCartId(existingCart.getCartId());
        }
        double totalPrice = 0.00;
        for (CartItemDTO cartItemDTO : cartItems) {
            Long productId = cartItemDTO.getProductId();
            Integer quantity = cartItemDTO.getQuantity();
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
            // Purpose: Reduce product stock when user adds product to cart
            // product.setQuantity(product.getQuantity() - quantity);
            totalPrice += product.getSpecialPrice() * quantity;
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(existingCart);
            cartItem.setQuantity(quantity);
            cartItem.setProductPrice(product.getSpecialPrice());
            cartItem.setDiscount(product.getDiscount());
            cartItemRepository.save(cartItem);
        }

        existingCart.setTotalPrice(totalPrice);
        cartRepository.save(existingCart);
        return "Cart items successfully saved";
    }

}
