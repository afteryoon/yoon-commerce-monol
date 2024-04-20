package com.app.ycommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class WishList {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wish_list_id")
    private int id;

    private int quantity;

    @OneToMany(mappedBy = "wishList")
    List<Product> products = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name ="member_id")
    private Member member;

}
