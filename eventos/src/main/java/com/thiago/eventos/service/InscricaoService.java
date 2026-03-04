package com.thiago.eventos.service;

import com.thiago.eventos.exception.ConflictException;
import com.thiago.eventos.exception.NotFoundException;
import com.thiago.eventos.model.Eventos;
import com.thiago.eventos.model.Inscricao;
import com.thiago.eventos.model.Participantes;
import com.thiago.eventos.model.StatusEventos;
import com.thiago.eventos.repository.EventosRepository;
import com.thiago.eventos.repository.InscricaoRepository;
import com.thiago.eventos.repository.ParticipantesRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InscricaoService {
    private InscricaoRepository inscricaoRepository;
    private EventosRepository eventosRepository;
    private ParticipantesRepository participantesRepository;

    public InscricaoService(InscricaoRepository inscricaoRepository, EventosRepository eventosRepository, ParticipantesRepository participantesRepository) {
        this.inscricaoRepository = inscricaoRepository;
        this.eventosRepository = eventosRepository;
        this.participantesRepository = participantesRepository;
    }

    @Transactional
    public void inscrever(Long eventos_id, Long participantes_id) {
        Eventos eventos = eventosRepository.findById(eventos_id).
                orElseThrow(() -> new NotFoundException("Evento nao encontrado"));

        Participantes participantes = participantesRepository.findById(participantes_id).
                orElseThrow(() -> new NotFoundException("Participante nao encontrado"));

        // Evento nao pode estar CANCELADO OU FINALIZADO
        if (eventos.getStatus() == StatusEventos.CANCELADO || eventos.getStatus() == StatusEventos.CONCLUIDO) {
            throw new ConflictException("Evento não aceita inscrições");
        }

        // Deve haver vaga disponivel
        if (eventos.getVagaDisponivel() <= 0) {
            throw new ConflictException("Evento sem vagas disponíveis");
        }

        // Participantes nao pode estar duplicados
        if (inscricaoRepository.existsByEventosAndParticipantes(eventos, participantes)) {
            throw new ConflictException("Participante já inscrito no evento");
        }

        // Criar a inscricao
        Inscricao inscricao = new Inscricao();
        inscricao.setEventos(eventos);
        inscricao.setParticipantes(participantes);
        inscricaoRepository.save(inscricao);

        // Atualiza as vagas
        eventos.setVagaDisponivel(eventos.getVagaDisponivel() - 1);
        eventosRepository.save(eventos);
    }

    @Transactional
    public void cancelarInscrever(Long eventos_id, Long participantes_id) {
        Eventos eventos = eventosRepository.findById(eventos_id).
                orElseThrow(() -> new NotFoundException("Evento nao encontrado"));

        Participantes participantes = participantesRepository.findById(participantes_id).
                orElseThrow(() -> new NotFoundException("Participante nao encontrado"));

        Inscricao inscricao = inscricaoRepository.findAll().stream().
                filter(i -> i.getEventos().equals(eventos) && i.getParticipantes().equals(participantes)).
                findFirst().orElseThrow(() -> new NotFoundException("Inscricao nao encontrado"));

        inscricaoRepository.delete(inscricao);

        eventos.setVagaDisponivel(eventos.getVagaDisponivel() + 1);
        eventosRepository.save(eventos);
    }

    public List<Participantes> listarInscritos(Long eventoId) {
        Eventos evento = eventosRepository.findById(eventoId)
                .orElseThrow(() -> new NotFoundException("Evento não encontrado"));

        return inscricaoRepository.findByEventos(evento).stream().
                map(Inscricao::getParticipantes).toList();
    }
}