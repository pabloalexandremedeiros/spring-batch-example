package br.com.company.analisedadosdevenda.batch.step.importdatasale.writers;

import br.com.company.analisedadosdevenda.model.Client;
import br.com.company.analisedadosdevenda.model.Line;
import br.com.company.analisedadosdevenda.model.Sale;
import br.com.company.analisedadosdevenda.model.Salesman;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
public class ItemWriterFactory {

    private EntityManagerFactory entityManagerFactory;

    public ItemWriterFactory(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;
    }

    @Bean
    public JpaItemWriter<Salesman> createSalesmanJpaItemWriter() {
        return new JpaItemWriterBuilder<Salesman>()
                .entityManagerFactory(this.entityManagerFactory)
                .build()
                ;
    }

    @Bean
    public JpaItemWriter<Sale> createSaleJpaItemWriter() {
        return new JpaItemWriterBuilder<Sale>()
                .entityManagerFactory(this.entityManagerFactory)
                .build()
                ;
    }

    @Bean
    public JpaItemWriter<Client> createClientJpaItemWriter() {
        return new JpaItemWriterBuilder<Client>()
                .entityManagerFactory(this.entityManagerFactory)
                .build()
                ;
    }

    @Bean
    public ClassifierCompositeItemWriter<Line> classifierCompositeItemWriter(ItemWriterClassifier itemClassifier) {

        ClassifierCompositeItemWriter<Line> classifierCompositeItemWriter = new ClassifierCompositeItemWriter<>();
        classifierCompositeItemWriter.setClassifier(itemClassifier);

        return classifierCompositeItemWriter;
    }
}
