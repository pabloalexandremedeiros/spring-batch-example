package br.com.company.analisedadosdevenda.batch.step.importdatasale;

import br.com.company.analisedadosdevenda.batch.step.importdatasale.readers.client.ClientFieldSetMapper;
import br.com.company.analisedadosdevenda.batch.step.importdatasale.readers.client.ClientTokenizer;
import br.com.company.analisedadosdevenda.batch.step.importdatasale.readers.sale.SaleFieldSetMapper;
import br.com.company.analisedadosdevenda.batch.step.importdatasale.readers.sale.SaleTokenizer;
import br.com.company.analisedadosdevenda.batch.step.importdatasale.readers.salesman.SalesmanFieldSetMapper;
import br.com.company.analisedadosdevenda.batch.step.importdatasale.readers.salesman.SalesmanTokenizer;
import br.com.company.analisedadosdevenda.model.Line;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ImportDataSaleStepFactory {

    @Autowired
    private FlatFileItemReader<Line> flatFileItemReaderLine;

    private String filesPath;
    private StepBuilderFactory stepBuilderFactory;
    private ClassifierCompositeItemWriter<Line> classifierCompositeItemWriter;

    public ImportDataSaleStepFactory(
            @Value("${filesPath}") String filesPath,
            StepBuilderFactory stepBuilderFactory,
            ClassifierCompositeItemWriter<Line> classifierCompositeItemWriter) {

        this.filesPath = filesPath;
        this.stepBuilderFactory = stepBuilderFactory;
        this.classifierCompositeItemWriter = classifierCompositeItemWriter;
    }


    public Collection<Resource> getResourcesInPath() {

        return Optional
                .ofNullable(new File(this.filesPath).list())
                .map(files ->

                        Arrays
                                .stream(files)
                                .map(filePath -> new FileSystemResource(this.filesPath + filePath))
                                .map(fileSystemResource -> (Resource) fileSystemResource)
                                .collect(Collectors.toList())

                ).orElse(new ArrayList<>());
    }


    public Partitioner partitioner(Resource[] resources) {

        MultiResourcePartitioner multiResourcePartitioner = new MultiResourcePartitioner();
        multiResourcePartitioner.partition(5);
        multiResourcePartitioner.setResources(resources);
        return multiResourcePartitioner;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Line> flatFileItemReader(@Value("#{stepExecutionContext['fileName']}") String filename) {

        return new FlatFileItemReaderBuilder<Line>()
                .name(filename)
                .lineMapper(fileLineMappers(filename))
                .build()
                ;
    }

    @Bean
    @StepScope
    @Qualifier("stepImport")
    public Step step() {

        return this
                .stepBuilderFactory
                .get("importDataSale")
                .<Line, Line>chunk(20)
                .reader(this.flatFileItemReaderLine)
                .writer(this.classifierCompositeItemWriter)
                .build()
                ;
    }

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setQueueCapacity(10);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }

    public Step importDataSale() {

        Collection<Resource> resources = getResourcesInPath();
        Partitioner partitioner = partitioner(resources.toArray(Resource[]::new));

        return this
                .stepBuilderFactory
                .get("multiImportDataSale")
                .partitioner("importDataSale", partitioner)
                .step(step())
                .taskExecutor(taskExecutor())
                .build()
                ;
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
