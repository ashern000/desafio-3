package com.compass.application.user.create;

import com.compass.domain.user.User;

public record CreateUserOutput(
        String id,
        String name,
        String email,
        boolean active
) {
    public static CreateUserOutput from(
            final String id,
            final String name,
            final String email,
            final boolean active
    ) {
        return new CreateUserOutput(id, name, email, active);
    }

    public static CreateUserOutput from (
            final User anUser
    ) {
        return new CreateUserOutput(
                anUser.getId().getValue(),
                anUser.getName(),
                anUser.getEmail().getValue(),
                anUser.isActive()
        );
    }
}
