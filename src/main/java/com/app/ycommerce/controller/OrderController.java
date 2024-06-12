package com.app.ycommerce.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.ycommerce.dto.CartItemDto;
import com.app.ycommerce.dto.OrderDto;
import com.app.ycommerce.entity.Order;
import com.app.ycommerce.request.CreateOrderRequest;
import com.app.ycommerce.request.OrderItemRequest;
import com.app.ycommerce.request.OrderProductsRequests;
import com.app.ycommerce.response.OrderResponse;
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

	//상품 주문 (배송지, 상품 id, 개수)
	@PostMapping("/orderProducts")
	public ResponseEntity<String> createOrder(@RequestBody OrderProductsRequests orderProductsRequests) {
		try {
			log.debug("orderProductsRequests = {}", orderProductsRequests);
			//dto 객체 생성
			OrderDto orderDto = OrderDto.createOrderDto(orderProductsRequests);
			log.debug("orderDto = {}", orderDto);
			System.out.println(orderDto);
			orderService.createOrder(orderDto);
			return ResponseEntity.ok("주문성공");
		} catch (Exception e) {
			return ResponseEntity.ok("주문실패");
		}
	}

	//상품주문 new
	@PostMapping("orders")
	public ResponseEntity<Order> addOrder(@RequestBody CreateOrderRequest createOrderRequest) {
		Order order = orderService.addOrder(createOrderRequest);

		return ResponseEntity.ok(order);
	}

	@GetMapping("/order-list")
	public ResponseEntity<List<OrderResponse>> getOrders() {
		List<Order> orders = orderService.getOders();
		List<OrderResponse> orderResponses = orders.stream()
			.map(OrderResponse::createOrderResponse)
			.collect(Collectors.toList());

		return ResponseEntity.ok(orderResponses);
	}

	//상품 주문 이후 바로 실행되는 장바구니에서 해당 상품 삭제
	//
	// @PostMapping("/cancelOrder")
	// public void cancelOrder(Order order) {
	// 	orderService.cancelOrder(order);
	// }

	//장바구니에 상품추가
	@PostMapping("/cart")
	public void addCart(@RequestBody OrderItemRequest orderItemRequest) {
		log.info("REQQQQQQQQQQ{}", orderItemRequest.getProductId());
		log.info("REQQQQQQQQQQ22{}", orderItemRequest.getQuantity());
		orderService.addToCart(orderItemRequest);
	}

	//상품조회
	@GetMapping("/cartList")
	public ResponseEntity<List<CartItemDto>> getCart() {
		List<CartItemDto> cartItemDtos = orderService.getCart();

		return ResponseEntity.ok(cartItemDtos);
	}
}
