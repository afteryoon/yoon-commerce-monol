package com.app.ycommerce.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAndCount {
	private Long productId;
	private int quantity;
	private int price;
}
