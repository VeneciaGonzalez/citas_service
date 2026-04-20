package com.veneciagonzalez.entrega3.salud.cita_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.veneciagonzalez.entrega3.salud.cita_service.dto.CitaMedicaRequestDTO;
import com.veneciagonzalez.entrega3.salud.cita_service.dto.CitaMedicaResponseDTO;
import com.veneciagonzalez.entrega3.salud.cita_service.entity.CitaMedicaEntity;
import com.veneciagonzalez.entrega3.salud.cita_service.repository.CitaMedicaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CitaServiceImpl implements CitaService {

    private final CitaMedicaRepository citaMedicaRepository;

    public CitaServiceImpl(CitaMedicaRepository citaMedicaRepository) {
        this.citaMedicaRepository = citaMedicaRepository;
    }

    @Override
    public List<CitaMedicaResponseDTO> obtenerTodas() {
        log.info("Obteniendo todas las citas médicas");
        return citaMedicaRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    public CitaMedicaResponseDTO obtenerPorId(Long id) {
        log.info("Buscando cita con ID: {}", id);
        Optional<CitaMedicaEntity> cita = citaMedicaRepository.findById(id);
        return cita.map(this::toDTO).orElse(null);
    }

    @Override
    public List<CitaMedicaResponseDTO> buscarPorEspecialidad(String especialidad) {
        log.info("Buscando citas por especialidad: {}", especialidad);
        return citaMedicaRepository.findByEspecialidad(especialidad).stream().map(this::toDTO).toList();
    }

    @Override
    public List<CitaMedicaResponseDTO> buscarPorEspecialidadParcial(String especialidad) {
        log.info("Buscando citas por especialidad parcial: {}", especialidad);
        return citaMedicaRepository.findByEspecialidadContaining(especialidad).stream().map(this::toDTO).toList();
    }

    @Override
    public List<CitaMedicaResponseDTO> buscarPorEstado(String estadoCita) {
        log.info("Buscando citas por estado: {}", estadoCita);
        return citaMedicaRepository.findByEstadoCita(estadoCita).stream().map(this::toDTO).toList();
    }

    @Override
    public List<CitaMedicaResponseDTO> obtenerActivas() {
        log.info("Obteniendo citas activas");
        return citaMedicaRepository.findByActivo(1).stream().map(this::toDTO).toList();
    }

    @Override
    public List<CitaMedicaResponseDTO> obtenerActivasOrdenadasPorFecha() {
        log.info("Obteniendo citas activas ordenadas por fecha");
        return citaMedicaRepository.findByActivoOrderByFechaCitaAsc(1).stream().map(this::toDTO).toList();
    }

    @Override
    public List<CitaMedicaResponseDTO> buscarPorEspecialidadYEstado(String especialidad, String estadoCita) {
        log.info("Buscando citas por especialidad: {} y estado: {}", especialidad, estadoCita);
        return citaMedicaRepository.findByEspecialidadAndEstadoCita(especialidad, estadoCita)
                .stream().map(this::toDTO).toList();
    }

    @Override
    public List<CitaMedicaResponseDTO> buscarPorFechaDesde(LocalDateTime fecha) {
        log.info("Buscando citas desde la fecha: {}", fecha);
        return citaMedicaRepository.findByFechaCitaAfter(fecha).stream().map(this::toDTO).toList();
    }

    @Override
    public List<CitaMedicaResponseDTO> buscarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        log.info("Buscando citas entre {} y {}", inicio, fin);
        return citaMedicaRepository.findByFechaCitaBetween(inicio, fin).stream().map(this::toDTO).toList();
    }

    @Override
    public List<CitaMedicaResponseDTO> buscarPorNombrePaciente(String nombre) {
        log.info("Buscando citas por nombre de paciente: {}", nombre);
        return citaMedicaRepository.findByNombrePacienteContaining(nombre).stream().map(this::toDTO).toList();
    }

    @Override
    public List<CitaMedicaResponseDTO> buscarComplejo(String especialidad) {
        log.info("Búsqueda compleja: activas por especialidad: {}", especialidad);
        return citaMedicaRepository
                .findByActivoAndEspecialidadContainingOrderByFechaCitaAsc(1, especialidad)
                .stream().map(this::toDTO).toList();
    }

    @Override
    public CitaMedicaResponseDTO crearCita(CitaMedicaRequestDTO request) {
        log.info("Creando nueva cita para paciente: {}", request.getNombrePaciente());
        CitaMedicaEntity entity = new CitaMedicaEntity();
        entity.setNombrePaciente(request.getNombrePaciente());
        entity.setEspecialidad(request.getEspecialidad());
        entity.setFechaCita(request.getFechaCita());
        entity.setEstadoCita(request.getEstadoCita());
        entity.setActivo(1);
        CitaMedicaEntity guardada = citaMedicaRepository.save(entity);
        return toDTO(guardada);
    }

    @Override
    public CitaMedicaResponseDTO actualizarCita(Long id, CitaMedicaRequestDTO request) {
        log.info("Actualizando cita con ID: {}", id);
        Optional<CitaMedicaEntity> optional = citaMedicaRepository.findById(id);
        if (optional.isEmpty()) {
            log.warn("Cita con ID {} no encontrada para actualizar", id);
            return null;
        }
        CitaMedicaEntity entity = optional.get();
        entity.setNombrePaciente(request.getNombrePaciente());
        entity.setEspecialidad(request.getEspecialidad());
        entity.setFechaCita(request.getFechaCita());
        entity.setEstadoCita(request.getEstadoCita());
        return toDTO(citaMedicaRepository.save(entity));
    }

    @Override
    public boolean eliminarCita(Long id) {
        log.info("Eliminando cita con ID: {}", id);
        if (citaMedicaRepository.existsById(id)) {
            citaMedicaRepository.deleteById(id);
            return true;
        }
        log.warn("Cita con ID {} no encontrada para eliminar", id);
        return false;
    }

    @Override
    public boolean desactivarCita(Long id) {
        log.info("Desactivando cita con ID: {}", id);
        Optional<CitaMedicaEntity> optional = citaMedicaRepository.findById(id);
        if (optional.isEmpty()) {
            log.warn("Cita con ID {} no encontrada para desactivar", id);
            return false;
        }
        CitaMedicaEntity entity = optional.get();
        entity.setActivo(0);
        citaMedicaRepository.save(entity);
        return true;
    }

    // Método privado para convertir Entity → ResponseDTO
    private CitaMedicaResponseDTO toDTO(CitaMedicaEntity entity) {
        return new CitaMedicaResponseDTO(
                entity.getId(),
                entity.getNombrePaciente(),
                entity.getEspecialidad(),
                entity.getFechaCita(),
                entity.getEstadoCita(),
                entity.getActivo()
        );
    }
}