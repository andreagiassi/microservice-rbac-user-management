package com.giassi.microservice.demo2.rest.users.dtos;

import com.giassi.microservice.demo2.rest.users.entities.Address;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AddressDTOTest {

    @Test
    public void getAddressDTO() {
        Address address = new Address();
        address.setCity("Trieste");
        address.setCountry("Italy");
        address.setZipCode("34100");

        AddressDTO addressDTO = new AddressDTO(address);
        assertEquals("Trieste", addressDTO.getCity());
        assertEquals("Italy", addressDTO.getCountry());
        assertEquals("34100", addressDTO.getZipCode());
    }

}
