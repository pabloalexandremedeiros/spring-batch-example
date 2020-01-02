package com.company.salesanalysis.batch.step.importdatasale.writers.client;

import com.company.salesanalysis.domain.model.Client;
import com.company.salesanalysis.domain.model.ClientRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientItemWriter implements ItemWriter<Client> {

    private ClientRepository clientRepository;
    public ClientItemWriter(ClientRepository clientRepository){ this.clientRepository = clientRepository; }

    @Override
    @SuppressWarnings("unchecked")
    public void write(List<? extends Client> items) {
        this.clientRepository.saveAll((List<Client>) items);
    }
}
