package com.app.ycommerce.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	private String name;
	private String description;
	private int price;
	private int inventory;

	@OneToOne
	@JoinColumn(name = "order_item_id")
	private OrderItem orderItem;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private List<LikeItem> likeItems = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "reservation_id")
	private Reservation reservation;

	// @ManyToOne
	// @JoinColumn(name = "wish_list_id")
	// private WishList wishList;

	@OneToOne
	@JoinColumn(name = "cart_id")
	private CartItem cartItem;

	public void decreaseInventory(int quantity) {
		if (this.inventory < quantity) {
			throw new IllegalArgumentException("재고 수량이 부족합니다.");
		}
		this.inventory -= quantity;
	}

	public void increaseInventory(int quantity) {
		this.inventory += quantity;
	}
}
