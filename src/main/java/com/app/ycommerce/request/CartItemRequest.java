package com.app.ycommerce.request;

import lombok.Getter;

@Getter
public class CartItemRequest {
	private Long productId;
	private int quantity;
}
