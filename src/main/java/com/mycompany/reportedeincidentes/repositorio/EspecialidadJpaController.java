/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reportedeincidentes.repositorio;

import com.mycompany.reportedeincidentes.modelo.Especialidad;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.reportedeincidentes.modelo.Incidencia;
import com.mycompany.reportedeincidentes.modelo.Tecnico;
import com.mycompany.reportedeincidentes.repositorio.exceptions.NonexistentEntityException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Lucas
 */
public class EspecialidadJpaController implements Serializable {

    public EspecialidadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EspecialidadJpaController() {
        emf = Persistence.createEntityManagerFactory("incidenciaPU");
    }
    
    
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Especialidad especialidad) {
        if (especialidad.getTecnicos() == null) {
            especialidad.setTecnicos(new HashSet<Tecnico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Incidencia incidencia = especialidad.getIncidencia();
            if (incidencia != null) {
                incidencia = em.getReference(incidencia.getClass(), incidencia.getIdIncidencia());
                especialidad.setIncidencia(incidencia);
            }
            Set<Tecnico> attachedTecnicos = new HashSet<Tecnico>();
            for (Tecnico tecnicosTecnicoToAttach : especialidad.getTecnicos()) {
                tecnicosTecnicoToAttach = em.getReference(tecnicosTecnicoToAttach.getClass(), tecnicosTecnicoToAttach.getIdTecnico());
                attachedTecnicos.add(tecnicosTecnicoToAttach);
            }
            especialidad.setTecnicos(attachedTecnicos);
            em.persist(especialidad);
            if (incidencia != null) {
                incidencia.getEspecialidades().add(especialidad);
                incidencia = em.merge(incidencia);
            }
            for (Tecnico tecnicosTecnico : especialidad.getTecnicos()) {
                tecnicosTecnico.getEspecialidades().add(especialidad);
                tecnicosTecnico = em.merge(tecnicosTecnico);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Especialidad especialidad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Especialidad persistentEspecialidad = em.find(Especialidad.class, especialidad.getIdEspecialidad());
            Incidencia incidenciaOld = persistentEspecialidad.getIncidencia();
            Incidencia incidenciaNew = especialidad.getIncidencia();
            Set<Tecnico> tecnicosOld = persistentEspecialidad.getTecnicos();
            Set<Tecnico> tecnicosNew = especialidad.getTecnicos();
            if (incidenciaNew != null) {
                incidenciaNew = em.getReference(incidenciaNew.getClass(), incidenciaNew.getIdIncidencia());
                especialidad.setIncidencia(incidenciaNew);
            }
            Set<Tecnico> attachedTecnicosNew = new HashSet<Tecnico>();
            for (Tecnico tecnicosNewTecnicoToAttach : tecnicosNew) {
                tecnicosNewTecnicoToAttach = em.getReference(tecnicosNewTecnicoToAttach.getClass(), tecnicosNewTecnicoToAttach.getIdTecnico());
                attachedTecnicosNew.add(tecnicosNewTecnicoToAttach);
            }
            tecnicosNew = attachedTecnicosNew;
            especialidad.setTecnicos(tecnicosNew);
            especialidad = em.merge(especialidad);
            if (incidenciaOld != null && !incidenciaOld.equals(incidenciaNew)) {
                incidenciaOld.getEspecialidades().remove(especialidad);
                incidenciaOld = em.merge(incidenciaOld);
            }
            if (incidenciaNew != null && !incidenciaNew.equals(incidenciaOld)) {
                incidenciaNew.getEspecialidades().add(especialidad);
                incidenciaNew = em.merge(incidenciaNew);
            }
            for (Tecnico tecnicosOldTecnico : tecnicosOld) {
                if (!tecnicosNew.contains(tecnicosOldTecnico)) {
                    tecnicosOldTecnico.getEspecialidades().remove(especialidad);
                    tecnicosOldTecnico = em.merge(tecnicosOldTecnico);
                }
            }
            for (Tecnico tecnicosNewTecnico : tecnicosNew) {
                if (!tecnicosOld.contains(tecnicosNewTecnico)) {
                    tecnicosNewTecnico.getEspecialidades().add(especialidad);
                    tecnicosNewTecnico = em.merge(tecnicosNewTecnico);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = especialidad.getIdEspecialidad();
                if (findEspecialidad(id) == null) {
                    throw new NonexistentEntityException("The especialidad with id " + id + " no longer exists.");
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
            Especialidad especialidad;
            try {
                especialidad = em.getReference(Especialidad.class, id);
                especialidad.getIdEspecialidad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The especialidad with id " + id + " no longer exists.", enfe);
            }
            Incidencia incidencia = especialidad.getIncidencia();
            if (incidencia != null) {
                incidencia.getEspecialidades().remove(especialidad);
                incidencia = em.merge(incidencia);
            }
            Set<Tecnico> tecnicos = especialidad.getTecnicos();
            for (Tecnico tecnicosTecnico : tecnicos) {
                tecnicosTecnico.getEspecialidades().remove(especialidad);
                tecnicosTecnico = em.merge(tecnicosTecnico);
            }
            em.remove(especialidad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Especialidad> findEspecialidadEntities() {
        return findEspecialidadEntities(true, -1, -1);
    }

    public List<Especialidad> findEspecialidadEntities(int maxResults, int firstResult) {
        return findEspecialidadEntities(false, maxResults, firstResult);
    }

    private List<Especialidad> findEspecialidadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Especialidad.class));
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

    public Especialidad findEspecialidad(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Especialidad.class, id);
        } finally {
            em.close();
        }
    }

    public int getEspecialidadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Especialidad> rt = cq.from(Especialidad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
