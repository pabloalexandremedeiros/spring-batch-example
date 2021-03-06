package com.company.salesanalysis.batch.step.importdatasale.reader.client;

import com.company.salesanalysis.domain.model.Client;
import com.company.salesanalysis.domain.model.Line;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class ClientFieldSetMapper implements FieldSetMapper<Line> {

    private String fileName;

    public ClientFieldSetMapper(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Client mapFieldSet(FieldSet fieldSet) {

        String cpnjString = fieldSet.readString(ClientFieldName.CNPJ.name());

         return new Client(
                 fileName,
                 cpnjString.isEmpty() ? 0L : Long.valueOf(cpnjString),
                 fieldSet.readString(ClientFieldName.NAME.name()),
                 fieldSet.readString(ClientFieldName.BUSINESS_AREA.name())
         );
    }
}
