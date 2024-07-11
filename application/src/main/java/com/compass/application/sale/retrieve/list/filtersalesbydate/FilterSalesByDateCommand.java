package com.compass.application.sale.retrieve.list.filtersalesbydate;

import java.time.Instant;

public record FilterSalesByDateCommand(Instant startDate, Instant endDate) {

    public static FilterSalesByDateCommand from(final Instant aStartDate, final Instant anEndDate) {
        return new FilterSalesByDateCommand(aStartDate, anEndDate);
    }
}
