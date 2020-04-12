package com.giassi.microservice.demo2.rest.users.services;

import com.giassi.microservice.demo2.rest.users.entities.Permission;
import com.giassi.microservice.demo2.rest.users.entities.Role;
import com.giassi.microservice.demo2.rest.users.exceptions.*;
import com.giassi.microservice.demo2.rest.users.repositories.PermissionRepository;
import com.giassi.microservice.demo2.rest.users.repositories.RoleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PermissionRepository permissionRepository;

    @Autowired
    @InjectMocks
    private RoleService roleService = new RoleService();

    @Test(expected = InvalidRoleIdentifierException.class)
    public void given_wrong_roleId_when_getRoleById_throw_InvalidRoleIdentifierException() {
        roleService.getRoleById(null);
    }

    @Test(expected = RoleNotFoundException.class)
    public void given_not_existing_roleId_when_getRoleById_throw_RoleNotFoundException() {
        Long roleId = 99L;
        roleService.getRoleById(roleId);
    }

    @Test
    public void given_existing_roleId_when_getRoleById_return_Role() {
        Long roleId = 99L;
        Role role = new Role(roleId, "TEST ROLE");

        given(roleRepository.findById(roleId)).willReturn(Optional.of(role));

        Role returnRole = roleService.getRoleById(roleId);

        assertNotNull(returnRole);
        assertEquals(role.getId(), returnRole.getId());
    }

    // validateRoleName

    @Test(expected = InvalidRoleDataException.class)
    public void given_invalid_role_name_when_validateRoleName_throw_InvalidRoleDataException() {
        roleService.validateRoleName(null);
    }

    @Test(expected = InvalidRoleDataException.class)
    public void given_empty_role_name_when_validateRoleName_throw_InvalidRoleDataException() {
        roleService.validateRoleName("");
    }

    @Test
    public void given_empty_role_name_when_validateRoleName_no_exception_occurs() {
        roleService.validateRoleName("VALID_ROLE_TEST");
    }

    // createRole

    @Test(expected = InvalidRoleDataException.class)
    public void given_invalid_role_name_when_createRole_throw_InvalidRoleDataException() {
        roleService.createRole(null);
    }

    @Test(expected = RoleInUseException.class)
    public void given_valid_used_name_when_createRole_throw_RoleInUseException() {
        Role role = new Role(1L, "TEST");
        given(roleRepository.findByRole("TEST")).willReturn(Optional.of(role));

        roleService.createRole("TEST");
    }

    @Test
    public void given_valid_not_used_name_when_createRole_returnRole() {
        Long genId = 123L;
        Role roleData = new Role(genId, "TEST");

        when(roleRepository.save(any(Role.class))).thenReturn(new Role(genId, roleData.getRole()));

        Role role = roleService.createRole("TEST");

        assertNotNull(role);
        assertEquals(genId, role.getId());
        assertEquals("TEST", role.getRole());
    }

    // deleteRole

    @Test(expected = RoleNotFoundException.class)
    public void given_not_existing_role_when_deleteRole_throw_RoleNotFoundException() {
        roleService.deleteRole(1L);
    }

    @Test(expected = RoleInUseException.class)
    public void given_existing_role_in_use_when_deleteRole_throw_RoleInUseException() {
        given(roleRepository.findById(1L)).willReturn(Optional.of(new Role(1L, "TEST")));
        given(roleRepository.countRoleUsage(1L)).willReturn(10L);

        roleService.deleteRole(1L);
    }

    @Test
    public void given_existing_role_not_in_use_when_deleteRole_Ok() {
        given(roleRepository.findById(1L)).willReturn(Optional.of(new Role(1L, "TEST")));
        given(roleRepository.countRoleUsage(1L)).willReturn(0L);

        roleService.deleteRole(1L);
    }

    // validatePermissionKey

    @Test(expected = InvalidPermissionDataException.class)
    public void given_null_permissionKey_when_validatePermissionKey_throw_InvalidPermissionDataException() {
        roleService.validatePermissionKey(null);
    }

    @Test(expected = InvalidPermissionDataException.class)
    public void given_empty_permissionKey_when_validatePermissionKey_throw_InvalidPermissionDataException() {
        roleService.validatePermissionKey("");
    }

    // addPermissionOnRole

    @Test(expected = InvalidPermissionDataException.class)
    public void given_invalid_permission_when_addPermissionOnRole_throw_InvalidPermissionDataException() {
        roleService.addPermissionOnRole(1L, "");
    }

    @Test(expected = RoleNotFoundException.class)
    public void given_not_existing_role_when_addPermissionOnRole_throw_RoleNotFoundException() {
        roleService.addPermissionOnRole(1L, "PERMISSION_ONE");
    }

    @Test
    public void given_existing_role_and_not_existing_permission_return_role_updated() {
        Role role = new Role(1L, "TEST");
        given(roleRepository.findById(1L)).willReturn(Optional.of(role));

        Role roleUpdated = roleService.addPermissionOnRole(1L, "PERMISSION_ONE");

        assertNotNull(roleUpdated);
        // role data
        assertEquals(Long.valueOf(1L), roleUpdated.getId());
        assertEquals("TEST", roleUpdated.getRole());

        // permissions
        assertEquals(1L, roleUpdated.getPermissions().size());
    }

    @Test
    public void given_existing_role_and_existing_permission_return_role_updated() {
        Role role = new Role(1L, "TEST");
        given(roleRepository.findById(1L)).willReturn(Optional.of(role));

        Permission permission = new Permission();
        permission.setId(1L);
        permission.setPermission("PERMISSION_ONE");

        given(permissionRepository.findByPermission("PERMISSION_ONE")).willReturn(Optional.of(permission));

        Role roleUpdated = roleService.addPermissionOnRole(1L, "PERMISSION_ONE");

        assertNotNull(roleUpdated);
        // role data
        assertEquals(Long.valueOf(1L), roleUpdated.getId());
        assertEquals("TEST", roleUpdated.getRole());

        // permissions
        assertEquals(1L, roleUpdated.getPermissions().size());
    }

    @Test(expected = InvalidPermissionDataException.class)
    public void given_existing_role_and_existing_already_associated_permission_throw_InvalidPermissionDataException() {
        Role role = new Role(1L, "TEST");
        role.getPermissions().add(new Permission(1L, "PERMISSION_ONE"));

        given(roleRepository.findById(1L)).willReturn(Optional.of(role));

        Permission permission = new Permission();
        permission.setId(1L);
        permission.setPermission("PERMISSION_ONE");

        given(permissionRepository.findByPermission("PERMISSION_ONE")).willReturn(Optional.of(permission));

        Role roleUpdated = roleService.addPermissionOnRole(1L, "PERMISSION_ONE");

        assertNotNull(roleUpdated);
        // role data
        assertEquals(Long.valueOf(1L), roleUpdated.getId());
        assertEquals("TEST", roleUpdated.getRole());

        // permissions
        assertEquals(1L, roleUpdated.getPermissions().size());
    }

    // removePermissionOnRole

    @Test(expected = InvalidPermissionDataException.class)
    public void given_not_valid_permission_when_removePermissionOnRole_throw_InvalidPermissionDataException() {
        roleService.removePermissionOnRole(1L, "");
    }

    @Test(expected = RoleNotFoundException.class)
    public void given_not_existing_role_when_removePermissionOnRole_throw_RoleNotFoundException() {
        roleService.removePermissionOnRole(1L, "PERMISSION");
    }

    @Test(expected = PermissionNotFoundException.class)
    public void given_existing_role_not_existing_permission_when_removePermissionOnRole_throw_PermissionNotFoundException() {
        Role role = new Role(1L, "TEST");
        given(roleRepository.findById(1L)).willReturn(Optional.of(role));

        roleService.removePermissionOnRole(1L, "PERMISSION");
    }

    @Test
    public void given_existing_role_existing_permission_not_used_when_removePermissionOnRole_return_Role() {
        Role role = new Role(1L, "TEST");
        given(roleRepository.findById(1L)).willReturn(Optional.of(role));

        Permission permission = new Permission();
        permission.setId(1L);
        permission.setPermission("PERMISSION");

        given(permissionRepository.findByPermission("PERMISSION")).willReturn(Optional.of(permission));

        Role roleUpdated = roleService.removePermissionOnRole(1L, "PERMISSION");

        assertNotNull(roleUpdated);
        // role data
        assertEquals(Long.valueOf(1L), roleUpdated.getId());
        assertEquals("TEST", roleUpdated.getRole());

        // permissions
        assertEquals(0L, roleUpdated.getPermissions().size());
    }

    @Test
    public void given_existing_role_existing_permission_in_used_when_removePermissionOnRole_return_Role() {
        Role role = new Role(1L, "TEST");
        given(roleRepository.findById(1L)).willReturn(Optional.of(role));

        Permission permission = new Permission();
        permission.setId(1L);
        permission.setPermission("PERMISSION");

        given(permissionRepository.findByPermission("PERMISSION")).willReturn(Optional.of(permission));
        given(permissionRepository.countPermissionUsage(1L)).willReturn(10L);

        Role roleUpdated = roleService.removePermissionOnRole(1L, "PERMISSION");

        assertNotNull(roleUpdated);
        // role data
        assertEquals(Long.valueOf(1L), roleUpdated.getId());
        assertEquals("TEST", roleUpdated.getRole());

        // permissions
        assertEquals(0L, roleUpdated.getPermissions().size());
    }

    // getRoleList

    @Test
    public void calling_getRoleList_then_return_list_of_roles() {
        ArrayList<Role> roleArrayList = new ArrayList<>();
        roleArrayList.add(new Role(1L, "FIRST_ROLE"));
        roleArrayList.add(new Role(2L, "SECOND_ROLE"));

        given(roleRepository.findAll()).willReturn(roleArrayList);

        Iterable<Role> roleIterable = roleService.getRoleList();

        assertNotNull(roleIterable);

        // TODO: check on size and or data
    }

    // getPermissionList

    @Test
    public void calling_getPermissionList_then_return_list_of_roles() {
        ArrayList<Permission> permissionArrayList = new ArrayList<>();

        Permission permission1 = new Permission();
        Permission permission2 = new Permission();

        permissionArrayList.add(permission1);
        permissionArrayList.add(permission2);

        given(permissionRepository.findAll()).willReturn(permissionArrayList);

        Iterable<Permission> permissionList = roleService.getPermissionList();

        assertNotNull(permissionList);

        // TODO: check on size and or data
    }

}
