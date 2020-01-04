package com.company.salesanalysis.batch.step.importdatasale;

import com.company.salesanalysis.batch.ContextProvider;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.partition.support.StepExecutionAggregator;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ImportDataSalesStepAggregator implements StepExecutionAggregator {


    @Override
    public void aggregate(StepExecution result, Collection<StepExecution> executions) {

        List<String> fileNames = executions
                .parallelStream()
                .map(StepExecution::getExecutionContext)
                .map(ContextProvider::getFileNameInStepContext)
                .collect(Collectors.toList());

        ContextProvider
                .putFileNamesInJobContext(fileNames, result.getJobExecution().getExecutionContext());

        result.setExitStatus(ExitStatus.COMPLETED);
    }


}
