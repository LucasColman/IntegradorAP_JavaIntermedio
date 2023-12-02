
package com.mycompany.reportedeincidentes.repositorio;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.reportedeincidentes.modelo.Especialidad;
import java.util.HashSet;
import java.util.Set;
import com.mycompany.reportedeincidentes.modelo.Incidencia;
import com.mycompany.reportedeincidentes.modelo.Tecnico;
import com.mycompany.reportedeincidentes.repositorio.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class TecnicoJpaController implements Serializable {

    public TecnicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public TecnicoJpaController() {
        emf = Persistence.createEntityManagerFactory("incidenciaPU");
    }
    
    
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tecnico tecnico) {
        if (tecnico.getEspecialidades() == null) {
            tecnico.setEspecialidades(new HashSet<Especialidad>());
        }
        if (tecnico.getIncidencias() == null) {
            tecnico.setIncidencias(new HashSet<Incidencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<Especialidad> attachedEspecialidades = new HashSet<Especialidad>();
            for (Especialidad especialidadesEspecialidadToAttach : tecnico.getEspecialidades()) {
                especialidadesEspecialidadToAttach = em.getReference(especialidadesEspecialidadToAttach.getClass(), especialidadesEspecialidadToAttach.getIdEspecialidad());
                attachedEspecialidades.add(especialidadesEspecialidadToAttach);
            }
            tecnico.setEspecialidades(attachedEspecialidades);
            Set<Incidencia> attachedIncidencias = new HashSet<Incidencia>();
            for (Incidencia incidenciasIncidenciaToAttach : tecnico.getIncidencias()) {
                incidenciasIncidenciaToAttach = em.getReference(incidenciasIncidenciaToAttach.getClass(), incidenciasIncidenciaToAttach.getIdIncidencia());
                attachedIncidencias.add(incidenciasIncidenciaToAttach);
            }
            tecnico.setIncidencias(attachedIncidencias);
            em.persist(tecnico);
            for (Especialidad especialidadesEspecialidad : tecnico.getEspecialidades()) {
                especialidadesEspecialidad.getTecnicos().add(tecnico);
                especialidadesEspecialidad = em.merge(especialidadesEspecialidad);
            }
            for (Incidencia incidenciasIncidencia : tecnico.getIncidencias()) {
                Tecnico oldTecnicoOfIncidenciasIncidencia = incidenciasIncidencia.getTecnico();
                incidenciasIncidencia.setTecnico(tecnico);
                incidenciasIncidencia = em.merge(incidenciasIncidencia);
                if (oldTecnicoOfIncidenciasIncidencia != null) {
                    oldTecnicoOfIncidenciasIncidencia.getIncidencias().remove(incidenciasIncidencia);
                    oldTecnicoOfIncidenciasIncidencia = em.merge(oldTecnicoOfIncidenciasIncidencia);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tecnico tecnico) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tecnico persistentTecnico = em.find(Tecnico.class, tecnico.getIdTecnico());
            Set<Especialidad> especialidadesOld = persistentTecnico.getEspecialidades();
            Set<Especialidad> especialidadesNew = tecnico.getEspecialidades();
            Set<Incidencia> incidenciasOld = persistentTecnico.getIncidencias();
            Set<Incidencia> incidenciasNew = tecnico.getIncidencias();
            Set<Especialidad> attachedEspecialidadesNew = new HashSet<Especialidad>();
            for (Especialidad especialidadesNewEspecialidadToAttach : especialidadesNew) {
                especialidadesNewEspecialidadToAttach = em.getReference(especialidadesNewEspecialidadToAttach.getClass(), especialidadesNewEspecialidadToAttach.getIdEspecialidad());
                attachedEspecialidadesNew.add(especialidadesNewEspecialidadToAttach);
            }
            especialidadesNew = attachedEspecialidadesNew;
            tecnico.setEspecialidades(especialidadesNew);
            Set<Incidencia> attachedIncidenciasNew = new HashSet<Incidencia>();
            for (Incidencia incidenciasNewIncidenciaToAttach : incidenciasNew) {
                incidenciasNewIncidenciaToAttach = em.getReference(incidenciasNewIncidenciaToAttach.getClass(), incidenciasNewIncidenciaToAttach.getIdIncidencia());
                attachedIncidenciasNew.add(incidenciasNewIncidenciaToAttach);
            }
            incidenciasNew = attachedIncidenciasNew;
            tecnico.setIncidencias(incidenciasNew);
            tecnico = em.merge(tecnico);
            for (Especialidad especialidadesOldEspecialidad : especialidadesOld) {
                if (!especialidadesNew.contains(especialidadesOldEspecialidad)) {
                    especialidadesOldEspecialidad.getTecnicos().remove(tecnico);
                    especialidadesOldEspecialidad = em.merge(especialidadesOldEspecialidad);
                }
            }
            for (Especialidad especialidadesNewEspecialidad : especialidadesNew) {
                if (!especialidadesOld.contains(especialidadesNewEspecialidad)) {
                    especialidadesNewEspecialidad.getTecnicos().add(tecnico);
                    especialidadesNewEspecialidad = em.merge(especialidadesNewEspecialidad);
                }
            }
            for (Incidencia incidenciasOldIncidencia : incidenciasOld) {
                if (!incidenciasNew.contains(incidenciasOldIncidencia)) {
                    incidenciasOldIncidencia.setTecnico(null);
                    incidenciasOldIncidencia = em.merge(incidenciasOldIncidencia);
                }
            }
            for (Incidencia incidenciasNewIncidencia : incidenciasNew) {
                if (!incidenciasOld.contains(incidenciasNewIncidencia)) {
                    Tecnico oldTecnicoOfIncidenciasNewIncidencia = incidenciasNewIncidencia.getTecnico();
                    incidenciasNewIncidencia.setTecnico(tecnico);
                    incidenciasNewIncidencia = em.merge(incidenciasNewIncidencia);
                    if (oldTecnicoOfIncidenciasNewIncidencia != null && !oldTecnicoOfIncidenciasNewIncidencia.equals(tecnico)) {
                        oldTecnicoOfIncidenciasNewIncidencia.getIncidencias().remove(incidenciasNewIncidencia);
                        oldTecnicoOfIncidenciasNewIncidencia = em.merge(oldTecnicoOfIncidenciasNewIncidencia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tecnico.getIdTecnico();
                if (findTecnico(id) == null) {
                    throw new NonexistentEntityException("The tecnico with id " + id + " no longer exists.");
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
            Tecnico tecnico;
            try {
                tecnico = em.getReference(Tecnico.class, id);
                tecnico.getIdTecnico();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tecnico with id " + id + " no longer exists.", enfe);
            }
            Set<Especialidad> especialidades = tecnico.getEspecialidades();
            for (Especialidad especialidadesEspecialidad : especialidades) {
                especialidadesEspecialidad.getTecnicos().remove(tecnico);
                especialidadesEspecialidad = em.merge(especialidadesEspecialidad);
            }
            Set<Incidencia> incidencias = tecnico.getIncidencias();
            for (Incidencia incidenciasIncidencia : incidencias) {
                incidenciasIncidencia.setTecnico(null);
                incidenciasIncidencia = em.merge(incidenciasIncidencia);
            }
            em.remove(tecnico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tecnico> findTecnicoEntities() {
        return findTecnicoEntities(true, -1, -1);
    }

    public List<Tecnico> findTecnicoEntities(int maxResults, int firstResult) {
        return findTecnicoEntities(false, maxResults, firstResult);
    }

    private List<Tecnico> findTecnicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tecnico.class));
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

    public Tecnico findTecnico(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tecnico.class, id);
        } finally {
            em.close();
        }
    }

    public int getTecnicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tecnico> rt = cq.from(Tecnico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
