package com.giassi.microservice.demo2.rest.users.exceptions;

public class InvalidRoleDataException extends RuntimeException {

    public InvalidRoleDataException(String message) {
        super(message);
    }

}
