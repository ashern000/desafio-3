package com.compass.domain.product;

import com.compass.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class ProductID extends Identifier {
    private final String value;

    private ProductID(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static ProductID unique() {
        return ProductID.from(UUID.randomUUID().toString().toLowerCase());
    }

    public static ProductID from(final String anId) {
        return new ProductID(anId);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ProductID productID = (ProductID) o;
        return Objects.equals(getValue(), productID.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}
