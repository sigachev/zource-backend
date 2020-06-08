package com.zource.service.product.images;

import com.zource.DTO.ProductImageDTO;
import com.zource.DTO.ProductImageUpdateDTO;
import com.zource.exceptions.product.ProductImageNotFoundException;
import com.zource.exceptions.product.ProductNotFoundException;
import com.zource.model.Product;
import com.zource.model.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductImageService {

    ProductImage getById(Long Id) throws ProductImageNotFoundException;
    List<ProductImage> getByProductId(Long productId);
    ProductImageDTO convertToDto(ProductImage prodImg) throws ProductImageNotFoundException, ProductNotFoundException;
    ProductImage convertToEntity(ProductImageDTO prodImgDto);
    ProductImage convertToEntity(ProductImageUpdateDTO prodImgDto);
    void addImage(MultipartFile file, Product product);
    ProductImage update(ProductImage prodImg) throws ProductImageNotFoundException;
    void delete (ProductImage pi);

}
