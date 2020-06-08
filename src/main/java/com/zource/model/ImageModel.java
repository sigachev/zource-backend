package com.zource.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "image")
public class ImageModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "type")
    private String type;

// With CascadeType.ALL child entity is managed by parent. You need to detach it before remove.
    @OneToMany(mappedBy="image", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<ProductImage> productImages;


}
