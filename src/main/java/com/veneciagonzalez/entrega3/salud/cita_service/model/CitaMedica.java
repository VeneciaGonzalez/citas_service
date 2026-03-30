package com.veneciagonzalez.entrega3.salud.cita_service.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitaMedica {

    @NotNull(message = "Debe ingresar ID.")
    private Long id;

    @NotBlank(message = "Debe ingresar nombre del paciente.")
    private String nombrePaciente;

    @NotBlank(message = "Debe ingresar La especialidad.")
    private String especialidad;

    @NotBlank(message = "Debe ingresar la fecha.")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "El formato de la fecha debe ser YYYY-MM-DD")
    private String fecha;

    @NotBlank(message = "Debe ingresar la hora")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "El formato de la hora debe ser HH:mm")
    private String hora;

    private String estadoCita;   //Pendiente, Confirmada y Cancelada
}