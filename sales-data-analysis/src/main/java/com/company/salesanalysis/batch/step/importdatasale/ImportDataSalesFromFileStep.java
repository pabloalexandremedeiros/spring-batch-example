package com.company.salesanalysis.batch.step.importdatasale;

import com.company.salesanalysis.batch.ContextProvider;
import com.company.salesanalysis.batch.step.importdatasale.reader.client.ClientFieldSetMapper;
import com.company.salesanalysis.batch.step.importdatasale.reader.client.ClientTokenizer;
import com.company.salesanalysis.batch.step.importdatasale.reader.sale.SaleFieldSetMapper;
import com.company.salesanalysis.batch.step.importdatasale.reader.sale.SaleTokenizer;
import com.company.salesanalysis.batch.step.importdatasale.reader.salesman.SalesmanFieldSetMapper;
import com.company.salesanalysis.batch.step.importdatasale.reader.salesman.SalesmanTokenizer;
import com.company.salesanalysis.domain.model.Line;
import com.company.salesanalysis.port.adapter.filesystem.ResourceProvider;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ImportDataSalesFromFileStep implements Step {

    private String folderInPath;
    private TaskletStep taskletStep;
    private StepBuilderFactory stepBuilderFactory;
    private ClassifierCompositeItemWriter<Line> classifierCompositeItemWriter;

    public ImportDataSalesFromFileStep(
            @Value("${filesPathIn}") String folderInPath,
            StepBuilderFactory stepBuilderFactory,
            ClassifierCompositeItemWriter<Line> classifierCompositeItemWriter) {

        this.folderInPath = folderInPath;
        this.classifierCompositeItemWriter = classifierCompositeItemWriter;
        this.stepBuilderFactory = stepBuilderFactory;

    }

    @Override
    public String getName() {
        return "flatItemReaderLine";
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
    public void execute(StepExecution stepExecution) throws JobInterruptedException {

        initializeTaskletStep(stepExecution);

        this.taskletStep.execute(stepExecution);
        this.removeFile(stepExecution);

        stepExecution.setExitStatus(ExitStatus.COMPLETED);
    }

    private void initializeTaskletStep(StepExecution stepExecution) {

        if (this.taskletStep == null) {

            String fileName = ContextProvider
                    .getFileNameInStepContext(stepExecution.getExecutionContext());

            Resource resource = ResourceProvider
                    .getFileInPath(this.folderInPath, fileName);

            this.taskletStep = this
                    .stepBuilderFactory
                    .get("importDataSale")
                    .<Line, Line>chunk(20)
                    .reader(createFlatFileItemReader(fileName, resource))
                    .writer(this.classifierCompositeItemWriter)
                    .exceptionHandler((context, throwable) -> {
                        context.close();
                        throw new RuntimeException(throwable);
                    })
                    .build();
        }
    }

    private static FlatFileItemReader<Line> createFlatFileItemReader(String fileName, Resource resource) {

        return new FlatFileItemReaderBuilder<Line>()
                .name(fileName)
                .resource(resource)
                .lineMapper(createFileLineMappers(fileName))
                .build();
    }

    private void removeFile(StepExecution stepExecution) {

        try {

            Resource resource = ResourceProvider.getFileInPath(
                    this.folderInPath,
                    ContextProvider.getFileNameInStepContext(stepExecution.getExecutionContext())
            );

            File file = resource.getFile();
            boolean deleted = file.delete();

            if (!deleted) {
                throw new UnexpectedJobExecutionException("Could not delete file " + file.getPath());
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static PatternMatchingCompositeLineMapper<Line> createFileLineMappers(String fileName) {

        PatternMatchingCompositeLineMapper<Line> lineMapper = new PatternMatchingCompositeLineMapper<>();

        Map<String, LineTokenizer> tokenizers = new HashMap<>(3);
        tokenizers.put("001*", new SalesmanTokenizer());
        tokenizers.put("002*", new ClientTokenizer());
        tokenizers.put("003*", new SaleTokenizer());

        lineMapper.setTokenizers(tokenizers);

        Map<String, FieldSetMapper<Line>> mappers = new HashMap<>(3);
        mappers.put("001*", new SalesmanFieldSetMapper(fileName));
        mappers.put("002*", new ClientFieldSetMapper(fileName));
        mappers.put("003*", new SaleFieldSetMapper(fileName));

        lineMapper.setFieldSetMappers(mappers);

        return lineMapper;
    }
}
