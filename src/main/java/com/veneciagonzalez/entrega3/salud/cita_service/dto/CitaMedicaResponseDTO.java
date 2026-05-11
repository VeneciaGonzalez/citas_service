package com.veneciagonzalez.entrega3.salud.cita_service.dto;

import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CitaMedicaResponseDTO extends RepresentationModel<CitaMedicaResponseDTO> {

    private Long id;
    private String nombrePaciente;
    private String especialidad;
    private LocalDateTime fechaCita;
    private String estadoCita;
    private Integer activo;
}