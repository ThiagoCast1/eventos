package com.thiago.eventos.controller;

import com.thiago.eventos.dto.EventosDto;
import com.thiago.eventos.exception.NotFoundException;
import com.thiago.eventos.model.Eventos;
import com.thiago.eventos.service.EventosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/eventos")
public class EventosController {
    public final EventosService eventosService;

    public EventosController(EventosService eventosService) {
        this.eventosService = eventosService;
    }

    @GetMapping
    public List<EventosDto> findAll() {
        return eventosService.findall().
                stream().
                map(this::toDto).
                collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventosDto> findById(@PathVariable Long id) {
        EventosDto dto = eventosService.findById(id).map(this::toDto).orElseThrow(() -> new NotFoundException("Evento nao encontrado"));
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public EventosDto save(@RequestBody EventosDto eventosDto) {
        Eventos e = new Eventos();
        e.setNome(eventosDto.nome());
        e.setDescricao(eventosDto.descricao());
        e.setLocal(eventosDto.local());
        e.setVagaTotal(eventosDto.vagaTotal());
        e.setVagaDisponivel(eventosDto.vagaDisponiveis());
        e.setDataHora(eventosDto.dataHora());
        e.setStatus(eventosDto.status());
        Eventos save = eventosService.save(e);
        return toDto(save);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EventosDto> delete(@PathVariable Long id) {
        eventosService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventosDto> update(@RequestBody EventosDto eventosDto, @PathVariable Long id) {
        Eventos eventos = new Eventos();
        eventos.setId(id);
        eventos.setNome(eventosDto.nome());
        eventos.setDescricao(eventosDto.descricao());
        eventos.setLocal(eventosDto.local());
        eventos.setVagaTotal(eventosDto.vagaTotal());
        eventos.setVagaDisponivel(eventosDto.vagaDisponiveis());
        eventos.setDataHora(eventosDto.dataHora());
        eventos.setStatus(eventosDto.status());

        Eventos atualizado = eventosService.update(id, eventos);
        return ResponseEntity.ok().body(toDto(atualizado));
    }

    private EventosDto toDto(Eventos eventos) {
        return new EventosDto(
                eventos.getNome(),
                eventos.getDescricao(),
                eventos.getLocal(),
                eventos.getVagaTotal(),
                eventos.getVagaDisponivel(),
                eventos.getDataHora(),
                eventos.getStatus()
        );
    }
}
