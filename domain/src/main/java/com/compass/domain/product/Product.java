package com.compass.domain.product;

import com.compass.domain.AggregateRoot;
import com.compass.domain.validation.ValidationHandler;

import java.time.Instant;

public class Product extends AggregateRoot<ProductID> {
    private String name;
    private boolean active;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Product(final ProductID anId,
                    final String aName,
                    final String aDescription,
                    final boolean isActive,
                    final Instant aCreatedAt,
                    final Instant aUpdatedAt,
                    final Instant aDeletedAt
                    ) {
        super(anId);
        this.name = aName;
        this.description = aDescription;
        this.active = isActive;
        this.createdAt = aCreatedAt;
        this.updatedAt = aUpdatedAt;
        this.deletedAt = aDeletedAt;
    }

    public static Product newProduct(final String aName, final String aDescription, final boolean isActive) {
        final var anId = ProductID.unique();
        final var now = Instant.now();
        return new Product(anId, aName, aDescription, isActive, now, now, null);
    }

    public static Product with(final ProductID anId, final String aName, final String aDescription, final boolean isActive, final Instant createdAt, final Instant updatedAt, final Instant deletedAt) {
        return new Product(anId, aName, aDescription, isActive, createdAt, updatedAt, deletedAt);
    }

    public static Product with(final Product aProduct) {
        return with(aProduct.getId(), aProduct.getName(), aProduct.getDescription(), aProduct.isActive(), aProduct.getCreatedAt(), aProduct.getUpdatedAt(), aProduct.getDeletedAt());
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

    public boolean isActive() {
        return active;
    }

    public Product deactive() {
        if(getDeletedAt() == null) {
            this.deletedAt = Instant.now();
        }

        this.active = false;
        this.updatedAt = Instant.now();
        return this;
    }

    public Product activate() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = Instant.now();
        return this;
    }

    public Product update(final String aName, final String aDescription, final boolean isActive) {
        if(isActive) {
            activate();
        } else {
            deactive();
        }

        this.name = aName;
        this.description = aDescription;
        this.updatedAt = Instant.now();
        return this;

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
