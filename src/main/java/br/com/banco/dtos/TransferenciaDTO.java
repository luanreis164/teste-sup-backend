package br.com.banco.dtos;

import br.com.banco.domain.Transferencia;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TransferenciaDTO implements Serializable {

    private Date dataTransferencia = new Date();
    private Double valor;
    private String tipo;
    private String nomeOperadorTransacao;

    public TransferenciaDTO(Transferencia obj) {
        dataTransferencia = obj.getDataTransferencia();
        valor = obj.getValor();
        tipo = obj.getTipo();
        nomeOperadorTransacao = obj.getNomeOperadorTransacao();
    }


}
