package br.com.company.analisedadosdevenda.batch.step.importdatasale;

import br.com.company.analisedadosdevenda.batch.step.importdatasale.writers.salesreport.SalesReportFieldExtractor;
import br.com.company.analisedadosdevenda.model.SalesReport;
import br.com.company.analisedadosdevenda.model.SalesReportRepository;
import br.com.company.analisedadosdevenda.util.ResourceProvider;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class SalesReportGenerator implements org.springframework.batch.core.StepExecutionListener {

    private String basePathOutFiles;
    private SalesReportRepository salesReportRepository;
    public SalesReportGenerator(
            @Value("${filesPathOut}") String basePathOutFiles,
            SalesReportRepository salesReportRepository){

        this.basePathOutFiles = basePathOutFiles;
        this.salesReportRepository = salesReportRepository;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {}

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        String fileName = "/" + ((String) stepExecution.getExecutionContext().get("fileName")).substring(6);
        SalesReport salesReport = salesReportRepository.getReportFromFileId(fileName);

        Resource resource = ResourceProvider.createFileOut(this.basePathOutFiles, fileName);
        writeSalesReportInFile(resource, salesReport, stepExecution.getExecutionContext());

        return ExitStatus.COMPLETED;
    }


    private void writeSalesReportInFile(Resource resource, SalesReport salesReport, ExecutionContext executionContext)  {

        FlatFileItemWriter<SalesReport> flatFileItemWriter = new FlatFileItemWriterBuilder<SalesReport>()
                .name("lineWriterOutFile")
                .resource(resource)
                .delimited()
                .delimiter(" ")
                .fieldExtractor(new SalesReportFieldExtractor())
                .build();

        try {

            flatFileItemWriter.open(executionContext);
            flatFileItemWriter.write(Collections.singletonList(salesReport));

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
