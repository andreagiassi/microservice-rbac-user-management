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
        assertEquals("marco.test@gmail.com", createdUser.getEmail());
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

        User createdUser = userService.createUser(createOrUpdateUserDTO);

        assertNotNull(createdUser);
        assertEquals("John", createdUser.getName());
        assertEquals("Rossi", createdUser.getSurname());
        assertEquals("john.test@gmail.com", createdUser.getEmail());
        assertEquals("MALE", createdUser.getGender().name());
        assertEquals("john", createdUser.getUsername());
        assertEquals("+3531122334499", createdUser.getPhone());
        assertEquals("ADMINISTRATOR", createdUser.getRole().getRole());
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

        User createdUser = userService.updateUser(1L, createOrUpdateUserDTO);

        assertNotNull(createdUser);
        assertEquals("Andrea", createdUser.getName());
        assertEquals("Giassi", createdUser.getSurname());
        assertEquals("andrea.test@gmail.com", createdUser.getEmail());
        assertEquals("MALE", createdUser.getGender().name());
        assertEquals("andrea", createdUser.getUsername());
        assertEquals("+35344335522", createdUser.getPhone());
        assertEquals("ADMINISTRATOR", createdUser.getRole().getRole());
    }

}
