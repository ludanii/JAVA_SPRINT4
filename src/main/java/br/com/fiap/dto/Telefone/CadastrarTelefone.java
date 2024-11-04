package br.com.fiap.dto.Telefone;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CadastrarTelefone {
    private int NR_DDD, NR_DDI, ID_USUARIO;
    private String TP_TELEFONE, ST_TELEFONE, NR_TELEFONE;
}
