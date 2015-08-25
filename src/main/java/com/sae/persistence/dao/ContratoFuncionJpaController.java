/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.ContratoFuncion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.Funcion;
import com.sae.persistence.domain.ContratoTercero;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ContratoFuncionJpaController implements Serializable {

    public ContratoFuncionJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ContratoFuncion contratoFuncion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcion idFuncion = contratoFuncion.getIdFuncion();
            if (idFuncion != null) {
                idFuncion = em.getReference(idFuncion.getClass(), idFuncion.getId());
                contratoFuncion.setIdFuncion(idFuncion);
            }
            ContratoTercero idContrato = contratoFuncion.getIdContrato();
            if (idContrato != null) {
                idContrato = em.getReference(idContrato.getClass(), idContrato.getId());
                contratoFuncion.setIdContrato(idContrato);
            }
            em.persist(contratoFuncion);
            if (idFuncion != null) {
                idFuncion.getContratoFuncionList().add(contratoFuncion);
                idFuncion = em.merge(idFuncion);
            }
            if (idContrato != null) {
                idContrato.getContratoFuncionList().add(contratoFuncion);
                idContrato = em.merge(idContrato);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findContratoFuncion(contratoFuncion.getId()) != null) {
                throw new PreexistingEntityException("ContratoFuncion " + contratoFuncion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ContratoFuncion contratoFuncion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ContratoFuncion persistentContratoFuncion = em.find(ContratoFuncion.class, contratoFuncion.getId());
            Funcion idFuncionOld = persistentContratoFuncion.getIdFuncion();
            Funcion idFuncionNew = contratoFuncion.getIdFuncion();
            ContratoTercero idContratoOld = persistentContratoFuncion.getIdContrato();
            ContratoTercero idContratoNew = contratoFuncion.getIdContrato();
            if (idFuncionNew != null) {
                idFuncionNew = em.getReference(idFuncionNew.getClass(), idFuncionNew.getId());
                contratoFuncion.setIdFuncion(idFuncionNew);
            }
            if (idContratoNew != null) {
                idContratoNew = em.getReference(idContratoNew.getClass(), idContratoNew.getId());
                contratoFuncion.setIdContrato(idContratoNew);
            }
            contratoFuncion = em.merge(contratoFuncion);
            if (idFuncionOld != null && !idFuncionOld.equals(idFuncionNew)) {
                idFuncionOld.getContratoFuncionList().remove(contratoFuncion);
                idFuncionOld = em.merge(idFuncionOld);
            }
            if (idFuncionNew != null && !idFuncionNew.equals(idFuncionOld)) {
                idFuncionNew.getContratoFuncionList().add(contratoFuncion);
                idFuncionNew = em.merge(idFuncionNew);
            }
            if (idContratoOld != null && !idContratoOld.equals(idContratoNew)) {
                idContratoOld.getContratoFuncionList().remove(contratoFuncion);
                idContratoOld = em.merge(idContratoOld);
            }
            if (idContratoNew != null && !idContratoNew.equals(idContratoOld)) {
                idContratoNew.getContratoFuncionList().add(contratoFuncion);
                idContratoNew = em.merge(idContratoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contratoFuncion.getId();
                if (findContratoFuncion(id) == null) {
                    throw new NonexistentEntityException("The contratoFuncion with id " + id + " no longer exists.");
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
            ContratoFuncion contratoFuncion;
            try {
                contratoFuncion = em.getReference(ContratoFuncion.class, id);
                contratoFuncion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contratoFuncion with id " + id + " no longer exists.", enfe);
            }
            Funcion idFuncion = contratoFuncion.getIdFuncion();
            if (idFuncion != null) {
                idFuncion.getContratoFuncionList().remove(contratoFuncion);
                idFuncion = em.merge(idFuncion);
            }
            ContratoTercero idContrato = contratoFuncion.getIdContrato();
            if (idContrato != null) {
                idContrato.getContratoFuncionList().remove(contratoFuncion);
                idContrato = em.merge(idContrato);
            }
            em.remove(contratoFuncion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ContratoFuncion> findContratoFuncionEntities() {
        return findContratoFuncionEntities(true, -1, -1);
    }

    public List<ContratoFuncion> findContratoFuncionEntities(int maxResults, int firstResult) {
        return findContratoFuncionEntities(false, maxResults, firstResult);
    }

    private List<ContratoFuncion> findContratoFuncionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ContratoFuncion.class));
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

    public ContratoFuncion findContratoFuncion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ContratoFuncion.class, id);
        } finally {
            em.close();
        }
    }

    public int getContratoFuncionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ContratoFuncion> rt = cq.from(ContratoFuncion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
