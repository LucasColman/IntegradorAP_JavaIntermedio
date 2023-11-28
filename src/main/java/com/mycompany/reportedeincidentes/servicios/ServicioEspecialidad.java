/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.reportedeincidentes.servicios;

import com.mycompany.reportedeincidentes.modelo.Especialidad;
import com.mycompany.reportedeincidentes.modelo.Tecnico;
import java.util.Set;

/**
 *
 * @author Lucas
 */
public interface ServicioEspecialidad {

    public void crearEspecialidad(Especialidad especialidad);
    public void eliminarEspecialidad(Long idEspecialidad);
    public void editarEspecialidad(Especialidad especialidad);
    public Especialidad buscarEspecialidad(Long idEspecialidad);
    public Set<Especialidad> listarEspecialidades();
    public Set<Especialidad> obtenerEspecialidadesPorTecnico(Tecnico tecnico);
     
     
    
}
