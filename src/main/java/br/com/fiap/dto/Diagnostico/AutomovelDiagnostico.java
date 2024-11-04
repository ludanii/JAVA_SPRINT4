package br.com.fiap.dto.Diagnostico;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
@Getter @Setter
public class AutomovelDiagnostico {
    private int ID_DIAGNOSTICO, VL_ORCAMENTO_PREVISTO, ID_AUTOMOVEL;
    private String TP_FEEDBACK_DIAGNOSTICO, DS_FEEDBACK_DIAGNOSTICO, DS_PROBLEMA, NR_HORARIO;
    private LocalDate DT_INICIO;

    private String formatarHora(String NR_HORARIO) {
        if (NR_HORARIO == null || NR_HORARIO.isEmpty()) {
            return NR_HORARIO;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime time = LocalTime.parse(NR_HORARIO, formatter);
            return time.format(formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Formato de hora inválido! Insira no formato HH:mm.");
            return null;
        }
    }
}


