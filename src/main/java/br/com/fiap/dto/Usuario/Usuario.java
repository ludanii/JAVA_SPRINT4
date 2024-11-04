package br.com.fiap.dto.Usuario;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;

@Getter @Setter
public class Usuario {
    private String NM_USUARIO, NR_CPF;
    private int ID_USUARIO, NR_IDADE, NR_CNH;
    private LocalDate DT_NASCIMENTO;

    public int calcularIdade(LocalDate DT_NASCIMENTO) throws IllegalArgumentException {
        LocalDate dataAtual = LocalDate.now();
        NR_IDADE = Period.between(DT_NASCIMENTO, dataAtual).getYears();
        if (NR_IDADE < 18) {
            throw new IllegalArgumentException("A idade não pode ser menor que 18 anos.");
        }
        return NR_IDADE;
    }
}
