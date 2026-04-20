package com.veneciagonzalez.entrega3.salud.cita_service.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.veneciagonzalez.entrega3.salud.cita_service.entity.CitaMedicaEntity;

@Repository
public interface CitaMedicaRepository extends JpaRepository<CitaMedicaEntity, Long> {

    // Busca especialidad 
    List<CitaMedicaEntity> findByEspecialidad(String especialidad);

    // Busca especialidad parcial
    List<CitaMedicaEntity> findByEspecialidadContaining(String especialidad);

    // Busca estado de cita
    List<CitaMedicaEntity> findByEstadoCita(String estadoCita);

    // Solo citas activas
    List<CitaMedicaEntity> findByActivo(Integer activo);

    // Citas activas ordenadas por fecha
    List<CitaMedicaEntity> findByActivoOrderByFechaCitaAsc(Integer activo);

    // Especialidad y estado
    List<CitaMedicaEntity> findByEspecialidadAndEstadoCita(String especialidad, String estadoCita);

    // Citas después de una fecha
    List<CitaMedicaEntity> findByFechaCitaAfter(LocalDateTime fecha);

    // Citas entre fechas
    List<CitaMedicaEntity> findByFechaCitaBetween(LocalDateTime inicio, LocalDateTime fin);

    // Nombre paciente
    List<CitaMedicaEntity> findByNombrePacienteContaining(String nombre);

    // Activas por especialidad ordenadas por fecha
    List<CitaMedicaEntity> findByActivoAndEspecialidadContainingOrderByFechaCitaAsc(Integer activo, String especialidad);
}