package com.app.ycommerce.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {
	private Long productId;
	private int quantity;
}
