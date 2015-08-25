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
import com.sae.persistence.domain.PagoAnticipoReintegro;
import com.sae.persistence.domain.PrestamoAnticipo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class PrestamoAnticipoJpaController implements Serializable {

    public PrestamoAnticipoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PrestamoAnticipo prestamoAnticipo) throws PreexistingEntityException, Exception {
        if (prestamoAnticipo.getPagoAnticipoReintegroList() == null) {
            prestamoAnticipo.setPagoAnticipoReintegroList(new ArrayList<PagoAnticipoReintegro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PagoAnticipoReintegro> attachedPagoAnticipoReintegroList = new ArrayList<PagoAnticipoReintegro>();
            for (PagoAnticipoReintegro pagoAnticipoReintegroListPagoAnticipoReintegroToAttach : prestamoAnticipo.getPagoAnticipoReintegroList()) {
                pagoAnticipoReintegroListPagoAnticipoReintegroToAttach = em.getReference(pagoAnticipoReintegroListPagoAnticipoReintegroToAttach.getClass(), pagoAnticipoReintegroListPagoAnticipoReintegroToAttach.getId());
                attachedPagoAnticipoReintegroList.add(pagoAnticipoReintegroListPagoAnticipoReintegroToAttach);
            }
            prestamoAnticipo.setPagoAnticipoReintegroList(attachedPagoAnticipoReintegroList);
            em.persist(prestamoAnticipo);
            for (PagoAnticipoReintegro pagoAnticipoReintegroListPagoAnticipoReintegro : prestamoAnticipo.getPagoAnticipoReintegroList()) {
                PrestamoAnticipo oldIdAnticipoOfPagoAnticipoReintegroListPagoAnticipoReintegro = pagoAnticipoReintegroListPagoAnticipoReintegro.getIdAnticipo();
                pagoAnticipoReintegroListPagoAnticipoReintegro.setIdAnticipo(prestamoAnticipo);
                pagoAnticipoReintegroListPagoAnticipoReintegro = em.merge(pagoAnticipoReintegroListPagoAnticipoReintegro);
                if (oldIdAnticipoOfPagoAnticipoReintegroListPagoAnticipoReintegro != null) {
                    oldIdAnticipoOfPagoAnticipoReintegroListPagoAnticipoReintegro.getPagoAnticipoReintegroList().remove(pagoAnticipoReintegroListPagoAnticipoReintegro);
                    oldIdAnticipoOfPagoAnticipoReintegroListPagoAnticipoReintegro = em.merge(oldIdAnticipoOfPagoAnticipoReintegroListPagoAnticipoReintegro);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPrestamoAnticipo(prestamoAnticipo.getId()) != null) {
                throw new PreexistingEntityException("PrestamoAnticipo " + prestamoAnticipo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PrestamoAnticipo prestamoAnticipo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PrestamoAnticipo persistentPrestamoAnticipo = em.find(PrestamoAnticipo.class, prestamoAnticipo.getId());
            List<PagoAnticipoReintegro> pagoAnticipoReintegroListOld = persistentPrestamoAnticipo.getPagoAnticipoReintegroList();
            List<PagoAnticipoReintegro> pagoAnticipoReintegroListNew = prestamoAnticipo.getPagoAnticipoReintegroList();
            List<PagoAnticipoReintegro> attachedPagoAnticipoReintegroListNew = new ArrayList<PagoAnticipoReintegro>();
            for (PagoAnticipoReintegro pagoAnticipoReintegroListNewPagoAnticipoReintegroToAttach : pagoAnticipoReintegroListNew) {
                pagoAnticipoReintegroListNewPagoAnticipoReintegroToAttach = em.getReference(pagoAnticipoReintegroListNewPagoAnticipoReintegroToAttach.getClass(), pagoAnticipoReintegroListNewPagoAnticipoReintegroToAttach.getId());
                attachedPagoAnticipoReintegroListNew.add(pagoAnticipoReintegroListNewPagoAnticipoReintegroToAttach);
            }
            pagoAnticipoReintegroListNew = attachedPagoAnticipoReintegroListNew;
            prestamoAnticipo.setPagoAnticipoReintegroList(pagoAnticipoReintegroListNew);
            prestamoAnticipo = em.merge(prestamoAnticipo);
            for (PagoAnticipoReintegro pagoAnticipoReintegroListOldPagoAnticipoReintegro : pagoAnticipoReintegroListOld) {
                if (!pagoAnticipoReintegroListNew.contains(pagoAnticipoReintegroListOldPagoAnticipoReintegro)) {
                    pagoAnticipoReintegroListOldPagoAnticipoReintegro.setIdAnticipo(null);
                    pagoAnticipoReintegroListOldPagoAnticipoReintegro = em.merge(pagoAnticipoReintegroListOldPagoAnticipoReintegro);
                }
            }
            for (PagoAnticipoReintegro pagoAnticipoReintegroListNewPagoAnticipoReintegro : pagoAnticipoReintegroListNew) {
                if (!pagoAnticipoReintegroListOld.contains(pagoAnticipoReintegroListNewPagoAnticipoReintegro)) {
                    PrestamoAnticipo oldIdAnticipoOfPagoAnticipoReintegroListNewPagoAnticipoReintegro = pagoAnticipoReintegroListNewPagoAnticipoReintegro.getIdAnticipo();
                    pagoAnticipoReintegroListNewPagoAnticipoReintegro.setIdAnticipo(prestamoAnticipo);
                    pagoAnticipoReintegroListNewPagoAnticipoReintegro = em.merge(pagoAnticipoReintegroListNewPagoAnticipoReintegro);
                    if (oldIdAnticipoOfPagoAnticipoReintegroListNewPagoAnticipoReintegro != null && !oldIdAnticipoOfPagoAnticipoReintegroListNewPagoAnticipoReintegro.equals(prestamoAnticipo)) {
                        oldIdAnticipoOfPagoAnticipoReintegroListNewPagoAnticipoReintegro.getPagoAnticipoReintegroList().remove(pagoAnticipoReintegroListNewPagoAnticipoReintegro);
                        oldIdAnticipoOfPagoAnticipoReintegroListNewPagoAnticipoReintegro = em.merge(oldIdAnticipoOfPagoAnticipoReintegroListNewPagoAnticipoReintegro);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = prestamoAnticipo.getId();
                if (findPrestamoAnticipo(id) == null) {
                    throw new NonexistentEntityException("The prestamoAnticipo with id " + id + " no longer exists.");
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
            PrestamoAnticipo prestamoAnticipo;
            try {
                prestamoAnticipo = em.getReference(PrestamoAnticipo.class, id);
                prestamoAnticipo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The prestamoAnticipo with id " + id + " no longer exists.", enfe);
            }
            List<PagoAnticipoReintegro> pagoAnticipoReintegroList = prestamoAnticipo.getPagoAnticipoReintegroList();
            for (PagoAnticipoReintegro pagoAnticipoReintegroListPagoAnticipoReintegro : pagoAnticipoReintegroList) {
                pagoAnticipoReintegroListPagoAnticipoReintegro.setIdAnticipo(null);
                pagoAnticipoReintegroListPagoAnticipoReintegro = em.merge(pagoAnticipoReintegroListPagoAnticipoReintegro);
            }
            em.remove(prestamoAnticipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PrestamoAnticipo> findPrestamoAnticipoEntities() {
        return findPrestamoAnticipoEntities(true, -1, -1);
    }

    public List<PrestamoAnticipo> findPrestamoAnticipoEntities(int maxResults, int firstResult) {
        return findPrestamoAnticipoEntities(false, maxResults, firstResult);
    }

    private List<PrestamoAnticipo> findPrestamoAnticipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PrestamoAnticipo.class));
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

    public PrestamoAnticipo findPrestamoAnticipo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PrestamoAnticipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getPrestamoAnticipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PrestamoAnticipo> rt = cq.from(PrestamoAnticipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
