package com.compass.application.product.retrieve.get;

public record GetProductCommand(String anId) {
    public static GetProductCommand with(final String anId) {return new GetProductCommand(anId);}
}
