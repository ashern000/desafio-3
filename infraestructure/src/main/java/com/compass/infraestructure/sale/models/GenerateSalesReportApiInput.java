package com.compass.infraestructure.sale.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GenerateSalesReportApiInput(
        @JsonProperty("reportType") ReportType reportType
) {
    public enum ReportType {
        MONTHLY,
        WEEKLY
    }
}
