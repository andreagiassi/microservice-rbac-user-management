package com.giassi.microservice.demo2.rest.users.dtos;

import com.giassi.microservice.demo2.rest.users.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleDTO implements Serializable {

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.role = role.getRole();
    }

    private Long id;
    private String role;

}
