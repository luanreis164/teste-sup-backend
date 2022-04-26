package br.com.banco.services;

import br.com.banco.domain.Conta;
import br.com.banco.domain.Transferencia;
import br.com.banco.dtos.NovaTransferenciaDTO;
import br.com.banco.dtos.TransferenciaDTO;
import br.com.banco.repositories.ContaRepository;
import br.com.banco.repositories.TransferenciaRepository;
import br.com.banco.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransferenciaService {

    @Autowired
    private TransferenciaRepository repo;

    @Autowired
    private ContaService contaService;

    @Autowired
    private ContaRepository contaRepository;


    public List<Transferencia> findAll(){
        return repo.findAll();
    }

    public Transferencia findOneById(Integer id){
        Optional<Transferencia> obj = repo.findById(id);
        return obj.orElseThrow( () -> new ObjectNotFoundException("Transferencia não encontrada!Id:" + id + ",Tipo: " + Transferencia.class.getName()) );
    }
    @Transactional
    public Transferencia insert(Transferencia obj){
        obj.setId(null);
        return repo.save(obj);
    }
    public Transferencia update(Transferencia obj){
        Transferencia newObj = findOneById(obj.getId());
        updateData(newObj,obj);
        return repo.save(newObj);
    }
    public Page<Transferencia> findByPeriod(Date inicio, Date termino,String nomeOperador,Integer page,Integer linesPerPage,String orderBy,String direction){
        PageRequest pageRequest = PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction),orderBy);
        if(nomeOperador.isBlank()){
            return repo.buscaCompleta(inicio,termino,nomeOperador,pageRequest);
        }
        return repo.buscaEntreDatas(inicio,termino,nomeOperador,pageRequest);
    }

    public Transferencia fromDTO(TransferenciaDTO objDto){
        Conta conta =  contaService.findOneById(objDto.getContaId());
        return new Transferencia(objDto.getId(), objDto.getDataTransferencia(), objDto.getValor(), objDto.getTipo(), objDto.getNomeOperadorTransacao(),conta);
    }

    public Transferencia fromDTO(NovaTransferenciaDTO objDto){
        Conta conta = contaRepository.getById(objDto.getContaId());
        return new Transferencia(null, objDto.getDataTransferencia(), objDto.getValor(), objDto.getTipo(), objDto.getNomeOperadorTransacao(), conta);
    }

    private void updateData(Transferencia newObj, Transferencia obj){
        newObj.setDataTransferencia(obj.getDataTransferencia());
        newObj.setValor(obj.getValor());
        newObj.setTipo(obj.getTipo());
        newObj.setConta(obj.getConta());
    }

    public void delete(Integer id){
        findOneById(id);
        repo.deleteById(id);
    }

}
