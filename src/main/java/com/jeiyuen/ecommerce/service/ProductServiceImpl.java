package com.jeiyuen.ecommerce.service;

import java.io.IOException;
import java.util.List;

import com.jeiyuen.ecommerce.exceptions.ApiException;
import com.jeiyuen.ecommerce.exceptions.ResourceNotFoundException;
import com.jeiyuen.ecommerce.model.Category;
import com.jeiyuen.ecommerce.model.Product;
import com.jeiyuen.ecommerce.payload.ProductDTO;
import com.jeiyuen.ecommerce.payload.ProductResponse;
import com.jeiyuen.ecommerce.repository.CategoryRepository;
import com.jeiyuen.ecommerce.repository.ProductRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductServiceImpl implements ProductService {

    // Add Dependencies
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository,
            ModelMapper modelMapper, FileService fileService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.fileService = fileService;
    }

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        // Check if user included Id in the DTO
        if (productDTO.getProductId() != null){
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
        for (Product value : products){
            if (value.getProductName().equals(productDTO.getProductName())){
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
    public ProductResponse getAllProducts() {
        List<Product> products = productRepository.findAll();
        // Check if list of products is empty
        if (products.isEmpty()){
            throw new ApiException("Product list is empty!");
        }
        // convert each products into DTO by using stream
        List<ProductDTO> productDTOs = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOs);
        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {
        // Find category id
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
        // Check if list of products is empty
        if (products.isEmpty()){
            throw new ApiException("Product list is empty!");
        }
        // convert each products into DTO by using stream
        List<ProductDTO> productDTOs = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOs);
        return productResponse;
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword) {
        // Find product by keyword
        List<Product> products = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%'); // pattern
                                                                                                         // matching
        // Check if list of products is empty
        if (products.isEmpty()){
            throw new ApiException("Product list is empty!");
        }
        // convert each products into DTO by using stream
        List<ProductDTO> productDTOs = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOs);
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
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
        return modelMapper.map(finalProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        // Find the existing product
        Product savedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
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
