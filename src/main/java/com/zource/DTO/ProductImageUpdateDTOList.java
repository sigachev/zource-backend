package com.zource.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductImageUpdateDTOList {

    @JsonProperty("productImageUpdateDTOList")
    List<ProductImageUpdateDTO> productImageUpdateDTOList;


}
