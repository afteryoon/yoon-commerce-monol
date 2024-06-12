package com.app.ycommerce.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.app.ycommerce.entity.Address;
import com.app.ycommerce.entity.Order;
import com.app.ycommerce.entity.OrderItem;
import com.app.ycommerce.entity.OrderStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderResponse {
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;//주문상태 배치,배송중,배송완료,구매,환불
	private LocalDateTime orderDate;
	private int amount;
	private Address deliveryAddress;
	private List<OrderItem> orderItems = new ArrayList<>();

	public static OrderResponse createOrderResponse(Order order) {
		return OrderResponse.builder()
			.orderStatus(order.getOrderStatus())
			.orderDate(order.getOrderDate())
			.amount(order.getAmount())
			.deliveryAddress(order.getDeliveryAddress())
			.orderItems(order.getOrderItems())
			.build();
	}
}
