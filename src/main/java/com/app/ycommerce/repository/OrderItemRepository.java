package com.app.ycommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.ycommerce.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
