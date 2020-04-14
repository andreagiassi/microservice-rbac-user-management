package com.giassi.microservice.demo2.rest.users.exceptions;

public class InvalidConfigurationException extends RuntimeException {

    public InvalidConfigurationException(String message) {
        super(message);
    }

}
