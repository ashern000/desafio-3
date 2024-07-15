package com.compass.domain.user;

import com.compass.domain.Identifier;
import com.compass.domain.sale.SaleID;

import java.util.Objects;
import java.util.UUID;

public class UserID extends Identifier {
    private final String value;

    /**
     * Private constructor for UserID.
     *
     * @param value The unique identifier value.
     */
    private UserID(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    /**
     * Generates a unique UserID.
     *
     * @return A new UserID instance with a unique value.
     */
    public static UserID unique() {
        return UserID.from(UUID.randomUUID().toString().toLowerCase());
    }

    /**
     * Creates a UserID from the given string value.
     *
     * @param anId The string value of the identifier.
     * @return A new UserID instance.
     */
    public static UserID from(final String anId) {
        return new UserID(anId);
    }

    /**
     * Gets the value of the UserID.
     *
     * @return The value of the identifier.
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * Checks if this UserID is equal to another object.
     *
     * @param o The object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UserID userID = (UserID) o;
        return Objects.equals(getValue(), userID.getValue());
    }

    /**
     * Computes the hash code for this UserID.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}
