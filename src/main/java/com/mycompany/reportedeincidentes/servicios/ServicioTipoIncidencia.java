/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.reportedeincidentes.servicios;

import com.mycompany.reportedeincidentes.modelo.TipoIncidencia;
import java.util.Set;

/**
 *
 * @author Lucas
 */
public interface ServicioTipoIncidencia {

    public void crearTipoIncidencia(TipoIncidencia tipoIncidencia);

    public void eliminarTipoIncidencia(Long idTipoIncidencia);

    public void editarTipoIncidencia(TipoIncidencia tipoIncidencia);

    public TipoIncidencia buscarTipoIncidencia(Long idTipoIncidencia);

    public Set<TipoIncidencia> listarTipoIncidencia();
    
}
