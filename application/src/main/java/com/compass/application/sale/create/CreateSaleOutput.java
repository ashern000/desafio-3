package com.compass.application.sale.create;

import com.compass.domain.product.ProductID;
import com.compass.domain.sale.Sale;
import com.compass.domain.sale.SaleID;

import java.util.List;

public record CreateSaleOutput(String id, List<String> productIds, String userId) {
    public static CreateSaleOutput from(final String id, final List<String> productIds, final String userId) {
        return new CreateSaleOutput(id, productIds, userId);
    }

    public static CreateSaleOutput from(final Sale sale) {
        return new CreateSaleOutput(sale.getId().getValue(), sale.getProductsIds().stream().map(ProductID::getValue).toList(), sale.getUserId().getValue());
    }
}
