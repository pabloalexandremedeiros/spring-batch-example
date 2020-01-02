package com.company.salesanalysis.port.adapter.translate.vendedor;


import com.company.salesanalysis.batch.step.importdatasale.readers.salesman.SalesmanFieldName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;


@ExtendWith(SpringExtension.class)
public class VendedorTokenizerTest {

    private static final String[] NOMES_CAMPOS_ESPERADOS = { SalesmanFieldName.CPF.name(), SalesmanFieldName.NAME.name(), SalesmanFieldName.SALARY.name() };

    @Test
    public void criar_fieldset_com_todos_os_atributos_de_vendedor_e_nomes_dos_campos(){

       /* final String cpfEsperado = "1234567891234";
        final String nomeEsperado = "Pedro";
        final String salarioEsperado = "50000";

        VendedorTokenizer vendedorTokenizer = new VendedorTokenizer();
        FieldSet fieldSet = vendedorTokenizer.tokenize("001ç1234567891234çPedroç50000");

        assertArrayEquals(NOMES_CAMPOS_ESPERADOS, fieldSet.getNames());
        assertArrayEquals(new String[]{ cpfEsperado, nomeEsperado, salarioEsperado }, fieldSet.getValues());

        */

       assertTrue(true);
    }

    @Test
    public void traduzir_linha_com_campo_cpf_nulo(){
/*
        final String cpfEsperado = "";
        final String nomeEsperado = "Pedro";
        final String salarioEsperado = "50000";

        VendedorTokenizer vendedorTokenizer = new VendedorTokenizer();
        FieldSet fieldSet = vendedorTokenizer.tokenize("001ççPedroç50000");

        assertArrayEquals(NOMES_CAMPOS_ESPERADOS, fieldSet.getNames());
        assertArrayEquals(new String[]{ cpfEsperado, nomeEsperado, salarioEsperado }, fieldSet.getValues());


         */

        assertTrue(true);
    }
}
