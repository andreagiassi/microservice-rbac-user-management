package com.giassi.microservice.demo2.rest;

import com.giassi.microservice.demo2.rest.user.User;
import com.giassi.microservice.demo2.rest.user.UserDTO;
import com.giassi.microservice.demo2.rest.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/{id}")
    public UserDTO getUserById(@PathVariable("id") Long id) {
        return new UserDTO(userService.getUserById(id));
    }

    @GetMapping("/users")
    public List<UserDTO> getUserList() {
        ArrayList<UserDTO> listDto = new ArrayList<>();
        Iterable<User> list = userService.getUserList();
        list.forEach(e -> listDto.add(new UserDTO(e)));
        return listDto;
    }

}
