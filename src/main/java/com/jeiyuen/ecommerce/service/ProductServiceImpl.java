package com.jeiyuen.ecommerce.service;

import java.io.IOException;
import java.util.List;

import com.jeiyuen.ecommerce.exceptions.ApiException;
import com.jeiyuen.ecommerce.exceptions.ResourceNotFoundException;
import com.jeiyuen.ecommerce.model.Cart;
import com.jeiyuen.ecommerce.model.Category;
import com.jeiyuen.ecommerce.model.Product;
import com.jeiyuen.ecommerce.payload.CartDTO;
import com.jeiyuen.ecommerce.payload.ProductDTO;
import com.jeiyuen.ecommerce.payload.ProductResponse;
import com.jeiyuen.ecommerce.repository.CartRepository;
import com.jeiyuen.ecommerce.repository.CategoryRepository;
import com.jeiyuen.ecommerce.repository.ProductRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductServiceImpl implements ProductService {

    // Add Dependencies
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private CartRepository cartRepository;
    private ModelMapper modelMapper;
    private FileService fileService;
    private CartService cartService;
    @Value("${project.image}")
    private String path;
    @Value("${image.base.url}")
    private String imageBaseUrl;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository,
            CartRepository cartRepository, ModelMapper modelMapper, FileService fileService, CartService cartService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.cartRepository = cartRepository;
        this.modelMapper = modelMapper;
        this.fileService = fileService;
        this.cartService = cartService;
    }

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        // Check if user included Id in the DTO
        if (productDTO.getProductId() != null) {
            throw new ApiException("Product ID is automatically generated, cannot assign ID!");
        }
        // Find id
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        // Map DTO into entity
        Product product = modelMapper.map(productDTO, Product.class);
        // Check if the product already exist
        boolean isProductPresent = false;
        List<Product> products = category.getProducts();
        for (Product value : products) {
            if (value.getProductName().equals(productDTO.getProductName())) {
                isProductPresent = true;
                break;
            }
        }
        if (isProductPresent) {
            throw new ApiException("Product with the name " + productDTO.getProductName() + " already exists!");
        }
        // Set image to default
        product.setImage("default.png");
        product.setCategory(category);
        // set special price based on given price and discount
        double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts(String keyword, String category, Integer pageNumber, Integer pageSize,
            String sortBy, String sortOrder) {
        Sort sortByandOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByandOrder);
        Specification<Product> spec = Specification.where(null);
        if (keyword != null && !keyword.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder
                    .like(criteriaBuilder.lower(root.get("productName")), "%" + keyword.toLowerCase() + "%"));
        }
        if (category != null && !category.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder
                    .like(root.get("category").get("categoryName"), category));
        }
        Page<Product> productPage = productRepository.findAll(spec, pageDetails);
        List<Product> products = productPage.getContent();
        // Check if list of products is empty
        if (products.isEmpty()) {
            throw new ApiException("Product list is empty!");
        }
        // convert each products into DTO by using stream
        List<ProductDTO> productDTOs = products.stream()
                .map(product -> {
                    ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
                    productDTO.setImage(constructImageURL(product.getImage()));
                    return productDTO;
                })
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOs);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setLastPage(productPage.isLast());
        return productResponse;
    }

    private String constructImageURL(String imageName) {
        return imageBaseUrl.endsWith("/") ? imageBaseUrl + imageName : "/" + imageName;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy,
            String sortOrder) {
        // Find category id
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        Sort sortByandOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByandOrder);
        Page<Product> productPage = productRepository.findByCategory(category, pageDetails);
        List<Product> products = productPage.getContent();
        // Check if list of products is empty
        if (products.isEmpty()) {
            throw new ApiException("Product list is empty!");
        }
        // convert each products into DTO by using stream
        List<ProductDTO> productDTOs = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOs);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setLastPage(productPage.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy,
            String sortOrder) {
        Sort sortByandOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByandOrder);
        Page<Product> productPage = productRepository.findByProductNameContainingIgnoreCase(keyword, pageDetails);
        // Find product by keyword
        List<Product> products = productPage.getContent();
        // Check if list of products is empty
        if (products.isEmpty()) {
            throw new ApiException("Product list is empty!");
        }
        // convert each products into DTO by using stream
        List<ProductDTO> productDTOs = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOs);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setLastPage(productPage.isLast());
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        // Find the existing product
        Product savedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        // Map DTO into entity
        Product product = modelMapper.map(productDTO, Product.class);
        // Update product if found
        savedProduct.setProductName(product.getProductName());
        savedProduct.setDescription(product.getDescription());
        savedProduct.setQuantity(product.getQuantity());
        savedProduct.setDiscount(product.getDiscount());
        savedProduct.setPrice(product.getPrice());
        // savedProduct.setSpecialPrice(product.getSpecialPrice());
        double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        savedProduct.setSpecialPrice(specialPrice);
        // Save changes
        Product finalProduct = productRepository.save(savedProduct);

        List<Cart> carts = cartRepository.findCartsByProductId(productId);
        // Map cart to DTO
        List<CartDTO> cartDTOs = carts.stream().map(cart -> {
            CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
            List<ProductDTO> products = cart.getCartItems().stream()
                    .map(p -> modelMapper.map(p.getProduct(), ProductDTO.class)).toList();
            cartDTO.setProducts(products);
            return cartDTO;
        }).toList();
        cartDTOs.forEach(cart -> cartService.updateProductInCarts(cart.getCartId(), productId));
        return modelMapper.map(finalProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        // Find the existing product
        Product savedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        // Delete product if it exists within carts
        List<Cart> carts = cartRepository.findCartsByProductId(productId);
        carts.forEach(cart -> {
            cartService.deleteProductFromCart(cart.getCartId(), productId);
        });
        // Delete the product
        productRepository.delete(savedProduct);
        // Map the product into DTO then return it
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        // Find the product
        Product savedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        // Upload image
        // Get the file name of the image
        String fileName = fileService.uploadImage(path, image);
        // Update the new file name to the product
        savedProduct.setImage(fileName);
        // Save product
        Product updatedProduct = productRepository.save(savedProduct);
        // return DTO
        return modelMapper.map(updatedProduct, ProductDTO.class);

    }

}
