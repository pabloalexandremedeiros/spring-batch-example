package br.com.company.analisedadosdevenda.batch.step.importdatasale.writers;

import br.com.company.analisedadosdevenda.model.Client;
import br.com.company.analisedadosdevenda.model.Line;
import br.com.company.analisedadosdevenda.model.Sale;
import br.com.company.analisedadosdevenda.model.Salesman;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.classify.Classifier;
import org.springframework.stereotype.Component;

@Component
public class ItemWriterClassifier implements Classifier<Line, ItemWriter<? super Line>> {

    private JpaItemWriter<Sale> saleItemWriter;
    private JpaItemWriter<Client> clientItemWriter;
    private JpaItemWriter<Salesman> salesmanItemWriter;

    public ItemWriterClassifier(
            JpaItemWriter<Sale> saleItemWriter,
            JpaItemWriter<Client> clientItemWriter,
            JpaItemWriter<Salesman> salesmanItemWriter) {

        this.saleItemWriter = saleItemWriter;
        this.clientItemWriter = clientItemWriter;
        this.salesmanItemWriter = salesmanItemWriter;

    }

    @Override
    @SuppressWarnings("unchecked")
    public ItemWriter<? super Line> classify(Line line) {

        if(line.getType() ==  Client.class){
            return (ItemWriter) clientItemWriter;

        } else if (line.getType() == Sale.class){
            return (ItemWriter) saleItemWriter;

        } else {
            return (ItemWriter) salesmanItemWriter;

        }
    }
}
