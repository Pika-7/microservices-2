package com.app.inventoryservice.exception;

public class GlobalExceptionHandler extends RuntimeException{
    public GlobalExceptionHandler(String message) {
        super(message);
    }
}
