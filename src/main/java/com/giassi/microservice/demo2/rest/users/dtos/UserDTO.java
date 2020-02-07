package com.giassi.microservice.demo2.rest.users.dtos;

import com.giassi.microservice.demo2.rest.users.entities.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {

    public UserDTO(User user) {
        if (user !=null) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.name = user.getName();
            this.surname = user.getSurname();
            this.email = user.getEmail();
            this.enabled = user.isEnabled();
            this.gender = user.getGender().name();
            this.phone = user.getPhone();
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
    private String email;
    private boolean enabled;
    private String gender;
    private String phone;
    private LocalDateTime creationDt;
    private LocalDateTime updatedDt;

    // role information
    private RoleDTO roleDTO;

}
