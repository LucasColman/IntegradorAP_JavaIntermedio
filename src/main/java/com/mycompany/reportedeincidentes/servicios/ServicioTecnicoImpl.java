package com.mycompany.reportedeincidentes.servicios;

import com.mycompany.reportedeincidentes.Enums.Estado;
import com.mycompany.reportedeincidentes.modelo.Especialidad;
import com.mycompany.reportedeincidentes.modelo.Incidencia;
import com.mycompany.reportedeincidentes.modelo.Tecnico;
import com.mycompany.reportedeincidentes.repositorio.TecnicoJpaController;
import com.mycompany.reportedeincidentes.repositorio.exceptions.NonexistentEntityException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicioTecnicoImpl implements ServicioTecnico {

    private final TecnicoJpaController tecnicoJpa = new TecnicoJpaController();
    public ServicioIncidenciaImpl servicioIncidenciaImpl = new ServicioIncidenciaImpl();

    @Override
    public void crearTecnico(Tecnico tecnico) {
        tecnicoJpa.create(tecnico);

    }

    @Override
    public void eliminarTecnico(Long idTecnico) {

        try {
            tecnicoJpa.destroy(idTecnico);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ServicioTecnicoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void editarTecnico(Tecnico tecnico) {

        try {
            tecnicoJpa.edit(tecnico);
        } catch (Exception ex) {
            Logger.getLogger(ServicioTecnicoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public Tecnico buscarTecnico(Long idTecnico) {
        return tecnicoJpa.findTecnico(idTecnico);

    }

    @Override
    public Set<Tecnico> listarTecnicos() {
        List<Tecnico> lista = tecnicoJpa.findTecnicoEntities();
        Set<Tecnico> listaTecnicos = new HashSet<>(lista);
        return listaTecnicos;
    }

    //*********************************************************************************************************************
    @Override
    public boolean puedeResolverIncidencia(Tecnico tecnico, Incidencia incidencia) {

        for (Especialidad especialidadTecnico : tecnico.getEspecialidades()) {
            for (Especialidad especialidadIncidencia : incidencia.getEspecialidades()) {
                if (especialidadTecnico.getNombreEspecialidad().equals(especialidadIncidencia.getNombreEspecialidad())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void resolverIncidencia(Incidencia incidencia) {
        incidencia.setFechaResolucion(LocalDateTime.now());
        incidencia.setEstado(Estado.CERRADO);

    }

    //a. Quién fue el técnico con más incidentes resueltos en los últimos N días
    @Override
    public Tecnico obtenerTecnicoConMasIncidenciasResueltas(int ultimosDias) {
        Tecnico tecnicoConMasIncidentes = null;
        int maxIncidencias = 0;

        Map<Tecnico, Integer> incidenciasPorTecnico = new HashMap<>();

        for (Incidencia incidencia : servicioIncidenciaImpl.obtenerIncidenciasResueltasEnNDias(ultimosDias)) {
            Tecnico tecnico = incidencia.getTecnico();

            incidenciasPorTecnico.put(tecnico, incidenciasPorTecnico.getOrDefault(tecnico, 0) + 1);

        }

        for (Map.Entry<Tecnico, Integer> entry : incidenciasPorTecnico.entrySet()) {
            if (entry.getValue() > maxIncidencias) {
                tecnicoConMasIncidentes = entry.getKey();
                maxIncidencias = entry.getValue();
            }
        }
        return tecnicoConMasIncidentes;

    }

    //b. Quién fue el técnico con más incidentes resueltos de una determinada especialidad en los últimos N días
    public Tecnico obtenerTecnicoConMasIncidenciasResueltas(int ultimosDias, String especialidad) throws Exception {
        Tecnico tecnicoConMasIncidentesPorEspecialidad = null;
        int maxIncidencias = 0;

        // Crear un mapa que asocie cada técnico con el número de incidencias que resolvió y que tienen la especialidad deseada
        try {
            Set<Incidencia> incidenciasResueltasPorEspecialidad = servicioIncidenciaImpl.obtenerIncidenciasResueltasPorEspecialidad(ultimosDias, especialidad);
            Map<Tecnico, Integer> incidenciasPorTecnico = new HashMap<>();
            for (Incidencia incidencia : incidenciasResueltasPorEspecialidad) {
                Tecnico tecnico = incidencia.getTecnico();
                incidenciasPorTecnico.put(tecnico, incidenciasPorTecnico.getOrDefault(tecnico, 0) + 1);
            }

            // Encontrar el técnico con el mayor número de incidencias resueltas
            for (Map.Entry<Tecnico, Integer> entry : incidenciasPorTecnico.entrySet()) {
                if (entry.getValue() > maxIncidencias) {
                    tecnicoConMasIncidentesPorEspecialidad = entry.getKey();
                    maxIncidencias = entry.getValue();
                }
            }
            return tecnicoConMasIncidentesPorEspecialidad;
        } catch (NullPointerException e) {
            throw new Exception("No se encontró ningún técnico que cumpla con las condiciones.");
        }

    }

    public Duration calcularTiempoPromedioResolucion() {
        long tiempoTotalResolucion = 0;

        for (Incidencia incidencia : servicioIncidenciaImpl.listarIncidencias() ) {
            if (incidencia.getFechaApertura() != null && incidencia.getFechaResolucion() != null) {
                tiempoTotalResolucion += Duration.between(incidencia.getFechaApertura(), incidencia.getFechaResolucion()).toMillis();
                return Duration.ofMillis(tiempoTotalResolucion / servicioIncidenciaImpl.listarIncidencias().size());
            } else {
                return Duration.ZERO;
            }
        }
        return Duration.ZERO;
    }

    //c. Quién fue el técnico que más rápido resolvió los incidentes
    @Override
    public Tecnico obtenerTecnicoMasRapidoEnResolverIncidencias() {
        Tecnico tecnicoMasRapido = null;
        Duration menorTiempoPromedio = Duration.ofDays(365);
        for (Tecnico tecnico : listarTecnicos()) {
            Duration tiempoPromedio = calcularTiempoPromedioResolucion();
            if (tiempoPromedio.compareTo(menorTiempoPromedio) < 0) {
                menorTiempoPromedio = tiempoPromedio;
                tecnicoMasRapido = tecnico;
            }
        }
        return tecnicoMasRapido;

    }

}
