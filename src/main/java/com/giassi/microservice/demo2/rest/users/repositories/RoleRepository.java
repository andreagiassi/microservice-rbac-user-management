package com.giassi.microservice.demo2.rest.users.repositories;

import com.giassi.microservice.demo2.rest.users.entities.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByRole(String role);

    @Query(value = "select count(*) from users_roles where role_id = ?1", nativeQuery = true)
    Long countRoleUsage(Long roleId);

}
