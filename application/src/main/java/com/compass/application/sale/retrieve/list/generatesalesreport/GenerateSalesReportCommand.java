package com.compass.application.sale.retrieve.list.generatesalesreport;

public record GenerateSalesReportCommand(ReportType reportType) {

    public enum ReportType {
        MONTHLY,
        WEEKLY
    }

    public static GenerateSalesReportCommand from(String reportType) {
        return new GenerateSalesReportCommand(ReportType.valueOf(reportType.toUpperCase()));
    }
}
