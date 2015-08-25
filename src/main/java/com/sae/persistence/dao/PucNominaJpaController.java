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
import com.sae.persistence.domain.Puc;
import com.sae.persistence.domain.ConceptoNomina;
import com.sae.persistence.domain.PucNomina;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class PucNominaJpaController implements Serializable {

    public PucNominaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PucNomina pucNomina) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Puc idPucDebito = pucNomina.getIdPucDebito();
            if (idPucDebito != null) {
                idPucDebito = em.getReference(idPucDebito.getClass(), idPucDebito.getId());
                pucNomina.setIdPucDebito(idPucDebito);
            }
            Puc idPucCredito = pucNomina.getIdPucCredito();
            if (idPucCredito != null) {
                idPucCredito = em.getReference(idPucCredito.getClass(), idPucCredito.getId());
                pucNomina.setIdPucCredito(idPucCredito);
            }
            ConceptoNomina idConcepto = pucNomina.getIdConcepto();
            if (idConcepto != null) {
                idConcepto = em.getReference(idConcepto.getClass(), idConcepto.getId());
                pucNomina.setIdConcepto(idConcepto);
            }
            em.persist(pucNomina);
            if (idPucDebito != null) {
                idPucDebito.getPucNominaList().add(pucNomina);
                idPucDebito = em.merge(idPucDebito);
            }
            if (idPucCredito != null) {
                idPucCredito.getPucNominaList().add(pucNomina);
                idPucCredito = em.merge(idPucCredito);
            }
            if (idConcepto != null) {
                idConcepto.getPucNominaList().add(pucNomina);
                idConcepto = em.merge(idConcepto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPucNomina(pucNomina.getId()) != null) {
                throw new PreexistingEntityException("PucNomina " + pucNomina + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PucNomina pucNomina) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PucNomina persistentPucNomina = em.find(PucNomina.class, pucNomina.getId());
            Puc idPucDebitoOld = persistentPucNomina.getIdPucDebito();
            Puc idPucDebitoNew = pucNomina.getIdPucDebito();
            Puc idPucCreditoOld = persistentPucNomina.getIdPucCredito();
            Puc idPucCreditoNew = pucNomina.getIdPucCredito();
            ConceptoNomina idConceptoOld = persistentPucNomina.getIdConcepto();
            ConceptoNomina idConceptoNew = pucNomina.getIdConcepto();
            if (idPucDebitoNew != null) {
                idPucDebitoNew = em.getReference(idPucDebitoNew.getClass(), idPucDebitoNew.getId());
                pucNomina.setIdPucDebito(idPucDebitoNew);
            }
            if (idPucCreditoNew != null) {
                idPucCreditoNew = em.getReference(idPucCreditoNew.getClass(), idPucCreditoNew.getId());
                pucNomina.setIdPucCredito(idPucCreditoNew);
            }
            if (idConceptoNew != null) {
                idConceptoNew = em.getReference(idConceptoNew.getClass(), idConceptoNew.getId());
                pucNomina.setIdConcepto(idConceptoNew);
            }
            pucNomina = em.merge(pucNomina);
            if (idPucDebitoOld != null && !idPucDebitoOld.equals(idPucDebitoNew)) {
                idPucDebitoOld.getPucNominaList().remove(pucNomina);
                idPucDebitoOld = em.merge(idPucDebitoOld);
            }
            if (idPucDebitoNew != null && !idPucDebitoNew.equals(idPucDebitoOld)) {
                idPucDebitoNew.getPucNominaList().add(pucNomina);
                idPucDebitoNew = em.merge(idPucDebitoNew);
            }
            if (idPucCreditoOld != null && !idPucCreditoOld.equals(idPucCreditoNew)) {
                idPucCreditoOld.getPucNominaList().remove(pucNomina);
                idPucCreditoOld = em.merge(idPucCreditoOld);
            }
            if (idPucCreditoNew != null && !idPucCreditoNew.equals(idPucCreditoOld)) {
                idPucCreditoNew.getPucNominaList().add(pucNomina);
                idPucCreditoNew = em.merge(idPucCreditoNew);
            }
            if (idConceptoOld != null && !idConceptoOld.equals(idConceptoNew)) {
                idConceptoOld.getPucNominaList().remove(pucNomina);
                idConceptoOld = em.merge(idConceptoOld);
            }
            if (idConceptoNew != null && !idConceptoNew.equals(idConceptoOld)) {
                idConceptoNew.getPucNominaList().add(pucNomina);
                idConceptoNew = em.merge(idConceptoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pucNomina.getId();
                if (findPucNomina(id) == null) {
                    throw new NonexistentEntityException("The pucNomina with id " + id + " no longer exists.");
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
            PucNomina pucNomina;
            try {
                pucNomina = em.getReference(PucNomina.class, id);
                pucNomina.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pucNomina with id " + id + " no longer exists.", enfe);
            }
            Puc idPucDebito = pucNomina.getIdPucDebito();
            if (idPucDebito != null) {
                idPucDebito.getPucNominaList().remove(pucNomina);
                idPucDebito = em.merge(idPucDebito);
            }
            Puc idPucCredito = pucNomina.getIdPucCredito();
            if (idPucCredito != null) {
                idPucCredito.getPucNominaList().remove(pucNomina);
                idPucCredito = em.merge(idPucCredito);
            }
            ConceptoNomina idConcepto = pucNomina.getIdConcepto();
            if (idConcepto != null) {
                idConcepto.getPucNominaList().remove(pucNomina);
                idConcepto = em.merge(idConcepto);
            }
            em.remove(pucNomina);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PucNomina> findPucNominaEntities() {
        return findPucNominaEntities(true, -1, -1);
    }

    public List<PucNomina> findPucNominaEntities(int maxResults, int firstResult) {
        return findPucNominaEntities(false, maxResults, firstResult);
    }

    private List<PucNomina> findPucNominaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PucNomina.class));
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

    public PucNomina findPucNomina(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PucNomina.class, id);
        } finally {
            em.close();
        }
    }

    public int getPucNominaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PucNomina> rt = cq.from(PucNomina.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
