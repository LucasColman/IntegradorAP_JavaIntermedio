
package com.mycompany.reportedeincidentes.servicios;

import com.mycompany.reportedeincidentes.modelo.Incidencia;
import com.mycompany.reportedeincidentes.repositorio.IncidenciaJpaController;
import com.mycompany.reportedeincidentes.repositorio.exceptions.NonexistentEntityException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServicioIncidencia {
    
    IncidenciaJpaController incidenciaJpa =  new IncidenciaJpaController();
    
    
    public void crearIncidencia(Incidencia incidencia){
        incidenciaJpa.create(incidencia);
        
    }
    public void eliminarIncidencia(Long idIncidencia){
       
        try {
            incidenciaJpa.destroy(idIncidencia);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ServicioIncidencia.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public void editarIncidencia(Incidencia incidencia){
        try {
            incidenciaJpa.edit(incidencia);
        } catch (Exception ex) {
            Logger.getLogger(ServicioCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public Incidencia buscarIncidencia(Long idIncidencia){
        return incidenciaJpa.findIncidencia(idIncidencia);
        
    }
    
    public Set<Incidencia> listarIncidencias(){
        List<Incidencia> lista = incidenciaJpa.findIncidenciaEntities();
        Set<Incidencia> listaIncidencias = new HashSet<Incidencia> (lista);
        return listaIncidencias;
    }
    
}
