package com.app.ycommerce.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;
	private String name;
	private String password;
	private String email;
	private String phone;
	@Embedded
	private Address address;
	private LocalDate createdAt;
	private String role;

	// @OneToMany(fetch = FetchType.LAZY)
	// private List<Order> orders = new ArrayList<>();

	// @OneToMany(mappedBy = "member")
	// private List<WishList> wishLists = new ArrayList<>();

	// @OneToMany(fetch = FetchType.LAZY)
	// private List<Reservation> reservations = new ArrayList<>();

	// @OneToMany(fetch = FetchType.LAZY)
	// private List<LikeItem> LikeItems = new ArrayList<>();

	// @OneToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "cart_id")
	// private Cart cart;
}
