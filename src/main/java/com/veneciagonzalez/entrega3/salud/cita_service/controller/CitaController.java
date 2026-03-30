package com.veneciagonzalez.entrega3.salud.cita_service.controller; // Cambié salud_service por cita_service

import com.veneciagonzalez.entrega3.salud.cita_service.model.CitaMedica;
import com.veneciagonzalez.entrega3.salud.cita_service.service.CitaService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/citas")

public class CitaController {

    //CitaService citaService = new CitaService();
    
    // Se impleneta Autowired para que genera las instancias, para que Spring devuelva el objeto CitaService listo 
    @Autowired  
    private CitaService citaService;


    
    //Se implenetan tres filtros 
    // http://localhost:8081/citas/1
    // http://localhost:8081/citas/estado?estadoCita =Cancelada
    // http://localhost:8081/citas/disponibilidad?especialidad=Pediatria&fecha=2026-03-30


    // GET
    // Devuelve todas las citas  -- http://localhost:8081/citas
    @GetMapping
    public List<CitaMedica> listarTodas() {
        return citaService.consultaListaCitas();
    }

    
    // GET
    // Filtra por ID -->  http://localhost:8081/citas/1
    @GetMapping("/{id}") 
    public CitaMedica consultaCitaId(@PathVariable Long id) {
        return citaService.consultaCitasId(id);
    } 


    // Se verifica si hay citas disponibles para la fecha 2026-03-30 --> http://localhost:8081/citas/disponibilidad?especialidad=Pediatria&fecha=2026-04-30
    @GetMapping("/disponibilidad")
    public ResponseEntity<List<CitaMedica>> consultarDisponibilidad(@RequestParam String especialidad, @RequestParam String fecha) {
        
        return ResponseEntity.ok(citaService.consultaCitasDisponibles(especialidad, fecha));

    }


    // GET
    // Se verifica si hay citas disponibles por estado --> http://localhost:8081/citas/estado?estadoCita=Cancelada
    //  {
    //     "estadoCita": "Cancelada"
    //  }
    @GetMapping("/estado")
    public ResponseEntity<List<CitaMedica>> consultaEstadoCita(@RequestParam String estadoCita) {        
        return ResponseEntity.ok(citaService.consultaEstadoCita(estadoCita));        
    }


    // ============================
    // POST
    // Se genera una nueva cita -->  http://localhost:8081/citas
    // {
    //     "id": 4,
    //     "nombrePaciente": "Miranda Carolina",
    //     "especialidad": "General",
    //     "fecha": "2026-10-05",
    //     "hora": "15:30",
    //     "estadoCita": "Agendada"
    // }
    @PostMapping
    public ResponseEntity<CitaMedica> crearCitaMedica(@Valid @RequestBody CitaMedica nuevaCitaMedica) {
        
        //nuevaCitaMedica.setEstadoCita("Programada");
        CitaMedica citaGuardada = citaService.agregarNuevaCitaMedica(nuevaCitaMedica);

        return ResponseEntity.status(HttpStatus.CREATED).body(citaGuardada);
    }

    // PUT 
    // Se cambia estado cita filtro Id  --> http://localhost:8081/citas/1/cancelar
    // http://localhost:8081/citas/1/cancelar
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarCita(@PathVariable Long id) {
        
        CitaMedica cita = citaService.consultaCitasId(id);//Primero se obtiene la cita filtrada
        if (cita != null) { //se valida cita obtenida
            cita.setEstadoCita("Cancelada"); //se setao el nuevo estado 
            return ResponseEntity.ok(cita);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Cita no encontrada"));
    }


}