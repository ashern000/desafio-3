package com.compass.application.user.update.resetpassword;

public record ResetPasswordCommand(
        String email,
        String password
) {
    public static ResetPasswordCommand with(final String email, final String password) {
        return new ResetPasswordCommand(email, password);
    }
}
