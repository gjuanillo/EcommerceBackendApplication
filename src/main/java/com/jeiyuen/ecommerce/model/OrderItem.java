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
@Table(name= "order_items")
public class OrderItem{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name= "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name= "order_id")
    private Order order;
    
    private Integer quantity;

    private Double discount;

    private Double orderedProductPrice;

    public OrderItem() {
    }

    public OrderItem(Long orderItemId, Product product, Order order, Integer quantity, Double discount,
            Double orderedProductPrice) {
        this.orderItemId = orderItemId;
        this.product = product;
        this.order = order;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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
        return Objects.hash(orderItemId, product, order, quantity, discount, orderedProductPrice);
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
        OrderItem other = (OrderItem) obj;
        return Objects.equals(orderItemId, other.orderItemId) && Objects.equals(product, other.product)
                && Objects.equals(order, other.order) && Objects.equals(quantity, other.quantity)
                && Objects.equals(discount, other.discount)
                && Objects.equals(orderedProductPrice, other.orderedProductPrice);
    }

    @Override
    public String toString() {
        return "OrderItem{orderItemId=" + orderItemId + ", product=" + product + ", order=" + order + ", quantity="
                + quantity + ", discount=" + discount + ", orderedProductPrice=" + orderedProductPrice + "}";
    }

}
