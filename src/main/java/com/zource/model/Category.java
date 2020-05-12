/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */

package com.zource.model;

import com.fasterxml.jackson.annotation.*;
import com.zource.model.jsonViews.CategoryView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
@Getter
@Setter
//@JsonIgnoreProperties( value= {"parentCategories"})
public class Category {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @JsonIgnore
    @Column(name = "image", length = 100)
    private String logoFileName;

    @JsonIgnore
    @Column(name = "top_banner", length = 100)
    private String topBanner;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "parent_child_category",
            joinColumns = @JoinColumn(name = "child_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "parent_id", nullable = false))
    @JsonIgnore
    private Set<Category> parentCategories = new HashSet<>();

    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonView(CategoryView.Children.class)
    @ManyToMany(mappedBy = "parentCategories", fetch = FetchType.LAZY)
    private Set<Category> childCategories = new HashSet<>();

    @JsonView(CategoryView.Children.class)
    @ManyToOne(targetEntity = Brand.class)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @JsonIgnore
    @ManyToMany(mappedBy = "categories", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Product> products = new HashSet<>();

    public Category() {
    }


}
