package com.company.salesanalysis.batch.step.importdatasale;

import com.company.salesanalysis.domain.model.Line;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static com.company.salesanalysis.port.adapter.filesystem.ResourceProvider.getFilesInPath;

@Component
public class ImportDataSalesStepFactory {


    private String filesPathIn;
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private StepBuilderFactory stepBuilderFactory;
    private ClassifierCompositeItemWriter<Line> classifierCompositeItemWriter;

    public ImportDataSalesStepFactory(
            @Value("${filesPathIn}") String filesPath,
            ThreadPoolTaskExecutor threadPoolTaskExecutor,
            StepBuilderFactory stepBuilderFactory,
            ClassifierCompositeItemWriter<Line> classifierCompositeItemWriter) {

        this.filesPathIn = filesPath;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.stepBuilderFactory = stepBuilderFactory;
        this.classifierCompositeItemWriter = classifierCompositeItemWriter;
    }

    public Partitioner partitioner(Resource[] resources) {

        MultiResourcePartitioner multiResourcePartitioner = new MultiResourcePartitioner();
        multiResourcePartitioner.partition(3);
        multiResourcePartitioner.setResources(resources);
        return multiResourcePartitioner;
    }

    public Step createImportDataSaleStep() {

        Collection<Resource> resources = getFilesInPath(this.filesPathIn);
        Partitioner partitioner = partitioner(resources.toArray(Resource[]::new));

        return this
                .stepBuilderFactory
                .get("importDataSalesMaster")
                .partitioner("importData", partitioner)
                .step(new ImportDataSalesFromFileStep(this.filesPathIn, this.stepBuilderFactory, this.classifierCompositeItemWriter))
                .aggregator(new ImportDataSalesStepAggregator())
                .taskExecutor(this.threadPoolTaskExecutor)
                .build();
    }


}
