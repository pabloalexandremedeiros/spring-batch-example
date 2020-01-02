package br.com.company.analisedadosdevenda.model;

import java.util.List;

public interface SaleRepository{

    void saveAll(List<Sale> sales);
}
