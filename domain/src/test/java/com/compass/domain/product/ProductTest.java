package com.compass.domain.product;

import com.compass.domain.exceptions.DomainException;
import com.compass.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    public void givenAValidProduct_whenCallNewProduct() {

        final var expectedName = "Café";
        final var expectedQuantity = 10;
        final var expectedDescription = "Café para reuniões";

        final Product product = Product.newProduct(expectedName, expectedDescription, expectedQuantity);


        Assertions.assertNotNull(product);
        Assertions.assertEquals(expectedName, product.getName());
        Assertions.assertEquals(expectedQuantity, product.getQuantity());
        Assertions.assertEquals(expectedDescription, product.getDescription());
        Assertions.assertNotNull(product.getCreatedAt());
        Assertions.assertNotNull(product.getUpdatedAt());
        Assertions.assertNull(product.getDeletedAt());

    }

    @Test
    public void givenAnInvalidNullName_whenCallNewProduct_thenShouldReceiveError() {
        final String expectedName = null;
        final var expectedQuantity = 10;
        final var expectedDescription = "Café para reuniões";
        final var expectedErrorMessage = "'name' should be not null";
        final var expectedErrorCount = 1;

        final Product product = Product.newProduct(expectedName, expectedDescription, expectedQuantity);

        final var actualException = Assertions.assertThrows(DomainException.class, ()-> product.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void givenAnInvalidEmptyName_whenCallNewProduct_thenShouldReceiveError() {
        final String expectedName = "  ";
        final var expectedQuantity = 10;
        final var expectedDescription = "Café para reuniões";
        final var expectedErrorMessage = "'name' should be not empty";
        final var expectedErrorCount = 1;

        final Product product = Product.newProduct(expectedName, expectedDescription, expectedQuantity);

        final var actualException = Assertions.assertThrows(DomainException.class, ()-> product.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }
}
