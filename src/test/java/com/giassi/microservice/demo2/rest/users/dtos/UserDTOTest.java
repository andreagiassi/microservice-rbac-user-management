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

        Contact contactInput = new Contact();
        contactInput.setEmail("email");
        contactInput.setPhone("+3531122334455");
        contactInput.setSkype("skype");
        contactInput.setFacebook("facebook");
        contactInput.setLinkedin("linkedin");
        contactInput.setWebsite("www.test.com");
        contactInput.setNote("Test note");

        user.setContact(contactInput);

        LocalDateTime creationDt = LocalDateTime.of(2020, 2, 1, 12, 30);
        user.setCreationDt(creationDt);

        LocalDateTime updatedDt = LocalDateTime.of(2020, 2, 1, 16, 45);
        user.setUpdatedDt(updatedDt);

        Role role = new Role(Role.ADMINISTRATOR, "ADMINISTRATOR");
        user.setRole(role);

        UserDTO userDTO = new UserDTO(user);

        assertEquals(userDTO.getId(), user.getId());
        assertEquals(userDTO.getUsername(), user.getUsername());
        assertEquals(userDTO.getName(), user.getName());
        assertEquals(userDTO.getSurname(), user.getSurname());

        // contact
        ContactDTO contactDTO = userDTO.getContactDTO();
        assertNotNull(contactDTO);

        assertEquals(userDTO.getContactDTO().getEmail(), user.getContact().getEmail());
        assertEquals(userDTO.getContactDTO().getPhone(), user.getContact().getPhone());
        assertEquals(userDTO.getContactDTO().getSkype(), user.getContact().getSkype());
        assertEquals(userDTO.getContactDTO().getFacebook(), user.getContact().getFacebook());
        assertEquals(userDTO.getContactDTO().getLinkedin(), user.getContact().getLinkedin());
        assertEquals(userDTO.getContactDTO().getWebsite(), user.getContact().getWebsite());
        assertEquals(userDTO.getContactDTO().getContactNote(), user.getContact().getNote());

        assertEquals(userDTO.isEnabled(), user.isEnabled());

        assertEquals(creationDt, userDTO.getCreationDt());
        assertEquals(updatedDt, userDTO.getUpdatedDt());
        assertEquals(null, userDTO.getLoginDt());

        assertNotNull(user.getRole());

        Role roleTest = user.getRole();
        assertEquals(role.getId(), roleTest.getId());
        assertEquals(role.getRole(), roleTest.getRole());
    }

}
