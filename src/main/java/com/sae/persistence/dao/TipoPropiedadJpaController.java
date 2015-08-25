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
import com.sae.persistence.domain.TipoPropiedad;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoPropiedadJpaController implements Serializable {

    public TipoPropiedadJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoPropiedad tipoPropiedad) throws PreexistingEntityException, Exception {
        if (tipoPropiedad.getHojaVidaList() == null) {
            tipoPropiedad.setHojaVidaList(new ArrayList<HojaVida>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<HojaVida> attachedHojaVidaList = new ArrayList<HojaVida>();
            for (HojaVida hojaVidaListHojaVidaToAttach : tipoPropiedad.getHojaVidaList()) {
                hojaVidaListHojaVidaToAttach = em.getReference(hojaVidaListHojaVidaToAttach.getClass(), hojaVidaListHojaVidaToAttach.getId());
                attachedHojaVidaList.add(hojaVidaListHojaVidaToAttach);
            }
            tipoPropiedad.setHojaVidaList(attachedHojaVidaList);
            em.persist(tipoPropiedad);
            for (HojaVida hojaVidaListHojaVida : tipoPropiedad.getHojaVidaList()) {
                TipoPropiedad oldIdTipoPropiedadHabitaOfHojaVidaListHojaVida = hojaVidaListHojaVida.getIdTipoPropiedadHabita();
                hojaVidaListHojaVida.setIdTipoPropiedadHabita(tipoPropiedad);
                hojaVidaListHojaVida = em.merge(hojaVidaListHojaVida);
                if (oldIdTipoPropiedadHabitaOfHojaVidaListHojaVida != null) {
                    oldIdTipoPropiedadHabitaOfHojaVidaListHojaVida.getHojaVidaList().remove(hojaVidaListHojaVida);
                    oldIdTipoPropiedadHabitaOfHojaVidaListHojaVida = em.merge(oldIdTipoPropiedadHabitaOfHojaVidaListHojaVida);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoPropiedad(tipoPropiedad.getId()) != null) {
                throw new PreexistingEntityException("TipoPropiedad " + tipoPropiedad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoPropiedad tipoPropiedad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoPropiedad persistentTipoPropiedad = em.find(TipoPropiedad.class, tipoPropiedad.getId());
            List<HojaVida> hojaVidaListOld = persistentTipoPropiedad.getHojaVidaList();
            List<HojaVida> hojaVidaListNew = tipoPropiedad.getHojaVidaList();
            List<HojaVida> attachedHojaVidaListNew = new ArrayList<HojaVida>();
            for (HojaVida hojaVidaListNewHojaVidaToAttach : hojaVidaListNew) {
                hojaVidaListNewHojaVidaToAttach = em.getReference(hojaVidaListNewHojaVidaToAttach.getClass(), hojaVidaListNewHojaVidaToAttach.getId());
                attachedHojaVidaListNew.add(hojaVidaListNewHojaVidaToAttach);
            }
            hojaVidaListNew = attachedHojaVidaListNew;
            tipoPropiedad.setHojaVidaList(hojaVidaListNew);
            tipoPropiedad = em.merge(tipoPropiedad);
            for (HojaVida hojaVidaListOldHojaVida : hojaVidaListOld) {
                if (!hojaVidaListNew.contains(hojaVidaListOldHojaVida)) {
                    hojaVidaListOldHojaVida.setIdTipoPropiedadHabita(null);
                    hojaVidaListOldHojaVida = em.merge(hojaVidaListOldHojaVida);
                }
            }
            for (HojaVida hojaVidaListNewHojaVida : hojaVidaListNew) {
                if (!hojaVidaListOld.contains(hojaVidaListNewHojaVida)) {
                    TipoPropiedad oldIdTipoPropiedadHabitaOfHojaVidaListNewHojaVida = hojaVidaListNewHojaVida.getIdTipoPropiedadHabita();
                    hojaVidaListNewHojaVida.setIdTipoPropiedadHabita(tipoPropiedad);
                    hojaVidaListNewHojaVida = em.merge(hojaVidaListNewHojaVida);
                    if (oldIdTipoPropiedadHabitaOfHojaVidaListNewHojaVida != null && !oldIdTipoPropiedadHabitaOfHojaVidaListNewHojaVida.equals(tipoPropiedad)) {
                        oldIdTipoPropiedadHabitaOfHojaVidaListNewHojaVida.getHojaVidaList().remove(hojaVidaListNewHojaVida);
                        oldIdTipoPropiedadHabitaOfHojaVidaListNewHojaVida = em.merge(oldIdTipoPropiedadHabitaOfHojaVidaListNewHojaVida);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoPropiedad.getId();
                if (findTipoPropiedad(id) == null) {
                    throw new NonexistentEntityException("The tipoPropiedad with id " + id + " no longer exists.");
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
            TipoPropiedad tipoPropiedad;
            try {
                tipoPropiedad = em.getReference(TipoPropiedad.class, id);
                tipoPropiedad.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoPropiedad with id " + id + " no longer exists.", enfe);
            }
            List<HojaVida> hojaVidaList = tipoPropiedad.getHojaVidaList();
            for (HojaVida hojaVidaListHojaVida : hojaVidaList) {
                hojaVidaListHojaVida.setIdTipoPropiedadHabita(null);
                hojaVidaListHojaVida = em.merge(hojaVidaListHojaVida);
            }
            em.remove(tipoPropiedad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoPropiedad> findTipoPropiedadEntities() {
        return findTipoPropiedadEntities(true, -1, -1);
    }

    public List<TipoPropiedad> findTipoPropiedadEntities(int maxResults, int firstResult) {
        return findTipoPropiedadEntities(false, maxResults, firstResult);
    }

    private List<TipoPropiedad> findTipoPropiedadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoPropiedad.class));
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

    public TipoPropiedad findTipoPropiedad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoPropiedad.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoPropiedadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoPropiedad> rt = cq.from(TipoPropiedad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
