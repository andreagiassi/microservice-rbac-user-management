package com.giassi.microservice.demo2.rest.user.dtos;

import lombok.Data;

/**
 * Create User DTO
 */
@Data
public class CreateOrUpdateUserDTO {

    private String username;
    private String name;
    private String surname;
    private String email;
    private boolean enabled;
    private String gender;
    private String phone;

}
