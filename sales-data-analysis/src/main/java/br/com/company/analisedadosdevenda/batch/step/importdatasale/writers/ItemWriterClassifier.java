package br.com.company.analisedadosdevenda.batch.step.importdatasale.writers;

import br.com.company.analisedadosdevenda.batch.step.importdatasale.writers.client.ClientItemWriter;
import br.com.company.analisedadosdevenda.batch.step.importdatasale.writers.sales.SaleItemWriter;
import br.com.company.analisedadosdevenda.batch.step.importdatasale.writers.salesman.SalesmanItemWriter;
import br.com.company.analisedadosdevenda.model.Client;
import br.com.company.analisedadosdevenda.model.Line;
import br.com.company.analisedadosdevenda.model.Sale;
import org.springframework.batch.item.ItemWriter;
import org.springframework.classify.Classifier;
import org.springframework.stereotype.Component;

@Component
public class ItemWriterClassifier implements Classifier<Line, ItemWriter<? super Line>> {

    private SaleItemWriter saleItemWriter;
    private ClientItemWriter clientItemWriter;
    private SalesmanItemWriter salesmanItemWriter;

    public ItemWriterClassifier(
            ClientItemWriter clientItemWriter,
            SaleItemWriter saleItemWriter,
            SalesmanItemWriter salesmanItemWriter) {

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
