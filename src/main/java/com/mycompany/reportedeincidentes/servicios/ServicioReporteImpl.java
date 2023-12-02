
package com.mycompany.reportedeincidentes.servicios;

import com.mycompany.reportedeincidentes.modelo.Reporte;
import com.mycompany.reportedeincidentes.repositorio.ReporteJpaController;
import com.mycompany.reportedeincidentes.repositorio.exceptions.NonexistentEntityException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServicioReporteImpl implements ServicioReporte{
    ReporteJpaController reporteJPA = new ReporteJpaController();
    
    @Override
    public void crearReporte(Reporte reporte){
        reporteJPA.create(reporte);
            
    }
    
    @Override
    public void eliminarReporte(Long idReporte){
        try {
            reporteJPA.destroy(idReporte);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ServicioReporteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void editarReporte(Reporte reporte){
        try {
            reporteJPA.edit(reporte);
        } catch (Exception ex) {
            Logger.getLogger(ServicioReporteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public Reporte buscarReporte(Long idReporte){
        return reporteJPA.findReporte(idReporte);
    }
    
    @Override
    public Set<Reporte> listarReportes(){
        List<Reporte> lista = reporteJPA.findReporteEntities();
        Set<Reporte> listaReportes = new HashSet<> (lista);
        return listaReportes;
        
    }
    
    
    
}
