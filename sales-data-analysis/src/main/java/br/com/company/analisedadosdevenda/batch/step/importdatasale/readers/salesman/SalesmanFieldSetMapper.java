package br.com.company.analisedadosdevenda.batch.step.importdatasale.readers.salesman;

import br.com.company.analisedadosdevenda.model.Line;
import br.com.company.analisedadosdevenda.model.Salesman;
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
