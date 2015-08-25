/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.AplicacionConcepto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.ConceptoRetencion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class AplicacionConceptoJpaController implements Serializable {

    public AplicacionConceptoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AplicacionConcepto aplicacionConcepto) throws PreexistingEntityException, Exception {
        if (aplicacionConcepto.getConceptoRetencionList() == null) {
            aplicacionConcepto.setConceptoRetencionList(new ArrayList<ConceptoRetencion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ConceptoRetencion> attachedConceptoRetencionList = new ArrayList<ConceptoRetencion>();
            for (ConceptoRetencion conceptoRetencionListConceptoRetencionToAttach : aplicacionConcepto.getConceptoRetencionList()) {
                conceptoRetencionListConceptoRetencionToAttach = em.getReference(conceptoRetencionListConceptoRetencionToAttach.getClass(), conceptoRetencionListConceptoRetencionToAttach.getId());
                attachedConceptoRetencionList.add(conceptoRetencionListConceptoRetencionToAttach);
            }
            aplicacionConcepto.setConceptoRetencionList(attachedConceptoRetencionList);
            em.persist(aplicacionConcepto);
            for (ConceptoRetencion conceptoRetencionListConceptoRetencion : aplicacionConcepto.getConceptoRetencionList()) {
                AplicacionConcepto oldIdAplicacionConceptoOfConceptoRetencionListConceptoRetencion = conceptoRetencionListConceptoRetencion.getIdAplicacionConcepto();
                conceptoRetencionListConceptoRetencion.setIdAplicacionConcepto(aplicacionConcepto);
                conceptoRetencionListConceptoRetencion = em.merge(conceptoRetencionListConceptoRetencion);
                if (oldIdAplicacionConceptoOfConceptoRetencionListConceptoRetencion != null) {
                    oldIdAplicacionConceptoOfConceptoRetencionListConceptoRetencion.getConceptoRetencionList().remove(conceptoRetencionListConceptoRetencion);
                    oldIdAplicacionConceptoOfConceptoRetencionListConceptoRetencion = em.merge(oldIdAplicacionConceptoOfConceptoRetencionListConceptoRetencion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAplicacionConcepto(aplicacionConcepto.getId()) != null) {
                throw new PreexistingEntityException("AplicacionConcepto " + aplicacionConcepto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AplicacionConcepto aplicacionConcepto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AplicacionConcepto persistentAplicacionConcepto = em.find(AplicacionConcepto.class, aplicacionConcepto.getId());
            List<ConceptoRetencion> conceptoRetencionListOld = persistentAplicacionConcepto.getConceptoRetencionList();
            List<ConceptoRetencion> conceptoRetencionListNew = aplicacionConcepto.getConceptoRetencionList();
            List<ConceptoRetencion> attachedConceptoRetencionListNew = new ArrayList<ConceptoRetencion>();
            for (ConceptoRetencion conceptoRetencionListNewConceptoRetencionToAttach : conceptoRetencionListNew) {
                conceptoRetencionListNewConceptoRetencionToAttach = em.getReference(conceptoRetencionListNewConceptoRetencionToAttach.getClass(), conceptoRetencionListNewConceptoRetencionToAttach.getId());
                attachedConceptoRetencionListNew.add(conceptoRetencionListNewConceptoRetencionToAttach);
            }
            conceptoRetencionListNew = attachedConceptoRetencionListNew;
            aplicacionConcepto.setConceptoRetencionList(conceptoRetencionListNew);
            aplicacionConcepto = em.merge(aplicacionConcepto);
            for (ConceptoRetencion conceptoRetencionListOldConceptoRetencion : conceptoRetencionListOld) {
                if (!conceptoRetencionListNew.contains(conceptoRetencionListOldConceptoRetencion)) {
                    conceptoRetencionListOldConceptoRetencion.setIdAplicacionConcepto(null);
                    conceptoRetencionListOldConceptoRetencion = em.merge(conceptoRetencionListOldConceptoRetencion);
                }
            }
            for (ConceptoRetencion conceptoRetencionListNewConceptoRetencion : conceptoRetencionListNew) {
                if (!conceptoRetencionListOld.contains(conceptoRetencionListNewConceptoRetencion)) {
                    AplicacionConcepto oldIdAplicacionConceptoOfConceptoRetencionListNewConceptoRetencion = conceptoRetencionListNewConceptoRetencion.getIdAplicacionConcepto();
                    conceptoRetencionListNewConceptoRetencion.setIdAplicacionConcepto(aplicacionConcepto);
                    conceptoRetencionListNewConceptoRetencion = em.merge(conceptoRetencionListNewConceptoRetencion);
                    if (oldIdAplicacionConceptoOfConceptoRetencionListNewConceptoRetencion != null && !oldIdAplicacionConceptoOfConceptoRetencionListNewConceptoRetencion.equals(aplicacionConcepto)) {
                        oldIdAplicacionConceptoOfConceptoRetencionListNewConceptoRetencion.getConceptoRetencionList().remove(conceptoRetencionListNewConceptoRetencion);
                        oldIdAplicacionConceptoOfConceptoRetencionListNewConceptoRetencion = em.merge(oldIdAplicacionConceptoOfConceptoRetencionListNewConceptoRetencion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = aplicacionConcepto.getId();
                if (findAplicacionConcepto(id) == null) {
                    throw new NonexistentEntityException("The aplicacionConcepto with id " + id + " no longer exists.");
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
            AplicacionConcepto aplicacionConcepto;
            try {
                aplicacionConcepto = em.getReference(AplicacionConcepto.class, id);
                aplicacionConcepto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The aplicacionConcepto with id " + id + " no longer exists.", enfe);
            }
            List<ConceptoRetencion> conceptoRetencionList = aplicacionConcepto.getConceptoRetencionList();
            for (ConceptoRetencion conceptoRetencionListConceptoRetencion : conceptoRetencionList) {
                conceptoRetencionListConceptoRetencion.setIdAplicacionConcepto(null);
                conceptoRetencionListConceptoRetencion = em.merge(conceptoRetencionListConceptoRetencion);
            }
            em.remove(aplicacionConcepto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AplicacionConcepto> findAplicacionConceptoEntities() {
        return findAplicacionConceptoEntities(true, -1, -1);
    }

    public List<AplicacionConcepto> findAplicacionConceptoEntities(int maxResults, int firstResult) {
        return findAplicacionConceptoEntities(false, maxResults, firstResult);
    }

    private List<AplicacionConcepto> findAplicacionConceptoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AplicacionConcepto.class));
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

    public AplicacionConcepto findAplicacionConcepto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AplicacionConcepto.class, id);
        } finally {
            em.close();
        }
    }

    public int getAplicacionConceptoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AplicacionConcepto> rt = cq.from(AplicacionConcepto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
