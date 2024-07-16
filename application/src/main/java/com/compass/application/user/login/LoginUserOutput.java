package com.compass.application.user.login;

public record LoginUserOutput(String email, String password, String token) {
    public static LoginUserOutput from(final String email, final String password, final String token) {
        return new LoginUserOutput(email,password,token);
    }
}
