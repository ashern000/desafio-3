package com.compass.application.product.update;

public record UpdateProductCommand(String id, String name, String description, boolean isActive, double price,
                                   int quantity) {

    public static UpdateProductCommand with(
            final String anId,
            final String aName,
            final String aDescription,
            final boolean isActive,
            final double price,
            final int quantity
    ) {
        return new UpdateProductCommand(anId, aName, aDescription, isActive, price, quantity);
    }
}
