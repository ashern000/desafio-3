package com.compass.domain.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public enum UserRole {
    USER_ROLE("USER"),
    ADMIN_ROLE("ADMIN");

    private final String role;

    UserRole(final String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public static Optional<UserRole> of(final String roleUser) {
        return Arrays.stream(UserRole.values()).filter(it -> it.role.equalsIgnoreCase(roleUser)).findFirst();
    }

}
