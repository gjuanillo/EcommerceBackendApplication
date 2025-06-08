package com.jeiyuen.ecommerce.payload;

import java.util.Objects;

public class PaymentDTO {

    private Long paymentId;
    private String paymentMethod;
    private String pgPaymentId;
    private String pgStatus;
    private String pgResponseMessage;
    private String pgName;

    public PaymentDTO() {
    }

    public PaymentDTO(Long paymentId, String paymentMethod, String pgPaymentId, String pgStatus,
            String pgResponseMessage, String pgName) {
        this.paymentId = paymentId;
        this.paymentMethod = paymentMethod;
        this.pgPaymentId = pgPaymentId;
        this.pgStatus = pgStatus;
        this.pgResponseMessage = pgResponseMessage;
        this.pgName = pgName;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
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

    public String getPgName() {
        return pgName;
    }

    public void setPgName(String pgName) {
        this.pgName = pgName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId, paymentMethod, pgPaymentId, pgStatus, pgResponseMessage, pgName);
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
        PaymentDTO other = (PaymentDTO) obj;
        return Objects.equals(paymentId, other.paymentId) && Objects.equals(paymentMethod, other.paymentMethod)
                && Objects.equals(pgPaymentId, other.pgPaymentId) && Objects.equals(pgStatus, other.pgStatus)
                && Objects.equals(pgResponseMessage, other.pgResponseMessage) && Objects.equals(pgName, other.pgName);
    }

    @Override
    public String toString() {
        return "PaymentDTO{paymentId=" + paymentId + ", paymentMethod=" + paymentMethod + ", pgPaymentId=" + pgPaymentId
                + ", pgStatus=" + pgStatus + ", pgResponseMessage=" + pgResponseMessage + ", pgName=" + pgName + "}";
    }

}
