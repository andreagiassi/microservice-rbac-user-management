package com.giassi.microservice.demo2.rest.users.services;

import com.giassi.microservice.demo2.rest.users.dtos.CreateOrUpdateUserDTO;
import com.giassi.microservice.demo2.rest.users.dtos.CreateUserAccountDTO;
import com.giassi.microservice.demo2.rest.users.dtos.UserDTO;
import com.giassi.microservice.demo2.rest.users.entities.Gender;
import com.giassi.microservice.demo2.rest.users.entities.Role;
import com.giassi.microservice.demo2.rest.users.entities.User;
import com.giassi.microservice.demo2.rest.users.exceptions.*;
import com.giassi.microservice.demo2.rest.users.repositories.RoleRepository;
import com.giassi.microservice.demo2.rest.users.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
public class UserServiceTest {

    @Autowired
    private TestEntityManager entityManager;

    // mocking UserRepository and RoleRepository
    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Autowired
    @InjectMocks
    private UserService userService;

    @Test
    public void given_existing_set_of_user_when_getUserPresentationList_return_list() {
        User user1 = getUserDataForTest(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");
        User user2= getUserDataForTest(2L, "marco", "Marco",
                "Rossi", "marco.test@gmail.com", "+3531122334466");
        User user3 = getUserDataForTest(3L, "francesco", "Francesco",
                "Verdi", "francesco.test@gmail.com", "+3531122334477");

        List<User> list =  new ArrayList<>();
        list.add(user1);
        list.add(user2);
        list.add(user3);

        given(userService.getUserList()).willReturn(list);

        List<UserDTO> userDTOList = userService.getUserPresentationList();

        assertNotNull(userDTOList);
        assertEquals(3, userDTOList.size());

        // take the second element to test the DTO content
        UserDTO userDTO = userDTOList.get(1);

        assertEquals(Long.valueOf(2L) , userDTO.getId());
        assertEquals("marco" , userDTO.getUsername());
        assertEquals("Marco" , userDTO.getName());
        assertEquals("Rossi" , userDTO.getSurname());
        assertEquals("marco.test@gmail.com" , userDTO.getEmail());
        assertEquals("+3531122334466" , userDTO.getPhone());
    }

    @Test
    public void given_existing_user_when_getUserById_returnUser() {
        Long userId = 1L;

        User user = getUserDataForTest(userId, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");

        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        User userRet = userService.getUserById(userId);

        assertEquals(userId, userRet.getId());
        assertEquals("andrea", user.getUsername());
        assertEquals("Andrea", user.getName());
        assertEquals("Giassi", user.getSurname());
        assertEquals("andrea.test@gmail.com", user.getEmail());
        assertEquals("+3531122334455", user.getPhone());
    }

    @Test(expected = InvalidUserIdentifierException.class)
    public void given_not_existing_user_when_getUserById_throw_exception() {
        Long userId = 2L;
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        userService.getUserById(userId);
    }

    @Test(expected = InvalidUserIdentifierException.class)
    public void given_null_user_id_when_getUserById_throw_exception() {
        userService.getUserById(null);
    }

    @Test(expected = InvalidUserDataException.class)
    public void given_null_username_when_getUserByUsername_return_user() {
        userService.getUserByUsername(null);
    }

    @Test
    public void given_valid_username_when_getUserByUsername_return_user() {
        User userDataForTest = getUserDataForTest(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");

        given(userRepository.findByUsername("andrea")).willReturn(userDataForTest);

        User user = userService.getUserByUsername("andrea");

        assertNotNull(user);
        assertEquals(Long.valueOf(1L), user.getId());
        assertEquals("andrea", user.getUsername());
        assertEquals("Andrea", user.getName());
        assertEquals("Giassi", user.getSurname());
        assertEquals("andrea.test@gmail.com", user.getEmail());
        assertEquals("+3531122334455", user.getPhone());
    }

    @Test
    public void given_valid_email_getUserByEmail_return_user() {
        User userDataForTest = getUserDataForTest(1L, "andrea", "Andrea",
            "Giassi", "andrea.test@gmail.com", "+3531122334455");

        given(userRepository.findByEmail("andrea.test@gmail.com")).willReturn(userDataForTest);

        User user = userService.getUserByEmail("andrea.test@gmail.com");

        assertNotNull(user);
        assertEquals(Long.valueOf(1L), user.getId());
        assertEquals("andrea", user.getUsername());
        assertEquals("Andrea", user.getName());
        assertEquals("Giassi", user.getSurname());
        assertEquals("andrea.test@gmail.com", user.getEmail());
        assertEquals("+3531122334455", user.getPhone());
    }

    @Test(expected = InvalidUserEmailException.class)
    public void given_invalid_email_getUserByEmail_throw_InvalidUserEmailException() {
        User user = userService.getUserByEmail(null);
    }

    @Test(expected = InvalidUserDataException.class)
    public void given_null_CreateUserAccountDTO_when_createNewUserAccount_throw_InvalidUserDataException() {
        userService.createNewUserAccount(null);
    }

    @Test(expected = InvalidUserDataException.class)
    public void given_already_existing_username_when_createNewUserAccount_throw_InvalidUserDataException() {
        User userDataForTest = getUserDataForTest(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");

        given(userRepository.findByUsername("andrea")).willReturn(userDataForTest);

        CreateUserAccountDTO createUserAccountDTO = new CreateUserAccountDTO();
        createUserAccountDTO.setName("Andrea");
        createUserAccountDTO.setSurname("Giassi");
        createUserAccountDTO.setEmail("andrea.test@gmail.com");
        createUserAccountDTO.setGender("MALE");
        createUserAccountDTO.setUsername("andrea");

        userService.createNewUserAccount(createUserAccountDTO);
    }

    @Test(expected = InvalidUserDataException.class)
    public void given_already_existing_email_when_createNewUserAccount_throw_InvalidUserDataException() {
        User userDataForTest = getUserDataForTest(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");

        given(userRepository.findByEmail("andrea.test@gmail.com")).willReturn(userDataForTest);

        CreateUserAccountDTO createUserAccountDTO = new CreateUserAccountDTO();
        createUserAccountDTO.setName("Marco");
        createUserAccountDTO.setSurname("Rossi");
        createUserAccountDTO.setEmail("andrea.test@gmail.com"); // existing email
        createUserAccountDTO.setGender("MALE");
        createUserAccountDTO.setUsername("marco");

        userService.createNewUserAccount(createUserAccountDTO);
    }

    @Test(expected = RoleNotFoundException.class)
    public void given_null_when_setUserRole_throw_RoleNotFoundException() {
        User userDataForTest = getUserDataForTest(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");

        // role doesn't exists
        userService.setUserRole(userDataForTest, 1);
    }

    @Test
    public void given_valid_role_id_when_setUserRole_throw_RoleNotFoundException() {
        User userDataForTest = getUserDataForTest(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");

        given(roleRepository.findById(1L)).willReturn(new Role(1L, "USER"));

        userService.setUserRole(userDataForTest, 1);
    }

    @Test(expected = InvalidUserDataException.class)
    public void given_invalid_CreateOrUpdateUserDTO_when_createUser_throw_InvalidUserDataException() {
        userService.createUser(null);
    }

    @Test(expected = InvalidUserDataException.class)
    public void given_already_registered_username_when_createUser_throw_InvalidUserDataException() {
        CreateOrUpdateUserDTO createOrUpdateUserDTO = new CreateOrUpdateUserDTO();
        createOrUpdateUserDTO.setUsername("andrea");

        User userDataForTest = getUserDataForTest(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");

        given(userRepository.findByUsername("andrea")).willReturn(userDataForTest);

        userService.createUser(createOrUpdateUserDTO);
    }

    @Test(expected = InvalidUserDataException.class)
    public void given_already_registered_email_when_createUser_throw_InvalidUserDataException() {
        CreateOrUpdateUserDTO createOrUpdateUserDTO = new CreateOrUpdateUserDTO();
        createOrUpdateUserDTO.setName("Marco");
        createOrUpdateUserDTO.setSurname("Rossi");
        createOrUpdateUserDTO.setEmail("andrea.test@gmail.com"); // existing email
        createOrUpdateUserDTO.setGender("MALE");
        createOrUpdateUserDTO.setUsername("marco");
        createOrUpdateUserDTO.setPhone("+3531122334466");
        createOrUpdateUserDTO.setEnabled(true);
        createOrUpdateUserDTO.setRoleId(1L);

        User userDataForTest = getUserDataForTest(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");

        given(userRepository.findByEmail("andrea.test@gmail.com")).willReturn(userDataForTest);

        userService.createUser(createOrUpdateUserDTO);
    }

    @Test(expected = InvalidUserGenderException.class)
    public void given_invalid_gender_string_when_getValidGender_throw_InvalidUserGenderException() {
        userService.getValidGender("WRONG_GENDER");
    }

    @Test
    public void given_valid_gender_string_when_getValidGender_return_Gender() {
        // here we're testing both values: male and female

        // male
        Gender genderMale = userService.getValidGender("MALE");

        assertNotNull(genderMale);
        assertEquals(1L , genderMale.getGender());


        // female
        Gender genderFemale = userService.getValidGender("FEMALE");

        assertNotNull(genderFemale);
        assertEquals(2L , genderFemale.getGender());
    }

    @Test(expected = InvalidUserIdentifierException.class)
    public void given_null_userId_when_deleteUserById_throw_InvalidUserIdentifierException() {
        userService.deleteUserById(null);
    }

    @Test(expected = UserNotFoundException.class)
    public void given_not_existing_userId_when_deleteUserById_throw_UserNotFoundException() {
        userService.deleteUserById(1L);
    }

    // create a test user data
    public User getUserDataForTest(Long id, String username,
                                   String name, String surname, String email, String phone) {
        User user = new User();

        user.setId(id);
        user.setUsername(username);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setEnabled(true);
        user.setGender(Gender.MALE);
        user.setPhone(phone);

        user.setCreationDt(LocalDateTime.of(2020, 2, 1, 12, 30));
        user.setUpdatedDt(LocalDateTime.of(2020, 2, 1, 16, 45));

        user.setRole(new Role(Role.USER, "USER"));
        return user;
    }

}
