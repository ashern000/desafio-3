package com.compass.application.product.delete;

import com.compass.application.UseCase;
import com.compass.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class DeleteProductUseCase extends UseCase<String, Either<Notification, DeleteProductOutput>> {
}
