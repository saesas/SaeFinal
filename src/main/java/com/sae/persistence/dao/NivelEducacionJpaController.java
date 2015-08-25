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
import com.sae.persistence.domain.NivelEducacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class NivelEducacionJpaController implements Serializable {

    public NivelEducacionJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NivelEducacion nivelEducacion) throws PreexistingEntityException, Exception {
        if (nivelEducacion.getHojaVidaList() == null) {
            nivelEducacion.setHojaVidaList(new ArrayList<HojaVida>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<HojaVida> attachedHojaVidaList = new ArrayList<HojaVida>();
            for (HojaVida hojaVidaListHojaVidaToAttach : nivelEducacion.getHojaVidaList()) {
                hojaVidaListHojaVidaToAttach = em.getReference(hojaVidaListHojaVidaToAttach.getClass(), hojaVidaListHojaVidaToAttach.getId());
                attachedHojaVidaList.add(hojaVidaListHojaVidaToAttach);
            }
            nivelEducacion.setHojaVidaList(attachedHojaVidaList);
            em.persist(nivelEducacion);
            for (HojaVida hojaVidaListHojaVida : nivelEducacion.getHojaVidaList()) {
                NivelEducacion oldIdNivelEducacionOfHojaVidaListHojaVida = hojaVidaListHojaVida.getIdNivelEducacion();
                hojaVidaListHojaVida.setIdNivelEducacion(nivelEducacion);
                hojaVidaListHojaVida = em.merge(hojaVidaListHojaVida);
                if (oldIdNivelEducacionOfHojaVidaListHojaVida != null) {
                    oldIdNivelEducacionOfHojaVidaListHojaVida.getHojaVidaList().remove(hojaVidaListHojaVida);
                    oldIdNivelEducacionOfHojaVidaListHojaVida = em.merge(oldIdNivelEducacionOfHojaVidaListHojaVida);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNivelEducacion(nivelEducacion.getId()) != null) {
                throw new PreexistingEntityException("NivelEducacion " + nivelEducacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NivelEducacion nivelEducacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NivelEducacion persistentNivelEducacion = em.find(NivelEducacion.class, nivelEducacion.getId());
            List<HojaVida> hojaVidaListOld = persistentNivelEducacion.getHojaVidaList();
            List<HojaVida> hojaVidaListNew = nivelEducacion.getHojaVidaList();
            List<HojaVida> attachedHojaVidaListNew = new ArrayList<HojaVida>();
            for (HojaVida hojaVidaListNewHojaVidaToAttach : hojaVidaListNew) {
                hojaVidaListNewHojaVidaToAttach = em.getReference(hojaVidaListNewHojaVidaToAttach.getClass(), hojaVidaListNewHojaVidaToAttach.getId());
                attachedHojaVidaListNew.add(hojaVidaListNewHojaVidaToAttach);
            }
            hojaVidaListNew = attachedHojaVidaListNew;
            nivelEducacion.setHojaVidaList(hojaVidaListNew);
            nivelEducacion = em.merge(nivelEducacion);
            for (HojaVida hojaVidaListOldHojaVida : hojaVidaListOld) {
                if (!hojaVidaListNew.contains(hojaVidaListOldHojaVida)) {
                    hojaVidaListOldHojaVida.setIdNivelEducacion(null);
                    hojaVidaListOldHojaVida = em.merge(hojaVidaListOldHojaVida);
                }
            }
            for (HojaVida hojaVidaListNewHojaVida : hojaVidaListNew) {
                if (!hojaVidaListOld.contains(hojaVidaListNewHojaVida)) {
                    NivelEducacion oldIdNivelEducacionOfHojaVidaListNewHojaVida = hojaVidaListNewHojaVida.getIdNivelEducacion();
                    hojaVidaListNewHojaVida.setIdNivelEducacion(nivelEducacion);
                    hojaVidaListNewHojaVida = em.merge(hojaVidaListNewHojaVida);
                    if (oldIdNivelEducacionOfHojaVidaListNewHojaVida != null && !oldIdNivelEducacionOfHojaVidaListNewHojaVida.equals(nivelEducacion)) {
                        oldIdNivelEducacionOfHojaVidaListNewHojaVida.getHojaVidaList().remove(hojaVidaListNewHojaVida);
                        oldIdNivelEducacionOfHojaVidaListNewHojaVida = em.merge(oldIdNivelEducacionOfHojaVidaListNewHojaVida);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = nivelEducacion.getId();
                if (findNivelEducacion(id) == null) {
                    throw new NonexistentEntityException("The nivelEducacion with id " + id + " no longer exists.");
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
            NivelEducacion nivelEducacion;
            try {
                nivelEducacion = em.getReference(NivelEducacion.class, id);
                nivelEducacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nivelEducacion with id " + id + " no longer exists.", enfe);
            }
            List<HojaVida> hojaVidaList = nivelEducacion.getHojaVidaList();
            for (HojaVida hojaVidaListHojaVida : hojaVidaList) {
                hojaVidaListHojaVida.setIdNivelEducacion(null);
                hojaVidaListHojaVida = em.merge(hojaVidaListHojaVida);
            }
            em.remove(nivelEducacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NivelEducacion> findNivelEducacionEntities() {
        return findNivelEducacionEntities(true, -1, -1);
    }

    public List<NivelEducacion> findNivelEducacionEntities(int maxResults, int firstResult) {
        return findNivelEducacionEntities(false, maxResults, firstResult);
    }

    private List<NivelEducacion> findNivelEducacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NivelEducacion.class));
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

    public NivelEducacion findNivelEducacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NivelEducacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getNivelEducacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NivelEducacion> rt = cq.from(NivelEducacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
