
package com.veneciagonzalez.entrega3.salud.cita_service.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.veneciagonzalez.entrega3.salud.cita_service.entity.CitaMedicaEntity;



// Configura entorno JPA de prueba con base de datos en memoria (H2)
// @DataJpaTest
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
// public class CitaRepositoryTest {


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@org.springframework.test.context.TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
public class CitaRepositoryTest {

    @Autowired
    private CitaMedicaRepository citaMedicaRepository;

    @Test
    @DisplayName("Debe guardar una cita y retornar con ID generado")
    public void guardarCitaTest() {
        // Arrange
        CitaMedicaEntity cita = new CitaMedicaEntity();
        cita.setNombrePaciente("Monica Mieres");
        cita.setEspecialidad("Pediatria");
        cita.setFechaCita(LocalDateTime.now().plusDays(1));
        cita.setEstadoCita("Agendada");
        cita.setActivo(1);

        // Act
        CitaMedicaEntity resultado = citaMedicaRepository.save(cita);

        // Assert
        assertNotNull(resultado.getId());
        assertEquals("Monica Mieres", resultado.getNombrePaciente());
    }

    @Test
    @DisplayName("Debe encontrar citas por especialidad")
    public void buscarPorEspecialidadTest() {
        // Arrange
        CitaMedicaEntity cita = new CitaMedicaEntity();
        cita.setNombrePaciente("Paula Mieres");
        cita.setEspecialidad("Odontologia");
        cita.setFechaCita(LocalDateTime.now().plusDays(2));
        cita.setEstadoCita("Agendada");
        cita.setActivo(1);
        citaMedicaRepository.save(cita);

        // Act
        List<CitaMedicaEntity> resultado = citaMedicaRepository.findByEspecialidad("Odontologia");

        // Assert
        assertNotNull(resultado);
        assertEquals("Odontologia", resultado.get(0).getEspecialidad());
    }
}