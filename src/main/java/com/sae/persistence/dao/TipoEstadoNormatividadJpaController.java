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
import com.sae.persistence.domain.Normatividad;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.SeguimientoNormatividad;
import com.sae.persistence.domain.TipoEstadoNormatividad;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoEstadoNormatividadJpaController implements Serializable {

    public TipoEstadoNormatividadJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoEstadoNormatividad tipoEstadoNormatividad) throws PreexistingEntityException, Exception {
        if (tipoEstadoNormatividad.getNormatividadList() == null) {
            tipoEstadoNormatividad.setNormatividadList(new ArrayList<Normatividad>());
        }
        if (tipoEstadoNormatividad.getSeguimientoNormatividadList() == null) {
            tipoEstadoNormatividad.setSeguimientoNormatividadList(new ArrayList<SeguimientoNormatividad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Normatividad> attachedNormatividadList = new ArrayList<Normatividad>();
            for (Normatividad normatividadListNormatividadToAttach : tipoEstadoNormatividad.getNormatividadList()) {
                normatividadListNormatividadToAttach = em.getReference(normatividadListNormatividadToAttach.getClass(), normatividadListNormatividadToAttach.getId());
                attachedNormatividadList.add(normatividadListNormatividadToAttach);
            }
            tipoEstadoNormatividad.setNormatividadList(attachedNormatividadList);
            List<SeguimientoNormatividad> attachedSeguimientoNormatividadList = new ArrayList<SeguimientoNormatividad>();
            for (SeguimientoNormatividad seguimientoNormatividadListSeguimientoNormatividadToAttach : tipoEstadoNormatividad.getSeguimientoNormatividadList()) {
                seguimientoNormatividadListSeguimientoNormatividadToAttach = em.getReference(seguimientoNormatividadListSeguimientoNormatividadToAttach.getClass(), seguimientoNormatividadListSeguimientoNormatividadToAttach.getId());
                attachedSeguimientoNormatividadList.add(seguimientoNormatividadListSeguimientoNormatividadToAttach);
            }
            tipoEstadoNormatividad.setSeguimientoNormatividadList(attachedSeguimientoNormatividadList);
            em.persist(tipoEstadoNormatividad);
            for (Normatividad normatividadListNormatividad : tipoEstadoNormatividad.getNormatividadList()) {
                TipoEstadoNormatividad oldIdEstadoOfNormatividadListNormatividad = normatividadListNormatividad.getIdEstado();
                normatividadListNormatividad.setIdEstado(tipoEstadoNormatividad);
                normatividadListNormatividad = em.merge(normatividadListNormatividad);
                if (oldIdEstadoOfNormatividadListNormatividad != null) {
                    oldIdEstadoOfNormatividadListNormatividad.getNormatividadList().remove(normatividadListNormatividad);
                    oldIdEstadoOfNormatividadListNormatividad = em.merge(oldIdEstadoOfNormatividadListNormatividad);
                }
            }
            for (SeguimientoNormatividad seguimientoNormatividadListSeguimientoNormatividad : tipoEstadoNormatividad.getSeguimientoNormatividadList()) {
                TipoEstadoNormatividad oldIdEstadoOfSeguimientoNormatividadListSeguimientoNormatividad = seguimientoNormatividadListSeguimientoNormatividad.getIdEstado();
                seguimientoNormatividadListSeguimientoNormatividad.setIdEstado(tipoEstadoNormatividad);
                seguimientoNormatividadListSeguimientoNormatividad = em.merge(seguimientoNormatividadListSeguimientoNormatividad);
                if (oldIdEstadoOfSeguimientoNormatividadListSeguimientoNormatividad != null) {
                    oldIdEstadoOfSeguimientoNormatividadListSeguimientoNormatividad.getSeguimientoNormatividadList().remove(seguimientoNormatividadListSeguimientoNormatividad);
                    oldIdEstadoOfSeguimientoNormatividadListSeguimientoNormatividad = em.merge(oldIdEstadoOfSeguimientoNormatividadListSeguimientoNormatividad);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoEstadoNormatividad(tipoEstadoNormatividad.getId()) != null) {
                throw new PreexistingEntityException("TipoEstadoNormatividad " + tipoEstadoNormatividad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoEstadoNormatividad tipoEstadoNormatividad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoEstadoNormatividad persistentTipoEstadoNormatividad = em.find(TipoEstadoNormatividad.class, tipoEstadoNormatividad.getId());
            List<Normatividad> normatividadListOld = persistentTipoEstadoNormatividad.getNormatividadList();
            List<Normatividad> normatividadListNew = tipoEstadoNormatividad.getNormatividadList();
            List<SeguimientoNormatividad> seguimientoNormatividadListOld = persistentTipoEstadoNormatividad.getSeguimientoNormatividadList();
            List<SeguimientoNormatividad> seguimientoNormatividadListNew = tipoEstadoNormatividad.getSeguimientoNormatividadList();
            List<Normatividad> attachedNormatividadListNew = new ArrayList<Normatividad>();
            for (Normatividad normatividadListNewNormatividadToAttach : normatividadListNew) {
                normatividadListNewNormatividadToAttach = em.getReference(normatividadListNewNormatividadToAttach.getClass(), normatividadListNewNormatividadToAttach.getId());
                attachedNormatividadListNew.add(normatividadListNewNormatividadToAttach);
            }
            normatividadListNew = attachedNormatividadListNew;
            tipoEstadoNormatividad.setNormatividadList(normatividadListNew);
            List<SeguimientoNormatividad> attachedSeguimientoNormatividadListNew = new ArrayList<SeguimientoNormatividad>();
            for (SeguimientoNormatividad seguimientoNormatividadListNewSeguimientoNormatividadToAttach : seguimientoNormatividadListNew) {
                seguimientoNormatividadListNewSeguimientoNormatividadToAttach = em.getReference(seguimientoNormatividadListNewSeguimientoNormatividadToAttach.getClass(), seguimientoNormatividadListNewSeguimientoNormatividadToAttach.getId());
                attachedSeguimientoNormatividadListNew.add(seguimientoNormatividadListNewSeguimientoNormatividadToAttach);
            }
            seguimientoNormatividadListNew = attachedSeguimientoNormatividadListNew;
            tipoEstadoNormatividad.setSeguimientoNormatividadList(seguimientoNormatividadListNew);
            tipoEstadoNormatividad = em.merge(tipoEstadoNormatividad);
            for (Normatividad normatividadListOldNormatividad : normatividadListOld) {
                if (!normatividadListNew.contains(normatividadListOldNormatividad)) {
                    normatividadListOldNormatividad.setIdEstado(null);
                    normatividadListOldNormatividad = em.merge(normatividadListOldNormatividad);
                }
            }
            for (Normatividad normatividadListNewNormatividad : normatividadListNew) {
                if (!normatividadListOld.contains(normatividadListNewNormatividad)) {
                    TipoEstadoNormatividad oldIdEstadoOfNormatividadListNewNormatividad = normatividadListNewNormatividad.getIdEstado();
                    normatividadListNewNormatividad.setIdEstado(tipoEstadoNormatividad);
                    normatividadListNewNormatividad = em.merge(normatividadListNewNormatividad);
                    if (oldIdEstadoOfNormatividadListNewNormatividad != null && !oldIdEstadoOfNormatividadListNewNormatividad.equals(tipoEstadoNormatividad)) {
                        oldIdEstadoOfNormatividadListNewNormatividad.getNormatividadList().remove(normatividadListNewNormatividad);
                        oldIdEstadoOfNormatividadListNewNormatividad = em.merge(oldIdEstadoOfNormatividadListNewNormatividad);
                    }
                }
            }
            for (SeguimientoNormatividad seguimientoNormatividadListOldSeguimientoNormatividad : seguimientoNormatividadListOld) {
                if (!seguimientoNormatividadListNew.contains(seguimientoNormatividadListOldSeguimientoNormatividad)) {
                    seguimientoNormatividadListOldSeguimientoNormatividad.setIdEstado(null);
                    seguimientoNormatividadListOldSeguimientoNormatividad = em.merge(seguimientoNormatividadListOldSeguimientoNormatividad);
                }
            }
            for (SeguimientoNormatividad seguimientoNormatividadListNewSeguimientoNormatividad : seguimientoNormatividadListNew) {
                if (!seguimientoNormatividadListOld.contains(seguimientoNormatividadListNewSeguimientoNormatividad)) {
                    TipoEstadoNormatividad oldIdEstadoOfSeguimientoNormatividadListNewSeguimientoNormatividad = seguimientoNormatividadListNewSeguimientoNormatividad.getIdEstado();
                    seguimientoNormatividadListNewSeguimientoNormatividad.setIdEstado(tipoEstadoNormatividad);
                    seguimientoNormatividadListNewSeguimientoNormatividad = em.merge(seguimientoNormatividadListNewSeguimientoNormatividad);
                    if (oldIdEstadoOfSeguimientoNormatividadListNewSeguimientoNormatividad != null && !oldIdEstadoOfSeguimientoNormatividadListNewSeguimientoNormatividad.equals(tipoEstadoNormatividad)) {
                        oldIdEstadoOfSeguimientoNormatividadListNewSeguimientoNormatividad.getSeguimientoNormatividadList().remove(seguimientoNormatividadListNewSeguimientoNormatividad);
                        oldIdEstadoOfSeguimientoNormatividadListNewSeguimientoNormatividad = em.merge(oldIdEstadoOfSeguimientoNormatividadListNewSeguimientoNormatividad);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoEstadoNormatividad.getId();
                if (findTipoEstadoNormatividad(id) == null) {
                    throw new NonexistentEntityException("The tipoEstadoNormatividad with id " + id + " no longer exists.");
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
            TipoEstadoNormatividad tipoEstadoNormatividad;
            try {
                tipoEstadoNormatividad = em.getReference(TipoEstadoNormatividad.class, id);
                tipoEstadoNormatividad.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoEstadoNormatividad with id " + id + " no longer exists.", enfe);
            }
            List<Normatividad> normatividadList = tipoEstadoNormatividad.getNormatividadList();
            for (Normatividad normatividadListNormatividad : normatividadList) {
                normatividadListNormatividad.setIdEstado(null);
                normatividadListNormatividad = em.merge(normatividadListNormatividad);
            }
            List<SeguimientoNormatividad> seguimientoNormatividadList = tipoEstadoNormatividad.getSeguimientoNormatividadList();
            for (SeguimientoNormatividad seguimientoNormatividadListSeguimientoNormatividad : seguimientoNormatividadList) {
                seguimientoNormatividadListSeguimientoNormatividad.setIdEstado(null);
                seguimientoNormatividadListSeguimientoNormatividad = em.merge(seguimientoNormatividadListSeguimientoNormatividad);
            }
            em.remove(tipoEstadoNormatividad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoEstadoNormatividad> findTipoEstadoNormatividadEntities() {
        return findTipoEstadoNormatividadEntities(true, -1, -1);
    }

    public List<TipoEstadoNormatividad> findTipoEstadoNormatividadEntities(int maxResults, int firstResult) {
        return findTipoEstadoNormatividadEntities(false, maxResults, firstResult);
    }

    private List<TipoEstadoNormatividad> findTipoEstadoNormatividadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoEstadoNormatividad.class));
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

    public TipoEstadoNormatividad findTipoEstadoNormatividad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoEstadoNormatividad.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoEstadoNormatividadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoEstadoNormatividad> rt = cq.from(TipoEstadoNormatividad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
