
package com.mycompany.reportedeincidentes.servicios;


import com.mycompany.reportedeincidentes.modelo.TipoIncidencia;
import com.mycompany.reportedeincidentes.repositorio.TipoIncidenciaJpaController;
import com.mycompany.reportedeincidentes.repositorio.exceptions.NonexistentEntityException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServicioTipoIncidenciaImpl implements ServicioTipoIncidencia{
    
    TipoIncidenciaJpaController tipoIncidenciaJpa = new TipoIncidenciaJpaController();
    
    
    @Override
    public void crearTipoIncidencia(TipoIncidencia tipoIncidencia){
        tipoIncidenciaJpa.create(tipoIncidencia);
        
    }
    @Override
    public void eliminarTipoIncidencia(Long idTipoIncidencia){
        try {
            tipoIncidenciaJpa.destroy(idTipoIncidencia);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ServicioTecnicoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void editarTipoIncidencia(TipoIncidencia tipoIncidencia){
       
        try {
            tipoIncidenciaJpa.edit(tipoIncidencia);
        } catch (Exception ex) {
            Logger.getLogger(ServicioTecnicoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
    
    @Override
    public TipoIncidencia buscarTipoIncidencia(Long idTipoIncidencia){
        return tipoIncidenciaJpa.findTipoIncidencia(idTipoIncidencia);
        
    }
    
    @Override
    public Set<TipoIncidencia> listarTipoIncidencia(){
        List<TipoIncidencia> lista = tipoIncidenciaJpa.findTipoIncidenciaEntities();
        Set<TipoIncidencia> listaTipoIncidencias = new HashSet<TipoIncidencia> (lista);
        return listaTipoIncidencias;
    }
    
    
}
