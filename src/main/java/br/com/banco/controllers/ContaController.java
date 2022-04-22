package br.com.banco.controllers;

import br.com.banco.domain.Conta;
import br.com.banco.dtos.ContaDTO;
import br.com.banco.services.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;


    @GetMapping
    public ResponseEntity<List<ContaDTO>> buscarContas(){
        List<Conta> lista = contaService.findAll();
        List<ContaDTO> listaDto = lista.stream().map(ContaDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listaDto);
    }

}
