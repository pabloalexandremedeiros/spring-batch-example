package com.company.salesanalysis.batch.step.generatesalesreport.writer.salesreport;

import com.company.salesanalysis.application.SalesAnalysisService;
import com.company.salesanalysis.batch.ContextProvider;
import com.company.salesanalysis.domain.model.SalesReport;
import com.company.salesanalysis.port.adapter.systemfile.ResourceProvider;
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
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreateSalesReportTasklet implements Tasklet {

    private String basePathOutFiles;
    private SalesAnalysisService salesAnalysisService;

    CreateSalesReportTasklet(
            @Value("${filesPathOut}") String basePathOutFiles,
            SalesAnalysisService salesAnalysisService ){

        this.basePathOutFiles = basePathOutFiles;
        this.salesAnalysisService = salesAnalysisService;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        ExecutionContext stepExecutionContext = contribution
                .getStepExecution()
                .getExecutionContext();

        ExecutionContext jobContext = contribution
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext();

        List<SalesReport> salesReportList = ContextProvider
                .getFileNamesInJobContext(jobContext)
                .parallelStream()
                .map(this.salesAnalysisService::generateReportForFile)
                .collect(Collectors.toList());

        salesReportList.forEach(salesReport -> {

            Resource fileForWrite = ResourceProvider.createFileInPath(this.basePathOutFiles, salesReport.getFileName());
            FlatFileItemWriter<SalesReport> salesReportFlatFileItemWriter = createFlatFileItemWriter(fileForWrite);

            salesReportFlatFileItemWriter.open(stepExecutionContext);

            try {
                salesReportFlatFileItemWriter.write(Collections.singletonList(salesReport));

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return RepeatStatus.FINISHED;
    }

    private static FlatFileItemWriter<SalesReport> createFlatFileItemWriter(Resource resource){

        return new FlatFileItemWriterBuilder<SalesReport>()
                .name("lineWriterOutFile")
                .resource(resource)
                .delimited()
                .delimiter(";")
                .fieldExtractor(new SalesReportFieldExtractor())
                .build();
    }
}
