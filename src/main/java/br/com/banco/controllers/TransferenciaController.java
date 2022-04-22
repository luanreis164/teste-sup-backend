package br.com.banco.controllers;

import br.com.banco.domain.Transferencia;
import br.com.banco.dtos.TransferenciaDTO;
import br.com.banco.repositories.TransferenciaRepository;
import br.com.banco.services.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/transferencias")
public class TransferenciaController {

    @Autowired
    private TransferenciaService transferenciaService;

    @Autowired
    private TransferenciaRepository repo;

    @GetMapping
    public ResponseEntity<List<TransferenciaDTO>> findAll(){
        List<Transferencia> lista = repo.findAll();
        List<TransferenciaDTO> listaDto = lista.stream().map(TransferenciaDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listaDto);
    }

    @GetMapping(value = "/buscar")
    public ResponseEntity<Page<TransferenciaDTO>> findByPeriodPage(
            @RequestParam(value = "inicio",defaultValue = "01/01/2010") Date inicio,
            @RequestParam(value = "termino",defaultValue = "01/01/2500") Date termino,
            @RequestParam(value = "nomeOperador",required = false) String nomeOperador,
            @RequestParam(value = "page",defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage",defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy",defaultValue = "dataTransferencia") String orderBy,
            @RequestParam(value = "direction",defaultValue = "DESC") String direction
            ) {
        Page<Transferencia> lista = transferenciaService.findByPeriod(inicio,termino,nomeOperador,page,linesPerPage,orderBy,direction);
        Page<TransferenciaDTO> listaDTO = lista.map(TransferenciaDTO::new);
        return ResponseEntity.ok().body(listaDTO);
    }
}

