package com.company.salesanalysis.batch.step.generatesalesreport.writer.salesreport;

import com.company.salesanalysis.domain.model.SalesReport;
import org.springframework.batch.item.file.transform.FieldExtractor;

public class SalesReportFieldExtractor implements FieldExtractor<SalesReport> {


    @Override
    public Object[] extract(SalesReport item) {

        Object[] objectReturn = new Object[5];

        objectReturn[0] = "fileName: " + item.getFileName();
        objectReturn[1] = "numberOfFileClients: " + item.getNumberOfFileClients();
        objectReturn[2] = "numberOfFileSalesman: " + item.getNumberOfFileSalesman();
        objectReturn[3] = "mostExpensiveSalesIds: " + item.getMostExpensiveSalesIds();
        objectReturn[4] = "worstSalesmanName: " + item.getWorstSalesman().getName();

        return objectReturn;
    }
}
