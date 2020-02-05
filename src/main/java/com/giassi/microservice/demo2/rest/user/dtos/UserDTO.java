package com.giassi.microservice.demo2.rest.user.dtos;

import com.giassi.microservice.demo2.rest.user.entities.User;
import lombok.Data;

@Data
public class UserDTO {

    public UserDTO(User user) {
        if (user !=null) {
            this.id = user.getId();
            this.name = user.getName();
            this.surname = user.getSurname();
            this.email = user.getEmail();
            this.gender = user.getGender().name();
        }
    }

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String gender;

}
