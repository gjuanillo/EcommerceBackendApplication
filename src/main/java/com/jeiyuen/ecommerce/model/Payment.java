package com.jeiyuen.ecommerce.model;

import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @OneToOne(mappedBy = "payment", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Order order;

    @NotBlank
    @Size(min = 4, message = "Payment method must contain at least 4 characters! ")
    private String paymentMethod;

    private String pgPaymentId;
    private String pgPaymentStatus;
    private String pgResponseMessage;
    private String pgName;

    public Payment() {
    }

    public Payment(Long paymentId, String pgPaymentId, String pgPaymentStatus, String pgResponseMessage,
            String pgName) {
        this.paymentId = paymentId;
        this.pgPaymentId = pgPaymentId;
        this.pgPaymentStatus = pgPaymentStatus;
        this.pgResponseMessage = pgResponseMessage;
        this.pgName = pgName;
    }

    public Payment(Long paymentId, Order order, String paymentMethod, String pgPaymentId,
            String pgPaymentStatus, String pgResponseMessage, String pgName) {
        this.paymentId = paymentId;
        this.order = order;
        this.paymentMethod = paymentMethod;
        this.pgPaymentId = pgPaymentId;
        this.pgPaymentStatus = pgPaymentStatus;
        this.pgResponseMessage = pgResponseMessage;
        this.pgName = pgName;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

    public String getPgPaymentStatus() {
        return pgPaymentStatus;
    }

    public void setPgPaymentStatus(String pgPaymentStatus) {
        this.pgPaymentStatus = pgPaymentStatus;
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
        return Objects.hash(paymentId, order, paymentMethod, pgPaymentId, pgPaymentStatus, pgResponseMessage, pgName);
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
        Payment other = (Payment) obj;
        return Objects.equals(paymentId, other.paymentId) && Objects.equals(order, other.order)
                && Objects.equals(paymentMethod, other.paymentMethod) && Objects.equals(pgPaymentId, other.pgPaymentId)
                && Objects.equals(pgPaymentStatus, other.pgPaymentStatus)
                && Objects.equals(pgResponseMessage, other.pgResponseMessage) && Objects.equals(pgName, other.pgName);
    }

    @Override
    public String toString() {
        return "Payment{paymentId=" + paymentId + ", order=" + order + ", paymentMethod=" + paymentMethod
                + ", pgPaymentId=" + pgPaymentId + ", pgPaymentStatus=" + pgPaymentStatus + ", pgResponseMessage="
                + pgResponseMessage + ", pgName=" + pgName + "}";
    }

}
