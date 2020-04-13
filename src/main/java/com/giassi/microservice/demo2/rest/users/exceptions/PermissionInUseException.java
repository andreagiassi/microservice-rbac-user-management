package com.giassi.microservice.demo2.rest.users.exceptions;

public class PermissionInUseException extends RuntimeException {

    public PermissionInUseException(String message) {
        super(message);
    }

}
