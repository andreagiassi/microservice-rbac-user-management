package com.giassi.microservice.demo2.rest.users.exceptions;

public class InvalidRoleIdentifierException extends RuntimeException {

    public InvalidRoleIdentifierException(String message) {
        super(message);
    }

}
