package com.compass.infraestructure.sale.persistence;

import com.compass.domain.product.Product;
import com.compass.domain.sale.Sale;
import com.compass.domain.sale.SaleID;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "sales")
public class SaleJpaEntity {

    @Id
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "sale_id")
    private List<SaleProductJpaEntity> saleProducts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<SaleProductJpaEntity> getSaleProducts() {
        return saleProducts;
    }

    public void setSaleProducts(List<SaleProductJpaEntity> saleProducts) {
        this.saleProducts = saleProducts;
    }

    public SaleJpaEntity() {}

}
