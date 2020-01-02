package br.com.company.analisedadosdevenda.batch.step.importdatasale;

import br.com.company.analisedadosdevenda.batch.step.importdatasale.readers.client.ClientFieldSetMapper;
import br.com.company.analisedadosdevenda.batch.step.importdatasale.readers.client.ClientTokenizer;
import br.com.company.analisedadosdevenda.batch.step.importdatasale.readers.sale.SaleFieldSetMapper;
import br.com.company.analisedadosdevenda.batch.step.importdatasale.readers.sale.SaleTokenizer;
import br.com.company.analisedadosdevenda.batch.step.importdatasale.readers.salesman.SalesmanFieldSetMapper;
import br.com.company.analisedadosdevenda.batch.step.importdatasale.readers.salesman.SalesmanTokenizer;
import br.com.company.analisedadosdevenda.model.Line;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ImportDataSaleFromFile implements Step {

    private TaskletStep taskletStep;
    private StepBuilderFactory stepBuilderFactory;
    private SalesReportGenerator salesReportGenerator;
    private ClassifierCompositeItemWriter<Line> classifierCompositeItemWriter;

    public ImportDataSaleFromFile(
            StepBuilderFactory stepBuilderFactory,
            SalesReportGenerator salesReportGenerator,
            ClassifierCompositeItemWriter<Line> classifierCompositeItemWriter) {

        this.classifierCompositeItemWriter = classifierCompositeItemWriter;
        this.salesReportGenerator = salesReportGenerator;
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

        if (this.taskletStep == null) {

            String fileName = ((String) stepExecution.getExecutionContext().get("fileName")).substring(6);
            Resource resource = new FileSystemResource("/" + fileName);

            // Alterar nome do arquivo a ser salvo

            FlatFileItemReader<Line> flatFileItemReaderLine = new FlatFileItemReaderBuilder<Line>()
                    .name("/" + fileName)
                    .resource(resource)
                    .lineMapper(fileLineMappers("/" + fileName))
                    .build();

            this.taskletStep = stepBuilderFactory
                    .get("importDataSale")
                    .<Line, Line>chunk(20)
                    .reader(flatFileItemReaderLine)
                    .writer(classifierCompositeItemWriter)
                    .listener(this.salesReportGenerator)
                    .build();
        }

        this.taskletStep.execute(stepExecution);
        this.removeFile(stepExecution);

        stepExecution.setExitStatus(ExitStatus.COMPLETED);
    }


    private void removeFile(StepExecution stepExecution) {

        try {

            String fileName = (String) stepExecution.getExecutionContext().get("fileName");
            Resource resource = new FileSystemResource("/" + fileName.substring(6));
            File file = resource.getFile();

            boolean deleted = file.delete();

            if (!deleted) {
                throw new UnexpectedJobExecutionException("Could not delete file " + file.getPath());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static PatternMatchingCompositeLineMapper<Line> fileLineMappers(String fileName) {

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
