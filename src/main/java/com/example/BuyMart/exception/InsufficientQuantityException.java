package com.example.BuyMart.exception;

public class InsufficientQuantityException extends Exception{

    public InsufficientQuantityException(String message){
        super(message);
    }
}
