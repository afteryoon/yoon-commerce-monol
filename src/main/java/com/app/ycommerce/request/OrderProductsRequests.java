package com.app.ycommerce.request;

import java.util.List;

import com.app.ycommerce.entity.Address;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderProductsRequests {

	private List<ProductAndCount> productAndCounts;
	private Address address;

}
