package com.giassi.microservice.demo2.rest.users.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="addresses")
@Data
public class Address {

    @Id
    @Column(name="user_id")
    private Long userId;

    @Column(name="address")
    private String address;

    @Column(name="address2")
    private String address2;

    @Column(name="city")
    private String city;

    @Column(name="country")
    private String country;

    @Column(name="zip_code")
    private String zipCode;

    @OneToOne
    @MapsId
    private User user;

}
