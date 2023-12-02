package com.mycompany.reportedeincidentes.servicios;

import com.mycompany.reportedeincidentes.modelo.Incidencia;
import java.util.Set;

public interface ServicioIncidencia {

    public void crearIncidencia(Incidencia incidencia);

    public void eliminarIncidencia(Long idIncidencia);

    public void editarIncidencia(Incidencia incidencia);

    public Incidencia buscarIncidencia(Long idIncidencia);

    public Set<Incidencia> listarIncidencias();

    public Set<Incidencia> obtenerIncidenciasResueltasEnNDias(int dias);
    
     public Set<Incidencia> obtenerIncidenciasResueltasPorEspecialidad(int dias, String especialidad);

}
