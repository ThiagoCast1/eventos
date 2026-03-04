package com.thiago.eventos.controller;

import com.thiago.eventos.dto.ParticipantesDto;
import com.thiago.eventos.exception.NotFoundException;
import com.thiago.eventos.model.Participantes;
import com.thiago.eventos.service.ParticipantesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/participantes")
public class ParticipantesController {
    public final ParticipantesService participantesService;

    public ParticipantesController(ParticipantesService participantesService) {
        this.participantesService = participantesService;
    }

    @GetMapping
    public List<ParticipantesDto> findAll() {
        return participantesService.findAll().
                stream().
                map(this::toDto).
                collect(Collectors.toList());
    }

    @GetMapping("{/id}")
    public ResponseEntity<ParticipantesDto> findById(@PathVariable Long id) {
        ParticipantesDto dto = participantesService.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new NotFoundException("Participante não encontrado"));

        return ResponseEntity.ok(dto);
    }


    @PostMapping
    public ParticipantesDto save(@RequestBody ParticipantesDto dto) {
        Participantes p = new Participantes();
        p.setNome(dto.nome());
        p.setEmail(dto.email());
        Participantes save = participantesService.save(p);
        return toDto(save);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        participantesService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParticipantesDto> update(@PathVariable Long id, @RequestBody ParticipantesDto dto) {
        Participantes p = new Participantes();
        p.setId(id);
        p.setNome(dto.nome());
        p.setEmail(dto.email());

        Participantes atualizado = participantesService.update(id, p);
        return ResponseEntity.ok(toDto(atualizado));
    }

    private ParticipantesDto toDto(Participantes participantes) {
        return new ParticipantesDto(
                participantes.getNome(),
                participantes.getEmail()
        );

    }


}
