package com.giassi.microservice.demo2.rest.users.dtos;

import com.giassi.microservice.demo2.rest.users.entities.Address;
import lombok.Data;

@Data
public class AddressDTO {

    public AddressDTO(Address address) {
        if (address != null) {
            this.address = address.getAddress();
            this.city = address.getCity();
            this.country = address.getCountry();
            this.zipCode = address.getZipCode();
        }

    }

    private String address;
    private String city;
    private String country;
    private String zipCode;

}
