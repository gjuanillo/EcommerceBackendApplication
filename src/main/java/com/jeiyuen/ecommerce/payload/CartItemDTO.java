package com.jeiyuen.ecommerce.payload;

import java.util.Objects;

public class CartItemDTO{

    private Long CartItemsId;
    private CartDTO cart;
    private ProductDTO productDTO;
    private Integer quantity;
    private Double discount;
    private Double productPrice;
    
    public CartItemDTO() {
    }

    public CartItemDTO(Long cartItemsId, CartDTO cart, ProductDTO productDTO, Integer quantity, Double discount,
            Double productPrice) {
        CartItemsId = cartItemsId;
        this.cart = cart;
        this.productDTO = productDTO;
        this.quantity = quantity;
        this.discount = discount;
        this.productPrice = productPrice;
    }

    public Long getCartItemsId() {
        return CartItemsId;
    }

    public void setCartItemsId(Long cartItemsId) {
        CartItemsId = cartItemsId;
    }

    public CartDTO getCart() {
        return cart;
    }

    public void setCart(CartDTO cart) {
        this.cart = cart;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
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

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(CartItemsId, cart, productDTO, quantity, discount, productPrice);
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
        return Objects.equals(CartItemsId, other.CartItemsId) && Objects.equals(cart, other.cart)
                && Objects.equals(productDTO, other.productDTO) && Objects.equals(quantity, other.quantity)
                && Objects.equals(discount, other.discount) && Objects.equals(productPrice, other.productPrice);
    }

    @Override
    public String toString() {
        return "CartItemsDTO{CartItemsId=" + CartItemsId + ", cart=" + cart + ", productDTO=" + productDTO
                + ", quantity=" + quantity + ", discount=" + discount + ", productPrice=" + productPrice + "}";
    }

}
