package com.zource.controller;

import com.zource.model.Category;
import com.zource.repository.CategoryRepository;
import com.zource.service.category.CategoryService;
import com.zource.service.storage.AmazonS3BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;
    private Long id;

    private AmazonS3BucketService amazonS3BucketService;

    @GetMapping("/api/category/{id}")
    public ResponseEntity getCategoryById(@PathVariable("id") Long id) {
        Optional cat = categoryRepository.findById(id);
        if (cat.isPresent())
            return new ResponseEntity(cat.get(), HttpStatus.OK);
        else return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/api/category/{id}/products")
    public ResponseEntity getAllProducts(@PathVariable("id") Long id) {

        return new ResponseEntity(categoryService.getAllProducts(id), HttpStatus.OK);
    }



    @GetMapping("/api/categories/all")
    public ResponseEntity allCategories() {
        return new ResponseEntity(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/api/category/{id}/childCategories")
    public ResponseEntity<?> getChildCategories(@PathVariable("id") Long id) {

        Optional<Category> cat = categoryRepository.findById(id);

        if (cat.isPresent())
            return new ResponseEntity(cat.get().getChildCategories(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


/*
    @PostMapping("/api/category/{id}/uploadImage")
    @Transactional
    public ResponseEntity uploadFile(@PathVariable("id") Long id, @RequestPart(value = "file") MultipartFile file) {
        Category c = this.categoryRepository.findById(id);

        String fileURL = this.amazonS3BucketService.uploadFile(file);
this.categoryRepository.save();

        return new ResponseEntity(fileURL, HttpStatus.OK);
    }
*/

}
