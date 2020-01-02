package br.com.company.analisedadosdevenda.batch.step.importdatasale.writers.salesman;

import br.com.company.analisedadosdevenda.model.Salesman;
import br.com.company.analisedadosdevenda.model.SalesmanRepository;
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
