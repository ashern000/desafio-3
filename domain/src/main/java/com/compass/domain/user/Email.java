package com.compass.domain.user;

import com.compass.domain.ValueObject;
import com.compass.domain.validation.Error;
import com.compass.domain.validation.ValidationHandler;

import java.util.Objects;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Email class represents an email value object in the domain.
 * It extends ValueObject.
 */
public class Email extends ValueObject {
    // Field representing the email
    private final String email;

    // Regular expression pattern for email validation
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
    );

    /**
     * Private constructor for the Email class.
     *
     * @param email The email string.
     */
    private Email(final String email) {
        this.email = email;
    }

    /**
     * Static factory method to create a new email.
     *
     * @param email The email string.
     * @return A new Email instance.
     */
    public static Email newEmail(final String email) {
        return new Email(email);
    }

    /**
     * Validates the email using the provided validation handler.
     *
     * @param handler The validation handler.
     */
    public void validate(ValidationHandler handler) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()) {
            handler.append(new Error("Email invalido"));
        }
    }

    /**
     * Method to get the email value.
     *
     * @return The email string.
     */
    public String getValue() {
        return email;
    }
}
