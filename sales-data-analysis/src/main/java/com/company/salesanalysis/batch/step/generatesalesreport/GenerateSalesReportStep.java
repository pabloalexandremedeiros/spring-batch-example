package com.company.salesanalysis.batch.step.generatesalesreport;

import com.company.salesanalysis.application.SalesAnalysisService;
import com.company.salesanalysis.batch.ContextProvider;
import com.company.salesanalysis.batch.step.generatesalesreport.writer.salesreport.SalesReportFieldExtractor;
import com.company.salesanalysis.domain.model.SalesReport;
import com.company.salesanalysis.port.adapter.systemfile.ResourceProvider;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class GenerateSalesReportStep implements Step {

    private String basePathOutFiles;
    private SalesAnalysisService salesAnalysisService;

    public GenerateSalesReportStep(
            @Value("${filesPathOut}") String basePathOutFiles,
            SalesAnalysisService salesAnalysisService) {

        this.basePathOutFiles = basePathOutFiles;
        this.salesAnalysisService = salesAnalysisService;
    }

    @Override
    public String getName() {
        return "salesReportStep";
    }

    @Override
    public boolean isAllowStartIfComplete() {
        return false;
    }

    @Override
    public int getStartLimit() {
        return 0;
    }

    @Override
    public void execute(StepExecution stepExecution) {

        ContextProvider
                .getFileNamesInJobContext(stepExecution.getJobExecution().getExecutionContext())
                .parallelStream()
                .map(this.salesAnalysisService::generateReportForFile)
                .forEach(salesReport -> {

                    Resource fileForWrite = ResourceProvider.createFileInPath(this.basePathOutFiles, salesReport.getFileName());
                    writeSalesReportInFile(fileForWrite, salesReport, stepExecution.getExecutionContext());

                });

        stepExecution.setExitStatus(ExitStatus.COMPLETED);
    }


    private static void writeSalesReportInFile(Resource resource, SalesReport salesReport, ExecutionContext executionContext) {

        FlatFileItemWriter<SalesReport> flatFileItemWriter = new FlatFileItemWriterBuilder<SalesReport>()
                .name("lineWriterOutFile")
                .resource(resource)
                .delimited()
                .delimiter(";")
                .fieldExtractor(new SalesReportFieldExtractor())
                .build();

        try {

            flatFileItemWriter.open(executionContext);
            flatFileItemWriter.write(Collections.singletonList(salesReport));

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
