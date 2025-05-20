package com.jeiyuen.ecommerce.payload;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartDTO{

    private Long cartId;
    private Double totalPrice = 0.0;
    private List<ProductDTO> products = new ArrayList<>();
    
    public CartDTO() {
    }

    public CartDTO(Long cartId, Double totalPrice, List<ProductDTO> products) {
        this.cartId = cartId;
        this.totalPrice = totalPrice;
        this.products = products;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, totalPrice, products);
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
        CartDTO other = (CartDTO) obj;
        return Objects.equals(cartId, other.cartId) && Objects.equals(totalPrice, other.totalPrice)
                && Objects.equals(products, other.products);
    }

    @Override
    public String toString() {
        return "CartDTO{cartId=" + cartId + ", totalPrice=" + totalPrice + ", products=" + products + "}";
    }

}
