package com.giassi.microservice.demo2.rest.users.dtos;

import lombok.Data;

import java.util.ArrayList;

/**
 * DTO for the List of users
 */
@Data
public class UserListDTO implements java.io.Serializable {

    private ArrayList<UserDTO> userList;

    public UserListDTO() {
        userList = new ArrayList<>();
    }

}
