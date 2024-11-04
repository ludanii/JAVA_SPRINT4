package br.com.fiap.dto.Usuario;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class CadastroUsuario {
    private String NM_USUARIO, NR_CPF;
    private int ID_USUARIO, NR_CNH;
    private LocalDate DT_NASCIMENTO;
}
