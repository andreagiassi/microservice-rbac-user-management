package com.giassi.microservice.demo2.rest.users.dtos;

import com.giassi.microservice.demo2.rest.users.entities.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {

    public UserDTO(User user) {
        if (user != null) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.name = user.getName();
            this.surname = user.getSurname();
            this.gender = user.getGender().name();

            this.email = user.getContact().getEmail();
            this.phone = user.getContact().getPhone();

            this.enabled = user.isEnabled();
            this.creationDt = user.getCreationDt();
            this.updatedDt = user.getUpdatedDt();

            // role, if set
            if (user.getRole() != null) {
                roleDTO = new RoleDTO(user.getRole());
            }
        }
    }

    private Long id;
    private String username;
    private String name;
    private String surname;
    private String gender;

    private String email;
    private String phone;

    private boolean enabled;
    private LocalDateTime creationDt;
    private LocalDateTime updatedDt;

    // role information
    private RoleDTO roleDTO;

}
