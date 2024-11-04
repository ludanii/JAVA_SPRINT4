package br.com.fiap.dto.Endereco;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EnderecoUsuario {
    private int ID_ENDERECO;
    private String NM_CIDADE, NM_ESTADO, NM_LOGRADOURO,
            TP_ENDERECO, DS_COMPLEMENTO, DS_PONTO_REFERENCIA, NM_BAIRRO, NR_CEP;

    public String cepFormatado(String NR_CEP){
        NR_CEP = NR_CEP.replaceAll("\\D", "");

        if (NR_CEP.length() == 8) {
            return NR_CEP.substring(0, 5) + "-" + NR_CEP.substring(5);
        } else {
            throw new IllegalArgumentException("CEP inválido. O CEP deve conter 8 dígitos.");
        }
    }

    public String logradouroFormatado(){
        NR_CEP = cepFormatado(NR_CEP);
        return NM_LOGRADOURO = String.format("%s, %s, %s, %s", NR_CEP, NM_BAIRRO, NM_CIDADE, NM_ESTADO);
    }
}
