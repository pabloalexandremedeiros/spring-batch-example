package br.com.company.analisedadosdevenda.model;

import java.util.List;

public interface ClientRepository {

    void saveAll(List<Client> clients);

}
