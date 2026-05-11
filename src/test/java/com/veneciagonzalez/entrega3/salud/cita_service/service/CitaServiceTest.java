package com.veneciagonzalez.entrega3.salud.cita_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.veneciagonzalez.entrega3.salud.cita_service.dto.CitaMedicaRequestDTO;
import com.veneciagonzalez.entrega3.salud.cita_service.dto.CitaMedicaResponseDTO;
import com.veneciagonzalez.entrega3.salud.cita_service.entity.CitaMedicaEntity;
import com.veneciagonzalez.entrega3.salud.cita_service.repository.CitaMedicaRepository;

// Extiende con Mockito para inyección automática de mocks
@ExtendWith(MockitoExtension.class)
public class CitaServiceTest {

    // Inyecta automáticamente los mocks en el servicio
    @InjectMocks
    private CitaServiceImpl citaServicio;

    // Crea un mock del repositorio para aislar el servicio
    @Mock
    private CitaMedicaRepository citaMedicaRepository;

    @Test
    @DisplayName("Debe crear una cita y retornar el DTO con los datos correctos")
    public void crearCitaTest() {
        // Arrange
        CitaMedicaRequestDTO request = new CitaMedicaRequestDTO(
                "Erik Lazcano", "Dermatologia",
                LocalDateTime.now().plusDays(1), "Pendiente");

        CitaMedicaEntity entityGuardada = new CitaMedicaEntity();
        entityGuardada.setId(1L);
        entityGuardada.setNombrePaciente("Erik Lazcano");
        entityGuardada.setEspecialidad("Dermatologia");
        entityGuardada.setFechaCita(request.getFechaCita());
        entityGuardada.setEstadoCita("Pendiente");
        entityGuardada.setActivo(1);

        // Configura el mock para devolver la entity cuando se llame save
        when(citaMedicaRepository.save(any())).thenReturn(entityGuardada);

        // Act
        CitaMedicaResponseDTO resultado = citaServicio.crearCita(request);

        // Assert
        assertNotNull(resultado);
        assertEquals("Erik Lazcano", resultado.getNombrePaciente());
        assertEquals("Dermatologia", resultado.getEspecialidad());
    }

    @Test
    @DisplayName("Debe retornar null cuando no existe la cita buscada por ID")
    public void obtenerPorIdInexistenteTest() {
        // Arrange
        when(citaMedicaRepository.findById(99L))
                .thenReturn(java.util.Optional.empty());

        // Act
        CitaMedicaResponseDTO resultado = citaServicio.obtenerPorId(99L);

        // Assert
        assertNull(resultado);
    }
}