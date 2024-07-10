package com.compass.infraestructure.sale.persistence;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class SaleProductID implements Serializable {

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "sale_id", nullable = false)
    private String saleId;

    public SaleProductID () {}

    private SaleProductID (final String aProductId, final String aSaleId) {
        this.productId = aProductId;
        this.saleId = aSaleId;
    }

    public static SaleProductID from(final String aProductId, final String aSaleId) {
        return new SaleProductID(aProductId, aSaleId);
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SaleProductID that = (SaleProductID) o;
        return productId.equals(that.productId) && saleId.equals(that.saleId);
    }

    @Override
    public int hashCode() {
        int result = productId.hashCode();
        result = 31 * result + saleId.hashCode();
        return result;
    }
}
