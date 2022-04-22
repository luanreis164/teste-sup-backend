package br.com.banco.controllers;

import br.com.banco.domain.Transferencia;
import br.com.banco.dtos.NovaTransferenciaDTO;
import br.com.banco.dtos.TransferenciaDTO;
import br.com.banco.services.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/transferencias")
public class TransferenciaController {

    @Autowired
    private TransferenciaService transferenciaService;

    @GetMapping
    public ResponseEntity<List<TransferenciaDTO>> findAll(){
        List<Transferencia> lista = transferenciaService.findAll();
        List<TransferenciaDTO> listaDto = lista.stream().map(TransferenciaDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listaDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Transferencia> find(@PathVariable Integer id){
        Transferencia obj = transferenciaService.findOneById(id);
        return  ResponseEntity.ok().body(obj);
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

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody NovaTransferenciaDTO objDto){
        Transferencia obj = transferenciaService.fromDTO(objDto);
        obj = transferenciaService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }


}
