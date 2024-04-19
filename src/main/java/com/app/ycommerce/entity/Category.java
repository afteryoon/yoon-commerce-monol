package com.app.ycommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int id;
    private String categoryTitle;

    @OneToMany(mappedBy = "category")
    private List<Product> products= new ArrayList<>();


}
