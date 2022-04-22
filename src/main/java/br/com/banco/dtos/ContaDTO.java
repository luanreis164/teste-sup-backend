package br.com.banco.dtos;

import br.com.banco.domain.Conta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ContaDTO implements Serializable{

    private Integer idConta;
    @NotEmpty(message = "Preenchimento obrigatório!")
    private String nomeResponsavel;

    public ContaDTO(Conta obj) {
        idConta = obj.getIdConta();
        nomeResponsavel = obj.getNomeResponsavel();
    }

}
