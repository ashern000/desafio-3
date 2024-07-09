package com.compass.domain.product;

import com.compass.domain.exceptions.DomainException;
import com.compass.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    public void givenAValidProduct_whenCallNewProduct() {

        final var expectedName = "Café";
        final var expectedDescription = "Café para reuniões";
        final var expectedActive = true;
        final var expectedPrice = 10.3;

        final Product product = Product.newProduct(expectedName, expectedDescription, expectedActive, expectedPrice);


        Assertions.assertNotNull(product);
        Assertions.assertEquals(expectedName, product.getName());
        Assertions.assertEquals(expectedDescription, product.getDescription());
        Assertions.assertEquals(expectedActive, product.isActive());
        Assertions.assertEquals(expectedPrice, product.getPrice());
        Assertions.assertTrue(product.isActive());
        Assertions.assertNotNull(product.getCreatedAt());
        Assertions.assertNotNull(product.getUpdatedAt());
        Assertions.assertNull(product.getDeletedAt());

    }

    @Test
    public void givenAnInvalidNullName_whenCallNewProduct_thenShouldReceiveError() {
        final String expectedName = null;
        final var expectedDescription = "Café para reuniões";
        final var expectedActive = true;
        final var expectedPrice = 10.3;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final Product product = Product.newProduct(expectedName, expectedDescription, expectedActive, expectedPrice);

        final var actualException = Assertions.assertThrows(DomainException.class, ()-> product.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void givenAnInvalidEmptyName_whenCallNewProduct_thenShouldReceiveError() {
        final String expectedName = "  ";
        final var expectedDescription = "Café para reuniões";
        final var expectedActive = true;
        final var expectedPrice = 10.3;
        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedErrorCount = 1;

        final Product product = Product.newProduct(expectedName, expectedDescription, expectedActive, expectedPrice);

        final var actualException = Assertions.assertThrows(DomainException.class, ()-> product.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void givenAValidProduct_whenCallDeactive_shouldInactive() throws InterruptedException {
        final var expectedName = "Café";
        final var expectedDescription = "Café para reuniões";
        final var expectedActive = false;
        final var expectedPrice = 10.3;

        final Product product = Product.newProduct(expectedName, expectedDescription, true, expectedPrice);
        final var aUpdatedAt = product.getUpdatedAt();
        Thread.sleep(200L);
        final var actualProduct = product.deactivate();

        Assertions.assertNotNull(actualProduct);
        Assertions.assertEquals(expectedName, actualProduct.getName());
        Assertions.assertEquals(expectedDescription, actualProduct.getDescription());
        Assertions.assertEquals(expectedActive, actualProduct.isActive());
        Assertions.assertNotNull(actualProduct.getCreatedAt());
        Assertions.assertNotNull(actualProduct.getUpdatedAt());
        Assertions.assertTrue(actualProduct.getUpdatedAt().isAfter(aUpdatedAt));
        Assertions.assertNotNull(actualProduct.getDeletedAt());

    }

    @Test
    public void givenAValidProduct_whenCallActive_shouldActive() throws InterruptedException {
        final var expectedName = "Café";
        final var expectedDescription = "Café para reuniões";
        final var expectedActive = true;
        final var expectedPrice = 10.3;

        final Product product = Product.newProduct(expectedName, expectedDescription, false, expectedPrice);
        final var aUpdatedAt = product.getUpdatedAt();
        Thread.sleep(200L);
        final var actualProduct = product.activate();

        Assertions.assertNotNull(actualProduct);
        Assertions.assertEquals(expectedName, actualProduct.getName());
        Assertions.assertEquals(expectedDescription, actualProduct.getDescription());
        Assertions.assertEquals(expectedActive, actualProduct.isActive());
        Assertions.assertNotNull(actualProduct.getCreatedAt());
        Assertions.assertNotNull(actualProduct.getUpdatedAt());
        Assertions.assertTrue(actualProduct.getUpdatedAt().isAfter(aUpdatedAt));
        Assertions.assertNull(actualProduct.getDeletedAt());

    }
}
