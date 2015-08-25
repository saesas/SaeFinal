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
import com.sae.persistence.domain.Credito;
import com.sae.persistence.domain.TipoCredito;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoCreditoJpaController implements Serializable {

    public TipoCreditoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoCredito tipoCredito) throws PreexistingEntityException, Exception {
        if (tipoCredito.getCreditoList() == null) {
            tipoCredito.setCreditoList(new ArrayList<Credito>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Credito> attachedCreditoList = new ArrayList<Credito>();
            for (Credito creditoListCreditoToAttach : tipoCredito.getCreditoList()) {
                creditoListCreditoToAttach = em.getReference(creditoListCreditoToAttach.getClass(), creditoListCreditoToAttach.getId());
                attachedCreditoList.add(creditoListCreditoToAttach);
            }
            tipoCredito.setCreditoList(attachedCreditoList);
            em.persist(tipoCredito);
            for (Credito creditoListCredito : tipoCredito.getCreditoList()) {
                TipoCredito oldIdTipoOfCreditoListCredito = creditoListCredito.getIdTipo();
                creditoListCredito.setIdTipo(tipoCredito);
                creditoListCredito = em.merge(creditoListCredito);
                if (oldIdTipoOfCreditoListCredito != null) {
                    oldIdTipoOfCreditoListCredito.getCreditoList().remove(creditoListCredito);
                    oldIdTipoOfCreditoListCredito = em.merge(oldIdTipoOfCreditoListCredito);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoCredito(tipoCredito.getId()) != null) {
                throw new PreexistingEntityException("TipoCredito " + tipoCredito + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoCredito tipoCredito) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoCredito persistentTipoCredito = em.find(TipoCredito.class, tipoCredito.getId());
            List<Credito> creditoListOld = persistentTipoCredito.getCreditoList();
            List<Credito> creditoListNew = tipoCredito.getCreditoList();
            List<Credito> attachedCreditoListNew = new ArrayList<Credito>();
            for (Credito creditoListNewCreditoToAttach : creditoListNew) {
                creditoListNewCreditoToAttach = em.getReference(creditoListNewCreditoToAttach.getClass(), creditoListNewCreditoToAttach.getId());
                attachedCreditoListNew.add(creditoListNewCreditoToAttach);
            }
            creditoListNew = attachedCreditoListNew;
            tipoCredito.setCreditoList(creditoListNew);
            tipoCredito = em.merge(tipoCredito);
            for (Credito creditoListOldCredito : creditoListOld) {
                if (!creditoListNew.contains(creditoListOldCredito)) {
                    creditoListOldCredito.setIdTipo(null);
                    creditoListOldCredito = em.merge(creditoListOldCredito);
                }
            }
            for (Credito creditoListNewCredito : creditoListNew) {
                if (!creditoListOld.contains(creditoListNewCredito)) {
                    TipoCredito oldIdTipoOfCreditoListNewCredito = creditoListNewCredito.getIdTipo();
                    creditoListNewCredito.setIdTipo(tipoCredito);
                    creditoListNewCredito = em.merge(creditoListNewCredito);
                    if (oldIdTipoOfCreditoListNewCredito != null && !oldIdTipoOfCreditoListNewCredito.equals(tipoCredito)) {
                        oldIdTipoOfCreditoListNewCredito.getCreditoList().remove(creditoListNewCredito);
                        oldIdTipoOfCreditoListNewCredito = em.merge(oldIdTipoOfCreditoListNewCredito);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoCredito.getId();
                if (findTipoCredito(id) == null) {
                    throw new NonexistentEntityException("The tipoCredito with id " + id + " no longer exists.");
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
            TipoCredito tipoCredito;
            try {
                tipoCredito = em.getReference(TipoCredito.class, id);
                tipoCredito.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoCredito with id " + id + " no longer exists.", enfe);
            }
            List<Credito> creditoList = tipoCredito.getCreditoList();
            for (Credito creditoListCredito : creditoList) {
                creditoListCredito.setIdTipo(null);
                creditoListCredito = em.merge(creditoListCredito);
            }
            em.remove(tipoCredito);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoCredito> findTipoCreditoEntities() {
        return findTipoCreditoEntities(true, -1, -1);
    }

    public List<TipoCredito> findTipoCreditoEntities(int maxResults, int firstResult) {
        return findTipoCreditoEntities(false, maxResults, firstResult);
    }

    private List<TipoCredito> findTipoCreditoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoCredito.class));
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

    public TipoCredito findTipoCredito(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoCredito.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoCreditoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoCredito> rt = cq.from(TipoCredito.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
