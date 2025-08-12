package com.jeiyuen.ecommerce.repository;

import java.util.List;

import com.jeiyuen.ecommerce.model.Cart;
import com.jeiyuen.ecommerce.model.CartItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = ?2 AND ci.product.id = ?1")
    CartItem findCartItemByProductIdAndCartId(Long productId, Long cartId);

    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = ?1 AND ci.product.id = ?2")
    void deleteCartItemByCartIdAndProductId(Long cartId, Long productId);

    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = ?1")
    List<Cart> deleteAllByCartId(Long cartId);
}
