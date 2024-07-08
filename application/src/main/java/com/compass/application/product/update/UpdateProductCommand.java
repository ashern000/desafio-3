package com.compass.application.product.update;

public record UpdateProductCommand(String id, String name, String description, boolean isActive) {

    public static UpdateProductCommand with(
            final String anId,
            final String aName,
            final String aDescription,
            final boolean isActive
    ) {
        return new UpdateProductCommand(anId,aName, aDescription, isActive);
    }
}
