package com.mycompany.reportedeincidentes;

import com.mycompany.reportedeincidentes.Enums.Estado;
import com.mycompany.reportedeincidentes.modelo.Cliente;
import com.mycompany.reportedeincidentes.modelo.Especialidad;
import com.mycompany.reportedeincidentes.modelo.Incidencia;
import com.mycompany.reportedeincidentes.modelo.Servicio;
import com.mycompany.reportedeincidentes.modelo.Tecnico;
import com.mycompany.reportedeincidentes.modelo.TipoIncidencia;
import com.mycompany.reportedeincidentes.servicios.ServicioClienteImpl;
import com.mycompany.reportedeincidentes.servicios.ServicioEspecialidaImpl;
import com.mycompany.reportedeincidentes.servicios.ServicioServicioImpl;
import com.mycompany.reportedeincidentes.servicios.ServicioTecnicoImpl;
import com.mycompany.reportedeincidentes.servicios.ServicioTipoIncidenciaImpl;
import java.time.LocalDateTime;
import java.time.LocalTime;
import com.mycompany.reportedeincidentes.servicios.ServicioIncidenciaImpl;

//Sistema de reportes de incidentes
public class ReporteDeIncidentes {

    public static void main(String[] args) throws Exception {
        ServicioClienteImpl servicioClienteImpl = new ServicioClienteImpl();
        ServicioTecnicoImpl servicioTecnicoImpl = new ServicioTecnicoImpl();
        ServicioEspecialidaImpl servicioEspecialidadImpl = new ServicioEspecialidaImpl();
        ServicioServicioImpl servicioServicioImpl = new ServicioServicioImpl();
        ServicioIncidenciaImpl servicioIncidenciaImpl = new ServicioIncidenciaImpl();
        ServicioTipoIncidenciaImpl servicioTipoIncidenciaImpl = new ServicioTipoIncidenciaImpl();

        //Admin
        //Se crean los servicios que ofrece la empresa
        Servicio servicio1 = new Servicio("Linux");
        Servicio servicio2 = new Servicio("Tango");
        Servicio servicio3 = new Servicio("SAP");
        Servicio servicio4 = new Servicio("Windows");
        Servicio servicio5 = new Servicio("Azure");
        Servicio servicio6 = new Servicio("MacOS");

        servicioServicioImpl.crearServicio(servicio1);
        servicioServicioImpl.crearServicio(servicio2);
        servicioServicioImpl.crearServicio(servicio3);
        servicioServicioImpl.crearServicio(servicio4);
        servicioServicioImpl.crearServicio(servicio5);
        servicioServicioImpl.crearServicio(servicio6);

        //Area Comercial - Alta de los clientes
        Cliente cliente1 = new Cliente("Messi", "Leo", "S.A", "4124123");
        Cliente cliente2 = new Cliente("Musk", "Elon", "S.R.L", "54234234");
        Cliente cliente3 = new Cliente("Steve", "Job", "S.R.L", "6456456");

        servicioClienteImpl.crearCliente(cliente1);
        servicioClienteImpl.crearCliente(cliente2);
        servicioClienteImpl.crearCliente(cliente3);

        //Se asocian los servicios a los clientes
        cliente1.agregarServicios(servicio1);
        cliente1.agregarServicios(servicio2);

        cliente2.agregarServicios(servicio4);
        cliente2.agregarServicios(servicio5);

        cliente3.agregarServicios(servicio6);

        //Se persiste en la base de datos
        servicioServicioImpl.editarServicio(servicio1);
        servicioServicioImpl.editarServicio(servicio2);
        servicioServicioImpl.editarServicio(servicio4);
        servicioServicioImpl.editarServicio(servicio5);
        servicioServicioImpl.editarServicio(servicio6);

        //AREA RRHH - Alta de los tecnicos
        Tecnico tecnico1 = new Tecnico("4334543", "Marcelo", "Bielsa");
        Tecnico tecnico2 = new Tecnico("20302302", "Pep", "Guardiola");
        Tecnico tecnico3 = new Tecnico("53645654", "Miguel", "Almiron");

        //Se persiste en la base de datos
        servicioTecnicoImpl.crearTecnico(tecnico1);
        servicioTecnicoImpl.crearTecnico(tecnico2);
        servicioTecnicoImpl.crearTecnico(tecnico3);

        //Se crean las especialidades de los tecnicos
        Especialidad especialidad1 = new Especialidad("MacOS");
        Especialidad especialidad2 = new Especialidad("Linux");
        Especialidad especialidad3 = new Especialidad("Windows");
        Especialidad especialidad4 = new Especialidad("Aplicaciones en la nube");
        Especialidad especialidad5 = new Especialidad("Azure");

        //Se persiste en la base de datos
        servicioEspecialidadImpl.crearEspecialidad(especialidad1);
        servicioEspecialidadImpl.crearEspecialidad(especialidad2);
        servicioEspecialidadImpl.crearEspecialidad(especialidad3);
        servicioEspecialidadImpl.crearEspecialidad(especialidad4);
        servicioEspecialidadImpl.crearEspecialidad(especialidad5);

        //Se asocian las especialidades con los tecnicos
        tecnico1.agregarEspecialidad(especialidad1);

        tecnico2.agregarEspecialidad(especialidad3);
        tecnico2.agregarEspecialidad(especialidad4);
        tecnico2.agregarEspecialidad(especialidad5);

        tecnico3.agregarEspecialidad(especialidad2);
        tecnico3.agregarEspecialidad(especialidad3);
        tecnico3.agregarEspecialidad(especialidad4);
        tecnico3.agregarEspecialidad(especialidad5);

        //Se persiste en la base de datos
        servicioTecnicoImpl.editarTecnico(tecnico1);
        servicioTecnicoImpl.editarTecnico(tecnico2);
        servicioTecnicoImpl.editarTecnico(tecnico3);
        servicioEspecialidadImpl.editarEspecialidad(especialidad1);
        servicioEspecialidadImpl.editarEspecialidad(especialidad2);
        servicioEspecialidadImpl.editarEspecialidad(especialidad3);
        servicioEspecialidadImpl.editarEspecialidad(especialidad4);
        servicioEspecialidadImpl.editarEspecialidad(especialidad5);

        //Mesa de ayuda - Ingresar al sistema los incidentes reportados
        Incidencia incidencia1 = new Incidencia("Reporte de incidente", null, LocalDateTime.now().minusDays(7), null, null, cliente1);

        incidencia1.agregarServicio(servicio1);  //Linux
        incidencia1.agregarServicio(servicio4); //Tango
        incidencia1.agregarServicio(servicio4);//windows

        incidencia1.agregarEspecialidad(especialidad2);
        incidencia1.agregarEspecialidad(especialidad3);
        incidencia1.agregarEspecialidad(especialidad4);

        Incidencia incidencia2 = new Incidencia("Reporte de incidente", null, LocalDateTime.now().minusDays(6), null, null, cliente2);

        incidencia2.agregarServicio(servicio4); //Windows
        incidencia2.agregarServicio(servicio5); //Azure

        incidencia2.agregarEspecialidad(especialidad3);
        incidencia2.agregarEspecialidad(especialidad4);
        incidencia2.agregarEspecialidad(especialidad5);

        Incidencia incidencia3 = new Incidencia("Reporte de incidente", null, LocalDateTime.now().minusDays(5), null, null, cliente2);

        incidencia3.agregarServicio(servicio5);
        incidencia3.agregarEspecialidad(especialidad3);

        Incidencia incidencia4 = new Incidencia("Reporte de incidente", null, LocalDateTime.now().minusDays(4), null, null, cliente3);

        incidencia4.agregarServicio(servicio6); //MacOs
        incidencia4.agregarEspecialidad(especialidad1);

        //Se persiste en la base de datos
        servicioIncidenciaImpl.crearIncidencia(incidencia1);
        servicioIncidenciaImpl.crearIncidencia(incidencia2);
        servicioIncidenciaImpl.crearIncidencia(incidencia3);
        servicioIncidenciaImpl.crearIncidencia(incidencia4);
        servicioServicioImpl.editarServicio(servicio1);
        servicioServicioImpl.editarServicio(servicio2);
        servicioServicioImpl.editarServicio(servicio3);
        servicioServicioImpl.editarServicio(servicio4);
        servicioServicioImpl.editarServicio(servicio5);
        servicioServicioImpl.editarServicio(servicio6);
        servicioEspecialidadImpl.editarEspecialidad(especialidad1);
        servicioEspecialidadImpl.editarEspecialidad(especialidad2);
        servicioEspecialidadImpl.editarEspecialidad(especialidad3);
        servicioEspecialidadImpl.editarEspecialidad(especialidad4);
        servicioEspecialidadImpl.editarEspecialidad(especialidad5);

        //Se crea el tipo de incidencia y se asocia con la incidencia y el servicio
        TipoIncidencia tipoDeIncidencia1 = new TipoIncidencia("Error xxxxx", "bajo", LocalTime.of(2, 0), LocalTime.of(4, 0), incidencia1, servicio1);
        TipoIncidencia tipoDeIncidencia2 = new TipoIncidencia("Error xxxxx", "alto", LocalTime.of(10, 30), LocalTime.of(23, 0), incidencia2, servicio4);
        TipoIncidencia tipoDeIncidencia3 = new TipoIncidencia("Error xxxxx", "medio", LocalTime.of(5, 30), LocalTime.of(10, 0), incidencia2, servicio5);
        TipoIncidencia tipoDeIncidencia4 = new TipoIncidencia("Error xxxxx", "alto", LocalTime.of(15, 0), LocalTime.of(23, 0), incidencia2, servicio5);
        TipoIncidencia tipoDeIncidencia5 = new TipoIncidencia("Error xxxxx", "bajo", LocalTime.of(3, 30), LocalTime.of(10, 0), incidencia3, servicio4);
        TipoIncidencia tipoDeIncidencia6 = new TipoIncidencia("Error xxxxx", "bajo", LocalTime.of(3, 30), LocalTime.of(10, 0), incidencia4, servicio6);

        //Se persiste en la base de datos
        servicioTipoIncidenciaImpl.crearTipoIncidencia(tipoDeIncidencia1);
        servicioTipoIncidenciaImpl.crearTipoIncidencia(tipoDeIncidencia2);
        servicioTipoIncidenciaImpl.crearTipoIncidencia(tipoDeIncidencia3);
        servicioTipoIncidenciaImpl.crearTipoIncidencia(tipoDeIncidencia4);
        servicioTipoIncidenciaImpl.crearTipoIncidencia(tipoDeIncidencia5);
        servicioTipoIncidenciaImpl.crearTipoIncidencia(tipoDeIncidencia6);

        //Persisto en la base de datos
        servicioIncidenciaImpl.editarIncidencia(incidencia1);
        servicioIncidenciaImpl.editarIncidencia(incidencia2);
        servicioIncidenciaImpl.editarIncidencia(incidencia3);
        servicioIncidenciaImpl.editarIncidencia(incidencia4);
        servicioTipoIncidenciaImpl.editarTipoIncidencia(tipoDeIncidencia1);
        servicioTipoIncidenciaImpl.editarTipoIncidencia(tipoDeIncidencia2);
        servicioTipoIncidenciaImpl.editarTipoIncidencia(tipoDeIncidencia3);
        servicioTipoIncidenciaImpl.editarTipoIncidencia(tipoDeIncidencia4);
        servicioTipoIncidenciaImpl.editarTipoIncidencia(tipoDeIncidencia5);
        servicioTipoIncidenciaImpl.editarTipoIncidencia(tipoDeIncidencia6);

        //Se verifica si se le pueade asignar al tecnico la incidencia
        if (servicioTecnicoImpl.puedeResolverIncidencia(tecnico3, incidencia1)) {
            incidencia1.setEstado(Estado.ABIERTO);
            tecnico3.agregarIncidencia(incidencia1);
            servicioTecnicoImpl.editarTecnico(tecnico3);
            servicioIncidenciaImpl.editarIncidencia(incidencia1);
        } else {
            System.out.println("El tecnico " + tecnico3.getApellido() + " no tiene la especialidad para resolver la incidencia");
        }

        servicioTecnicoImpl.resolverIncidencia(incidencia1);
        servicioIncidenciaImpl.editarIncidencia(incidencia1);

        if (servicioTecnicoImpl.puedeResolverIncidencia(tecnico2, incidencia2)) {
            incidencia2.setEstado(Estado.ABIERTO);
            tecnico2.agregarIncidencia(incidencia2);
            servicioTecnicoImpl.editarTecnico(tecnico1);
            servicioIncidenciaImpl.editarIncidencia(incidencia2);
        } else {
            System.out.println("El tecnico " + tecnico2.getApellido() + " no tiene la especialidad para resolver la incidencia");
        }

       
        servicioTecnicoImpl.resolverIncidencia(incidencia2);
        servicioIncidenciaImpl.editarIncidencia(incidencia2);

        if (servicioTecnicoImpl.puedeResolverIncidencia(tecnico2, incidencia3)) {
            incidencia3.setEstado(Estado.ABIERTO);
            tecnico2.agregarIncidencia(incidencia3);
            servicioTecnicoImpl.editarTecnico(tecnico2);
            servicioIncidenciaImpl.editarIncidencia(incidencia3);
        } else {
            System.out.println("El tecnico " + tecnico2.getApellido() + " no tiene la especialidad para resolver la incidencia");
        }

        servicioTecnicoImpl.resolverIncidencia(incidencia3);
        servicioIncidenciaImpl.editarIncidencia(incidencia3);

        if (servicioTecnicoImpl.puedeResolverIncidencia(tecnico1, incidencia4)) {
            incidencia4.setEstado(Estado.ABIERTO);
            tecnico1.agregarIncidencia(incidencia4);
            servicioTecnicoImpl.editarTecnico(tecnico1);
            servicioIncidenciaImpl.editarIncidencia(incidencia4);
        } else {
            System.out.println("El tecnico " + tecnico1.getApellido() + " no tiene la especialidad para resolver la incidencia");
        }

        //Se resuelve la incidencia de manera setenado manualmente
        incidencia4.setFechaResolucion(LocalDateTime.now().minusDays(3));
        incidencia4.setEstado(Estado.CERRADO);
        servicioIncidenciaImpl.editarIncidencia(incidencia4);

        
        
        //----------------------------Solucion a los requerimientos----------------------------------------------
   
        Tecnico tecnicoConMasIncidenciasResueltas = servicioTecnicoImpl.obtenerTecnicoConMasIncidenciasResueltas(4);
        Tecnico tecnicoConMasIncidenciasResueltasPorEspecialidad = servicioTecnicoImpl.obtenerTecnicoConMasIncidenciasResueltas(10, "Windows");
        Tecnico tecnicoMasRapido = servicioTecnicoImpl.obtenerTecnicoMasRapidoEnResolverIncidencias();

        //a)
        System.out.println("El tecnico con mas incidencias resueltas en los ultimos dias ingresados es: " + tecnicoConMasIncidenciasResueltas.getApellido() + " " + tecnicoConMasIncidenciasResueltas.getNombre());

        //b. Quién fue el técnico con más incidentes resueltos de una determinada especialidad en los últimos N días
        System.out.println("El tecnico con mas incidencias resueltas en los ultimos dias ingresados de la especialidad ingresada es: " + tecnicoConMasIncidenciasResueltasPorEspecialidad.getApellido() + " " + tecnicoConMasIncidenciasResueltasPorEspecialidad.getNombre());
        
        //c. Quién fue el técnico que más rápido resolvió los incidentes    ss
        System.out.println("El tecnico que mas rapido resolvio sus incidencias es: " + tecnicoMasRapido.getApellido() + " " + tecnicoMasRapido.getNombre());
    }
}
