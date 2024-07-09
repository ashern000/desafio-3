package com.compass.application.product.update;

import com.compass.application.product.UseCaseTest;
import com.compass.domain.exceptions.NotFoundException;
import com.compass.domain.product.Product;
import com.compass.domain.product.ProductGateway;
import com.compass.domain.product.ProductID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class UpdateProductUseCaseTest extends UseCaseTest {

    // TODO: Fix test for update atribute price

    @InjectMocks
    private DefaultUpdateProductUseCase updateProductUseCase;

    @Mock
    private ProductGateway productGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(productGateway);
    }


    @Test
    public void givenAValidCommand_whenCallsUpdateProduct_shouldReturnCategoryId() {
        final var aProduct = Product.newProduct("Caf", null, false, 0.1);

        final var expectedName = "Café";
        final var expectedDescription = "Café importado selo premium";
        final var expectedActive = true;
        final var expectedPrice = 12.1;
        final var expectedId = aProduct.getId();




        final var aCommand = UpdateProductCommand.with(expectedId.getValue(),expectedName, expectedDescription, expectedActive);

        when(productGateway.findById(eq(expectedId))).thenReturn(Optional.of(Product.with(aProduct)));

        when(productGateway.update(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = updateProductUseCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.anId());

        verify(productGateway, times(1)).findById(eq(expectedId));

        verify(productGateway, times(1)).update(argThat(aUpdatedProduct ->

                Objects.equals(expectedName, aUpdatedProduct.getName()) &&
                        Objects.equals(expectedDescription, aUpdatedProduct.getDescription()) &&
                        Objects.equals(expectedActive, aUpdatedProduct.isActive()) &&
                        Objects.equals(expectedId,aUpdatedProduct.getId()) &&
                        Objects.equals(aProduct.getCreatedAt(),aUpdatedProduct.getCreatedAt()) &&
                        aProduct.getUpdatedAt().isBefore(aUpdatedProduct.getUpdatedAt()) &&
                        Objects.isNull(aUpdatedProduct.getDeletedAt())
        ));
    }


    @Test

    public void givenAInvalidName_whenCallsUpdateProduct_thenShouldReturnDomainException() {
        final var aProduct = Product.newProduct("Caf", null, false, 0.1);

        final String expectedName = null;
        final var expectedDescription = "Café importado selo premium";
        final var expectedActive = true;
        final var expectedId = aProduct.getId();
        final var expectedMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand = UpdateProductCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedActive);

        when(productGateway.findById(eq(expectedId))).thenReturn(Optional.of(Product.with(aProduct)));

        final var notification = updateProductUseCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedMessage, notification.firstError().message());

        verify(productGateway, times(0)).update(any());
    }

    @Test
    public void givenAValidInactiveCommand_whenCallsUpdateProduct_shouldReturnInativeProductId(){
        final var aProduct = Product.newProduct("Caf", null, true, 0.1);

        final var expectedName = "Café";
        final var expectedDescription = "Café importado selo premium";
        final var expectedActive = false;
        final var expectedId = aProduct.getId();


        final var aCommand = UpdateProductCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedActive);

        when(productGateway.findById(eq(expectedId))).thenReturn(Optional.of(Product.with(aProduct)));

        when(productGateway.update(any())).thenAnswer(returnsFirstArg());

        Assertions.assertTrue(aProduct.isActive());
        Assertions.assertNull(aProduct.getDeletedAt());

        final var actualOutput = updateProductUseCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.anId());

        verify(productGateway, times(1)).update(argThat(
                aUpdatedProduct ->
                        Objects.equals(expectedName, aUpdatedProduct.getName()) &&
                        Objects.equals(expectedDescription, aUpdatedProduct.getDescription()) &&
                        Objects.equals(expectedActive, aUpdatedProduct.isActive()) &&
                        Objects.equals(expectedId, aUpdatedProduct.getId()) &&
                        Objects.equals(aProduct.getCreatedAt(), aUpdatedProduct.getCreatedAt()) &&
                        Objects.nonNull(aUpdatedProduct.getDeletedAt()) &&
                        aProduct.getUpdatedAt().isBefore(aUpdatedProduct.getUpdatedAt())
        ));

    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnsExpection() {
        final var aProduct = Product.newProduct("Caf", null, true, 0.1);

        final var expectedName = "Café";
        final var expectedDescription = "Café importado selo premium";
        final var expectedActive = true;
        final var expectedId = aProduct.getId();
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "Gateway error";

        final var aCommand = UpdateProductCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedActive);

        when(productGateway.findById(eq(expectedId))).thenReturn(Optional.of(Product.with(aProduct)));

        when(productGateway.update(any())).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var notification = updateProductUseCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        verify(productGateway, times(1)).update(argThat(
                aUpdatedProduct ->
                        Objects.equals(expectedName, aUpdatedProduct.getName()) &&
                                Objects.equals(expectedDescription, aUpdatedProduct.getDescription()) &&
                                Objects.equals(expectedActive, aUpdatedProduct.isActive()) &&
                                Objects.equals(expectedId, aUpdatedProduct.getId()) &&
                                Objects.equals(aProduct.getCreatedAt(), aUpdatedProduct.getCreatedAt()) &&
                                Objects.isNull(aUpdatedProduct.getDeletedAt()) &&
                                aProduct.getUpdatedAt().isBefore(aUpdatedProduct.getUpdatedAt())

        ));


    }


    @Test
    public void givenACommandWithInvalidID_whenCallsUpdateProduct_shouldReturnNotFoundException() {
        final var expectedName = "Café";
        final var expectedDescription = "Café importado selo premium";
        final var expectedActive = true;
        final var expectedId = "123";
        final var expectedMessage = "Product with ID 123 was not found";

        final var aCommand = UpdateProductCommand.with(expectedId, expectedName, expectedDescription, expectedActive);

        when(productGateway.findById(eq(ProductID.from(expectedId)))).thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(NotFoundException.class, ()->updateProductUseCase.execute(aCommand));

        Assertions.assertEquals(expectedMessage, actualException.getMessage());

        verify(productGateway, times(1)).findById(eq(ProductID.from(expectedId)));

        verify(productGateway, times(0)).update(any());
    }

}
