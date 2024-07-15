package com.compass.domain.user;


import com.compass.domain.ValueObject;
import com.compass.domain.exceptions.DomainException;
import com.compass.domain.validation.Error;

import java.util.Objects;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Password extends ValueObject {
    private final String password;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"
    );

    private Password(final String password) {
        this.password = Objects.requireNonNull(password);
        this.validate();
    }

    private void validate() {
        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        if (!matcher.matches()) {
            throw DomainException.with(new Error("Senha inválida"));
        }
    }
}
