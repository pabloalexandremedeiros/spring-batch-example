package br.com.company.analisedadosdevenda.batch.step.importdatasale.writers.salesreport;

import br.com.company.analisedadosdevenda.model.SalesReport;
import org.springframework.batch.item.file.transform.FieldExtractor;

public class SalesReportFieldExtractor implements FieldExtractor<SalesReport> {


    @Override
    public Object[] extract(SalesReport item) {

        Object[] objectReturn = new Object[5];

        objectReturn[0] = item.getFileName();
        objectReturn[1] = item.getNumberOfFileClients();
        objectReturn[2] = item.getNumberOfFileSalesman();
        objectReturn[3] = item.getMostExpensiveSalesIds();
        objectReturn[4] = item.getWorstSalesman().getName();

        return objectReturn;
    }
}
