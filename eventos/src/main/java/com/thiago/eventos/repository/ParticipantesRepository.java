package com.thiago.eventos.repository;

import com.thiago.eventos.model.Participantes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantesRepository extends JpaRepository<Participantes,Long> {
}
