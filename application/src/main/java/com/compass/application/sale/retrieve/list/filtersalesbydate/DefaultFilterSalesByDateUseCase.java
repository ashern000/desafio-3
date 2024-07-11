package com.compass.application.sale.retrieve.list.filtersalesbydate;

import com.compass.domain.sale.Sale;
import com.compass.domain.sale.SaleGateway;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DefaultFilterSalesByDateUseCase extends FilterSalesByDateUseCase {

    private final SaleGateway saleGateway;

    public DefaultFilterSalesByDateUseCase(final SaleGateway saleGateway) {
        this.saleGateway = Objects.requireNonNull(saleGateway);
    }

    @Override
    public List<FilterSalesByDateOutput> execute(final FilterSalesByDateCommand input) {
        return this.saleGateway.findAll().stream()
                .filter(sale -> !sale.getCreatedAt().isBefore(input.startDate()) && !sale.getCreatedAt().isAfter(input.endDate()))
                .map(FilterSalesByDateOutput::from)
                .collect(Collectors.toList());
    }
}