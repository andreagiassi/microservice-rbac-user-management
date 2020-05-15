package com.giassi.microservice.demo2.rest.users.services.validation;

import com.giassi.microservice.demo2.rest.users.exceptions.InvalidUserDataException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// Test the phone validator: message must to be ITU-T E.123 compliant
public class PhoneValidatorTest {

    private PhoneValidator phoneValidator = new PhoneValidator();

    // phone cannot be null or empty
    @Test
    public void given_nullPhone_whenCheckPhone_thenInvalidDataException() {
        Throwable exception = assertThrows(InvalidUserDataException.class, () -> {
            phoneValidator.checkPhone(null);
        });

        assertEquals(exception.getMessage(), "The phone cannot be null or empty");
    }

    // phone cannot contains letters
    @Test
    public void given_wrongPhone_whenCheckPhone_thenInvalidDataException() {
        String phone = "ab1234";
        Throwable exception = assertThrows(InvalidUserDataException.class, () -> {
            phoneValidator.checkPhone(phone);
        });

        assertEquals(exception.getMessage(), "The phone provided ab1234 is not formal valid");
    }

    // phone number too long
    @Test
    public void given_longPhone_whenCheckPhone_thenInvalidDataException() {
        String phone = "+353 01234567890123456789012345678901234567890123456789";
        Throwable exception = assertThrows(InvalidUserDataException.class, () -> {
            phoneValidator.checkPhone(phone);
        });

        assertEquals(exception.getMessage(), "The phone is too long: max number of chars is 50");
    }

    // valid phone number 1
    @Test
    public void given_validPhone1_whenCheckPhone_thenOK() {
        String phone = "+353 82994455";
        phoneValidator.checkPhone(phone);
    }

    // valid phone number 2 - no spaces
    @Test
    public void given_validPhone2_whenCheckPhone_thenOK() {
        String phone = "+35382994455";
        phoneValidator.checkPhone(phone);
    }

}
