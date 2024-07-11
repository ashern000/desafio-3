package com.compass.application.sale.delete;

public record DeleteSaleCommand(String saleId) {

    public static DeleteSaleCommand with(final String saleId) {
        return new DeleteSaleCommand(saleId);
    }
}
