package com.thiago.eventos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExpetion
{
    // Trata exceções de recurso não encontrado (404).
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handlerNotFound(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
    }

    // Trata conflitos de regra de negócio (409).
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> handlerConflit(ConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", ex.getMessage()));
    }

    // Trata erros de requisição inválida (400).
    @ExceptionHandler(BadException.class)
    public ResponseEntity<?> handlerBad(BadException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
    }

    // Trata erros de validação (@Valid) Exemplo:
    // Campo obrigatório nãoinformado
    // Valor fora do padrão esperado
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handlerValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    // Tratamento genérico para qualquer exceção não prevista.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerGeneric(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("Error", " Erro Interno: " + ex.getMessage()));
    }
}
