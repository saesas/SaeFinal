/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.ActaCobro;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.Presupuesto;
import com.sae.persistence.domain.ActaCobroActividad;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ActaCobroJpaController implements Serializable {

    public ActaCobroJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ActaCobro actaCobro) throws PreexistingEntityException, Exception {
        if (actaCobro.getActaCobroActividadList() == null) {
            actaCobro.setActaCobroActividadList(new ArrayList<ActaCobroActividad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Presupuesto idPresupuesto = actaCobro.getIdPresupuesto();
            if (idPresupuesto != null) {
                idPresupuesto = em.getReference(idPresupuesto.getClass(), idPresupuesto.getId());
                actaCobro.setIdPresupuesto(idPresupuesto);
            }
            List<ActaCobroActividad> attachedActaCobroActividadList = new ArrayList<ActaCobroActividad>();
            for (ActaCobroActividad actaCobroActividadListActaCobroActividadToAttach : actaCobro.getActaCobroActividadList()) {
                actaCobroActividadListActaCobroActividadToAttach = em.getReference(actaCobroActividadListActaCobroActividadToAttach.getClass(), actaCobroActividadListActaCobroActividadToAttach.getId());
                attachedActaCobroActividadList.add(actaCobroActividadListActaCobroActividadToAttach);
            }
            actaCobro.setActaCobroActividadList(attachedActaCobroActividadList);
            em.persist(actaCobro);
            if (idPresupuesto != null) {
                idPresupuesto.getActaCobroList().add(actaCobro);
                idPresupuesto = em.merge(idPresupuesto);
            }
            for (ActaCobroActividad actaCobroActividadListActaCobroActividad : actaCobro.getActaCobroActividadList()) {
                ActaCobro oldIdActaCobroOfActaCobroActividadListActaCobroActividad = actaCobroActividadListActaCobroActividad.getIdActaCobro();
                actaCobroActividadListActaCobroActividad.setIdActaCobro(actaCobro);
                actaCobroActividadListActaCobroActividad = em.merge(actaCobroActividadListActaCobroActividad);
                if (oldIdActaCobroOfActaCobroActividadListActaCobroActividad != null) {
                    oldIdActaCobroOfActaCobroActividadListActaCobroActividad.getActaCobroActividadList().remove(actaCobroActividadListActaCobroActividad);
                    oldIdActaCobroOfActaCobroActividadListActaCobroActividad = em.merge(oldIdActaCobroOfActaCobroActividadListActaCobroActividad);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findActaCobro(actaCobro.getId()) != null) {
                throw new PreexistingEntityException("ActaCobro " + actaCobro + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ActaCobro actaCobro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ActaCobro persistentActaCobro = em.find(ActaCobro.class, actaCobro.getId());
            Presupuesto idPresupuestoOld = persistentActaCobro.getIdPresupuesto();
            Presupuesto idPresupuestoNew = actaCobro.getIdPresupuesto();
            List<ActaCobroActividad> actaCobroActividadListOld = persistentActaCobro.getActaCobroActividadList();
            List<ActaCobroActividad> actaCobroActividadListNew = actaCobro.getActaCobroActividadList();
            if (idPresupuestoNew != null) {
                idPresupuestoNew = em.getReference(idPresupuestoNew.getClass(), idPresupuestoNew.getId());
                actaCobro.setIdPresupuesto(idPresupuestoNew);
            }
            List<ActaCobroActividad> attachedActaCobroActividadListNew = new ArrayList<ActaCobroActividad>();
            for (ActaCobroActividad actaCobroActividadListNewActaCobroActividadToAttach : actaCobroActividadListNew) {
                actaCobroActividadListNewActaCobroActividadToAttach = em.getReference(actaCobroActividadListNewActaCobroActividadToAttach.getClass(), actaCobroActividadListNewActaCobroActividadToAttach.getId());
                attachedActaCobroActividadListNew.add(actaCobroActividadListNewActaCobroActividadToAttach);
            }
            actaCobroActividadListNew = attachedActaCobroActividadListNew;
            actaCobro.setActaCobroActividadList(actaCobroActividadListNew);
            actaCobro = em.merge(actaCobro);
            if (idPresupuestoOld != null && !idPresupuestoOld.equals(idPresupuestoNew)) {
                idPresupuestoOld.getActaCobroList().remove(actaCobro);
                idPresupuestoOld = em.merge(idPresupuestoOld);
            }
            if (idPresupuestoNew != null && !idPresupuestoNew.equals(idPresupuestoOld)) {
                idPresupuestoNew.getActaCobroList().add(actaCobro);
                idPresupuestoNew = em.merge(idPresupuestoNew);
            }
            for (ActaCobroActividad actaCobroActividadListOldActaCobroActividad : actaCobroActividadListOld) {
                if (!actaCobroActividadListNew.contains(actaCobroActividadListOldActaCobroActividad)) {
                    actaCobroActividadListOldActaCobroActividad.setIdActaCobro(null);
                    actaCobroActividadListOldActaCobroActividad = em.merge(actaCobroActividadListOldActaCobroActividad);
                }
            }
            for (ActaCobroActividad actaCobroActividadListNewActaCobroActividad : actaCobroActividadListNew) {
                if (!actaCobroActividadListOld.contains(actaCobroActividadListNewActaCobroActividad)) {
                    ActaCobro oldIdActaCobroOfActaCobroActividadListNewActaCobroActividad = actaCobroActividadListNewActaCobroActividad.getIdActaCobro();
                    actaCobroActividadListNewActaCobroActividad.setIdActaCobro(actaCobro);
                    actaCobroActividadListNewActaCobroActividad = em.merge(actaCobroActividadListNewActaCobroActividad);
                    if (oldIdActaCobroOfActaCobroActividadListNewActaCobroActividad != null && !oldIdActaCobroOfActaCobroActividadListNewActaCobroActividad.equals(actaCobro)) {
                        oldIdActaCobroOfActaCobroActividadListNewActaCobroActividad.getActaCobroActividadList().remove(actaCobroActividadListNewActaCobroActividad);
                        oldIdActaCobroOfActaCobroActividadListNewActaCobroActividad = em.merge(oldIdActaCobroOfActaCobroActividadListNewActaCobroActividad);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = actaCobro.getId();
                if (findActaCobro(id) == null) {
                    throw new NonexistentEntityException("The actaCobro with id " + id + " no longer exists.");
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
            ActaCobro actaCobro;
            try {
                actaCobro = em.getReference(ActaCobro.class, id);
                actaCobro.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actaCobro with id " + id + " no longer exists.", enfe);
            }
            Presupuesto idPresupuesto = actaCobro.getIdPresupuesto();
            if (idPresupuesto != null) {
                idPresupuesto.getActaCobroList().remove(actaCobro);
                idPresupuesto = em.merge(idPresupuesto);
            }
            List<ActaCobroActividad> actaCobroActividadList = actaCobro.getActaCobroActividadList();
            for (ActaCobroActividad actaCobroActividadListActaCobroActividad : actaCobroActividadList) {
                actaCobroActividadListActaCobroActividad.setIdActaCobro(null);
                actaCobroActividadListActaCobroActividad = em.merge(actaCobroActividadListActaCobroActividad);
            }
            em.remove(actaCobro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ActaCobro> findActaCobroEntities() {
        return findActaCobroEntities(true, -1, -1);
    }

    public List<ActaCobro> findActaCobroEntities(int maxResults, int firstResult) {
        return findActaCobroEntities(false, maxResults, firstResult);
    }

    private List<ActaCobro> findActaCobroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ActaCobro.class));
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

    public ActaCobro findActaCobro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ActaCobro.class, id);
        } finally {
            em.close();
        }
    }

    public int getActaCobroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ActaCobro> rt = cq.from(ActaCobro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
