package br.com.fiap.dto.Diagnostico;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter @Setter
public class CadastrarDiagnostico {
    private int VL_ORCAMENTO_PREVISTO, ID_AUTOMOVEL;
    private String TP_FEEDBACK_DIAGNOSTICO, DS_FEEDBACK_DIAGNOSTICO, DS_PROBLEMA, NR_HORARIO;
    private LocalDate DT_INICIO;
}
