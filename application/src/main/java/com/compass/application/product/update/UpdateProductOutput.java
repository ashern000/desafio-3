package com.compass.application.product.update;

import com.compass.domain.product.Product;

public record UpdateProductOutput(String anId) {
    public static UpdateProductOutput from(final String anId) {
        return new UpdateProductOutput(anId);
    }

    public static UpdateProductOutput from(final Product aProduct) {
        return new UpdateProductOutput(aProduct.getId().getValue());
    }
}
