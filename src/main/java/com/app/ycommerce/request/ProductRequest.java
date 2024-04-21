package com.app.ycommerce.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class ProductRequest {
	private int productId;
	private int categoryId;
	private String productName;
	private String productDescription;
	private int price;
	private int inventory;

	public ProductRequest(int productId, int categoryId, String productName, String productDescription, int price,
		int inventory) {
		this.productId = productId;
		this.categoryId = categoryId;
		this.productName = productName;
		this.productDescription = productDescription;
		this.price = price;
		this.inventory = inventory;
	}
}
