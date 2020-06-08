package com.zource.model;

import com.fasterxml.jackson.annotation.*;
import com.zource.DTO.resolvers.EntityIdResolver;
import com.zource.model.jsonViews.ProductView;
import lombok.Data;
import lombok.Value;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "product_preset")
public class ProductPreset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonProperty("userId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "sku")
    private String SKU;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")

    @JsonProperty("brand")
/*    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id",
            resolver = EntityIdResolver.class,
            scope = Brand.class)
    @JsonIdentityReference(alwaysAsId = true)*/
    @JsonIgnoreProperties({"description", "type", "enabled", "featured"})
    private Brand brand;

    @Column(name = "active", nullable = true, columnDefinition = "TINYINT", length = 1)
    @ColumnDefault("0")
    private boolean active;

}
