package com.company.salesanalysis.batch.step.importdatasale;

import com.company.salesanalysis.application.SalesAnalysisService;
import com.company.salesanalysis.batch.step.importdatasale.writers.salesreport.SalesReportFieldExtractor;
import com.company.salesanalysis.domain.model.SalesReport;
import com.company.salesanalysis.domain.model.GenerateSalesReportService;
import com.company.salesanalysis.port.adapter.systemfile.ResourceProvider;
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
    private SalesAnalysisService salesAnalysisService;

    public SalesReportGenerator(
            @Value("${filesPathOut}") String basePathOutFiles,
            SalesAnalysisService salesAnalysisService){

        this.basePathOutFiles = basePathOutFiles;
        this.salesAnalysisService = salesAnalysisService;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {}

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        String fileName = "/" + ((String) stepExecution.getExecutionContext().get("fileName")).substring(6);
        SalesReport salesReport = salesAnalysisService.generateReportForFile(fileName);

        Resource resource = ResourceProvider.createFileInPath(this.basePathOutFiles, fileName);
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
