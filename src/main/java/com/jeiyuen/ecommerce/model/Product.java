package com.jeiyuen.ecommerce.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

    // Define Fields
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    private String productName;

    private String description;

    private String image;

    private Integer quantity;

    private double price;

    private double discount;

    private double specialPrice;

    // Relate product entity to category entity
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User user;

    // Constructors
    public Product() {
    }

    public Product(Long productId, String productName, String description, String image, Integer quantity, double price,
            double discount, double specialPrice, Category category, User user) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.specialPrice = specialPrice;
        this.category = category;
        this.user = user;
    }

    // Getters and Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(double specialPrice) {
        this.specialPrice = specialPrice;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, description, image, quantity, price, discount, specialPrice,
                category);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Product other = (Product) obj;
        return Objects.equals(productId, other.productId) && Objects.equals(productName, other.productName)
                && Objects.equals(description, other.description) && Objects.equals(image, other.image)
                && Objects.equals(quantity, other.quantity)
                && Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
                && Double.doubleToLongBits(discount) == Double.doubleToLongBits(other.discount)
                && Double.doubleToLongBits(specialPrice) == Double.doubleToLongBits(other.specialPrice)
                && Objects.equals(category, other.category);
    }

    @Override
    public String toString() {
        return "Product{productId=" + productId + ", productName=" + productName + ", description=" + description
                + ", image=" + image + ", quantity=" + quantity + ", price=" + price + ", discount=" + discount
                + ", specialPrice=" + specialPrice + ", category=" + category + "}";
    }

}
