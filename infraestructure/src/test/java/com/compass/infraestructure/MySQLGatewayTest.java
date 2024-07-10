package com.compass.infraestructure;


import com.compass.infraestructure.product.persistence.ProductRepository;
import com.compass.infraestructure.sale.persistence.SaleRepository;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.*;
import java.util.Collection;
import java.util.List;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("test")
@DataJpaTest
@ComponentScan(
        basePackages = "com.compass.infraestructure",
        includeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX,pattern = "com\\.compass\\.infraestructure\\..*MySQLGateway")
})
@EnableJpaRepositories("com.compass.infraestructure")
@ExtendWith(MySQLGatewayTest.CleanUp.class)
public @interface MySQLGatewayTest {
     class CleanUp implements BeforeEachCallback {

        @Override
        public void beforeEach(final ExtensionContext extensionContext) throws Exception {
            final var context =  SpringExtension.getApplicationContext(extensionContext);

            cleanUp(List.of(
                    context.getBean(SaleRepository.class),
                    context.getBean(ProductRepository.class)

            ));


            final var em = context.getBean(TestEntityManager.class);
            em.flush();
            em.clear();
        }

        private void cleanUp(final Collection<CrudRepository> repositories) {
            repositories.forEach(CrudRepository::deleteAll);
        }
    }
}
