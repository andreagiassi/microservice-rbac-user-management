package com.giassi.microservice.demo2.rest.users.dtos;

import com.giassi.microservice.demo2.rest.users.entities.Permission;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PermissionDTOTest {

    @Test
    public void testPermissionDTOConstructor() {
        Permission permission = new Permission(1L, "Browse website");

        PermissionDTO permissionDTO = new PermissionDTO(permission);

        assertEquals(permission.getId(), permissionDTO.getId());
        assertEquals(permission.getPermission(), permissionDTO.getPermission());
    }

}
