package com.zource.DTO;

import com.zource.model.ProductImage;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Array;

@Data
public class ProductImageDTO {
    @NotNull
    private Long id;
    private String name;
    private String fileName;
    private String type;
    private short imageOrder;


    public ProductImageDTO(ProductImage productImage) {
        this.setId(productImage.getId());
        this.setName(productImage.getImage().getName());
        this.setFileName(productImage.getImage().getFileName());
        this.setType(productImage.getImage().getType());
        this.setImageOrder(productImage.getImageOrder());
    }

}


