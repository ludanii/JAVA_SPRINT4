package br.com.fiap.dto.Telefone;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TelefoneUsuario {
    private int NR_DDD, NR_DDI, ID_TELEFONE, ID_USUARIO;
    private String TP_TELEFONE, ST_TELEFONE, NR_TELEFONE, NR_TELEFONE_COMPLETO;

    public String telefoneFormatado(String NR_TELEFONE) {
        if (NR_TELEFONE != null) { // Adicionando verificação para evitar NullPointerException
            if (NR_TELEFONE.length() == 9) {
                return NR_TELEFONE.substring(0, 5) + "-" + NR_TELEFONE.substring(5);
            } else if (NR_TELEFONE.length() == 8) {
                return NR_TELEFONE.substring(0, 4) + "-" + NR_TELEFONE.substring(4);
            }
        }
        return NR_TELEFONE; // Retorna NR_TELEFONE como está se nulo
    }
    public String getTelefoneCompleto() {
        return String.format("+%d (%d) %s", NR_DDI, NR_DDD, telefoneFormatado(NR_TELEFONE));
    }
}
