package com.app.ycommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.ycommerce.entity.Cart;
import com.app.ycommerce.entity.Member;

public interface CartRepository extends JpaRepository<Cart, Long> {

	Optional<Cart> findByMember(Member member);
	
}
