package com.giassi.microservice.demo2.rest.users;

import com.giassi.microservice.demo2.rest.users.dtos.CreateUserAccountDTO;
import com.giassi.microservice.demo2.rest.users.services.UserService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@Ignore
public class UserServiceSpringContextTest {

    // TODO: to adjust and complete

    @Autowired
    private UserService userService;

    @Test
    public void given_valid_user_data_when_createNewUserAccount_return_createdUser() {
        CreateUserAccountDTO createUserAccountDTO = new CreateUserAccountDTO();
        createUserAccountDTO.setName("Marco");
        createUserAccountDTO.setSurname("Rossi");
        createUserAccountDTO.setEmail("andrea.test@gmail.com");
        createUserAccountDTO.setGender("MALE");
        createUserAccountDTO.setUsername("marco");

        userService.createNewUserAccount(createUserAccountDTO);
    }

}
