package com.compass.domain.user;

import com.compass.domain.ValueObject;
import com.compass.domain.exceptions.DomainException;
import com.compass.domain.validation.Error;

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
        this.validate();
    }

    public static Email newEmail(final String email) {
        return new Email(email);
    }

    private void validate() {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()) {
            throw DomainException.with(new Error("Email inválido"));
        }
    }
}
