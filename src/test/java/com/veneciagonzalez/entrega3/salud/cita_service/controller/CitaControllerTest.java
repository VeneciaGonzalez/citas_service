package com.veneciagonzalez.entrega3.salud.cita_service.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.veneciagonzalez.entrega3.salud.cita_service.dto.CitaMedicaResponseDTO;
import com.veneciagonzalez.entrega3.salud.cita_service.service.CitaServiceImpl;

// Configura entorno de prueba solo para el controlador
@WebMvcTest(CitaController.class)
public class CitaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Mock del servicio para aislar el controlador
    // Usando @MockitoBean en lugar de @MockBean (deprecado desde Spring Boot 3.4)
    @MockitoBean
    private CitaServiceImpl citaServicioMock;

    @Test
    @DisplayName("Debe retornar lista de citas con status 200 y estructura HATEOAS")
    public void obtenerTodasTest() throws Exception {
        // Arrange
        CitaMedicaResponseDTO cita1 = new CitaMedicaResponseDTO(
                1L, "Monica Mieres", "Pediatria",
                LocalDateTime.now().plusDays(1), "Agendada", 1);

        CitaMedicaResponseDTO cita2 = new CitaMedicaResponseDTO(
                2L, "Erik Lazcano", "Dermatologia",
                LocalDateTime.now().plusDays(2), "Pendiente", 1);

        List<CitaMedicaResponseDTO> citas = Arrays.asList(cita1, cita2);
        when(citaServicioMock.obtenerTodas()).thenReturn(citas);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/citas"))
                .andExpect(status().isOk())
                // Verifica estructura HATEOAS
                .andExpect(jsonPath("$._embedded.citaMedicaResponseDTOList").exists())
                .andExpect(jsonPath("$._embedded.citaMedicaResponseDTOList[0].nombrePaciente").value("Monica Mieres"))
                .andExpect(jsonPath("$._embedded.citaMedicaResponseDTOList[1].nombrePaciente").value("Erik Lazcano"))
                .andExpect(jsonPath("$._links.self").exists());
    }

    @Test
    @DisplayName("Debe retornar 404 cuando la cita no existe")
    public void obtenerPorIdNoEncontradoTest() throws Exception {
        // Arrange
        when(citaServicioMock.obtenerPorId(99L)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/citas/99"))
                .andExpect(status().isNotFound());
    }
}