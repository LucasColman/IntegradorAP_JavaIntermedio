package com.mycompany.reportedeincidentes.servicios;

import com.mycompany.reportedeincidentes.modelo.Reporte;
import java.util.Set;

public interface ServicioReporte {

    public void crearReporte(Reporte reporte);

    public void eliminarReporte(Long idReporte);

    public void editarReporte(Reporte reporte);

    public Reporte buscarReporte(Long idReporte);

    public Set<Reporte> listarReportes();

}
