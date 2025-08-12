package com.jeiyuen.ecommerce.payload;

import java.util.Objects;

public class CartItemDTO {
    private Long productId;
    private Integer quantity;

    public CartItemDTO() {
    }

    public CartItemDTO(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, quantity);
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
        CartItemDTO other = (CartItemDTO) obj;
        return Objects.equals(productId, other.productId) && Objects.equals(quantity, other.quantity);
    }

    @Override
    public String toString() {
        return "CartItemDTO{productId=" + productId + ", quantity=" + quantity + "}";
    }

}
