package com.thiago.eventos.repository;

import com.thiago.eventos.model.Eventos;
import com.thiago.eventos.model.Inscricao;
import com.thiago.eventos.model.Participantes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {
    boolean existsByEventosAndParticipantes(Eventos eventos, Participantes participantes);

    List<Inscricao> findByEventos(Eventos eventos);
}
