package com.giassi.microservice.demo2.rest.users.dtos;

import com.giassi.microservice.demo2.rest.users.entities.Role;
import lombok.Data;

@Data
public class RoleDTO {

    public RoleDTO() {
        // empty constructor
    }

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.role = role.getRole();
    }

    private Long id;
    private String role;

}
