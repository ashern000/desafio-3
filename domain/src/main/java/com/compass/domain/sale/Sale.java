package com.compass.domain.sale;

import com.compass.domain.AggregateRoot;
import com.compass.domain.product.Product;
import com.compass.domain.product.ProductID;
import com.compass.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * Sale class represents a sale entity in the domain.
 * It extends AggregateRoot and implements Cloneable.
 */
public class Sale extends AggregateRoot<SaleID> implements Cloneable {

    // Fields representing sale properties
    private List<ProductID> products;
    private Instant createdAt;
    private Instant updatedAt;

    /**
     * Private constructor for the Sale class.
     *
     * @param anId        The unique identifier for the sale.
     * @param aProducts   The list of products in the sale.
     * @param aCreatedAt  The timestamp when the sale was created.
     * @param aUpdatedAt  The timestamp when the sale was last updated.
     */
    private Sale(final SaleID anId,
                 final List<ProductID> aProducts,
                 final Instant aCreatedAt,
                 final Instant aUpdatedAt) {
        super(anId);
        this.products = aProducts;
        this.createdAt = Objects.requireNonNull(aCreatedAt, "'createdAt' should not be null");
        this.updatedAt = Objects.requireNonNull(aUpdatedAt, "'updatedAt' should not be null");
    }

    /**
     * Static factory method to create a new sale.
     *
     * @param aProducts   The list of products in the sale.
     * @return A new Sale instance.
     */
    public static Sale newSale(final List<ProductID> aProducts) {
        final var anId = SaleID.unique();
        final var now = Instant.now();
        return new Sale(anId, aProducts, now, now);
    }

    /**
     * Static factory method to create a sale with specific attributes.
     *
     * @param anId        The unique identifier for the sale.
     * @param aProducts   The list of products in the sale.
     * @param createdAt   The timestamp when the sale was created.
     * @param updatedAt   The timestamp when the sale was last updated.
     * @return A Sale instance.
     */
    public static Sale with(final SaleID anId, final List<ProductID> aProducts, final Instant createdAt, final Instant updatedAt) {
        return new Sale(anId, aProducts, createdAt, updatedAt);
    }

    /**
     * Static factory method to create a sale from an existing sale.
     *
     * @param aSale The existing sale instance.
     * @return A new Sale instance.
     */
    public static Sale with(final Sale aSale) {
        return with(aSale.getId(), aSale.getProductsIds(), aSale.getCreatedAt(), aSale.getUpdatedAt());
    }

    /**
     * Validates the sale using the provided validation handler.
     *
     * @param handler The validation handler.
     */
    public void validate(ValidationHandler handler) {
        new SaleValidator(this, handler).validate();
    }

    // Getters for the sale properties

    public List<ProductID> getProductsIds() {
        return products;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Updates the sale properties.
     *
     * @param aProducts   The new list of products in the sale.
     * @return The current Sale instance.
     */
    public Sale update(final List<ProductID> aProducts) {
        this.products = aProducts;
        this.updatedAt = Instant.now();
        return this;
    }

    /**
     * Creates and returns a copy of this sale.
     *
     * @return A clone of this sale.
     */
    @Override
    public Sale clone() {
        try {
            return (Sale) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
