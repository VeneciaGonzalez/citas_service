package com.veneciagonzalez.entrega3.salud.cita_service.service;

import java.time.LocalDateTime;
import java.util.List;



import com.veneciagonzalez.entrega3.salud.cita_service.dto.CitaMedicaRequestDTO;
import com.veneciagonzalez.entrega3.salud.cita_service.dto.CitaMedicaResponseDTO;

public interface CitaService {

    List<CitaMedicaResponseDTO> obtenerTodas();

    CitaMedicaResponseDTO obtenerPorId(Long id);

    List<CitaMedicaResponseDTO> buscarPorEspecialidad(String especialidad);

    List<CitaMedicaResponseDTO> buscarPorEspecialidadParcial(String especialidad);

    List<CitaMedicaResponseDTO> buscarPorEstado(String estadoCita);

    List<CitaMedicaResponseDTO> obtenerActivas();

    List<CitaMedicaResponseDTO> obtenerActivasOrdenadasPorFecha();

    List<CitaMedicaResponseDTO> buscarPorEspecialidadYEstado(String especialidad, String estadoCita);

    List<CitaMedicaResponseDTO> buscarPorFechaDesde(LocalDateTime fecha);

    List<CitaMedicaResponseDTO> buscarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin);

    List<CitaMedicaResponseDTO> buscarPorNombrePaciente(String nombre);

    List<CitaMedicaResponseDTO> buscarComplejo(String especialidad);

    CitaMedicaResponseDTO crearCita(CitaMedicaRequestDTO request);

    CitaMedicaResponseDTO actualizarCita(Long id, CitaMedicaRequestDTO request);

    boolean eliminarCita(Long id);

    boolean desactivarCita(Long id);
}