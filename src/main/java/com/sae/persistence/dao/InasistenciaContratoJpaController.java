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
import com.sae.persistence.domain.MotivoInasistencia;
import com.sae.persistence.domain.ContratoTercero;
import com.sae.persistence.domain.InasistenciaContrato;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class InasistenciaContratoJpaController implements Serializable {

    public InasistenciaContratoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InasistenciaContrato inasistenciaContrato) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MotivoInasistencia idMotivoInasistencia = inasistenciaContrato.getIdMotivoInasistencia();
            if (idMotivoInasistencia != null) {
                idMotivoInasistencia = em.getReference(idMotivoInasistencia.getClass(), idMotivoInasistencia.getId());
                inasistenciaContrato.setIdMotivoInasistencia(idMotivoInasistencia);
            }
            ContratoTercero idContrato = inasistenciaContrato.getIdContrato();
            if (idContrato != null) {
                idContrato = em.getReference(idContrato.getClass(), idContrato.getId());
                inasistenciaContrato.setIdContrato(idContrato);
            }
            em.persist(inasistenciaContrato);
            if (idMotivoInasistencia != null) {
                idMotivoInasistencia.getInasistenciaContratoList().add(inasistenciaContrato);
                idMotivoInasistencia = em.merge(idMotivoInasistencia);
            }
            if (idContrato != null) {
                idContrato.getInasistenciaContratoList().add(inasistenciaContrato);
                idContrato = em.merge(idContrato);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInasistenciaContrato(inasistenciaContrato.getId()) != null) {
                throw new PreexistingEntityException("InasistenciaContrato " + inasistenciaContrato + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InasistenciaContrato inasistenciaContrato) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InasistenciaContrato persistentInasistenciaContrato = em.find(InasistenciaContrato.class, inasistenciaContrato.getId());
            MotivoInasistencia idMotivoInasistenciaOld = persistentInasistenciaContrato.getIdMotivoInasistencia();
            MotivoInasistencia idMotivoInasistenciaNew = inasistenciaContrato.getIdMotivoInasistencia();
            ContratoTercero idContratoOld = persistentInasistenciaContrato.getIdContrato();
            ContratoTercero idContratoNew = inasistenciaContrato.getIdContrato();
            if (idMotivoInasistenciaNew != null) {
                idMotivoInasistenciaNew = em.getReference(idMotivoInasistenciaNew.getClass(), idMotivoInasistenciaNew.getId());
                inasistenciaContrato.setIdMotivoInasistencia(idMotivoInasistenciaNew);
            }
            if (idContratoNew != null) {
                idContratoNew = em.getReference(idContratoNew.getClass(), idContratoNew.getId());
                inasistenciaContrato.setIdContrato(idContratoNew);
            }
            inasistenciaContrato = em.merge(inasistenciaContrato);
            if (idMotivoInasistenciaOld != null && !idMotivoInasistenciaOld.equals(idMotivoInasistenciaNew)) {
                idMotivoInasistenciaOld.getInasistenciaContratoList().remove(inasistenciaContrato);
                idMotivoInasistenciaOld = em.merge(idMotivoInasistenciaOld);
            }
            if (idMotivoInasistenciaNew != null && !idMotivoInasistenciaNew.equals(idMotivoInasistenciaOld)) {
                idMotivoInasistenciaNew.getInasistenciaContratoList().add(inasistenciaContrato);
                idMotivoInasistenciaNew = em.merge(idMotivoInasistenciaNew);
            }
            if (idContratoOld != null && !idContratoOld.equals(idContratoNew)) {
                idContratoOld.getInasistenciaContratoList().remove(inasistenciaContrato);
                idContratoOld = em.merge(idContratoOld);
            }
            if (idContratoNew != null && !idContratoNew.equals(idContratoOld)) {
                idContratoNew.getInasistenciaContratoList().add(inasistenciaContrato);
                idContratoNew = em.merge(idContratoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = inasistenciaContrato.getId();
                if (findInasistenciaContrato(id) == null) {
                    throw new NonexistentEntityException("The inasistenciaContrato with id " + id + " no longer exists.");
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
            InasistenciaContrato inasistenciaContrato;
            try {
                inasistenciaContrato = em.getReference(InasistenciaContrato.class, id);
                inasistenciaContrato.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inasistenciaContrato with id " + id + " no longer exists.", enfe);
            }
            MotivoInasistencia idMotivoInasistencia = inasistenciaContrato.getIdMotivoInasistencia();
            if (idMotivoInasistencia != null) {
                idMotivoInasistencia.getInasistenciaContratoList().remove(inasistenciaContrato);
                idMotivoInasistencia = em.merge(idMotivoInasistencia);
            }
            ContratoTercero idContrato = inasistenciaContrato.getIdContrato();
            if (idContrato != null) {
                idContrato.getInasistenciaContratoList().remove(inasistenciaContrato);
                idContrato = em.merge(idContrato);
            }
            em.remove(inasistenciaContrato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<InasistenciaContrato> findInasistenciaContratoEntities() {
        return findInasistenciaContratoEntities(true, -1, -1);
    }

    public List<InasistenciaContrato> findInasistenciaContratoEntities(int maxResults, int firstResult) {
        return findInasistenciaContratoEntities(false, maxResults, firstResult);
    }

    private List<InasistenciaContrato> findInasistenciaContratoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InasistenciaContrato.class));
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

    public InasistenciaContrato findInasistenciaContrato(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InasistenciaContrato.class, id);
        } finally {
            em.close();
        }
    }

    public int getInasistenciaContratoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InasistenciaContrato> rt = cq.from(InasistenciaContrato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
