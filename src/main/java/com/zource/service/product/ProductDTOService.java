package com.zource.service.product;

import com.zource.DTO.ProductImageDTO;

import java.util.List;


public interface ProductDTOService {

    List<ProductImageDTO> getProductImageDTOList(Long productId);

}
