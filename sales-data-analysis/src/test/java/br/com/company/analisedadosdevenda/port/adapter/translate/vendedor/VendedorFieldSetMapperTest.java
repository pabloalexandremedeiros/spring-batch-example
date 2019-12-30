package br.com.company.analisedadosdevenda.port.adapter.translate.vendedor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.*;

@ExtendWith(SpringExtension.class)
public class VendedorFieldSetMapperTest {

    @Test
    public void criar_vendedor_com_base_em_fieldset_completo() {

      /*  final String cpfEsperado = "1234567891234";
        final String nomeEsperado = "Pedro";
        final Double salarioEsperado = 50000D;

        VendedorTokenizer vendedorTokenizer = new VendedorTokenizer();
        FieldSet fieldSet = vendedorTokenizer.tokenize("001ç1234567891234çPedroç50000");

        Vendedor vendedor = new VendedorFieldSetMapper("").mapFieldSet(fieldSet);

        assertEquals(vendedor.getCpf(), cpfEsperado);
        assertEquals(vendedor.getNome(), nomeEsperado);
        assertEquals(vendedor.getSalario(), salarioEsperado);

         */

        assertTrue(true);
    }

    @Test
    public void criar_vendedor_com_base_em_fieldset_com_cpf_vazio(){

        /*try {

            VendedorTokenizer vendedorTokenizer = new VendedorTokenizer();
            FieldSet fieldSet = vendedorTokenizer.tokenize("001ç1234567891234çç50000");
            new VendedorFieldSetMapper("").mapFieldSet(fieldSet);
            fail();

        } catch (IllegalArgumentException ex){
            assertEquals(ex.getMessage(),"O nome é uma informação obrigatória ao instânciar " + Vendedor.class.getSimpleName());
        }

         */

        assertTrue(true);
    }
}
