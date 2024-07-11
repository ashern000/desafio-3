package com.compass.application.product.retrieve.get;

import com.compass.application.UseCaseTest;
import com.compass.domain.exceptions.DomainException;
import com.compass.domain.product.Product;
import com.compass.domain.product.ProductGateway;
import com.compass.domain.product.ProductID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

public class GetProductUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultGetProductUseCase defaultGetProductUseCase;

    @Mock
    private ProductGateway productGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(productGateway);
    }

    @Test
    public void givenAValidId_whenCallsGetProduct_shouldReturnProduct() {
        final var expectedName = "Café";
        final var expectedDescription = "Café importado selo premium";
        final var expectedActive = true;
        final var expectedPrice = 10.3;
        final var expectedQuantity = 10;

        final var aProduct = Product.newProduct(expectedName, expectedDescription, expectedActive, expectedPrice, expectedQuantity);

        final var anId = aProduct.getId();

        final var aCommand = GetProductCommand.with(anId.getValue());

        when(productGateway.findById(eq(anId))).thenReturn(Optional.of(aProduct.clone()));

        final var actualProduct = defaultGetProductUseCase.execute(aCommand);

        Assertions.assertEquals(anId.getValue(), actualProduct.id());
        Assertions.assertEquals(expectedName, actualProduct.name());
        Assertions.assertEquals(expectedDescription, actualProduct.description());
        Assertions.assertEquals(expectedPrice, actualProduct.price());
        Assertions.assertEquals(expectedActive, actualProduct.active());
        Assertions.assertEquals(aProduct.getCreatedAt(), actualProduct.createdAt());
        Assertions.assertEquals(aProduct.getUpdatedAt(), actualProduct.updatedAt());
        Assertions.assertEquals(aProduct.getDeletedAt(), actualProduct.deletedAt());
        Assertions.assertNull(actualProduct.deletedAt());

        Assertions.assertEquals(GetProductOutput.from(aProduct), actualProduct);
    }


    @Test

    public void givenAnInvalidId_whenCallsGetProduct_shouldReturnNotFound() {
        final var anId = ProductID.from("123");
        final var aCommand = GetProductCommand.with(anId.getValue());
        final var expectedErrorMessage = "Product with ID 123 was not found";

        when(productGateway.findById(eq(anId))).thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(DomainException.class, ()-> defaultGetProductUseCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

    }

    @Test

    public void givenAnInvalidId_whenGatewayThrowsException_shouldReturnException() {
        final var anId = ProductID.from("123");
        final var aCommand = GetProductCommand.with(anId.getValue());
        final var expectedErrorMessage = "Gateway Error";


        when(productGateway.findById(eq(anId))).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var actualException = Assertions.assertThrows(IllegalStateException.class, ()-> defaultGetProductUseCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

    }


}
