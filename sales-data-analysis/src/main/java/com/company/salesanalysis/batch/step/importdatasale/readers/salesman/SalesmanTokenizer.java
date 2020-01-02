package com.company.salesanalysis.batch.step.importdatasale.readers.salesman;

import org.springframework.batch.item.file.transform.DefaultFieldSet;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.LineTokenizer;

public class SalesmanTokenizer implements LineTokenizer {

    private static final String FIELDS_SEPARATOR = "ç";
    private static final String[] FIELD_NAMES = { SalesmanFieldName.CPF.name(), SalesmanFieldName.NAME.name(), SalesmanFieldName.SALARY.name() };

    @Override
    public FieldSet tokenize(String line) {

        final String[] fields = line
                .substring(4)
                .split(FIELDS_SEPARATOR);

        return new DefaultFieldSet(fields, FIELD_NAMES);
    }
}
