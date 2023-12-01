package com.mycompany.reportedeincidentes.servicios;

import com.mycompany.reportedeincidentes.Enums.Estado;

import com.mycompany.reportedeincidentes.modelo.Incidencia;
import com.mycompany.reportedeincidentes.modelo.Tecnico;
import com.mycompany.reportedeincidentes.repositorio.IncidenciaJpaController;
import com.mycompany.reportedeincidentes.repositorio.exceptions.NonexistentEntityException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicioIncidenciaImpl implements ServicioIncidencia {

    IncidenciaJpaController incidenciaJpa = new IncidenciaJpaController();

    @Override
    public void crearIncidencia(Incidencia incidencia) {
        incidenciaJpa.create(incidencia);

    }

    @Override
    public void eliminarIncidencia(Long idIncidencia) {

        try {
            incidenciaJpa.destroy(idIncidencia);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ServicioIncidenciaImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void editarIncidencia(Incidencia incidencia) {

        try {
            incidenciaJpa.edit(incidencia);
        } catch (Exception ex) {
            Logger.getLogger(ServicioIncidenciaImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public Incidencia buscarIncidencia(Long idIncidencia) {
        return incidenciaJpa.findIncidencia(idIncidencia);

    }

    @Override
    public Set<Incidencia> listarIncidencias() {
        List<Incidencia> lista = incidenciaJpa.findIncidenciaEntities();
        Set<Incidencia> listaIncidencias = new HashSet<>(lista);
        return listaIncidencias;
    }

    @Override
    public long calcularIncidenciasResueltas(Tecnico tecnico, int dias) {
        LocalDateTime fechaLimite = LocalDateTime.now().minusDays(dias);
        return listarIncidencias().stream()
                .filter(incidencia -> incidencia.getEstado() == Estado.CERRADO)
                .filter(incidencia -> incidencia.getTecnico().equals(tecnico))
                .filter(incidencia -> incidencia.getFechaResolucion() != null)
                .filter(incidencia -> incidencia.getFechaResolucion().isAfter(fechaLimite))
                .count();
    }

    @Override
    public Set<Incidencia> obtenerIncidenciasResueltasEnNDias(int dias) {

        Set<Incidencia> incidenciasRecientes = new HashSet<>();
        try {
            for (Incidencia incidencia : listarIncidencias()) {
                if (incidencia.getFechaResolucion().isAfter(LocalDateTime.now().minusDays(dias))) {
                    incidenciasRecientes.add(incidencia);
                }
            }

        } catch (Exception e) {
        }

        return incidenciasRecientes;
    }

    @Override
    public Set<Incidencia> obtenerIncidenciasResueltasPorEspecialidad(int dias, String especialidad) {
        Set<Incidencia> incidenciasRecientesPorEspecialidad = new HashSet<>();

        // Crear una lista de incidencias resueltas en los últimos n días que tienen la especialidad deseada
        try {
            for (Incidencia incidencia : listarIncidencias()) {
                if (incidencia.getFechaResolucion().isAfter(LocalDateTime.now().minusDays(dias)) && incidencia.getEspecialidades().stream().anyMatch(e -> e.getNombreEspecialidad().equals(especialidad))) {
                    incidenciasRecientesPorEspecialidad.add(incidencia);
                }
            }
            return incidenciasRecientesPorEspecialidad;

        } catch (Exception e) {
        }
        return null;
    }
}
