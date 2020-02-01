package com.giassi.microservice.demo2.rest.user;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="users")
public class User {

    @Id
    @Column(name="id")
    private Long Id;

    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

}
