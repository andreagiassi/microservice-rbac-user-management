package com.giassi.microservice.demo2.rest.users;

import com.giassi.microservice.demo2.rest.users.dtos.AddressDTO;
import com.giassi.microservice.demo2.rest.users.dtos.RoleDTO;
import com.giassi.microservice.demo2.rest.users.dtos.UserDTO;
import com.giassi.microservice.demo2.rest.users.dtos.requests.CreateOrUpdateUserDTO;
import com.giassi.microservice.demo2.rest.users.dtos.requests.CreateUserAccountDTO;
import com.giassi.microservice.demo2.rest.users.entities.Role;
import com.giassi.microservice.demo2.rest.users.entities.User;
import com.giassi.microservice.demo2.rest.users.repositories.UserRepository;
import com.giassi.microservice.demo2.rest.users.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRestControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void test_getUserById() {
        Long userId = 1L;
        String userURL = "/users/" + userId;

        ResponseEntity<UserDTO> response = restTemplate.getForEntity(userURL, UserDTO.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        UserDTO userDTO = response.getBody();
        assertNotNull(userDTO);

        assertThat(userDTO.getId(), equalTo(1L));
        assertThat(userDTO.getName(), equalTo("Andrea"));
        assertThat(userDTO.getSurname(), equalTo("Test"));
        assertThat(userDTO.getEmail(), equalTo("andrea.test@gmail.com"));
        assertThat(userDTO.isEnabled(), equalTo(true));
    }

    @Test
    public void test_createUser() {
        CreateOrUpdateUserDTO createOrUpdateUserDTO = CreateOrUpdateUserDTO.builder()
               .username("frank")
               .password("Frank!123")
               .name("Frank")
               .surname("Blu")
               .gender("MALE")
               .enabled(true)
               .roleId(Role.USER)
               .note("created for test")
               .email("frank.blu@gmail.com")
               .phone("+3531194334455")
               .address("dark road 1")
               .city("Dublin")
               .country("Ireland")
               .zipCode("47335").build();

        URI uri = URI.create("/users");

        HttpEntity<CreateOrUpdateUserDTO> request = new HttpEntity<>(createOrUpdateUserDTO);
        ResponseEntity<UserDTO> response = restTemplate.postForEntity(uri, request, UserDTO.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));

        UserDTO userDTO = response.getBody();
        assertNotNull(userDTO);

        assertNotNull(userDTO);
        assertEquals("frank", userDTO.getUsername());
        assertEquals("Frank", userDTO.getName());
        assertEquals("Blu", userDTO.getSurname());
        assertEquals("MALE", userDTO.getGender());

        RoleDTO roleDTO = userDTO.getRoleDTO();
        assertNotNull(roleDTO);
        assertEquals(Long.valueOf(1) ,roleDTO.getId());
        assertEquals("USER", roleDTO.getRole());

        assertEquals(true, userDTO.isEnabled());
        assertEquals("created for test", userDTO.getNote());
        assertEquals("frank.blu@gmail.com", userDTO.getEmail());
        assertEquals("+3531194334455", userDTO.getPhone());

        assertNotNull(userDTO.getAddressDTO());
        AddressDTO addressDTO = userDTO.getAddressDTO();
        assertEquals("dark road 1", addressDTO.getAddress());
        assertEquals("Dublin", addressDTO.getCity());
        assertEquals("Ireland", addressDTO.getCountry());
        assertEquals("47335", addressDTO.getZipCode());

        // delete the created user
        userService.deleteUserById(userDTO.getId());
    }

    @Test
    public void test_createNewUserAccount() {
        // create a new user using the quick account endpoint
        CreateUserAccountDTO quickAccount = CreateUserAccountDTO.builder()
                .username("violet")
                .password("Violet!123")
                .name("Marco")
                .surname("Violet")
                .gender("MALE")
                .email("marco.violet@gmail.com")
                .build();

        String userQuickAccountURL = "/users/quickAccount";

        HttpEntity<CreateUserAccountDTO> request = new HttpEntity<>(quickAccount);
        ResponseEntity<UserDTO> response = restTemplate.postForEntity(userQuickAccountURL, request, UserDTO.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));

        UserDTO userDTO = response.getBody();

        assertNotNull(userDTO);
        assertEquals("violet", userDTO.getUsername());
        assertEquals("Marco", userDTO.getName());
        assertEquals("Violet", userDTO.getSurname());
        assertEquals("MALE", userDTO.getGender());
        assertEquals("marco.violet@gmail.com", userDTO.getEmail());

        // delete the created user
        userService.deleteUserById(userDTO.getId());
    }

    @Test
    public void test_updateUser() {
        // create a new user to update
        CreateUserAccountDTO quickAccount = CreateUserAccountDTO.builder()
                .username("anna")
                .password("Anna!123")
                .name("Anna")
                .surname("Verdi")
                .gender("FEMALE")
                .email("anna.verdi@gmail.com")
                .build();

        String userQuickAccountURL = "/users/quickAccount";
        HttpEntity<CreateUserAccountDTO> requestCreate = new HttpEntity<>(quickAccount);
        ResponseEntity<UserDTO> responseCreate = restTemplate.postForEntity(userQuickAccountURL, requestCreate, UserDTO.class);

        assertThat(responseCreate.getStatusCode(), equalTo(HttpStatus.CREATED));
        UserDTO userDTO = responseCreate.getBody();

        assertNotNull(userDTO);

        // test the update
        Long userId = userDTO.getId();
        URI uri = URI.create("/users/" + userId);

        CreateOrUpdateUserDTO createOrUpdateUserDTO = CreateOrUpdateUserDTO.builder()
                .username("anna")
                .password("Anna!123456")
                .name("Anna")
                .surname("Verdi")
                .gender("FEMALE")
                .enabled(true)
                .roleId(Role.USER)
                .note("updated for test")
                .email("anna.verdi@gmail.com")
                .phone("+3531194334455")
                .address("The sunny road 15")
                .city("Dublin")
                .country("Ireland")
                .zipCode("47335").build();

        HttpEntity<CreateOrUpdateUserDTO> request = new HttpEntity<>(createOrUpdateUserDTO);
        ResponseEntity<UserDTO> response = restTemplate.exchange(uri, HttpMethod.PUT, request, UserDTO.class);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

        UserDTO userUpdatedDTO = response.getBody();

        assertEquals("anna", userUpdatedDTO.getUsername());
        assertEquals("Anna", userUpdatedDTO.getName());
        assertEquals("Verdi", userUpdatedDTO.getSurname());
        assertEquals("FEMALE", userUpdatedDTO.getGender());
        assertEquals(true, userUpdatedDTO.isEnabled());

        // role
        assertNotNull(userUpdatedDTO.getRoleDTO());
        assertEquals("USER", userUpdatedDTO.getRoleDTO().getRole());

        assertEquals("updated for test", userUpdatedDTO.getNote());
        assertEquals("anna.verdi@gmail.com", userUpdatedDTO.getEmail());
        assertEquals("+3531194334455", userUpdatedDTO.getPhone());
        assertEquals("anna.verdi@gmail.com", userUpdatedDTO.getEmail());

        // address
        assertNotNull(userUpdatedDTO.getAddressDTO());
        assertEquals("The sunny road 15", userUpdatedDTO.getAddressDTO().getAddress());
        assertEquals("Dublin", userUpdatedDTO.getAddressDTO().getCity());
        assertEquals("Ireland", userUpdatedDTO.getAddressDTO().getCountry());
        assertEquals("47335", userUpdatedDTO.getAddressDTO().getZipCode());

        // delete the user
        userService.deleteUserById(userUpdatedDTO.getId());
    }

    @Test
    public void test_deleteUser() {
        // create a new user to test the deletion
        CreateUserAccountDTO quickAccount = CreateUserAccountDTO.builder()
                .username("anna")
                .password("Anna!123")
                .name("Anna")
                .surname("Verdi")
                .gender("FEMALE")
                .email("anna.verdi@gmail.com")
                .build();

        String userQuickAccountURL = "/users/quickAccount";
        HttpEntity<CreateUserAccountDTO> request = new HttpEntity<>(quickAccount);
        ResponseEntity<UserDTO> response = restTemplate.postForEntity(userQuickAccountURL, request, UserDTO.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
        UserDTO userDTO = response.getBody();

        assertNotNull(userDTO);

        // call the delete endpoint
        String deleteUserURL = "/users/" + userDTO.getId();
        restTemplate.delete(deleteUserURL);

        // retrieve a not existing user must to be empty response
        Optional<User> userOpt = userRepository.findById(userDTO.getId());
        assertFalse(userOpt.isPresent());
    }

}
