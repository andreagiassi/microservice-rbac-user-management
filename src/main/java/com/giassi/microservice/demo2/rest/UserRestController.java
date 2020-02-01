package com.giassi.microservice.demo2.rest;

import com.giassi.microservice.demo2.rest.user.entities.User;
import com.giassi.microservice.demo2.rest.user.dtos.UserDTO;
import com.giassi.microservice.demo2.rest.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserRestController {

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
