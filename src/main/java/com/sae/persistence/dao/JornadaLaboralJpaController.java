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
import com.sae.persistence.domain.JornadaLaboral;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class JornadaLaboralJpaController implements Serializable {

    public JornadaLaboralJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(JornadaLaboral jornadaLaboral) throws PreexistingEntityException, Exception {
        if (jornadaLaboral.getContratoTerceroList() == null) {
            jornadaLaboral.setContratoTerceroList(new ArrayList<ContratoTercero>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ContratoTercero> attachedContratoTerceroList = new ArrayList<ContratoTercero>();
            for (ContratoTercero contratoTerceroListContratoTerceroToAttach : jornadaLaboral.getContratoTerceroList()) {
                contratoTerceroListContratoTerceroToAttach = em.getReference(contratoTerceroListContratoTerceroToAttach.getClass(), contratoTerceroListContratoTerceroToAttach.getId());
                attachedContratoTerceroList.add(contratoTerceroListContratoTerceroToAttach);
            }
            jornadaLaboral.setContratoTerceroList(attachedContratoTerceroList);
            em.persist(jornadaLaboral);
            for (ContratoTercero contratoTerceroListContratoTercero : jornadaLaboral.getContratoTerceroList()) {
                JornadaLaboral oldIdJornadaLaboralOfContratoTerceroListContratoTercero = contratoTerceroListContratoTercero.getIdJornadaLaboral();
                contratoTerceroListContratoTercero.setIdJornadaLaboral(jornadaLaboral);
                contratoTerceroListContratoTercero = em.merge(contratoTerceroListContratoTercero);
                if (oldIdJornadaLaboralOfContratoTerceroListContratoTercero != null) {
                    oldIdJornadaLaboralOfContratoTerceroListContratoTercero.getContratoTerceroList().remove(contratoTerceroListContratoTercero);
                    oldIdJornadaLaboralOfContratoTerceroListContratoTercero = em.merge(oldIdJornadaLaboralOfContratoTerceroListContratoTercero);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findJornadaLaboral(jornadaLaboral.getId()) != null) {
                throw new PreexistingEntityException("JornadaLaboral " + jornadaLaboral + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(JornadaLaboral jornadaLaboral) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            JornadaLaboral persistentJornadaLaboral = em.find(JornadaLaboral.class, jornadaLaboral.getId());
            List<ContratoTercero> contratoTerceroListOld = persistentJornadaLaboral.getContratoTerceroList();
            List<ContratoTercero> contratoTerceroListNew = jornadaLaboral.getContratoTerceroList();
            List<ContratoTercero> attachedContratoTerceroListNew = new ArrayList<ContratoTercero>();
            for (ContratoTercero contratoTerceroListNewContratoTerceroToAttach : contratoTerceroListNew) {
                contratoTerceroListNewContratoTerceroToAttach = em.getReference(contratoTerceroListNewContratoTerceroToAttach.getClass(), contratoTerceroListNewContratoTerceroToAttach.getId());
                attachedContratoTerceroListNew.add(contratoTerceroListNewContratoTerceroToAttach);
            }
            contratoTerceroListNew = attachedContratoTerceroListNew;
            jornadaLaboral.setContratoTerceroList(contratoTerceroListNew);
            jornadaLaboral = em.merge(jornadaLaboral);
            for (ContratoTercero contratoTerceroListOldContratoTercero : contratoTerceroListOld) {
                if (!contratoTerceroListNew.contains(contratoTerceroListOldContratoTercero)) {
                    contratoTerceroListOldContratoTercero.setIdJornadaLaboral(null);
                    contratoTerceroListOldContratoTercero = em.merge(contratoTerceroListOldContratoTercero);
                }
            }
            for (ContratoTercero contratoTerceroListNewContratoTercero : contratoTerceroListNew) {
                if (!contratoTerceroListOld.contains(contratoTerceroListNewContratoTercero)) {
                    JornadaLaboral oldIdJornadaLaboralOfContratoTerceroListNewContratoTercero = contratoTerceroListNewContratoTercero.getIdJornadaLaboral();
                    contratoTerceroListNewContratoTercero.setIdJornadaLaboral(jornadaLaboral);
                    contratoTerceroListNewContratoTercero = em.merge(contratoTerceroListNewContratoTercero);
                    if (oldIdJornadaLaboralOfContratoTerceroListNewContratoTercero != null && !oldIdJornadaLaboralOfContratoTerceroListNewContratoTercero.equals(jornadaLaboral)) {
                        oldIdJornadaLaboralOfContratoTerceroListNewContratoTercero.getContratoTerceroList().remove(contratoTerceroListNewContratoTercero);
                        oldIdJornadaLaboralOfContratoTerceroListNewContratoTercero = em.merge(oldIdJornadaLaboralOfContratoTerceroListNewContratoTercero);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = jornadaLaboral.getId();
                if (findJornadaLaboral(id) == null) {
                    throw new NonexistentEntityException("The jornadaLaboral with id " + id + " no longer exists.");
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
            JornadaLaboral jornadaLaboral;
            try {
                jornadaLaboral = em.getReference(JornadaLaboral.class, id);
                jornadaLaboral.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jornadaLaboral with id " + id + " no longer exists.", enfe);
            }
            List<ContratoTercero> contratoTerceroList = jornadaLaboral.getContratoTerceroList();
            for (ContratoTercero contratoTerceroListContratoTercero : contratoTerceroList) {
                contratoTerceroListContratoTercero.setIdJornadaLaboral(null);
                contratoTerceroListContratoTercero = em.merge(contratoTerceroListContratoTercero);
            }
            em.remove(jornadaLaboral);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<JornadaLaboral> findJornadaLaboralEntities() {
        return findJornadaLaboralEntities(true, -1, -1);
    }

    public List<JornadaLaboral> findJornadaLaboralEntities(int maxResults, int firstResult) {
        return findJornadaLaboralEntities(false, maxResults, firstResult);
    }

    private List<JornadaLaboral> findJornadaLaboralEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(JornadaLaboral.class));
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

    public JornadaLaboral findJornadaLaboral(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(JornadaLaboral.class, id);
        } finally {
            em.close();
        }
    }

    public int getJornadaLaboralCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<JornadaLaboral> rt = cq.from(JornadaLaboral.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
