package com.giassi.microservice.demo2.rest.user.services;

import com.giassi.microservice.demo2.rest.user.RestUserDTO;
import com.giassi.microservice.demo2.rest.user.dtos.UserDTO;
import com.giassi.microservice.demo2.rest.user.entities.User;
import com.giassi.microservice.demo2.rest.user.exceptions.InvalidUserDataException;
import com.giassi.microservice.demo2.rest.user.exceptions.InvalidUserIdentifierException;
import com.giassi.microservice.demo2.rest.user.exceptions.UserNotFoundException;
import com.giassi.microservice.demo2.rest.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ArrayList<UserDTO> getUserPresentationList() {
        ArrayList<UserDTO> listDto = new ArrayList<>();
        Iterable<User> list = getUserList();
        list.forEach(e -> listDto.add(new UserDTO(e)));
        return listDto;
    }

    public User getUserById(Long id) {
        if (id == null) {
            throw new InvalidUserIdentifierException();
        }
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            return userOpt.get();
        }
        throw new InvalidUserIdentifierException();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User createUser(RestUserDTO createUserDTO) {
        if (createUserDTO == null) {
            throw new InvalidUserDataException();
        }

        // check if the email has not been registered
        User userEmail = getUserByEmail(createUserDTO.getEmail());
        if (userEmail != null) {
            throw new InvalidUserDataException();
        }

        User user = new User();
        user.setName(createUserDTO.getName());
        user.setSurname(createUserDTO.getSurname());
        user.setEmail(createUserDTO.getEmail());

        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long id, RestUserDTO updateUserDTO) {
        if (id == null) {
            throw new InvalidUserIdentifierException();
        }
        if (updateUserDTO == null) {
            throw new InvalidUserDataException();
        }

        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new UserNotFoundException();
        }
        User user = userOpt.get();

        // check if the new email has not been registered yet
        User userEmail = getUserByEmail(updateUserDTO.getEmail());
        if (userEmail != null) {
            throw new InvalidUserDataException();
        }

        // update the user data
        user.setName(updateUserDTO.getName());
        user.setSurname(updateUserDTO.getSurname());
        user.setEmail(updateUserDTO.getEmail());

        return userRepository.save(user);
    }

    public Iterable<User> getUserList() {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteUserById(Long id) {
        if (id == null) {
            throw new InvalidUserIdentifierException();
        }

        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException();
        }
    }

}
