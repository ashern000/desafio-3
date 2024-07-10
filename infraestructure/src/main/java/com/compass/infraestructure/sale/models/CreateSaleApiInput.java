package com.compass.infraestructure.sale.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CreateSaleApiInput(
        @JsonProperty("productSales") List<ProductSale> productSales
) {
    public static class ProductSale {
        public String productId;
        public int quantity;

        // getters and setters
    }
}
