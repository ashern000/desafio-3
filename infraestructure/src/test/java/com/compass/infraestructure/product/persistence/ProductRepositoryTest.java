package com.compass.infraestructure.product.persistence;

import com.compass.domain.product.Product;
import com.compass.infraestructure.MySQLGatewayTest;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@MySQLGatewayTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;


    @Test

    public void givenAnInvalidNullName_whenCallsSave_shouldReturnError() {
        final var aProduct = Product.newProduct("Café", "Café premium", true, 10.1);
        final var expectedPropertyError = "name";
        final var expectedErrorMessage = "not-null property references a null or transient value : com.compass.infraestructure.product.persistence.ProductJpaEntity.name";

        final var anEntity = ProductJpaEntity.from(aProduct);

        anEntity.setName(null);

        final var exception = Assertions.assertThrows(DataIntegrityViolationException.class, () -> productRepository.save(anEntity));

        final var actualCause = Assertions.assertInstanceOf(PropertyValueException.class,exception.getCause());

        Assertions.assertEquals(expectedPropertyError, actualCause.getPropertyName());
        Assertions.assertEquals(expectedErrorMessage, actualCause.getMessage());
    }

    @Test
    public void givenAnInvalidNullCreatedAt_whenCallsSave_shouldReturnError() {
        final var aProduct = Product.newProduct("Café", "Café premium", true, 10.1);
        final var expectedPropertyError = "createdAt";
        final var expectedErrorMessage = "not-null property references a null or transient value : com.compass.infraestructure.product.persistence.ProductJpaEntity.createdAt";

        final var anEntity = ProductJpaEntity.from(aProduct);

        anEntity.setCreatedAt(null);

        final var exception = Assertions.assertThrows(DataIntegrityViolationException.class, () -> productRepository.save(anEntity));

        final var actualCause = Assertions.assertInstanceOf(PropertyValueException.class,exception.getCause());

        Assertions.assertEquals(expectedPropertyError, actualCause.getPropertyName());
        Assertions.assertEquals(expectedErrorMessage, actualCause.getMessage());
    }

    @Test
    public void givenAnInvalidNullUpdatedAt_whenCallsSave_shouldReturnError() {
        final var aProduct = Product.newProduct("Café", "Café premium", true, 10.1);
        final var expectedPropertyError = "updatedAt";
        final var expectedErrorMessage = "not-null property references a null or transient value : com.compass.infraestructure.product.persistence.ProductJpaEntity.updatedAt";

        final var anEntity = ProductJpaEntity.from(aProduct);

        anEntity.setUpdatedAt(null);

        final var exception = Assertions.assertThrows(DataIntegrityViolationException.class, () -> productRepository.save(anEntity));

        final var actualCause = Assertions.assertInstanceOf(PropertyValueException.class,exception.getCause());

        Assertions.assertEquals(expectedPropertyError, actualCause.getPropertyName());
        Assertions.assertEquals(expectedErrorMessage, actualCause.getMessage());
    }
}
