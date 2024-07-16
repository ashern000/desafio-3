package com.compass.domain.sale;

import com.compass.domain.exceptions.DomainException;
import com.compass.domain.product.ProductID;
import com.compass.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class SaleTest {

    @Test
    public void givenAValidSale_whenCallNewSale() {

        final var expectedProductIds = Arrays.asList(ProductID.unique(), ProductID.unique());

        final Sale sale = Sale.newSale(expectedProductIds, 1);

        Assertions.assertNotNull(sale);
        Assertions.assertEquals(expectedProductIds, sale.getProductsIds());
        Assertions.assertNotNull(sale.getCreatedAt());
        Assertions.assertNotNull(sale.getUpdatedAt());
    }

    @Test
    public void givenAnInvalidNullProductIds_whenCallNewSale_thenShouldReceiveError() {
        final List<ProductID> expectedProductIds = List.of();
        final var expectedErrorMessage = "'productIds' should not be null or empty";
        final var expectedErrorCount = 1;

        final Sale sale = Sale.newSale(expectedProductIds, 1);

        final var actualException = Assertions.assertThrows(DomainException.class, ()-> sale.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void givenAnInvalidEmptyProductIds_whenCallNewSale_thenShouldReceiveError() {
        final List<ProductID> expectedProductIds = List.of();
        final var expectedErrorMessage = "'productIds' should not be null or empty";
        final var expectedErrorCount = 1;

        final Sale sale = Sale.newSale(expectedProductIds, 1);

        final var actualException = Assertions.assertThrows(DomainException.class, ()-> sale.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }
}
