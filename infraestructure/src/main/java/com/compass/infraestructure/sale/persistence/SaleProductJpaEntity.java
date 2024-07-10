package com.compass.infraestructure.sale.persistence;

import com.compass.domain.product.ProductID;
import com.compass.infraestructure.product.persistence.ProductJpaEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "sale_products")
public class SaleProductJpaEntity {

    @EmbeddedId
    private SaleProductID id;

    @ManyToOne
    @MapsId("saleId")
    private SaleJpaEntity sale;
    public SaleProductJpaEntity() {}

    private SaleProductJpaEntity(final SaleJpaEntity aSale, final ProductID aProduct) {
        this.id = SaleProductID.from(aProduct.getValue(), aSale.getId());
        this.sale = aSale;
    }

    public static SaleProductJpaEntity from (final SaleJpaEntity aProduct, final ProductID aProductId) {
        return new SaleProductJpaEntity(aProduct, aProductId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SaleProductJpaEntity that = (SaleProductJpaEntity) o;
        return getId().equals(that.getId()) && getSale().equals(that.getSale());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getSale().hashCode();
        return result;
    }

    public SaleProductID getId() {
        return id;
    }

    public void setId(SaleProductID id) {
        this.id = id;
    }

    public SaleJpaEntity getSale() {
        return sale;
    }

    public void setSale(SaleJpaEntity sale) {
        this.sale = sale;
    }
}

