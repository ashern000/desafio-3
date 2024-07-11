package com.compass.application.sale.create;

import com.compass.domain.product.ProductGateway;
import com.compass.domain.product.ProductID;
import com.compass.domain.exceptions.NotificationException;
import com.compass.domain.sale.Sale;
import com.compass.domain.sale.SaleGateway;
import com.compass.domain.validation.Error;
import com.compass.domain.validation.ValidationHandler;
import com.compass.domain.validation.handler.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DefaultCreateSaleUseCase extends CreateSaleUseCase {

    private final ProductGateway productGateway;
    private final SaleGateway saleGateway;

    public DefaultCreateSaleUseCase(final ProductGateway productGateway, final SaleGateway saleGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
        this.saleGateway = Objects.requireNonNull(saleGateway);
    }

    @Override
    public CreateSaleOutput execute(final CreateSaleCommand aCommand) {
        final var productSales = aCommand.productSales();

        final var notification = Notification.create();
        notification.append(validateProducts(productSales));

        if (notification.hasErrors()) {
            throw new NotificationException("Could not create Aggregate Sale", notification);
        }

        double totalPrice = productSales.stream()
                .mapToDouble(productSale -> {
                    var product = productGateway.findById(ProductID.from(productSale.getProductId()));
                    return product.map(value -> value.getPrice() * productSale.getQuantity()).orElse(0.0);
                })
                .sum();

        final var aSale = Sale.newSale(productSales.stream().map(CreateSaleCommand.ProductSale::getProductId).map(ProductID::from).toList(), totalPrice);
        return CreateSaleOutput.from(this.saleGateway.create(aSale));
    }

    private ValidationHandler validateProducts(final List<CreateSaleCommand.ProductSale> productSales) {
        final var notification = Notification.create();
        if (productSales == null || productSales.isEmpty()) {
            return notification.append(new Error("No Products for insert in Sale"));
        }

        for (CreateSaleCommand.ProductSale productSale : productSales) {
            var product = productGateway.findById(ProductID.from(productSale.getProductId()));
            if (product.isEmpty()) {
                notification.append(new Error("Product with ID " + productSale.getProductId() + " does not exist"));
            } else if (product.get().getQuantity() < productSale.getQuantity()) {
                notification.append(new Error("Product with ID " + productSale.getProductId() + " does not have enough quantity available"));
            }
        }

        return notification;
    }
}
