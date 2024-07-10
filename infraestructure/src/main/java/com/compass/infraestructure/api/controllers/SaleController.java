package com.compass.infraestructure.api.controllers;

import com.compass.application.sale.create.CreateSaleCommand;
import com.compass.application.sale.create.CreateSaleUseCase;
import com.compass.domain.exceptions.NotificationException;
import com.compass.infraestructure.api.SaleAPI;
import com.compass.infraestructure.sale.models.CreateSaleApiInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SaleController implements SaleAPI {

    private CreateSaleUseCase createSaleUseCase;

    public SaleController(final CreateSaleUseCase createSaleUseCase) {
        this.createSaleUseCase = Objects.requireNonNull(createSaleUseCase);
    }

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

    @Override
    public List<?> listSales() {
        return List.of();
    }
}
