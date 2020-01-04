package com.company.salesanalysis.port.adapter.jpa;

import com.company.salesanalysis.domain.model.Salesman;
import com.company.salesanalysis.domain.model.SalesmanRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

@Component
public class SalesmanRepositoryImpl implements SalesmanRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public SalesmanRepositoryImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public void saveAll(Collection<Salesman> salesmen) {

        salesmen.forEach(salesman -> {

            if(!this.entityManager.contains(salesman)){
                this.entityManager.merge(salesman);
            }
        });

        this.entityManager.flush();
    }

    @Override
    public Long quantitySalesmaneForFile(String fileId) {

        Query query = this.entityManager.createQuery("SELECT COUNT(SM) FROM SALESMAN AS SM WHERE SM.fileId = :fileId");
        query.setParameter("fileId", fileId);

        return (Long) query.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Salesman findWorstSeller() {

        ArrayList<Object[]> quantitySalesPerSalesman = ((ArrayList<Object[]>) this
                .entityManager
                .createQuery("SELECT S.salesmanName, COUNT(S) FROM SALE AS S GROUP BY S.salesmanName")
                .getResultList());

        String worstSellerName = quantitySalesPerSalesman
                .parallelStream()
                .min(Comparator.comparingDouble(objects -> (Long) objects[1]))
                .map(objects -> (String) objects[0])
                .orElseThrow();

        Query salesmanQuery = this.entityManager.createQuery("SELECT SM FROM SALESMAN AS SM WHERE SM.name = :salesmanName");
        salesmanQuery.setParameter("salesmanName", worstSellerName);

        return (Salesman) salesmanQuery.getResultList().get(0);
    }
}
