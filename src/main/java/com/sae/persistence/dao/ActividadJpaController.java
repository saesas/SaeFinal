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
import com.sae.persistence.domain.ActaCobroActividad;
import com.sae.persistence.domain.Actividad;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.ActividadPresupuesto;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ActividadJpaController implements Serializable {

    public ActividadJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Actividad actividad) throws PreexistingEntityException, Exception {
        if (actividad.getActaCobroActividadList() == null) {
            actividad.setActaCobroActividadList(new ArrayList<ActaCobroActividad>());
        }
        if (actividad.getActividadPresupuestoList() == null) {
            actividad.setActividadPresupuestoList(new ArrayList<ActividadPresupuesto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ActaCobroActividad> attachedActaCobroActividadList = new ArrayList<ActaCobroActividad>();
            for (ActaCobroActividad actaCobroActividadListActaCobroActividadToAttach : actividad.getActaCobroActividadList()) {
                actaCobroActividadListActaCobroActividadToAttach = em.getReference(actaCobroActividadListActaCobroActividadToAttach.getClass(), actaCobroActividadListActaCobroActividadToAttach.getId());
                attachedActaCobroActividadList.add(actaCobroActividadListActaCobroActividadToAttach);
            }
            actividad.setActaCobroActividadList(attachedActaCobroActividadList);
            List<ActividadPresupuesto> attachedActividadPresupuestoList = new ArrayList<ActividadPresupuesto>();
            for (ActividadPresupuesto actividadPresupuestoListActividadPresupuestoToAttach : actividad.getActividadPresupuestoList()) {
                actividadPresupuestoListActividadPresupuestoToAttach = em.getReference(actividadPresupuestoListActividadPresupuestoToAttach.getClass(), actividadPresupuestoListActividadPresupuestoToAttach.getId());
                attachedActividadPresupuestoList.add(actividadPresupuestoListActividadPresupuestoToAttach);
            }
            actividad.setActividadPresupuestoList(attachedActividadPresupuestoList);
            em.persist(actividad);
            for (ActaCobroActividad actaCobroActividadListActaCobroActividad : actividad.getActaCobroActividadList()) {
                Actividad oldIdActividadOfActaCobroActividadListActaCobroActividad = actaCobroActividadListActaCobroActividad.getIdActividad();
                actaCobroActividadListActaCobroActividad.setIdActividad(actividad);
                actaCobroActividadListActaCobroActividad = em.merge(actaCobroActividadListActaCobroActividad);
                if (oldIdActividadOfActaCobroActividadListActaCobroActividad != null) {
                    oldIdActividadOfActaCobroActividadListActaCobroActividad.getActaCobroActividadList().remove(actaCobroActividadListActaCobroActividad);
                    oldIdActividadOfActaCobroActividadListActaCobroActividad = em.merge(oldIdActividadOfActaCobroActividadListActaCobroActividad);
                }
            }
            for (ActividadPresupuesto actividadPresupuestoListActividadPresupuesto : actividad.getActividadPresupuestoList()) {
                Actividad oldIdActividadOfActividadPresupuestoListActividadPresupuesto = actividadPresupuestoListActividadPresupuesto.getIdActividad();
                actividadPresupuestoListActividadPresupuesto.setIdActividad(actividad);
                actividadPresupuestoListActividadPresupuesto = em.merge(actividadPresupuestoListActividadPresupuesto);
                if (oldIdActividadOfActividadPresupuestoListActividadPresupuesto != null) {
                    oldIdActividadOfActividadPresupuestoListActividadPresupuesto.getActividadPresupuestoList().remove(actividadPresupuestoListActividadPresupuesto);
                    oldIdActividadOfActividadPresupuestoListActividadPresupuesto = em.merge(oldIdActividadOfActividadPresupuestoListActividadPresupuesto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findActividad(actividad.getId()) != null) {
                throw new PreexistingEntityException("Actividad " + actividad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Actividad actividad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Actividad persistentActividad = em.find(Actividad.class, actividad.getId());
            List<ActaCobroActividad> actaCobroActividadListOld = persistentActividad.getActaCobroActividadList();
            List<ActaCobroActividad> actaCobroActividadListNew = actividad.getActaCobroActividadList();
            List<ActividadPresupuesto> actividadPresupuestoListOld = persistentActividad.getActividadPresupuestoList();
            List<ActividadPresupuesto> actividadPresupuestoListNew = actividad.getActividadPresupuestoList();
            List<ActaCobroActividad> attachedActaCobroActividadListNew = new ArrayList<ActaCobroActividad>();
            for (ActaCobroActividad actaCobroActividadListNewActaCobroActividadToAttach : actaCobroActividadListNew) {
                actaCobroActividadListNewActaCobroActividadToAttach = em.getReference(actaCobroActividadListNewActaCobroActividadToAttach.getClass(), actaCobroActividadListNewActaCobroActividadToAttach.getId());
                attachedActaCobroActividadListNew.add(actaCobroActividadListNewActaCobroActividadToAttach);
            }
            actaCobroActividadListNew = attachedActaCobroActividadListNew;
            actividad.setActaCobroActividadList(actaCobroActividadListNew);
            List<ActividadPresupuesto> attachedActividadPresupuestoListNew = new ArrayList<ActividadPresupuesto>();
            for (ActividadPresupuesto actividadPresupuestoListNewActividadPresupuestoToAttach : actividadPresupuestoListNew) {
                actividadPresupuestoListNewActividadPresupuestoToAttach = em.getReference(actividadPresupuestoListNewActividadPresupuestoToAttach.getClass(), actividadPresupuestoListNewActividadPresupuestoToAttach.getId());
                attachedActividadPresupuestoListNew.add(actividadPresupuestoListNewActividadPresupuestoToAttach);
            }
            actividadPresupuestoListNew = attachedActividadPresupuestoListNew;
            actividad.setActividadPresupuestoList(actividadPresupuestoListNew);
            actividad = em.merge(actividad);
            for (ActaCobroActividad actaCobroActividadListOldActaCobroActividad : actaCobroActividadListOld) {
                if (!actaCobroActividadListNew.contains(actaCobroActividadListOldActaCobroActividad)) {
                    actaCobroActividadListOldActaCobroActividad.setIdActividad(null);
                    actaCobroActividadListOldActaCobroActividad = em.merge(actaCobroActividadListOldActaCobroActividad);
                }
            }
            for (ActaCobroActividad actaCobroActividadListNewActaCobroActividad : actaCobroActividadListNew) {
                if (!actaCobroActividadListOld.contains(actaCobroActividadListNewActaCobroActividad)) {
                    Actividad oldIdActividadOfActaCobroActividadListNewActaCobroActividad = actaCobroActividadListNewActaCobroActividad.getIdActividad();
                    actaCobroActividadListNewActaCobroActividad.setIdActividad(actividad);
                    actaCobroActividadListNewActaCobroActividad = em.merge(actaCobroActividadListNewActaCobroActividad);
                    if (oldIdActividadOfActaCobroActividadListNewActaCobroActividad != null && !oldIdActividadOfActaCobroActividadListNewActaCobroActividad.equals(actividad)) {
                        oldIdActividadOfActaCobroActividadListNewActaCobroActividad.getActaCobroActividadList().remove(actaCobroActividadListNewActaCobroActividad);
                        oldIdActividadOfActaCobroActividadListNewActaCobroActividad = em.merge(oldIdActividadOfActaCobroActividadListNewActaCobroActividad);
                    }
                }
            }
            for (ActividadPresupuesto actividadPresupuestoListOldActividadPresupuesto : actividadPresupuestoListOld) {
                if (!actividadPresupuestoListNew.contains(actividadPresupuestoListOldActividadPresupuesto)) {
                    actividadPresupuestoListOldActividadPresupuesto.setIdActividad(null);
                    actividadPresupuestoListOldActividadPresupuesto = em.merge(actividadPresupuestoListOldActividadPresupuesto);
                }
            }
            for (ActividadPresupuesto actividadPresupuestoListNewActividadPresupuesto : actividadPresupuestoListNew) {
                if (!actividadPresupuestoListOld.contains(actividadPresupuestoListNewActividadPresupuesto)) {
                    Actividad oldIdActividadOfActividadPresupuestoListNewActividadPresupuesto = actividadPresupuestoListNewActividadPresupuesto.getIdActividad();
                    actividadPresupuestoListNewActividadPresupuesto.setIdActividad(actividad);
                    actividadPresupuestoListNewActividadPresupuesto = em.merge(actividadPresupuestoListNewActividadPresupuesto);
                    if (oldIdActividadOfActividadPresupuestoListNewActividadPresupuesto != null && !oldIdActividadOfActividadPresupuestoListNewActividadPresupuesto.equals(actividad)) {
                        oldIdActividadOfActividadPresupuestoListNewActividadPresupuesto.getActividadPresupuestoList().remove(actividadPresupuestoListNewActividadPresupuesto);
                        oldIdActividadOfActividadPresupuestoListNewActividadPresupuesto = em.merge(oldIdActividadOfActividadPresupuestoListNewActividadPresupuesto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = actividad.getId();
                if (findActividad(id) == null) {
                    throw new NonexistentEntityException("The actividad with id " + id + " no longer exists.");
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
            Actividad actividad;
            try {
                actividad = em.getReference(Actividad.class, id);
                actividad.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actividad with id " + id + " no longer exists.", enfe);
            }
            List<ActaCobroActividad> actaCobroActividadList = actividad.getActaCobroActividadList();
            for (ActaCobroActividad actaCobroActividadListActaCobroActividad : actaCobroActividadList) {
                actaCobroActividadListActaCobroActividad.setIdActividad(null);
                actaCobroActividadListActaCobroActividad = em.merge(actaCobroActividadListActaCobroActividad);
            }
            List<ActividadPresupuesto> actividadPresupuestoList = actividad.getActividadPresupuestoList();
            for (ActividadPresupuesto actividadPresupuestoListActividadPresupuesto : actividadPresupuestoList) {
                actividadPresupuestoListActividadPresupuesto.setIdActividad(null);
                actividadPresupuestoListActividadPresupuesto = em.merge(actividadPresupuestoListActividadPresupuesto);
            }
            em.remove(actividad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Actividad> findActividadEntities() {
        return findActividadEntities(true, -1, -1);
    }

    public List<Actividad> findActividadEntities(int maxResults, int firstResult) {
        return findActividadEntities(false, maxResults, firstResult);
    }

    private List<Actividad> findActividadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Actividad.class));
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

    public Actividad findActividad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Actividad.class, id);
        } finally {
            em.close();
        }
    }

    public int getActividadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Actividad> rt = cq.from(Actividad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
