package com.app.ycommerce.dto;

import com.app.ycommerce.entity.CartItem;
import com.app.ycommerce.entity.Product;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartItemDto {
	private Product product;
	private int quantity;
	private Long cartId;

	public static CartItemDto mapToCartItemDto(CartItem cartItem) {
		return CartItemDto.builder()
			.cartId(cartItem.getCart().getId())
			.quantity(cartItem.getQuantity())
			.product(cartItem.getProduct())
			.build();
	}

}
