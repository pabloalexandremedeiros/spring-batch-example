package br.com.company.analisedadosdevenda.batch.step.importdatasale.readers.sale;

import br.com.company.analisedadosdevenda.model.Item;
import br.com.company.analisedadosdevenda.model.Line;
import br.com.company.analisedadosdevenda.model.Sale;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SaleFieldSetMapper implements FieldSetMapper<Line> {

    private String fileName;

    public SaleFieldSetMapper(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Sale mapFieldSet(FieldSet fieldSet) {

        Collection<Item> items = createItemsFromFieldset(fieldSet);
        Double totalValue = calculateTotalSaleValue(items);

        return new Sale(
                fileName,
                fieldSet.readLong(SaleFieldName.ID.name()),
                fieldSet.readString(SaleFieldName.SALEMAN_NAME.name()),
                totalValue,
                items
        );
    }

    private static Double calculateTotalSaleValue(Collection<Item> items){

        return items
                .stream()
                .map(item ->

                    BigDecimal
                            .valueOf(item.getPrice())
                            .multiply(BigDecimal.valueOf(item.getQuantity()))
                            .setScale(2, RoundingMode.HALF_DOWN)

                )
                .mapToDouble(BigDecimal::doubleValue)
                .sum();
    }

    private static Collection<Item> createItemsFromFieldset(FieldSet fieldSet) {

        final String itensFieldSetWithKeys = fieldSet
                .readRawString(SaleFieldName.ITENS.name());

        if(itensFieldSetWithKeys.length() < 3){
            return new ArrayList<>();

        } else {

            Long saleId = fieldSet.readLong(SaleFieldName.ID.name());

            String[] itemsFieldSet = itensFieldSetWithKeys
                    .substring(1, itensFieldSetWithKeys.length() - 1).split(",");

            return Stream
                    .of(itemsFieldSet)
                    .map(itenString -> {

                        String[] fieldsItem = itenString.split("-");

                        return new Item(
                                Long.valueOf(fieldsItem[0]),
                                saleId,
                                Integer.valueOf(fieldsItem[1]),
                                Double.valueOf(fieldsItem[2])
                        );
                    })
                    .collect(Collectors.toUnmodifiableList());
        }

    }
}
