package br.com.banco.services;

import br.com.banco.domain.Conta;
import br.com.banco.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaService {

    @Autowired
    private ContaRepository repo;

    public List<Conta> findAll(){
        return repo.findAll();
    }

}
