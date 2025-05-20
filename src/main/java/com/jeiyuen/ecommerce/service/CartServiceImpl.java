package com.jeiyuen.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jeiyuen.ecommerce.exceptions.ApiException;
import com.jeiyuen.ecommerce.exceptions.ResourceNotFoundException;
import com.jeiyuen.ecommerce.model.Cart;
import com.jeiyuen.ecommerce.model.CartItem;
import com.jeiyuen.ecommerce.model.Product;
import com.jeiyuen.ecommerce.payload.CartDTO;
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
                List<ProductDTO> products = cart.getCartItems().stream()
                    .map(product -> modelMapper.map(product.getProduct(), ProductDTO.class)).collect(Collectors.toList());
                
                // Set the mapped products
                cartDTO.setProducts(products);

                return cartDTO;
            }).collect(Collectors.toList());
        return cartDTOs;
    }

}
