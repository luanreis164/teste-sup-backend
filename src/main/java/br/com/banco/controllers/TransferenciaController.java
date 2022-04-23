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


    @GetMapping(value = "/{id}")
    public ResponseEntity<Transferencia> find(@PathVariable Integer id){
        Transferencia obj = transferenciaService.findOneById(id);
        return  ResponseEntity.ok().body(obj);
    }

    @GetMapping
    public ResponseEntity<Page<TransferenciaDTO>> findByPeriodPage(
            @RequestParam(value = "inicio",defaultValue = "01/01/2010") Date inicio,
            @RequestParam(value = "termino",defaultValue = "01/01/2500") Date termino,
            @RequestParam(value = "nomeOperador",defaultValue = "") String nomeOperador,
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

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@RequestBody TransferenciaDTO objDto, @PathVariable Integer id){
        Transferencia obj = transferenciaService.fromDTO(objDto);
        obj.setId(id);
        transferenciaService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> updatePatch(@RequestBody TransferenciaDTO objDto, @PathVariable Integer id){
        Transferencia obj = transferenciaService.fromDTO(objDto);
        obj.setId(id);
        obj = transferenciaService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        transferenciaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

