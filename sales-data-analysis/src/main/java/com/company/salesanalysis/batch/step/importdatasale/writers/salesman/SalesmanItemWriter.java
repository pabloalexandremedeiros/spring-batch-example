package com.company.salesanalysis.batch.step.importdatasale.writers.salesman;

import com.company.salesanalysis.domain.model.Salesman;
import com.company.salesanalysis.domain.model.SalesmanRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SalesmanItemWriter implements ItemWriter<Salesman> {

    private SalesmanRepository salesmanRepository;
    public SalesmanItemWriter(SalesmanRepository salesmanRepository){ this.salesmanRepository = salesmanRepository; }

    @Override
    @SuppressWarnings("unchecked")
    public void write(List<? extends Salesman> items) {
        this.salesmanRepository.saveAll((List<Salesman>) items);
    }
}
