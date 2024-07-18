package com.compass.application.user.login;

/**
 * LoginUserCommand is a record class that encapsulates the data needed to log in a user.
 */
public record LoginUserCommand(
        String email,      // The email of the user
        String password    // The password of the user
) {

    /**
     * Static factory method to create a new LoginUserCommand.
     *
     * @param anEmail The email of the user.
     * @param aPassword The password of the user.
     * @return A new LoginUserCommand instance.
     */
    public static LoginUserCommand with(final String anEmail, final String aPassword) {
        return new LoginUserCommand(anEmail,aPassword);
    }
}

