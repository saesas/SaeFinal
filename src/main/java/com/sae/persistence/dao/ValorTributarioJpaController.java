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
import com.sae.persistence.domain.RetencionEmpleado;
import com.sae.persistence.domain.ValorTributario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ValorTributarioJpaController implements Serializable {

    public ValorTributarioJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ValorTributario valorTributario) throws PreexistingEntityException, Exception {
        if (valorTributario.getRetencionEmpleadoList() == null) {
            valorTributario.setRetencionEmpleadoList(new ArrayList<RetencionEmpleado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<RetencionEmpleado> attachedRetencionEmpleadoList = new ArrayList<RetencionEmpleado>();
            for (RetencionEmpleado retencionEmpleadoListRetencionEmpleadoToAttach : valorTributario.getRetencionEmpleadoList()) {
                retencionEmpleadoListRetencionEmpleadoToAttach = em.getReference(retencionEmpleadoListRetencionEmpleadoToAttach.getClass(), retencionEmpleadoListRetencionEmpleadoToAttach.getId());
                attachedRetencionEmpleadoList.add(retencionEmpleadoListRetencionEmpleadoToAttach);
            }
            valorTributario.setRetencionEmpleadoList(attachedRetencionEmpleadoList);
            em.persist(valorTributario);
            for (RetencionEmpleado retencionEmpleadoListRetencionEmpleado : valorTributario.getRetencionEmpleadoList()) {
                ValorTributario oldIdValorTributarioOfRetencionEmpleadoListRetencionEmpleado = retencionEmpleadoListRetencionEmpleado.getIdValorTributario();
                retencionEmpleadoListRetencionEmpleado.setIdValorTributario(valorTributario);
                retencionEmpleadoListRetencionEmpleado = em.merge(retencionEmpleadoListRetencionEmpleado);
                if (oldIdValorTributarioOfRetencionEmpleadoListRetencionEmpleado != null) {
                    oldIdValorTributarioOfRetencionEmpleadoListRetencionEmpleado.getRetencionEmpleadoList().remove(retencionEmpleadoListRetencionEmpleado);
                    oldIdValorTributarioOfRetencionEmpleadoListRetencionEmpleado = em.merge(oldIdValorTributarioOfRetencionEmpleadoListRetencionEmpleado);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findValorTributario(valorTributario.getId()) != null) {
                throw new PreexistingEntityException("ValorTributario " + valorTributario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ValorTributario valorTributario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ValorTributario persistentValorTributario = em.find(ValorTributario.class, valorTributario.getId());
            List<RetencionEmpleado> retencionEmpleadoListOld = persistentValorTributario.getRetencionEmpleadoList();
            List<RetencionEmpleado> retencionEmpleadoListNew = valorTributario.getRetencionEmpleadoList();
            List<RetencionEmpleado> attachedRetencionEmpleadoListNew = new ArrayList<RetencionEmpleado>();
            for (RetencionEmpleado retencionEmpleadoListNewRetencionEmpleadoToAttach : retencionEmpleadoListNew) {
                retencionEmpleadoListNewRetencionEmpleadoToAttach = em.getReference(retencionEmpleadoListNewRetencionEmpleadoToAttach.getClass(), retencionEmpleadoListNewRetencionEmpleadoToAttach.getId());
                attachedRetencionEmpleadoListNew.add(retencionEmpleadoListNewRetencionEmpleadoToAttach);
            }
            retencionEmpleadoListNew = attachedRetencionEmpleadoListNew;
            valorTributario.setRetencionEmpleadoList(retencionEmpleadoListNew);
            valorTributario = em.merge(valorTributario);
            for (RetencionEmpleado retencionEmpleadoListOldRetencionEmpleado : retencionEmpleadoListOld) {
                if (!retencionEmpleadoListNew.contains(retencionEmpleadoListOldRetencionEmpleado)) {
                    retencionEmpleadoListOldRetencionEmpleado.setIdValorTributario(null);
                    retencionEmpleadoListOldRetencionEmpleado = em.merge(retencionEmpleadoListOldRetencionEmpleado);
                }
            }
            for (RetencionEmpleado retencionEmpleadoListNewRetencionEmpleado : retencionEmpleadoListNew) {
                if (!retencionEmpleadoListOld.contains(retencionEmpleadoListNewRetencionEmpleado)) {
                    ValorTributario oldIdValorTributarioOfRetencionEmpleadoListNewRetencionEmpleado = retencionEmpleadoListNewRetencionEmpleado.getIdValorTributario();
                    retencionEmpleadoListNewRetencionEmpleado.setIdValorTributario(valorTributario);
                    retencionEmpleadoListNewRetencionEmpleado = em.merge(retencionEmpleadoListNewRetencionEmpleado);
                    if (oldIdValorTributarioOfRetencionEmpleadoListNewRetencionEmpleado != null && !oldIdValorTributarioOfRetencionEmpleadoListNewRetencionEmpleado.equals(valorTributario)) {
                        oldIdValorTributarioOfRetencionEmpleadoListNewRetencionEmpleado.getRetencionEmpleadoList().remove(retencionEmpleadoListNewRetencionEmpleado);
                        oldIdValorTributarioOfRetencionEmpleadoListNewRetencionEmpleado = em.merge(oldIdValorTributarioOfRetencionEmpleadoListNewRetencionEmpleado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = valorTributario.getId();
                if (findValorTributario(id) == null) {
                    throw new NonexistentEntityException("The valorTributario with id " + id + " no longer exists.");
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
            ValorTributario valorTributario;
            try {
                valorTributario = em.getReference(ValorTributario.class, id);
                valorTributario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The valorTributario with id " + id + " no longer exists.", enfe);
            }
            List<RetencionEmpleado> retencionEmpleadoList = valorTributario.getRetencionEmpleadoList();
            for (RetencionEmpleado retencionEmpleadoListRetencionEmpleado : retencionEmpleadoList) {
                retencionEmpleadoListRetencionEmpleado.setIdValorTributario(null);
                retencionEmpleadoListRetencionEmpleado = em.merge(retencionEmpleadoListRetencionEmpleado);
            }
            em.remove(valorTributario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ValorTributario> findValorTributarioEntities() {
        return findValorTributarioEntities(true, -1, -1);
    }

    public List<ValorTributario> findValorTributarioEntities(int maxResults, int firstResult) {
        return findValorTributarioEntities(false, maxResults, firstResult);
    }

    private List<ValorTributario> findValorTributarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ValorTributario.class));
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

    public ValorTributario findValorTributario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ValorTributario.class, id);
        } finally {
            em.close();
        }
    }

    public int getValorTributarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ValorTributario> rt = cq.from(ValorTributario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
