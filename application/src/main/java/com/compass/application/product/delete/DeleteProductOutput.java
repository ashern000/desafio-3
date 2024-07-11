package com.compass.application.product.delete;

import com.compass.domain.product.ProductID;

public record DeleteProductOutput(String id, String message) {
    public static DeleteProductOutput with(final ProductID anId, final String message) {
        return new DeleteProductOutput(anId.getValue(), message);
    }
}
