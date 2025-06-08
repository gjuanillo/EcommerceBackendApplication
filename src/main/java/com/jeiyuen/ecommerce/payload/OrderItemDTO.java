package com.jeiyuen.ecommerce.payload;

import java.util.Objects;

public class OrderItemDTO {

    private Long orderItemId;
    private ProductDTO product;
    private Integer quantity;
    private Double discount;
    private Double orderedProductPrice;

    public OrderItemDTO() {
    }

    public OrderItemDTO(Long orderItemId, ProductDTO product, Integer quantity, Double discount,
            Double orderedProductPrice) {
        this.orderItemId = orderItemId;
        this.product = product;
        this.quantity = quantity;
        this.discount = discount;
        this.orderedProductPrice = orderedProductPrice;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getOrderedProductPrice() {
        return orderedProductPrice;
    }

    public void setOrderedProductPrice(Double orderedProductPrice) {
        this.orderedProductPrice = orderedProductPrice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderItemId, product, quantity, discount, orderedProductPrice);
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
        OrderItemDTO other = (OrderItemDTO) obj;
        return Objects.equals(orderItemId, other.orderItemId) && Objects.equals(product, other.product)
                && Objects.equals(quantity, other.quantity) && Objects.equals(discount, other.discount)
                && Objects.equals(orderedProductPrice, other.orderedProductPrice);
    }

    @Override
    public String toString() {
        return "OrderItemDTO{orderItemId=" + orderItemId + ", product=" + product + ", quantity=" + quantity
                + ", discount=" + discount + ", orderedProductPrice=" + orderedProductPrice + "}";
    }

}
