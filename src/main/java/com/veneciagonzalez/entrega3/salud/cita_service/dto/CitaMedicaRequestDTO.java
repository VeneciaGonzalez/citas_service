package com.veneciagonzalez.entrega3.salud.cita_service.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaMedicaRequestDTO {

    @NotBlank(message = "El nombre del paciente debe ser ingresado.")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres.")
    private String nombrePaciente;

    @NotBlank(message = "Debe ingresar la especialidad.")
    @Size(min = 3, max = 100, message = "La especialidad debe tener entre 3 y 100 caracteres.")
    private String especialidad;

    @NotNull(message = "Debe ingresar la fecha y hora de la cita.")
    @Future(message = "La fecha de la cita debe ser futura.")
    private LocalDateTime fechaCita;

    @NotBlank(message = "Debe ingresar el estado de la cita.")
    private String estadoCita;
}