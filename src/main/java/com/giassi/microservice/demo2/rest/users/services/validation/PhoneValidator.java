package com.giassi.microservice.demo2.rest.users.services.validation;

import com.giassi.microservice.demo2.rest.users.exceptions.InvalidUserDataException;
import com.google.common.base.Strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Validates international phone numbers based on industry-standard notation specified by ITU-T E.123
// https://howtodoinjava.com/regex/java-regex-validate-international-phone-numbers/
public class PhoneValidator {

    private static int MAX_PHONE_LENGTH = 50;

    private static final String PHONE_REGEX = "^\\+(?:[0-9] ?){6,14}[0-9]$";

    private Pattern pattern;

    public PhoneValidator() {
        pattern = Pattern.compile(PHONE_REGEX);
    }

    public void checkPhone(String phone) {
        if (Strings.isNullOrEmpty(phone)) {
            throw new InvalidUserDataException("The phone cannot be null or empty");
        }

        // check max phone length
        if (phone.length() > MAX_PHONE_LENGTH) {
            throw new InvalidUserDataException(String.format("The phone is too long: max number of chars is %s",
                    MAX_PHONE_LENGTH));
        }

        Matcher matcher = pattern.matcher(phone);
        if (!matcher.matches()) {
            throw new InvalidUserDataException(String.format("The phone provided %s is not formal valid", phone));
        }
    }

}
