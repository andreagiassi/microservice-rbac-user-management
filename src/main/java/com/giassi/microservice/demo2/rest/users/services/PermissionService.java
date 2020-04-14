package com.giassi.microservice.demo2.rest.users.services;

import com.giassi.microservice.demo2.rest.users.dtos.PermissionDTO;
import com.giassi.microservice.demo2.rest.users.entities.Permission;
import com.giassi.microservice.demo2.rest.users.exceptions.InvalidPermissionDataException;
import com.giassi.microservice.demo2.rest.users.exceptions.PermissionInUseException;
import com.giassi.microservice.demo2.rest.users.exceptions.PermissionNotFoundException;
import com.giassi.microservice.demo2.rest.users.repositories.PermissionRepository;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    public Iterable<Permission> getPermissionList() {
        return permissionRepository.findAll();
    }

    public Permission getPermissionByKey(String key) {
        if (Strings.isNullOrEmpty(key)) {
            throw new InvalidPermissionDataException("Permission key cannot be null or empty");
        }
        Optional<Permission> userOpt = permissionRepository.findByPermission(key);
        if (userOpt.isPresent()) {
            return userOpt.get();
        }
        throw new PermissionNotFoundException(String.format("Permission not found for permission key = %s", key));
    }

    @Transactional
    public Permission createPermission(PermissionDTO permissionDTO) {
        if (permissionDTO == null) {
            throw new InvalidPermissionDataException("Permission data cannot be null or empty");
        }

        String permissionKey = permissionDTO.getPermission();

        if (Strings.isNullOrEmpty(permissionKey)) {
            throw new InvalidPermissionDataException("Permission key cannot be null or empty");
        }

        // check permission
        Optional<Permission> permissionOpt = permissionRepository.findByPermission(permissionKey);
        if (permissionOpt.isPresent()) {
            throw new PermissionNotFoundException(String.format("Permission %s already existing with the same key with Id = %s",
                    permissionKey, permissionOpt.get().getId()));
        }

        Permission permission = new Permission();
        permission.setPermission(permissionKey);

        permission.setNote(permissionDTO.getNote());
        permission.setEnabled(permissionDTO.isEnabled());

        Permission createdPermission = permissionRepository.save(permission);

        log.info(String.format("Created permission %s with Id = %s", permissionKey, createdPermission.getId()));
        return createdPermission;
    }

    @Transactional
    public Permission updatePermission(PermissionDTO permissionDTO) {
        if (permissionDTO == null) {
            throw new InvalidPermissionDataException("Permission data cannot be null");
        }

        Long permissionId = permissionDTO.getId();
        Optional<Permission> permissionOpt = permissionRepository.findById(permissionId);
        if (!permissionOpt.isPresent()) {
            throw new PermissionNotFoundException(String.format("The permission with the id = %s has not been found",
                    permissionId));
        }

        Permission permission = permissionOpt.get();
        String permissionKey = permissionDTO.getPermission();

        // check if exists a different configuration with the same permissionKey
        Optional<Permission> permissionByKeyOpt = permissionRepository.findByPermission(permissionKey);
        if (permissionByKeyOpt.isPresent()) {
            Permission permission1 = permissionByKeyOpt.get();
            if (!permission1.getId().equals(permission.getId())) {
                throw new InvalidPermissionDataException(String.format("Exists already a permission with the key %s." +
                        " Use another key", permissionKey));
            }
        }

        // update permission
        permission.setPermission(permissionDTO.getPermission());
        permission.setEnabled(permissionDTO.isEnabled());
        permission.setNote(permissionDTO.getNote());

        Permission updatedPermission = permissionRepository.save(permission);
        log.info(String.format("Permission with id = %s has been updated.", permission.getId()));

        return updatedPermission;
    }

    @Transactional
    public void deletePermissionByKey(String permissionKey) {
        if (Strings.isNullOrEmpty(permissionKey)) {
            throw new InvalidPermissionDataException("Permission key cannot be null or empty");
        }

        // check permission
        Optional<Permission> permissionOpt = permissionRepository.findByPermission(permissionKey);
        if (!permissionOpt.isPresent()) {
            throw new PermissionNotFoundException(String.format("Permission %s not found", permissionKey));
        }

        Permission permission = permissionOpt.get();

        // check usage
        Long rowsFound = permissionRepository.countPermissionUsage(permission.getId());
        if (rowsFound > 0) {
            // permission cannot be deleted
            throw new PermissionInUseException(String.format("The permission with key %s is in used (%s configuration rows)",
                    permissionKey, rowsFound));
        }

        permissionRepository.delete(permission);

        log.info(String.format("Deleted permission with key %s", permission.getPermission()));
    }

}
