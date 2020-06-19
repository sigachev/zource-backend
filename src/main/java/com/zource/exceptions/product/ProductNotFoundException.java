package com.zource.exceptions.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a product was not found.
 *
 * @author Mikhail Sigachev
 *
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends Exception {
    private static final long serialVersionUID = 1105045175631879877L;

    public ProductNotFoundException(String message) {
        super(message);
    }

}
