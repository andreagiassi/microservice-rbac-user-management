package com.giassi.microservice.demo2.rest.users.dtos;

import com.giassi.microservice.demo2.rest.users.entities.Address;
import lombok.Data;

import java.io.Serializable;

@Data
public class AddressDTO implements Serializable {

    public AddressDTO() {
        // empty constructor
    }

    public AddressDTO(Address address) {
        if (address != null) {
            this.address = address.getAddress();
            this.address2 = address.getAddress2();
            this.city = address.getCity();
            this.country = address.getCountry();
            this.zipCode = address.getZipCode();
        }
    }

    private String address;
    private String address2;
    private String city;
    private String country;
    private String zipCode;

}
