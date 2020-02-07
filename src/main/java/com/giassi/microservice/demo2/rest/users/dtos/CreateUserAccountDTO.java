package com.giassi.microservice.demo2.rest.users.dtos;

import lombok.Data;

/**
 * DTO to create a new user account
 */
@Data
public class CreateUserAccountDTO {

    private String username;
    private String name;
    private String surname;
    private String email;
    private String gender;

}
