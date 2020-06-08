package com.zource.controller;

import com.zource.DTO.ProductDTO;
import com.zource.exceptions.product.ProductNotFoundException;
import com.zource.exceptions.product.ProductPresetNotFoundException;
import com.zource.model.Product;
import com.zource.model.ProductPreset;
import com.zource.model.User;
import com.zource.repository.UserRepository;
import com.zource.repository.product.ProductPresetRepository;
import com.zource.service.product.preset.ProductPresetService;
import com.zource.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
public class ProductPresetController {

    @Autowired
    ProductPresetRepository productPresetRepository;

    @Autowired
    ProductPresetService productPresetService;

    @Autowired
    UserService userService;

    public ProductPreset getProductPreset() {
        return null;
    }

    @GetMapping("/api/product/preset/{id}")
    public ResponseEntity getById(@PathVariable(value = "id", required = true) Long id) throws ProductPresetNotFoundException {

        ProductPreset pp = productPresetService.getById(id);

        System.out.println(pp.getId());

        return new ResponseEntity(pp, HttpStatus.OK);

    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/api/product/preset/user/{id}/list")
    public ResponseEntity getProductPresetsByUserId(@PathVariable(value = "id", required = true) Long id) {
        User u = this.userService.findById(id);
        return new ResponseEntity(this.productPresetRepository.findAllByUser(u), HttpStatus.OK);
    };

}
