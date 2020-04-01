package com.giassi.microservice.demo2.rest.users.services;

import com.giassi.microservice.demo2.rest.users.exceptions.InvalidUserDataException;
import org.junit.Before;
import org.junit.Test;

public class PasswordValidatorTest {

    private PasswordValidator passwordValidator;

    @Before
    public void initTest() {
        passwordValidator = new PasswordValidator();
    }

    @Test(expected = InvalidUserDataException.class)
    public void given_null_password_when_checkPassword_throw_InvalidUserDataException() {
        String password = null;
        passwordValidator.checkPassword(password);
    }

    @Test(expected = InvalidUserDataException.class)
    public void given_empty_password_when_checkPassword_throw_InvalidUserDataException() {
        String password = "";
        passwordValidator.checkPassword(password);
    }

    @Test(expected = InvalidUserDataException.class)
    public void given_too_long_password_when_checkPassword_throw_InvalidUserDataException() {
        String password = "01234567890123456789012345678901234567890123456789012345678901234567890123456789";
        passwordValidator.checkPassword(password);
    }

    @Test(expected = InvalidUserDataException.class)
    public void given_not_valid_password_when_checkPassword_throw_InvalidUserDataException() {
        String password = "aaaa asdasd1234";
        passwordValidator.checkPassword(password);
    }

    // 1 number, 1 upper case, 1 lower case letter, 1 special char
    @Test(expected = InvalidUserDataException.class)
    public void given_invalid_password_less_than_8_chars_when_checkPassword_then_OK() {
        String password = "Andrea";
        passwordValidator.checkPassword(password);
    }

    @Test(expected = InvalidUserDataException.class)
    public void given_invalid_password_with_spaces_when_checkPassword_then_OK() {
        String password = "Andrea test";
        passwordValidator.checkPassword(password);
    }

    @Test(expected = InvalidUserDataException.class)
    public void given_invalid_password_no_uppercase_letter_when_checkPassword_then_OK() {
        String password = "andrea!123";
        passwordValidator.checkPassword(password);
    }

    @Test(expected = InvalidUserDataException.class)
    public void given_invalid_password_no_lowercase_letter_when_checkPassword_then_OK() {
        String password = "ANDREA!123";
        passwordValidator.checkPassword(password);
    }

    @Test(expected = InvalidUserDataException.class)
    public void given_invalid_password_no_spacial_chars_when_checkPassword_then_OK() {
        String password = "ANDREA123";
        passwordValidator.checkPassword(password);
    }

    @Test
    public void given_valid_password_when_checkPassword_then_OK() {
        String password = "Andrea!123";
        passwordValidator.checkPassword(password);
    }

}
