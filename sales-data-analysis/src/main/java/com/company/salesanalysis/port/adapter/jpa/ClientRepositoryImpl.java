package com.company.salesanalysis.port.adapter.jpa;

import com.company.salesanalysis.domain.model.Client;
import com.company.salesanalysis.domain.model.ClientRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

@Component
public class ClientRepositoryImpl implements ClientRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public ClientRepositoryImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public void saveAll(Collection<Client> clients) {

        clients.forEach(client -> {
            if(!this.entityManager.contains(client)){
                this.entityManager.merge(client);
            }
        });

        this.entityManager.flush();
    }

    @Override
    public Long quantityClientsForFile(String fileId) {

        Query query = this.entityManager.createQuery("SELECT COUNT(C) FROM CLIENT AS C WHERE fileId= :fileId");
        query.setParameter("fileId", fileId);

        return (Long) query.getSingleResult();
    }
}
