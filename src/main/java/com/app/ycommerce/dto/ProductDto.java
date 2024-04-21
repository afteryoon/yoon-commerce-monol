package com.app.ycommerce.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class ProductDto {

	private int productId;
	private int categoryId;
	private String productName;
	private String productDescription;
	private int price;
	private int inventory;

	public ProductDto(int productId, int categoryId, String productName, String productDescription, int price,
		int inventory) {
		this.productId = productId;
		this.categoryId = categoryId;
		this.productName = productName;
		this.productDescription = productDescription;
		this.price = price;
		this.inventory = inventory;
	}
}
