package br.com.company.analisedadosdevenda.batch.step.importdatasale.writers.salesman;

import br.com.company.analisedadosdevenda.model.Salesman;
import br.com.company.analisedadosdevenda.model.SalesmanRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.SynchronizationType;
import java.util.List;

@Component
public class SalesmanRepositoryImpl implements SalesmanRepository {

    @PersistenceContext(synchronization = SynchronizationType.SYNCHRONIZED, type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @Override
    public void saveAll(List<Salesman> salesmen) {

        salesmen.forEach(salesman -> {

            if(!this.entityManager.contains(salesman)){
                this.entityManager.merge(salesman);
            }
        });

        this.entityManager.flush();
    }
}
