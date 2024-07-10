package com.compass.domain.sale;

import com.compass.domain.product.ProductID;
import com.compass.domain.sale.Sale;

import java.util.List;
import java.util.Optional;

/**
 * SaleGateway interface defines the contract for managing Sale entities.
 */
public interface SaleGateway {

    /**
     * Creates a new sale.
     *
     * @param sale The sale to be created.
     * @return The created sale.
     */
    Sale create(Sale sale);

    /**
     * Deletes a sale by its unique identifier.
     *
     * @param anId The unique identifier of the sale to be deleted.
     */
    void delete(SaleID anId);

    /**
     * Updates an existing sale.
     *
     * @param sale The sale to be updated.
     * @return The updated sale.
     */
    Sale update(Sale sale);

    /**
     * Finds a sale by its unique identifier.
     *
     * @param anId The unique identifier of the sale to be found.
     * @return An Optional containing the found sale, or an empty Optional if no sale is found.
     */
    Optional<Sale> findById(SaleID anId);

    /**
     * Finds sales containing a specific product.
     *
     * @param productId The unique identifier of the product to be found in sales.
     * @return A list of sales containing the product.
     */
    List<Sale> findByProductId(ProductID productId);

    /**
     * Retrieves all sales.
     *
     * @return A list of all sales.
     */
    List<Sale> findAll();
}
