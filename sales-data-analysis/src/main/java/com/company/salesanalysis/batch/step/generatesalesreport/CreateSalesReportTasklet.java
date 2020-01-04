package com.company.salesanalysis.batch.step.generatesalesreport;

import com.company.salesanalysis.application.SalesAnalysisService;
import com.company.salesanalysis.batch.ContextProvider;
import com.company.salesanalysis.batch.step.generatesalesreport.writer.salesreport.SalesReportFieldExtractor;
import com.company.salesanalysis.domain.model.SalesReport;
import com.company.salesanalysis.port.adapter.filesystem.ResourceProvider;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CreateSalesReportTasklet implements Tasklet {

    private String basePathOutFiles;
    private SalesAnalysisService salesAnalysisService;

    private static final String MSG_ERROR_WRITE_IN_FILE = "error writing to sales report in file ";

    CreateSalesReportTasklet(
            @Value("${filesPathOut}") String basePathOutFiles,
            SalesAnalysisService salesAnalysisService) {

        this.basePathOutFiles = basePathOutFiles;
        this.salesAnalysisService = salesAnalysisService;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {

        ExecutionContext stepExecutionContext = contribution
                .getStepExecution()
                .getExecutionContext();

        ExecutionContext jobContext = contribution
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext();

        ContextProvider
                .getFileNamesInJobContext(jobContext)
                .parallelStream()
                .map(this.salesAnalysisService::generateReportForFile)
                .forEach(salesReport -> writeInFile(this.basePathOutFiles, salesReport, stepExecutionContext));

        return RepeatStatus.FINISHED;
    }


    private static void writeInFile(String filePathOut,
                                    SalesReport salesReport,
                                    ExecutionContext stepExecution) {

        Resource fileForWrite = ResourceProvider
                .createFileInPath(filePathOut, salesReport.getFileName());

        FlatFileItemWriter<SalesReport> salesReportFlatFileItemWriter = createFlatFileItemWriter(fileForWrite);
        salesReportFlatFileItemWriter.open(stepExecution);

        try {

            salesReportFlatFileItemWriter
                    .write(Collections.singletonList(salesReport));

        } catch (Exception e) {
            throw new RuntimeException(
                    MSG_ERROR_WRITE_IN_FILE
                            + salesReport.getFileName()
            );
        }
    }

    private static FlatFileItemWriter<SalesReport> createFlatFileItemWriter(Resource resource) {

        return new FlatFileItemWriterBuilder<SalesReport>()
                .name("lineWriterOutFile")
                .resource(resource)
                .delimited()
                .delimiter(";")
                .fieldExtractor(new SalesReportFieldExtractor())
                .build();
    }
}
