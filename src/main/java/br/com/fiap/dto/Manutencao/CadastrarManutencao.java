package br.com.fiap.dto.Manutencao;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class CadastrarManutencao {
    private int VL_MANUTENCAO, ID_AUTOMOVEL;
    private String TP_MANUTENCAO, DS_MANUTENCAO, NM_OFICINA, TP_FEEDBACK_OFICINA, DS_FEEDBACK_OFICINA;
    private LocalDate DT_MANUTENCAO;
}
