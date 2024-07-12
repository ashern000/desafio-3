package com.compass.application.sale.update;

import java.util.List;

public record UpdateSaleCommand(String saleId, List<ProductSale> productSales) {

    public static UpdateSaleCommand with(final String saleId, final List<ProductSale> productSales) {
        return new UpdateSaleCommand(saleId, productSales);
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
