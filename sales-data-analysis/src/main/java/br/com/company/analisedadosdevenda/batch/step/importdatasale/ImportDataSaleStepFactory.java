package br.com.company.analisedadosdevenda.batch.step.importdatasale;

import br.com.company.analisedadosdevenda.model.Line;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.*;
import static br.com.company.analisedadosdevenda.util.ResourceProvider.*;

@Component
@EnableBatchProcessing
public class ImportDataSaleStepFactory {


    private String filesPath;
    private StepBuilderFactory stepBuilderFactory;
    private SalesReportGenerator salesReportGenerator;
    private ClassifierCompositeItemWriter<Line> classifierCompositeItemWriter;

    public ImportDataSaleStepFactory(
            @Value("${filesPath}") String filesPath,
            StepBuilderFactory stepBuilderFactory,
            SalesReportGenerator salesReportGenerator,
            ClassifierCompositeItemWriter<Line> classifierCompositeItemWriter) {

        this.filesPath = filesPath;
        this.stepBuilderFactory = stepBuilderFactory;
        this.salesReportGenerator = salesReportGenerator;
        this.classifierCompositeItemWriter = classifierCompositeItemWriter;
    }

    public Partitioner partitioner(Resource[] resources) {

        MultiResourcePartitioner multiResourcePartitioner = new MultiResourcePartitioner();
        multiResourcePartitioner.partition(3);
        multiResourcePartitioner.setResources(resources);
        return multiResourcePartitioner;
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

        Collection<Resource> resources = getResourcesInPath(this.filesPath);
        Partitioner partitioner = partitioner(resources.toArray(Resource[]::new));

        return this
                .stepBuilderFactory
                .get("multiImportDataSale")
                .partitioner("im", partitioner)
                .step(new ImportDataSaleFromFile(stepBuilderFactory, salesReportGenerator, classifierCompositeItemWriter))
                .taskExecutor(taskExecutor())
                .build()
                ;
    }


}
