package com.compass.application.sale.update;

import java.util.List;

public record UpdateSaleOutput(String saleId) {

    public static UpdateSaleOutput with(final String saleId) {
        return new UpdateSaleOutput(saleId);
    }
}