package com.compass.application.sale.delete;

import com.compass.domain.sale.SaleID;
import com.compass.domain.sale.SaleGateway;
import com.compass.domain.exceptions.NotificationException;
import com.compass.domain.validation.Error;
import com.compass.domain.validation.ValidationHandler;
import com.compass.domain.validation.handler.Notification;

import java.util.Objects;

public class DefaultDeleteSaleUseCase extends DeleteSaleUseCase {

    private final SaleGateway saleGateway;

    public DefaultDeleteSaleUseCase(final SaleGateway saleGateway) {
        this.saleGateway = Objects.requireNonNull(saleGateway);
    }

    @Override
    public DeleteSaleOutput execute(final DeleteSaleCommand aCommand) {
        final var saleId = aCommand.saleId();

        final var notification = Notification.create();
        notification.append(validateSale(saleId));

        if (notification.hasErrors()) {
            throw new NotificationException("Could not delete Sale", notification);
        }

        saleGateway.delete(SaleID.from(saleId));
        return DeleteSaleOutput.from(saleId);
    }

    private ValidationHandler validateSale(final String saleId) {
        final var notification = Notification.create();
        var sale = saleGateway.findById(SaleID.from(saleId));
        if (sale.isEmpty()) {
            notification.append(new Error("Sale with ID " + saleId + " does not exist"));
        }
        return notification;
    }
}
