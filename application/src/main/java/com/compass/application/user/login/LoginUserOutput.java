package com.compass.application.user.login;

import java.io.Serializable;

public record LoginUserOutput(String id, String email, String token) implements Serializable {
    private static final long serialVersionUID = 1L;
    public static LoginUserOutput from(final String id, final String email, final String token) {
        return new LoginUserOutput(id, email,token);
    }
}
