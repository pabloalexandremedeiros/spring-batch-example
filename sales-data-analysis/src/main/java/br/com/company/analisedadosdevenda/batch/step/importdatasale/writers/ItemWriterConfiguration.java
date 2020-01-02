package br.com.company.analisedadosdevenda.batch.step.importdatasale.writers;

import br.com.company.analisedadosdevenda.model.Line;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemWriterConfiguration {

    @Bean
    public ClassifierCompositeItemWriter<Line> classifierCompositeItemWriter(ItemWriterClassifier itemClassifier) {

        ClassifierCompositeItemWriter<Line> classifierCompositeItemWriter = new ClassifierCompositeItemWriter<>();
        classifierCompositeItemWriter.setClassifier(itemClassifier);

        return classifierCompositeItemWriter;
    }
}
