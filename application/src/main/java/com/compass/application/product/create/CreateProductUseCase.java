package com.compass.application.product.create;

import com.compass.application.UseCase;
import com.compass.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateProductUseCase extends UseCase<CreateProductCommand, Either<Notification, CreateProductOutput>> {
}
