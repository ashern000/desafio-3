package com.compass.application.product.create;

public record CreateProductCommand(
        String name,
        String description,
        boolean isActive
) {

    public static CreateProductCommand with(final String name,
                                            final String description,
                                            final boolean isActive
    ) {
        return new CreateProductCommand(name, description, isActive);
    }
}
