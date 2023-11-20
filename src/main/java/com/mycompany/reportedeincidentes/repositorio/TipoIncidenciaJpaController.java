
package com.mycompany.reportedeincidentes.repositorio;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.reportedeincidentes.modelo.Incidencia;
import com.mycompany.reportedeincidentes.modelo.Servicio;
import com.mycompany.reportedeincidentes.modelo.TipoIncidencia;
import com.mycompany.reportedeincidentes.repositorio.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class TipoIncidenciaJpaController implements Serializable {

    public TipoIncidenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public TipoIncidenciaJpaController() {
        emf = Persistence.createEntityManagerFactory("incidenciaPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoIncidencia tipoIncidencia) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Incidencia incidencia = tipoIncidencia.getIncidencia();
            if (incidencia != null) {
                incidencia = em.getReference(incidencia.getClass(), incidencia.getIdIncidencia());
                tipoIncidencia.setIncidencia(incidencia);
            }
            Servicio servicio = tipoIncidencia.getServicio();
            if (servicio != null) {
                servicio = em.getReference(servicio.getClass(), servicio.getIdServicio());
                tipoIncidencia.setServicio(servicio);
            }
            em.persist(tipoIncidencia);
            if (incidencia != null) {
                incidencia.getTiposIncidencias().add(tipoIncidencia);
                incidencia = em.merge(incidencia);
            }
            if (servicio != null) {
                servicio.getTiposIncidencias().add(tipoIncidencia);
                servicio = em.merge(servicio);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoIncidencia tipoIncidencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoIncidencia persistentTipoIncidencia = em.find(TipoIncidencia.class, tipoIncidencia.getIdTipoIncidenciaLong());
            Incidencia incidenciaOld = persistentTipoIncidencia.getIncidencia();
            Incidencia incidenciaNew = tipoIncidencia.getIncidencia();
            Servicio servicioOld = persistentTipoIncidencia.getServicio();
            Servicio servicioNew = tipoIncidencia.getServicio();
            if (incidenciaNew != null) {
                incidenciaNew = em.getReference(incidenciaNew.getClass(), incidenciaNew.getIdIncidencia());
                tipoIncidencia.setIncidencia(incidenciaNew);
            }
            if (servicioNew != null) {
                servicioNew = em.getReference(servicioNew.getClass(), servicioNew.getIdServicio());
                tipoIncidencia.setServicio(servicioNew);
            }
            tipoIncidencia = em.merge(tipoIncidencia);
            if (incidenciaOld != null && !incidenciaOld.equals(incidenciaNew)) {
                incidenciaOld.getTiposIncidencias().remove(tipoIncidencia);
                incidenciaOld = em.merge(incidenciaOld);
            }
            if (incidenciaNew != null && !incidenciaNew.equals(incidenciaOld)) {
                incidenciaNew.getTiposIncidencias().add(tipoIncidencia);
                incidenciaNew = em.merge(incidenciaNew);
            }
            if (servicioOld != null && !servicioOld.equals(servicioNew)) {
                servicioOld.getTiposIncidencias().remove(tipoIncidencia);
                servicioOld = em.merge(servicioOld);
            }
            if (servicioNew != null && !servicioNew.equals(servicioOld)) {
                servicioNew.getTiposIncidencias().add(tipoIncidencia);
                servicioNew = em.merge(servicioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tipoIncidencia.getIdTipoIncidenciaLong();
                if (findTipoIncidencia(id) == null) {
                    throw new NonexistentEntityException("The tipoIncidencia with id " + id + " no longer exists.");
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
            TipoIncidencia tipoIncidencia;
            try {
                tipoIncidencia = em.getReference(TipoIncidencia.class, id);
                tipoIncidencia.getIdTipoIncidenciaLong();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoIncidencia with id " + id + " no longer exists.", enfe);
            }
            Incidencia incidencia = tipoIncidencia.getIncidencia();
            if (incidencia != null) {
                incidencia.getTiposIncidencias().remove(tipoIncidencia);
                incidencia = em.merge(incidencia);
            }
            Servicio servicio = tipoIncidencia.getServicio();
            if (servicio != null) {
                servicio.getTiposIncidencias().remove(tipoIncidencia);
                servicio = em.merge(servicio);
            }
            em.remove(tipoIncidencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoIncidencia> findTipoIncidenciaEntities() {
        return findTipoIncidenciaEntities(true, -1, -1);
    }

    public List<TipoIncidencia> findTipoIncidenciaEntities(int maxResults, int firstResult) {
        return findTipoIncidenciaEntities(false, maxResults, firstResult);
    }

    private List<TipoIncidencia> findTipoIncidenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoIncidencia.class));
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

    public TipoIncidencia findTipoIncidencia(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoIncidencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoIncidenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoIncidencia> rt = cq.from(TipoIncidencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
