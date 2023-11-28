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
            for (Especialidad especialidad : incidencia.getEspecialidades()) {
                for (Tecnico tecnico : especialidad.getTecnicos()) {
                    incidenciasPorTecnico.put(tecnico, incidenciasPorTecnico.getOrDefault(tecnico, 0) + 1);
                }
            }
        }
        for (Map.Entry<Tecnico, Integer> entry : incidenciasPorTecnico.entrySet()) {
            if (entry.getValue() > maxIncidencias) {
                tecnicoConMasIncidentes = entry.getKey();
                maxIncidencias = entry.getValue();
            }
        }
        return tecnicoConMasIncidentes;

    }

    /*
    Set<Tecnico> tecnicos = listarTecnicos();
        try {
            for (Tecnico tecnico : tecnicos) {
                long incidentesResueltos = servicioIncidenciaImpl.calcularIncidenciasResueltas(tecnico, ultimosDias);

                if (incidentesResueltos > maxIncidentes) {
                    maxIncidentes = incidentesResueltos;
                    tecnicoConMasIncidentes = tecnico;
                }
            }
            return tecnicoConMasIncidentes;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
     */
    //b. Quién fue el técnico con más incidentes resueltos de una determinada especialidad en los últimos N días
    public Tecnico obtenerTecnicoConMasIncidenciasResueltas(int ultimosDias, String especialidad) {
        Tecnico tecnicoConMasIncidentes = null;

        long maxIncidentes = 0;
        for (Tecnico tecnico : listarTecnicos()) {
            for (Especialidad especialidadTecnico : tecnico.getEspecialidades()) {
                long incidentesResueltosEnNDias = servicioIncidenciaImpl.calcularIncidenciasResueltas(tecnico, ultimosDias);

                if (incidentesResueltosEnNDias > maxIncidentes && especialidadTecnico.getNombreEspecialidad().equals(especialidad)) {
                    maxIncidentes = incidentesResueltosEnNDias;
                    tecnicoConMasIncidentes = tecnico;

                }

            }
        }
        return tecnicoConMasIncidentes;

    }

    //c. Quién fue el técnico que más rápido resolvió los incidentes
    @Override
    public Tecnico obtenerTecnicoMasRapidoEnResolverIncidencias() {
        Tecnico tecnicoMasRapido = null;
        Duration menorTiempoPromedio = Duration.ofDays(365);
        for (Tecnico tecnico : listarTecnicos()) {
            Duration tiempoPromedio = tecnico.calcularTiempoPromedioResolucion();

            if (tiempoPromedio.compareTo(menorTiempoPromedio) < 0) {
                menorTiempoPromedio = tiempoPromedio;
                tecnicoMasRapido = tecnico;
            }
        }
        return tecnicoMasRapido;

    }

}
