package br.com.company.analisedadosdevenda.model;

import java.util.Objects;

public class SalesReport {

    private String fileName;
    private Long clientAmout;
    private Long salesmanAmout;
    private Long mostExpensiveSaleId;
    private String worstSalesman;

    public SalesReport(){}

    public String getFileName() { return fileName; }
    public Long getClientAmout() { return clientAmout; }
    public Long getSalesmanAmout() { return salesmanAmout; }
    public Long getMostExpensiveSaleId() { return mostExpensiveSaleId; }
    public String getWorstSalesman() { return worstSalesman; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesReport that = (SalesReport) o;
        return Objects.equals(fileName, that.fileName) &&
                Objects.equals(clientAmout, that.clientAmout) &&
                Objects.equals(salesmanAmout, that.salesmanAmout) &&
                Objects.equals(mostExpensiveSaleId, that.mostExpensiveSaleId) &&
                Objects.equals(worstSalesman, that.worstSalesman);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, clientAmout, salesmanAmout, mostExpensiveSaleId, worstSalesman);
    }

    @Override
    public String toString() {
        return "SalesReport{" +
                "fileName='" + fileName + '\'' +
                ", clientAmout=" + clientAmout +
                ", salesmanAmout=" + salesmanAmout +
                ", mostExpensiveSaleId=" + mostExpensiveSaleId +
                ", worstSalesman='" + worstSalesman + '\'' +
                '}';
    }
}
