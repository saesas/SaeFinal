/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.ContratistaContrato;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.ContratoProyectoProveedor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ContratistaContratoJpaController implements Serializable {

    public ContratistaContratoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ContratistaContrato contratistaContrato) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ContratoProyectoProveedor idContrato = contratistaContrato.getIdContrato();
            if (idContrato != null) {
                idContrato = em.getReference(idContrato.getClass(), idContrato.getId());
                contratistaContrato.setIdContrato(idContrato);
            }
            em.persist(contratistaContrato);
            if (idContrato != null) {
                idContrato.getContratistaContratoList().add(contratistaContrato);
                idContrato = em.merge(idContrato);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findContratistaContrato(contratistaContrato.getId()) != null) {
                throw new PreexistingEntityException("ContratistaContrato " + contratistaContrato + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ContratistaContrato contratistaContrato) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ContratistaContrato persistentContratistaContrato = em.find(ContratistaContrato.class, contratistaContrato.getId());
            ContratoProyectoProveedor idContratoOld = persistentContratistaContrato.getIdContrato();
            ContratoProyectoProveedor idContratoNew = contratistaContrato.getIdContrato();
            if (idContratoNew != null) {
                idContratoNew = em.getReference(idContratoNew.getClass(), idContratoNew.getId());
                contratistaContrato.setIdContrato(idContratoNew);
            }
            contratistaContrato = em.merge(contratistaContrato);
            if (idContratoOld != null && !idContratoOld.equals(idContratoNew)) {
                idContratoOld.getContratistaContratoList().remove(contratistaContrato);
                idContratoOld = em.merge(idContratoOld);
            }
            if (idContratoNew != null && !idContratoNew.equals(idContratoOld)) {
                idContratoNew.getContratistaContratoList().add(contratistaContrato);
                idContratoNew = em.merge(idContratoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contratistaContrato.getId();
                if (findContratistaContrato(id) == null) {
                    throw new NonexistentEntityException("The contratistaContrato with id " + id + " no longer exists.");
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
            ContratistaContrato contratistaContrato;
            try {
                contratistaContrato = em.getReference(ContratistaContrato.class, id);
                contratistaContrato.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contratistaContrato with id " + id + " no longer exists.", enfe);
            }
            ContratoProyectoProveedor idContrato = contratistaContrato.getIdContrato();
            if (idContrato != null) {
                idContrato.getContratistaContratoList().remove(contratistaContrato);
                idContrato = em.merge(idContrato);
            }
            em.remove(contratistaContrato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ContratistaContrato> findContratistaContratoEntities() {
        return findContratistaContratoEntities(true, -1, -1);
    }

    public List<ContratistaContrato> findContratistaContratoEntities(int maxResults, int firstResult) {
        return findContratistaContratoEntities(false, maxResults, firstResult);
    }

    private List<ContratistaContrato> findContratistaContratoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ContratistaContrato.class));
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

    public ContratistaContrato findContratistaContrato(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ContratistaContrato.class, id);
        } finally {
            em.close();
        }
    }

    public int getContratistaContratoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ContratistaContrato> rt = cq.from(ContratistaContrato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
