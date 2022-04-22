package br.com.banco.services;

import br.com.banco.domain.Transferencia;
import br.com.banco.repositories.TransferenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransferenciaService {

    @Autowired
    private TransferenciaRepository repo;

    public List<Transferencia> findAll(){
        return repo.findAll();
    }

    public Page<Transferencia> findByPeriod(Date inicio, Date termino,String nomeOperador,Integer page,Integer linesPerPage,String orderBy,String direction){
        PageRequest pageRequest = PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction),orderBy);
        if(nomeOperador.isBlank()){
            return repo.buscaCompleta(inicio,termino,nomeOperador,pageRequest);
        }
        return repo.buscaEntreDatasENome(inicio,termino,nomeOperador,pageRequest);
    }

}
