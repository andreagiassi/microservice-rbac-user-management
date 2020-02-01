package com.giassi.microservice.demo2.rest;

import com.giassi.microservice.demo2.rest.user.User;
import com.giassi.microservice.demo2.rest.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/users")
    public Iterable<User> getUserList() {
        return userService.getUserList();
    }

}
