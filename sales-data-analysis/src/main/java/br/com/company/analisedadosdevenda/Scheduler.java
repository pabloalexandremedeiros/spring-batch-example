package br.com.company.analisedadosdevenda;

import br.com.company.analisedadosdevenda.batch.JobFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class Scheduler {

    private JobFactory jobFactory;
    private JobLauncher jobLauncher;

    public Scheduler(JobFactory jobFactory, JobLauncher jobLauncher){
        this.jobLauncher = jobLauncher;
        this.jobFactory = jobFactory;
    }

    @Scheduled(fixedDelay = 1000)
    public void run() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        Job job = jobFactory.importDataSale();

        JobParameters params = new JobParametersBuilder().addLong("jobId", System.currentTimeMillis()).toJobParameters();
        jobLauncher.run(job, params);

    }



}
