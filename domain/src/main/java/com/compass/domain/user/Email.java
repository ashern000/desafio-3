package com.compass.domain.user;

import com.compass.domain.ValueObject;
import com.compass.domain.validation.Error;
import com.compass.domain.validation.ValidationHandler;

import java.util.Objects;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Email extends ValueObject {
    private final String email;
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
    );

    private Email(final String email) {
        this.email = Objects.requireNonNull(email);
    }

    public static Email newEmail(final String email) {
        return new Email(email);
    }

    public void validate(ValidationHandler handler) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()) {
            handler.append(new Error("Email invalido"));
        }
    }

    public String getValue() {
        return email;
    }
}
