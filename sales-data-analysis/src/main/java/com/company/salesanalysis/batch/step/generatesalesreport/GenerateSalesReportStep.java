package com.company.salesanalysis.batch.step.generatesalesreport;

import com.company.salesanalysis.batch.step.generatesalesreport.writer.salesreport.CreateSalesReportTasklet;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobInterruptedException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.stereotype.Component;

@Component
public class GenerateSalesReportStep implements Step {

    private TaskletStep taskletStep;
    private StepBuilderFactory stepBuilderFactory;
    private CreateSalesReportTasklet createSalesReportTasklet;

    public GenerateSalesReportStep(
            StepBuilderFactory stepBuilderFactory,
            CreateSalesReportTasklet createSalesReportTasklet) {

        this.stepBuilderFactory = stepBuilderFactory;
        this.createSalesReportTasklet = createSalesReportTasklet;
    }

    @Override
    public String getName() {
        return "salesReportStep";
    }

    @Override
    public boolean isAllowStartIfComplete() {
        return false;
    }

    @Override
    public int getStartLimit() {
        return 1;
    }

    @Override
    public void execute(StepExecution stepExecution) throws JobInterruptedException {

        if(this.taskletStep == null){

            this.taskletStep = this
                    .stepBuilderFactory
                    .get("generateSalesReport")
                    .tasklet(createSalesReportTasklet)
                    .exceptionHandler((context, throwable) -> {throwable.printStackTrace();})
                    .build();
        }

        this.taskletStep.execute(stepExecution);
        stepExecution.setExitStatus(ExitStatus.COMPLETED);
    }



}
