package com.app.ycommerce.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.ycommerce.entity.CartItem;
import com.app.ycommerce.request.OrderItemRequest;
import com.app.ycommerce.request.OrderRequest;
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
	public ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequests) {
		try {
			orderService.createOrder(orderRequests);
			return ResponseEntity.ok("주문성공");
		} catch (Exception e) {
			return ResponseEntity.ok("주문실패");
		}
	}

	//
	// @PostMapping("/cancelOrder")
	// public void cancelOrder(Order order) {
	// 	orderService.cancelOrder(order);
	// }
	//
	//장바구니에 상품추가
	@PostMapping("/cart")
	public void addCart(@RequestBody OrderItemRequest orderItemRequest) {
		log.info("REQQQQQQQQQQ{}", orderItemRequest.getProductId());
		log.info("REQQQQQQQQQQ22{}", orderItemRequest.getQuantity());
		orderService.addToCart(orderItemRequest);
	}

	//상품조회
	@GetMapping("/cartList")
	public ResponseEntity<List<CartItem>> getCart() {
		List<CartItem> cartItems = orderService.getCart();

		return ResponseEntity.ok(cartItems);
	}
}
