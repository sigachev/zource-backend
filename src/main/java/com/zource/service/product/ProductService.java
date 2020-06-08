package com.zource.service.product;

import com.zource.exceptions.product.ProductNotFoundException;
import com.zource.model.Product;
import com.zource.model.ProductImage;
import org.springframework.security.access.annotation.Secured;

import java.util.List;
import java.util.Set;

public interface ProductService {

    Product getById(Long Id) throws ProductNotFoundException;

    Set<ProductImage>  getProductImages();

    @Secured("ROLE_ADMIN")
    Product saveProduct(Product product);

    List<Product> findAllProducts();
    List<Product> findAll(Long categoryId, Long brandId, String name);

    Product updateProduct(Product product) throws ProductNotFoundException;
    void deleteProduct(Long id);
}
