package com.veneciagonzalez.entrega3.salud.cita_service.dto;


import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaMedicaResponseDTO {

    private Long id;
    private String nombrePaciente;
    private String especialidad;
    private LocalDateTime fechaCita;
    private String estadoCita;
    private Integer activo;
}