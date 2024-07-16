package com.compass.domain.user;

import com.compass.domain.ValueObject;
import com.compass.domain.validation.Error;
import com.compass.domain.validation.ValidationHandler;

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
    }

    public static Password newPassword(final String password) {
        return new Password(password);
    }

    public static Password with(final String password) {
        return new Password(password);
    }

    public void validate(ValidationHandler handler) {
        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        if (!matcher.matches()) {
            handler.append(new Error("Senha inválida"));
        }
    }

    public String getPassword() {
        return password;
    }
}

