package com.mycompany.reportedeincidentes.servicios;

import com.mycompany.reportedeincidentes.modelo.Incidencia;
import com.mycompany.reportedeincidentes.modelo.Tecnico;
import java.util.Set;

public interface ServicioTecnico {

    public void crearTecnico(Tecnico tecnico);

    public void eliminarTecnico(Long idTecnico);

    public void editarTecnico(Tecnico tecnico);

    public Tecnico buscarTecnico(Long idTecnico);

    public Set<Tecnico> listarTecnicos();

    public boolean puedeResolverIncidencia(Tecnico tecnico, Incidencia incidencia);

    public void resolverIncidencia(Incidencia incidencia);

    public Tecnico obtenerTecnicoConMasIncidenciasResueltas(int ultimosDias);

    public Tecnico obtenerTecnicoMasRapidoEnResolverIncidencias();

}
