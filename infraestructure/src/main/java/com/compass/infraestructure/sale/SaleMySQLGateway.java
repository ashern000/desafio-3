package com.compass.infraestructure.sale;

import com.compass.domain.product.ProductID;
import com.compass.domain.sale.Sale;
import com.compass.domain.sale.SaleGateway;
import com.compass.domain.sale.SaleID;
import com.compass.infraestructure.sale.persistence.SaleJpaEntity;
import com.compass.infraestructure.sale.persistence.SaleProductJpaEntity;
import com.compass.infraestructure.sale.persistence.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SaleMySQLGateway implements SaleGateway {
    private final SaleRepository repository;

    public SaleMySQLGateway(final SaleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Sale create(final Sale sale) {
        return save(sale);
    }

    @Override
    public void delete(SaleID anId) {
        this.repository.deleteById(anId.getValue());
    }

    @Override
    public Sale update(Sale sale) {
        return save(sale);
    }

    private Sale save(Sale sale) {
        return this.repository.save(SaleJpaEntity.from(sale)).toDomain();
    }

    @Override
    public Optional<Sale> findById(SaleID anId) {
        return this.repository.findById(anId.getValue()).map(SaleJpaEntity::toDomain);
    }

    @Override
    public boolean findByProductId(ProductID productId) {
        return this.repository.existsProductInSale(productId.getValue());
    }

    @Override
    public List<Sale> findAll() {
        return this.repository.findAll()
                .stream()
                .map(SaleJpaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Sale> findAllSalesByProductId(ProductID productID) {
        System.out.println(this.repository.findSalesByProductId(productID.getValue()).stream()
                .map(SaleJpaEntity::toDomain)
                .collect(Collectors.toList()));
        return this.repository.findSalesByProductId(productID.getValue()).stream()
                .map(SaleJpaEntity::toDomain)
                .collect(Collectors.toList());
    }


}
