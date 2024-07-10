package com.compass.domain.product;

import com.compass.domain.validation.Error;
import com.compass.domain.validation.ValidationHandler;
import com.compass.domain.validation.Validator;

/**
 * ProductValidator class is responsible for validating the Product entity.
 */
public class ProductValidator extends Validator {

    private final Product product;
    private static final int MIN_LENGTH_PROD = 2;
    private static final int MAX_LENGTH_PROD = 255;
    private static final int MIN_LENGTH_PROD_DESCRIPTION = 1;
    private static final int MAX_LENGTH_PROD_DESCRIPTION = 1250;

    /**
     * Constructor for ProductValidator.
     *
     * @param aProduct           The product to be validated.
     * @param aValidationHandler The handler for managing validation errors.
     */
    public ProductValidator(Product aProduct, ValidationHandler aValidationHandler) {
        super(aValidationHandler);
        this.product = aProduct;
    }

    /**
     * Validates the product by checking its name and description.
     */
    @Override
    public void validate() {
        checkName();
        checkDescription();
        checkPrice();
    }

    /**
     * Checks the validity of the product name.
     */
    private void checkName() {
        final var name = this.product.getName();

        if (name == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
            return;
        }
        if (name.isBlank()) {
            this.validationHandler().append(new Error("'name' should not be empty"));
            return;
        }

        if (name.trim().isEmpty()) {
            this.validationHandler().append(new Error("'name' should not be empty"));
            return;
        }

        if (name.length() < MIN_LENGTH_PROD || name.length() > MAX_LENGTH_PROD) {
            this.validationHandler().append(new Error("'name' must be between 3 and 255 characters"));
        }
    }

    /**
     * Checks the validity of the product description.
     */
    private void checkDescription() {
        final String description = this.product.getDescription();

        if (description == null) {
            this.validationHandler().append(new Error("'description' should not be null"));
            return;
        }
        if (description.isBlank()) {
            this.validationHandler().append(new Error("'description' should not be null"));
            return;
        }

        if (description.isEmpty()) {
            this.validationHandler().append(new Error("'description' should not be empty"));
            return;
        }

        if (description.trim().length() < MIN_LENGTH_PROD_DESCRIPTION || description.trim().length() > MAX_LENGTH_PROD_DESCRIPTION) {
            this.validationHandler().append(new Error("'description' must be between 1 and 1250 characters"));
        }
    }

    private void checkPrice() {
        double price = this.product.getPrice();

        if (price <= 0) {
            this.validationHandler().append(new Error("'price' must be greater than zero"));
        }
    }

    private void checkQuantity() {
        int quantity = this.product.getQuantity();

        if (quantity < 0) {
            this.validationHandler().append(new Error("'quantity' must be positive number"));
        }
    }
}
