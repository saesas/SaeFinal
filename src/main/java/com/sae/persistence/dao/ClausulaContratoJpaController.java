/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.ClausulaContrato;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.TipoClausulaContrato;
import com.sae.persistence.domain.ContratoTercero;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ClausulaContratoJpaController implements Serializable {

    public ClausulaContratoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClausulaContrato clausulaContrato) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoClausulaContrato idClausula = clausulaContrato.getIdClausula();
            if (idClausula != null) {
                idClausula = em.getReference(idClausula.getClass(), idClausula.getId());
                clausulaContrato.setIdClausula(idClausula);
            }
            ContratoTercero idContrato = clausulaContrato.getIdContrato();
            if (idContrato != null) {
                idContrato = em.getReference(idContrato.getClass(), idContrato.getId());
                clausulaContrato.setIdContrato(idContrato);
            }
            em.persist(clausulaContrato);
            if (idClausula != null) {
                idClausula.getClausulaContratoList().add(clausulaContrato);
                idClausula = em.merge(idClausula);
            }
            if (idContrato != null) {
                idContrato.getClausulaContratoList().add(clausulaContrato);
                idContrato = em.merge(idContrato);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClausulaContrato(clausulaContrato.getId()) != null) {
                throw new PreexistingEntityException("ClausulaContrato " + clausulaContrato + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClausulaContrato clausulaContrato) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClausulaContrato persistentClausulaContrato = em.find(ClausulaContrato.class, clausulaContrato.getId());
            TipoClausulaContrato idClausulaOld = persistentClausulaContrato.getIdClausula();
            TipoClausulaContrato idClausulaNew = clausulaContrato.getIdClausula();
            ContratoTercero idContratoOld = persistentClausulaContrato.getIdContrato();
            ContratoTercero idContratoNew = clausulaContrato.getIdContrato();
            if (idClausulaNew != null) {
                idClausulaNew = em.getReference(idClausulaNew.getClass(), idClausulaNew.getId());
                clausulaContrato.setIdClausula(idClausulaNew);
            }
            if (idContratoNew != null) {
                idContratoNew = em.getReference(idContratoNew.getClass(), idContratoNew.getId());
                clausulaContrato.setIdContrato(idContratoNew);
            }
            clausulaContrato = em.merge(clausulaContrato);
            if (idClausulaOld != null && !idClausulaOld.equals(idClausulaNew)) {
                idClausulaOld.getClausulaContratoList().remove(clausulaContrato);
                idClausulaOld = em.merge(idClausulaOld);
            }
            if (idClausulaNew != null && !idClausulaNew.equals(idClausulaOld)) {
                idClausulaNew.getClausulaContratoList().add(clausulaContrato);
                idClausulaNew = em.merge(idClausulaNew);
            }
            if (idContratoOld != null && !idContratoOld.equals(idContratoNew)) {
                idContratoOld.getClausulaContratoList().remove(clausulaContrato);
                idContratoOld = em.merge(idContratoOld);
            }
            if (idContratoNew != null && !idContratoNew.equals(idContratoOld)) {
                idContratoNew.getClausulaContratoList().add(clausulaContrato);
                idContratoNew = em.merge(idContratoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clausulaContrato.getId();
                if (findClausulaContrato(id) == null) {
                    throw new NonexistentEntityException("The clausulaContrato with id " + id + " no longer exists.");
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
            ClausulaContrato clausulaContrato;
            try {
                clausulaContrato = em.getReference(ClausulaContrato.class, id);
                clausulaContrato.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clausulaContrato with id " + id + " no longer exists.", enfe);
            }
            TipoClausulaContrato idClausula = clausulaContrato.getIdClausula();
            if (idClausula != null) {
                idClausula.getClausulaContratoList().remove(clausulaContrato);
                idClausula = em.merge(idClausula);
            }
            ContratoTercero idContrato = clausulaContrato.getIdContrato();
            if (idContrato != null) {
                idContrato.getClausulaContratoList().remove(clausulaContrato);
                idContrato = em.merge(idContrato);
            }
            em.remove(clausulaContrato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClausulaContrato> findClausulaContratoEntities() {
        return findClausulaContratoEntities(true, -1, -1);
    }

    public List<ClausulaContrato> findClausulaContratoEntities(int maxResults, int firstResult) {
        return findClausulaContratoEntities(false, maxResults, firstResult);
    }

    private List<ClausulaContrato> findClausulaContratoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClausulaContrato.class));
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

    public ClausulaContrato findClausulaContrato(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClausulaContrato.class, id);
        } finally {
            em.close();
        }
    }

    public int getClausulaContratoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClausulaContrato> rt = cq.from(ClausulaContrato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
