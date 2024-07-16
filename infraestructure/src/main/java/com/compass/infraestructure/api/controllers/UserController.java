package com.compass.infraestructure.api.controllers;

import com.compass.application.product.create.CreateProductOutput;
import com.compass.application.user.create.CreateUserCommand;
import com.compass.application.user.create.CreateUserOutput;
import com.compass.application.user.create.CreateUserUseCase;
import com.compass.application.user.login.LoginUserUseCase;
import com.compass.domain.validation.handler.Notification;
import com.compass.infraestructure.api.UserAPI;
import com.compass.infraestructure.user.models.CreateUserApiInput;
import com.compass.infraestructure.user.models.LoginUserApiInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class UserController implements UserAPI {

    private CreateUserUseCase createUserUseCase;


    public UserController(final CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = Objects.requireNonNull(createUserUseCase);

    }

    @Override
    public ResponseEntity<?> createUser(CreateUserApiInput input) {
        final var aName = input.name();
        final var anEmail = input.email();
        final var aPassword = input.password();
        final var active = input.active() != null ? input.active() : true;
        final var aRole = input.role();

        System.out.println(aRole);

        final var aCommand = CreateUserCommand.with(aName, anEmail, aPassword, active, aRole);

        final Function<Notification, ResponseEntity<?>> onError = notification -> {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("code", HttpStatus.UNPROCESSABLE_ENTITY.value());
            responseBody.put("errors", notification.getErrors());
            responseBody.put("status", HttpStatus.UNPROCESSABLE_ENTITY.name());
            return ResponseEntity.unprocessableEntity().body(responseBody);
        };

        final Function<CreateUserOutput, ResponseEntity<?>> onSuccess = ResponseEntity::ok;


        return this.createUserUseCase.execute(aCommand).fold(onError, onSuccess);
    }

    @Override
    public ResponseEntity<?> loginUser(LoginUserApiInput input) {
        return null;
    }
}
