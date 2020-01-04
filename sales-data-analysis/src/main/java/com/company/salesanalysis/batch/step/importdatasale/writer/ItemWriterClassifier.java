package com.company.salesanalysis.batch.step.importdatasale.writer;

import com.company.salesanalysis.batch.step.importdatasale.writer.client.ClientItemWriter;
import com.company.salesanalysis.batch.step.importdatasale.writer.sale.SaleItemWriter;
import com.company.salesanalysis.batch.step.importdatasale.writer.salesman.SalesmanItemWriter;
import com.company.salesanalysis.domain.model.Client;
import com.company.salesanalysis.domain.model.Line;
import com.company.salesanalysis.domain.model.Sale;
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
