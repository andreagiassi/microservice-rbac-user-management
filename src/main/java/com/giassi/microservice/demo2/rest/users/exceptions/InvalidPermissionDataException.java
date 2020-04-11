package com.giassi.microservice.demo2.rest.users.exceptions;

public class InvalidPermissionDataException extends RuntimeException {

    public InvalidPermissionDataException(String message) {
        super(message);
    }

}
