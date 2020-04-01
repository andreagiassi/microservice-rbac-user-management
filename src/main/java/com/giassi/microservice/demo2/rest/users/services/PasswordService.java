package com.giassi.microservice.demo2.rest.users.services;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

/**
 * Provides a set of methods to encrypt or decrypt a password.
 *
 * See {@http://www.appsdeveloperblog.com/ http://www.appsdeveloperblog.com/encrypt-user-password-example-java/}
 *
 */
public class PasswordService {

    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    private static final String DEFAULT_SALT = "WZeBXmCI9cAz3LyY9Sdllj9l5iPsXC";

    public static String getSalt(int length) {
        StringBuilder saltStr = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            saltStr.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(saltStr);
    }

    public static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    public static String generatePassword(String password, String salt) {
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());
        return Base64.getEncoder().encodeToString(securePassword);
    }

    public static boolean verifyPassword(String providedPassword,
                                         String securedPassword, String salt)
    {
        // Generate new secure password with the same salt
        String newSecurePassword = generatePassword(providedPassword, salt);
        // Check if the passwords are equal
        return newSecurePassword.equalsIgnoreCase(securedPassword);
    }

    public static boolean verifyPassword(String providedPassword, String securedPassword) {
        return verifyPassword(providedPassword, securedPassword, DEFAULT_SALT);
    }

}
