package com.compass.domain.product;

import com.compass.domain.validation.Error;
import com.compass.domain.validation.ValidationHandler;
import com.compass.domain.validation.Validator;

public class ProductValidator extends Validator {

    private final Product product;
    private static final int MIN_LENGTH_PROD = 2;
    private static final int MAX_LENGTH_PROD = 255;
    private static final int MIN_LENGTH_PROD_DESCRIPTION = 1;
    private static final int MAX_LENGTH_PROD_DESCRIPTION = 1250;


    public ProductValidator(Product aProduct, ValidationHandler aValidationHandler) {
    super(aValidationHandler);
    this.product = aProduct;
    }


    @Override
    public void validate() {
        checkName();
        checkDescription();
    }

    private void checkName() {
        final var name = this.product.getName();

        if(name == null) {
            this.validationHandler().append(new Error("'name' should be not null"));
            return;
        }
        if(name.isBlank()) {
            this.validationHandler().append(new Error("'name' should be not empty"));
            return;
        }

        if(name.trim().isEmpty()) {
            this.validationHandler().append(new Error("'name' should be not empty"));
            return;
        }

        if(name.length() < MIN_LENGTH_PROD || name.length() > MAX_LENGTH_PROD) {
            this.validationHandler().append(new Error("'name' must be between 3 and 255 characters"));
        }
    }

    private void checkDescription() {
        final String description = this.product.getDescription();

        if (description.isEmpty()) {
            this.validationHandler().append(new Error("'description' should be not empty"));
            return;
        }

        if (description.isBlank()) {
            this.validationHandler().append(new Error("'description' should be not null"));
            return;
        }

        if (description.trim().length() < MIN_LENGTH_PROD_DESCRIPTION || description.trim().length() > MAX_LENGTH_PROD_DESCRIPTION) {
            this.validationHandler().append(new Error("'description' must be between 1 and 1250 characters"));
        }
    }
}
