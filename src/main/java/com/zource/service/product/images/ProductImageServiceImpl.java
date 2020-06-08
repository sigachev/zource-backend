package com.zource.service.product.images;

import com.zource.DTO.ProductImageDTO;
import com.zource.DTO.ProductImageUpdateDTO;
import com.zource.exceptions.product.ProductImageNotFoundException;
import com.zource.exceptions.product.ProductNotFoundException;
import com.zource.model.ImageModel;
import com.zource.model.Product;
import com.zource.model.ProductImage;
import com.zource.repository.product.ProductImageRepository;
import com.zource.service.storage.AmazonS3BucketService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AmazonS3BucketService amazonS3BucketService;

    public ProductImage getById(Long id) throws ProductImageNotFoundException {
        return this.productImageRepository.findById(id).orElseThrow(() -> new ProductImageNotFoundException("No product image was found for productImage Id = " + id));
    }

    @Override
    public ProductImageDTO convertToDto(ProductImage p) throws ProductImageNotFoundException, ProductNotFoundException {
        ProductImage prodImg = this.getById(p.getId());
        ProductImageDTO prodImgDto = modelMapper.map(prodImg, ProductImageDTO.class);
        return prodImgDto;
    }

    @Override
    public ProductImage convertToEntity(ProductImageDTO prodImgDto) {
        ProductImage pp = modelMapper.map(prodImgDto, ProductImage.class);
        return pp;
    }

    @Override
    public ProductImage convertToEntity(ProductImageUpdateDTO p) {
        ProductImage pp = modelMapper.map(p, ProductImage.class);
        return pp;
    }

    @Override
    public void addImage(MultipartFile f, Product product) {
        ImageModel img = new ImageModel();
        final String ext = FilenameUtils.getExtension(f.getOriginalFilename());
        String name = "products/" + String.format("%s.%s", RandomStringUtils.randomAlphanumeric(10), ext);

        // if file exist --> regenerate file name
        while (amazonS3BucketService.doesObjectExist(name)) {
            name = "products/" + String.format("%s.%s", RandomStringUtils.randomAlphanumeric(10), ext);
        }


        img.setFileName(name);

        ProductImage pi = new ProductImage();
        pi.setImage(img);
       /* pi.setImageOrder();*/
        pi.setProduct(product);

        this.productImageRepository.save(pi);
/*
        System.out.println("Before S3 filename: " + f.getOriginalFilename());
        System.out.println("Before S3 img: " + img.getFileName());*/

        amazonS3BucketService.uploadFile(f, img.getFileName());
    }

    @Override
    public ProductImage update(ProductImage prodImg) throws ProductImageNotFoundException {
        ProductImage pi = this.getById(prodImg.getId());
            pi.setImageOrder(prodImg.getImageOrder());
        return pi;
    }


    @Override
    @Transactional
    public void delete(ProductImage pi) {
        //delete image from AWS S3 if it is not in use by other products
        if (pi.getImage().getProductImages().size() == 1) {
            this.amazonS3BucketService.deleteFileFromBucket(pi.getImage().getFileName());
        }
        //need to remove child from parent before deleting from repository
        pi.getImage().getProductImages().remove(pi);
        this.productImageRepository.deleteById(pi.getId());
    }

    public List<ProductImage> getByProductId (Long productId){
            return this.productImageRepository.findAll(Sort.by(Sort.Direction.DESC, "imageOrder")).stream().filter(img -> img.getProduct().getId() == productId).collect(Collectors.toList());
        }


    }
