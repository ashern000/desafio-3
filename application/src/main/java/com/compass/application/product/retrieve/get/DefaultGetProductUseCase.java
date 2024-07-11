package com.compass.application.product.retrieve.get;

import com.compass.domain.exceptions.DomainException;
import com.compass.domain.product.ProductGateway;
import com.compass.domain.product.ProductID;
import com.compass.domain.validation.Error;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetProductUseCase extends GetProductUseCase {

    private ProductGateway productGateway;

    public DefaultGetProductUseCase(final ProductGateway productGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
    }

    @Override
    public GetProductOutput execute(GetProductCommand aCommand) {
        final var anProductId = ProductID.from(aCommand.anId());
        return this.productGateway.findById(anProductId).map(GetProductOutput::from).orElseThrow(notFound(anProductId));
    }

    private Supplier<DomainException> notFound(final ProductID anId) {
        return () -> DomainException.with(new Error("Product with ID %s was not found".formatted(anId.getValue())));
    }
}
