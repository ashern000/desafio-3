package com.compass.application.sale.retrieve.list.generatesalesreport;

import com.compass.domain.sale.SaleGateway;


import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DefaultGenerateSalesReportUseCase extends GenerateSalesReportUseCase {

    private final SaleGateway saleGateway;

    public DefaultGenerateSalesReportUseCase(final SaleGateway saleGateway) {
        this.saleGateway = Objects.requireNonNull(saleGateway);
    }

    @Override
    public List<GenerateSalesReportOutput> execute(final GenerateSalesReportCommand aCommand) {
        if (aCommand.reportType() == GenerateSalesReportCommand.ReportType.MONTHLY) {
            return this.saleGateway.findAll().stream()
                    .filter(sale -> YearMonth.from(sale.getCreatedAt().atZone(ZoneId.systemDefault()).toLocalDate()).equals(YearMonth.now()))
                    .map(GenerateSalesReportOutput::from)
                    .collect(Collectors.toList());
        } else {
            return this.saleGateway.findAll().stream()
                    .filter(sale -> ChronoUnit.WEEKS.between(sale.getCreatedAt().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now()) == 0)
                    .map(GenerateSalesReportOutput::from)
                    .collect(Collectors.toList());
        }
    }
}

