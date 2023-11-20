
package com.mycompany.reportedeincidentes.repositorio;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.reportedeincidentes.modelo.Incidencia;
import com.mycompany.reportedeincidentes.modelo.Reporte;
import com.mycompany.reportedeincidentes.repositorio.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Lucas
 */
public class ReporteJpaController implements Serializable {

    public ReporteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public ReporteJpaController() {
        emf = Persistence.createEntityManagerFactory("incidenciaPU");
    }
    
    
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reporte reporte) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Incidencia incidencia = reporte.getIncidencia();
            if (incidencia != null) {
                incidencia = em.getReference(incidencia.getClass(), incidencia.getIdIncidencia());
                reporte.setIncidencia(incidencia);
            }
            em.persist(reporte);
            if (incidencia != null) {
                incidencia.getReportes().add(reporte);
                incidencia = em.merge(incidencia);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reporte reporte) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reporte persistentReporte = em.find(Reporte.class, reporte.getIdReporte());
            Incidencia incidenciaOld = persistentReporte.getIncidencia();
            Incidencia incidenciaNew = reporte.getIncidencia();
            if (incidenciaNew != null) {
                incidenciaNew = em.getReference(incidenciaNew.getClass(), incidenciaNew.getIdIncidencia());
                reporte.setIncidencia(incidenciaNew);
            }
            reporte = em.merge(reporte);
            if (incidenciaOld != null && !incidenciaOld.equals(incidenciaNew)) {
                incidenciaOld.getReportes().remove(reporte);
                incidenciaOld = em.merge(incidenciaOld);
            }
            if (incidenciaNew != null && !incidenciaNew.equals(incidenciaOld)) {
                incidenciaNew.getReportes().add(reporte);
                incidenciaNew = em.merge(incidenciaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = reporte.getIdReporte();
                if (findReporte(id) == null) {
                    throw new NonexistentEntityException("The reporte with id " + id + " no longer exists.");
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
            Reporte reporte;
            try {
                reporte = em.getReference(Reporte.class, id);
                reporte.getIdReporte();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reporte with id " + id + " no longer exists.", enfe);
            }
            Incidencia incidencia = reporte.getIncidencia();
            if (incidencia != null) {
                incidencia.getReportes().remove(reporte);
                incidencia = em.merge(incidencia);
            }
            em.remove(reporte);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reporte> findReporteEntities() {
        return findReporteEntities(true, -1, -1);
    }

    public List<Reporte> findReporteEntities(int maxResults, int firstResult) {
        return findReporteEntities(false, maxResults, firstResult);
    }

    private List<Reporte> findReporteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reporte.class));
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

    public Reporte findReporte(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reporte.class, id);
        } finally {
            em.close();
        }
    }

    public int getReporteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reporte> rt = cq.from(Reporte.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
