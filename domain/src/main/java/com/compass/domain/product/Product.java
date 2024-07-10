package com.compass.domain.product;

import com.compass.domain.AggregateRoot;
import com.compass.domain.validation.ValidationHandler;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * Product class represents a product entity in the domain.
 * It extends AggregateRoot and implements Cloneable.
 */
public class Product extends AggregateRoot<ProductID> implements Cloneable, Serializable {

    // Fields representing product properties
    private String name;
    private boolean active;
    private String description;
    private double price;
    private int quantity;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    /**
     * Private constructor for the Product class.
     *
     * @param anId        The unique identifier for the product.
     * @param aName       The name of the product.
     * @param aDescription The description of the product.
     * @param isActive    The active status of the product.
     * @param aPrice      The price of the product.
     * @param aCreatedAt  The timestamp when the product was created.
     * @param aUpdatedAt  The timestamp when the product was last updated.
     * @param aDeletedAt  The timestamp when the product was deleted.
     */
    private Product(final ProductID anId,
                    final String aName,
                    final String aDescription,
                    final boolean isActive,
                    final double aPrice,
                    final int aQuantity,
                    final Instant aCreatedAt,
                    final Instant aUpdatedAt,
                    final Instant aDeletedAt) {
        super(anId);
        this.name = aName;
        this.description = aDescription;
        this.active = isActive;
        this.price = aPrice;
        this.quantity = aQuantity;
        this.createdAt = Objects.requireNonNull(aCreatedAt, "'createdAt' should not be null");
        this.updatedAt = Objects.requireNonNull(aUpdatedAt, "'updatedAt' should not be null");
        this.deletedAt = aDeletedAt;
    }

    /**
     * Static factory method to create a new product.
     *
     * @param aName       The name of the product.
     * @param aDescription The description of the product.
     * @param isActive    The active status of the product.
     * @param aPrice      The price of the product.
     * @return A new Product instance.
     */
    public static Product newProduct(final String aName, final String aDescription, final boolean isActive, final double aPrice, final int aQuantity) {
        final var anId = ProductID.unique();
        final var now = Instant.now();
        return new Product(anId, aName, aDescription, isActive, aPrice,aQuantity, now, now, null);
    }

    /**
     * Static factory method to create a product with specific attributes.
     *
     * @param anId        The unique identifier for the product.
     * @param aName       The name of the product.
     * @param aDescription The description of the product.
     * @param isActive    The active status of the product.
     * @param aPrice      The price of the product.
     * @param createdAt   The timestamp when the product was created.
     * @param updatedAt   The timestamp when the product was last updated.
     * @param deletedAt   The timestamp when the product was deleted.
     * @return A Product instance.
     */
    public static Product with(final ProductID anId, final String aName, final String aDescription, final boolean isActive, final double aPrice, final int aQuantity,final Instant createdAt, final Instant updatedAt, final Instant deletedAt) {
        return new Product(anId, aName, aDescription, isActive, aPrice, aQuantity,createdAt, updatedAt, deletedAt);
    }

    /**
     * Static factory method to create a product from an existing product.
     *
     * @param aProduct The existing product instance.
     * @return A new Product instance.
     */
    public static Product with(final Product aProduct) {
        return with(aProduct.getId(), aProduct.getName(), aProduct.getDescription(), aProduct.isActive(), aProduct.getPrice(), aProduct.getQuantity(),aProduct.getCreatedAt(), aProduct.getUpdatedAt(), aProduct.getDeletedAt());
    }

    /**
     * Validates the product using the provided validation handler.
     *
     * @param handler The validation handler.
     */
    public void validate(ValidationHandler handler) {
        new ProductValidator(this, handler).validate();
    }

    // Getters for the product properties

    public String getName() {
        return name;
    }

    public int getQuantity() { return quantity;}

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public boolean isActive() {
        return active;
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

    /**
     * Deactivates the product.
     *
     * @return The current Product instance.
     */
    public Product deactivate() {
        if (getDeletedAt() == null) {
            this.deletedAt = Instant.now();
        }
        this.active = false;
        this.updatedAt = Instant.now();
        return this;
    }

    /**
     * Activates the product.
     *
     * @return The current Product instance.
     */
    public Product activate() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = Instant.now();
        return this;
    }

    /**
     * Updates the product properties.
     *
     * @param aName        The new name of the product.
     * @param aDescription The new description of the product.
     * @param isActive     The new active status of the product.
     * @return The current Product instance.
     */
    public Product update(final String aName, final String aDescription, final boolean isActive, final double aPrice, final int aQuantity) {
        if (isActive) {
            activate();
        } else {
            deactivate();
        }
        this.name = aName;
        this.description = aDescription;
        this.price = aPrice;
        this.quantity = aQuantity;
        this.updatedAt = Instant.now();
        return this;
    }

    /**
     * Creates and returns a copy of this product.
     *
     * @return A clone of this product.
     */
    @Override
    public Product clone() {
        try {
            return (Product) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
