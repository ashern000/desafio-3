package com.compass.infraestructure.sale;

import com.compass.domain.product.ProductID;
import com.compass.domain.sale.Sale;
import com.compass.domain.sale.SaleGateway;
import com.compass.domain.sale.SaleID;
import com.compass.infraestructure.sale.persistence.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleMySQLGateway implements SaleGateway {
    private SaleRepository repository;

    public SaleMySQLGateway(final SaleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Sale create(Sale sale) {
        return null;
    }

    @Override
    public void delete(SaleID anId) {

    }

    @Override
    public Sale update(Sale sale) {
        return null;
    }

    @Override
    public Optional<Sale> findById(SaleID anId) {
        return Optional.empty();
    }

    @Override
    public List<Sale> findByProductId(ProductID productId) {
        return List.of();
    }

    @Override
    public List<Sale> findAll() {
        return List.of();
    }
}
