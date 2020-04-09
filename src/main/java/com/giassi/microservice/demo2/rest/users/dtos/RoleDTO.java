package com.giassi.microservice.demo2.rest.users.dtos;

import com.giassi.microservice.demo2.rest.users.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleDTO implements Serializable {

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

    private Long id;
    private String role;

    // permissions
    private List<PermissionDTO> permissions = new ArrayList<>();

}
