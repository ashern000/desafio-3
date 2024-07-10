package com.compass.application.product.create;

import com.compass.domain.product.Product;
import com.compass.domain.product.ProductGateway;
import com.compass.domain.validation.handler.Notification;
import io.vavr.control.Either;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

import java.util.Objects;

public class DefaulCreateProductUseCase extends CreateProductUseCase {

    private ProductGateway productGateway;

    public DefaulCreateProductUseCase(final ProductGateway productGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
    }

    @Override
    public Either<Notification, CreateProductOutput> execute(final CreateProductCommand aCommand) {
        final String name = aCommand.name();
        final String description = aCommand.description();
        final boolean active = aCommand.isActive();
        final double price = aCommand.price();
        final int quantity = aCommand.quantity();

        final var notification = Notification.create();
;
        final var aProduct = Product.newProduct(name, description, active, price, quantity);
        aProduct.validate(notification);

        return notification.hasErrors() ? Left(notification) : create(aProduct);
    }

    private Either<Notification, CreateProductOutput> create(final Product product) {
        return Try(() -> this.productGateway.create(product))
                .toEither()
                .bimap(Notification::create, CreateProductOutput::from);
    }
}
