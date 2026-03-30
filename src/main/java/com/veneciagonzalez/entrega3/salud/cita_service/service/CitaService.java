package com.veneciagonzalez.entrega3.salud.cita_service.service;

import com.veneciagonzalez.entrega3.salud.cita_service.model.CitaMedica;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitaService {
    
    private List<CitaMedica> listaCitas = new ArrayList<>();

    public CitaService() {
        listaCitas.add(new CitaMedica(1L, "Mónica Mieres",       "Pediatria",      "2026-04-30", "09:00", "Agendada"));
        listaCitas.add(new CitaMedica(2L, "Paula Mieres",        "Odontologia",    "2026-05-30", "09:00", "Agendada"));
        listaCitas.add(new CitaMedica(3L, "Erik Lazcano",        "Dermatología",   "2026-06-31", "09:00", "Pendiente"));
        listaCitas.add(new CitaMedica(3L, "Sebastian Schneider", "Nutrición",      "2026-07-31", "09:00", "Agendada"));
        listaCitas.add(new CitaMedica(4L, "Jose Jadad",          "Dermatología",   "2026-08-31", "09:00", "Cancelada"));
        listaCitas.add(new CitaMedica(4L, "Jorge Padraza",       "Nutrición",      "2026-09-31", "09:00", "Confirmada"));

    }

    //Devuelve la lista de citas 
    public List<CitaMedica> consultaListaCitas() { return listaCitas; }


    //Devuelve cita por Id
    public CitaMedica consultaCitasId(Long id) {
        return listaCitas.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }


    //Devuelve cita disponible
    public List<CitaMedica> consultaCitasDisponibles(String especialidad, String fecha) {
        return listaCitas.stream()
                .filter(c -> c.getEspecialidad().equalsIgnoreCase(especialidad) && c.getFecha().equals(fecha))
                .collect(Collectors.toList());
    }


    //Devuelve estadoCita 
    public List<CitaMedica> consultaEstadoCita(String estadoCita) {
        return listaCitas.stream()
                .filter(c -> c.getEstadoCita().equalsIgnoreCase(estadoCita))
                .collect(Collectors.toList());
    }


    //Agrega nueva cita
    public CitaMedica agregarNuevaCitaMedica(CitaMedica nuevaCitaMedica) {
        listaCitas.add(nuevaCitaMedica);
        return nuevaCitaMedica;
    }

    
}