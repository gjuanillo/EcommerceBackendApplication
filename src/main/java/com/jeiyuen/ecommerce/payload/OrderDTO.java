package com.jeiyuen.ecommerce.payload;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class OrderDTO {

    private Long orderId;
    private String email;
    private List<OrderItemDTO> orderItems;
    private LocalDate localDate;
    private PaymentDTO payment;
    private Double totalAmount;
    private String orderStatus;
    private Long addressId;

    public OrderDTO() {
    }

    public OrderDTO(Long orderId, String email, List<OrderItemDTO> orderItems, LocalDate localDate, PaymentDTO payment,
            Double totalAmount, String orderStatus, Long addressId) {
        this.orderId = orderId;
        this.email = email;
        this.orderItems = orderItems;
        this.localDate = localDate;
        this.payment = payment;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.addressId = addressId;
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

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public PaymentDTO getPayment() {
        return payment;
    }

    public void setPayment(PaymentDTO payment) {
        this.payment = payment;
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

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, email, orderItems, localDate, payment, totalAmount, orderStatus, addressId);
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
        OrderDTO other = (OrderDTO) obj;
        return Objects.equals(orderId, other.orderId) && Objects.equals(email, other.email)
                && Objects.equals(orderItems, other.orderItems) && Objects.equals(localDate, other.localDate)
                && Objects.equals(payment, other.payment) && Objects.equals(totalAmount, other.totalAmount)
                && Objects.equals(orderStatus, other.orderStatus) && Objects.equals(addressId, other.addressId);
    }

    @Override
    public String toString() {
        return "OrderDTO{orderId=" + orderId + ", email=" + email + ", orderItems=" + orderItems + ", localDate="
                + localDate + ", payment=" + payment + ", totalAmount=" + totalAmount + ", orderStatus=" + orderStatus
                + ", addressId=" + addressId + "}";
    }

}
