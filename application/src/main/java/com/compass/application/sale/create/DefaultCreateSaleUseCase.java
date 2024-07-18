package com.compass.application.sale.create;

import com.compass.application.adapters.TokenAdapter;
import com.compass.domain.exceptions.DomainException;
import com.compass.domain.product.ProductGateway;
import com.compass.domain.product.ProductID;
import com.compass.domain.exceptions.NotificationException;
import com.compass.domain.sale.Sale;
import com.compass.domain.sale.SaleGateway;
import com.compass.domain.user.Email;
import com.compass.domain.user.UserGateway;
import com.compass.domain.validation.Error;
import com.compass.domain.validation.ValidationHandler;
import com.compass.domain.validation.handler.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class DefaultCreateSaleUseCase extends CreateSaleUseCase {

    private final ProductGateway productGateway;
    private final SaleGateway saleGateway;
    private final TokenAdapter tokenAdapter;
    private final UserGateway userGateway;

    public DefaultCreateSaleUseCase(final ProductGateway productGateway, final SaleGateway saleGateway, final TokenAdapter tokenAdapter, final UserGateway userGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
        this.saleGateway = Objects.requireNonNull(saleGateway);
        this.tokenAdapter = Objects.requireNonNull(tokenAdapter);
        this.userGateway = Objects.requireNonNull(userGateway);
    }

    @Override
    public CreateSaleOutput execute(final CreateSaleCommand aCommand) {
        final var productSales = aCommand.productSales();
        final var token = aCommand.token();
        final var anEmail = Email.newEmail(tokenAdapter.getSubject(token));

    System.out.println("TOKEN  "+tokenAdapter.getSubject(token));
        System.out.println(token);

        final var notification = Notification.create();
        notification.append(validateProducts(productSales));

        if (notification.hasErrors()) {
            throw new NotificationException("Could not create Aggregate Sale", notification);
        }

        final var anUser = this.userGateway.findByEmail(anEmail).orElseThrow(notFound(anEmail));
        System.out.println(anUser.getEmail().getValue()+ "  " + anUser.getId().getValue());

        double totalPrice = productSales.stream()
                .mapToDouble(productSale -> {
                    var product = productGateway.findById(ProductID.from(productSale.getProductId()));
                    return product.map(value -> value.getPrice() * productSale.getQuantity()).orElse(0.0);
                })
                .sum();

        final var aSale = Sale.newSale(productSales.stream().map(CreateSaleCommand.ProductSale::getProductId).map(ProductID::from).toList(),anUser.getId(), totalPrice);

        System.out.println(aSale.getUserId().getValue());
        return CreateSaleOutput.from(this.saleGateway.create(aSale));
    }

    private Supplier<DomainException> notFound(Email anEmail) {
        return () -> DomainException.with(new Error("Email %s was not found".formatted(anEmail.getValue())));
    }

    private ValidationHandler validateProducts(final List<CreateSaleCommand.ProductSale> productSales) {
        final var notification = Notification.create();
        if (productSales == null || productSales.isEmpty()) {
            return notification.append(new Error("No Products for insert in Sale"));
        }

        for (CreateSaleCommand.ProductSale productSale : productSales) {
            var product = productGateway.findById(ProductID.from(productSale.getProductId()));
            if (product.isEmpty()) {
                notification.append(new Error("Product with ID " + productSale.getProductId() + " does not exist"));
            } else if (product.get().getQuantity() < productSale.getQuantity()) {
                notification.append(new Error("Product with ID " + productSale.getProductId() + " does not have enough quantity available"));
            }
        }

        return notification;
    }
}
