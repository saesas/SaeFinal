/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.ActividadConcepto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.ConceptoRetencion;
import com.sae.persistence.domain.ActividadEconomica;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ActividadConceptoJpaController implements Serializable {

    public ActividadConceptoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ActividadConcepto actividadConcepto) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ConceptoRetencion idConcepto = actividadConcepto.getIdConcepto();
            if (idConcepto != null) {
                idConcepto = em.getReference(idConcepto.getClass(), idConcepto.getId());
                actividadConcepto.setIdConcepto(idConcepto);
            }
            ActividadEconomica idActividadEconomica = actividadConcepto.getIdActividadEconomica();
            if (idActividadEconomica != null) {
                idActividadEconomica = em.getReference(idActividadEconomica.getClass(), idActividadEconomica.getId());
                actividadConcepto.setIdActividadEconomica(idActividadEconomica);
            }
            em.persist(actividadConcepto);
            if (idConcepto != null) {
                idConcepto.getActividadConceptoList().add(actividadConcepto);
                idConcepto = em.merge(idConcepto);
            }
            if (idActividadEconomica != null) {
                idActividadEconomica.getActividadConceptoList().add(actividadConcepto);
                idActividadEconomica = em.merge(idActividadEconomica);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findActividadConcepto(actividadConcepto.getId()) != null) {
                throw new PreexistingEntityException("ActividadConcepto " + actividadConcepto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ActividadConcepto actividadConcepto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ActividadConcepto persistentActividadConcepto = em.find(ActividadConcepto.class, actividadConcepto.getId());
            ConceptoRetencion idConceptoOld = persistentActividadConcepto.getIdConcepto();
            ConceptoRetencion idConceptoNew = actividadConcepto.getIdConcepto();
            ActividadEconomica idActividadEconomicaOld = persistentActividadConcepto.getIdActividadEconomica();
            ActividadEconomica idActividadEconomicaNew = actividadConcepto.getIdActividadEconomica();
            if (idConceptoNew != null) {
                idConceptoNew = em.getReference(idConceptoNew.getClass(), idConceptoNew.getId());
                actividadConcepto.setIdConcepto(idConceptoNew);
            }
            if (idActividadEconomicaNew != null) {
                idActividadEconomicaNew = em.getReference(idActividadEconomicaNew.getClass(), idActividadEconomicaNew.getId());
                actividadConcepto.setIdActividadEconomica(idActividadEconomicaNew);
            }
            actividadConcepto = em.merge(actividadConcepto);
            if (idConceptoOld != null && !idConceptoOld.equals(idConceptoNew)) {
                idConceptoOld.getActividadConceptoList().remove(actividadConcepto);
                idConceptoOld = em.merge(idConceptoOld);
            }
            if (idConceptoNew != null && !idConceptoNew.equals(idConceptoOld)) {
                idConceptoNew.getActividadConceptoList().add(actividadConcepto);
                idConceptoNew = em.merge(idConceptoNew);
            }
            if (idActividadEconomicaOld != null && !idActividadEconomicaOld.equals(idActividadEconomicaNew)) {
                idActividadEconomicaOld.getActividadConceptoList().remove(actividadConcepto);
                idActividadEconomicaOld = em.merge(idActividadEconomicaOld);
            }
            if (idActividadEconomicaNew != null && !idActividadEconomicaNew.equals(idActividadEconomicaOld)) {
                idActividadEconomicaNew.getActividadConceptoList().add(actividadConcepto);
                idActividadEconomicaNew = em.merge(idActividadEconomicaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = actividadConcepto.getId();
                if (findActividadConcepto(id) == null) {
                    throw new NonexistentEntityException("The actividadConcepto with id " + id + " no longer exists.");
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
            ActividadConcepto actividadConcepto;
            try {
                actividadConcepto = em.getReference(ActividadConcepto.class, id);
                actividadConcepto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actividadConcepto with id " + id + " no longer exists.", enfe);
            }
            ConceptoRetencion idConcepto = actividadConcepto.getIdConcepto();
            if (idConcepto != null) {
                idConcepto.getActividadConceptoList().remove(actividadConcepto);
                idConcepto = em.merge(idConcepto);
            }
            ActividadEconomica idActividadEconomica = actividadConcepto.getIdActividadEconomica();
            if (idActividadEconomica != null) {
                idActividadEconomica.getActividadConceptoList().remove(actividadConcepto);
                idActividadEconomica = em.merge(idActividadEconomica);
            }
            em.remove(actividadConcepto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ActividadConcepto> findActividadConceptoEntities() {
        return findActividadConceptoEntities(true, -1, -1);
    }

    public List<ActividadConcepto> findActividadConceptoEntities(int maxResults, int firstResult) {
        return findActividadConceptoEntities(false, maxResults, firstResult);
    }

    private List<ActividadConcepto> findActividadConceptoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ActividadConcepto.class));
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

    public ActividadConcepto findActividadConcepto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ActividadConcepto.class, id);
        } finally {
            em.close();
        }
    }

    public int getActividadConceptoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ActividadConcepto> rt = cq.from(ActividadConcepto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
