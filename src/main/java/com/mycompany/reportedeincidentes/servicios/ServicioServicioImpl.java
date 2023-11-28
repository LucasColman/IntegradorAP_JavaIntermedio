
package com.mycompany.reportedeincidentes.servicios;


import com.mycompany.reportedeincidentes.modelo.Servicio;
import com.mycompany.reportedeincidentes.repositorio.ServicioJpaController;
import com.mycompany.reportedeincidentes.repositorio.exceptions.NonexistentEntityException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServicioServicioImpl implements ServicioServicio{
    ServicioJpaController servicioJpa = new ServicioJpaController();
    
    @Override
    public void crearServicio(Servicio servicio){
        servicioJpa.create(servicio);
        
    }
    @Override
    public void eliminarServicio(Long idServicio){
        try {
            servicioJpa.destroy(idServicio);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ServicioServicioImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void editarServicio(Servicio servicio){
       
        try {
            servicioJpa.edit(servicio);
        } catch (Exception ex) {
            Logger.getLogger(ServicioServicioImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
    
    @Override
   public Servicio buscarServicio(Long idServicio){
        return servicioJpa.findServicio(idServicio);
        
    }
    
    @Override
    public Set<Servicio> listarServicios(){
        List<Servicio> lista = servicioJpa.findServicioEntities();
        Set<Servicio> listaServicios = new HashSet<> (lista);
        return listaServicios;
    }
    
}
