package com.compass.application.sale.create;

import java.util.List;

public record CreateSaleCommand(
        List<ProductSale> productSales
) {

    public static CreateSaleCommand with(final List<ProductSale> productSales) {
        return new CreateSaleCommand(productSales);
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
