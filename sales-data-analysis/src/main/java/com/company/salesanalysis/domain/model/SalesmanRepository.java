package com.company.salesanalysis.domain.model;

import java.util.Collection;

public interface SalesmanRepository {

    void saveAll(Collection<Salesman> salesmen);
    Long quantitySalesmaneForFile(String fileId);
    Salesman findWorstSeller();
}
