package com.compass.application.sale.update;

import com.compass.domain.product.ProductGateway;
import com.compass.domain.product.ProductID;
import com.compass.domain.exceptions.NotificationException;
import com.compass.domain.sale.Sale;
import com.compass.domain.sale.SaleGateway;
import com.compass.domain.sale.SaleID;
import com.compass.domain.validation.Error;
import com.compass.domain.validation.ValidationHandler;
import com.compass.domain.validation.handler.Notification;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DefaultUpdateSaleUseCase extends UpdateSaleUseCase {

    private final ProductGateway productGateway;
    private final SaleGateway saleGateway;

    public DefaultUpdateSaleUseCase(final ProductGateway productGateway, final SaleGateway saleGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
        this.saleGateway = Objects.requireNonNull(saleGateway);
    }

    @Override
    public UpdateSaleOutput execute(final UpdateSaleCommand aCommand) {
        final var productSales = aCommand.productSales();

        final var notification = Notification.create();
        notification.append(validateProducts(productSales));

        if (notification.hasErrors()) {
            throw new NotificationException("Could not update Aggregate Sale", notification);
        }

        double totalPrice = productSales.stream()
                .mapToDouble(productSale -> {
                    var product = productGateway.findById(ProductID.from(productSale.getProductId()));
                    return product.map(value -> value.getPrice() * productSale.getQuantity()).orElse(0.0);
                })
                .sum();

        final var aSale = this.saleGateway.findById(SaleID.from(aCommand.saleId())).orElse(null);
        aSale.update(productSales.stream().map(it -> ProductID.from(it.getProductId())).toList(), totalPrice).validate(notification);
        return UpdateSaleOutput.with(this.saleGateway.update(aSale).getId().getValue());
    }

    private ValidationHandler validateProducts(final List<UpdateSaleCommand.ProductSale> productSales) {
        final var notification = Notification.create();
        if (productSales == null || productSales.isEmpty()) {
            return notification.append(new Error("No Products for update in Sale"));
        }

        for (UpdateSaleCommand.ProductSale productSale : productSales) {
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
