package com.compass.application.product.retrieve.list;

import com.compass.domain.product.Product;
import com.compass.domain.product.ProductID;

import java.time.Instant;

public record ListProductOutput(ProductID id,
                                String name,
                                String description,
                                boolean isActive,
                                double price,
                                Instant createdAt,
                                Instant updatedAt,
                                Instant deletedaAt) {
    public static ListProductOutput from(final Product aProduct) {
        return new ListProductOutput(
                aProduct.getId(),
                aProduct.getName(),
                aProduct.getDescription(),
                aProduct.isActive(),
                aProduct.getPrice(),
                aProduct.getCreatedAt(),
                aProduct.getUpdatedAt(),
                aProduct.getDeletedAt()
        );
    }
}
