package com.compass.application.user.login;

public record LoginUserOutput(String id,String email, String password, String token) {
    public static LoginUserOutput from(final String id, final String email, final String password, final String token) {
        return new LoginUserOutput(id, email,password,token);
    }
}
