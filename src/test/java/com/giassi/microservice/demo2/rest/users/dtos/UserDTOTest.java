package com.giassi.microservice.demo2.rest.users.dtos;

import com.giassi.microservice.demo2.rest.users.entities.Contact;
import com.giassi.microservice.demo2.rest.users.entities.Gender;
import com.giassi.microservice.demo2.rest.users.entities.Role;
import com.giassi.microservice.demo2.rest.users.entities.User;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserDTOTest {

    @Test
    public void userDTOTestConstructor() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUsername");
        user.setName("testName");
        user.setSurname("testSurname");
        user.setEnabled(true);
        user.setGender(Gender.MALE);

        Contact contact = new Contact();
        contact.setEmail("email");
        contact.setPhone("+3531122334455");

        user.setContact(contact);

        LocalDateTime creationDt = LocalDateTime.of(2020, 2, 1, 12, 30);
        user.setCreationDt(creationDt);

        LocalDateTime updatedDt = LocalDateTime.of(2020, 2, 1, 16, 45);
        user.setUpdatedDt(updatedDt);

        Role role = new Role();
        role.setId(Role.ADMINISTRATOR);
        role.setRole("ADMINISTRATOR");

        user.setRole(role);

        UserDTO userDTO = new UserDTO(user);

        assertEquals(userDTO.getId(), user.getId());
        assertEquals(userDTO.getUsername(), user.getUsername());
        assertEquals(userDTO.getName(), user.getName());
        assertEquals(userDTO.getSurname(), user.getSurname());

        assertEquals(userDTO.getEmail(), user.getContact().getEmail());
        assertEquals(userDTO.getPhone(), user.getContact().getPhone());

        assertEquals(userDTO.isEnabled(), user.isEnabled());

        assertEquals(creationDt, userDTO.getCreationDt());
        assertEquals(updatedDt, userDTO.getUpdatedDt());

        assertNotNull(user.getRole());

        Role roleTest = user.getRole();
        assertEquals(role.getId(), roleTest.getId());
        assertEquals(role.getRole(), roleTest.getRole());
    }

}
