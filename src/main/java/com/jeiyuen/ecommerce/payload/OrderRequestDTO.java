package com.jeiyuen.ecommerce.payload;

import java.util.Objects;

public class OrderRequestDTO {

    private Long addressId;
    private String paymentMethod;
    private String pgName;
    private String pgPaymentId;
    private String pgStatus;
    private String pgResponseMessage;

    public OrderRequestDTO() {
    }

    public OrderRequestDTO(Long addressId, String paymentMethod, String pgName, String pgPaymentId, String pgStatus,
            String pgResponseMessage) {
        this.addressId = addressId;
        this.paymentMethod = paymentMethod;
        this.pgName = pgName;
        this.pgPaymentId = pgPaymentId;
        this.pgStatus = pgStatus;
        this.pgResponseMessage = pgResponseMessage;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPgName() {
        return pgName;
    }

    public void setPgName(String pgName) {
        this.pgName = pgName;
    }

    public String getPgPaymentId() {
        return pgPaymentId;
    }

    public void setPgPaymentId(String pgPaymentId) {
        this.pgPaymentId = pgPaymentId;
    }

    public String getPgStatus() {
        return pgStatus;
    }

    public void setPgStatus(String pgStatus) {
        this.pgStatus = pgStatus;
    }

    public String getPgResponseMessage() {
        return pgResponseMessage;
    }

    public void setPgResponseMessage(String pgResponseMessage) {
        this.pgResponseMessage = pgResponseMessage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId, paymentMethod, pgName, pgPaymentId, pgStatus, pgResponseMessage);
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
        OrderRequestDTO other = (OrderRequestDTO) obj;
        return Objects.equals(addressId, other.addressId) && Objects.equals(paymentMethod, other.paymentMethod)
                && Objects.equals(pgName, other.pgName) && Objects.equals(pgPaymentId, other.pgPaymentId)
                && Objects.equals(pgStatus, other.pgStatus)
                && Objects.equals(pgResponseMessage, other.pgResponseMessage);
    }

    @Override
    public String toString() {
        return "OrderRequestDTO{addressId=" + addressId + ", paymentMethod=" + paymentMethod + ", pgName=" + pgName
                + ", pgPaymentId=" + pgPaymentId + ", pgStatus=" + pgStatus + ", pgResponseMessage=" + pgResponseMessage
                + "}";
    }

}
