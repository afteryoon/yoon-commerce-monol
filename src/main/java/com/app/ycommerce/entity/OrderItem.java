package com.app.ycommerce.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private int id;

    private int quantity;

    @ManyToOne()
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToMany(mappedBy = "orderItem")
    private List<Product> products = new ArrayList<>();



}
