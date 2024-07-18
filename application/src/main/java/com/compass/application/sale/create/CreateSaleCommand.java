package com.compass.application.sale.create;

import com.compass.domain.user.UserID;

import java.util.List;

public record CreateSaleCommand(List<ProductSale> productSales, String token) {

    public static CreateSaleCommand with(final List<ProductSale> productSales, final String token) {
        return new CreateSaleCommand(productSales, token);
    }


    public static class ProductSale {
        private String productId;
        private int quantity;

        public ProductSale(String productId, int quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
