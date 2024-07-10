package com.compass.application.product.retrieve.list;

import com.compass.domain.product.ProductGateway;

import java.util.List;

public class DefaultListProductUseCase extends ListProductUseCase {

    private ProductGateway gateway;

    public DefaultListProductUseCase(final ProductGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public List<ListProductOutput> execute() {
        return this.gateway.findAll().stream().map(ListProductOutput::from).toList();
    }
}
