package com.compass.application.sale.retrieve.list.defaultlist;

import com.compass.domain.product.ProductID;
import com.compass.domain.sale.Sale;
import com.compass.domain.sale.SaleID;

import java.time.Instant;
import java.util.List;

public record ListSaleOutput(SaleID id, List<ProductID> products, double totalPrice, Instant createdAt, Instant updatedAt) {

    public static ListSaleOutput from(Sale sale) {
        return new ListSaleOutput(
                sale.getId(),
                sale.getProductsIds(),
                sale.getTotalPrice(),
                sale.getCreatedAt(),
                sale.getUpdatedAt()
        );
    }
}
