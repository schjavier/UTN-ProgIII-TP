package com.utn.ProgIII.exceptions;

public class ProductSupplierNotExistException extends RuntimeException {
    public ProductSupplierNotExistException(String msg){
        super(msg);
    }
}
