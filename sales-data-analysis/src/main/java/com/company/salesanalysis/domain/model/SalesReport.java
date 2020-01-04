package com.company.salesanalysis.domain.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.Collection;
import java.util.Objects;

public class SalesReport {

    private String fileName;
    private Long numberOfFileClients;
    private Long numberOfFileSalesman;
    private Collection<Long> mostExpensiveSalesIds;
    private Salesman worstSalesman;

    public SalesReport(
            String fileName,
            Long numberOfFileClients,
            Long numberOfFileSalesman,
            Collection<Long> mostExpensiveSalesIds,
            Salesman worstSalesman ){

        Validate.isTrue(StringUtils.isNotBlank(fileName));
        Validate.notEmpty(mostExpensiveSalesIds);
        Validate.notNull(worstSalesman);

        this.fileName = fileName;
        this.numberOfFileClients = numberOfFileClients;
        this.numberOfFileSalesman = numberOfFileSalesman;
        this.mostExpensiveSalesIds = mostExpensiveSalesIds;
        this.worstSalesman = worstSalesman;
    }


    public String getFileName() { return fileName; }
    public Long getNumberOfFileClients() { return numberOfFileClients; }
    public Long getNumberOfFileSalesman() { return numberOfFileSalesman; }
    public Collection<Long> getMostExpensiveSalesIds() { return mostExpensiveSalesIds; }
    public Salesman getWorstSalesman() { return worstSalesman; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesReport that = (SalesReport) o;
        return Objects.equals(fileName, that.fileName) &&
                Objects.equals(numberOfFileClients, that.numberOfFileClients) &&
                Objects.equals(numberOfFileSalesman, that.numberOfFileSalesman) &&
                Objects.equals(mostExpensiveSalesIds, that.mostExpensiveSalesIds) &&
                Objects.equals(worstSalesman, that.worstSalesman);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, numberOfFileClients, numberOfFileSalesman, mostExpensiveSalesIds, worstSalesman);
    }

    @Override
    public String toString() {
        return "SalesReport{" +
                "fileName='" + fileName + '\'' +
                ", clientAmout=" + numberOfFileClients +
                ", salesmanAmout=" + numberOfFileSalesman +
                ", mostExpensiveSaleId=" + mostExpensiveSalesIds +
                ", worstSalesman='" + worstSalesman + '\'' +
                '}';
    }
}
