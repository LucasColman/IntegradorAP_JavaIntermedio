
package com.mycompany.reportedeincidentes.servicios;

import com.mycompany.reportedeincidentes.modelo.Cliente;
import com.mycompany.reportedeincidentes.modelo.Especialidad;
import com.mycompany.reportedeincidentes.repositorio.EspecialidadJpaController;
import com.mycompany.reportedeincidentes.repositorio.exceptions.NonexistentEntityException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServicioEspecialidad {
    EspecialidadJpaController especialidadJpa  = new EspecialidadJpaController();
    
    
    public void crearEspecialidad(Especialidad especialidad){
        especialidadJpa.create(especialidad);
        
    }
    public void eliminarEspecialidad(Long idEspecialidad){
        try {
            especialidadJpa.destroy(idEspecialidad);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ServicioCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void editarEspecialidad(Especialidad especialidad){
        try {
            especialidadJpa.edit(especialidad);
        } catch (Exception ex) {
            Logger.getLogger(ServicioCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public Especialidad buscarEspecialidad(Long idEspecialidad){
        return especialidadJpa.findEspecialidad(idEspecialidad);
        
    }
    
    public Set<Especialidad> listarEspecialidades(){
        List<Especialidad> lista = especialidadJpa.findEspecialidadEntities();
        Set<Especialidad> listaEspecialidades = new HashSet<Especialidad> (lista);
        return listaEspecialidades;
    }
    
    
    
}
