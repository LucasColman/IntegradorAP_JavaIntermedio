
package com.mycompany.reportedeincidentes.servicios;

import com.mycompany.reportedeincidentes.modelo.TipoIncidencia;
import java.util.Set;


public interface ServicioTipoIncidencia {

    public void crearTipoIncidencia(TipoIncidencia tipoIncidencia);

    public void eliminarTipoIncidencia(Long idTipoIncidencia);

    public void editarTipoIncidencia(TipoIncidencia tipoIncidencia);

    public TipoIncidencia buscarTipoIncidencia(Long idTipoIncidencia);

    public Set<TipoIncidencia> listarTipoIncidencia();
    
}
