package com.compass.infraestructure.product.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateProductApiInput(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("active") Boolean active,
        @JsonProperty("price") double price,
        @JsonProperty("quantity") int quantity
) {
}
