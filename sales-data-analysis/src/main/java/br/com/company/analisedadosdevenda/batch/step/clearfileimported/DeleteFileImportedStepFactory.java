package br.com.company.analisedadosdevenda.batch.step.clearfileimported;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DeleteFileImportedStepFactory {

    private  StepBuilderFactory stepBuilderFactory;
    @Value("${filesPath}") String filePath;

    public DeleteFileImportedStepFactory(StepBuilderFactory stepBuilderFactory){
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Step deleteFileTasklet(){
        return this
                .stepBuilderFactory
                .get("deleteFileImportedStep")
                .tasklet(new DeleteFileTasklet(filePath))
                .build();
    }

}
