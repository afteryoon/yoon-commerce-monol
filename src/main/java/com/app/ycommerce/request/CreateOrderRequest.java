package com.app.ycommerce.request;

import java.util.List;

import com.app.ycommerce.entity.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
	private Long memberId;
	private Address address;
	private List<ProductAndCount> productAndCounts;
}
