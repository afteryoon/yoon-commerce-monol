package com.app.ycommerce.request;

import com.app.ycommerce.entity.Address;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderRequest {

	private int cart;
	private Address deliveryAddress;
	//private PaymentDto payment;
}
