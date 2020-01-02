package br.com.company.analisedadosdevenda.batch;

import br.com.company.analisedadosdevenda.batch.step.importdatasale.ImportDataSaleStepFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@EnableBatchProcessing
public class JobFactory {

    private JobBuilderFactory jobBuilderFactory;
    private ImportDataSaleStepFactory importDataSaleStepFactory;

    public JobFactory(
            JobRepository jobRepository,
            ImportDataSaleStepFactory importDataSaleStepFactory) {

        this.jobBuilderFactory = new JobBuilderFactory(jobRepository);
        this.importDataSaleStepFactory = importDataSaleStepFactory;
    }

    @Bean
    public Job importDataSale()  {

            return this
                    .jobBuilderFactory
                    .get("importDataSaleJob")
                    .incrementer(new RunIdIncrementer())
                    .flow(this.importDataSaleStepFactory.importDataSale())
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
