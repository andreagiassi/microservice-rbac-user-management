package com.giassi.microservice.demo2.rest.users.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modify the User data DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrUpdateUserDTO {

    private String username;
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
    private String city;
    private String country;
    private String zipCode;

}
