package br.com.company.analisedadosdevenda.batch.step.importdatasale.writers.sales;

import br.com.company.analisedadosdevenda.model.Sale;
import br.com.company.analisedadosdevenda.model.SaleRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.SynchronizationType;
import java.util.List;

@Component
public class SaleRepositoryImpl implements SaleRepository {

    @PersistenceContext(synchronization = SynchronizationType.SYNCHRONIZED, type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @Override
    public void saveAll(List<Sale> sales) {

        sales.forEach(sale -> {

            if(!this.entityManager.contains(sale)){
                this.entityManager.merge(sale);
            }
        });

        this.entityManager.flush();
    }
}
