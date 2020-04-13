# Changelog

### Added

Permission
- Enabled or disabled a permission: allows to expose or to hidden dynamically a software feature
- Added descriptive permission text note
- New create permission endpoint on POST /users/rbac/permission
- Update permission endpoint on PUT /users/rbac/permission
- Retrieve single permission endpoint by key on GET /users/rbac/permission{permissionKey}
- Delete single permission endpoint by key on DELETE /users/rbac/permission{permissionKey}

Introduced PermissionService component.
Removed deletion of not used permission on RoleService.

## v1.0 (13/04/2020)

#### Enhancements:

- Implemented all the necessary basic RBAC features

#### Bug Fixes:

---