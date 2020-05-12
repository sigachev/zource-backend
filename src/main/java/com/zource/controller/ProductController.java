package com.zource.controller;

import com.zource.DTO.*;
import com.zource.exceptions.ProductNotFoundException;
import com.zource.model.ImageModel;
import com.zource.model.Product;
import com.zource.model.ProductImage;
import com.zource.repository.ProductImageRepository;
import com.zource.service.product.ProductDTOService;
import com.zource.service.product.ProductService;
import com.zource.service.product.images.ProductImageService;
import com.zource.service.storage.AmazonS3BucketService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.tomcat.jni.Time;
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

@RestController
public class ProductController {

    @Autowired
    private Environment environment;


    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductDTOService productDTOService;

    @Autowired
    private AmazonS3BucketService amazonS3BucketService;

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
    public ResponseEntity<Product> updateProduct(@DTO(ProductDTO.class) Product product) {
        return ResponseEntity.ok().body(this.productService.updateProduct(product));
    }

/*
    @PostMapping("/api/product/{id}/uploadImages")
    public ResponseEntity uploadFile(@RequestPart(value = "file") MultipartFile file) {


        return new ResponseEntity(this.amazonS3BucketService.uploadFile(file), HttpStatus.OK);
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



        Set<ProductImage> existingSet = new HashSet();
        Set<ProductImage> newSet = new HashSet();

        if (data.isEmpty())
            return new ResponseEntity(null, HttpStatus.OK);

        Product product = this.productService.getById(id);

        existingSet = product.getProductImages();
        data.stream().forEach(d -> {
            newSet.add(productImageService.convertToEntity(d));
        });



        for (MultipartFile f: files) {
            ImageModel img = new ImageModel();
            final String ext = FilenameUtils.getExtension(f.getOriginalFilename());
            final String name = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(10), ext);
            img.setFileName("products/" + name);

            ProductImage pi = new ProductImage();
            pi.setImage(img);
            pi.setProduct(product);

            this.productImageRepository.save(pi);

            System.out.println("Before S3 filename: " + f.getOriginalFilename());
            System.out.println("Before S3 img: " + img.getFileName());

            amazonS3BucketService.uploadFile(f, img.getFileName());

        }



/*
        for (ProductImageUpdateDTO productImageDTO : data.getProductImageUpdateDTOList()) {
            try {
                ProductImage productImage = this.productImageService.getById(productImageDTO.getId());
                productImage.setImageOrder(productImageDTO.getOrder());
                this.productImageRepository.save(productImage);
            } catch (ProductImageNotFoundException e) {
                e.printStackTrace();
            }



        }
*/

        ProductImageUpdateDTOList result = new ProductImageUpdateDTOList();
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
