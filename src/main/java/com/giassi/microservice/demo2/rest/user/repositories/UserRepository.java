package com.giassi.microservice.demo2.rest.user.repositories;

import com.giassi.microservice.demo2.rest.user.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);

}
