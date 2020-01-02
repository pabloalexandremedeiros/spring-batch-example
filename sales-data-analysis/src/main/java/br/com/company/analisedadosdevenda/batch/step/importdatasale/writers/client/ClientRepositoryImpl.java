package br.com.company.analisedadosdevenda.batch.step.importdatasale.writers.client;

import br.com.company.analisedadosdevenda.model.Client;
import br.com.company.analisedadosdevenda.model.ClientRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.SynchronizationType;
import java.util.List;

@Component
public class ClientRepositoryImpl implements ClientRepository {

    @PersistenceContext(synchronization = SynchronizationType.SYNCHRONIZED, type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @Override
    public void saveAll(List<Client> clients) {

        clients.forEach(client -> {
           if(!this.entityManager.contains(client)){
               this.entityManager.merge(client);
           }
        });

        this.entityManager.flush();
    }
}
