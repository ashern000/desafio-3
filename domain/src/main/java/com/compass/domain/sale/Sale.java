package com.compass.domain.sale;

import com.compass.domain.AggregateRoot;
import com.compass.domain.product.Product;
import com.compass.domain.product.ProductID;
import com.compass.domain.user.UserID;
import com.compass.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Sale class represents a sale entity in the domain.
 * It extends AggregateRoot and implements Cloneable.
 */
public class Sale extends AggregateRoot<SaleID> implements Cloneable {

    // Fields representing sale properties
    private List<ProductID> products;
    private UserID userID;
    private double totalPrice;
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
                 final UserID userID,
                 final double totalPrice,
                 final Instant aCreatedAt,
                 final Instant aUpdatedAt) {
        super(anId);
        this.userID = userID;
        this.products = aProducts;
        this.totalPrice = totalPrice;
        this.createdAt = Objects.requireNonNull(aCreatedAt, "'createdAt' should not be null");
        this.updatedAt = Objects.requireNonNull(aUpdatedAt, "'updatedAt' should not be null");
    }

    /**
     * Static factory method to create a new sale.
     *
     * @param aProducts   The list of products in the sale.
     * @return A new Sale instance.
     */
    public static Sale newSale(final List<ProductID> aProducts, UserID anUser, final double totalPrice) {
        final var anId = SaleID.unique();
        final var now = Instant.now();
        return new Sale(anId,aProducts,anUser,totalPrice, now, now);
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
    public static Sale with(final SaleID anId, final List<ProductID> aProducts,final UserID anUser,final double
                            totalPrice,final Instant createdAt, final Instant updatedAt) {
        return new Sale(anId, aProducts,anUser,totalPrice, createdAt, updatedAt);
    }

    /**
     * Static factory method to create a sale from an existing sale.
     *
     * @param aSale The existing sale instance.
     * @return A new Sale instance.
     */
    public static Sale with(final Sale aSale) {
        return with(aSale.getId(), aSale.getProductsIds(),aSale.getUserId(),aSale.getTotalPrice(), aSale.getCreatedAt(), aSale.getUpdatedAt());
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
        return Collections.unmodifiableList(products);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public UserID getUserId() {
        return userID;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setProductIds(List<ProductID> products) {this.products = products; }

    public double getTotalPrice() { return totalPrice; }

    /**
     * Updates the sale properties.
     *
     * @param aProducts   The new list of products in the sale.
     * @return The current Sale instance.
     */
    public Sale update(final List<ProductID> aProducts, final double totalPrice) {
        this.products = aProducts;
        this.updatedAt = Instant.now();
        this.totalPrice = totalPrice;
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
