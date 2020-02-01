package com.giassi.microservice.demo2.rest.user;

import lombok.Data;

@Data
public class UserDTO {

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
    }

    private Long id;
    private String name;
    private String surname;

}
