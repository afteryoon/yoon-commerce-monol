package com.app.ycommerce.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class ProductRequest {
	private Long productId;
	private Long categoryId;
	private String productName;
	private String productDescription;
	private int price;
	private int inventory;

	public ProductRequest(Long productId, Long categoryId, String productName, String productDescription, int price,
		int inventory) {
		this.productId = productId;
		this.categoryId = categoryId;
		this.productName = productName;
		this.productDescription = productDescription;
		this.price = price;
		this.inventory = inventory;
	}
}
