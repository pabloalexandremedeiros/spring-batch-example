package br.com.company.analisedadosdevenda;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AnaliseDadosDeVendaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnaliseDadosDeVendaApplication.class, args);
	}

}
