package com.giassi.microservice.demo2.rest.users;

import com.giassi.microservice.demo2.rest.users.dtos.requests.CreateOrUpdateUserDTO;
import com.giassi.microservice.demo2.rest.users.dtos.requests.RegisterUserAccountDTO;
import com.giassi.microservice.demo2.rest.users.entities.Role;
import com.giassi.microservice.demo2.rest.users.entities.User;
import com.giassi.microservice.demo2.rest.users.exceptions.UserNotFoundException;
import com.giassi.microservice.demo2.rest.users.services.EncryptionService;
import com.giassi.microservice.demo2.rest.users.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

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
        RegisterUserAccountDTO registerUserAccountDTO = new RegisterUserAccountDTO();
        registerUserAccountDTO.setName("Marco");
        registerUserAccountDTO.setSurname("Rossi");
        registerUserAccountDTO.setEmail("marco.test@gmail.com");
        registerUserAccountDTO.setGender("MALE");
        registerUserAccountDTO.setUsername("marco");
        registerUserAccountDTO.setPassword("Test!123");

        User createdUser = userService.registerUserAccount(registerUserAccountDTO);

        assertNotNull(createdUser);
        assertEquals("Marco", createdUser.getName());
        assertEquals("Rossi", createdUser.getSurname());
        assertEquals("marco.test@gmail.com", createdUser.getContact().getEmail());
        assertEquals("MALE", createdUser.getGender().name());
        assertEquals("marco", createdUser.getUsername());

        assertTrue(EncryptionService.isPasswordValid("Test!123", createdUser.getPassword(),
                EncryptionService.DEFAULT_SALT));
    }

    @Test
    public void given_valid_user_data_when_createUser_return_createdUser() {
        CreateOrUpdateUserDTO createOrUpdateUserDTO = new CreateOrUpdateUserDTO();
        createOrUpdateUserDTO.setName("John");
        createOrUpdateUserDTO.setSurname("Rossi");
        createOrUpdateUserDTO.setEmail("john.test@gmail.com");
        createOrUpdateUserDTO.setGender("MALE");
        createOrUpdateUserDTO.setUsername("john");
        createOrUpdateUserDTO.setPassword("Test!123");
        createOrUpdateUserDTO.setMobile("+3531122334499");
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

        assertTrue(EncryptionService.isPasswordValid("Test!123", createdUser.getPassword(),
                EncryptionService.DEFAULT_SALT));

        assertEquals("+3531122334499", createdUser.getContact().getPhone());

        Role adminRole = new Role(Role.USER, "USER");
        assertTrue(createdUser.getRoles().contains(adminRole));

        assertEquals("test note", createdUser.getNote());
        // check on address
        assertEquals("via Frescobaldi 123" , createdUser.getAddress().getAddress());
        assertEquals("Trieste", createdUser.getAddress().getCity());
        assertEquals("Italy", createdUser.getAddress().getCountry());
        assertEquals("34100", createdUser.getAddress().getZipCode());
    }

    @Test
    public void given_valid_user_data_when_updateUser_return_userUpdated() {
        CreateOrUpdateUserDTO updateUserDTO = new CreateOrUpdateUserDTO();
        updateUserDTO.setName("Andrea");
        updateUserDTO.setSurname("Giassi");
        updateUserDTO.setEmail("andrea.test@gmail.com");
        updateUserDTO.setGender("MALE");
        updateUserDTO.setUsername("andrea");
        updateUserDTO.setPassword("Test!123");
        updateUserDTO.setMobile("+35344335522"); // update the phone number
        updateUserDTO.setNote("update phone number note");
        // set address
        updateUserDTO.setAddress("via Frescobaldi 123");
        updateUserDTO.setCity("Trieste");
        updateUserDTO.setCountry("Italy");
        updateUserDTO.setZipCode("34100");

        User updatedUser = userService.updateUser(1L, updateUserDTO);

        assertNotNull(updatedUser);
        assertEquals("Andrea", updatedUser.getName());
        assertEquals("Giassi", updatedUser.getSurname());
        //
        assertEquals("andrea.test@gmail.com", updatedUser.getContact().getEmail());
        assertEquals("andrea", updatedUser.getUsername());
        assertEquals("1d/NZaEqNgtEomytAPrwm/+QjmbudLg33oeEk77Xh88=", updatedUser.getPassword());
        assertEquals("MALE", updatedUser.getGender().name());
        assertEquals("+35344335522", updatedUser.getContact().getPhone());

        Role adminRole = new Role(Role.USER, "USER");
        assertTrue(updatedUser.getRoles().contains(adminRole));

        assertEquals("update phone number note", updatedUser.getNote());
        // check on address
        assertEquals("via Frescobaldi 123", updatedUser.getAddress().getAddress());
        assertEquals("Trieste", updatedUser.getAddress().getCity());
        assertEquals("Italy", updatedUser.getAddress().getCountry());
        assertEquals("34100", updatedUser.getAddress().getZipCode());
    }

    @Test(expected = UserNotFoundException.class)
    public void given_valid_user_when_deleteUserById_user_deleted() {
        Long userId= 2L;
        userService.deleteUserById(userId);

        User user = userService.getUserById(userId);
    }

}
