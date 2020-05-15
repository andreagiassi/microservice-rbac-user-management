# Changelog

#### 15/05/2020

- Phone validation ITU-T E.123 compliant (international numbers)

#### 15/04/2020

- Using phone instead mobile - fix

#### 14/04/2020

- Exposed salt generator endpoint
- New email validation support
- Documentation improvements

#### 13/04/2020

Permissions:
- Enabled or disabled a permission: allows to expose or to hidden dynamically a software feature
- Added descriptive permission text note
- New create permission endpoint on POST /users/rbac/permission
- Update permission endpoint on PUT /users/rbac/permission
- Retrieve single permission endpoint by key on GET /users/rbac/permission{permissionKey}
- Delete single permission endpoint by key on DELETE /users/rbac/permission{permissionKey}

Removed the automatic deletion of not used permission on RoleService.

## v1.0 (13/04/2020)

#### Enhancements:

- Implemented all the necessary basic RBAC features

---