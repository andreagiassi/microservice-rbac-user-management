package com.giassi.microservice.demo2.rest.users.services;

import com.giassi.microservice.demo2.rest.users.dtos.CreateOrUpdateUserDTO;
import com.giassi.microservice.demo2.rest.users.dtos.CreateUserAccountDTO;
import com.giassi.microservice.demo2.rest.users.dtos.UserDTO;
import com.giassi.microservice.demo2.rest.users.entities.*;
import com.giassi.microservice.demo2.rest.users.exceptions.*;
import com.giassi.microservice.demo2.rest.users.repositories.AddressRepository;
import com.giassi.microservice.demo2.rest.users.repositories.ContactRepository;
import com.giassi.microservice.demo2.rest.users.repositories.RoleRepository;
import com.giassi.microservice.demo2.rest.users.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<UserDTO> getUserPresentationList() {
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
        throw new UserNotFoundException();
    }

    public User getUserByUsername(String username) {
        if (username == null) {
            throw new InvalidUsernameException();
        }
        return userRepository.findByUsername(username);
    }

    public User getUserByEmail(String email) {
        if (email == null) {
            throw new InvalidEmailException();
        }
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User createNewUserAccount(CreateUserAccountDTO createUserAccountDTO) {
        if (createUserAccountDTO == null) {
            throw new InvalidUserDataException();
        }

        // check if the username has not been registered
        User userByUsername = getUserByUsername(createUserAccountDTO.getUsername());
        if (userByUsername != null) {
            log.error(String.format("The username %s it's already in use from another user with ID = %s",
                    createUserAccountDTO.getUsername(), userByUsername.getId()));
            throw new InvalidUserDataException();
        }

        // check if the email has not been registered
        User userByEmail = getUserByEmail(createUserAccountDTO.getEmail());
        if (userByEmail != null) {
            log.error(String.format("The email %s it's already in use from another user with ID = %s",
                    createUserAccountDTO.getEmail(), userByEmail.getId()));
            throw new InvalidUserDataException();
        }

        // create the new user account: not all the user information required
        User user = new User();
        user.setUsername(createUserAccountDTO.getUsername());
        user.setName(createUserAccountDTO.getName());
        user.setSurname(createUserAccountDTO.getSurname());

        user.setEnabled(true);

        Gender gender = getValidGender(createUserAccountDTO.getGender());
        user.setGender(gender);

        setUserRole(user, Role.USER);

        user.setCreationDt(LocalDateTime.now());

        User userCreated = userRepository.save(user);

        // contact
        Contact contact = new Contact();
        contact.setEmail(createUserAccountDTO.getEmail());

        addContactOnUser(userCreated, contact);

        log.info(String.format("User %s has been created.", user.getId()));

        return userCreated;
    }

    @Transactional
    public User createUser(CreateOrUpdateUserDTO createUserDTO) {
        if (createUserDTO == null) {
            throw new InvalidUserDataException();
        }

        // check if the username has not been registered
        User userByUsername = getUserByUsername(createUserDTO.getUsername());
        if (userByUsername != null) {
            log.error(String.format("The username %s it's already in use from another user with ID = %s",
                    createUserDTO.getUsername(), userByUsername.getId()));
            throw new InvalidUserDataException();
        }

        // check if the email has not been registered
        User userByEmail = getUserByEmail(createUserDTO.getEmail());
        if (userByEmail != null) {
            log.error(String.format("The email %s it's already in use from another user with ID = %s",
                    createUserDTO.getEmail(), userByEmail.getId()));
            throw new InvalidUserDataException();
        }

        // create the new user
        User user = new User();
        user.setUsername(createUserDTO.getUsername());
        user.setName(createUserDTO.getName());
        user.setSurname(createUserDTO.getSurname());

        Gender gender = getValidGender(createUserDTO.getGender());
        user.setGender(gender);

        user.setEnabled(true);
        user.setNote(createUserDTO.getNote());

        user.setCreationDt(LocalDateTime.now());

        // set the role
        Role role = roleRepository.findById(createUserDTO.getRoleId());
        user.setRole(role);

        User userCreated = userRepository.save(user);

        // contact
        Contact contact = new Contact();
        contact.setEmail(createUserDTO.getEmail());
        contact.setPhone(createUserDTO.getPhone());

        addContactOnUser(userCreated, contact);

        log.info(String.format("User %s has been created.", user.getId()));

        return userCreated;
    }

    public User addContactOnUser(User user, Contact contact) {
        contact.setUser(user);
        user.setContact(contact);

        log.info(String.format("Contact information added on User %s .", user.getId()));

        return userRepository.save(user);
    }

    public User addAddressOnUser(User user, Address address) {
        address.setUser(user);
        user.setAddress(address);

        log.info(String.format("Address information added on User %s .", user.getId()));

        return userRepository.save(user);
    }

    public void setUserRole(User user, long roleId) {
        Role role = roleRepository.findById(roleId);
        if (role == null) {
            throw new RoleNotFoundException();
        }
        user.setRole(role);
    }

    @Transactional
    public User updateUser(Long id, CreateOrUpdateUserDTO updateUserDTO) {
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

        // check if the username has not been registered
        User userByUsername = getUserByUsername(updateUserDTO.getUsername());
        if (userByUsername != null) {
            // check if the user's id is different than the actual user
            if (!user.getId().equals(userByUsername.getId())) {
                log.error(String.format("The username %s it's already in use from another user with ID = %s",
                        updateUserDTO.getUsername(), userByUsername.getId()));
                throw new InvalidUserDataException();
            }
        }

        // check if the new email has not been registered yet
        User userEmail = getUserByEmail(updateUserDTO.getEmail());
        if (userEmail != null) {
            // check if the user's email is different than the actual user
            if (!user.getId().equals(userEmail.getId())) {
                log.error(String.format("The email %s it's already in use from another user with ID = %s",
                        updateUserDTO.getEmail(), userEmail.getId()));
                throw new InvalidUserDataException();
            }
        }

        // update the user
        user.setUsername(updateUserDTO.getUsername());
        user.setName(updateUserDTO.getName());
        user.setSurname(updateUserDTO.getSurname());

        Gender gender = getValidGender(updateUserDTO.getGender());
        user.setGender(gender);

        user.getContact().setEmail(updateUserDTO.getEmail());
        user.getContact().setPhone(updateUserDTO.getPhone());

        user.setEnabled(updateUserDTO.isEnabled());
        user.setNote(updateUserDTO.getNote());

        // set the role
        Role role = roleRepository.findById(updateUserDTO.getRoleId());
        user.setRole(role);

        user.setUpdatedDt(LocalDateTime.now());

        User userUpdated =  userRepository.save(user);
        log.info(String.format("User %s has been updated.", user.getId()));

        return userUpdated;
    }

    public Gender getValidGender(String genderName) {
        Gender gender;
        try {
            gender = Gender.valueOf(genderName);
        } catch(Exception ex) {
            throw new InvalidGenderException();
        }
        return gender;
    }

    public Iterable<User> getUserList() {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteUserById(Long id) {
        if (id == null) {
            throw new InvalidUserIdentifierException();
        }

        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);

        log.info(String.format("User %s has been deleted.", id));
    }

}
