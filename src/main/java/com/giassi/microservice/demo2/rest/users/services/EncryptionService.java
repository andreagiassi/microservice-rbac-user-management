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
 * Provides a set of methods to encrypt or decrypt a String information.
 *
 * See {@http://www.appsdeveloperblog.com/ http://www.appsdeveloperblog.com/encrypt-user-password-example-java/}
 *
 */
public class EncryptionService {

    public static final String ALGORITHM = "PBKDF2WithHmacSHA1";

    // demo salt for testing or first setup purpose
    public static final String DEFAULT_SALT = "WZeBXmCI9cAz3LyY9Sdllj9l5iPsXC";

    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    public static String generateSalt(int length) {
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
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    public static String encrypt(String password, String salt) {
        if (salt == null) {
            throw new RuntimeException("Invalid salt - Wrong configuration");
        }
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());
        return Base64.getEncoder().encodeToString(securePassword);
    }

    public static boolean isPasswordValid(String providedPassword,
                                          String securedPassword, String salt)
    {
        // Generate new secure password with the same salt
        String newSecurePassword = encrypt(providedPassword, salt);
        // Check if the passwords are equal
        return newSecurePassword.equalsIgnoreCase(securedPassword);
    }

}
