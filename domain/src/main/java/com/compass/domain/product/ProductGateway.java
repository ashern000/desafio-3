package com.compass.domain.product;

import java.util.List;
import java.util.Optional;

/**
 * ProductGateway interface defines the contract for managing Product entities.
 */
public interface ProductGateway {

    /**
     * Creates a new product.
     *
     * @param product The product to be created.
     * @return The created product.
     */
    Product create(Product product);

    /**
     * Deletes a product by its unique identifier.
     *
     * @param anId The unique identifier of the product to be deleted.
     */
    void delete(ProductID anId);

    /**
     * Updates an existing product.
     *
     * @param product The product to be updated.
     * @return The updated product.
     */
    Product update(Product product);

    /**
     * Finds a product by its unique identifier.
     *
     * @param anId The unique identifier of the product to be found.
     * @return An Optional containing the found product, or an empty Optional if no product is found.
     */
    Optional<Product> findById(ProductID anId);

    /**
     * Finds a product by its name.
     *
     * @param name The name of the product to be found.
     * @return An Optional containing the found product, or an empty Optional if no product is found.
     */
    Optional<Product> findByName(String name);

    /**
     * Retrieves all products.
     *
     * @return A list of all products.
     */
    List<Product> findAll();
}
