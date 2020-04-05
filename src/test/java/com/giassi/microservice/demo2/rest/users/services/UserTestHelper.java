package com.giassi.microservice.demo2.rest.users.services;

import com.giassi.microservice.demo2.rest.users.entities.Contact;
import com.giassi.microservice.demo2.rest.users.entities.Gender;
import com.giassi.microservice.demo2.rest.users.entities.Role;
import com.giassi.microservice.demo2.rest.users.entities.User;

import java.time.LocalDateTime;

public class UserTestHelper {

    // test data password & salt
    public static final String TEST_PASSWORD_ENCRYPTED = "1d/NZaEqNgtEomytAPrwm/+QjmbudLg33oeEk77Xh88=";
    public static final String TEST_PASSWORD_DECRYPTED = "Test!123";

    // create a test user data
    public static User getUserTestData(Long id, String username, String name, String surname, String email, String phone) {
        User user = new User();
        user.setId(id);

        user.setUsername(username);
        user.setPassword(TEST_PASSWORD_ENCRYPTED);

        user.setName(name);
        user.setSurname(surname);
        user.setGender(Gender.MALE);

        Contact contact = new Contact();
        contact.setEmail(email);
        contact.setPhone(phone);

        user.setContact(contact);

        user.setEnabled(true);

        user.setCreationDt(LocalDateTime.of(2020, 2, 1, 12, 30));
        user.setUpdatedDt(LocalDateTime.of(2020, 2, 1, 16, 45));
        user.setLoginDt(null);

        user.setRole(new Role(Role.USER, "USER"));
        return user;
    }

}
