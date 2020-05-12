package com.zource.service.product.images;

import com.zource.DTO.ProductImageDTO;
import com.zource.DTO.ProductImageUpdateDTO;
import com.zource.exceptions.ProductImageNotFoundException;
import com.zource.exceptions.ProductNotFoundException;
import com.zource.model.ProductImage;
import com.zource.repository.ProductImageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private ModelMapper modelMapper;

    public ProductImage getById(Long id) throws ProductImageNotFoundException {
        return this.productImageRepository.findById(id).orElseThrow(() -> new ProductImageNotFoundException("No product image was found for productImage Id = " + id));
    }

    @Override
    public ProductImageDTO convertToDto(ProductImage p) throws ProductImageNotFoundException, ProductNotFoundException {
        ProductImage prodImg = productImageService.getById(p.getId());
        ProductImageDTO  prodImgDto = modelMapper.map(prodImg, ProductImageDTO .class);
        return prodImgDto;
    }

    @Override
    public ProductImage convertToEntity(ProductImageDTO prodImgDto) {
            ProductImage post = modelMapper.map(prodImgDto, ProductImage.class);

            return post;
        }

    @Override
    public ProductImage convertToEntity(ProductImageUpdateDTO p) {
        ProductImage post = modelMapper.map(p, ProductImage.class);
        return null;
    }


    public List<ProductImage> getByProductId(Long productId) {
       return this.productImageRepository.findAll(Sort.by(Sort.Direction.DESC, "imageOrder")).stream().filter(img -> img.getProduct().getId() == productId).collect(Collectors.toList());
    }


}
