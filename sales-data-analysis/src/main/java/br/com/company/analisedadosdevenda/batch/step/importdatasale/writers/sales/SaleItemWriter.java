package br.com.company.analisedadosdevenda.batch.step.importdatasale.writers.sales;

import br.com.company.analisedadosdevenda.model.Sale;
import br.com.company.analisedadosdevenda.model.SaleRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SaleItemWriter implements ItemWriter<Sale> {

    private SaleRepository saleRepository;
    public SaleItemWriter(SaleRepository saleRepository){ this.saleRepository = saleRepository; }

    @Override
    @SuppressWarnings("unchecked")
    public void write(List<? extends Sale> items) {
        this.saleRepository.saveAll((List<Sale>) items);
    }
}
