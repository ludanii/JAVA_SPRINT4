package br.com.fiap.dto.Endereco;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CadastrarEndereco {
    private String NM_CIDADE, NM_ESTADO,
            TP_ENDERECO, DS_COMPLEMENTO, DS_PONTO_REFERENCIA, NM_BAIRRO, NR_CEP;
}
