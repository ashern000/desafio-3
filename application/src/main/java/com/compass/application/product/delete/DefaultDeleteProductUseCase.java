package com.compass.application.product.delete;

import com.compass.domain.product.ProductGateway;
import com.compass.domain.product.ProductID;
import com.compass.domain.sale.SaleGateway;
import com.compass.domain.validation.Error;
import com.compass.domain.validation.handler.Notification;
import io.vavr.control.Either;
import io.vavr.control.Try;
import java.util.Objects;

import static io.vavr.API.Left;

public class DefaultDeleteProductUseCase extends DeleteProductUseCase {

    private ProductGateway productGateway;

    private SaleGateway saleGateway;

    public DefaultDeleteProductUseCase(ProductGateway productGateway, SaleGateway saleGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
        this.saleGateway = Objects.requireNonNull(saleGateway);
    }

    @Override
    public Either<Notification, DeleteProductOutput> execute(String aCommand) {
        final var notification = Notification.create();
        final var id = ProductID.from(aCommand);
        final var productsInSale = this.saleGateway.findAllSalesByProductId(id);
        if (!productsInSale.isEmpty()) {
            notification.append(new Error("Product with ID %s, exists in sale".formatted(aCommand)));
        }
        return notification.hasErrors() ? Left(notification) : delete(id);
    }

    private Either<Notification, DeleteProductOutput> delete(final ProductID anId) {
        return Try.of(() -> {
            this.productGateway.delete(anId);
            return DeleteProductOutput.with(anId, "Product deleted");
        }).toEither().mapLeft(Notification::create);
    }
}
