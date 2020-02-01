package com.giassi.microservice.demo2.rest.user;

import lombok.Data;

@Data
public class UserDTO {

    public UserDTO(User user) {
        this.Id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
    }

    private Long Id;
    private String name;
    private String surname;

}
