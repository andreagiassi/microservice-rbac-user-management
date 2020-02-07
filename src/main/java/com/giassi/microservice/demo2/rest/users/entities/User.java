package com.giassi.microservice.demo2.rest.users.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="id")
    private Long id;

    @Column(name="username", nullable = false)
    private String username;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="surname", nullable = false)
    private String surname;

    @Column(name="email", nullable = false)
    private String email;

    @Basic
    private java.time.LocalDateTime creationDt;

    @Basic
    private java.time.LocalDateTime updatedDt;

    @Enumerated
    @Column(columnDefinition = "tinyint")
    private Gender gender;

    @Column(name="enabled")
    private boolean enabled;

    @Column(name="phone", nullable = true)
    private String phone;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="role_id")
    private Role role;

}
