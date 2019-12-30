package br.com.company.analisedadosdevenda.port.adapter.translate.cliente;

import br.com.company.analisedadosdevenda.batch.step.importdatasale.readers.client.ClientTokenizer;
import br.com.company.analisedadosdevenda.batch.step.importdatasale.readers.client.ClientFieldName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.*;


@ExtendWith(SpringExtension.class)
public class ClienteTokenizerTest {

    private static final String[] NOMES_CAMPOS = { ClientFieldName.CNPJ.name(), ClientFieldName.NAME.name(), ClientFieldName.BUSINESS_AREA.name() };

    @Test
    public void criar_fieldset_com_todos_atributos_e_nomes(){

        String linhaEsperada = "002ç2345675434544345çJose da SilvaçRural";
        String[] camposEsperados = linhaEsperada.substring(4).split("ç");

        FieldSet fieldSet = new ClientTokenizer().tokenize(linhaEsperada);
        assertArrayEquals(fieldSet.getValues(), camposEsperados);
        assertArrayEquals(fieldSet.getNames(), NOMES_CAMPOS);
    }

    @Test
    public void criar_fieldset_sem_campo_cnpj(){

        String linhaEsperada = "002ççJose da SilvaçRural";
        String[] camposEsperados = linhaEsperada.substring(4).split("ç");

        FieldSet fieldSet = new ClientTokenizer().tokenize(linhaEsperada);
        assertArrayEquals(fieldSet.getValues(), camposEsperados);
        assertArrayEquals(fieldSet.getNames(), NOMES_CAMPOS);
    }
}
