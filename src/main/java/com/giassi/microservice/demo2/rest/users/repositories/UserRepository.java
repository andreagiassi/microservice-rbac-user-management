package com.giassi.microservice.demo2.rest.users.repositories;

import com.giassi.microservice.demo2.rest.users.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);

    User findByUsername(String username);

}
