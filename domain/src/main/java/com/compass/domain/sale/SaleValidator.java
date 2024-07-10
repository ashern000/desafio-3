package com.compass.domain.sale;

import com.compass.domain.validation.Error;
import com.compass.domain.validation.ValidationHandler;
import com.compass.domain.validation.Validator;

/**
 * SaleValidator class is responsible for validating the Sale entity.
 */
public class SaleValidator extends Validator {

    private final Sale sale;

    /**
     * Constructor for SaleValidator.
     *
     * @param aSale              The sale to be validated.
     * @param aValidationHandler The handler for managing validation errors.
     */
    public SaleValidator(Sale aSale, ValidationHandler aValidationHandler) {
        super(aValidationHandler);
        this.sale = aSale;
    }

    /**
     * Validates the sale by checking its products.
     */
    @Override
    public void validate() {
        checkProducts();
    }

    /**
     * Checks the validity of the products in the sale.
     */
    private void checkProducts() {
        final var productIds = this.sale.getProductsIds();

        if (productIds == null || productIds.isEmpty()) {
            this.validationHandler().append(new Error("'productIds' should not be null or empty"));
            return;
        }

        // You can add more checks here depending on your business rules
        // For example, you might want to check if each product ID exists in the database
    }
}
