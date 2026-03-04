package com.thiago.eventos.dto;

import com.thiago.eventos.model.StatusEventos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record EventosDto(
       String nome,
       String descricao,
       String local,
       Integer vagaTotal,
       Integer vagaDisponiveis,
       LocalDateTime dataHora,
       StatusEventos status
) {
}
