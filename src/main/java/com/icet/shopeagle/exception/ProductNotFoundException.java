package com.icet.shopeagle.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String productNotFoundException) {
        super(productNotFoundException);
    }
}
