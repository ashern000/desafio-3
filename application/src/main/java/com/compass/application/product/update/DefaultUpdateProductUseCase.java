package com.compass.application.product.update;

import com.compass.domain.exceptions.DomainException;
import com.compass.domain.exceptions.NotFoundException;
import com.compass.domain.product.Product;
import com.compass.domain.product.ProductGateway;
import com.compass.domain.product.ProductID;
import com.compass.domain.validation.handler.Notification;
import io.vavr.API;
import io.vavr.control.Either;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultUpdateProductUseCase extends UpdateProductUseCase {

    private ProductGateway productGateway;

    public DefaultUpdateProductUseCase(final ProductGateway productGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
    }

    @Override
    public Either<Notification, UpdateProductOutput> execute(UpdateProductCommand aCommand) {
        final var anId = ProductID.from(aCommand.id());
        final var aName = aCommand.name();
        final var aDescription = aCommand.description();
        final var isActive = aCommand.isActive();
        final var price = aCommand.price();
        final var quantity = aCommand.quantity();

        final var aProduct = this.productGateway.findById(anId).orElseThrow(notFound(anId));

        final var notification = Notification.create();

        aProduct.update(aName, aDescription, isActive, price, quantity).validate(notification);

        return notification.hasErrors() ? Left(notification) : update(aProduct);


    }

    private Either<Notification, UpdateProductOutput> update(final Product aProduct) {
        return Try(() -> this.productGateway.update(aProduct)).toEither().bimap(Notification::create, UpdateProductOutput::from);
    }

    private Supplier<DomainException> notFound(final ProductID anId) {
        return () -> NotFoundException.with(Product.class, anId);
    }
}

