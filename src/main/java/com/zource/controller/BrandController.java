package com.zource.controller;


import com.zource.model.Brand;
import com.zource.repository.BrandRepository;
import com.zource.service.brand.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class BrandController {

    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private BrandService brandService;

    @GetMapping("/api/brands/all")
    public ResponseEntity allBrands(){
        return new ResponseEntity(brandRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("api/brands")
    public ResponseEntity<?> getProducts(@RequestParam(name = "filterName", defaultValue = "") String filterName){
        return new ResponseEntity(brandService.find(filterName), HttpStatus.OK);
    }

    @GetMapping("api/brand/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id) {
        Optional<Brand> brand = this.brandRepository.findById(id);

        if (brand == null) return new ResponseEntity(HttpStatus.NO_CONTENT);

        else return new ResponseEntity(brand, HttpStatus.OK);
    }

}
