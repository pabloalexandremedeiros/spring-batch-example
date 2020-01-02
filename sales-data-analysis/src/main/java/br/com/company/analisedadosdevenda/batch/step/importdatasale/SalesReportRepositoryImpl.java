package br.com.company.analisedadosdevenda.batch.step.importdatasale;

import br.com.company.analisedadosdevenda.model.Sale;
import br.com.company.analisedadosdevenda.model.SalesReport;
import br.com.company.analisedadosdevenda.model.SalesReportRepository;
import br.com.company.analisedadosdevenda.model.Salesman;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SalesReportRepositoryImpl implements SalesReportRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public SalesReport getReportFromFileId(String fileId) {

        Collection<Long> mostExpensiveSalesIds = findMostExpensiveSalesIds();
        Salesman worstSeller = findWorstSeller();
        Long numberOfFileClients = getClientsFromFile(fileId);
        Long numberOfFileSalesman = getSalesmanFromFile(fileId);

        return new SalesReport(
                fileId,
                numberOfFileClients,
                numberOfFileSalesman,
                mostExpensiveSalesIds,
                worstSeller
        );
    }

    private Long getClientsFromFile(String fileId) {

        Query query = this.entityManager.createQuery("SELECT COUNT(C) FROM CLIENT AS C WHERE fileId= :fileId");
        query.setParameter("fileId", fileId);

            return (Long) query.getSingleResult();
    }

    private Long getSalesmanFromFile(String fileId) {

        Query query = this.entityManager.createQuery("SELECT COUNT(SM) FROM SALESMAN AS SM WHERE fileId = :fileId");
        query.setParameter("fileId", fileId);

        return (Long) query.getSingleResult();
    }

    private List<Long> findMostExpensiveSalesIds() {

        return this
                .entityManager
                .createQuery("SELECT S FROM SALE S WHERE S.totalValue = (SELECT MAX(SS.totalValue) FROM SALE AS SS)", Sale.class)
                .getResultList()
                .parallelStream()
                .distinct()
                .map(Sale::getId)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private Salesman findWorstSeller() {

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
