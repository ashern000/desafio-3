package com.compass.infraestructure.product;

import com.compass.domain.product.Product;
import com.compass.infraestructure.MySQLGatewayTest;
import com.compass.infraestructure.product.persistence.ProductJpaEntity;
import com.compass.infraestructure.product.persistence.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@MySQLGatewayTest
public class ProductMySQLGatewayTest {
    @Autowired
    private ProductMySQLGateway productGateway;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void givenAValidProduct_whenCallsCreate_shouldReturnANewProduct(){
        final var expectedName = "Café";
        final var expectedDescription = "Café premium";
        final var expectedIsActive = true;
        final var expectedPrice = 10.1;

        final var aProduct = Product.newProduct(expectedName, expectedDescription, expectedIsActive, expectedPrice);

        Assertions.assertEquals(0, productRepository.count());

        final var actualProduct = productGateway.create(aProduct);

        Assertions.assertEquals(1, productRepository.count());

        Assertions.assertEquals(aProduct.getId(), actualProduct.getId());
        Assertions.assertEquals(aProduct.getName(), actualProduct.getName());
        Assertions.assertEquals(aProduct.getDescription(), actualProduct.getDescription());
        Assertions.assertEquals(aProduct.getPrice(), actualProduct.getPrice());
        Assertions.assertEquals(aProduct.isActive(), actualProduct.isActive());
        Assertions.assertEquals(aProduct.getCreatedAt(), actualProduct.getCreatedAt());
        Assertions.assertEquals(aProduct.getUpdatedAt(), actualProduct.getUpdatedAt());
        Assertions.assertEquals(aProduct.getDeletedAt(), actualProduct.getDeletedAt());
        Assertions.assertNull(actualProduct.getDeletedAt());

        final var productPersistence = productRepository.findById(aProduct.getId().getValue()).get();

        Assertions.assertEquals(aProduct.getId().getValue(), productPersistence.getId());
        Assertions.assertEquals(aProduct.getName(), productPersistence.getName());
        Assertions.assertEquals(aProduct.getDescription(), productPersistence.getDescription());
        Assertions.assertEquals(aProduct.getPrice(), productPersistence.getPrice());
        Assertions.assertEquals(aProduct.isActive(), productPersistence.isActive());
        Assertions.assertEquals(aProduct.getCreatedAt(), productPersistence.getCreatedAt());
        Assertions.assertEquals(aProduct.getUpdatedAt(), productPersistence.getUpdatedAt());
        Assertions.assertEquals(aProduct.getDeletedAt(), productPersistence.getDeletedAt());
        Assertions.assertNull(productPersistence.getDeletedAt());
    }

    @Test
    public void givenAValidProduct_whenCallsUpdate_shouldReturnAnUpdatedProduct(){
        final var expectedName = "Cafe";
        final var expectedDescription = "Café premium";
        final var expectedIsActive = true;
        final var expectedPrice = 10.1;

        final var aProduct = Product.newProduct("Caf", "efac", expectedIsActive, 1);

        Assertions.assertEquals(0, productRepository.count());

        productRepository.saveAndFlush(ProductJpaEntity.from(aProduct));

        Assertions.assertEquals(1, productRepository.count());

        final var aUpdatedProduct = aProduct.clone().update(expectedName, expectedDescription, expectedIsActive);

        final var actualProduct = productGateway.update(aUpdatedProduct);

        Assertions.assertEquals(1, productRepository.count());

        Assertions.assertEquals(aProduct.getId(), actualProduct.getId());
        Assertions.assertEquals(expectedName, actualProduct.getName());
        Assertions.assertEquals(expectedDescription, actualProduct.getDescription());
        Assertions.assertEquals(aProduct.getPrice(), actualProduct.getPrice());
        Assertions.assertEquals(aProduct.isActive(), actualProduct.isActive());
        Assertions.assertEquals(aProduct.getCreatedAt(), actualProduct.getCreatedAt());
        Assertions.assertTrue(aProduct.getUpdatedAt().isBefore(actualProduct.getUpdatedAt()));
        Assertions.assertEquals(aProduct.getDeletedAt(), actualProduct.getDeletedAt());
        Assertions.assertNull(actualProduct.getDeletedAt());

        final var productPersistence = productRepository.findById(aProduct.getId().getValue()).get();

        Assertions.assertEquals(aProduct.getId().getValue(), productPersistence.getId());
        Assertions.assertEquals(aUpdatedProduct.getName(), productPersistence.getName());
        Assertions.assertEquals(aUpdatedProduct.getDescription(), productPersistence.getDescription());
        Assertions.assertEquals(aProduct.getPrice(), productPersistence.getPrice());
        Assertions.assertEquals(aUpdatedProduct.isActive(), productPersistence.isActive());
        Assertions.assertEquals(aProduct.getCreatedAt(), productPersistence.getCreatedAt());
        Assertions.assertTrue(aProduct.getUpdatedAt().isBefore(productPersistence.getUpdatedAt()));
        Assertions.assertEquals(aProduct.getDeletedAt(), productPersistence.getDeletedAt());
        Assertions.assertNull(productPersistence.getDeletedAt());
    }
}
