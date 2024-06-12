package com.app.ycommerce.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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
	@Embedded
	private Address deliveryAddress;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private final List<OrderItem> orderItems = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	//연관관계 메서드
	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}

	//==생성메서드==//
	public static Order createOrder(Member member, Address deliveryAddress, List<OrderItem> orderItems) {
		Order order = Order.builder()
			.member(member)
			.orderStatus(OrderStatus.PLACED)
			.orderDate(LocalDateTime.now())
			.deliveryAddress(deliveryAddress)
			.build();

		for (OrderItem orderItem : orderItems) {
			order.addOrderItem(orderItem);
			order.sumPrice(orderItem.getProduct().getPrice() * orderItem.getQuantity());
		}

		return order;
	}

	public void sumPrice(int price) {
		this.amount = amount + price;
	}

	//==출력 메서드==//
	@Override
	public String toString() {
		return "Order{" + "id=" + id + ", orderStatus=" + orderStatus + ", orderDate=" + orderDate + ", amount="
			+ amount + ", deliveryAddress=" + deliveryAddress + ", orderItems=" + orderItems + ", member=" + member
			+ '}';
	}
}

