package com.compass.application.user.login;

public record LoginUserCommand(String email, String password) {
    public static LoginUserCommand with(final String email, final String password) {
        return new LoginUserCommand(email, password);
    }
}
