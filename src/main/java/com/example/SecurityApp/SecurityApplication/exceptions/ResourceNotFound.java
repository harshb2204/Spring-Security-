package com.example.SecurityApp.SecurityApplication.exceptions;

public class ResourceNotFound extends RuntimeException{

    public ResourceNotFound(String message){
        super(message);
    }
}
