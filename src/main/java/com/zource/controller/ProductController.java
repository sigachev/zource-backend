package com.zource.controller;

import com.zource.DTO.*;
import com.zource.exceptions.product.ProductImageNotFoundException;
import com.zource.exceptions.product.ProductNotFoundException;
import com.zource.model.Product;
import com.zource.model.ProductImage;
import com.zource.service.product.ProductDTOService;
import com.zource.service.product.ProductService;
import com.zource.service.product.images.ProductImageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    @Autowired
    private Environment environment;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private ProductDTOService productDTOService;

    @Autowired
    private ModelMapper modelMapper;


    @Value("${api.url}")
    private String apiUrl;

    @GetMapping("/api/products/all")
    public ResponseEntity<?> getAllProducts() {
        return new ResponseEntity(productService.findAllProducts(), HttpStatus.OK);
    }

/*    @PostMapping
    public void newExam(@DTO(ProductDTO.class) Product prod) {
        productRepository.save(prod);
    }*/


    @GetMapping("/api/products")
    public ResponseEntity<?> getProducts(@RequestParam(name = "categoryId", defaultValue = "0") Long categoryId,
                                         @RequestParam(name = "brandId", defaultValue = "0") Long brandId,
                                         @RequestParam(name = "name", defaultValue = "") String name) {
        return new ResponseEntity(productService.findAll(categoryId, brandId, name), HttpStatus.OK);
    }

    @GetMapping("/api/product/{id}")
    @Transactional(readOnly = true)
    /*    @JsonView(ProductView.Short.class)*/
    public ResponseEntity getById(@PathVariable(value = "id", required = true) Long id) throws ProductNotFoundException {

        Product product = productService.getById(id);

        return new ResponseEntity(new ProductDTO(product), HttpStatus.OK);

    }


    @PutMapping("/api/product/update")
    public ResponseEntity<Product> updateProduct(@DTO(ProductDTO.class) Product product) throws ProductNotFoundException {
        return ResponseEntity.ok().body(this.productService.updateProduct(product));
    }

    @PostMapping("/api/product/new")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        return new ResponseEntity(this.productService.saveProduct(product), HttpStatus.OK);
    }

/*
    @PostMapping("/api/product/{id}/uploadImages")
    public ResponseEntity uploadFile(@RequestPart(value = "file") MultipartFile file) {


        return new ResponseEntit5y(this.amazonS3BucketService.uploadFile(file), HttpStatus.OK);
    }*/

    @GetMapping("/api/product/{id}/images")
    public ResponseEntity<List<ProductImageDTO>> getProductImages(@PathVariable Long id) {

        return new ResponseEntity(productDTOService.getProductImageDTOList(id), HttpStatus.OK);

    }

    /*
        @Secured("ROLE_ADMIN")*/
    @PostMapping(value = "/api/product/{id}/updateImages",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Sbm Data Transfer Service", response = Iterable.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully find."),
            @ApiResponse(code = 400, message = "There has been an error."),
            @ApiResponse(code = 401, message = "You are not authorized to save the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})

    public ResponseEntity updateImages(@RequestPart(value = "data", required = false) Set<ProductImageUpdateDTO> data,
                                       @RequestPart(value = "files", required = false) final MultipartFile[] files,
                                       @PathVariable final Long id) throws ProductNotFoundException {


        System.out.println("START DATA");
        for (ProductImageUpdateDTO p : data) {
            System.out.println(p.getId());
        }

        System.out.println(data);

        System.out.println("FINISH DATA");
        System.out.println();
        System.out.println("START FILES");
        for (int i = 0; i < files.length; i++)
            System.out.println(files[i].getOriginalFilename());
        System.out.println("FINISH FILES");


        Product product = productService.getById(id);

        Set<ProductImage> existingSet = product.getProductImages();
        Set<ProductImage> newSet = new HashSet();

        //update product images
        if (!data.isEmpty()) {
            data.stream().forEach(d -> {
                newSet.add(productImageService.convertToEntity(d));
            });
            newSet.forEach(s -> {
                try {
                    productImageService.update(s);
                } catch (ProductImageNotFoundException e) {
                    new ProductImageNotFoundException(e.getMessage());
                }
            });
        }


        // check for deletes - if any
        // deleteSet contains only elements that do not exist in newSet
        Set<ProductImage> deleteSet = existingSet.stream().filter(s1 -> newSet.stream().noneMatch(s2 -> s1.getId().equals(s2.getId()))).collect(Collectors.toSet());
        deleteSet.forEach(s -> productImageService.delete(s));

        //set of added files with
        Set<ProductImage> fileSet = newSet.stream().filter(s -> s.getId() == 0).collect(Collectors.toSet());
        System.out.println("fileSet size: " + fileSet.size());
        for (MultipartFile f : files) {
            this.productImageService.addImage(f, product);
        }

        return new ResponseEntity(data, HttpStatus.OK);

    }

    @PostMapping("/api/product/{id}/uploadImages")
    public ResponseEntity uploadImages(@RequestBody ProductImageUpdateDTOList productImageUpdateDTOList) {
        System.out.println(productImageUpdateDTOList);
        return new ResponseEntity(productImageUpdateDTOList, HttpStatus.OK);

    }

/*    @GetMapping("/api/product/{id}/images")
    public ResponseEntity<List<ProductImageDTO>> getProductImages(@PathVariable Long id) throws UnsupportedEncodingException {

        String url = apiUrl + "product/" + id;
        ResponseEntity<ProductDTO> productEntity = restTemplate.getForEntity(url, ProductDTO.class);
        if (productEntity.hasBody()) {
            ProductDTO productDTO = productEntity.getBody();

            return new ResponseEntity(productDTOService.getProductImageDTOList(productDTO), HttpStatus.OK);

        } else
            return new ResponseEntity(HttpStatus.NO_CONTENT);

    }*/

}
