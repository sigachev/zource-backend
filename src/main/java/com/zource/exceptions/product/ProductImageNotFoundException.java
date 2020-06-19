package com.zource.exceptions.product;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProductImageNotFoundException extends Exception {
    private static final long serialVersionUID = 1105045175631879877L;

    public ProductImageNotFoundException(String message) {
        super(message);
    }

}
