package com.giassi.microservice.demo2.rest.users.dtos;

import com.giassi.microservice.demo2.rest.users.entities.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class RoleDTO implements Serializable {

    private Long id;
    private String role;

    // permissions
    private List<PermissionDTO> permissions = new ArrayList<>();

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.role = role.getRole();

        // permissions
        role.getPermissions().stream().forEach(e -> permissions.add(new PermissionDTO(e)));
    }

    public RoleDTO(Long id, String role) {
        this.id = id;
        this.role = role;
    }

}
