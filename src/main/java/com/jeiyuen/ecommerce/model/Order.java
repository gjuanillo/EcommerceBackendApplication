package com.jeiyuen.ecommerce.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;

@Entity
@Table(name="orders")
public class Order{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Email
    @Column(name="email", nullable=false)
    private String email;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDate orderDate;

    // @OneToOne
    // @JoinColumn(name= "payment_id")
    // private Payment payment;

    private Double totalAmount;

    private String orderStatus;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    public Order() {
    }

    public Order(Long orderId, String email, List<OrderItem> orderItems, LocalDate orderDate, Double totalAmount,
            String orderStatus, Address address) {
        this.orderId = orderId;
        this.email = email;
        this.orderItems = orderItems;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.address = address;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, email, orderItems, orderDate, totalAmount, orderStatus, address);
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
        Order other = (Order) obj;
        return Objects.equals(orderId, other.orderId) && Objects.equals(email, other.email)
                && Objects.equals(orderItems, other.orderItems) && Objects.equals(orderDate, other.orderDate)
                && Objects.equals(totalAmount, other.totalAmount) && Objects.equals(orderStatus, other.orderStatus)
                && Objects.equals(address, other.address);
    }

    @Override
    public String toString() {
        return "Order{orderId=" + orderId + ", email=" + email + ", orderItems=" + orderItems + ", orderDate="
                + orderDate + ", totalAmount=" + totalAmount + ", orderStatus=" + orderStatus + ", address=" + address
                + "}";
    }

}
