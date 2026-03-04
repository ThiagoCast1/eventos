package com.thiago.eventos.controller;

import com.thiago.eventos.service.InscricaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inscricoes")
public class InscricaoController {
    private final InscricaoService inscricaoService;

    public InscricaoController(InscricaoService inscricaoService) {
        this.inscricaoService = inscricaoService;
    }

    @PostMapping("/evento/{eventoId}/participante/{participanteId}")
    public ResponseEntity<?> inscrever(@PathVariable Long eventoId, @PathVariable Long participanteId) {
        inscricaoService.inscrever(eventoId, participanteId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/evento/{eventoId}/participante/{participanteId}")
    public ResponseEntity<?> delete(@PathVariable Long eventoId, @PathVariable Long participanteId) {
        inscricaoService.cancelarInscrever(eventoId, participanteId);
        return ResponseEntity.noContent().build();
    }
}
