package com.giassi.microservice.demo2.rest;

import com.giassi.microservice.demo2.rest.users.dtos.UserDTO;
import com.giassi.microservice.demo2.rest.users.dtos.UserListDTO;
import com.giassi.microservice.demo2.rest.users.dtos.requests.CreateOrUpdateUserDTO;
import com.giassi.microservice.demo2.rest.users.dtos.requests.CreateUserAccountDTO;
import com.giassi.microservice.demo2.rest.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<UserListDTO> getUserPresentationList() {
        List<UserDTO> list = userService.getUserPresentationList();
        UserListDTO userListDTO = new UserListDTO();
        list.stream().forEach(e -> userListDTO.getUserList().add(e));
        return ResponseEntity.ok(userListDTO);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateOrUpdateUserDTO createOrUpdateUserDTO) {
        return new ResponseEntity(new UserDTO(userService.createUser(createOrUpdateUserDTO)), null, HttpStatus.CREATED);
    }

    // quick account creation: no all the user information are required
    @PostMapping("/quickAccount")
    public ResponseEntity<UserDTO> createNewUserAccount(@RequestBody CreateUserAccountDTO createUserAccountDTO) {
        return new ResponseEntity(new UserDTO(userService.createNewUserAccount(createUserAccountDTO)), null, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable("id") Long id) {
        return new UserDTO(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long id, @RequestBody CreateOrUpdateUserDTO updateUserDTO) {
        return new ResponseEntity(new UserDTO(userService.updateUser(id, updateUserDTO)), null, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
    }

}
