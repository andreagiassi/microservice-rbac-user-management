package com.giassi.microservice.demo2.rest.users.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Create or modify user data
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrUpdateUserDTO implements Serializable {

    private String username;
    private String password;

    private String name;
    private String surname;
    private String gender;

    private boolean enabled;

    private long roleId;

    private String note;

    // contact information
    private String email;
    private String phone;

    // address information
    private String address;
    private String address2;
    private String city;
    private String country;
    private String zipCode;

}
