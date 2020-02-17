package com.giassi.microservice.demo2.rest.users.entities;

import com.giassi.microservice.demo2.rest.users.exceptions.InvalidGenderException;

public enum Gender {

    MALE(1), FEMALE(2);

    private int gender;

    Gender(int gender) {
        this.gender = gender;
    }

    public int getGender() {
        return gender;
    }

    public static Gender getValidGender(String genderName) {
        Gender gender;
        try {
            gender = Gender.valueOf(genderName);
        } catch(IllegalArgumentException ex) {
            throw new InvalidGenderException(String.format("Gender string %s not supported", genderName));
        }
        return gender;
    }

}
