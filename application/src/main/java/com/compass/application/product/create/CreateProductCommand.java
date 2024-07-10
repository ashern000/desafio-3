package com.compass.application.product.create;

public record CreateProductCommand(
        String name,
        String description,
        boolean isActive,
        double price,
        int quantity
) {

    public static CreateProductCommand with(final String name,
                                            final String description,
                                            final boolean isActive,
                                            final double price,
                                            final int quantity
    ) {
        return new CreateProductCommand(name, description, isActive, price, quantity);
    }
}
