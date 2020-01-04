package com.company.salesanalysis.batch.step.importdatasale.reader.client;


import org.springframework.batch.item.file.transform.DefaultFieldSet;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.LineTokenizer;

public class ClientTokenizer implements LineTokenizer {

    private static final String FIELDS_SEPARATOR = "ç";
    private static final String[] FIELD_NAMES = { ClientFieldName.CNPJ.name(), ClientFieldName.NAME.name(), ClientFieldName.BUSINESS_AREA.name() };

    @Override
    public FieldSet tokenize(String line) {

        final String[] fields = line
                .substring(4)
                .split(FIELDS_SEPARATOR);

        return new DefaultFieldSet(fields, FIELD_NAMES);
    }
}
