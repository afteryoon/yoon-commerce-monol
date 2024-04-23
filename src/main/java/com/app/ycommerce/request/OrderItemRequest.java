package com.app.ycommerce.request;

import com.app.ycommerce.entity.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {
	private Product product;
	private int quantity;
}
