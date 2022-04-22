package br.com.banco.dtos;

import br.com.banco.domain.Transferencia;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TransferenciaDTO implements Serializable {

    private Integer id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataTransferencia;
    private Double valor;
    private String tipo;
    private String nomeOperadorTransacao;

    private Integer contaId;


    public TransferenciaDTO(Transferencia obj) {
        id = obj.getId();
        dataTransferencia = obj.getDataTransferencia();
        valor = obj.getValor();
        tipo = obj.getTipo();
        nomeOperadorTransacao = obj.getNomeOperadorTransacao();
        contaId = obj.getConta().getIdConta();
    }


}
