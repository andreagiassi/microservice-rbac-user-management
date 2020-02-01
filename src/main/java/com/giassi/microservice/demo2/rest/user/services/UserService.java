package com.giassi.microservice.demo2.rest.user.services;

import com.giassi.microservice.demo2.rest.user.repositories.UserRepository;
import com.giassi.microservice.demo2.rest.user.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            return userOpt.get();
        }
        return null;
    }

    public Iterable<User> getUserList() {
        return userRepository.findAll();
    }

}
