package com.zource.service.product.images;

import com.zource.DTO.ProductImageDTO;
import com.zource.DTO.ProductImageUpdateDTO;
import com.zource.exceptions.ProductImageNotFoundException;
import com.zource.exceptions.ProductNotFoundException;
import com.zource.model.ProductImage;

import java.util.List;

public interface ProductImageService {

    ProductImage getById(Long Id) throws ProductImageNotFoundException, ProductNotFoundException;
    List<ProductImage> getByProductId(Long productId);
    ProductImageDTO convertToDto(ProductImage prodImg) throws ProductImageNotFoundException, ProductNotFoundException;
    ProductImage convertToEntity(ProductImageDTO prodImgDto);
    ProductImage convertToEntity(ProductImageUpdateDTO prodImgDto);
}
