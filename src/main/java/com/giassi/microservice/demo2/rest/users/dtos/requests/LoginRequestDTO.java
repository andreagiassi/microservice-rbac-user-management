package com.giassi.microservice.demo2.rest.users.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Support the creation of a new user account with a minimum set of required data
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO implements Serializable {

    private String username;
    private String password;

}
