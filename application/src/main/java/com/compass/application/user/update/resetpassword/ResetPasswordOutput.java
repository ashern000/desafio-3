package com.compass.application.user.update.resetpassword;

import com.compass.domain.user.User;

import java.io.Serializable;

public record ResetPasswordOutput(String id, String name, String email) implements Serializable {
    private static final long serialVersionUID = 1L;

    public static ResetPasswordOutput from(final String id, final String name, final String email) {
        return new ResetPasswordOutput(id, name, email);
    }

    public static ResetPasswordOutput from(final User anUser) {
        return new ResetPasswordOutput(anUser.getId().getValue(), anUser.getName(), anUser.getEmail().getValue());
    }
}
