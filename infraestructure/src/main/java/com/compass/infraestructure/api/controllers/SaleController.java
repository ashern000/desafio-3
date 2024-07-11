package com.compass.infraestructure.api.controllers;

import com.compass.application.sale.create.CreateSaleCommand;
import com.compass.application.sale.create.CreateSaleUseCase;
import com.compass.application.sale.delete.DeleteSaleCommand;
import com.compass.application.sale.delete.DeleteSaleUseCase;
import com.compass.application.sale.retrieve.list.defaultlist.ListSaleUseCase;
import com.compass.application.sale.retrieve.list.filtersalesbydate.FilterSalesByDateCommand;
import com.compass.application.sale.retrieve.list.filtersalesbydate.FilterSalesByDateUseCase;
import com.compass.application.sale.retrieve.list.generatesalesreport.GenerateSalesReportCommand;
import com.compass.application.sale.retrieve.list.generatesalesreport.GenerateSalesReportUseCase;
import com.compass.domain.exceptions.NotificationException;
import com.compass.infraestructure.api.SaleAPI;
import com.compass.infraestructure.sale.models.CreateSaleApiInput;
import com.compass.infraestructure.sale.models.GenerateSalesReportApiInput;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@RestController
public class SaleController implements SaleAPI {

    private CreateSaleUseCase createSaleUseCase;

    private FilterSalesByDateUseCase filterSalesByDateUseCase;

    private ListSaleUseCase listSaleUseCase;

    private GenerateSalesReportUseCase generateSalesReportUseCase;

    private DeleteSaleUseCase deleteSaleUseCase;

    public SaleController(final CreateSaleUseCase createSaleUseCase, final FilterSalesByDateUseCase filterSalesByDateUseCase, final ListSaleUseCase listSaleUseCase, final GenerateSalesReportUseCase generateSalesReportUseCase, final DeleteSaleUseCase deleteSaleUseCase) {
        this.createSaleUseCase = Objects.requireNonNull(createSaleUseCase);
        this.filterSalesByDateUseCase = Objects.requireNonNull(filterSalesByDateUseCase);
        this.listSaleUseCase = Objects.requireNonNull(listSaleUseCase);
        this.generateSalesReportUseCase = Objects.requireNonNull(generateSalesReportUseCase);
        this.deleteSaleUseCase = Objects.requireNonNull(deleteSaleUseCase);
    }

    @CacheEvict(value = "sales", allEntries = true)
    public ResponseEntity<?> createSale(final CreateSaleApiInput input) {
        final var productSales = input.productSales();

        final var aCommand = CreateSaleCommand.with(productSales.stream().map(inputValues ->
                new CreateSaleCommand.ProductSale(inputValues.productId, inputValues.quantity)
                ).toList());

        try {
            final var createSaleOutput = this.createSaleUseCase.execute(aCommand);
            return ResponseEntity.created(URI.create("/sales/" + createSaleOutput.id())).body(createSaleOutput);
        } catch (NotificationException e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("code", HttpStatus.UNPROCESSABLE_ENTITY.value());
            responseBody.put("errors", e.getErrors());
            responseBody.put("status", HttpStatus.UNPROCESSABLE_ENTITY.name());
            return ResponseEntity.unprocessableEntity().body(responseBody);
        }
    }

    @Cacheable(value = "sales")
    @Override
    public List<?> listSales() {
        return this.listSaleUseCase.execute();
    }

    @Cacheable(value = "sales")
    @Override
    public List<?> listSalesByDate(Instant startDate, Instant endDate) {
        final var aCommand = FilterSalesByDateCommand.from(startDate,endDate);
        return this.filterSalesByDateUseCase.execute(aCommand);
    }

    @Cacheable(value = "sales")
    @Override
    public List<?> generateSalesReport(GenerateSalesReportApiInput.ReportType reportType) {
        final var aCommand = GenerateSalesReportCommand.from(reportType.toString());
        return this.generateSalesReportUseCase.execute(aCommand);
    }

    @CacheEvict(value = "sales", allEntries = true)
    @Override
    public ResponseEntity<?> deleteSale(String saleId) {
        System.out.println(saleId);
        try {
            final var aCommand = DeleteSaleCommand.with(saleId);
            return ResponseEntity.ok(this.deleteSaleUseCase.execute(aCommand));
        } catch (NotificationException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while trying to delete the sale.");
        }
    }


}
