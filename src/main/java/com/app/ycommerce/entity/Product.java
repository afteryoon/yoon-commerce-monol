package com.app.ycommerce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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

	@ManyToOne
	@JoinColumn(name = "order_item_id")
	private OrderItem orderItem;

	@OneToOne
	@JoinColumn(name = "like_item_id")
	private LikeItem likeItem;

	@ManyToOne
	@JoinColumn(name = "reservation_id")
	private Reservation reservation;

	@ManyToOne
	@JoinColumn(name = "wish_list_id")
	private WishList wishList;

}
