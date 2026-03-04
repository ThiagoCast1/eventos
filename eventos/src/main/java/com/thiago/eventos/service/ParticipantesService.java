package com.thiago.eventos.service;

import com.thiago.eventos.exception.NotFoundException;
import com.thiago.eventos.model.Participantes;
import com.thiago.eventos.repository.ParticipantesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipantesService {
    private final ParticipantesRepository participantesRepository;

    public ParticipantesService(ParticipantesRepository participantesRepository) {
        this.participantesRepository = participantesRepository;
    }

    public List<Participantes> findAll() {
        return participantesRepository.findAll();
    }

    public Optional<Participantes> findById(Long id) {
        return Optional.of(participantesRepository.findById(id).orElseThrow(() -> new NotFoundException("Participante nao encontrado")));
    }

    public Participantes save(Participantes participantes) {
        return participantesRepository.save(participantes);
    }

    public void delete(Long id) {
        Participantes participantes = participantesRepository.findById(id).orElseThrow(() -> new NotFoundException("Participante nao encontrado"));
    }

    public Participantes update(Long id, Participantes novosDados) {
        Participantes ParticipanteExistentes = participantesRepository.findById(id).orElseThrow(() -> new NotFoundException("Participante nao encontrado"));
        ParticipanteExistentes.setNome(novosDados.getNome());
        ParticipanteExistentes.setEmail(novosDados.getEmail());
        return participantesRepository.save(ParticipanteExistentes);
    }
}
