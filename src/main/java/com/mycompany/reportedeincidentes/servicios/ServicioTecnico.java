package com.mycompany.reportedeincidentes.servicios;

import com.mycompany.reportedeincidentes.modelo.Tecnico;
import com.mycompany.reportedeincidentes.repositorio.TecnicoJpaController;
import com.mycompany.reportedeincidentes.repositorio.exceptions.NonexistentEntityException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicioTecnico {

    TecnicoJpaController tecnicoJpa = new TecnicoJpaController();

    public void crearTecnico(Tecnico tecnico) {
        tecnicoJpa.create(tecnico);

    }

    public void eliminarTecnico(Long idTecnico) {

        try {
            tecnicoJpa.destroy(idTecnico);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ServicioTecnico.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void editarTecnico(Tecnico tecnico) {

        try {
            tecnicoJpa.edit(tecnico);
        } catch (Exception ex) {
            Logger.getLogger(ServicioTecnico.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Tecnico buscarTecnico(Long idTecnico) {
        return tecnicoJpa.findTecnico(idTecnico);

    }

    public Set<Tecnico> listarTecnicos() {
        List<Tecnico> lista = tecnicoJpa.findTecnicoEntities();
        Set<Tecnico> listaTecnicos = new HashSet<Tecnico>(lista);
        return listaTecnicos;
    }

}
