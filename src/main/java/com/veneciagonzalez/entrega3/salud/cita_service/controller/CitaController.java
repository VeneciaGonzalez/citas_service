package com.veneciagonzalez.entrega3.salud.cita_service.controller;

//import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.veneciagonzalez.entrega3.salud.cita_service.dto.CitaMedicaRequestDTO;
import com.veneciagonzalez.entrega3.salud.cita_service.dto.CitaMedicaResponseDTO;
import com.veneciagonzalez.entrega3.salud.cita_service.exception.CitaNotFoundException;
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

    // GET todas --> http://localhost:8081/citas
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<CitaMedicaResponseDTO>>> obtenerTodas() {
        log.info("GET /citas - Obteniendo todas las citas");

        List<EntityModel<CitaMedicaResponseDTO>> citas = citaService.obtenerTodas()
                .stream()
                .map(cita -> EntityModel.of(cita,
                        linkTo(methodOn(this.getClass()).obtenerPorId(cita.getId())).withSelfRel(),
                        linkTo(methodOn(this.getClass()).obtenerTodas()).withRel("all-citas")))
                .toList();

        CollectionModel<EntityModel<CitaMedicaResponseDTO>> recursos =
                CollectionModel.of(citas,
                        linkTo(methodOn(this.getClass()).obtenerTodas()).withSelfRel());

        return ResponseEntity.ok(recursos);
    }

    // GET por ID --> http://localhost:8081/citas/1
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CitaMedicaResponseDTO>> obtenerPorId(@PathVariable Long id) {
        log.info("GET /citas/{} - Buscando cita por ID", id);

        CitaMedicaResponseDTO cita = citaService.obtenerPorId(id);
        if (cita == null) {
            throw new CitaNotFoundException("Cita no encontrada con ID: " + id);
        }

        EntityModel<CitaMedicaResponseDTO> recurso = EntityModel.of(cita,
                linkTo(methodOn(this.getClass()).obtenerPorId(id)).withSelfRel(),
                linkTo(methodOn(this.getClass()).obtenerTodas()).withRel("all-citas"));

        return ResponseEntity.ok(recurso);
    }

    // POST crear --> http://localhost:8081/citas
    @PostMapping
    public ResponseEntity<EntityModel<CitaMedicaResponseDTO>> crearCita(
            @Valid @RequestBody CitaMedicaRequestDTO request) {
        log.info("POST /citas - Creando cita para: {}", request.getNombrePaciente());

        CitaMedicaResponseDTO creada = citaService.crearCita(request);

        EntityModel<CitaMedicaResponseDTO> recurso = EntityModel.of(creada,
                linkTo(methodOn(this.getClass()).obtenerPorId(creada.getId())).withSelfRel(),
                linkTo(methodOn(this.getClass()).obtenerTodas()).withRel("all-citas"));

        return ResponseEntity.status(HttpStatus.CREATED).body(recurso);
    }

    // PUT actualizar --> http://localhost:8081/citas/1
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<CitaMedicaResponseDTO>> actualizarCita(
            @PathVariable Long id,
            @Valid @RequestBody CitaMedicaRequestDTO request) {
        log.info("PUT /citas/{} - Actualizando cita", id);

        CitaMedicaResponseDTO actualizada = citaService.actualizarCita(id, request);
        if (actualizada == null) {
            throw new CitaNotFoundException("Cita no encontrada con ID: " + id);
        }

        EntityModel<CitaMedicaResponseDTO> recurso = EntityModel.of(actualizada,
                linkTo(methodOn(this.getClass()).obtenerPorId(id)).withSelfRel(),
                linkTo(methodOn(this.getClass()).obtenerTodas()).withRel("all-citas"));

        return ResponseEntity.ok(recurso);
    }

    // DELETE físico --> http://localhost:8081/citas/1
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCita(@PathVariable Long id) {
        log.info("DELETE /citas/{} - Eliminando cita", id);
        boolean eliminada = citaService.eliminarCita(id);
        if (!eliminada) {
            throw new CitaNotFoundException("Cita no encontrada con ID: " + id);
        }
        return ResponseEntity.noContent().build();
    }

    // PUT desactivar --> http://localhost:8081/citas/1/desactivar
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<?> desactivarCita(@PathVariable Long id) {
        log.info("PUT /citas/{}/desactivar - Desactivando cita", id);
        boolean desactivada = citaService.desactivarCita(id);
        if (!desactivada) {
            throw new CitaNotFoundException("Cita no encontrada con ID: " + id);
        }
        return ResponseEntity.noContent().build();
    }

    // GET por especialidad --> http://localhost:8081/citas/especialidad?nombre=Pediatria
    @GetMapping("/especialidad")
    public ResponseEntity<List<CitaMedicaResponseDTO>> buscarPorEspecialidad(@RequestParam String nombre) {
        log.info("GET /citas/especialidad - Buscando por especialidad: {}", nombre);
        return ResponseEntity.ok(citaService.buscarPorEspecialidad(nombre));
    }

    // GET por estado --> http://localhost:8081/citas/estado?estadoCita=Cancelada
    @GetMapping("/estado")
    public ResponseEntity<List<CitaMedicaResponseDTO>> buscarPorEstado(@RequestParam String estadoCita) {
        log.info("GET /citas/estado - Buscando por estado: {}", estadoCita);
        return ResponseEntity.ok(citaService.buscarPorEstado(estadoCita));
    }

    // GET activas --> http://localhost:8081/citas/activas
    @GetMapping("/activas")
    public ResponseEntity<List<CitaMedicaResponseDTO>> obtenerActivas() {
        log.info("GET /citas/activas - Obteniendo citas activas");
        return ResponseEntity.ok(citaService.obtenerActivas());
    }

    // GET por paciente --> http://localhost:8081/citas/paciente?nombre=Monica
    @GetMapping("/paciente")
    public ResponseEntity<List<CitaMedicaResponseDTO>> buscarPorNombrePaciente(@RequestParam String nombre) {
        log.info("GET /citas/paciente - Buscando por paciente: {}", nombre);
        return ResponseEntity.ok(citaService.buscarPorNombrePaciente(nombre));
    }




    
}