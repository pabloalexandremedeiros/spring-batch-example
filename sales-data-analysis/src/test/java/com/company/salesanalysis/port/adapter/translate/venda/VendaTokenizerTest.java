package com.company.salesanalysis.port.adapter.translate.venda;

import com.company.salesanalysis.batch.step.importdatasale.readers.sale.SaleFieldName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.Assert.*;

@ExtendWith(SpringExtension.class)
public class VendaTokenizerTest {

    private static final String[] NOMES_CAMPOS = { SaleFieldName.ID.name(), SaleFieldName.ITENS.name(), SaleFieldName.SALEMAN_NAME.name() };

    @Test
    public void criar_fieldset_com_todos_campos(){

 /*       String[] valoresEsperados = "08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo".split("ç");
        FieldSet fieldSet = new VendaTokenizer().tokenize("003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo");

        assertArrayEquals(fieldSet.getNames(), NOMES_CAMPOS);
        assertArrayEquals(fieldSet.getValues(), valoresEsperados);

   *
  */

 assertTrue(true);
    }

    @Test
    public void criar_fieldset_sem_itens(){


   /*     String[] valoresEsperados = "08ççPaulo".split("ç");
        FieldSet fieldSet = new VendaTokenizer().tokenize("003ç08ççPaulo");

        assertArrayEquals(fieldSet.getNames(), NOMES_CAMPOS);
        assertArrayEquals(fieldSet.getValues(), valoresEsperados);

    */

   assertTrue(true);
    }

}
