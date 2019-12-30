package br.com.company.analisedadosdevenda.batch.step.importdatasale.readers.sale;

import org.springframework.batch.item.file.transform.DefaultFieldSet;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.LineTokenizer;

public class SaleTokenizer implements LineTokenizer {

    private static final String FIELD_SEPARATOR = "ç";
    private static final String[] FIELD_NAMES = { SaleFieldName.ID.name(), SaleFieldName.ITENS.name(), SaleFieldName.NOME_VENDEDOR.name() };

    @Override
    public FieldSet tokenize(String line) {

        final String[] fields = line
                .substring(4)
                .split(FIELD_SEPARATOR);

        return new DefaultFieldSet(fields, FIELD_NAMES);
    }
}
