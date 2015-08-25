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
import com.sae.persistence.domain.ActividadConcepto;
import com.sae.persistence.domain.ActividadEconomica;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ActividadEconomicaJpaController implements Serializable {

    public ActividadEconomicaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ActividadEconomica actividadEconomica) throws PreexistingEntityException, Exception {
        if (actividadEconomica.getActividadConceptoList() == null) {
            actividadEconomica.setActividadConceptoList(new ArrayList<ActividadConcepto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ActividadConcepto> attachedActividadConceptoList = new ArrayList<ActividadConcepto>();
            for (ActividadConcepto actividadConceptoListActividadConceptoToAttach : actividadEconomica.getActividadConceptoList()) {
                actividadConceptoListActividadConceptoToAttach = em.getReference(actividadConceptoListActividadConceptoToAttach.getClass(), actividadConceptoListActividadConceptoToAttach.getId());
                attachedActividadConceptoList.add(actividadConceptoListActividadConceptoToAttach);
            }
            actividadEconomica.setActividadConceptoList(attachedActividadConceptoList);
            em.persist(actividadEconomica);
            for (ActividadConcepto actividadConceptoListActividadConcepto : actividadEconomica.getActividadConceptoList()) {
                ActividadEconomica oldIdActividadEconomicaOfActividadConceptoListActividadConcepto = actividadConceptoListActividadConcepto.getIdActividadEconomica();
                actividadConceptoListActividadConcepto.setIdActividadEconomica(actividadEconomica);
                actividadConceptoListActividadConcepto = em.merge(actividadConceptoListActividadConcepto);
                if (oldIdActividadEconomicaOfActividadConceptoListActividadConcepto != null) {
                    oldIdActividadEconomicaOfActividadConceptoListActividadConcepto.getActividadConceptoList().remove(actividadConceptoListActividadConcepto);
                    oldIdActividadEconomicaOfActividadConceptoListActividadConcepto = em.merge(oldIdActividadEconomicaOfActividadConceptoListActividadConcepto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findActividadEconomica(actividadEconomica.getId()) != null) {
                throw new PreexistingEntityException("ActividadEconomica " + actividadEconomica + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ActividadEconomica actividadEconomica) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ActividadEconomica persistentActividadEconomica = em.find(ActividadEconomica.class, actividadEconomica.getId());
            List<ActividadConcepto> actividadConceptoListOld = persistentActividadEconomica.getActividadConceptoList();
            List<ActividadConcepto> actividadConceptoListNew = actividadEconomica.getActividadConceptoList();
            List<ActividadConcepto> attachedActividadConceptoListNew = new ArrayList<ActividadConcepto>();
            for (ActividadConcepto actividadConceptoListNewActividadConceptoToAttach : actividadConceptoListNew) {
                actividadConceptoListNewActividadConceptoToAttach = em.getReference(actividadConceptoListNewActividadConceptoToAttach.getClass(), actividadConceptoListNewActividadConceptoToAttach.getId());
                attachedActividadConceptoListNew.add(actividadConceptoListNewActividadConceptoToAttach);
            }
            actividadConceptoListNew = attachedActividadConceptoListNew;
            actividadEconomica.setActividadConceptoList(actividadConceptoListNew);
            actividadEconomica = em.merge(actividadEconomica);
            for (ActividadConcepto actividadConceptoListOldActividadConcepto : actividadConceptoListOld) {
                if (!actividadConceptoListNew.contains(actividadConceptoListOldActividadConcepto)) {
                    actividadConceptoListOldActividadConcepto.setIdActividadEconomica(null);
                    actividadConceptoListOldActividadConcepto = em.merge(actividadConceptoListOldActividadConcepto);
                }
            }
            for (ActividadConcepto actividadConceptoListNewActividadConcepto : actividadConceptoListNew) {
                if (!actividadConceptoListOld.contains(actividadConceptoListNewActividadConcepto)) {
                    ActividadEconomica oldIdActividadEconomicaOfActividadConceptoListNewActividadConcepto = actividadConceptoListNewActividadConcepto.getIdActividadEconomica();
                    actividadConceptoListNewActividadConcepto.setIdActividadEconomica(actividadEconomica);
                    actividadConceptoListNewActividadConcepto = em.merge(actividadConceptoListNewActividadConcepto);
                    if (oldIdActividadEconomicaOfActividadConceptoListNewActividadConcepto != null && !oldIdActividadEconomicaOfActividadConceptoListNewActividadConcepto.equals(actividadEconomica)) {
                        oldIdActividadEconomicaOfActividadConceptoListNewActividadConcepto.getActividadConceptoList().remove(actividadConceptoListNewActividadConcepto);
                        oldIdActividadEconomicaOfActividadConceptoListNewActividadConcepto = em.merge(oldIdActividadEconomicaOfActividadConceptoListNewActividadConcepto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = actividadEconomica.getId();
                if (findActividadEconomica(id) == null) {
                    throw new NonexistentEntityException("The actividadEconomica with id " + id + " no longer exists.");
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
            ActividadEconomica actividadEconomica;
            try {
                actividadEconomica = em.getReference(ActividadEconomica.class, id);
                actividadEconomica.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actividadEconomica with id " + id + " no longer exists.", enfe);
            }
            List<ActividadConcepto> actividadConceptoList = actividadEconomica.getActividadConceptoList();
            for (ActividadConcepto actividadConceptoListActividadConcepto : actividadConceptoList) {
                actividadConceptoListActividadConcepto.setIdActividadEconomica(null);
                actividadConceptoListActividadConcepto = em.merge(actividadConceptoListActividadConcepto);
            }
            em.remove(actividadEconomica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ActividadEconomica> findActividadEconomicaEntities() {
        return findActividadEconomicaEntities(true, -1, -1);
    }

    public List<ActividadEconomica> findActividadEconomicaEntities(int maxResults, int firstResult) {
        return findActividadEconomicaEntities(false, maxResults, firstResult);
    }

    private List<ActividadEconomica> findActividadEconomicaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ActividadEconomica.class));
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

    public ActividadEconomica findActividadEconomica(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ActividadEconomica.class, id);
        } finally {
            em.close();
        }
    }

    public int getActividadEconomicaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ActividadEconomica> rt = cq.from(ActividadEconomica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
