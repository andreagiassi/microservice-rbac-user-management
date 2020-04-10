package com.giassi.microservice.demo2.rest.users.services;

import com.giassi.microservice.demo2.rest.users.dtos.UserDTO;
import com.giassi.microservice.demo2.rest.users.dtos.requests.CreateOrUpdateUserDTO;
import com.giassi.microservice.demo2.rest.users.dtos.requests.RegisterUserAccountDTO;
import com.giassi.microservice.demo2.rest.users.entities.*;
import com.giassi.microservice.demo2.rest.users.exceptions.*;
import com.giassi.microservice.demo2.rest.users.repositories.AddressRepository;
import com.giassi.microservice.demo2.rest.users.repositories.ContactRepository;
import com.giassi.microservice.demo2.rest.users.repositories.RoleRepository;
import com.giassi.microservice.demo2.rest.users.repositories.UserRepository;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${microservice.security.salt}")
    private String salt;

    private PasswordValidator passwordValidator;

    public UserService() {
        passwordValidator = new PasswordValidator();
    }

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
    public User registerUserAccount(RegisterUserAccountDTO registerUserAccountDTO) {
        if (registerUserAccountDTO == null) {
            throw new InvalidUserDataException("User account data cannot be null");
        }

        checkIfUsernameNotUsed(registerUserAccountDTO.getUsername());
        passwordValidator.checkPassword(registerUserAccountDTO.getPassword());
        checkIfEmailNotUsed(registerUserAccountDTO.getEmail());

        // create the new user account: not all the user information required
        User user = new User();
        user.setUsername(registerUserAccountDTO.getUsername());
        user.setPassword(EncryptionService.encrypt(registerUserAccountDTO.getPassword(), salt));

        user.setName(registerUserAccountDTO.getName());
        user.setSurname(registerUserAccountDTO.getSurname());
        user.setEnabled(true);
        user.setSecured(false);

        // set gender
        Gender gender = Gender.getValidGender(registerUserAccountDTO.getGender());
        user.setGender(gender);

        addUserRole(user, Role.USER);
        user.setCreationDt(LocalDateTime.now());

        User userCreated = userRepository.save(user);

        // set contact
        Contact contact = new Contact();
        contact.setEmail(registerUserAccountDTO.getEmail());

        addContactOnUser(userCreated, contact);

        // set empty address
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
            throw new InvalidUserDataException(String.format("This email %s it's already in use.",
                    userByEmail.getContact().getEmail()));
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
        user.setPassword(EncryptionService.encrypt(createUserDTO.getPassword(), salt));

        user.setName(createUserDTO.getName());
        user.setSurname(createUserDTO.getSurname());

        // set gender
        Gender gender = Gender.getValidGender(createUserDTO.getGender());
        user.setGender(gender);

        // date of birth
        user.setBirthDate(createUserDTO.getBirthDate());

        user.setEnabled(true);
        user.setSecured(createUserDTO.isSecured());

        user.setNote(createUserDTO.getNote());
        user.setCreationDt(LocalDateTime.now());

        // set default user the role
        addUserRole(user, Role.USER);

        User userCreated = userRepository.save(user);

        // set contact
        Contact contact = new Contact();
        contact.setEmail(createUserDTO.getEmail());
        contact.setPhone(createUserDTO.getMobile());
        contact.setSkype(createUserDTO.getSkype());
        contact.setFacebook(createUserDTO.getFacebook());
        contact.setLinkedin(createUserDTO.getLinkedin());
        contact.setWebsite(createUserDTO.getWebsite());
        contact.setNote(createUserDTO.getContactNote());

        addContactOnUser(userCreated, contact);

        // set address
        Address address = new Address();
        address.setAddress(createUserDTO.getAddress());
        address.setAddress2(createUserDTO.getAddress2());
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

    public void addUserRole(User user, long roleId) {
        Optional<Role> roleOpt = roleRepository.findById(roleId);
        if (!roleOpt.isPresent()) {
            throw new RoleNotFoundException("Role cannot be null");
        }
        user.getRoles().add(roleOpt.get());
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

        // using the user's salt to secure the new validated password
        user.setPassword(EncryptionService.encrypt(updateUserDTO.getPassword(), salt));
        user.setName(updateUserDTO.getName());
        user.setSurname(updateUserDTO.getSurname());

        // set gender
        Gender gender = Gender.getValidGender(updateUserDTO.getGender());
        user.setGender(gender);

        // date of birth
        user.setBirthDate(updateUserDTO.getBirthDate());

        user.setEnabled(updateUserDTO.isEnabled());
        user.setNote(updateUserDTO.getNote());

        // set contact, entity always present
        Contact contact = user.getContact();
        contact.setEmail(updateUserDTO.getEmail());
        contact.setPhone(updateUserDTO.getMobile());
        contact.setSkype(updateUserDTO.getSkype());
        contact.setFacebook(updateUserDTO.getFacebook());
        contact.setLinkedin(updateUserDTO.getLinkedin());
        contact.setWebsite(updateUserDTO.getWebsite());
        contact.setNote(updateUserDTO.getContactNote());

        user.setUpdatedDt(LocalDateTime.now());

        // set address
        Address address = user.getAddress();
        if (address == null) {
            address = new Address();
        }
        address.setAddress(updateUserDTO.getAddress());
        address.setAddress2(updateUserDTO.getAddress2());
        address.setCity(updateUserDTO.getCity());
        address.setCountry(updateUserDTO.getCountry());
        address.setZipCode(updateUserDTO.getZipCode());

        addAddressOnUser(user, address);

        User userUpdated = userRepository.save(user);
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

        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new UserNotFoundException(String.format("User not found with Id = %s", id));
        }

        // only not secured users can be deleted
        User user = userOpt.get();
        if (user.isSecured()) {
            throw new UserIsSecuredException(String.format("User %s is secured and cannot be deleted.", id));
        }

        userRepository.deleteById(id);
        log.info(String.format("User %s has been deleted.", id));
    }

    @Transactional
    public User login(String username, String password) {
        if ((Strings.isNullOrEmpty(username)) || (Strings.isNullOrEmpty(password))) {
            throw new InvalidLoginException("Username or Password cannot be null or empty");
        }

        User user = getUserByUsername(username);
        if (user == null) {
            // invalid username
            throw new InvalidLoginException("Invalid username or password");
        }

        log.info(String.format("Login request from %s", username));

        // check the password
        if (EncryptionService.isPasswordValid(password, user.getPassword(), salt)) {
            // check if the user is enabled
            if (!user.isEnabled()) {
                // not enabled
                throw new InvalidLoginException("User is not enabled");
            }

            // update the last login timestamp
            user.setLoginDt(LocalDateTime.now());
            userRepository.save(user);

            log.info(String.format("Valid login for %s", username));
        } else {
            throw new InvalidLoginException("Invalid username or password");
        }
        return user;
    }

    // add or remove a role on user

    @Transactional
    public User addRole(Long id, Long roleId) {
        // check user
        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new UserNotFoundException(String.format("User not found with Id = %s", id));
        }
        User user = userOpt.get();

        // check role
        Optional<Role> roleOpt = roleRepository.findById(roleId);
        if (!roleOpt.isPresent()) {
            throw new RoleNotFoundException(String.format("Role not found with Id = %s", roleId));
        }

        Role role = roleOpt.get();

        user.getRoles().add(role);
        user.setUpdatedDt(LocalDateTime.now());

        userRepository.save(user);
        log.info(String.format("Added role %s on user id = %s", role.getRole(), user.getId()));

        return user;
    }

    @Transactional
    public User removeRole(Long id, Long roleId) {
        // check user
        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new UserNotFoundException(String.format("User not found with Id = %s", id));
        }
        User user = userOpt.get();

        // check role
        Optional<Role> roleOpt = roleRepository.findById(roleId);
        if (!roleOpt.isPresent()) {
            throw new RoleNotFoundException(String.format("Role not found with Id = %s", roleId));
        }

        Role role = roleOpt.get();

        user.getRoles().remove(role);
        user.setUpdatedDt(LocalDateTime.now());

        userRepository.save(user);
        log.info(String.format("Removed role %s on user id = %s", role.getRole(), user.getId()));

        return user;
    }

}
