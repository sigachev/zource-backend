package com.zource.service.product;

import com.zource.DTO.ProductImageDTO;
import com.zource.model.Product;
import com.zource.model.ProductImage;
import com.zource.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductDTOServiceImpl implements ProductDTOService{

    @Autowired
    private ProductRepository productRepository;


    public List<ProductImageDTO> getProductImageDTOList(Long productId) {
        List<ProductImageDTO> resultList = new ArrayList<>();
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent())
            for (ProductImage prodImage : productOptional.get().getProductImages()) {
                ProductImageDTO prodImageDTO = new ProductImageDTO(prodImage);
                resultList.add(prodImageDTO);
        }

        return resultList;
    }


}
