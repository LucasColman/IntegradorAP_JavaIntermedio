
package com.mycompany.reportedeincidentes.servicios;


import com.mycompany.reportedeincidentes.modelo.TipoIncidencia;
import com.mycompany.reportedeincidentes.repositorio.TipoIncidenciaJpaController;
import com.mycompany.reportedeincidentes.repositorio.exceptions.NonexistentEntityException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServicioTipoIncidencia {
    
    TipoIncidenciaJpaController tipoIncidenciaJpa = new TipoIncidenciaJpaController();
    
    
    public void crearTipoIncidencia(TipoIncidencia tipoIncidencia){
        tipoIncidenciaJpa.create(tipoIncidencia);
        
    }
    public void eliminarTipoIncidencia(Long idTipoIncidencia){
        try {
            tipoIncidenciaJpa.destroy(idTipoIncidencia);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ServicioTecnico.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void editarTipoIncidencia(TipoIncidencia tipoIncidencia){
       
        try {
            tipoIncidenciaJpa.edit(tipoIncidencia);
        } catch (Exception ex) {
            Logger.getLogger(ServicioTecnico.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
    
    public TipoIncidencia buscarTipoIncidencia(Long idTipoIncidencia){
        return tipoIncidenciaJpa.findTipoIncidencia(idTipoIncidencia);
        
    }
    
    public Set<TipoIncidencia> listarTipoIncidencia(){
        List<TipoIncidencia> lista = tipoIncidenciaJpa.findTipoIncidenciaEntities();
        Set<TipoIncidencia> listaTipoIncidencias = new HashSet<TipoIncidencia> (lista);
        return listaTipoIncidencias;
    }
    
    
}
