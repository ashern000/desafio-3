package com.compass.domain.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * UserRole is an enumeration representing the different roles a User can have.
 * Currently, there are two roles: USER and ADMIN.
 */
public enum UserRole {
    USER_ROLE("USER"),
    ADMIN_ROLE("ADMIN");

    // Field representing the role
    private final String role;

    /**
     * Constructor for the UserRole enumeration.
     *
     * @param role The role as a string.
     */
    UserRole(final String role) {
        this.role = role;
    }

    /**
     * Method to get the role.
     *
     * @return The role as a string.
     */
    public String getRole() {
        return role;
    }

    /**
     * Static method to get a UserRole from a string.
     *
     * @param roleUser The role as a string.
     * @return An Optional that can contain the UserRole if found.
     */
    public static Optional<UserRole> of(final String roleUser) {
        return Arrays.stream(UserRole.values()).filter(it -> it.role.equalsIgnoreCase(roleUser)).findFirst();
    }
}

