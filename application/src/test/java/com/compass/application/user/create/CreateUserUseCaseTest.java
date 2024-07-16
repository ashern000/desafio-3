package com.compass.application.user.create;

import com.compass.application.UseCaseTest;
import com.compass.application.adapters.CryptoAdapter;
import com.compass.domain.user.UserGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Objects;

public class CreateUserUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateUserUseCase createUserUseCase;

    @Mock
    private UserGateway userGateway;

    @Mock
    private CryptoAdapter cryptoAdapter;


    @Override
    protected List<Object> getMocks() {
        return List.of(userGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsCreateUser_shouldReturnUserId() {
        final var expectedName = "Test User";
        final var expectedEmail = "test@example.com";
        final var expectedPassword = "Password1@";
        final var expectedActive = true;
        final var expectedRole = "USER";

        final var aCommand = new CreateUserCommand(expectedName, expectedEmail, expectedPassword, expectedActive, expectedRole);

        when(cryptoAdapter.encode(anyString())).thenReturn(expectedPassword);
        when(userGateway.create(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = createUserUseCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(userGateway, times(1)).create(argThat(aUser ->
                Objects.equals(expectedName, aUser.getName()) &&
                        Objects.equals(expectedEmail, aUser.getEmail().getValue()) &&
                        Objects.equals(expectedActive, aUser.isActive()) &&
                        Objects.equals(expectedRole, aUser.getRole().getRole()) &&
                        Objects.nonNull(aUser.getId()) &&
                        Objects.nonNull(aUser.getCreatedAt()) &&
                        Objects.nonNull(aUser.getUpdatedAt()) &&
                        Objects.isNull(aUser.getDeletedAt())
        ));
    }

    @Test
    public void givenInvalidEmailAndPasswordAndRole_whenCallsCreateUser_thenShouldReturnDomainException() {
        final String expectedName = "Test User";
        final var expectedEmail = "test"; // fornecer um e-mail inválido
        final var expectedPassword = "password"; // fornecer uma senha inválida
        final var expectedActive = true;
        final var expectedRole = "USER";
        final var expectedEmailErrorMessage = "Email invalido";
        final var expectedPasswordErrorMessage = "Senha inválida";
        final var expectedRoleErrorMessage = "Role inválido";

        final var aCommand = new CreateUserCommand(expectedName, expectedEmail, expectedPassword, expectedActive, expectedRole);

        when(cryptoAdapter.encode(anyString())).thenReturn(expectedPassword);
        final var notification = createUserUseCase.execute(aCommand).getLeft();

        Assertions.assertTrue(notification.getErrors().stream().anyMatch(error -> error.message().equals(expectedEmailErrorMessage)));
        Assertions.assertTrue(notification.getErrors().stream().anyMatch(error -> error.message().equals(expectedPasswordErrorMessage)));

        verify(userGateway, times(0)).create(any());
        verify(cryptoAdapter, times(1)).encode(anyString());
    }

    @Test
    public void givenInvalidName_whenCallsCreateUser_thenShouldReturnDomainException() {
        final String expectedName = ""; // fornecer um nome inválido
        final var expectedEmail = "test@example.com";
        final var expectedPassword = "Password@1";
        final var expectedActive = true;
        final var expectedRole = "USER";
        final var expectedErrorMessage = "'name' should not be empty";

        final var aCommand = new CreateUserCommand(expectedName, expectedEmail, expectedPassword, expectedActive, expectedRole);

        when(cryptoAdapter.encode(anyString())).thenReturn(expectedPassword);

        final var notification = createUserUseCase.execute(aCommand).getLeft();

        Assertions.assertTrue(notification.getErrors().stream().anyMatch(error -> error.message().equals(expectedErrorMessage)));

        verify(userGateway, times(0)).create(any());
        verify(cryptoAdapter, times(1)).encode(anyString());
    }

}
