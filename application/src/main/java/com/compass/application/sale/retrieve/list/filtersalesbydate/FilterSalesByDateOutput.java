package com.compass.application.sale.retrieve.list.filtersalesbydate;

import com.compass.domain.sale.Sale;
import com.compass.domain.sale.SaleID;
import com.compass.domain.product.ProductID;

import java.time.Instant;
import java.util.List;

public record FilterSalesByDateOutput(
        SaleID id,
        List<ProductID> products,
        double totalPrice,
        Instant createdAt,
        Instant updatedAt
) {
    public static FilterSalesByDateOutput from(final Sale aSale) {
        return new FilterSalesByDateOutput(
                aSale.getId(),
                aSale.getProductsIds(),
                aSale.getTotalPrice(),
                aSale.getCreatedAt(),
                aSale.getUpdatedAt()
        );
    }
}
