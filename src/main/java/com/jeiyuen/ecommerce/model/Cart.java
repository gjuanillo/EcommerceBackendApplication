package com.jeiyuen.ecommerce.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="carts")
public class Cart{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // @OneToMany(mappedBy = "cart", cascade = pmr, orphanRemoval)
    // private List<CartItem> cartItems = new ArrayList<>();
    
    private Double totalPrice = 0.0;

    public Cart() {
    }

    public Cart(Long cartId, User user, Double totalPrice) {
        this.cartId = cartId;
        this.user = user;
        this.totalPrice = totalPrice;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, user, totalPrice);
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
        Cart other = (Cart) obj;
        return Objects.equals(cartId, other.cartId) && Objects.equals(user, other.user)
                && Objects.equals(totalPrice, other.totalPrice);
    }

    @Override
    public String toString() {
        return "Cart{cartId=" + cartId + ", user=" + user + ", totalPrice=" + totalPrice + "}";
    }

}
