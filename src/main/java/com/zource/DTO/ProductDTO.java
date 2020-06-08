package com.zource.DTO;

import com.fasterxml.jackson.annotation.*;
import com.zource.DTO.resolvers.EntityIdResolver;
import com.zource.model.Brand;
import com.zource.model.Product;
import com.zource.model.ProductImage;
import com.zource.model.UnitOfMeasure;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ProductDTO {

    @Id
    @NotNull
    private Long id;

    @NotNull(message = "Name is required.")
    private String name;

    private String sku;

    private String description;

    @NotNull(message = "Price is required.")
    private BigDecimal price;

    private UnitOfMeasure priceFor;

    private UnitOfMeasure soldBy;

    private double uomRatio;

    private Date create_date;

    @NotNull(message = "Enabled is required.")
    private boolean enabled;

    @NotNull(message = "Brand is required.")
    /*  @JsonDeserialize(using = BrandDeserializer.class)*/
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id",
            resolver = EntityIdResolver.class,
            scope = Brand.class)
    @JsonIdentityReference(alwaysAsId = true)
    private Brand brand;

    private List<ProductImageDTO> images = new ArrayList<>();

/*    @JsonIgnore
    private List<ProductImageDTO> images;*/


    public ProductDTO() {
    }

    public ProductDTO(Product product) {

        this.setId(product.getId());
        this.setName(product.getName());
        this.sku = product.getSku();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.priceFor = product.getPriceFor();
        this.soldBy = product.getSoldBy();
        this.uomRatio = product.getUomRatio();
        this.create_date = product.getCreate_date();
        this.setEnabled(product.isEnabled());
        this.brand = product.getBrand();
        this.images = this.getProductImageDTOList(product);
    }

    public List<ProductImageDTO> getProductImageDTOList(Product product) {
        List<ProductImageDTO> resultList = new ArrayList<>();

        for (ProductImage prodImage : product.getProductImages())
            resultList.add(new ProductImageDTO(prodImage));

        return resultList;
    }


}
