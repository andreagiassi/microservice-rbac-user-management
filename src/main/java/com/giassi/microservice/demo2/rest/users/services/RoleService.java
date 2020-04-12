package com.giassi.microservice.demo2.rest.users.services;

import com.giassi.microservice.demo2.rest.users.entities.Permission;
import com.giassi.microservice.demo2.rest.users.entities.Role;
import com.giassi.microservice.demo2.rest.users.exceptions.*;
import com.giassi.microservice.demo2.rest.users.repositories.PermissionRepository;
import com.giassi.microservice.demo2.rest.users.repositories.RoleRepository;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    public Iterable<Role> getRoleList() {
        return roleRepository.findAll();
    }

    public Iterable<Permission> getPermissionList() {
        return permissionRepository.findAll();
    }

    public Role getRoleById(Long id) {
        if (id == null) {
            throw new InvalidRoleIdentifierException("Id role cannot be null");
        }
        Optional<Role> roleOpt = roleRepository.findById(id);
        if (roleOpt.isPresent()) {
            return roleOpt.get();
        }
        throw new RoleNotFoundException(String.format("Role not found for Id = %s", id));
    }

    public static void validateRoleName(String roleName) {
        if (Strings.isNullOrEmpty(roleName)) {
            throw new InvalidRoleDataException(String.format("Invalid role name: %s", roleName));
        }
    }

    @Transactional
    public Role createRole(String roleStr) {
        validateRoleName(roleStr);

        // check roleStr not in use
        if (roleRepository.findByRole(roleStr).isPresent()) {
            String errMsg = String.format("The role %s already exists", roleStr);
            log.error(errMsg);
            throw new RoleInUseException(errMsg);
        }

        Role role = new Role();
        role.setRole(roleStr);

        role = roleRepository.save(role);
        log.info(String.format("Role %s %s has been created.", role.getId(), role.getRole()));

        return role;
    }

    @Transactional
    public void deleteRole(Long id) {
        Optional<Role> roleOpt = roleRepository.findById(id);
        if (!roleOpt.isPresent()) {
            String errMsg = String.format("Role not found for Id = %s cannot be deleted", id);
            log.error(errMsg);
            throw new RoleNotFoundException(errMsg);
        }

        Role role = roleOpt.get();

        // check if the role is in use
        Long countUsages = roleRepository.countRoleUsage(id);
        if (countUsages > 0) {
            String errMsg = String.format("The role %s %s is in use (%s users_roles configuration rows)" +
                            " and cannot be deleted", role.getId(), role.getRole(), countUsages);
            log.error(errMsg);
            throw new RoleInUseException(errMsg);
        }

        roleRepository.deleteById(id);
        log.info(String.format("Role %s has been deleted.", id));
    }

    // add or remove a permission on a role

    public static void validatePermissionKey(String permissionKey) {
        if (Strings.isNullOrEmpty(permissionKey)) {
            throw new InvalidPermissionDataException("Permission key cannot be null or empty");
        }
    }

    @Transactional
    public Role addPermissionOnRole(Long roleId, String permissionKey) {
        validatePermissionKey(permissionKey);

        // check role
        Optional<Role> roleOpt = roleRepository.findById(roleId);
        if (!roleOpt.isPresent()) {
            throw new RoleNotFoundException(String.format("Role not found with Id = %s", roleId));
        }
        Role role = roleOpt.get();

        // check if exists the permission key
        Permission permission;

        Optional<Permission> permissionOpt = permissionRepository.findByPermission(permissionKey);
        if (permissionOpt.isPresent()) {
            // the permission exists
            permission = permissionOpt.get();
        } else {
            // if the permission doesn't exists: create one
            permission = new Permission();
            permission.setPermission(permissionKey);

            permission = permissionRepository.save(permission);
        }

        // check if this role contains already the given permission
        if (role.getPermissions().contains(permission)) {
            throw new InvalidPermissionDataException(String.format("The permission %s has been already" +
                            " associated on the role %s", permission.getPermission(), role.getRole() ));
        }

        role.getPermissions().add(permission);
        roleRepository.save(role);

        log.info(String.format("Added permission %s on role id = %s", permissionKey, roleId));
        return roleRepository.findById(roleId).get();
    }

    @Transactional
    public Role removePermissionOnRole(Long roleId, String permissionKey) {
        validatePermissionKey(permissionKey);

        // check role
        Optional<Role> roleOpt = roleRepository.findById(roleId);
        if (!roleOpt.isPresent()) {
            throw new RoleNotFoundException(String.format("Role not found with Id = %s", roleId));
        }
        Role role = roleOpt.get();

        // check permission
        Optional<Permission> permissionOpt = permissionRepository.findByPermission(permissionKey);
        if (!permissionOpt.isPresent()) {
            throw new PermissionNotFoundException(String.format("Permission not found with Id = %s on role %s",
                    permissionKey, roleId));
        }

        Permission permission = permissionOpt.get();

        role.getPermissions().remove(permission);
        roleRepository.save(role);

        // check if the permission is in use
        Long countUsages = permissionRepository.countPermissionUsage(permission.getId());
        if (countUsages == 0) {
            permissionRepository.deleteByPermission(permissionKey);
            log.info(String.format("Removed not used permission key %s", permissionKey));
        }

        log.info(String.format("Removed permission %s from role id = %s", permissionKey, roleId));
        return roleRepository.findById(roleId).get();
    }

}
