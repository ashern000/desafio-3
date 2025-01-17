package com.compass.infraestructure.api;

import com.compass.infraestructure.product.models.CreateProductApiInput;
import com.compass.infraestructure.sale.models.CreateSaleApiInput;
import com.compass.infraestructure.sale.models.GenerateSalesReportApiInput;
import com.compass.infraestructure.sale.models.UpdateSaleApiInput;
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
    ResponseEntity<?> createSale(@RequestHeader(value="Authorization") String token,@RequestBody CreateSaleApiInput input);

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
    List<?> listSalesByDate(@RequestHeader(value="Authorization") String token,@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startDate,
                            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endDate);


    @Operation(summary = "Generate Sales Report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List successfully"),
            @ApiResponse(responseCode = "422", description = "Unprocessable error"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    @GetMapping(value = "/report", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<?> generateSalesReport(@RequestHeader(value="Authorization") String token,
            @RequestParam("reportType") GenerateSalesReportApiInput.ReportType reportType
    );

    @DeleteMapping()
    public ResponseEntity<?> deleteSale(@RequestHeader(value="Authorization") String token,@RequestParam("saleId") String saleId);

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update an existing Sale")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "422", description = "Unprocessable error"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    ResponseEntity<?> updateSale(@RequestHeader(value="Authorization") String token, @RequestBody UpdateSaleApiInput input);

}
