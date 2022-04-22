package br.com.banco.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class NovaTransferenciaDTO implements Serializable{

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date dataTransferencia;

    @NotNull
    private Double valor;

    @NotEmpty(message = "Preenchimento obrigatório!")
    @Size(min = 3,max = 80,message = "O tamanho deve ter entre 3 e 30 caracteres!")
    private String tipo;

    private String nomeOperadorTransacao;

    @NotNull
    private Integer contaId;



}
