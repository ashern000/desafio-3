package com.compass.application.user.create;

/**
 * CreateUserCommand is a record class that encapsulates the data needed to create a new user.
 */
public record CreateUserCommand(
        String name,       // The name of the user
        String email,      // The email of the user
        String password,   // The password of the user
        boolean active,    // The active status of the user
        String role        // The role of the user
) {

    /**
     * Static factory method to create a new CreateUserCommand.
     *
     * @param aName The name of the user.
     * @param anEmail The email of the user.
     * @param aPassword The password of the user.
     * @param anActive The active status of the user.
     * @param aRole The role of the user.
     * @return A new CreateUserCommand instance.
     */
    public static CreateUserCommand with(final String aName, final String anEmail, final String aPassword, final boolean anActive, final String aRole) {
        return new CreateUserCommand(aName,anEmail,aPassword,anActive,aRole);
    }
}

