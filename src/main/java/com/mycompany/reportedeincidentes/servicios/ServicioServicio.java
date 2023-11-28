
package com.mycompany.reportedeincidentes.servicios;

import com.mycompany.reportedeincidentes.modelo.Servicio;
import java.util.Set;


public interface ServicioServicio {

    public void crearServicio(Servicio servicio);

    public void eliminarServicio(Long idServicio);

    public void editarServicio(Servicio servicio);

    public Servicio buscarServicio(Long idServicio);

    public Set<Servicio> listarServicios();

}
