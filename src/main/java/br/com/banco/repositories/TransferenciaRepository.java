package br.com.banco.repositories;

import br.com.banco.domain.Transferencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia,Integer> {

    @Query("select distinct t from Transferencia t " +
           "where t.dataTransferencia between ?1 and ?2 and upper(t.nomeOperadorTransacao) like upper(concat('%', ?3, '%'))")
    Page<Transferencia> buscaEntreDatas(Date inicio, Date termino, String nomeOperadorTransacao, Pageable pageable);

    @Query("select distinct t from Transferencia t " +
            "where t.dataTransferencia between ?1 and ?2 and upper(t.nomeOperadorTransacao) like upper(concat('%', ?3, '%')) or t.nomeOperadorTransacao is null")
    Page<Transferencia> buscaCompleta(Date inicio, Date termino, String nomeOperadorTransacao, Pageable pageable);


}
