package com.demo.upload;

/**
 * @author jam
 * @description
 * @create 2018-05-15
 **/
public class FileStorageException extends RuntimeException {
    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
