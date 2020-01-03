package com.company.salesanalysis.batch.step.importdatasale.reader.salesman;

import com.company.salesanalysis.domain.model.Line;
import com.company.salesanalysis.domain.model.Salesman;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class SalesmanFieldSetMapper implements FieldSetMapper<Line> {

    private String fileName;

    public SalesmanFieldSetMapper(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Salesman mapFieldSet(FieldSet fieldSet) {

        return new Salesman(
                fileName,
                fieldSet.readLong(SalesmanFieldName.CPF.name()),
                fieldSet.readString(SalesmanFieldName.NAME.name()),
                fieldSet.readDouble(SalesmanFieldName.SALARY.name())
        );
    }
}
