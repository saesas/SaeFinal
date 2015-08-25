/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.Audiencia;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.CitacionAudiencia;
import com.sae.persistence.domain.EstadoAudiencia;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class AudienciaJpaController implements Serializable {

    public AudienciaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Audiencia audiencia) throws PreexistingEntityException, Exception {
        if (audiencia.getEstadoAudienciaList() == null) {
            audiencia.setEstadoAudienciaList(new ArrayList<EstadoAudiencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CitacionAudiencia idCitacion = audiencia.getIdCitacion();
            if (idCitacion != null) {
                idCitacion = em.getReference(idCitacion.getClass(), idCitacion.getId());
                audiencia.setIdCitacion(idCitacion);
            }
            List<EstadoAudiencia> attachedEstadoAudienciaList = new ArrayList<EstadoAudiencia>();
            for (EstadoAudiencia estadoAudienciaListEstadoAudienciaToAttach : audiencia.getEstadoAudienciaList()) {
                estadoAudienciaListEstadoAudienciaToAttach = em.getReference(estadoAudienciaListEstadoAudienciaToAttach.getClass(), estadoAudienciaListEstadoAudienciaToAttach.getId());
                attachedEstadoAudienciaList.add(estadoAudienciaListEstadoAudienciaToAttach);
            }
            audiencia.setEstadoAudienciaList(attachedEstadoAudienciaList);
            em.persist(audiencia);
            if (idCitacion != null) {
                idCitacion.getAudienciaList().add(audiencia);
                idCitacion = em.merge(idCitacion);
            }
            for (EstadoAudiencia estadoAudienciaListEstadoAudiencia : audiencia.getEstadoAudienciaList()) {
                Audiencia oldIdAudienciaOfEstadoAudienciaListEstadoAudiencia = estadoAudienciaListEstadoAudiencia.getIdAudiencia();
                estadoAudienciaListEstadoAudiencia.setIdAudiencia(audiencia);
                estadoAudienciaListEstadoAudiencia = em.merge(estadoAudienciaListEstadoAudiencia);
                if (oldIdAudienciaOfEstadoAudienciaListEstadoAudiencia != null) {
                    oldIdAudienciaOfEstadoAudienciaListEstadoAudiencia.getEstadoAudienciaList().remove(estadoAudienciaListEstadoAudiencia);
                    oldIdAudienciaOfEstadoAudienciaListEstadoAudiencia = em.merge(oldIdAudienciaOfEstadoAudienciaListEstadoAudiencia);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAudiencia(audiencia.getId()) != null) {
                throw new PreexistingEntityException("Audiencia " + audiencia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Audiencia audiencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Audiencia persistentAudiencia = em.find(Audiencia.class, audiencia.getId());
            CitacionAudiencia idCitacionOld = persistentAudiencia.getIdCitacion();
            CitacionAudiencia idCitacionNew = audiencia.getIdCitacion();
            List<EstadoAudiencia> estadoAudienciaListOld = persistentAudiencia.getEstadoAudienciaList();
            List<EstadoAudiencia> estadoAudienciaListNew = audiencia.getEstadoAudienciaList();
            if (idCitacionNew != null) {
                idCitacionNew = em.getReference(idCitacionNew.getClass(), idCitacionNew.getId());
                audiencia.setIdCitacion(idCitacionNew);
            }
            List<EstadoAudiencia> attachedEstadoAudienciaListNew = new ArrayList<EstadoAudiencia>();
            for (EstadoAudiencia estadoAudienciaListNewEstadoAudienciaToAttach : estadoAudienciaListNew) {
                estadoAudienciaListNewEstadoAudienciaToAttach = em.getReference(estadoAudienciaListNewEstadoAudienciaToAttach.getClass(), estadoAudienciaListNewEstadoAudienciaToAttach.getId());
                attachedEstadoAudienciaListNew.add(estadoAudienciaListNewEstadoAudienciaToAttach);
            }
            estadoAudienciaListNew = attachedEstadoAudienciaListNew;
            audiencia.setEstadoAudienciaList(estadoAudienciaListNew);
            audiencia = em.merge(audiencia);
            if (idCitacionOld != null && !idCitacionOld.equals(idCitacionNew)) {
                idCitacionOld.getAudienciaList().remove(audiencia);
                idCitacionOld = em.merge(idCitacionOld);
            }
            if (idCitacionNew != null && !idCitacionNew.equals(idCitacionOld)) {
                idCitacionNew.getAudienciaList().add(audiencia);
                idCitacionNew = em.merge(idCitacionNew);
            }
            for (EstadoAudiencia estadoAudienciaListOldEstadoAudiencia : estadoAudienciaListOld) {
                if (!estadoAudienciaListNew.contains(estadoAudienciaListOldEstadoAudiencia)) {
                    estadoAudienciaListOldEstadoAudiencia.setIdAudiencia(null);
                    estadoAudienciaListOldEstadoAudiencia = em.merge(estadoAudienciaListOldEstadoAudiencia);
                }
            }
            for (EstadoAudiencia estadoAudienciaListNewEstadoAudiencia : estadoAudienciaListNew) {
                if (!estadoAudienciaListOld.contains(estadoAudienciaListNewEstadoAudiencia)) {
                    Audiencia oldIdAudienciaOfEstadoAudienciaListNewEstadoAudiencia = estadoAudienciaListNewEstadoAudiencia.getIdAudiencia();
                    estadoAudienciaListNewEstadoAudiencia.setIdAudiencia(audiencia);
                    estadoAudienciaListNewEstadoAudiencia = em.merge(estadoAudienciaListNewEstadoAudiencia);
                    if (oldIdAudienciaOfEstadoAudienciaListNewEstadoAudiencia != null && !oldIdAudienciaOfEstadoAudienciaListNewEstadoAudiencia.equals(audiencia)) {
                        oldIdAudienciaOfEstadoAudienciaListNewEstadoAudiencia.getEstadoAudienciaList().remove(estadoAudienciaListNewEstadoAudiencia);
                        oldIdAudienciaOfEstadoAudienciaListNewEstadoAudiencia = em.merge(oldIdAudienciaOfEstadoAudienciaListNewEstadoAudiencia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = audiencia.getId();
                if (findAudiencia(id) == null) {
                    throw new NonexistentEntityException("The audiencia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Audiencia audiencia;
            try {
                audiencia = em.getReference(Audiencia.class, id);
                audiencia.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The audiencia with id " + id + " no longer exists.", enfe);
            }
            CitacionAudiencia idCitacion = audiencia.getIdCitacion();
            if (idCitacion != null) {
                idCitacion.getAudienciaList().remove(audiencia);
                idCitacion = em.merge(idCitacion);
            }
            List<EstadoAudiencia> estadoAudienciaList = audiencia.getEstadoAudienciaList();
            for (EstadoAudiencia estadoAudienciaListEstadoAudiencia : estadoAudienciaList) {
                estadoAudienciaListEstadoAudiencia.setIdAudiencia(null);
                estadoAudienciaListEstadoAudiencia = em.merge(estadoAudienciaListEstadoAudiencia);
            }
            em.remove(audiencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Audiencia> findAudienciaEntities() {
        return findAudienciaEntities(true, -1, -1);
    }

    public List<Audiencia> findAudienciaEntities(int maxResults, int firstResult) {
        return findAudienciaEntities(false, maxResults, firstResult);
    }

    private List<Audiencia> findAudienciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Audiencia.class));
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

    public Audiencia findAudiencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Audiencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getAudienciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Audiencia> rt = cq.from(Audiencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
