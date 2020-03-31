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

    public UserService() {
        passwordValidator = new PasswordValidator();
    }

    private PasswordValidator passwordValidator;

    public List<UserDTO> getUserPresentationList() {
        ArrayList<UserDTO> listDto = new ArrayList<>();
        Iterable<User> list = getUserList();
        list.forEach(e -> listDto.add(new UserDTO(e)));
        return listDto;
    }

    public User getUserById(Long id) {
        if (id == null) {
            throw new InvalidUserIdentifierException("Id cannot be null");
        }
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            return userOpt.get();
        }
        throw new UserNotFoundException(String.format("User not found for Id = %s", id));
    }

    public User getUserByUsername(String username) {
        if (username == null) {
            throw new InvalidUsernameException("username cannot be null");
        }
        return userRepository.findByUsername(username);
    }

    public User getUserByEmail(String email) {
        if (email == null) {
            throw new InvalidEmailException("email cannot be null");
        }
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User createNewUserAccount(CreateUserAccountDTO createUserAccountDTO) {
        if (createUserAccountDTO == null) {
            throw new InvalidUserDataException("User account data cannot be null");
        }

        checkIfUsernameNotUsed(createUserAccountDTO.getUsername());
        passwordValidator.checkPassword(createUserAccountDTO.getPassword());
        checkIfEmailNotUsed(createUserAccountDTO.getEmail());

        // create the new user account: not all the user information required
        User user = new User();
        user.setUsername(createUserAccountDTO.getUsername());
        user.setPassword(createUserAccountDTO.getPassword());
        user.setName(createUserAccountDTO.getName());
        user.setSurname(createUserAccountDTO.getSurname());
        user.setEnabled(true);

        // set gender
        Gender gender = Gender.getValidGender(createUserAccountDTO.getGender());
        user.setGender(gender);

        setUserRole(user, Role.USER);

        user.setCreationDt(LocalDateTime.now());

        User userCreated = userRepository.save(user);

        // set contact
        Contact contact = new Contact();
        contact.setEmail(createUserAccountDTO.getEmail());

        addContactOnUser(userCreated, contact);

        // set an empty address
        addAddressOnUser(userCreated, new Address());

        userCreated = userRepository.save(userCreated);

        log.info(String.format("User %s has been created.", userCreated.getId()));
        return userCreated;
    }

    // check if the username has not been registered
    public void checkIfUsernameNotUsed(String username) {
        User userByUsername = getUserByUsername(username);
            if (userByUsername != null) {
                String msg = String.format("The username %s it's already in use from another user with ID = %s",
                        userByUsername.getUsername(), userByUsername.getId());
                log.error(msg);
            throw new InvalidUserDataException(msg);
        }
    }

    // check if the email has not been registered
    public void checkIfEmailNotUsed(String email) {
        User userByEmail = getUserByEmail(email);
        if (userByEmail != null) {
            String msg = String.format("The email %s it's already in use from another user with ID = %s",
                    userByEmail.getContact().getEmail(), userByEmail.getId());
            log.error(msg);
            throw new InvalidUserDataException(msg);
        }
    }

    @Transactional
    public User createUser(CreateOrUpdateUserDTO createUserDTO) {
        if (createUserDTO == null) {
            throw new InvalidUserDataException("User account data cannot be null");
        }

        checkIfUsernameNotUsed(createUserDTO.getUsername());
        checkIfEmailNotUsed(createUserDTO.getEmail());
        passwordValidator.checkPassword(createUserDTO.getPassword());

        // create the user
        User user = new User();
        user.setUsername(createUserDTO.getUsername());
        user.setPassword(createUserDTO.getPassword());

        user.setName(createUserDTO.getName());
        user.setSurname(createUserDTO.getSurname());

        // set gender
        Gender gender = Gender.getValidGender(createUserDTO.getGender());
        user.setGender(gender);

        user.setEnabled(true);
        user.setNote(createUserDTO.getNote());

        user.setCreationDt(LocalDateTime.now());

        // set the role
        Role role = roleRepository.findById(createUserDTO.getRoleId());
        user.setRole(role);

        User userCreated = userRepository.save(user);

        // set contact
        Contact contact = new Contact();
        contact.setEmail(createUserDTO.getEmail());
        contact.setPhone(createUserDTO.getPhone());

        addContactOnUser(userCreated, contact);

        // set address
        Address address = new Address();
        address.setAddress(createUserDTO.getAddress());
        address.setCity(createUserDTO.getCity());
        address.setCountry(createUserDTO.getCountry());
        address.setZipCode(createUserDTO.getZipCode());

        addAddressOnUser(userCreated, address);

        userCreated = userRepository.save(userCreated);

        log.info(String.format("User %s has been created.", userCreated.getId()));
        return userCreated;
    }

    public void addContactOnUser(User user, Contact contact) {
        contact.setUser(user);
        user.setContact(contact);

        log.debug(String.format("Contact information set on User %s .", user.getId()));
    }

    public void addAddressOnUser(User user, Address address) {
        address.setUser(user);
        user.setAddress(address);

        log.debug(String.format("Address information set on User %s .", user.getId()));
    }

    public void setUserRole(User user, long roleId) {
        Role role = roleRepository.findById(roleId);
        if (role == null) {
            throw new RoleNotFoundException("Role cannot be null");
        }
        user.setRole(role);
    }

    @Transactional
    public User updateUser(Long id, CreateOrUpdateUserDTO updateUserDTO) {
        if (id == null) {
            throw new InvalidUserIdentifierException("Id cannot be null");
        }
        if (updateUserDTO == null) {
            throw new InvalidUserDataException("User account data cannot be null");
        }

        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new UserNotFoundException(String.format("The user with Id = %s doesn't exists", id));
        }
        User user = userOpt.get();

        // check if the username has not been registered
        User userByUsername = getUserByUsername(updateUserDTO.getUsername());
        if (userByUsername != null) {
            // check if the user's id is different than the actual user
            if (!user.getId().equals(userByUsername.getId())) {
                String msg = String.format("The username %s it's already in use from another user with ID = %s",
                        updateUserDTO.getUsername(), userByUsername.getId());
                log.error(msg);
                throw new InvalidUserDataException(msg);
            }
        }

        passwordValidator.checkPassword(updateUserDTO.getPassword());

        // check if the new email has not been registered yet
        User userEmail = getUserByEmail(updateUserDTO.getEmail());
        if (userEmail != null) {
            // check if the user's email is different than the actual user
            if (!user.getId().equals(userEmail.getId())) {
                String msg = String.format("The email %s it's already in use from another user with ID = %s",
                        updateUserDTO.getEmail(), userEmail.getId());
                log.error(msg);
                throw new InvalidUserDataException(msg);
            }
        }

        // update the user
        user.setUsername(updateUserDTO.getUsername());
        user.setPassword(updateUserDTO.getPassword());
        user.setName(updateUserDTO.getName());
        user.setSurname(updateUserDTO.getSurname());

        // set gender
        Gender gender = Gender.getValidGender(updateUserDTO.getGender());
        user.setGender(gender);

        user.setEnabled(updateUserDTO.isEnabled());
        user.setNote(updateUserDTO.getNote());

        // set the role
        Role role = roleRepository.findById(updateUserDTO.getRoleId());
        user.setRole(role);

        // set contact
        user.getContact().setEmail(updateUserDTO.getEmail());
        user.getContact().setPhone(updateUserDTO.getPhone());

        user.setUpdatedDt(LocalDateTime.now());

        // set address
        Address address = user.getAddress();
        if (address == null) {
            address = new Address();
        }
        address.setAddress(updateUserDTO.getAddress());
        address.setCity(updateUserDTO.getCity());
        address.setCountry(updateUserDTO.getCountry());
        address.setZipCode(updateUserDTO.getZipCode());

        addAddressOnUser(user, address);

        User userUpdated =  userRepository.save(user);
        log.info(String.format("User %s has been updated.", user.getId()));

        return userUpdated;
    }

    public Iterable<User> getUserList() {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteUserById(Long id) {
        if (id == null) {
            throw new InvalidUserIdentifierException("Id cannot be null");
        }

        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(String.format("User not found with Id = %s", id));
        }
        userRepository.deleteById(id);
        log.info(String.format("User %s has been deleted.", id));
    }

}
