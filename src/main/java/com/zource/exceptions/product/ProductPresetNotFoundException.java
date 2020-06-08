package com.zource.exceptions.product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a product was not found.
 *
 * @author Mikhail Sigachev
 *
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProductPresetNotFoundException extends Exception {
    private static final long serialVersionUID = 1105045175631879877L;

    public ProductPresetNotFoundException(String message) {
        super(message);
    }

}
