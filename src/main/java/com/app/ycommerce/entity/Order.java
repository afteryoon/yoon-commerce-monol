package com.app.ycommerce.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;//주문상태 배치,배송중,배송완료,구매,환불

	private LocalDateTime orderDate;
	private int amount;
	private Address deliveryAddress;

	@OneToMany
	private List<OrderItem> orderItems = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	public static Order createOrder(Member member, Address deliveryAddress) {
		return Order.builder()
			.member(member)
			.orderStatus(OrderStatus.PLACED)
			.orderDate(LocalDateTime.now())
			.deliveryAddress(deliveryAddress)
			.build();
	}

	public void addOrderItems(List<OrderItem> orderItems) {
		int amount = 0;
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrder(this);
			this.orderItems.add(orderItem);
			amount += orderItem.getProduct().getPrice();
		}
		this.amount = amount;
	}

}

