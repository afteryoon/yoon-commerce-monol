package com.app.ycommerce.dto;

import java.util.List;

import com.app.ycommerce.entity.Address;
import com.app.ycommerce.request.OrderProductsRequests;
import com.app.ycommerce.request.ProductAndCount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
	private Address address;
	private List<ProductAndCount> productAndCounts;

	public static OrderDto createOrderDto(OrderProductsRequests orderProductsRequests) {
		return OrderDto.builder()
			.productAndCounts(orderProductsRequests.getProductAndCounts())
			.address(orderProductsRequests.getAddress())
			.build();
	}
}
