package com.company.salesanalysis.application;

import com.company.salesanalysis.domain.model.Client;
import com.company.salesanalysis.domain.model.Sale;
import com.company.salesanalysis.domain.model.SalesReport;
import com.company.salesanalysis.domain.model.Salesman;

import java.util.Collection;

public interface SalesAnalysisService {

    void registerSale(Collection<Sale> sales);
    void registerClient(Collection<Client> clients);
    void registerSalesman(Collection<Salesman> salesmen);
    SalesReport generateReportForFile(String fileId);
}
