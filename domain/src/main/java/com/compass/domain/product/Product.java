package com.compass.domain.product;

import com.compass.domain.AggregateRoot;
import com.compass.domain.validation.ValidationHandler;

import java.time.Instant;

public class Product extends AggregateRoot<ProductID> {
    private String name;
    private String description;
    private double quantity;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Product(final ProductID anId,
                    final String aName,
                    final String aDescription,
                    final double aQuantity,
                    final Instant aCreatedAt,
                    final Instant aUpdatedAt,
                    final Instant aDeletedAt
                    ) {
        super(anId);
        this.name = aName;
        this.description = aDescription;
        this.quantity = aQuantity;
        this.createdAt = aCreatedAt;
        this.updatedAt = aUpdatedAt;
        this.deletedAt = aDeletedAt;
    }

    public static Product newProduct(final String aName, final String aDescription, final double aQuantity) {
        final var anId = ProductID.unique();
        final var now = Instant.now();
        return new Product(anId, aName, aDescription, aQuantity, now, now, null);
    }

    public void validate(ValidationHandler handler) {
        new ProductValidator(this, handler).validate();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getQuantity() {
        return quantity;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }
}
