package com.compass.domain.product;

import com.compass.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

/**
 * ProductID class represents a unique identifier for a Product.
 */
public class ProductID extends Identifier {
    private final String value;

    /**
     * Private constructor for ProductID.
     *
     * @param value The unique identifier value.
     */
    private ProductID(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    /**
     * Generates a unique ProductID.
     *
     * @return A new ProductID instance with a unique value.
     */
    public static ProductID unique() {
        return ProductID.from(UUID.randomUUID().toString().toLowerCase());
    }

    /**
     * Creates a ProductID from the given string value.
     *
     * @param anId The string value of the identifier.
     * @return A new ProductID instance.
     */
    public static ProductID from(final String anId) {
        return new ProductID(anId);
    }

    /**
     * Gets the value of the ProductID.
     *
     * @return The value of the identifier.
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * Checks if this ProductID is equal to another object.
     *
     * @param o The object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ProductID productID = (ProductID) o;
        return Objects.equals(getValue(), productID.getValue());
    }

    /**
     * Computes the hash code for this ProductID.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}
