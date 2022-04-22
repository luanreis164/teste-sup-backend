package br.com.banco.services;

import br.com.banco.domain.Conta;
import br.com.banco.domain.Transferencia;
import br.com.banco.dtos.NovaTransferenciaDTO;
import br.com.banco.repositories.ContaRepository;
import br.com.banco.repositories.TransferenciaRepository;
import br.com.banco.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransferenciaService {

    @Autowired
    private TransferenciaRepository repo;

    @Autowired
    private ContaRepository contaRepository;

    public List<Transferencia> findAll(){
        return repo.findAll();
    }

    public Transferencia findOneById(Integer id){
        Optional<Transferencia> obj = repo.findById(id);
        return obj.orElseThrow( () -> new ObjectNotFoundException("Transferencia não encontrada!Id:" + id + ",Tipo: " + Transferencia.class.getName()) );
    }
    public Transferencia insert(Transferencia obj){
        obj.setId(null);
        return repo.save(obj);
    }

    public Page<Transferencia> findByPeriod(Date inicio, Date termino,String nomeOperador,Integer page,Integer linesPerPage,String orderBy,String direction){
        PageRequest pageRequest = PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction),orderBy);
        if(nomeOperador.isBlank()){
            return repo.buscaCompleta(inicio,termino,nomeOperador,pageRequest);
        }
        return repo.buscaEntreDatasENome(inicio,termino,nomeOperador,pageRequest);
    }

    public Transferencia fromDTO(NovaTransferenciaDTO objDto){
        Conta conta = contaRepository.getById(objDto.getContaId());
        return new Transferencia(null, objDto.getDataTransferencia(), objDto.getValor(), objDto.getTipo(), objDto.getNomeOperadorTransacao(), conta);
    }

}
