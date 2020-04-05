package com.giassi.microservice.demo2.rest.users.dtos;

import com.giassi.microservice.demo2.rest.users.entities.Contact;
import com.giassi.microservice.demo2.rest.users.entities.User;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserDTO implements Serializable {

    public UserDTO() {
        // empty constructor
    }

    public UserDTO(User user) {
        if (user != null) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.name = user.getName();
            this.surname = user.getSurname();
            this.gender = user.getGender().name();

            this.enabled = user.isEnabled();

            this.note = user.getNote();

            this.creationDt = user.getCreationDt();
            this.updatedDt = user.getUpdatedDt();
            this.loginDt = user.getLoginDt();

            // contact, if set
            if (user.getContact() != null) {
                Contact contact = user.getContact();

                this.email = contact.getEmail();
                this.phone = contact.getPhone();
                this.skype = contact.getSkype();
                this.facebook = contact.getFacebook();
                this.linkedin = contact.getLinkedin();
                this.website = contact.getWebsite();
                this.contactNote = contact.getNote();
            }

            // role, if set
            if (user.getRole() != null) {
                roleDTO = new RoleDTO(user.getRole());
            }

            // address, if set
            if (user.getAddress() != null) {
                addressDTO = new AddressDTO(user.getAddress());
            }
        }
    }

    private Long id;
    private String username;
    private String name;
    private String surname;
    private String gender;

    // contact
    private String email;
    private String phone;
    private String skype;
    private String facebook;
    private String linkedin;
    private String website;
    private String contactNote;

    private boolean enabled;

    private String note;

    private LocalDateTime creationDt;
    private LocalDateTime updatedDt;
    private LocalDateTime loginDt;

    // additional information
    private RoleDTO roleDTO;
    private AddressDTO addressDTO;

}
