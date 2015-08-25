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
import com.sae.persistence.domain.Presupuesto;
import com.sae.persistence.domain.Actividad;
import com.sae.persistence.domain.ActividadInsumo;
import com.sae.persistence.domain.ActividadPresupuesto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ActividadPresupuestoJpaController implements Serializable {

    public ActividadPresupuestoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ActividadPresupuesto actividadPresupuesto) throws PreexistingEntityException, Exception {
        if (actividadPresupuesto.getActividadInsumoList() == null) {
            actividadPresupuesto.setActividadInsumoList(new ArrayList<ActividadInsumo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Presupuesto idPresupuesto = actividadPresupuesto.getIdPresupuesto();
            if (idPresupuesto != null) {
                idPresupuesto = em.getReference(idPresupuesto.getClass(), idPresupuesto.getId());
                actividadPresupuesto.setIdPresupuesto(idPresupuesto);
            }
            Actividad idActividad = actividadPresupuesto.getIdActividad();
            if (idActividad != null) {
                idActividad = em.getReference(idActividad.getClass(), idActividad.getId());
                actividadPresupuesto.setIdActividad(idActividad);
            }
            List<ActividadInsumo> attachedActividadInsumoList = new ArrayList<ActividadInsumo>();
            for (ActividadInsumo actividadInsumoListActividadInsumoToAttach : actividadPresupuesto.getActividadInsumoList()) {
                actividadInsumoListActividadInsumoToAttach = em.getReference(actividadInsumoListActividadInsumoToAttach.getClass(), actividadInsumoListActividadInsumoToAttach.getId());
                attachedActividadInsumoList.add(actividadInsumoListActividadInsumoToAttach);
            }
            actividadPresupuesto.setActividadInsumoList(attachedActividadInsumoList);
            em.persist(actividadPresupuesto);
            if (idPresupuesto != null) {
                idPresupuesto.getActividadPresupuestoList().add(actividadPresupuesto);
                idPresupuesto = em.merge(idPresupuesto);
            }
            if (idActividad != null) {
                idActividad.getActividadPresupuestoList().add(actividadPresupuesto);
                idActividad = em.merge(idActividad);
            }
            for (ActividadInsumo actividadInsumoListActividadInsumo : actividadPresupuesto.getActividadInsumoList()) {
                ActividadPresupuesto oldIdActividadPresupuestoOfActividadInsumoListActividadInsumo = actividadInsumoListActividadInsumo.getIdActividadPresupuesto();
                actividadInsumoListActividadInsumo.setIdActividadPresupuesto(actividadPresupuesto);
                actividadInsumoListActividadInsumo = em.merge(actividadInsumoListActividadInsumo);
                if (oldIdActividadPresupuestoOfActividadInsumoListActividadInsumo != null) {
                    oldIdActividadPresupuestoOfActividadInsumoListActividadInsumo.getActividadInsumoList().remove(actividadInsumoListActividadInsumo);
                    oldIdActividadPresupuestoOfActividadInsumoListActividadInsumo = em.merge(oldIdActividadPresupuestoOfActividadInsumoListActividadInsumo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findActividadPresupuesto(actividadPresupuesto.getId()) != null) {
                throw new PreexistingEntityException("ActividadPresupuesto " + actividadPresupuesto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ActividadPresupuesto actividadPresupuesto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ActividadPresupuesto persistentActividadPresupuesto = em.find(ActividadPresupuesto.class, actividadPresupuesto.getId());
            Presupuesto idPresupuestoOld = persistentActividadPresupuesto.getIdPresupuesto();
            Presupuesto idPresupuestoNew = actividadPresupuesto.getIdPresupuesto();
            Actividad idActividadOld = persistentActividadPresupuesto.getIdActividad();
            Actividad idActividadNew = actividadPresupuesto.getIdActividad();
            List<ActividadInsumo> actividadInsumoListOld = persistentActividadPresupuesto.getActividadInsumoList();
            List<ActividadInsumo> actividadInsumoListNew = actividadPresupuesto.getActividadInsumoList();
            if (idPresupuestoNew != null) {
                idPresupuestoNew = em.getReference(idPresupuestoNew.getClass(), idPresupuestoNew.getId());
                actividadPresupuesto.setIdPresupuesto(idPresupuestoNew);
            }
            if (idActividadNew != null) {
                idActividadNew = em.getReference(idActividadNew.getClass(), idActividadNew.getId());
                actividadPresupuesto.setIdActividad(idActividadNew);
            }
            List<ActividadInsumo> attachedActividadInsumoListNew = new ArrayList<ActividadInsumo>();
            for (ActividadInsumo actividadInsumoListNewActividadInsumoToAttach : actividadInsumoListNew) {
                actividadInsumoListNewActividadInsumoToAttach = em.getReference(actividadInsumoListNewActividadInsumoToAttach.getClass(), actividadInsumoListNewActividadInsumoToAttach.getId());
                attachedActividadInsumoListNew.add(actividadInsumoListNewActividadInsumoToAttach);
            }
            actividadInsumoListNew = attachedActividadInsumoListNew;
            actividadPresupuesto.setActividadInsumoList(actividadInsumoListNew);
            actividadPresupuesto = em.merge(actividadPresupuesto);
            if (idPresupuestoOld != null && !idPresupuestoOld.equals(idPresupuestoNew)) {
                idPresupuestoOld.getActividadPresupuestoList().remove(actividadPresupuesto);
                idPresupuestoOld = em.merge(idPresupuestoOld);
            }
            if (idPresupuestoNew != null && !idPresupuestoNew.equals(idPresupuestoOld)) {
                idPresupuestoNew.getActividadPresupuestoList().add(actividadPresupuesto);
                idPresupuestoNew = em.merge(idPresupuestoNew);
            }
            if (idActividadOld != null && !idActividadOld.equals(idActividadNew)) {
                idActividadOld.getActividadPresupuestoList().remove(actividadPresupuesto);
                idActividadOld = em.merge(idActividadOld);
            }
            if (idActividadNew != null && !idActividadNew.equals(idActividadOld)) {
                idActividadNew.getActividadPresupuestoList().add(actividadPresupuesto);
                idActividadNew = em.merge(idActividadNew);
            }
            for (ActividadInsumo actividadInsumoListOldActividadInsumo : actividadInsumoListOld) {
                if (!actividadInsumoListNew.contains(actividadInsumoListOldActividadInsumo)) {
                    actividadInsumoListOldActividadInsumo.setIdActividadPresupuesto(null);
                    actividadInsumoListOldActividadInsumo = em.merge(actividadInsumoListOldActividadInsumo);
                }
            }
            for (ActividadInsumo actividadInsumoListNewActividadInsumo : actividadInsumoListNew) {
                if (!actividadInsumoListOld.contains(actividadInsumoListNewActividadInsumo)) {
                    ActividadPresupuesto oldIdActividadPresupuestoOfActividadInsumoListNewActividadInsumo = actividadInsumoListNewActividadInsumo.getIdActividadPresupuesto();
                    actividadInsumoListNewActividadInsumo.setIdActividadPresupuesto(actividadPresupuesto);
                    actividadInsumoListNewActividadInsumo = em.merge(actividadInsumoListNewActividadInsumo);
                    if (oldIdActividadPresupuestoOfActividadInsumoListNewActividadInsumo != null && !oldIdActividadPresupuestoOfActividadInsumoListNewActividadInsumo.equals(actividadPresupuesto)) {
                        oldIdActividadPresupuestoOfActividadInsumoListNewActividadInsumo.getActividadInsumoList().remove(actividadInsumoListNewActividadInsumo);
                        oldIdActividadPresupuestoOfActividadInsumoListNewActividadInsumo = em.merge(oldIdActividadPresupuestoOfActividadInsumoListNewActividadInsumo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = actividadPresupuesto.getId();
                if (findActividadPresupuesto(id) == null) {
                    throw new NonexistentEntityException("The actividadPresupuesto with id " + id + " no longer exists.");
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
            ActividadPresupuesto actividadPresupuesto;
            try {
                actividadPresupuesto = em.getReference(ActividadPresupuesto.class, id);
                actividadPresupuesto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actividadPresupuesto with id " + id + " no longer exists.", enfe);
            }
            Presupuesto idPresupuesto = actividadPresupuesto.getIdPresupuesto();
            if (idPresupuesto != null) {
                idPresupuesto.getActividadPresupuestoList().remove(actividadPresupuesto);
                idPresupuesto = em.merge(idPresupuesto);
            }
            Actividad idActividad = actividadPresupuesto.getIdActividad();
            if (idActividad != null) {
                idActividad.getActividadPresupuestoList().remove(actividadPresupuesto);
                idActividad = em.merge(idActividad);
            }
            List<ActividadInsumo> actividadInsumoList = actividadPresupuesto.getActividadInsumoList();
            for (ActividadInsumo actividadInsumoListActividadInsumo : actividadInsumoList) {
                actividadInsumoListActividadInsumo.setIdActividadPresupuesto(null);
                actividadInsumoListActividadInsumo = em.merge(actividadInsumoListActividadInsumo);
            }
            em.remove(actividadPresupuesto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ActividadPresupuesto> findActividadPresupuestoEntities() {
        return findActividadPresupuestoEntities(true, -1, -1);
    }

    public List<ActividadPresupuesto> findActividadPresupuestoEntities(int maxResults, int firstResult) {
        return findActividadPresupuestoEntities(false, maxResults, firstResult);
    }

    private List<ActividadPresupuesto> findActividadPresupuestoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ActividadPresupuesto.class));
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

    public ActividadPresupuesto findActividadPresupuesto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ActividadPresupuesto.class, id);
        } finally {
            em.close();
        }
    }

    public int getActividadPresupuestoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ActividadPresupuesto> rt = cq.from(ActividadPresupuesto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
