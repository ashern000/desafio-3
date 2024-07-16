package com.compass.application.user.create;

public record CreateUserCommand(
        String name,
        String email,
        String password,
        boolean active,
        String role
) {

    public static CreateUserCommand with(final String aName, final String anEmail, final String aPassword, final boolean anActive, final String aRole) {
        return new CreateUserCommand(aName,anEmail,aPassword,anActive,aRole);
    }
}
