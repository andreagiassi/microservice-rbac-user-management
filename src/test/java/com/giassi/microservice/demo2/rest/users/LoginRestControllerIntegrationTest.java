package com.giassi.microservice.demo2.rest.users;

import com.giassi.microservice.demo2.rest.users.dtos.UserDTO;
import com.giassi.microservice.demo2.rest.users.dtos.requests.LoginRequestDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginRestControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void test_valid_login() {
        LoginRequestDTO loginRequest = new LoginRequestDTO("Andrea", "Test!123");

        ResponseEntity<UserDTO> response = restTemplate.postForEntity("/login", loginRequest, UserDTO.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        UserDTO userDTO = response.getBody();
        assertNotNull(userDTO);

        assertThat(userDTO.getId(), equalTo(1L));
        assertThat(userDTO.getName(), equalTo("Andrea"));
        assertThat(userDTO.getSurname(), equalTo("Test"));

        assertNotNull(userDTO.getContactDTO());

        assertThat(userDTO.getContactDTO().getEmail(), equalTo("andrea.test@gmail.com"));
        assertThat(userDTO.isEnabled(), equalTo(true));
    }

    @Test
    public void test_invalid_login() {
        // use a formal valid password but not correct for the given account
        LoginRequestDTO loginRequest = new LoginRequestDTO("Andrea", "Test!123456");

        ResponseEntity<UserDTO> response = restTemplate.postForEntity("/login", loginRequest, UserDTO.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

}
