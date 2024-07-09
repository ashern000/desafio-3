package com.compass.application.product.update;

import com.compass.application.UseCase;
import com.compass.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateProductUseCase extends UseCase<UpdateProductCommand, Either<Notification, UpdateProductOutput>> {
}
