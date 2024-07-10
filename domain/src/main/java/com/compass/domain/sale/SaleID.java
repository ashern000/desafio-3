package com.compass.domain.sale;

import com.compass.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

/**
 * SaleID class represents a unique identifier for a Sale.
 */
public class SaleID extends Identifier {
    private final String value;

    /**
     * Private constructor for SaleID.
     *
     * @param value The unique identifier value.
     */
    private SaleID(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    /**
     * Generates a unique SaleID.
     *
     * @return A new SaleID instance with a unique value.
     */
    public static SaleID unique() {
        return SaleID.from(UUID.randomUUID().toString().toLowerCase());
    }

    /**
     * Creates a SaleID from the given string value.
     *
     * @param anId The string value of the identifier.
     * @return A new SaleID instance.
     */
    public static SaleID from(final String anId) {
        return new SaleID(anId);
    }

    /**
     * Gets the value of the SaleID.
     *
     * @return The value of the identifier.
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * Checks if this SaleID is equal to another object.
     *
     * @param o The object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final SaleID saleID = (SaleID) o;
        return Objects.equals(getValue(), saleID.getValue());
    }

    /**
     * Computes the hash code for this SaleID.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}
