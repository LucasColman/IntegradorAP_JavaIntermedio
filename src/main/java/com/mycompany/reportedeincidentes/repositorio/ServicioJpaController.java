
package com.mycompany.reportedeincidentes.repositorio;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.reportedeincidentes.modelo.Cliente;
import java.util.HashSet;
import java.util.Set;
import com.mycompany.reportedeincidentes.modelo.Incidencia;
import com.mycompany.reportedeincidentes.modelo.Servicio;
import com.mycompany.reportedeincidentes.modelo.TipoIncidencia;
import com.mycompany.reportedeincidentes.repositorio.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class ServicioJpaController implements Serializable {

    public ServicioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public ServicioJpaController() {
        emf = Persistence.createEntityManagerFactory("incidenciaPU");
    }
    
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Servicio servicio) {
        if (servicio.getClientes() == null) {
            servicio.setClientes(new HashSet<Cliente>());
        }
        if (servicio.getIncidencias() == null) {
            servicio.setIncidencias(new HashSet<Incidencia>());
        }
        if (servicio.getTiposIncidencias() == null) {
            servicio.setTiposIncidencias(new HashSet<TipoIncidencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<Cliente> attachedClientes = new HashSet<Cliente>();
            for (Cliente clientesClienteToAttach : servicio.getClientes()) {
                clientesClienteToAttach = em.getReference(clientesClienteToAttach.getClass(), clientesClienteToAttach.getIdCliente());
                attachedClientes.add(clientesClienteToAttach);
            }
            servicio.setClientes(attachedClientes);
            Set<Incidencia> attachedIncidencias = new HashSet<Incidencia>();
            for (Incidencia incidenciasIncidenciaToAttach : servicio.getIncidencias()) {
                incidenciasIncidenciaToAttach = em.getReference(incidenciasIncidenciaToAttach.getClass(), incidenciasIncidenciaToAttach.getIdIncidencia());
                attachedIncidencias.add(incidenciasIncidenciaToAttach);
            }
            servicio.setIncidencias(attachedIncidencias);
            Set<TipoIncidencia> attachedTiposIncidencias = new HashSet<TipoIncidencia>();
            for (TipoIncidencia tiposIncidenciasTipoIncidenciaToAttach : servicio.getTiposIncidencias()) {
                tiposIncidenciasTipoIncidenciaToAttach = em.getReference(tiposIncidenciasTipoIncidenciaToAttach.getClass(), tiposIncidenciasTipoIncidenciaToAttach.getIdTipoIncidenciaLong());
                attachedTiposIncidencias.add(tiposIncidenciasTipoIncidenciaToAttach);
            }
            servicio.setTiposIncidencias(attachedTiposIncidencias);
            em.persist(servicio);
            for (Cliente clientesCliente : servicio.getClientes()) {
                clientesCliente.getServicios().add(servicio);
                clientesCliente = em.merge(clientesCliente);
            }
            for (Incidencia incidenciasIncidencia : servicio.getIncidencias()) {
                incidenciasIncidencia.getServicios().add(servicio);
                incidenciasIncidencia = em.merge(incidenciasIncidencia);
            }
            for (TipoIncidencia tiposIncidenciasTipoIncidencia : servicio.getTiposIncidencias()) {
                Servicio oldServicioOfTiposIncidenciasTipoIncidencia = tiposIncidenciasTipoIncidencia.getServicio();
                tiposIncidenciasTipoIncidencia.setServicio(servicio);
                tiposIncidenciasTipoIncidencia = em.merge(tiposIncidenciasTipoIncidencia);
                if (oldServicioOfTiposIncidenciasTipoIncidencia != null) {
                    oldServicioOfTiposIncidenciasTipoIncidencia.getTiposIncidencias().remove(tiposIncidenciasTipoIncidencia);
                    oldServicioOfTiposIncidenciasTipoIncidencia = em.merge(oldServicioOfTiposIncidenciasTipoIncidencia);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Servicio servicio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Servicio persistentServicio = em.find(Servicio.class, servicio.getIdServicio());
            Set<Cliente> clientesOld = persistentServicio.getClientes();
            Set<Cliente> clientesNew = servicio.getClientes();
            Set<Incidencia> incidenciasOld = persistentServicio.getIncidencias();
            Set<Incidencia> incidenciasNew = servicio.getIncidencias();
            Set<TipoIncidencia> tiposIncidenciasOld = persistentServicio.getTiposIncidencias();
            Set<TipoIncidencia> tiposIncidenciasNew = servicio.getTiposIncidencias();
            Set<Cliente> attachedClientesNew = new HashSet<Cliente>();
            for (Cliente clientesNewClienteToAttach : clientesNew) {
                clientesNewClienteToAttach = em.getReference(clientesNewClienteToAttach.getClass(), clientesNewClienteToAttach.getIdCliente());
                attachedClientesNew.add(clientesNewClienteToAttach);
            }
            clientesNew = attachedClientesNew;
            servicio.setClientes(clientesNew);
            Set<Incidencia> attachedIncidenciasNew = new HashSet<Incidencia>();
            for (Incidencia incidenciasNewIncidenciaToAttach : incidenciasNew) {
                incidenciasNewIncidenciaToAttach = em.getReference(incidenciasNewIncidenciaToAttach.getClass(), incidenciasNewIncidenciaToAttach.getIdIncidencia());
                attachedIncidenciasNew.add(incidenciasNewIncidenciaToAttach);
            }
            incidenciasNew = attachedIncidenciasNew;
            servicio.setIncidencias(incidenciasNew);
            Set<TipoIncidencia> attachedTiposIncidenciasNew = new HashSet<TipoIncidencia>();
            for (TipoIncidencia tiposIncidenciasNewTipoIncidenciaToAttach : tiposIncidenciasNew) {
                tiposIncidenciasNewTipoIncidenciaToAttach = em.getReference(tiposIncidenciasNewTipoIncidenciaToAttach.getClass(), tiposIncidenciasNewTipoIncidenciaToAttach.getIdTipoIncidenciaLong());
                attachedTiposIncidenciasNew.add(tiposIncidenciasNewTipoIncidenciaToAttach);
            }
            tiposIncidenciasNew = attachedTiposIncidenciasNew;
            servicio.setTiposIncidencias(tiposIncidenciasNew);
            servicio = em.merge(servicio);
            for (Cliente clientesOldCliente : clientesOld) {
                if (!clientesNew.contains(clientesOldCliente)) {
                    clientesOldCliente.getServicios().remove(servicio);
                    clientesOldCliente = em.merge(clientesOldCliente);
                }
            }
            for (Cliente clientesNewCliente : clientesNew) {
                if (!clientesOld.contains(clientesNewCliente)) {
                    clientesNewCliente.getServicios().add(servicio);
                    clientesNewCliente = em.merge(clientesNewCliente);
                }
            }
            for (Incidencia incidenciasOldIncidencia : incidenciasOld) {
                if (!incidenciasNew.contains(incidenciasOldIncidencia)) {
                    incidenciasOldIncidencia.getServicios().remove(servicio);
                    incidenciasOldIncidencia = em.merge(incidenciasOldIncidencia);
                }
            }
            for (Incidencia incidenciasNewIncidencia : incidenciasNew) {
                if (!incidenciasOld.contains(incidenciasNewIncidencia)) {
                    incidenciasNewIncidencia.getServicios().add(servicio);
                    incidenciasNewIncidencia = em.merge(incidenciasNewIncidencia);
                }
            }
            for (TipoIncidencia tiposIncidenciasOldTipoIncidencia : tiposIncidenciasOld) {
                if (!tiposIncidenciasNew.contains(tiposIncidenciasOldTipoIncidencia)) {
                    tiposIncidenciasOldTipoIncidencia.setServicio(null);
                    tiposIncidenciasOldTipoIncidencia = em.merge(tiposIncidenciasOldTipoIncidencia);
                }
            }
            for (TipoIncidencia tiposIncidenciasNewTipoIncidencia : tiposIncidenciasNew) {
                if (!tiposIncidenciasOld.contains(tiposIncidenciasNewTipoIncidencia)) {
                    Servicio oldServicioOfTiposIncidenciasNewTipoIncidencia = tiposIncidenciasNewTipoIncidencia.getServicio();
                    tiposIncidenciasNewTipoIncidencia.setServicio(servicio);
                    tiposIncidenciasNewTipoIncidencia = em.merge(tiposIncidenciasNewTipoIncidencia);
                    if (oldServicioOfTiposIncidenciasNewTipoIncidencia != null && !oldServicioOfTiposIncidenciasNewTipoIncidencia.equals(servicio)) {
                        oldServicioOfTiposIncidenciasNewTipoIncidencia.getTiposIncidencias().remove(tiposIncidenciasNewTipoIncidencia);
                        oldServicioOfTiposIncidenciasNewTipoIncidencia = em.merge(oldServicioOfTiposIncidenciasNewTipoIncidencia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = servicio.getIdServicio();
                if (findServicio(id) == null) {
                    throw new NonexistentEntityException("The servicio with id " + id + " no longer exists.");
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
            Servicio servicio;
            try {
                servicio = em.getReference(Servicio.class, id);
                servicio.getIdServicio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The servicio with id " + id + " no longer exists.", enfe);
            }
            Set<Cliente> clientes = servicio.getClientes();
            for (Cliente clientesCliente : clientes) {
                clientesCliente.getServicios().remove(servicio);
                clientesCliente = em.merge(clientesCliente);
            }
            Set<Incidencia> incidencias = servicio.getIncidencias();
            for (Incidencia incidenciasIncidencia : incidencias) {
                incidenciasIncidencia.getServicios().remove(servicio);
                incidenciasIncidencia = em.merge(incidenciasIncidencia);
            }
            Set<TipoIncidencia> tiposIncidencias = servicio.getTiposIncidencias();
            for (TipoIncidencia tiposIncidenciasTipoIncidencia : tiposIncidencias) {
                tiposIncidenciasTipoIncidencia.setServicio(null);
                tiposIncidenciasTipoIncidencia = em.merge(tiposIncidenciasTipoIncidencia);
            }
            em.remove(servicio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Servicio> findServicioEntities() {
        return findServicioEntities(true, -1, -1);
    }

    public List<Servicio> findServicioEntities(int maxResults, int firstResult) {
        return findServicioEntities(false, maxResults, firstResult);
    }

    private List<Servicio> findServicioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Servicio.class));
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

    public Servicio findServicio(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Servicio.class, id);
        } finally {
            em.close();
        }
    }

    public int getServicioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Servicio> rt = cq.from(Servicio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
