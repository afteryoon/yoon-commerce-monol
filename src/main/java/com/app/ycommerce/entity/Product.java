package com.app.ycommerce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("checkstyle:RegexpMultiline")
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
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	private Category category;

	private String name;
	private String description;
	private int price;
	private int inventory;

	// @OneToMany
	// //@JsonIgnore
	// private List<Reservation> reservations = new ArrayList<>();

	// @ManyToOne
	// @JoinColumn(name = "wish_list_id")
	// private WishList wishList;

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
