package com.giassi.microservice.demo2.rest;

import com.giassi.microservice.demo2.rest.user.RestUserDTO;
import com.giassi.microservice.demo2.rest.user.dtos.UserDTO;
import com.giassi.microservice.demo2.rest.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUserPresentationList() {
        return ResponseEntity.ok(userService.getUserPresentationList());
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody RestUserDTO createUserDTO) {
        return ResponseEntity.ok(new UserDTO(userService.createUser(createUserDTO)));
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable("id") Long id) {
        return new UserDTO(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long id, @RequestBody RestUserDTO updateUserDTO) {
        return ResponseEntity.ok(new UserDTO(userService.updateUser(id, updateUserDTO)));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
    }

}
