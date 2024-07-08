package com.compass.application.product.create;

import com.compass.application.product.UseCaseTest;
import com.compass.domain.product.ProductGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Objects;

public class CreateProductUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaulCreateProductUseCase createProductUseCase;

    @Mock
    private ProductGateway productGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(productGateway);
    }

    @Test

    public void givenAValidCommand_whenCallsCreateProduct_shouldReturnCategoryId() {
        final var expectedName = "Café";
        final var expectedDescription = "Café importado selo premium";
        final var expectedActive = true;

        final var aCommand = CreateProductCommand.with(expectedName, expectedDescription, expectedActive);

        when(productGateway.create(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = createProductUseCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(productGateway, times(1)).create(argThat(aProduct ->

            Objects.equals(expectedName, aProduct.getName()) &&
                    Objects.equals(expectedDescription, aProduct.getDescription()) &&
                    Objects.equals(expectedActive, aProduct.isActive()) &&
                    Objects.nonNull(aProduct.getId()) &&
                    Objects.nonNull(aProduct.getCreatedAt()) &&
                    Objects.nonNull(aProduct.getUpdatedAt()) &&
                    Objects.isNull(aProduct.getDeletedAt())
        ));
    }

    @Test
    public void givenAnInvalidName_whenCallsCreateProduct_thenShouldReturnDomainException(){
        final String expectedName = null;
        final var expectedDescription = "Café importado selo premium";
        final var expectedActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand = CreateProductCommand.with(expectedName, expectedDescription, expectedActive);

        final var notification = createProductUseCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        verify(productGateway, times(0)).create(any());
    }

    @Test
    public void givenAnInvalidDescription_whenCallsCreateProduct_thenShouldReturnDomainException(){
        final String expectedName = "Café";
        final var expectedDescription = "";
        final var expectedActive = true;
        final var expectedErrorMessage = "'description' should not be empty";
        final var expectedErrorCount = 1;

        final var aCommand = CreateProductCommand.with(expectedName, expectedDescription, expectedActive);

        final var notification = createProductUseCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        verify(productGateway, times(0)).create(any());
    }

    @Test
    public void givenADescriptionWithMore1250Chars_whenCallsCreateProduct_thenShouldReturnDomainException(){
        final var expectedDescription = """
                Não obstante, o julgamento imparcial das eventualidades possibilita uma melhor visão global das posturas dos órgãos dirigentes com relação às suas atribuições
                Não obstante, o julgamento imparcial das eventualidades possibilita uma melhor visão global das posturas dos órgãos dirigentes com relação às suas atribuições
                Não obstante, o julgamento imparcial das eventualidades possibilita uma melhor visão global das posturas dos órgãos dirigentes com relação às suas atribuições
                Não obstante, o julgamento imparcial das eventualidades possibilita uma melhor visão global das posturas dos órgãos dirigentes com relação às suas atribuições
                Não obstante, o julgamento imparcial das eventualidades possibilita uma melhor visão global das posturas dos órgãos dirigentes com relação às suas atribuições
                Não obstante, o julgamento imparcial das eventualidades possibilita uma melhor visão global das posturas dos órgãos dirigentes com relação às suas atribuições
                Não obstante, o julgamento imparcial das eventualidades possibilita uma melhor visão global das posturas dos órgãos dirigentes com relação às suas atribuições
                Não obstante, o julgamento imparcial das eventualidades possibilita uma melhor visão global das posturas dos órgãos dirigentes com relação às suas atribuições
                """;
        final String expectedName = "Café";
        final var expectedActive = true;
        final var expectedErrorMessage = "'description' must between 1 and 1250 characters";
        final var expectedErrorCount = 1;

        final var aCommand = CreateProductCommand.with(expectedName, expectedDescription, expectedActive);

        final var notification = createProductUseCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        verify(productGateway, times(0)).create(any());
    }
}
