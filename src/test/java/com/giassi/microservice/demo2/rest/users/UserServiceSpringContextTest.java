package com.giassi.microservice.demo2.rest.users;

import com.giassi.microservice.demo2.rest.users.dtos.CreateOrUpdateUserDTO;
import com.giassi.microservice.demo2.rest.users.dtos.CreateUserAccountDTO;
import com.giassi.microservice.demo2.rest.users.entities.Role;
import com.giassi.microservice.demo2.rest.users.entities.User;
import com.giassi.microservice.demo2.rest.users.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserServiceSpringContextTest {

    @TestConfiguration
    static class UserServiceTestContextConfiguration {
        @Bean
        public UserService getUserService() {
            return new UserService();
        }
    }

    @Autowired
    private UserService userService;

    @Test
    public void given_valid_user_data_when_createNewUserAccount_return_createdUser() {
        CreateUserAccountDTO createUserAccountDTO = new CreateUserAccountDTO();
        createUserAccountDTO.setName("Marco");
        createUserAccountDTO.setSurname("Rossi");
        createUserAccountDTO.setEmail("marco.test@gmail.com");
        createUserAccountDTO.setGender("MALE");
        createUserAccountDTO.setUsername("marco");

        User createdUser = userService.createNewUserAccount(createUserAccountDTO);

        assertNotNull(createdUser);
        assertEquals("Marco", createdUser.getName());
        assertEquals("Rossi", createdUser.getSurname());
        assertEquals("marco.test@gmail.com", createdUser.getContact().getEmail());
        assertEquals("MALE", createdUser.getGender().name());
        assertEquals("marco", createdUser.getUsername());
    }

    @Test
    public void given_valid_user_data_when_createUser_return_createdUser() {
        CreateOrUpdateUserDTO createOrUpdateUserDTO = new CreateOrUpdateUserDTO();
        createOrUpdateUserDTO.setName("John");
        createOrUpdateUserDTO.setSurname("Rossi");
        createOrUpdateUserDTO.setEmail("john.test@gmail.com");
        createOrUpdateUserDTO.setGender("MALE");
        createOrUpdateUserDTO.setUsername("john");
        createOrUpdateUserDTO.setPhone("+3531122334499");
        createOrUpdateUserDTO.setRoleId(Role.ADMINISTRATOR);
        createOrUpdateUserDTO.setNote("test note");
        // set address
        createOrUpdateUserDTO.setAddress("via Frescobaldi 123");
        createOrUpdateUserDTO.setCity("Trieste");
        createOrUpdateUserDTO.setCountry("Italy");
        createOrUpdateUserDTO.setZipCode("34100");

        User createdUser = userService.createUser(createOrUpdateUserDTO);

        assertNotNull(createdUser);
        assertEquals("John", createdUser.getName());
        assertEquals("Rossi", createdUser.getSurname());
        assertEquals("john.test@gmail.com", createdUser.getContact().getEmail());
        assertEquals("MALE", createdUser.getGender().name());
        assertEquals("john", createdUser.getUsername());
        assertEquals("+3531122334499", createdUser.getContact().getPhone());
        assertEquals("ADMINISTRATOR", createdUser.getRole().getRole());
        assertEquals("test note", createdUser.getNote());
        // check on address
        assertEquals("via Frescobaldi 123" , createdUser.getAddress().getAddress());
        assertEquals("Trieste", createdUser.getAddress().getCity());
        assertEquals("Italy", createdUser.getAddress().getCountry());
        assertEquals("34100", createdUser.getAddress().getZipCode());
    }

    @Test
    public void given_valid_user_date_when_updateUser_return_userUpdated() {
        CreateOrUpdateUserDTO createOrUpdateUserDTO = new CreateOrUpdateUserDTO();
        createOrUpdateUserDTO.setName("Andrea");
        createOrUpdateUserDTO.setSurname("Giassi");
        createOrUpdateUserDTO.setEmail("andrea.test@gmail.com");
        createOrUpdateUserDTO.setGender("MALE");
        createOrUpdateUserDTO.setUsername("andrea");
        createOrUpdateUserDTO.setPhone("+35344335522"); // update the phone number
        createOrUpdateUserDTO.setRoleId(Role.ADMINISTRATOR);
        createOrUpdateUserDTO.setNote("update phone number note");
        // set address
        createOrUpdateUserDTO.setAddress("via Frescobaldi 123");
        createOrUpdateUserDTO.setCity("Trieste");
        createOrUpdateUserDTO.setCountry("Italy");
        createOrUpdateUserDTO.setZipCode("34100");

        User updatedUser = userService.updateUser(1L, createOrUpdateUserDTO);

        assertNotNull(updatedUser);
        assertEquals("Andrea", updatedUser.getName());
        assertEquals("Giassi", updatedUser.getSurname());
        assertEquals("andrea.test@gmail.com", updatedUser.getContact().getEmail());
        assertEquals("andrea", updatedUser.getUsername());
        assertEquals("MALE", updatedUser.getGender().name());
        assertEquals("+35344335522", updatedUser.getContact().getPhone());
        assertEquals("ADMINISTRATOR", updatedUser.getRole().getRole());
        assertEquals("update phone number note", updatedUser.getNote());
        // check on address
        assertEquals("via Frescobaldi 123", updatedUser.getAddress().getAddress());
        assertEquals("Trieste", updatedUser.getAddress().getCity());
        assertEquals("Italy", updatedUser.getAddress().getCountry());
        assertEquals("34100", updatedUser.getAddress().getZipCode());
    }

}
