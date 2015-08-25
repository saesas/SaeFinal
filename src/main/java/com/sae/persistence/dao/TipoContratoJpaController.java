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
import com.sae.persistence.domain.ContratoTercero;
import com.sae.persistence.domain.TipoContrato;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoContratoJpaController implements Serializable {

    public TipoContratoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoContrato tipoContrato) throws PreexistingEntityException, Exception {
        if (tipoContrato.getContratoTerceroList() == null) {
            tipoContrato.setContratoTerceroList(new ArrayList<ContratoTercero>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ContratoTercero> attachedContratoTerceroList = new ArrayList<ContratoTercero>();
            for (ContratoTercero contratoTerceroListContratoTerceroToAttach : tipoContrato.getContratoTerceroList()) {
                contratoTerceroListContratoTerceroToAttach = em.getReference(contratoTerceroListContratoTerceroToAttach.getClass(), contratoTerceroListContratoTerceroToAttach.getId());
                attachedContratoTerceroList.add(contratoTerceroListContratoTerceroToAttach);
            }
            tipoContrato.setContratoTerceroList(attachedContratoTerceroList);
            em.persist(tipoContrato);
            for (ContratoTercero contratoTerceroListContratoTercero : tipoContrato.getContratoTerceroList()) {
                TipoContrato oldIdTipoContratoOfContratoTerceroListContratoTercero = contratoTerceroListContratoTercero.getIdTipoContrato();
                contratoTerceroListContratoTercero.setIdTipoContrato(tipoContrato);
                contratoTerceroListContratoTercero = em.merge(contratoTerceroListContratoTercero);
                if (oldIdTipoContratoOfContratoTerceroListContratoTercero != null) {
                    oldIdTipoContratoOfContratoTerceroListContratoTercero.getContratoTerceroList().remove(contratoTerceroListContratoTercero);
                    oldIdTipoContratoOfContratoTerceroListContratoTercero = em.merge(oldIdTipoContratoOfContratoTerceroListContratoTercero);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoContrato(tipoContrato.getId()) != null) {
                throw new PreexistingEntityException("TipoContrato " + tipoContrato + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoContrato tipoContrato) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoContrato persistentTipoContrato = em.find(TipoContrato.class, tipoContrato.getId());
            List<ContratoTercero> contratoTerceroListOld = persistentTipoContrato.getContratoTerceroList();
            List<ContratoTercero> contratoTerceroListNew = tipoContrato.getContratoTerceroList();
            List<ContratoTercero> attachedContratoTerceroListNew = new ArrayList<ContratoTercero>();
            for (ContratoTercero contratoTerceroListNewContratoTerceroToAttach : contratoTerceroListNew) {
                contratoTerceroListNewContratoTerceroToAttach = em.getReference(contratoTerceroListNewContratoTerceroToAttach.getClass(), contratoTerceroListNewContratoTerceroToAttach.getId());
                attachedContratoTerceroListNew.add(contratoTerceroListNewContratoTerceroToAttach);
            }
            contratoTerceroListNew = attachedContratoTerceroListNew;
            tipoContrato.setContratoTerceroList(contratoTerceroListNew);
            tipoContrato = em.merge(tipoContrato);
            for (ContratoTercero contratoTerceroListOldContratoTercero : contratoTerceroListOld) {
                if (!contratoTerceroListNew.contains(contratoTerceroListOldContratoTercero)) {
                    contratoTerceroListOldContratoTercero.setIdTipoContrato(null);
                    contratoTerceroListOldContratoTercero = em.merge(contratoTerceroListOldContratoTercero);
                }
            }
            for (ContratoTercero contratoTerceroListNewContratoTercero : contratoTerceroListNew) {
                if (!contratoTerceroListOld.contains(contratoTerceroListNewContratoTercero)) {
                    TipoContrato oldIdTipoContratoOfContratoTerceroListNewContratoTercero = contratoTerceroListNewContratoTercero.getIdTipoContrato();
                    contratoTerceroListNewContratoTercero.setIdTipoContrato(tipoContrato);
                    contratoTerceroListNewContratoTercero = em.merge(contratoTerceroListNewContratoTercero);
                    if (oldIdTipoContratoOfContratoTerceroListNewContratoTercero != null && !oldIdTipoContratoOfContratoTerceroListNewContratoTercero.equals(tipoContrato)) {
                        oldIdTipoContratoOfContratoTerceroListNewContratoTercero.getContratoTerceroList().remove(contratoTerceroListNewContratoTercero);
                        oldIdTipoContratoOfContratoTerceroListNewContratoTercero = em.merge(oldIdTipoContratoOfContratoTerceroListNewContratoTercero);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoContrato.getId();
                if (findTipoContrato(id) == null) {
                    throw new NonexistentEntityException("The tipoContrato with id " + id + " no longer exists.");
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
            TipoContrato tipoContrato;
            try {
                tipoContrato = em.getReference(TipoContrato.class, id);
                tipoContrato.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoContrato with id " + id + " no longer exists.", enfe);
            }
            List<ContratoTercero> contratoTerceroList = tipoContrato.getContratoTerceroList();
            for (ContratoTercero contratoTerceroListContratoTercero : contratoTerceroList) {
                contratoTerceroListContratoTercero.setIdTipoContrato(null);
                contratoTerceroListContratoTercero = em.merge(contratoTerceroListContratoTercero);
            }
            em.remove(tipoContrato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoContrato> findTipoContratoEntities() {
        return findTipoContratoEntities(true, -1, -1);
    }

    public List<TipoContrato> findTipoContratoEntities(int maxResults, int firstResult) {
        return findTipoContratoEntities(false, maxResults, firstResult);
    }

    private List<TipoContrato> findTipoContratoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoContrato.class));
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

    public TipoContrato findTipoContrato(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoContrato.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoContratoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoContrato> rt = cq.from(TipoContrato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
