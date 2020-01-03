package com.company.salesanalysis.batch;

import com.company.salesanalysis.batch.step.importdatasale.ImportDataSalesStepFactory;
import com.company.salesanalysis.batch.step.generatesalesreport.GenerateSalesReportStep;
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
    private ImportDataSalesStepFactory importDataSaleStepFactory;
    private GenerateSalesReportStep salesReportStep;

    public JobFactory(
            JobRepository jobRepository,
            ImportDataSalesStepFactory importDataSaleStepFactory,
            GenerateSalesReportStep salesReportStep) {

        this.jobBuilderFactory = new JobBuilderFactory(jobRepository);
        this.importDataSaleStepFactory = importDataSaleStepFactory;
        this.salesReportStep = salesReportStep;
    }

    @Bean
    public Job importDataSale()  {

            return this
                    .jobBuilderFactory
                    .get("importDataSaleJob")
                    .incrementer(new RunIdIncrementer())
                    .start(this.importDataSaleStepFactory.createImportDataSaleStep())
                    .next(this.salesReportStep)
                    .build();
    }
}
