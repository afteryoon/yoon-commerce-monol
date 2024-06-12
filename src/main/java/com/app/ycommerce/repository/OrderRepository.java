package com.app.ycommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.ycommerce.entity.Member;
import com.app.ycommerce.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findAllByMember(Member member);
}
