package com.giassi.microservice.demo2.rest.users.dtos;

import com.giassi.microservice.demo2.rest.users.entities.User;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

            this.birthDate = user.getBirthDate();

            this.enabled = user.isEnabled();

            this.note = user.getNote();

            this.creationDt = user.getCreationDt();
            this.updatedDt = user.getUpdatedDt();
            this.loginDt = user.getLoginDt();

            this.secured = user.isSecured();

            // role, if set
            if (user.getRoles() != null) {
                roleDTOSet = new HashSet<>();
                user.getRoles().stream().forEach(e -> roleDTOSet.add(new RoleDTO(e)));
            }

            // contact, if set
            if (user.getContact() != null) {
                this.contactDTO = new ContactDTO(user.getContact());
            }

            // address, if set
            if (user.getAddress() != null) {
                this.addressDTO = new AddressDTO(user.getAddress());
            }
        }
    }

    private Long id;
    private String username;
    private String name;
    private String surname;
    private String gender;
    private java.time.LocalDate birthDate;

    private boolean enabled;

    private String note;

    private LocalDateTime creationDt;
    private LocalDateTime updatedDt;
    private LocalDateTime loginDt;

    private boolean secured;

    // additional information
    private Set<RoleDTO> roleDTOSet;

    private ContactDTO contactDTO;
    private AddressDTO addressDTO;

}
