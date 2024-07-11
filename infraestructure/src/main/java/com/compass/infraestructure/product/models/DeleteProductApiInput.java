package com.compass.infraestructure.product.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DeleteProductApiInput(
        @JsonProperty("id") String id
) {
}
