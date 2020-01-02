package com.company.salesanalysis.domain.model;

import java.util.Collection;
import java.util.List;

public interface SaleRepository {

    void saveAll(Collection<Sale> sales);
    List<Long> findMostExpensiveSalesIds();
}
