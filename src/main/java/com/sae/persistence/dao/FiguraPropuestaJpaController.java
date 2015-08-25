/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.FiguraPropuesta;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.Propuesta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class FiguraPropuestaJpaController implements Serializable {

    public FiguraPropuestaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FiguraPropuesta figuraPropuesta) throws PreexistingEntityException, Exception {
        if (figuraPropuesta.getPropuestaList() == null) {
            figuraPropuesta.setPropuestaList(new ArrayList<Propuesta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Propuesta> attachedPropuestaList = new ArrayList<Propuesta>();
            for (Propuesta propuestaListPropuestaToAttach : figuraPropuesta.getPropuestaList()) {
                propuestaListPropuestaToAttach = em.getReference(propuestaListPropuestaToAttach.getClass(), propuestaListPropuestaToAttach.getId());
                attachedPropuestaList.add(propuestaListPropuestaToAttach);
            }
            figuraPropuesta.setPropuestaList(attachedPropuestaList);
            em.persist(figuraPropuesta);
            for (Propuesta propuestaListPropuesta : figuraPropuesta.getPropuestaList()) {
                FiguraPropuesta oldIdFiguraOfPropuestaListPropuesta = propuestaListPropuesta.getIdFigura();
                propuestaListPropuesta.setIdFigura(figuraPropuesta);
                propuestaListPropuesta = em.merge(propuestaListPropuesta);
                if (oldIdFiguraOfPropuestaListPropuesta != null) {
                    oldIdFiguraOfPropuestaListPropuesta.getPropuestaList().remove(propuestaListPropuesta);
                    oldIdFiguraOfPropuestaListPropuesta = em.merge(oldIdFiguraOfPropuestaListPropuesta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFiguraPropuesta(figuraPropuesta.getId()) != null) {
                throw new PreexistingEntityException("FiguraPropuesta " + figuraPropuesta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FiguraPropuesta figuraPropuesta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FiguraPropuesta persistentFiguraPropuesta = em.find(FiguraPropuesta.class, figuraPropuesta.getId());
            List<Propuesta> propuestaListOld = persistentFiguraPropuesta.getPropuestaList();
            List<Propuesta> propuestaListNew = figuraPropuesta.getPropuestaList();
            List<Propuesta> attachedPropuestaListNew = new ArrayList<Propuesta>();
            for (Propuesta propuestaListNewPropuestaToAttach : propuestaListNew) {
                propuestaListNewPropuestaToAttach = em.getReference(propuestaListNewPropuestaToAttach.getClass(), propuestaListNewPropuestaToAttach.getId());
                attachedPropuestaListNew.add(propuestaListNewPropuestaToAttach);
            }
            propuestaListNew = attachedPropuestaListNew;
            figuraPropuesta.setPropuestaList(propuestaListNew);
            figuraPropuesta = em.merge(figuraPropuesta);
            for (Propuesta propuestaListOldPropuesta : propuestaListOld) {
                if (!propuestaListNew.contains(propuestaListOldPropuesta)) {
                    propuestaListOldPropuesta.setIdFigura(null);
                    propuestaListOldPropuesta = em.merge(propuestaListOldPropuesta);
                }
            }
            for (Propuesta propuestaListNewPropuesta : propuestaListNew) {
                if (!propuestaListOld.contains(propuestaListNewPropuesta)) {
                    FiguraPropuesta oldIdFiguraOfPropuestaListNewPropuesta = propuestaListNewPropuesta.getIdFigura();
                    propuestaListNewPropuesta.setIdFigura(figuraPropuesta);
                    propuestaListNewPropuesta = em.merge(propuestaListNewPropuesta);
                    if (oldIdFiguraOfPropuestaListNewPropuesta != null && !oldIdFiguraOfPropuestaListNewPropuesta.equals(figuraPropuesta)) {
                        oldIdFiguraOfPropuestaListNewPropuesta.getPropuestaList().remove(propuestaListNewPropuesta);
                        oldIdFiguraOfPropuestaListNewPropuesta = em.merge(oldIdFiguraOfPropuestaListNewPropuesta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = figuraPropuesta.getId();
                if (findFiguraPropuesta(id) == null) {
                    throw new NonexistentEntityException("The figuraPropuesta with id " + id + " no longer exists.");
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
            FiguraPropuesta figuraPropuesta;
            try {
                figuraPropuesta = em.getReference(FiguraPropuesta.class, id);
                figuraPropuesta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The figuraPropuesta with id " + id + " no longer exists.", enfe);
            }
            List<Propuesta> propuestaList = figuraPropuesta.getPropuestaList();
            for (Propuesta propuestaListPropuesta : propuestaList) {
                propuestaListPropuesta.setIdFigura(null);
                propuestaListPropuesta = em.merge(propuestaListPropuesta);
            }
            em.remove(figuraPropuesta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FiguraPropuesta> findFiguraPropuestaEntities() {
        return findFiguraPropuestaEntities(true, -1, -1);
    }

    public List<FiguraPropuesta> findFiguraPropuestaEntities(int maxResults, int firstResult) {
        return findFiguraPropuestaEntities(false, maxResults, firstResult);
    }

    private List<FiguraPropuesta> findFiguraPropuestaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FiguraPropuesta.class));
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

    public FiguraPropuesta findFiguraPropuesta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FiguraPropuesta.class, id);
        } finally {
            em.close();
        }
    }

    public int getFiguraPropuestaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FiguraPropuesta> rt = cq.from(FiguraPropuesta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
