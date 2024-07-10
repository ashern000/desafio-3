package com.compass.application.product.retrieve.get;

import com.compass.domain.product.Product;
import com.compass.domain.product.ProductID;

import java.time.Instant;

public record GetProductOutput(
        String id,
        String name,
        boolean active,
        String description,
        double price,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt

) {
    public static GetProductOutput from(final Product aProduct) {
        return new GetProductOutput(aProduct.getId().getValue(),
                aProduct.getName(),
                aProduct.isActive(),
                aProduct.getDescription(),
                aProduct.getPrice(),
                aProduct.getCreatedAt(),
                aProduct.getUpdatedAt(),
                aProduct.getDeletedAt()
        );
    }
}
