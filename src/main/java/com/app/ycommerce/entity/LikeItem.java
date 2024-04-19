package com.app.ycommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter @Setter
public class LikeItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_item_id")
    private int id;
    private int quantity;

    @OneToMany(mappedBy = "likeItem")
    private List<Product> products =new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

}
