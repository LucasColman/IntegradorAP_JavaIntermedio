
package com.mycompany.reportedeincidentes.repositorio;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.reportedeincidentes.modelo.Tecnico;
import com.mycompany.reportedeincidentes.modelo.Cliente;
import com.mycompany.reportedeincidentes.modelo.Servicio;
import java.util.HashSet;
import java.util.Set;
import com.mycompany.reportedeincidentes.modelo.Reporte;
import com.mycompany.reportedeincidentes.modelo.TipoIncidencia;
import com.mycompany.reportedeincidentes.modelo.Especialidad;
import com.mycompany.reportedeincidentes.modelo.Incidencia;
import com.mycompany.reportedeincidentes.repositorio.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Lucas
 */
public class IncidenciaJpaController implements Serializable {

    public IncidenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
     public IncidenciaJpaController(){
        emf = Persistence.createEntityManagerFactory("incidenciaPU");
    }
     
     
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Incidencia incidencia) {
        if (incidencia.getServicios() == null) {
            incidencia.setServicios(new HashSet<Servicio>());
        }
        if (incidencia.getReportes() == null) {
            incidencia.setReportes(new HashSet<Reporte>());
        }
        if (incidencia.getTiposIncidencias() == null) {
            incidencia.setTiposIncidencias(new HashSet<TipoIncidencia>());
        }
        if (incidencia.getEspecialidades() == null) {
            incidencia.setEspecialidades(new HashSet<Especialidad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tecnico tecnico = incidencia.getTecnico();
            if (tecnico != null) {
                tecnico = em.getReference(tecnico.getClass(), tecnico.getIdTecnico());
                incidencia.setTecnico(tecnico);
            }
            Cliente cliente = incidencia.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getIdCliente());
                incidencia.setCliente(cliente);
            }
            Set<Servicio> attachedServicios = new HashSet<Servicio>();
            for (Servicio serviciosServicioToAttach : incidencia.getServicios()) {
                serviciosServicioToAttach = em.getReference(serviciosServicioToAttach.getClass(), serviciosServicioToAttach.getIdServicio());
                attachedServicios.add(serviciosServicioToAttach);
            }
            incidencia.setServicios(attachedServicios);
            Set<Reporte> attachedReportes = new HashSet<Reporte>();
            for (Reporte reportesReporteToAttach : incidencia.getReportes()) {
                reportesReporteToAttach = em.getReference(reportesReporteToAttach.getClass(), reportesReporteToAttach.getIdReporte());
                attachedReportes.add(reportesReporteToAttach);
            }
            incidencia.setReportes(attachedReportes);
            Set<TipoIncidencia> attachedTiposIncidencias = new HashSet<TipoIncidencia>();
            for (TipoIncidencia tiposIncidenciasTipoIncidenciaToAttach : incidencia.getTiposIncidencias()) {
                tiposIncidenciasTipoIncidenciaToAttach = em.getReference(tiposIncidenciasTipoIncidenciaToAttach.getClass(), tiposIncidenciasTipoIncidenciaToAttach.getIdTipoIncidenciaLong());
                attachedTiposIncidencias.add(tiposIncidenciasTipoIncidenciaToAttach);
            }
            incidencia.setTiposIncidencias(attachedTiposIncidencias);
            Set<Especialidad> attachedEspecialidades = new HashSet<Especialidad>();
            for (Especialidad especialidadesEspecialidadToAttach : incidencia.getEspecialidades()) {
                especialidadesEspecialidadToAttach = em.getReference(especialidadesEspecialidadToAttach.getClass(), especialidadesEspecialidadToAttach.getIdEspecialidad());
                attachedEspecialidades.add(especialidadesEspecialidadToAttach);
            }
            incidencia.setEspecialidades(attachedEspecialidades);
            em.persist(incidencia);
            if (tecnico != null) {
                tecnico.getIncidencias().add(incidencia);
                tecnico = em.merge(tecnico);
            }
            if (cliente != null) {
                cliente.getIncidencias().add(incidencia);
                cliente = em.merge(cliente);
            }
            for (Servicio serviciosServicio : incidencia.getServicios()) {
                serviciosServicio.getIncidencias().add(incidencia);
                serviciosServicio = em.merge(serviciosServicio);
            }
            for (Reporte reportesReporte : incidencia.getReportes()) {
                Incidencia oldIncidenciaOfReportesReporte = reportesReporte.getIncidencia();
                reportesReporte.setIncidencia(incidencia);
                reportesReporte = em.merge(reportesReporte);
                if (oldIncidenciaOfReportesReporte != null) {
                    oldIncidenciaOfReportesReporte.getReportes().remove(reportesReporte);
                    oldIncidenciaOfReportesReporte = em.merge(oldIncidenciaOfReportesReporte);
                }
            }
            for (TipoIncidencia tiposIncidenciasTipoIncidencia : incidencia.getTiposIncidencias()) {
                Incidencia oldIncidenciaOfTiposIncidenciasTipoIncidencia = tiposIncidenciasTipoIncidencia.getIncidencia();
                tiposIncidenciasTipoIncidencia.setIncidencia(incidencia);
                tiposIncidenciasTipoIncidencia = em.merge(tiposIncidenciasTipoIncidencia);
                if (oldIncidenciaOfTiposIncidenciasTipoIncidencia != null) {
                    oldIncidenciaOfTiposIncidenciasTipoIncidencia.getTiposIncidencias().remove(tiposIncidenciasTipoIncidencia);
                    oldIncidenciaOfTiposIncidenciasTipoIncidencia = em.merge(oldIncidenciaOfTiposIncidenciasTipoIncidencia);
                }
            }
            for (Especialidad especialidadesEspecialidad : incidencia.getEspecialidades()) {
                Incidencia oldIncidenciaOfEspecialidadesEspecialidad = especialidadesEspecialidad.getIncidencia();
                especialidadesEspecialidad.setIncidencia(incidencia);
                especialidadesEspecialidad = em.merge(especialidadesEspecialidad);
                if (oldIncidenciaOfEspecialidadesEspecialidad != null) {
                    oldIncidenciaOfEspecialidadesEspecialidad.getEspecialidades().remove(especialidadesEspecialidad);
                    oldIncidenciaOfEspecialidadesEspecialidad = em.merge(oldIncidenciaOfEspecialidadesEspecialidad);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Incidencia incidencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Incidencia persistentIncidencia = em.find(Incidencia.class, incidencia.getIdIncidencia());
            Tecnico tecnicoOld = persistentIncidencia.getTecnico();
            Tecnico tecnicoNew = incidencia.getTecnico();
            Cliente clienteOld = persistentIncidencia.getCliente();
            Cliente clienteNew = incidencia.getCliente();
            Set<Servicio> serviciosOld = persistentIncidencia.getServicios();
            Set<Servicio> serviciosNew = incidencia.getServicios();
            Set<Reporte> reportesOld = persistentIncidencia.getReportes();
            Set<Reporte> reportesNew = incidencia.getReportes();
            Set<TipoIncidencia> tiposIncidenciasOld = persistentIncidencia.getTiposIncidencias();
            Set<TipoIncidencia> tiposIncidenciasNew = incidencia.getTiposIncidencias();
            Set<Especialidad> especialidadesOld = persistentIncidencia.getEspecialidades();
            Set<Especialidad> especialidadesNew = incidencia.getEspecialidades();
            if (tecnicoNew != null) {
                tecnicoNew = em.getReference(tecnicoNew.getClass(), tecnicoNew.getIdTecnico());
                incidencia.setTecnico(tecnicoNew);
            }
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getIdCliente());
                incidencia.setCliente(clienteNew);
            }
            Set<Servicio> attachedServiciosNew = new HashSet<Servicio>();
            for (Servicio serviciosNewServicioToAttach : serviciosNew) {
                serviciosNewServicioToAttach = em.getReference(serviciosNewServicioToAttach.getClass(), serviciosNewServicioToAttach.getIdServicio());
                attachedServiciosNew.add(serviciosNewServicioToAttach);
            }
            serviciosNew = attachedServiciosNew;
            incidencia.setServicios(serviciosNew);
            Set<Reporte> attachedReportesNew = new HashSet<Reporte>();
            for (Reporte reportesNewReporteToAttach : reportesNew) {
                reportesNewReporteToAttach = em.getReference(reportesNewReporteToAttach.getClass(), reportesNewReporteToAttach.getIdReporte());
                attachedReportesNew.add(reportesNewReporteToAttach);
            }
            reportesNew = attachedReportesNew;
            incidencia.setReportes(reportesNew);
            Set<TipoIncidencia> attachedTiposIncidenciasNew = new HashSet<TipoIncidencia>();
            for (TipoIncidencia tiposIncidenciasNewTipoIncidenciaToAttach : tiposIncidenciasNew) {
                tiposIncidenciasNewTipoIncidenciaToAttach = em.getReference(tiposIncidenciasNewTipoIncidenciaToAttach.getClass(), tiposIncidenciasNewTipoIncidenciaToAttach.getIdTipoIncidenciaLong());
                attachedTiposIncidenciasNew.add(tiposIncidenciasNewTipoIncidenciaToAttach);
            }
            tiposIncidenciasNew = attachedTiposIncidenciasNew;
            incidencia.setTiposIncidencias(tiposIncidenciasNew);
            Set<Especialidad> attachedEspecialidadesNew = new HashSet<Especialidad>();
            for (Especialidad especialidadesNewEspecialidadToAttach : especialidadesNew) {
                especialidadesNewEspecialidadToAttach = em.getReference(especialidadesNewEspecialidadToAttach.getClass(), especialidadesNewEspecialidadToAttach.getIdEspecialidad());
                attachedEspecialidadesNew.add(especialidadesNewEspecialidadToAttach);
            }
            especialidadesNew = attachedEspecialidadesNew;
            incidencia.setEspecialidades(especialidadesNew);
            incidencia = em.merge(incidencia);
            if (tecnicoOld != null && !tecnicoOld.equals(tecnicoNew)) {
                tecnicoOld.getIncidencias().remove(incidencia);
                tecnicoOld = em.merge(tecnicoOld);
            }
            if (tecnicoNew != null && !tecnicoNew.equals(tecnicoOld)) {
                tecnicoNew.getIncidencias().add(incidencia);
                tecnicoNew = em.merge(tecnicoNew);
            }
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getIncidencias().remove(incidencia);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getIncidencias().add(incidencia);
                clienteNew = em.merge(clienteNew);
            }
            for (Servicio serviciosOldServicio : serviciosOld) {
                if (!serviciosNew.contains(serviciosOldServicio)) {
                    serviciosOldServicio.getIncidencias().remove(incidencia);
                    serviciosOldServicio = em.merge(serviciosOldServicio);
                }
            }
            for (Servicio serviciosNewServicio : serviciosNew) {
                if (!serviciosOld.contains(serviciosNewServicio)) {
                    serviciosNewServicio.getIncidencias().add(incidencia);
                    serviciosNewServicio = em.merge(serviciosNewServicio);
                }
            }
            for (Reporte reportesOldReporte : reportesOld) {
                if (!reportesNew.contains(reportesOldReporte)) {
                    reportesOldReporte.setIncidencia(null);
                    reportesOldReporte = em.merge(reportesOldReporte);
                }
            }
            for (Reporte reportesNewReporte : reportesNew) {
                if (!reportesOld.contains(reportesNewReporte)) {
                    Incidencia oldIncidenciaOfReportesNewReporte = reportesNewReporte.getIncidencia();
                    reportesNewReporte.setIncidencia(incidencia);
                    reportesNewReporte = em.merge(reportesNewReporte);
                    if (oldIncidenciaOfReportesNewReporte != null && !oldIncidenciaOfReportesNewReporte.equals(incidencia)) {
                        oldIncidenciaOfReportesNewReporte.getReportes().remove(reportesNewReporte);
                        oldIncidenciaOfReportesNewReporte = em.merge(oldIncidenciaOfReportesNewReporte);
                    }
                }
            }
            for (TipoIncidencia tiposIncidenciasOldTipoIncidencia : tiposIncidenciasOld) {
                if (!tiposIncidenciasNew.contains(tiposIncidenciasOldTipoIncidencia)) {
                    tiposIncidenciasOldTipoIncidencia.setIncidencia(null);
                    tiposIncidenciasOldTipoIncidencia = em.merge(tiposIncidenciasOldTipoIncidencia);
                }
            }
            for (TipoIncidencia tiposIncidenciasNewTipoIncidencia : tiposIncidenciasNew) {
                if (!tiposIncidenciasOld.contains(tiposIncidenciasNewTipoIncidencia)) {
                    Incidencia oldIncidenciaOfTiposIncidenciasNewTipoIncidencia = tiposIncidenciasNewTipoIncidencia.getIncidencia();
                    tiposIncidenciasNewTipoIncidencia.setIncidencia(incidencia);
                    tiposIncidenciasNewTipoIncidencia = em.merge(tiposIncidenciasNewTipoIncidencia);
                    if (oldIncidenciaOfTiposIncidenciasNewTipoIncidencia != null && !oldIncidenciaOfTiposIncidenciasNewTipoIncidencia.equals(incidencia)) {
                        oldIncidenciaOfTiposIncidenciasNewTipoIncidencia.getTiposIncidencias().remove(tiposIncidenciasNewTipoIncidencia);
                        oldIncidenciaOfTiposIncidenciasNewTipoIncidencia = em.merge(oldIncidenciaOfTiposIncidenciasNewTipoIncidencia);
                    }
                }
            }
            for (Especialidad especialidadesOldEspecialidad : especialidadesOld) {
                if (!especialidadesNew.contains(especialidadesOldEspecialidad)) {
                    especialidadesOldEspecialidad.setIncidencia(null);
                    especialidadesOldEspecialidad = em.merge(especialidadesOldEspecialidad);
                }
            }
            for (Especialidad especialidadesNewEspecialidad : especialidadesNew) {
                if (!especialidadesOld.contains(especialidadesNewEspecialidad)) {
                    Incidencia oldIncidenciaOfEspecialidadesNewEspecialidad = especialidadesNewEspecialidad.getIncidencia();
                    especialidadesNewEspecialidad.setIncidencia(incidencia);
                    especialidadesNewEspecialidad = em.merge(especialidadesNewEspecialidad);
                    if (oldIncidenciaOfEspecialidadesNewEspecialidad != null && !oldIncidenciaOfEspecialidadesNewEspecialidad.equals(incidencia)) {
                        oldIncidenciaOfEspecialidadesNewEspecialidad.getEspecialidades().remove(especialidadesNewEspecialidad);
                        oldIncidenciaOfEspecialidadesNewEspecialidad = em.merge(oldIncidenciaOfEspecialidadesNewEspecialidad);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = incidencia.getIdIncidencia();
                if (findIncidencia(id) == null) {
                    throw new NonexistentEntityException("The incidencia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Incidencia incidencia;
            try {
                incidencia = em.getReference(Incidencia.class, id);
                incidencia.getIdIncidencia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The incidencia with id " + id + " no longer exists.", enfe);
            }
            Tecnico tecnico = incidencia.getTecnico();
            if (tecnico != null) {
                tecnico.getIncidencias().remove(incidencia);
                tecnico = em.merge(tecnico);
            }
            Cliente cliente = incidencia.getCliente();
            if (cliente != null) {
                cliente.getIncidencias().remove(incidencia);
                cliente = em.merge(cliente);
            }
            Set<Servicio> servicios = incidencia.getServicios();
            for (Servicio serviciosServicio : servicios) {
                serviciosServicio.getIncidencias().remove(incidencia);
                serviciosServicio = em.merge(serviciosServicio);
            }
            Set<Reporte> reportes = incidencia.getReportes();
            for (Reporte reportesReporte : reportes) {
                reportesReporte.setIncidencia(null);
                reportesReporte = em.merge(reportesReporte);
            }
            Set<TipoIncidencia> tiposIncidencias = incidencia.getTiposIncidencias();
            for (TipoIncidencia tiposIncidenciasTipoIncidencia : tiposIncidencias) {
                tiposIncidenciasTipoIncidencia.setIncidencia(null);
                tiposIncidenciasTipoIncidencia = em.merge(tiposIncidenciasTipoIncidencia);
            }
            Set<Especialidad> especialidades = incidencia.getEspecialidades();
            for (Especialidad especialidadesEspecialidad : especialidades) {
                especialidadesEspecialidad.setIncidencia(null);
                especialidadesEspecialidad = em.merge(especialidadesEspecialidad);
            }
            em.remove(incidencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Incidencia> findIncidenciaEntities() {
        return findIncidenciaEntities(true, -1, -1);
    }

    public List<Incidencia> findIncidenciaEntities(int maxResults, int firstResult) {
        return findIncidenciaEntities(false, maxResults, firstResult);
    }

    private List<Incidencia> findIncidenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Incidencia.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Incidencia findIncidencia(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Incidencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getIncidenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Incidencia> rt = cq.from(Incidencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
