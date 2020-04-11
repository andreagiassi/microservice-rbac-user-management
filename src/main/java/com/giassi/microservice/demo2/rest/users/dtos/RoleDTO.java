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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleDTO)) return false;
        return id != null && id.equals(((RoleDTO) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
