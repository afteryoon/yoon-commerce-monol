package com.app.ycommerce.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private int id;
	private String name;
	private String password;
	private String email;
	private String phone;
	//    @Embedded
	private String address;
	private LocalDate createdAt;
	private String role;

	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	private List<Order> orders = new ArrayList<>();

	// @OneToMany(mappedBy = "member")
	// private List<WishList> wishLists = new ArrayList<>();

	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	private List<Reservation> reservations = new ArrayList<>();

	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	private List<LikeItem> LikeItems = new ArrayList<>();

	@OneToOne
	@JoinColumn(name = "cart_id")
	private Cart cart;
}
