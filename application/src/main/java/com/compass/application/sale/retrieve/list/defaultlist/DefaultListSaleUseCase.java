package com.compass.application.sale.retrieve.list.defaultlist;

import com.compass.domain.sale.SaleGateway;

import java.util.List;

public class DefaultListSaleUseCase extends ListSaleUseCase {

    private SaleGateway gateway;

    public DefaultListSaleUseCase(final SaleGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public List<ListSaleOutput> execute() {
        return this.gateway.findAll().stream().map(ListSaleOutput::from).toList();
    }
}