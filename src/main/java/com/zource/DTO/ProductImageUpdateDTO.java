package com.zource.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zource.model.ProductImage;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductImageUpdateDTO {


    private Long id;
    @JsonProperty("url")
    private String url;
    private short order;




    public ProductImageUpdateDTO() {

    }

    public ProductImageUpdateDTO(ProductImage productImage) {
        this.setId(productImage.getId());

        this.setOrder(productImage.getImageOrder());
    }

    public ProductImageUpdateDTO(Long id,String url, short order) {
        this.id = id;
        this.url = url;
        this.order = order;

    }
}


