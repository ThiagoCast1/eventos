package com.thiago.eventos.service;


import com.thiago.eventos.exception.NotFoundException;
import com.thiago.eventos.model.Eventos;
import com.thiago.eventos.repository.EventosRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventosService {
    private final EventosRepository eventosRepository;

    public EventosService(EventosRepository eventosRepository) {
        this.eventosRepository = eventosRepository;
    }

    public List<Eventos> findall() {
        return eventosRepository.findAll();
    }

    public Optional<Eventos> findById(Long id) {
        return Optional.of(eventosRepository.findById(id).orElseThrow(() -> new NotFoundException("Evento nao encontrado")));
    }

    public Eventos save(Eventos eventos) {
        return eventosRepository.save(eventos);
    }

    public void delete(Long id) {
        Eventos eventos = eventosRepository.findById(id).orElseThrow(() -> new NotFoundException("Evento nao encontrado"));

    }

    public Eventos update(Long id, Eventos novosDados) {
        Eventos eventoExistente = eventosRepository.findById(id).orElseThrow(() -> new NotFoundException("Evento nao encontrado"));
        eventoExistente.setNome(novosDados.getNome());
        eventoExistente.setDescricao(novosDados.getDescricao());
        eventoExistente.setLocal(novosDados.getLocal());
        eventoExistente.setVagaTotal(novosDados.getVagaTotal());
        eventoExistente.setVagaDisponivel(novosDados.getVagaDisponivel());
        eventoExistente.setDataHora(novosDados.getDataHora());
        eventoExistente.setStatus(novosDados.getStatus());
        return eventosRepository.save(eventoExistente);
    }
}
