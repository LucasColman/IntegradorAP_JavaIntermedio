
package com.mycompany.reportedeincidentes.repositorio;

import com.mycompany.reportedeincidentes.modelo.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.reportedeincidentes.modelo.Servicio;
import java.util.HashSet;
import java.util.Set;
import com.mycompany.reportedeincidentes.modelo.Incidencia;
import com.mycompany.reportedeincidentes.repositorio.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public ClienteJpaController(){
        emf = Persistence.createEntityManagerFactory("incidenciaPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        if (cliente.getServicios() == null) {
            cliente.setServicios(new HashSet<Servicio>());
        }
        if (cliente.getIncidencias() == null) {
            cliente.setIncidencias(new HashSet<Incidencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<Servicio> attachedServicios = new HashSet<Servicio>();
            for (Servicio serviciosServicioToAttach : cliente.getServicios()) {
                serviciosServicioToAttach = em.getReference(serviciosServicioToAttach.getClass(), serviciosServicioToAttach.getIdServicio());
                attachedServicios.add(serviciosServicioToAttach);
            }
            cliente.setServicios(attachedServicios);
            Set<Incidencia> attachedIncidencias = new HashSet<Incidencia>();
            for (Incidencia incidenciasIncidenciaToAttach : cliente.getIncidencias()) {
                incidenciasIncidenciaToAttach = em.getReference(incidenciasIncidenciaToAttach.getClass(), incidenciasIncidenciaToAttach.getIdIncidencia());
                attachedIncidencias.add(incidenciasIncidenciaToAttach);
            }
            cliente.setIncidencias(attachedIncidencias);
            em.persist(cliente);
            for (Servicio serviciosServicio : cliente.getServicios()) {
                serviciosServicio.getClientes().add(cliente);
                serviciosServicio = em.merge(serviciosServicio);
            }
            for (Incidencia incidenciasIncidencia : cliente.getIncidencias()) {
                Cliente oldClienteOfIncidenciasIncidencia = incidenciasIncidencia.getCliente();
                incidenciasIncidencia.setCliente(cliente);
                incidenciasIncidencia = em.merge(incidenciasIncidencia);
                if (oldClienteOfIncidenciasIncidencia != null) {
                    oldClienteOfIncidenciasIncidencia.getIncidencias().remove(incidenciasIncidencia);
                    oldClienteOfIncidenciasIncidencia = em.merge(oldClienteOfIncidenciasIncidencia);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getIdCliente());
            Set<Servicio> serviciosOld = persistentCliente.getServicios();
            Set<Servicio> serviciosNew = cliente.getServicios();
            Set<Incidencia> incidenciasOld = persistentCliente.getIncidencias();
            Set<Incidencia> incidenciasNew = cliente.getIncidencias();
            Set<Servicio> attachedServiciosNew = new HashSet<Servicio>();
            for (Servicio serviciosNewServicioToAttach : serviciosNew) {
                serviciosNewServicioToAttach = em.getReference(serviciosNewServicioToAttach.getClass(), serviciosNewServicioToAttach.getIdServicio());
                attachedServiciosNew.add(serviciosNewServicioToAttach);
            }
            serviciosNew = attachedServiciosNew;
            cliente.setServicios(serviciosNew);
            Set<Incidencia> attachedIncidenciasNew = new HashSet<Incidencia>();
            for (Incidencia incidenciasNewIncidenciaToAttach : incidenciasNew) {
                incidenciasNewIncidenciaToAttach = em.getReference(incidenciasNewIncidenciaToAttach.getClass(), incidenciasNewIncidenciaToAttach.getIdIncidencia());
                attachedIncidenciasNew.add(incidenciasNewIncidenciaToAttach);
            }
            incidenciasNew = attachedIncidenciasNew;
            cliente.setIncidencias(incidenciasNew);
            cliente = em.merge(cliente);
            for (Servicio serviciosOldServicio : serviciosOld) {
                if (!serviciosNew.contains(serviciosOldServicio)) {
                    serviciosOldServicio.getClientes().remove(cliente);
                    serviciosOldServicio = em.merge(serviciosOldServicio);
                }
            }
            for (Servicio serviciosNewServicio : serviciosNew) {
                if (!serviciosOld.contains(serviciosNewServicio)) {
                    serviciosNewServicio.getClientes().add(cliente);
                    serviciosNewServicio = em.merge(serviciosNewServicio);
                }
            }
            for (Incidencia incidenciasOldIncidencia : incidenciasOld) {
                if (!incidenciasNew.contains(incidenciasOldIncidencia)) {
                    incidenciasOldIncidencia.setCliente(null);
                    incidenciasOldIncidencia = em.merge(incidenciasOldIncidencia);
                }
            }
            for (Incidencia incidenciasNewIncidencia : incidenciasNew) {
                if (!incidenciasOld.contains(incidenciasNewIncidencia)) {
                    Cliente oldClienteOfIncidenciasNewIncidencia = incidenciasNewIncidencia.getCliente();
                    incidenciasNewIncidencia.setCliente(cliente);
                    incidenciasNewIncidencia = em.merge(incidenciasNewIncidencia);
                    if (oldClienteOfIncidenciasNewIncidencia != null && !oldClienteOfIncidenciasNewIncidencia.equals(cliente)) {
                        oldClienteOfIncidenciasNewIncidencia.getIncidencias().remove(incidenciasNewIncidencia);
                        oldClienteOfIncidenciasNewIncidencia = em.merge(oldClienteOfIncidenciasNewIncidencia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = cliente.getIdCliente();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getIdCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            Set<Servicio> servicios = cliente.getServicios();
            for (Servicio serviciosServicio : servicios) {
                serviciosServicio.getClientes().remove(cliente);
                serviciosServicio = em.merge(serviciosServicio);
            }
            Set<Incidencia> incidencias = cliente.getIncidencias();
            for (Incidencia incidenciasIncidencia : incidencias) {
                incidenciasIncidencia.setCliente(null);
                incidenciasIncidencia = em.merge(incidenciasIncidencia);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
