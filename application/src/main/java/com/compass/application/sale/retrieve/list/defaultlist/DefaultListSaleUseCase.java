package com.compass.application.sale.retrieve.list.defaultlist;

import com.compass.domain.product.Product;
import com.compass.domain.product.ProductGateway;
import com.compass.domain.sale.SaleGateway;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultListSaleUseCase extends ListSaleUseCase {

    private SaleGateway gateway;

    private ProductGateway productGateway;

    public DefaultListSaleUseCase(final SaleGateway gateway, final ProductGateway productGateway) {
        this.gateway = gateway;
        this.productGateway = productGateway;
    }

    @Override
    public List<ListSaleOutput> execute() {
        return this.gateway.findAll().stream()
                .map(sale -> {
                    var activeProducts = sale.getProductsIds().stream()
                            .filter(productId -> {
                                var productOpt = productGateway.findById(productId);
                                return productOpt.isPresent() && productOpt.get().isActive();
                            })
                            .collect(Collectors.toList());
                    sale.setProductIds(activeProducts);
                    return sale;
                })
                .map(ListSaleOutput::from)
                .collect(Collectors.toList());

    }
}