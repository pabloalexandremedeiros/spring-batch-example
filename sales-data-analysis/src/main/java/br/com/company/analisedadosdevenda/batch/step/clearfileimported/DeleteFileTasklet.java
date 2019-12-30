package br.com.company.analisedadosdevenda.batch.step.clearfileimported;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.UnexpectedJobExecutionException;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;

public class DeleteFileTasklet implements Tasklet {

    private String filePath;

    public DeleteFileTasklet(String filePath){
        this.filePath = filePath;
    }

    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        Resource resource = new FileSystemResource(this.filePath);
        File file = resource.getFile();

        boolean deleted = file.delete();

        if(!deleted){
            throw new UnexpectedJobExecutionException("Could not delete file " + file.getPath());
        }

        return RepeatStatus.FINISHED;
    }
}
