/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.CuentaRetencion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.ValorRetencion;
import com.sae.persistence.domain.Puc;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class CuentaRetencionJpaController implements Serializable {

    public CuentaRetencionJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CuentaRetencion cuentaRetencion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ValorRetencion idValorRetencion = cuentaRetencion.getIdValorRetencion();
            if (idValorRetencion != null) {
                idValorRetencion = em.getReference(idValorRetencion.getClass(), idValorRetencion.getId());
                cuentaRetencion.setIdValorRetencion(idValorRetencion);
            }
            Puc idPuc = cuentaRetencion.getIdPuc();
            if (idPuc != null) {
                idPuc = em.getReference(idPuc.getClass(), idPuc.getId());
                cuentaRetencion.setIdPuc(idPuc);
            }
            em.persist(cuentaRetencion);
            if (idValorRetencion != null) {
                idValorRetencion.getCuentaRetencionList().add(cuentaRetencion);
                idValorRetencion = em.merge(idValorRetencion);
            }
            if (idPuc != null) {
                idPuc.getCuentaRetencionList().add(cuentaRetencion);
                idPuc = em.merge(idPuc);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCuentaRetencion(cuentaRetencion.getId()) != null) {
                throw new PreexistingEntityException("CuentaRetencion " + cuentaRetencion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CuentaRetencion cuentaRetencion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CuentaRetencion persistentCuentaRetencion = em.find(CuentaRetencion.class, cuentaRetencion.getId());
            ValorRetencion idValorRetencionOld = persistentCuentaRetencion.getIdValorRetencion();
            ValorRetencion idValorRetencionNew = cuentaRetencion.getIdValorRetencion();
            Puc idPucOld = persistentCuentaRetencion.getIdPuc();
            Puc idPucNew = cuentaRetencion.getIdPuc();
            if (idValorRetencionNew != null) {
                idValorRetencionNew = em.getReference(idValorRetencionNew.getClass(), idValorRetencionNew.getId());
                cuentaRetencion.setIdValorRetencion(idValorRetencionNew);
            }
            if (idPucNew != null) {
                idPucNew = em.getReference(idPucNew.getClass(), idPucNew.getId());
                cuentaRetencion.setIdPuc(idPucNew);
            }
            cuentaRetencion = em.merge(cuentaRetencion);
            if (idValorRetencionOld != null && !idValorRetencionOld.equals(idValorRetencionNew)) {
                idValorRetencionOld.getCuentaRetencionList().remove(cuentaRetencion);
                idValorRetencionOld = em.merge(idValorRetencionOld);
            }
            if (idValorRetencionNew != null && !idValorRetencionNew.equals(idValorRetencionOld)) {
                idValorRetencionNew.getCuentaRetencionList().add(cuentaRetencion);
                idValorRetencionNew = em.merge(idValorRetencionNew);
            }
            if (idPucOld != null && !idPucOld.equals(idPucNew)) {
                idPucOld.getCuentaRetencionList().remove(cuentaRetencion);
                idPucOld = em.merge(idPucOld);
            }
            if (idPucNew != null && !idPucNew.equals(idPucOld)) {
                idPucNew.getCuentaRetencionList().add(cuentaRetencion);
                idPucNew = em.merge(idPucNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cuentaRetencion.getId();
                if (findCuentaRetencion(id) == null) {
                    throw new NonexistentEntityException("The cuentaRetencion with id " + id + " no longer exists.");
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
            CuentaRetencion cuentaRetencion;
            try {
                cuentaRetencion = em.getReference(CuentaRetencion.class, id);
                cuentaRetencion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuentaRetencion with id " + id + " no longer exists.", enfe);
            }
            ValorRetencion idValorRetencion = cuentaRetencion.getIdValorRetencion();
            if (idValorRetencion != null) {
                idValorRetencion.getCuentaRetencionList().remove(cuentaRetencion);
                idValorRetencion = em.merge(idValorRetencion);
            }
            Puc idPuc = cuentaRetencion.getIdPuc();
            if (idPuc != null) {
                idPuc.getCuentaRetencionList().remove(cuentaRetencion);
                idPuc = em.merge(idPuc);
            }
            em.remove(cuentaRetencion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CuentaRetencion> findCuentaRetencionEntities() {
        return findCuentaRetencionEntities(true, -1, -1);
    }

    public List<CuentaRetencion> findCuentaRetencionEntities(int maxResults, int firstResult) {
        return findCuentaRetencionEntities(false, maxResults, firstResult);
    }

    private List<CuentaRetencion> findCuentaRetencionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CuentaRetencion.class));
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

    public CuentaRetencion findCuentaRetencion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CuentaRetencion.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuentaRetencionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CuentaRetencion> rt = cq.from(CuentaRetencion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
