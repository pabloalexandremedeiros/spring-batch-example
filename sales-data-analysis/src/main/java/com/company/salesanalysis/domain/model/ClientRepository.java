package com.company.salesanalysis.domain.model;

import java.util.Collection;

public interface ClientRepository {

    void saveAll(Collection<Client> clients);
    Long quantityClientsForFile(String fileId);
}
