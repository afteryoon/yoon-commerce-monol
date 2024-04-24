package com.app.ycommerce.request;

import java.util.List;

import com.app.ycommerce.dto.CartItemDto;
import com.app.ycommerce.entity.Address;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderRequest {

	private List<CartItemDto> cartItems;
	private Address deliveryAddress;
	//private PaymentDto payment;
}
