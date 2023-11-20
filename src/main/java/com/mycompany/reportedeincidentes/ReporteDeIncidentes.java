package com.mycompany.reportedeincidentes;

import com.mycompany.reportedeincidentes.modelo.Cliente;
import com.mycompany.reportedeincidentes.modelo.Especialidad;
import com.mycompany.reportedeincidentes.modelo.Incidencia;
import com.mycompany.reportedeincidentes.modelo.Servicio;
import com.mycompany.reportedeincidentes.modelo.Tecnico;
import com.mycompany.reportedeincidentes.modelo.TipoIncidencia;
import com.mycompany.reportedeincidentes.servicios.ServicioCliente;
import com.mycompany.reportedeincidentes.servicios.ServicioEspecialidad;
import com.mycompany.reportedeincidentes.servicios.ServicioIncidencia;
import com.mycompany.reportedeincidentes.servicios.ServicioReporte;
import com.mycompany.reportedeincidentes.servicios.ServicioServicio;
import com.mycompany.reportedeincidentes.servicios.ServicioTecnico;
import com.mycompany.reportedeincidentes.servicios.ServicioTipoIncidencia;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


/*Requerimientos 
    a. Quién fue el técnico con más incidentes resueltos en los últimos N días
    b. Quién fue el técnico con más incidentes resueltos de una determinada especialidad 
    en los últimos N días
    c. Quién fue el técnico que más rápido resolvió los incidentes */

public class ReporteDeIncidentes {

    public static void main(String[] args) {

        ServicioCliente servicioCliente = new ServicioCliente();
        ServicioTecnico servicioTecnico = new ServicioTecnico();
        ServicioEspecialidad servicioEspecialidad = new ServicioEspecialidad();
        ServicioServicio servicioServicio = new ServicioServicio();
        ServicioIncidencia servicioIncidencia = new ServicioIncidencia();
        ServicioTipoIncidencia servicioTipoIncidencia = new ServicioTipoIncidencia();
        ServicioReporte servicioReporte = new ServicioReporte();
      
        
        /*
        //Area Comercial
        Cliente cliente1 = new Cliente(1L, "Messi", "Leo", "nosequees", "4124123");

        //AREA RRHH
        Tecnico tecnico1 = new Tecnico(1L, "4334543", "Carlos", "Bielsa");

        //Admin
        Servicio servicio1 = new Servicio(1L, "Linux");
        Especialidad especialidad1 = new Especialidad(1L, "Sistemas Operativos");
        Especialidad especialidad2 = new Especialidad(2L, "Linux");

        //Mesa de ayuda
        Incidencia incidencia1 = new Incidencia(1L, "dasdasfffdfds", true, LocalDate.now(), null, tecnico1, cliente1);

        TipoIncidencia tipoDeIncidencia1 = new TipoIncidencia(1L, "Error de instalacion", "bajo",LocalTime.of(01,00), LocalTime.of(02, 00), incidencia1, servicio1);
        */
        
    }
}
