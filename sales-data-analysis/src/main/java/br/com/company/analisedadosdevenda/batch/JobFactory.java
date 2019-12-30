package br.com.company.analisedadosdevenda.batch;

import br.com.company.analisedadosdevenda.batch.step.clearfileimported.DeleteFileImportedStepFactory;
import br.com.company.analisedadosdevenda.batch.step.importdatasale.ImportDataSaleStepFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@EnableBatchProcessing
public class JobFactory {

    private JobBuilderFactory jobBuilderFactory;
    private ImportDataSaleStepFactory importDataSaleStepFactory;
    private DeleteFileImportedStepFactory deleteFileImportedStepFactory;
    @Value("${filePath}") String filePath;

    public JobFactory(
            JobRepository jobRepository,
            DeleteFileImportedStepFactory deleteFileImportedStepFactory,
            ImportDataSaleStepFactory importDataSaleStepFactory) {

        this.jobBuilderFactory = new JobBuilderFactory(jobRepository);
        this.importDataSaleStepFactory = importDataSaleStepFactory;
        this.deleteFileImportedStepFactory = deleteFileImportedStepFactory;
    }

    @Bean
    public Job importDataSale()  {

            return this
                    .jobBuilderFactory
                    .get("importDataSaleJob")
                    .incrementer(new RunIdIncrementer())
                    .flow(this.importDataSaleStepFactory.importDataSale())
                    .end()
                    .start(this.deleteFileImportedStepFactory.deleteFileTasklet())
                    .end()
                    .build();

    }

    @Bean
    private JobRepository createJobRepository()  {

        MapJobRepositoryFactoryBean factory = new MapJobRepositoryFactoryBean();
        factory.setTransactionManager(new ResourcelessTransactionManager());

        JobRepository jobRepository = null;

        try {
            jobRepository = factory.getObject();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jobRepository;
    }


}
