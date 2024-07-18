package com.compass.application.user.update.resetpassword;

/**
 * ResetPasswordCommand is a record class that encapsulates the data needed to reset a user's password.
 */
public record ResetPasswordCommand(
        String email,      // The email of the user
        String password    // The new password of the user
) {

    /**
     * Static factory method to create a new ResetPasswordCommand.
     *
     * @param anEmail The email of the user.
     * @param aPassword The new password of the user.
     * @return A new ResetPasswordCommand instance.
     */
    public static ResetPasswordCommand with(final String anEmail, final String aPassword) {
        return new ResetPasswordCommand(anEmail,aPassword);
    }
}
