package com.app.ycommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.ycommerce.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
