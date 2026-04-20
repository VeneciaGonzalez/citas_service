package com.veneciagonzalez.entrega3.salud.cita_service.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.veneciagonzalez.entrega3.salud.cita_service.dto.CitaMedicaRequestDTO;
import com.veneciagonzalez.entrega3.salud.cita_service.dto.CitaMedicaResponseDTO;

import com.veneciagonzalez.entrega3.salud.cita_service.service.CitaService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    // GET alls --> http://localhost:8081/citas
    @GetMapping
    public ResponseEntity<List<CitaMedicaResponseDTO>> obtenerTodas() {
        log.info("GET /citas - Obteniendo todas las citas");
        return ResponseEntity.ok(citaService.obtenerTodas());
    }

    // GET ID --> http://localhost:8081/citas/1
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        log.info("GET /citas/{} - Buscando cita por ID", id);
        CitaMedicaResponseDTO cita = citaService.obtenerPorId(id);
        if (cita == null) {
            log.warn("Cita ID {} no encontrada", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cita);
    }

    // GET especialidad  --> http://localhost:8081/citas/especialidad?nombre=Pediatria
    @GetMapping("/especialidad")
    public ResponseEntity<List<CitaMedicaResponseDTO>> buscarPorEspecialidad(@RequestParam String nombre) {
        log.info("GET /citas/especialidad - Buscando por especialidad: {}", nombre);
        return ResponseEntity.ok(citaService.buscarPorEspecialidad(nombre));
    }

    // GET especialidad parcial --> http://localhost:8081/citas/especialidad-parcial?nombre=ped
    @GetMapping("/especialidad-parcial")
    public ResponseEntity<List<CitaMedicaResponseDTO>> buscarPorEspecialidadParcial(@RequestParam String nombre) {
        log.info("GET /citas/especialidad - Buscando especialidad: {}", nombre);
        return ResponseEntity.ok(citaService.buscarPorEspecialidadParcial(nombre));
    }

    // GET estado --> http://localhost:8081/citas/estado?estadoCita=Cancelada
    @GetMapping("/estado")
    public ResponseEntity<List<CitaMedicaResponseDTO>> buscarPorEstado(@RequestParam String estadoCita) {
        log.info("GET /citas/estado - Buscando por estado: {}", estadoCita);
        return ResponseEntity.ok(citaService.buscarPorEstado(estadoCita));
    }

    // GET citas activas --> http://localhost:8081/citas/activas
    @GetMapping("/activas")
    public ResponseEntity<List<CitaMedicaResponseDTO>> obtenerActivas() {
        log.info("GET /citas/activas - Obteniendo citas activas");
        return ResponseEntity.ok(citaService.obtenerActivas());
    }

    // GET activas ordenadas por fecha --> http://localhost:8081/citas/activas-ordenadas
    @GetMapping("/activas-ordenadas")
    public ResponseEntity<List<CitaMedicaResponseDTO>> obtenerActivasOrdenadasPorFecha() {
        log.info("GET /citas/activas-ordenadas - Obteniendo citas activas ordenadas por fecha");
        return ResponseEntity.ok(citaService.obtenerActivasOrdenadasPorFecha());
    }

    // GET especialidad y estado --> http://localhost:8081/citas/filtro?especialidad=Pediatria&estadoCita=Agendada
    @GetMapping("/filtro")
    public ResponseEntity<List<CitaMedicaResponseDTO>> buscarPorEspecialidadYEstado(
            @RequestParam String especialidad,
            @RequestParam String estadoCita) {
        log.info("GET /citas/filtro - especialidad: {}, estado: {}", especialidad, estadoCita);
        return ResponseEntity.ok(citaService.buscarPorEspecialidadYEstado(especialidad, estadoCita));
    }

    // GET citas fecha --> http://localhost:8081/citas/fecha?desde=2026-05-01T00:00:00
    @GetMapping("/fecha")
    public ResponseEntity<List<CitaMedicaResponseDTO>> buscarPorFechaDesde(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde) {
        log.info("GET /citas/fecha - Buscando citas desde: {}", desde);
        return ResponseEntity.ok(citaService.buscarPorFechaDesde(desde));
    }

    // GET rango fechas --> http://localhost:8081/citas/fecha-rango?inicio=2026-05-01T00:00:00&fin=2026-08-01T00:00:00
    @GetMapping("/fecha-rango")
    public ResponseEntity<List<CitaMedicaResponseDTO>> buscarPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        log.info("GET /citas/fecha-rango - Entre {} y {}", inicio, fin);
        return ResponseEntity.ok(citaService.buscarPorRangoFechas(inicio, fin));
    }

    // GET nombre paciente --> http://localhost:8081/citas/paciente?nombre=Monica
    @GetMapping("/paciente")
    public ResponseEntity<List<CitaMedicaResponseDTO>> buscarPorNombrePaciente(@RequestParam String nombre) {
        log.info("GET /citas/paciente - Buscando por nombre: {}", nombre);
        return ResponseEntity.ok(citaService.buscarPorNombrePaciente(nombre));
    }

    // GET búsqueda complejo --> http://localhost:8081/citas/buscar-complejo?especialidad=derma
    @GetMapping("/buscar-complejo")
    public ResponseEntity<List<CitaMedicaResponseDTO>> buscarComplejo(@RequestParam String especialidad) {
        log.info("GET /citas/buscar-complejo - especialidad: {}", especialidad);
        return ResponseEntity.ok(citaService.buscarComplejo(especialidad));
    }

    // POST crear cita --> http://localhost:8081/citas
    @PostMapping
    public ResponseEntity<CitaMedicaResponseDTO> crearCita(@Valid @RequestBody CitaMedicaRequestDTO request) {
        log.info("POST /citas - Creando cita para: {}", request.getNombrePaciente());
        CitaMedicaResponseDTO creada = citaService.crearCita(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    // PUT actualizar cita --> http://localhost:8081/citas/1
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCita(@PathVariable Long id,
            @Valid @RequestBody CitaMedicaRequestDTO request) {
        log.info("PUT /citas/{} - Actualizando cita", id);
        CitaMedicaResponseDTO actualizada = citaService.actualizarCita(id, request);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizada);
    }

    // DELETE físico --> http://localhost:8081/citas/1
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCita(@PathVariable Long id) {
        log.info("DELETE /citas/{} - Eliminando cita", id);
        boolean eliminada = citaService.eliminarCita(id);
        if (!eliminada) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    // PUT desactivar (eliminado lógico) --> http://localhost:8081/citas/1/desactivar
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<?> desactivarCita(@PathVariable Long id) {
        log.info("PUT /citas/{}/desactivar - Desactivando cita", id);
        boolean desactivada = citaService.desactivarCita(id);
        if (!desactivada) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}