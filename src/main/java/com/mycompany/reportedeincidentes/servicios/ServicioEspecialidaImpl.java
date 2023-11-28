package com.mycompany.reportedeincidentes.servicios;

import com.mycompany.reportedeincidentes.modelo.Cliente;
import com.mycompany.reportedeincidentes.modelo.Especialidad;
import com.mycompany.reportedeincidentes.modelo.Tecnico;
import com.mycompany.reportedeincidentes.repositorio.EspecialidadJpaController;
import com.mycompany.reportedeincidentes.repositorio.exceptions.NonexistentEntityException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicioEspecialidaImpl implements ServicioEspecialidad {

    EspecialidadJpaController especialidadJpa = new EspecialidadJpaController();

    @Override
    public void crearEspecialidad(Especialidad especialidad) {
        especialidadJpa.create(especialidad);

    }

    @Override
    public void eliminarEspecialidad(Long idEspecialidad) {

        try {
            especialidadJpa.destroy(idEspecialidad);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ServicioEspecialidaImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void editarEspecialidad(Especialidad especialidad) {

        try {
            especialidadJpa.edit(especialidad);
        } catch (Exception ex) {
            Logger.getLogger(ServicioEspecialidaImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public Especialidad buscarEspecialidad(Long idEspecialidad) {
        return especialidadJpa.findEspecialidad(idEspecialidad);

    }

    @Override
    public Set<Especialidad> listarEspecialidades() {
        List<Especialidad> lista = especialidadJpa.findEspecialidadEntities();
        Set<Especialidad> listaEspecialidades = new HashSet<Especialidad>(lista);
        return listaEspecialidades;
    }
    
    @Override
    public Set<Especialidad> obtenerEspecialidadesPorTecnico(Tecnico tecnico){
        return tecnico.getEspecialidades();
        
    }

}
