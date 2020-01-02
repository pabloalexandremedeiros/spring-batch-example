package com.company.salesanalysis.port.adapter.jpa;

import com.company.salesanalysis.domain.model.Sale;
import com.company.salesanalysis.domain.model.SaleRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SaleRepositoryImpl implements SaleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public SaleRepositoryImpl(EntityManager entityManager) { this.entityManager = entityManager; }

    @Override
    public void saveAll(Collection<Sale> sales) {

        sales.forEach(sale -> {
            if(!this.entityManager.contains(sale)){
                this.entityManager.merge(sale);
            }
        });

        this.entityManager.flush();
    }

    @Override
    public List<Long> findMostExpensiveSalesIds() {

        return this
                .entityManager
                .createQuery("SELECT S FROM SALE S WHERE S.totalValue = (SELECT MAX(SS.totalValue) FROM SALE AS SS)", Sale.class)
                .getResultList()
                .parallelStream()
                .distinct()
                .map(Sale::getId)
                .collect(Collectors.toList());
    }
}
