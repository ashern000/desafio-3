package com.compass.infraestructure.api.controllers;

import com.compass.application.product.create.CreateProductCommand;
import com.compass.application.product.create.CreateProductOutput;
import com.compass.application.product.create.CreateProductUseCase;
import com.compass.application.product.delete.DeleteProductOutput;
import com.compass.application.product.delete.DeleteProductUseCase;
import com.compass.application.product.retrieve.list.ListProductUseCase;
import com.compass.application.product.update.UpdateProductCommand;
import com.compass.application.product.update.UpdateProductOutput;
import com.compass.application.product.update.UpdateProductUseCase;
import com.compass.domain.validation.handler.Notification;
import com.compass.infraestructure.api.ProductAPI;
import com.compass.infraestructure.product.models.CreateProductApiInput;
import com.compass.infraestructure.product.models.DeleteProductApiInput;
import com.compass.infraestructure.product.models.UpdateProductApiInput;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class ProductController implements ProductAPI {

    private CreateProductUseCase createProductUseCase;

    private ListProductUseCase listProductUseCase;

    private UpdateProductUseCase updateProductUseCase;

    private DeleteProductUseCase deleteProductUseCase;

    public ProductController(final CreateProductUseCase createProductUseCase,
                             final ListProductUseCase listProductUseCase,
                             final UpdateProductUseCase updateProductUseCase,
                             final DeleteProductUseCase deleteProductUseCase)
    {
        this.createProductUseCase = Objects.requireNonNull(createProductUseCase);
        this.listProductUseCase = Objects.requireNonNull(listProductUseCase);
        this.updateProductUseCase = Objects.requireNonNull(updateProductUseCase);
        this.deleteProductUseCase = Objects.requireNonNull(deleteProductUseCase);
    }

    @Override
    @CacheEvict(value = "products", allEntries = true)
    public ResponseEntity<?> createProduct(final CreateProductApiInput input) {
        final var name = input.name();
        final var description = input.description();
        final var active = input.active() != null ? input.active() : true;
        final var price = input.price();
        final var quantity = input.quantity();

        final var aCommand = CreateProductCommand.with(name, description, active, price, quantity);

        final Function<Notification, ResponseEntity<?>> onError = notification -> {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("code", HttpStatus.UNPROCESSABLE_ENTITY.value());
            responseBody.put("errors", notification.getErrors());
            responseBody.put("status", HttpStatus.UNPROCESSABLE_ENTITY.name());
            return ResponseEntity.unprocessableEntity().body(responseBody);
        };

        final Function<CreateProductOutput, ResponseEntity<?>> onSuccess = ResponseEntity::ok;

        return this.createProductUseCase.execute(aCommand).fold(onError, onSuccess);
    }


    @Override
    @Cacheable(value = "products")
    public List<?> listProducts() {
        return this.listProductUseCase.execute();
    }

    @Override
    public ResponseEntity<?> updateProduct(UpdateProductApiInput input) {
        final var id = input.id();
        final var name = input.name();
        final var description = input.description();
        final var active = input.active() != null ? input.active() : true;
        final var price = input.price();
        final var quantity = input.quantity();

        final var aCommand = UpdateProductCommand.with(id, name, description, active, price, quantity);

        final Function<Notification, ResponseEntity<?>> onError = notification -> {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("code", HttpStatus.UNPROCESSABLE_ENTITY.value());
            responseBody.put("errors", notification.getErrors());
            responseBody.put("status", HttpStatus.UNPROCESSABLE_ENTITY.name());
            return ResponseEntity.unprocessableEntity().body(responseBody);
        };

        final Function<UpdateProductOutput, ResponseEntity<?>> onSuccess = ResponseEntity::ok;

        return this.updateProductUseCase.execute(aCommand).fold(onError, onSuccess);
    }

    @Override
    public ResponseEntity<?> deleteProduct(DeleteProductApiInput input) {
        final var id = input.id();

        final Function<Notification, ResponseEntity<?>> onError = notification -> {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("code", HttpStatus.UNPROCESSABLE_ENTITY.value());
            responseBody.put("errors", notification.getErrors());
            responseBody.put("status", HttpStatus.UNPROCESSABLE_ENTITY.name());
            return ResponseEntity.unprocessableEntity().body(responseBody);
        };

        final Function<DeleteProductOutput, ResponseEntity<?>> onSuccess = ResponseEntity::ok;
        return this.deleteProductUseCase.execute(id).fold(onError, onSuccess);
    }
}
