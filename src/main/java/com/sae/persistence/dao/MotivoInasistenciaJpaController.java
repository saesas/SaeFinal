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
import com.sae.persistence.domain.InasistenciaContrato;
import com.sae.persistence.domain.MotivoInasistencia;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class MotivoInasistenciaJpaController implements Serializable {

    public MotivoInasistenciaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MotivoInasistencia motivoInasistencia) throws PreexistingEntityException, Exception {
        if (motivoInasistencia.getInasistenciaContratoList() == null) {
            motivoInasistencia.setInasistenciaContratoList(new ArrayList<InasistenciaContrato>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<InasistenciaContrato> attachedInasistenciaContratoList = new ArrayList<InasistenciaContrato>();
            for (InasistenciaContrato inasistenciaContratoListInasistenciaContratoToAttach : motivoInasistencia.getInasistenciaContratoList()) {
                inasistenciaContratoListInasistenciaContratoToAttach = em.getReference(inasistenciaContratoListInasistenciaContratoToAttach.getClass(), inasistenciaContratoListInasistenciaContratoToAttach.getId());
                attachedInasistenciaContratoList.add(inasistenciaContratoListInasistenciaContratoToAttach);
            }
            motivoInasistencia.setInasistenciaContratoList(attachedInasistenciaContratoList);
            em.persist(motivoInasistencia);
            for (InasistenciaContrato inasistenciaContratoListInasistenciaContrato : motivoInasistencia.getInasistenciaContratoList()) {
                MotivoInasistencia oldIdMotivoInasistenciaOfInasistenciaContratoListInasistenciaContrato = inasistenciaContratoListInasistenciaContrato.getIdMotivoInasistencia();
                inasistenciaContratoListInasistenciaContrato.setIdMotivoInasistencia(motivoInasistencia);
                inasistenciaContratoListInasistenciaContrato = em.merge(inasistenciaContratoListInasistenciaContrato);
                if (oldIdMotivoInasistenciaOfInasistenciaContratoListInasistenciaContrato != null) {
                    oldIdMotivoInasistenciaOfInasistenciaContratoListInasistenciaContrato.getInasistenciaContratoList().remove(inasistenciaContratoListInasistenciaContrato);
                    oldIdMotivoInasistenciaOfInasistenciaContratoListInasistenciaContrato = em.merge(oldIdMotivoInasistenciaOfInasistenciaContratoListInasistenciaContrato);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMotivoInasistencia(motivoInasistencia.getId()) != null) {
                throw new PreexistingEntityException("MotivoInasistencia " + motivoInasistencia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MotivoInasistencia motivoInasistencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MotivoInasistencia persistentMotivoInasistencia = em.find(MotivoInasistencia.class, motivoInasistencia.getId());
            List<InasistenciaContrato> inasistenciaContratoListOld = persistentMotivoInasistencia.getInasistenciaContratoList();
            List<InasistenciaContrato> inasistenciaContratoListNew = motivoInasistencia.getInasistenciaContratoList();
            List<InasistenciaContrato> attachedInasistenciaContratoListNew = new ArrayList<InasistenciaContrato>();
            for (InasistenciaContrato inasistenciaContratoListNewInasistenciaContratoToAttach : inasistenciaContratoListNew) {
                inasistenciaContratoListNewInasistenciaContratoToAttach = em.getReference(inasistenciaContratoListNewInasistenciaContratoToAttach.getClass(), inasistenciaContratoListNewInasistenciaContratoToAttach.getId());
                attachedInasistenciaContratoListNew.add(inasistenciaContratoListNewInasistenciaContratoToAttach);
            }
            inasistenciaContratoListNew = attachedInasistenciaContratoListNew;
            motivoInasistencia.setInasistenciaContratoList(inasistenciaContratoListNew);
            motivoInasistencia = em.merge(motivoInasistencia);
            for (InasistenciaContrato inasistenciaContratoListOldInasistenciaContrato : inasistenciaContratoListOld) {
                if (!inasistenciaContratoListNew.contains(inasistenciaContratoListOldInasistenciaContrato)) {
                    inasistenciaContratoListOldInasistenciaContrato.setIdMotivoInasistencia(null);
                    inasistenciaContratoListOldInasistenciaContrato = em.merge(inasistenciaContratoListOldInasistenciaContrato);
                }
            }
            for (InasistenciaContrato inasistenciaContratoListNewInasistenciaContrato : inasistenciaContratoListNew) {
                if (!inasistenciaContratoListOld.contains(inasistenciaContratoListNewInasistenciaContrato)) {
                    MotivoInasistencia oldIdMotivoInasistenciaOfInasistenciaContratoListNewInasistenciaContrato = inasistenciaContratoListNewInasistenciaContrato.getIdMotivoInasistencia();
                    inasistenciaContratoListNewInasistenciaContrato.setIdMotivoInasistencia(motivoInasistencia);
                    inasistenciaContratoListNewInasistenciaContrato = em.merge(inasistenciaContratoListNewInasistenciaContrato);
                    if (oldIdMotivoInasistenciaOfInasistenciaContratoListNewInasistenciaContrato != null && !oldIdMotivoInasistenciaOfInasistenciaContratoListNewInasistenciaContrato.equals(motivoInasistencia)) {
                        oldIdMotivoInasistenciaOfInasistenciaContratoListNewInasistenciaContrato.getInasistenciaContratoList().remove(inasistenciaContratoListNewInasistenciaContrato);
                        oldIdMotivoInasistenciaOfInasistenciaContratoListNewInasistenciaContrato = em.merge(oldIdMotivoInasistenciaOfInasistenciaContratoListNewInasistenciaContrato);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = motivoInasistencia.getId();
                if (findMotivoInasistencia(id) == null) {
                    throw new NonexistentEntityException("The motivoInasistencia with id " + id + " no longer exists.");
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
            MotivoInasistencia motivoInasistencia;
            try {
                motivoInasistencia = em.getReference(MotivoInasistencia.class, id);
                motivoInasistencia.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The motivoInasistencia with id " + id + " no longer exists.", enfe);
            }
            List<InasistenciaContrato> inasistenciaContratoList = motivoInasistencia.getInasistenciaContratoList();
            for (InasistenciaContrato inasistenciaContratoListInasistenciaContrato : inasistenciaContratoList) {
                inasistenciaContratoListInasistenciaContrato.setIdMotivoInasistencia(null);
                inasistenciaContratoListInasistenciaContrato = em.merge(inasistenciaContratoListInasistenciaContrato);
            }
            em.remove(motivoInasistencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MotivoInasistencia> findMotivoInasistenciaEntities() {
        return findMotivoInasistenciaEntities(true, -1, -1);
    }

    public List<MotivoInasistencia> findMotivoInasistenciaEntities(int maxResults, int firstResult) {
        return findMotivoInasistenciaEntities(false, maxResults, firstResult);
    }

    private List<MotivoInasistencia> findMotivoInasistenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MotivoInasistencia.class));
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

    public MotivoInasistencia findMotivoInasistencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MotivoInasistencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getMotivoInasistenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MotivoInasistencia> rt = cq.from(MotivoInasistencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
