package com.compass.application.user.login;

import com.compass.application.UseCaseTest;
import com.compass.application.adapters.CryptoAdapter;
import com.compass.application.adapters.TokenAdapter;
import com.compass.domain.exceptions.DomainException;
import com.compass.domain.user.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class LoginUserUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultLoginUserUseCase loginUserUseCase;

    @Mock
    private UserGateway userGateway;

    @Mock
    private CryptoAdapter cryptoAdapter;

    @Mock
    private TokenAdapter tokenAdapter;

    @Override
    protected List<Object> getMocks() {
        return List.of(userGateway, cryptoAdapter, tokenAdapter);
    }

    @Test
    public void givenAValidCommand_whenCallsLoginUser_shouldReturnUserIdAndToken() {
        final var expectedEmail = "test@example.com";
        final var expectedPassword = "Password1@";
        final var expectedToken = "token";

        final var aCommand = LoginUserCommand.with(expectedEmail, expectedPassword);

        User user = mock(User.class);
        final var mockUserID = UserID.from("id");
        final var mockPassword = Password.with(expectedPassword);
        when(user.getId()).thenReturn(mockUserID);
        when(user.getEmail()).thenReturn(Email.newEmail(expectedEmail));
        when(user.getPassword()).thenReturn(mockPassword);

        when(userGateway.findByEmail(any())).thenReturn(Optional.of(user));
        when(cryptoAdapter.matches(anyString(), anyString())).thenReturn(true);
        when(tokenAdapter.generateToken(any(), any(), any())).thenReturn(expectedToken);

        final var actualOutput = loginUserUseCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals("id", actualOutput.id());
        Assertions.assertEquals(expectedToken, actualOutput.token());

        verify(userGateway, times(2)).findByEmail(any());
        verify(cryptoAdapter, times(1)).matches(anyString(), anyString());
        verify(tokenAdapter, times(1)).generateToken(any(), any(), any());
    }

    @Test
    public void givenAnInvalidEmail_whenCallsLoginUser_thenShouldReturnDomainException(){
        final var expectedEmail = "test"; // fornecer um e-mail inválido
        final var expectedPassword = "Password1@";
        final var expectedErrorMessage = "Usuário ou senha inválidos";

        final var aCommand = LoginUserCommand.with(expectedEmail, expectedPassword);

        when(userGateway.findByEmail(any())).thenReturn(Optional.empty());

        final var thrown = Assertions.assertThrows(
                DomainException.class,
                () -> loginUserUseCase.execute(aCommand)
        );

        Assertions.assertEquals(expectedErrorMessage, thrown.getMessage());

        verify(userGateway, times(1)).findByEmail(any());
        verify(cryptoAdapter, times(0)).matches(anyString(), anyString());
    }


    @Test
    public void givenAnInvalidPassword_whenCallsLoginUser_thenShouldReturnDomainException(){
        final var expectedEmail = "test@test.com"; // fornecer um e-mail inválido
        final var expectedPassword = "password";
        final var expectedErrorMessage = "Usuário ou senha inválidos";

        final var aCommand = LoginUserCommand.with(expectedEmail, expectedPassword);

        when(userGateway.findByEmail(any())).thenReturn(Optional.empty());

        final var thrown = Assertions.assertThrows(
                DomainException.class,
                () -> loginUserUseCase.execute(aCommand)
        );

        Assertions.assertEquals(expectedErrorMessage, thrown.getMessage());

        verify(userGateway, times(1)).findByEmail(any());
        verify(cryptoAdapter, times(0)).matches(anyString(), anyString());
    }
}
