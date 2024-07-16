package com.compass.infraestructure.configuration;

import com.compass.application.adapters.CryptoAdapter;
import com.compass.application.product.create.CreateProductUseCase;
import com.compass.application.product.create.DefaulCreateProductUseCase;
import com.compass.application.product.delete.DefaultDeleteProductUseCase;
import com.compass.application.product.delete.DeleteProductUseCase;
import com.compass.application.product.retrieve.get.DefaultGetProductUseCase;
import com.compass.application.product.retrieve.get.GetProductUseCase;
import com.compass.application.product.retrieve.list.DefaultListProductUseCase;
import com.compass.application.product.retrieve.list.ListProductUseCase;
import com.compass.application.product.update.DefaultUpdateProductUseCase;
import com.compass.application.product.update.UpdateProductUseCase;
import com.compass.application.sale.create.CreateSaleUseCase;
import com.compass.application.sale.create.DefaultCreateSaleUseCase;
import com.compass.application.sale.delete.DefaultDeleteSaleUseCase;
import com.compass.application.sale.retrieve.list.defaultlist.DefaultListSaleUseCase;
import com.compass.application.sale.retrieve.list.filtersalesbydate.DefaultFilterSalesByDateUseCase;
import com.compass.application.sale.retrieve.list.generatesalesreport.DefaultGenerateSalesReportUseCase;
import com.compass.application.sale.update.DefaultUpdateSaleUseCase;
import com.compass.application.user.create.DefaultCreateUserUseCase;
import com.compass.domain.product.ProductGateway;
import com.compass.domain.sale.SaleGateway;
import com.compass.domain.user.UserGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {

    private final ProductGateway productGateway;

    private final SaleGateway saleGateway;

    private final UserGateway userGateway;

    private final CryptoAdapter cryptoAdapter;

    public UseCasesConfig(final ProductGateway productGateway, final SaleGateway saleGateway, final UserGateway userGateway, final CryptoAdapter cryptoAdapter) {
        this.productGateway = productGateway;
        this.saleGateway = saleGateway;
        this.userGateway = userGateway;
        this.cryptoAdapter = cryptoAdapter;
    }

    @Bean
    public CreateProductUseCase createProductUseCase() {
        return new DefaulCreateProductUseCase(productGateway);
    }

    @Bean
    public UpdateProductUseCase updateProductUseCase() {
        return new DefaultUpdateProductUseCase(productGateway);
    }

    @Bean
    public GetProductUseCase getProductUseCase(){
        return new DefaultGetProductUseCase(productGateway);
    }

    @Bean
    public ListProductUseCase listProductUseCase() {
        return new DefaultListProductUseCase(productGateway);
    }

    @Bean
    public DeleteProductUseCase deleteProductUseCase(){ return new DefaultDeleteProductUseCase(productGateway, saleGateway);}

    @Bean
    public CreateSaleUseCase createSaleUseCase() {
        return new DefaultCreateSaleUseCase(productGateway,saleGateway);
    }

    @Bean
    public DefaultFilterSalesByDateUseCase filterSalesByDateUseCase() {
        return new DefaultFilterSalesByDateUseCase(saleGateway);
    }

    @Bean
    public DefaultListSaleUseCase listSaleUseCase() {
        return new DefaultListSaleUseCase(saleGateway, productGateway);
    }

    @Bean
    public DefaultGenerateSalesReportUseCase generateSalesReportUseCase() {
        return new DefaultGenerateSalesReportUseCase(saleGateway);
    }

    @Bean
    public DefaultDeleteSaleUseCase deleteSaleUseCase() {
        return new DefaultDeleteSaleUseCase(saleGateway);
    }

    @Bean
    public DefaultUpdateSaleUseCase updateSaleUseCase() {return new DefaultUpdateSaleUseCase(productGateway,saleGateway);}

    @Bean
    public DefaultCreateUserUseCase createUserUseCase() { return new DefaultCreateUserUseCase(userGateway, cryptoAdapter);}
}
