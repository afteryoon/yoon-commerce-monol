package com.app.ycommerce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_item_id")
	private Long id;

	private int quantity;
	private int price;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;

	public static OrderItem createOrderItem(Product product, int quantity, int price) {
		return OrderItem.builder()
			.product(product)
			.quantity(quantity)
			.price(price)
			.build();
	}

	@Override
	public String toString() {
		return "OrderItem{" +
			"id=" + id +
			", quantity=" + quantity +
			// ", order=" + order +
			", product=" + product +
			'}';
	}
}
