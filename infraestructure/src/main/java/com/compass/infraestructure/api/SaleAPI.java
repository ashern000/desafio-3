package com.compass.infraestructure.api;

import com.compass.infraestructure.product.models.CreateProductApiInput;
import com.compass.infraestructure.sale.models.CreateSaleApiInput;
import com.compass.infraestructure.sale.models.GenerateSalesReportApiInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RequestMapping(value = "/sales")
@Tag(name = "Sales")
public interface SaleAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new Sale")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "422", description = "Unprocessable error"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    ResponseEntity<?> createSale(@RequestBody CreateSaleApiInput input);

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "List Sales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "List successfully"),
            @ApiResponse(responseCode = "422", description = "Unprocessable error"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    List<?> listSales();

    @GetMapping(
            value = "/filter_by_date",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "List Sales by date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "List successfully"),
            @ApiResponse(responseCode = "422", description = "Unprocessable error"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    List<?> listSalesByDate(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startDate,
                            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endDate);


    @Operation(summary = "Generate Sales Report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List successfully"),
            @ApiResponse(responseCode = "422", description = "Unprocessable error"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    @GetMapping(value = "/report", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<?> generateSalesReport(
            @RequestParam("reportType") GenerateSalesReportApiInput.ReportType reportType
    );

    @DeleteMapping()
    public ResponseEntity<?> deleteSale(@RequestParam("saleId") String saleId);
}
