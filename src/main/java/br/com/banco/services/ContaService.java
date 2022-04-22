package br.com.banco.services;

import br.com.banco.domain.Conta;

import br.com.banco.domain.Transferencia;
import br.com.banco.dtos.ContaDTO;
import br.com.banco.repositories.ContaRepository;
import br.com.banco.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository repo;

    public List<Conta> findAll(){
        return repo.findAll();
    }

    public Conta findOneById(Integer id){
        Optional<Conta> obj = repo.findById(id);
        return obj.orElseThrow( () -> new ObjectNotFoundException("Conta não encontrada!Id:" + id + ",Tipo: " + Transferencia.class.getName()) );
    }


}
