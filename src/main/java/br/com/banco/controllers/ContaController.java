package br.com.banco.controllers;

import br.com.banco.domain.Conta;
import br.com.banco.dtos.ContaDTO;
import br.com.banco.services.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @GetMapping
    public ResponseEntity<List<ContaDTO>> findAll(){
        List<Conta> lista = contaService.findAll();
        List<ContaDTO> listaDto = lista.stream().map(ContaDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listaDto);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<Conta> find(@PathVariable Integer id){
        Conta obj = contaService.findOneById(id);
        return  ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody ContaDTO objDto){
        Conta obj = contaService.fromDTO(objDto);
        obj = contaService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getIdConta()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@RequestBody ContaDTO objDto, @PathVariable Integer id){
        Conta obj = contaService.fromDTO(objDto);
        obj.setIdConta(id);
        contaService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> updatePatch(@RequestBody ContaDTO objDto, @PathVariable Integer id){
        Conta obj = contaService.fromDTO(objDto);
        obj.setIdConta(id);
        obj = contaService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        contaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
