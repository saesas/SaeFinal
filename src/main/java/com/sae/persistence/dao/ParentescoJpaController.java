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
import com.sae.persistence.domain.HojaVida;
import com.sae.persistence.domain.Parentesco;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ParentescoJpaController implements Serializable {

    public ParentescoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Parentesco parentesco) throws PreexistingEntityException, Exception {
        if (parentesco.getHojaVidaList() == null) {
            parentesco.setHojaVidaList(new ArrayList<HojaVida>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<HojaVida> attachedHojaVidaList = new ArrayList<HojaVida>();
            for (HojaVida hojaVidaListHojaVidaToAttach : parentesco.getHojaVidaList()) {
                hojaVidaListHojaVidaToAttach = em.getReference(hojaVidaListHojaVidaToAttach.getClass(), hojaVidaListHojaVidaToAttach.getId());
                attachedHojaVidaList.add(hojaVidaListHojaVidaToAttach);
            }
            parentesco.setHojaVidaList(attachedHojaVidaList);
            em.persist(parentesco);
            for (HojaVida hojaVidaListHojaVida : parentesco.getHojaVidaList()) {
                Parentesco oldIdParentescoContactoOfHojaVidaListHojaVida = hojaVidaListHojaVida.getIdParentescoContacto();
                hojaVidaListHojaVida.setIdParentescoContacto(parentesco);
                hojaVidaListHojaVida = em.merge(hojaVidaListHojaVida);
                if (oldIdParentescoContactoOfHojaVidaListHojaVida != null) {
                    oldIdParentescoContactoOfHojaVidaListHojaVida.getHojaVidaList().remove(hojaVidaListHojaVida);
                    oldIdParentescoContactoOfHojaVidaListHojaVida = em.merge(oldIdParentescoContactoOfHojaVidaListHojaVida);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findParentesco(parentesco.getId()) != null) {
                throw new PreexistingEntityException("Parentesco " + parentesco + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Parentesco parentesco) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Parentesco persistentParentesco = em.find(Parentesco.class, parentesco.getId());
            List<HojaVida> hojaVidaListOld = persistentParentesco.getHojaVidaList();
            List<HojaVida> hojaVidaListNew = parentesco.getHojaVidaList();
            List<HojaVida> attachedHojaVidaListNew = new ArrayList<HojaVida>();
            for (HojaVida hojaVidaListNewHojaVidaToAttach : hojaVidaListNew) {
                hojaVidaListNewHojaVidaToAttach = em.getReference(hojaVidaListNewHojaVidaToAttach.getClass(), hojaVidaListNewHojaVidaToAttach.getId());
                attachedHojaVidaListNew.add(hojaVidaListNewHojaVidaToAttach);
            }
            hojaVidaListNew = attachedHojaVidaListNew;
            parentesco.setHojaVidaList(hojaVidaListNew);
            parentesco = em.merge(parentesco);
            for (HojaVida hojaVidaListOldHojaVida : hojaVidaListOld) {
                if (!hojaVidaListNew.contains(hojaVidaListOldHojaVida)) {
                    hojaVidaListOldHojaVida.setIdParentescoContacto(null);
                    hojaVidaListOldHojaVida = em.merge(hojaVidaListOldHojaVida);
                }
            }
            for (HojaVida hojaVidaListNewHojaVida : hojaVidaListNew) {
                if (!hojaVidaListOld.contains(hojaVidaListNewHojaVida)) {
                    Parentesco oldIdParentescoContactoOfHojaVidaListNewHojaVida = hojaVidaListNewHojaVida.getIdParentescoContacto();
                    hojaVidaListNewHojaVida.setIdParentescoContacto(parentesco);
                    hojaVidaListNewHojaVida = em.merge(hojaVidaListNewHojaVida);
                    if (oldIdParentescoContactoOfHojaVidaListNewHojaVida != null && !oldIdParentescoContactoOfHojaVidaListNewHojaVida.equals(parentesco)) {
                        oldIdParentescoContactoOfHojaVidaListNewHojaVida.getHojaVidaList().remove(hojaVidaListNewHojaVida);
                        oldIdParentescoContactoOfHojaVidaListNewHojaVida = em.merge(oldIdParentescoContactoOfHojaVidaListNewHojaVida);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = parentesco.getId();
                if (findParentesco(id) == null) {
                    throw new NonexistentEntityException("The parentesco with id " + id + " no longer exists.");
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
            Parentesco parentesco;
            try {
                parentesco = em.getReference(Parentesco.class, id);
                parentesco.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The parentesco with id " + id + " no longer exists.", enfe);
            }
            List<HojaVida> hojaVidaList = parentesco.getHojaVidaList();
            for (HojaVida hojaVidaListHojaVida : hojaVidaList) {
                hojaVidaListHojaVida.setIdParentescoContacto(null);
                hojaVidaListHojaVida = em.merge(hojaVidaListHojaVida);
            }
            em.remove(parentesco);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Parentesco> findParentescoEntities() {
        return findParentescoEntities(true, -1, -1);
    }

    public List<Parentesco> findParentescoEntities(int maxResults, int firstResult) {
        return findParentescoEntities(false, maxResults, firstResult);
    }

    private List<Parentesco> findParentescoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Parentesco.class));
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

    public Parentesco findParentesco(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Parentesco.class, id);
        } finally {
            em.close();
        }
    }

    public int getParentescoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Parentesco> rt = cq.from(Parentesco.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
