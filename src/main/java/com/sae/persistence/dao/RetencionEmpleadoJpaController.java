/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.ValorTributario;
import com.sae.persistence.domain.Puc;
import com.sae.persistence.domain.RetencionEmpleado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class RetencionEmpleadoJpaController implements Serializable {

    public RetencionEmpleadoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RetencionEmpleado retencionEmpleado) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ValorTributario idValorTributario = retencionEmpleado.getIdValorTributario();
            if (idValorTributario != null) {
                idValorTributario = em.getReference(idValorTributario.getClass(), idValorTributario.getId());
                retencionEmpleado.setIdValorTributario(idValorTributario);
            }
            Puc idPuc = retencionEmpleado.getIdPuc();
            if (idPuc != null) {
                idPuc = em.getReference(idPuc.getClass(), idPuc.getId());
                retencionEmpleado.setIdPuc(idPuc);
            }
            em.persist(retencionEmpleado);
            if (idValorTributario != null) {
                idValorTributario.getRetencionEmpleadoList().add(retencionEmpleado);
                idValorTributario = em.merge(idValorTributario);
            }
            if (idPuc != null) {
                idPuc.getRetencionEmpleadoList().add(retencionEmpleado);
                idPuc = em.merge(idPuc);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRetencionEmpleado(retencionEmpleado.getId()) != null) {
                throw new PreexistingEntityException("RetencionEmpleado " + retencionEmpleado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RetencionEmpleado retencionEmpleado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RetencionEmpleado persistentRetencionEmpleado = em.find(RetencionEmpleado.class, retencionEmpleado.getId());
            ValorTributario idValorTributarioOld = persistentRetencionEmpleado.getIdValorTributario();
            ValorTributario idValorTributarioNew = retencionEmpleado.getIdValorTributario();
            Puc idPucOld = persistentRetencionEmpleado.getIdPuc();
            Puc idPucNew = retencionEmpleado.getIdPuc();
            if (idValorTributarioNew != null) {
                idValorTributarioNew = em.getReference(idValorTributarioNew.getClass(), idValorTributarioNew.getId());
                retencionEmpleado.setIdValorTributario(idValorTributarioNew);
            }
            if (idPucNew != null) {
                idPucNew = em.getReference(idPucNew.getClass(), idPucNew.getId());
                retencionEmpleado.setIdPuc(idPucNew);
            }
            retencionEmpleado = em.merge(retencionEmpleado);
            if (idValorTributarioOld != null && !idValorTributarioOld.equals(idValorTributarioNew)) {
                idValorTributarioOld.getRetencionEmpleadoList().remove(retencionEmpleado);
                idValorTributarioOld = em.merge(idValorTributarioOld);
            }
            if (idValorTributarioNew != null && !idValorTributarioNew.equals(idValorTributarioOld)) {
                idValorTributarioNew.getRetencionEmpleadoList().add(retencionEmpleado);
                idValorTributarioNew = em.merge(idValorTributarioNew);
            }
            if (idPucOld != null && !idPucOld.equals(idPucNew)) {
                idPucOld.getRetencionEmpleadoList().remove(retencionEmpleado);
                idPucOld = em.merge(idPucOld);
            }
            if (idPucNew != null && !idPucNew.equals(idPucOld)) {
                idPucNew.getRetencionEmpleadoList().add(retencionEmpleado);
                idPucNew = em.merge(idPucNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = retencionEmpleado.getId();
                if (findRetencionEmpleado(id) == null) {
                    throw new NonexistentEntityException("The retencionEmpleado with id " + id + " no longer exists.");
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
            RetencionEmpleado retencionEmpleado;
            try {
                retencionEmpleado = em.getReference(RetencionEmpleado.class, id);
                retencionEmpleado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The retencionEmpleado with id " + id + " no longer exists.", enfe);
            }
            ValorTributario idValorTributario = retencionEmpleado.getIdValorTributario();
            if (idValorTributario != null) {
                idValorTributario.getRetencionEmpleadoList().remove(retencionEmpleado);
                idValorTributario = em.merge(idValorTributario);
            }
            Puc idPuc = retencionEmpleado.getIdPuc();
            if (idPuc != null) {
                idPuc.getRetencionEmpleadoList().remove(retencionEmpleado);
                idPuc = em.merge(idPuc);
            }
            em.remove(retencionEmpleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RetencionEmpleado> findRetencionEmpleadoEntities() {
        return findRetencionEmpleadoEntities(true, -1, -1);
    }

    public List<RetencionEmpleado> findRetencionEmpleadoEntities(int maxResults, int firstResult) {
        return findRetencionEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<RetencionEmpleado> findRetencionEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RetencionEmpleado.class));
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

    public RetencionEmpleado findRetencionEmpleado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RetencionEmpleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getRetencionEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RetencionEmpleado> rt = cq.from(RetencionEmpleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
