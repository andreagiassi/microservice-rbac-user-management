package com.giassi.microservice.demo2.rest.users.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="contacts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Contact {

    @Id
    @Column(name="user_id")
    private Long userId;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="phone")
    private String phone;

    @OneToOne
    @MapsId
    private User user;

}
