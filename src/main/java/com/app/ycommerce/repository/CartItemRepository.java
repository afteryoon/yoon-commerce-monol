package com.app.ycommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.ycommerce.entity.Cart;
import com.app.ycommerce.entity.CartItem;
import com.app.ycommerce.entity.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

	Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
	//SELECT * FROM your_table_name WHERE cart_id = ? AND product_id = ?
}
