package com.giassi.microservice.demo2.rest.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RoleNotFoundException extends RuntimeException {

}
