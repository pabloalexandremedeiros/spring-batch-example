package br.com.company.analisedadosdevenda.model;

public interface SalesReportRepository {

    SalesReport getReportFromFileId(String fileId);

}
