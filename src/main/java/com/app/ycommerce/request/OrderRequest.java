package com.app.ycommerce.request;

import java.util.ArrayList;
import java.util.List;

import com.app.ycommerce.entity.OrderItem;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderRequest {

	private int id;
	private int amount;
	private int delivery_address;

	private List<OrderItem> orderItems = new ArrayList<>();

	public OrderRequest(int id, int amount, int delivery_address, List<OrderItem> orderItems) {
		this.id = id;
		this.amount = amount;
		this.delivery_address = delivery_address;
		this.orderItems = orderItems;
	}
}
