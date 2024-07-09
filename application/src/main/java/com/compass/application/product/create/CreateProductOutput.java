package com.compass.application.product.create;

import com.compass.domain.product.Product;

public record CreateProductOutput(
        String id
) {
    public static CreateProductOutput from(final String id) {
        return new CreateProductOutput(id);
    }

    public static CreateProductOutput from(final Product product) {
        return new CreateProductOutput(product.getId().getValue());
    }
}
