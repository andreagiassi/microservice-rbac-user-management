package com.giassi.microservice.demo2.rest.users.services;

import com.giassi.microservice.demo2.rest.users.dtos.UserDTO;
import com.giassi.microservice.demo2.rest.users.dtos.requests.CreateOrUpdateUserDTO;
import com.giassi.microservice.demo2.rest.users.dtos.requests.RegisterUserAccountDTO;
import com.giassi.microservice.demo2.rest.users.entities.Gender;
import com.giassi.microservice.demo2.rest.users.entities.Role;
import com.giassi.microservice.demo2.rest.users.entities.User;
import com.giassi.microservice.demo2.rest.users.exceptions.*;
import com.giassi.microservice.demo2.rest.users.repositories.RoleRepository;
import com.giassi.microservice.demo2.rest.users.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.giassi.microservice.demo2.rest.users.services.UserTestHelper.getUserTestData;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Autowired
    @InjectMocks
    private UserService userService = new UserService();

    @Before
    public void setUp() {
        // using the default salt for test
        ReflectionTestUtils.setField(userService, "salt", EncryptionService.DEFAULT_SALT);
    }

    @Test
    public void given_existing_users_when_getUserPresentationList_return_validList() {
        User user1 = getUserTestData(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");
        User user2= getUserTestData(2L, "marco", "Marco",
                "Rossi", "marco.test@gmail.com", "+3531122334466");
        User user3 = getUserTestData(3L, "francesco", "Francesco",
                "Verdi", "francesco.test@gmail.com", "+3531122334477");

        List<User> list = Arrays.asList(user1, user2, user3);

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

        assertNotNull(userDTO.getContactDTO());
        assertEquals("marco.test@gmail.com" , userDTO.getContactDTO().getEmail());
        assertEquals("+3531122334466" , userDTO.getContactDTO().getPhone());
    }

    @Test
    public void given_existing_user_when_getUserById_returnUser() {
        Long userId = 1L;

        User user = getUserTestData(userId, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");

        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        User userRet = userService.getUserById(userId);

        assertNotNull(userRet);
        assertEquals(userId, userRet.getId());
        assertEquals("andrea", userRet.getUsername());
        assertEquals("Andrea", userRet.getName());
        assertEquals("Giassi", userRet.getSurname());
        assertEquals("andrea.test@gmail.com", userRet.getContact().getEmail());
        assertEquals("+3531122334455", userRet.getContact().getPhone());
    }

    @Test(expected = UserNotFoundException.class)
    public void given_not_existing_user_when_getUserById_throw_exception() {
        Long userId = 2L;

        given(userRepository.findById(userId)).willReturn(Optional.empty());

        userService.getUserById(userId);
    }

    @Test(expected = InvalidUserIdentifierException.class)
    public void given_null_user_id_when_getUserById_throw_exception() {
        userService.getUserById(null);
    }

    @Test(expected = InvalidUsernameException.class)
    public void given_null_username_when_getUserByUsername_return_user() {
        userService.getUserByUsername(null);
    }

    @Test
    public void given_existing_username_when_getUserByUsername_return_user() {
        User userDataForTest = getUserTestData(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");

        given(userRepository.findByUsername("andrea")).willReturn(userDataForTest);

        User user = userService.getUserByUsername("andrea");

        assertNotNull(user);
        assertEquals(Long.valueOf(1L), user.getId());
        assertEquals("andrea", user.getUsername());
        assertEquals("Andrea", user.getName());
        assertEquals("Giassi", user.getSurname());
        assertEquals("andrea.test@gmail.com", user.getContact().getEmail());
        assertEquals("+3531122334455", user.getContact().getPhone());
    }

    @Test
    public void given_existing_email_when_getUserByEmail_return_user() {
        User userDataForTest = getUserTestData(1L, "andrea", "Andrea",
            "Giassi", "andrea.test@gmail.com", "+3531122334455");

        given(userRepository.findByEmail("andrea.test@gmail.com")).willReturn(userDataForTest);

        User user = userService.getUserByEmail("andrea.test@gmail.com");

        assertNotNull(user);
        assertEquals(Long.valueOf(1L), user.getId());
        assertEquals("andrea", user.getUsername());
        assertEquals("Andrea", user.getName());
        assertEquals("Giassi", user.getSurname());
        assertEquals("andrea.test@gmail.com", user.getContact().getEmail());
        assertEquals("+3531122334455", user.getContact().getPhone());
    }

    @Test(expected = InvalidEmailException.class)
    public void given_invalid_email_getUserByEmail_throw_InvalidUserEmailException() {
        User user = userService.getUserByEmail(null);
    }

    @Test(expected = InvalidUserDataException.class)
    public void given_null_CreateUserAccountDTO_when_createNewUserAccount_throw_InvalidUserDataException() {
        userService.registerUserAccount(null);
    }

    @Test(expected = InvalidUserDataException.class)
    public void given_already_existing_username_when_createNewUserAccount_throw_InvalidUserDataException() {
        User userDataForTest = getUserTestData(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");

        given(userRepository.findByUsername("andrea")).willReturn(userDataForTest);

        RegisterUserAccountDTO registerUserAccountDTO = RegisterUserAccountDTO.builder()
                .name("Andrea")
                .surname("Giassi")
                .email("andrea.test@gmail.com")
                .gender("MALE")
                .username("andrea")
                .password(UserTestHelper.TEST_PASSWORD_DECRYPTED)
                .build();

        userService.registerUserAccount(registerUserAccountDTO);
    }

    @Test(expected = InvalidUserDataException.class)
    public void given_existing_email_when_createNewUserAccount_throw_InvalidUserDataException() {
        User userDataForTest = getUserTestData(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");

        given(userRepository.findByEmail("andrea.test@gmail.com")).willReturn(userDataForTest);

        // existing email
        RegisterUserAccountDTO registerUserAccountDTO = RegisterUserAccountDTO.builder()
                .name("Marco")
                .password("Marco!123")
                .surname("Rossi")
                .email("andrea.test@gmail.com")
                .gender("MALE")
                .username("marco")
                .password(UserTestHelper.TEST_PASSWORD_DECRYPTED)
                .build();

        userService.registerUserAccount(registerUserAccountDTO);
    }

    @Test(expected = RoleNotFoundException.class)
    public void given_invalidRole_when_setUserRole_throw_RoleNotFoundException() {
        User userDataForTest = getUserTestData(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");

        // role doesn't exists
        userService.addUserRole(userDataForTest, 1);
    }

    @Test
    public void given_valid_role_id_when_setUserRole_returnUser() {
        User userDataForTest = getUserTestData(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");

        given(roleRepository.findById(Role.USER)).willReturn(Optional.of(new Role(Role.USER, "USER")));

        userService.addUserRole(userDataForTest, Role.USER);

        assertNotNull(userDataForTest);

        Role roleUser = new Role(Role.USER, "USER");
        assertTrue(userDataForTest.getRoles().contains(roleUser));

        assertEquals("andrea", userDataForTest.getUsername());
        assertEquals("Andrea", userDataForTest.getName());
        assertEquals("Giassi", userDataForTest.getSurname());
        assertTrue(userDataForTest.isEnabled());

        assertNotNull(userDataForTest.getContact());
        assertEquals("andrea.test@gmail.com", userDataForTest.getContact().getEmail());
        assertEquals("+3531122334455", userDataForTest.getContact().getPhone());
    }

    @Test(expected = InvalidUserDataException.class)
    public void given_invalid_CreateOrUpdateUserDTO_when_createUser_throw_InvalidUserDataException() {
        userService.createUser(null);
    }

    @Test(expected = InvalidUserDataException.class)
    public void given_already_registered_username_when_createUser_throw_InvalidUserDataException() {
        CreateOrUpdateUserDTO createOrUpdateUserDTO = CreateOrUpdateUserDTO.builder().username("andrea").build();

        User userDataForTest = getUserTestData(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");

        given(userRepository.findByUsername("andrea")).willReturn(userDataForTest);

        userService.createUser(createOrUpdateUserDTO);
    }

    @Test(expected = InvalidUserDataException.class)
    public void given_already_registered_email_when_createUser_throw_InvalidUserDataException() {
        // existing email
        CreateOrUpdateUserDTO createOrUpdateUserDTO = CreateOrUpdateUserDTO.builder()
                .name("Marco")
                .surname("Rossi")
                .email("andrea.test@gmail.com")
                .gender("MALE")
                .username("marco")
                .mobile("+3531122334466")
                .enabled(true).build();

        User userDataForTest = getUserTestData(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");

        given(userRepository.findByEmail("andrea.test@gmail.com")).willReturn(userDataForTest);

        userService.createUser(createOrUpdateUserDTO);
    }

    @Test(expected = InvalidGenderException.class)
    public void given_invalid_gender_string_when_getValidGender_throw_InvalidUserGenderException() {
        Gender.getValidGender("WRONG_GENDER");
    }

    @Test
    public void given_valid_gender_strings_when_getValidGender_return_Gender() {
        // male
        Gender maleGender = Gender.getValidGender("MALE");

        assertNotNull(maleGender);
        assertEquals(1L , maleGender.getGender());

        // female
        Gender femaleGender = Gender.getValidGender("FEMALE");

        assertNotNull(femaleGender);
        assertEquals(2L , femaleGender.getGender());
    }

    @Test(expected = InvalidUserIdentifierException.class)
    public void given_invalid_userId_when_updateUser_throw_InvalidUserIdentifierException() {
        userService.updateUser(null, new CreateOrUpdateUserDTO());
    }

    @Test(expected = InvalidUserDataException.class)
    public void given_invalid_createOrUpdateUserDTO_when_updateUser_throw_InvalidUserDataException() {
        userService.updateUser(1L, null);
    }

    @Test(expected = UserNotFoundException.class)
    public void given_not_existing_userId_when_updateUser_throw_UserNotFoundException() {
        given(userRepository.findById(1L)).willReturn(Optional.empty());
        userService.updateUser(1L, new CreateOrUpdateUserDTO());
    }

    @Test(expected = InvalidUserDataException.class)
    public void given_existing_username_when_updateUser_throw_InvalidUserDataException() {
        // setting an existing username
        CreateOrUpdateUserDTO createOrUpdateUserDTO = CreateOrUpdateUserDTO.builder()
                .name("Marco")
                .surname("Rossi")
                .email("andrea.test@gmail.com")
                .gender("MALE")
                .username("andrea")
                .mobile("+3531122334466")
                .enabled(true)
                .build();

        User userDataForTest = getUserTestData(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");
        User userDataForTest2 = getUserTestData(2L, "andrea", "Marco",
                "Rossi", "marco.test@gmail.com", "+3531122334466");

        given(userRepository.findById(2L)).willReturn(Optional.of(userDataForTest2));
        given(userRepository.findByUsername("andrea")).willReturn(userDataForTest);

        userService.updateUser(2L, createOrUpdateUserDTO);
    }

    @Test(expected = InvalidUserDataException.class)
    public void given_existing_email_when_updateUser_throw_InvalidUserDataException() {
        // setting an existing email
        CreateOrUpdateUserDTO createOrUpdateUserDTO = CreateOrUpdateUserDTO.builder()
                .name("Marco")
                .surname("Rossi")
                .email("andrea.test@gmail.com")
                .gender("MALE")
                .username("marco")
                .password("Test!123")
                .mobile("+3531122334466")
                .enabled(true)
                .build();

        User userDataForTest = getUserTestData(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");
        User userDataForTest2 = getUserTestData(2L, "marco", "Marco",
                "Rossi", "marco.test@gmail.com", "+3531122334466");

        given(userRepository.findById(2L)).willReturn(Optional.of(userDataForTest2));
        given(userRepository.findByEmail("andrea.test@gmail.com")).willReturn(userDataForTest);

        userService.updateUser(2L, createOrUpdateUserDTO);
    }

    @Test
    public void given_existing_user_when_updatedUser_return_userUpdated() {
        // correct user data, update the phone number
        CreateOrUpdateUserDTO createOrUpdateUserDTO = CreateOrUpdateUserDTO.builder()
                .name("Andrea")
                .surname("Giassi")
                .email("andrea.test@gmail.com")
                .gender("MALE")
                .username("andrea")
                .password("Test!123")
                .mobile("+3539988776655")
                .enabled(true)
                .address("via roma 3").city("Rome").country("Italy").zipCode("00100")
                .build();

        User userDataForTest = getUserTestData(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");

        given(userRepository.findById(1L)).willReturn(Optional.of(userDataForTest));

        userService.updateUser(1L, createOrUpdateUserDTO);
        verify(userRepository, times(1)).save(Mockito.any(User.class));
    }

    @Test(expected = InvalidUserIdentifierException.class)
    public void given_null_userId_when_deleteUserById_throw_InvalidUserIdentifierException() {
        userService.deleteUserById(null);
    }

    @Test(expected = UserNotFoundException.class)
    public void given_not_existing_userId_when_deleteUserById_throw_UserNotFoundException() {
        userService.deleteUserById(1L);
    }

    @Test(expected = InvalidLoginException.class)
    public void given_null_username_and_null_password_when_login_throw_InvalidLoginException() {
        userService.login(null, null);
    }

    @Test(expected = InvalidLoginException.class)
    public void given_null_username_login_when_login_throw_InvalidLoginException() {
        userService.login(null, "WRONG_PWD");
    }

    @Test(expected = InvalidLoginException.class)
    public void given_null_password_login_when_login_throw_InvalidLoginException() {
        userService.login("WRONG", null);
    }

    @Test(expected = InvalidLoginException.class)
    public void given_invalid_login_when_login_throw_InvalidLoginException() {
        userService.login("WRONG", "WRONG_PWD");
    }

    @Test
    public void given_valid_login_when_login_return_User() {
        User userDataForTest = getUserTestData(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");

        given(userRepository.findByUsername("andrea")).willReturn(userDataForTest);

        User user = userService.login("andrea", UserTestHelper.TEST_PASSWORD_DECRYPTED);

        assertNotNull(user);
        assertEquals("andrea", user.getUsername());
    }

    @Test(expected = InvalidLoginException.class)
    public void given_invalid_login2_when_login_throw_InvalidLoginException() {
        User userDataForTest = getUserTestData(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");

        given(userRepository.findByUsername("andrea")).willReturn(userDataForTest);

        User user = userService.login("andrea", "WRONG_PWD");
    }

    @Test(expected = InvalidLoginException.class)
    public void given_not_enabled_login_when_login_throw_InvalidLoginException() {
        User userDataForTest = getUserTestData(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");

        userDataForTest.setEnabled(false);

        given(userRepository.findByUsername("andrea")).willReturn(userDataForTest);

        User user = userService.login("andrea", UserTestHelper.TEST_PASSWORD_DECRYPTED);
    }

    // tests add role on User
    @Test(expected = UserNotFoundException.class)
    public void given_notExistingUserId_when_addRole_throw_UserNotFoundException() {
        User user = userService.addRole(99L, 2L);
    }

    @Test(expected = RoleNotFoundException.class)
    public void given_existingUserId_notExistingRoleId_when_addRole_throw_RoleNotFoundException() {
        User userDataForTest = getUserTestData(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");

        given(userRepository.findById(1L)).willReturn(Optional.of(userDataForTest));

        userService.addRole(1L, 99L);
    }

    @Test
    public void given_validUserAndRoleIds_when_addRole_returnUser() {
        User userDataForTest = getUserTestData(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");

        given(userRepository.findById(1L)).willReturn(Optional.of(userDataForTest));

        Role roleAdmin = new Role(Role.ADMINISTRATOR, "Administrator");

        given(roleRepository.findById(2L)).willReturn(Optional.of(roleAdmin));

        User user = userService.addRole(1L, 2L);

        assertNotNull(user);

        // check the new added role
        Set<Role> roleSet = user.getRoles();

        assertNotNull(roleSet);
        assertEquals(2, roleSet.size());
        assertTrue(roleSet.contains(roleAdmin));
    }

    // test remove role from User
    @Test(expected = UserNotFoundException.class)
    public void given_notExistingUserId_when_removeRole_throw_UserNotFoundException() {
        User user = userService.removeRole(99L, 2L);
    }

    @Test(expected = RoleNotFoundException.class)
    public void given_existingUserId_notExistingRoleId_when_removeRole_throw_RoleNotFoundException() {
        User userDataForTest = getUserTestData(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");

        given(userRepository.findById(1L)).willReturn(Optional.of(userDataForTest));

        userService.removeRole(1L, 99L);
    }

    @Test
    public void given_validUserAndRoleIds_when_removeRole_returnUser() {
        User userDataForTest = getUserTestData(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");

        Role roleAdmin = new Role(Role.ADMINISTRATOR, "Administrator");
        userDataForTest.getRoles().add(roleAdmin);

        given(userRepository.findById(1L)).willReturn(Optional.of(userDataForTest));
        given(roleRepository.findById(2L)).willReturn(Optional.of(roleAdmin));

        User user = userService.removeRole(1L, 2L);

        assertNotNull(user);

        // check the remove role
        Set<Role> roleSet = user.getRoles();

        assertNotNull(roleSet);
        assertEquals(1, roleSet.size());
        assertTrue(!roleSet.contains(roleAdmin));
    }

    @Test(expected = UserIsSecuredException.class)
    public void given_validSecuredUser_when_deleteUser_throw_UserIsSecuredException() {
        User userDataForTest = getUserTestData(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");
        // set a secure user
        userDataForTest.setSecured(true);

        given(userRepository.findById(1L)).willReturn(Optional.of(userDataForTest));

        userService.deleteUserById(1L);
    }

}
