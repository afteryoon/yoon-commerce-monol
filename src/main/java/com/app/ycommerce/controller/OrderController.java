package com.app.ycommerce.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.ycommerce.entity.Order;
import com.app.ycommerce.request.OrderItemRequest;
import com.app.ycommerce.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@RestController
@ResponseBody
@Slf4j
@RequestMapping("/api/order")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping("/orderProducts")
	public ResponseEntity<Order> createOrder(List<OrderItemRequest> orderRequests) {
		Order order = orderService.createOrder(orderRequests);
		return ResponseEntity.ok(order);
	}

	@PostMapping("/cancelOrder")
	public void cancelOrder(Order order) {
		orderService.cancelOrder(order);
	}

	//장바구니에 상품추가
	@PostMapping("/cart")
	public void addCart(OrderItemRequest orderItemRequest) {
		orderService.addToCart(orderItemRequest);
	}

}
