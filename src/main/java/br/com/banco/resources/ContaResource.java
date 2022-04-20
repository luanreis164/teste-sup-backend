package br.com.banco.resources;

import br.com.banco.domain.Conta;
import br.com.banco.services.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/contas")
public class ContaResource {

    @Autowired
    private ContaService contaService;


    @GetMapping
    public ResponseEntity<List<Conta>> buscarContas(){
        List<Conta> lista = contaService.findAll();
        return ResponseEntity.ok().body(lista);
    }

}
