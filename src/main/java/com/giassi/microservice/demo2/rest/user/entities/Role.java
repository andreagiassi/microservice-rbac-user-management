package com.giassi.microservice.demo2.rest.user.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="roles")
@Data
public class Role {

    public static long USER = 1;
    public static long ADMINISTRATOR = 2;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="id")
    private Long id;

    @Column(name="role", nullable = false)
    private String role;

}
