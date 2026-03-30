package com.veneciagonzalez.entrega3.salud.cita_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    
    public ResponseEntity<Map<String, Object>> manejarValidaciones(MethodArgumentNotValidException ex) {
        
        Map<String, String> erroresCampos = new HashMap<>();
        
        // Recorre cada eerror de validación y arma un mapa: campo -> mensaje
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String campo = ((FieldError) error).getField(); // campo que falló
            String mensaje = error.getDefaultMessage();     // mensaje para  @Valid
            erroresCampos.put(campo, mensaje);
        });

        Map<String, Object> respuesta = new HashMap<>();

        respuesta.put("timestamp", LocalDateTime.now().toString()); // momento del error
        respuesta.put("status", 400);
        respuesta.put("error", "Error de validación");
        respuesta.put("errores", erroresCampos); // detalle por campo

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> manejarErrorGeneral(Exception ex) {
       
        Map<String, Object> respuesta = new HashMap<>();
        
        respuesta.put("timestamp", LocalDateTime.now().toString());
        respuesta.put("status", 500);
        respuesta.put("error", "Error interno del servidor");

        // mensaje  para  debug
        respuesta.put("mensaje", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
    }
}