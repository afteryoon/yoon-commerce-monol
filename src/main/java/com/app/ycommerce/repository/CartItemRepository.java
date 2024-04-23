package com.app.ycommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.ycommerce.entity.CartItem;
import com.app.ycommerce.entity.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	Optional<CartItem> findByCartIdAndProduct(Long cartId, Product product);
	//SELECT * FROM your_table_name WHERE cart_id = ? AND product_id = ?
}
