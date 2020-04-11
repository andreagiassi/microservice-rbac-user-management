package com.giassi.microservice.demo2.rest.users.exceptions;

public class RoleInUseException extends RuntimeException {

    public RoleInUseException(String message) {
        super(message);
    }

}
