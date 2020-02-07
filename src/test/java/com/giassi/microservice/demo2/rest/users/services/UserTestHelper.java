package com.giassi.microservice.demo2.rest.users.services;

import com.giassi.microservice.demo2.rest.users.entities.Gender;
import com.giassi.microservice.demo2.rest.users.entities.Role;
import com.giassi.microservice.demo2.rest.users.entities.User;

import java.time.LocalDateTime;

public class UserTestHelper {

    // create a test user data
    public static User getUserDataForTest(Long id, String username, String name, String surname,
                                          String email, String phone) {
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
