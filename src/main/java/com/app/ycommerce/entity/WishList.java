// package com.app.ycommerce.entity;
//
// import java.util.ArrayList;
// import java.util.List;
//
// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.OneToMany;
// import lombok.Getter;
// import lombok.Setter;
//
// @Entity
// @Getter
// @Setter
// public class WishList {
//
// 	@Id
// 	@GeneratedValue(strategy = GenerationType.IDENTITY)
// 	@Column(name = "wish_list_id")
// 	private int id;
//
// 	private int quantity;
//
// 	@OneToMany(mappedBy = "wishList")
// 	List<Product> products = new ArrayList<>();
//
// 	@ManyToOne
// 	@JoinColumn(name = "member_id")
// 	private Member member;
//
// }
