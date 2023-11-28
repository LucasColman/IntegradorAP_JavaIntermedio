
package com.mycompany.reportedeincidentes.modelo;

import com.mycompany.reportedeincidentes.servicios.ServicioClienteImpl;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.mycompany.reportedeincidentes.servicios.ServicioIncidencia;
import com.mycompany.reportedeincidentes.servicios.ServicioIncidenciaImpl;


public class Controladora {
    ServicioClienteImpl servicioCliente = new ServicioClienteImpl();
    ServicioIncidenciaImpl servicioIncidencia = new ServicioIncidenciaImpl();
    
    public void agregarCliente(String apellido, String nombre, String razonSocial, String cuil){
        Cliente cliente = new Cliente();
        cliente.setApellido(apellido);
        cliente.setNombre(nombre);
        cliente.setRazonSocial(razonSocial);
        cliente.setCuil(cuil);
        
        servicioCliente.crearCliente(cliente);
        
    }
    
    public void agregarIncidencia(String descripcion, boolean estado, LocalDateTime fechaApertura, LocalDateTime fechaResolucion, Tecnico tecnico, Cliente cliente){
        Incidencia incidencia = new Incidencia();
        incidencia.setDescripcion(descripcion);
        incidencia.setEstado(null);
        incidencia.setFechaApertura(fechaApertura);
        incidencia.setFechaResolucion(fechaResolucion);
        incidencia.setTecnico(null);
        incidencia.setCliente(cliente);
        
    }
    
}
