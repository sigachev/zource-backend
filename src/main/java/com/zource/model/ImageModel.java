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


    @OneToMany(mappedBy="image", orphanRemoval = true)
    private Set<ProductImage> productImages;


}
