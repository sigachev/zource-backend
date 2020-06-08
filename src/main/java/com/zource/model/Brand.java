package com.zource.model;

import com.fasterxml.jackson.annotation.*;
import com.zource.model.jsonViews.BrandView;
import com.zource.model.jsonViews.ProductView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "products"})

@Table(name = "brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ProductView.Short.class)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotNull
    @JsonView(ProductView.Short.class)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @Column(name = "enabled", columnDefinition = "bit default 0", nullable = false)
    private boolean enabled;

    @Column(name = "featured", columnDefinition = "bit default 0", nullable = false)
    private boolean featured;

    @OneToMany(mappedBy = "brand", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonView(BrandView.Full.class)
    private Set<Product> products = new HashSet<>();

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Category> brandCategories;

}
