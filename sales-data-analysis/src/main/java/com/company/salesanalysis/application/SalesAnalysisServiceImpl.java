package com.company.salesanalysis.application;

import com.company.salesanalysis.domain.model.*;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
class SalesAnalysisServiceImpl implements SalesAnalysisService {

    private SaleRepository saleRepository;
    private ClientRepository clientRepository;
    private SalesmanRepository salesmanRepository;

    SalesAnalysisServiceImpl(
            SaleRepository saleRepository,
            ClientRepository clientRepository,
            SalesmanRepository salesmanRepository ) {

        this.saleRepository = saleRepository;
        this.clientRepository = clientRepository;
        this.salesmanRepository = salesmanRepository;
    }

    @Override
    public void registerSale(Collection<Sale> sales) {
        this.saleRepository.saveAll(sales);
    }

    @Override
    public void registerClient(Collection<Client> clients) {
        this.clientRepository.saveAll(clients);
    }

    @Override
    public void registerSalesman(Collection<Salesman> salesmen) {
        this.salesmanRepository.saveAll(salesmen);
    }

    @Override
    public SalesReport generateReportForFile(String fileId) {

        Long quantityClientsFile = this
                .clientRepository
                .quantityClientsForFile(fileId);

        Long quantitySalesmanFile = this
                .salesmanRepository
                .quantitySalesmaneForFile(fileId);

        List<Long> mostExpensisalesIds = this
                .saleRepository
                .findMostExpensiveSalesIds();

        Salesman worstSeller = this
                .salesmanRepository
                .findWorstSeller();

        return new SalesReport(
                fileId,
                quantityClientsFile,
                quantitySalesmanFile,
                mostExpensisalesIds,
                worstSeller
        );
    }
}
