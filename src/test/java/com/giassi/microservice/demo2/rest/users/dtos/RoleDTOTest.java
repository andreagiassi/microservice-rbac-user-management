package com.giassi.microservice.demo2.rest.users.dtos;

import com.giassi.microservice.demo2.rest.users.entities.Role;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RoleDTOTest {

    @Test
    public void testRoleDTOConstructor() {
        Role role = new Role(1L, "USER");

        RoleDTO roleDTO = new RoleDTO(role);

        assertEquals(role.getId(), roleDTO.getId());
        assertEquals(role.getRole(), roleDTO.getRole());
    }

}
