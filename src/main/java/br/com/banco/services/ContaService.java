package br.com.banco.services;

import br.com.banco.domain.Conta;
import br.com.banco.dtos.ContaDTO;
import br.com.banco.repositories.ContaRepository;
import br.com.banco.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        return obj.orElseThrow( () -> new ObjectNotFoundException("Conta não encontrada!Id:" + id + ",Tipo: " + Conta.class.getName()) );
    }

    @Transactional
    public Conta insert(Conta obj){
        obj.setIdConta(null);
        return repo.save(obj);
    }

    public Conta update(Conta obj){
        Conta newObj = findOneById(obj.getIdConta());
        updateData(newObj,obj);
        return repo.save(newObj);
    }

    public Conta fromDTO(ContaDTO objDto){
        return new Conta(null, objDto.getNomeResponsavel(),null);
    }

    private void updateData(Conta newObj, Conta obj){
        newObj.setNomeResponsavel(obj.getNomeResponsavel());
    }

    public void delete(Integer id){
        findOneById(id);
        repo.deleteById(id);
    }


}
