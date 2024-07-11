package com.compass.application.sale.delete;

public record DeleteSaleOutput(String saleId) {

    public static DeleteSaleOutput from(final String saleId) {
        return new DeleteSaleOutput(saleId);
    }
}
