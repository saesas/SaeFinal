/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.ConceptoNomina;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.PucNomina;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ConceptoNominaJpaController implements Serializable {

    public ConceptoNominaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConceptoNomina conceptoNomina) throws PreexistingEntityException, Exception {
        if (conceptoNomina.getPucNominaList() == null) {
            conceptoNomina.setPucNominaList(new ArrayList<PucNomina>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PucNomina> attachedPucNominaList = new ArrayList<PucNomina>();
            for (PucNomina pucNominaListPucNominaToAttach : conceptoNomina.getPucNominaList()) {
                pucNominaListPucNominaToAttach = em.getReference(pucNominaListPucNominaToAttach.getClass(), pucNominaListPucNominaToAttach.getId());
                attachedPucNominaList.add(pucNominaListPucNominaToAttach);
            }
            conceptoNomina.setPucNominaList(attachedPucNominaList);
            em.persist(conceptoNomina);
            for (PucNomina pucNominaListPucNomina : conceptoNomina.getPucNominaList()) {
                ConceptoNomina oldIdConceptoOfPucNominaListPucNomina = pucNominaListPucNomina.getIdConcepto();
                pucNominaListPucNomina.setIdConcepto(conceptoNomina);
                pucNominaListPucNomina = em.merge(pucNominaListPucNomina);
                if (oldIdConceptoOfPucNominaListPucNomina != null) {
                    oldIdConceptoOfPucNominaListPucNomina.getPucNominaList().remove(pucNominaListPucNomina);
                    oldIdConceptoOfPucNominaListPucNomina = em.merge(oldIdConceptoOfPucNominaListPucNomina);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findConceptoNomina(conceptoNomina.getId()) != null) {
                throw new PreexistingEntityException("ConceptoNomina " + conceptoNomina + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConceptoNomina conceptoNomina) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ConceptoNomina persistentConceptoNomina = em.find(ConceptoNomina.class, conceptoNomina.getId());
            List<PucNomina> pucNominaListOld = persistentConceptoNomina.getPucNominaList();
            List<PucNomina> pucNominaListNew = conceptoNomina.getPucNominaList();
            List<PucNomina> attachedPucNominaListNew = new ArrayList<PucNomina>();
            for (PucNomina pucNominaListNewPucNominaToAttach : pucNominaListNew) {
                pucNominaListNewPucNominaToAttach = em.getReference(pucNominaListNewPucNominaToAttach.getClass(), pucNominaListNewPucNominaToAttach.getId());
                attachedPucNominaListNew.add(pucNominaListNewPucNominaToAttach);
            }
            pucNominaListNew = attachedPucNominaListNew;
            conceptoNomina.setPucNominaList(pucNominaListNew);
            conceptoNomina = em.merge(conceptoNomina);
            for (PucNomina pucNominaListOldPucNomina : pucNominaListOld) {
                if (!pucNominaListNew.contains(pucNominaListOldPucNomina)) {
                    pucNominaListOldPucNomina.setIdConcepto(null);
                    pucNominaListOldPucNomina = em.merge(pucNominaListOldPucNomina);
                }
            }
            for (PucNomina pucNominaListNewPucNomina : pucNominaListNew) {
                if (!pucNominaListOld.contains(pucNominaListNewPucNomina)) {
                    ConceptoNomina oldIdConceptoOfPucNominaListNewPucNomina = pucNominaListNewPucNomina.getIdConcepto();
                    pucNominaListNewPucNomina.setIdConcepto(conceptoNomina);
                    pucNominaListNewPucNomina = em.merge(pucNominaListNewPucNomina);
                    if (oldIdConceptoOfPucNominaListNewPucNomina != null && !oldIdConceptoOfPucNominaListNewPucNomina.equals(conceptoNomina)) {
                        oldIdConceptoOfPucNominaListNewPucNomina.getPucNominaList().remove(pucNominaListNewPucNomina);
                        oldIdConceptoOfPucNominaListNewPucNomina = em.merge(oldIdConceptoOfPucNominaListNewPucNomina);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = conceptoNomina.getId();
                if (findConceptoNomina(id) == null) {
                    throw new NonexistentEntityException("The conceptoNomina with id " + id + " no longer exists.");
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
            ConceptoNomina conceptoNomina;
            try {
                conceptoNomina = em.getReference(ConceptoNomina.class, id);
                conceptoNomina.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The conceptoNomina with id " + id + " no longer exists.", enfe);
            }
            List<PucNomina> pucNominaList = conceptoNomina.getPucNominaList();
            for (PucNomina pucNominaListPucNomina : pucNominaList) {
                pucNominaListPucNomina.setIdConcepto(null);
                pucNominaListPucNomina = em.merge(pucNominaListPucNomina);
            }
            em.remove(conceptoNomina);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ConceptoNomina> findConceptoNominaEntities() {
        return findConceptoNominaEntities(true, -1, -1);
    }

    public List<ConceptoNomina> findConceptoNominaEntities(int maxResults, int firstResult) {
        return findConceptoNominaEntities(false, maxResults, firstResult);
    }

    private List<ConceptoNomina> findConceptoNominaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConceptoNomina.class));
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

    public ConceptoNomina findConceptoNomina(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConceptoNomina.class, id);
        } finally {
            em.close();
        }
    }

    public int getConceptoNominaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConceptoNomina> rt = cq.from(ConceptoNomina.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
