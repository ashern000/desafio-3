package com.compass.domain.user;

import com.compass.domain.ValueObject;
import com.compass.domain.validation.Error;
import com.compass.domain.validation.ValidationHandler;

import java.util.Objects;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Password class represents a password value object in the domain.
 * It extends ValueObject.
 */
public class Password extends ValueObject {
    // Field representing the password
    private final String password;

    // Regular expression pattern for password validation
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"
    );

    /**
     * Private constructor for the Password class.
     *
     * @param password The password string.
     */
    private Password(final String password) {
        this.password = password;
    }

    /**
     * Static factory method to create a new password.
     *
     * @param password The password string.
     * @return A new Password instance.
     */
    public static Password newPassword(final String password) {
        return new Password(password);
    }

    /**
     * Static factory method to create a password with a specific value.
     *
     * @param password The password string.
     * @return A Password instance.
     */
    public static Password with(final String password) {
        return new Password(password);
    }

    /**
     * Validates the password using the provided validation handler.
     *
     * @param handler The validation handler.
     */
    public void validate(ValidationHandler handler) {
        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        if (!matcher.matches()) {
            handler.append(new Error("Senha inválida"));
        }
    }

    /**
     * Method to get the password value.
     *
     * @return The password string.
     */
    public String getValue() {
        return password;
    }
}
