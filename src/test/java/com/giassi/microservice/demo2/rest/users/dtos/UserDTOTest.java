package com.giassi.microservice.demo2.rest.users.dtos;

import com.giassi.microservice.demo2.rest.users.entities.*;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.Assert.*;

public class UserDTOTest {

    @Test
    public void userDTOTestConstructor1() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUsername");
        user.setName("testName");
        user.setSurname("testSurname");
        user.setEnabled(true);
        user.setGender(Gender.MALE);
        user.setEnabled(true);
        user.setSecured(false);

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

        Role roleUser = new Role(Role.USER, "USER");
        Role roleAdmin = new Role(Role.ADMINISTRATOR, "ADMINISTRATOR");

        user.getRoles().add(roleAdmin);
        user.getRoles().add(roleUser);

        UserDTO userDTO = new UserDTO(user);

        assertEquals(userDTO.getId(), user.getId());
        assertEquals(userDTO.getUsername(), user.getUsername());
        assertEquals(userDTO.getName(), user.getName());
        assertEquals(userDTO.getSurname(), user.getSurname());

        assertTrue(userDTO.isEnabled());
        assertTrue(!userDTO.isSecured());

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

        assertNotNull(user.getRoles());

        Set<Role> rolesTest = user.getRoles();

        assertTrue(rolesTest.contains(roleUser));
        assertTrue(rolesTest.contains(roleAdmin));
    }

    @Test
    public void userDTOTestConstructor2() {
        // test enabled and disabled permissions
        User user = new User();
        user.setId(1L);
        user.setUsername("testUsername");
        user.setName("testName");
        user.setSurname("testSurname");
        user.setEnabled(true);
        user.setGender(Gender.MALE);
        user.setEnabled(true);
        user.setSecured(false);

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

        // create a set of roles and permissions
        Role roleUser = new Role(Role.USER, "USER");
        Role roleAdmin = new Role(Role.ADMINISTRATOR, "ADMINISTRATOR");

        Permission p1 = new Permission(1L, "LOGIN", true, "Login");
        Permission p2 = new Permission(2L, "VIEW_PROFILE", true, "View Profile");
        Permission p3 = new Permission(3L, "ADMIN_STATISTICS", false, "View statistical graphs");
        Permission p4 = new Permission(4L, "ADMIN_PROFILES", true, "Manage users");

        roleUser.getPermissions().add(p1);
        roleUser.getPermissions().add(p2);

        roleAdmin.getPermissions().add(p3);
        roleAdmin.getPermissions().add(p4);

        user.getRoles().add(roleAdmin);
        user.getRoles().add(roleUser);

        UserDTO userDTO = new UserDTO(user);

        assertEquals(userDTO.getId(), user.getId());
        assertEquals(userDTO.getUsername(), user.getUsername());
        assertEquals(userDTO.getName(), user.getName());
        assertEquals(userDTO.getSurname(), user.getSurname());

        assertTrue(userDTO.isEnabled());
        assertTrue(!userDTO.isSecured());

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

        assertNotNull(user.getRoles());

        Set<Role> rolesTest = user.getRoles();

        assertTrue(rolesTest.contains(roleUser));
        assertTrue(rolesTest.contains(roleAdmin));

        assertEquals(2, userDTO.getRoles().size());
        assertTrue(userDTO.getRoles().contains("USER"));
        assertTrue(userDTO.getRoles().contains("ADMINISTRATOR"));

        assertEquals(2, userDTO.getRoles().size());
        assertEquals(3, userDTO.getPermissions().size());

        assertEquals(3, userDTO.getPermissions().size());
        assertTrue(userDTO.getPermissions().contains("LOGIN"));
        assertTrue(userDTO.getPermissions().contains("VIEW_PROFILE"));

        assertTrue(userDTO.getPermissions().contains("ADMIN_PROFILES"));
        assertFalse(userDTO.getPermissions().contains("ADMIN_STATISTICS"));
    }

}
