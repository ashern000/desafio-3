package com.compass.application.sale.retrieve.list.generatesalesreport;

import com.compass.domain.product.ProductID;
import com.compass.domain.sale.Sale;
import com.compass.domain.sale.SaleID;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public record GenerateSalesReportOutput(
        SaleID id,
        List<ProductID> products,
        double totalPrice,
        Instant createdAt,
        Instant updatedAt
) {
    public static GenerateSalesReportOutput from(Sale sale) {
        return new GenerateSalesReportOutput(
                sale.getId(),
                sale.getProductsIds(),
                sale.getTotalPrice(),
                sale.getCreatedAt(),
                sale.getUpdatedAt()
        );
    }
}
