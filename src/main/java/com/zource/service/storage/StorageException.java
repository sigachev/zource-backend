/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */

package com.zource.service.storage;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StorageException extends RuntimeException {

    private static final long serialVersionUID = 7718828512143293558L;
    private final Integer code = 0;
    private String pageURL = "";


    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }


    public StorageException(String message, String pageURL) {
        super(message);
        this.pageURL = pageURL;
    }


    public StorageException(String message, Throwable cause, String pageURL) {
        super(message, cause);
        this.pageURL = pageURL;
    }
}
