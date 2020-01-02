package br.com.company.analisedadosdevenda.batch.step.importdatasale.writers.client;

import br.com.company.analisedadosdevenda.model.Client;
import br.com.company.analisedadosdevenda.model.ClientRepository;
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
