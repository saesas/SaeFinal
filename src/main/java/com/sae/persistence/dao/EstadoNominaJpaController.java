/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.EstadoNomina;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.TipoEstadoNomina;
import com.sae.persistence.domain.Nomina;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class EstadoNominaJpaController implements Serializable {

    public EstadoNominaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoNomina estadoNomina) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoEstadoNomina idEstadoNomina = estadoNomina.getIdEstadoNomina();
            if (idEstadoNomina != null) {
                idEstadoNomina = em.getReference(idEstadoNomina.getClass(), idEstadoNomina.getId());
                estadoNomina.setIdEstadoNomina(idEstadoNomina);
            }
            Nomina idNomina = estadoNomina.getIdNomina();
            if (idNomina != null) {
                idNomina = em.getReference(idNomina.getClass(), idNomina.getId());
                estadoNomina.setIdNomina(idNomina);
            }
            em.persist(estadoNomina);
            if (idEstadoNomina != null) {
                idEstadoNomina.getEstadoNominaList().add(estadoNomina);
                idEstadoNomina = em.merge(idEstadoNomina);
            }
            if (idNomina != null) {
                idNomina.getEstadoNominaList().add(estadoNomina);
                idNomina = em.merge(idNomina);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstadoNomina(estadoNomina.getId()) != null) {
                throw new PreexistingEntityException("EstadoNomina " + estadoNomina + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadoNomina estadoNomina) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoNomina persistentEstadoNomina = em.find(EstadoNomina.class, estadoNomina.getId());
            TipoEstadoNomina idEstadoNominaOld = persistentEstadoNomina.getIdEstadoNomina();
            TipoEstadoNomina idEstadoNominaNew = estadoNomina.getIdEstadoNomina();
            Nomina idNominaOld = persistentEstadoNomina.getIdNomina();
            Nomina idNominaNew = estadoNomina.getIdNomina();
            if (idEstadoNominaNew != null) {
                idEstadoNominaNew = em.getReference(idEstadoNominaNew.getClass(), idEstadoNominaNew.getId());
                estadoNomina.setIdEstadoNomina(idEstadoNominaNew);
            }
            if (idNominaNew != null) {
                idNominaNew = em.getReference(idNominaNew.getClass(), idNominaNew.getId());
                estadoNomina.setIdNomina(idNominaNew);
            }
            estadoNomina = em.merge(estadoNomina);
            if (idEstadoNominaOld != null && !idEstadoNominaOld.equals(idEstadoNominaNew)) {
                idEstadoNominaOld.getEstadoNominaList().remove(estadoNomina);
                idEstadoNominaOld = em.merge(idEstadoNominaOld);
            }
            if (idEstadoNominaNew != null && !idEstadoNominaNew.equals(idEstadoNominaOld)) {
                idEstadoNominaNew.getEstadoNominaList().add(estadoNomina);
                idEstadoNominaNew = em.merge(idEstadoNominaNew);
            }
            if (idNominaOld != null && !idNominaOld.equals(idNominaNew)) {
                idNominaOld.getEstadoNominaList().remove(estadoNomina);
                idNominaOld = em.merge(idNominaOld);
            }
            if (idNominaNew != null && !idNominaNew.equals(idNominaOld)) {
                idNominaNew.getEstadoNominaList().add(estadoNomina);
                idNominaNew = em.merge(idNominaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estadoNomina.getId();
                if (findEstadoNomina(id) == null) {
                    throw new NonexistentEntityException("The estadoNomina with id " + id + " no longer exists.");
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
            EstadoNomina estadoNomina;
            try {
                estadoNomina = em.getReference(EstadoNomina.class, id);
                estadoNomina.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoNomina with id " + id + " no longer exists.", enfe);
            }
            TipoEstadoNomina idEstadoNomina = estadoNomina.getIdEstadoNomina();
            if (idEstadoNomina != null) {
                idEstadoNomina.getEstadoNominaList().remove(estadoNomina);
                idEstadoNomina = em.merge(idEstadoNomina);
            }
            Nomina idNomina = estadoNomina.getIdNomina();
            if (idNomina != null) {
                idNomina.getEstadoNominaList().remove(estadoNomina);
                idNomina = em.merge(idNomina);
            }
            em.remove(estadoNomina);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstadoNomina> findEstadoNominaEntities() {
        return findEstadoNominaEntities(true, -1, -1);
    }

    public List<EstadoNomina> findEstadoNominaEntities(int maxResults, int firstResult) {
        return findEstadoNominaEntities(false, maxResults, firstResult);
    }

    private List<EstadoNomina> findEstadoNominaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadoNomina.class));
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

    public EstadoNomina findEstadoNomina(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoNomina.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoNominaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadoNomina> rt = cq.from(EstadoNomina.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
