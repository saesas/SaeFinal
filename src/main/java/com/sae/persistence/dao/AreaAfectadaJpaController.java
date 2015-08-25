/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.AreaAfectada;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.TipoAreaAfectadaNorma;
import com.sae.persistence.domain.AreaAfectadaNormatividad;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class AreaAfectadaJpaController implements Serializable {

    public AreaAfectadaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AreaAfectada areaAfectada) throws PreexistingEntityException, Exception {
        if (areaAfectada.getAreaAfectadaNormatividadList() == null) {
            areaAfectada.setAreaAfectadaNormatividadList(new ArrayList<AreaAfectadaNormatividad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoAreaAfectadaNorma idTipo = areaAfectada.getIdTipo();
            if (idTipo != null) {
                idTipo = em.getReference(idTipo.getClass(), idTipo.getId());
                areaAfectada.setIdTipo(idTipo);
            }
            List<AreaAfectadaNormatividad> attachedAreaAfectadaNormatividadList = new ArrayList<AreaAfectadaNormatividad>();
            for (AreaAfectadaNormatividad areaAfectadaNormatividadListAreaAfectadaNormatividadToAttach : areaAfectada.getAreaAfectadaNormatividadList()) {
                areaAfectadaNormatividadListAreaAfectadaNormatividadToAttach = em.getReference(areaAfectadaNormatividadListAreaAfectadaNormatividadToAttach.getClass(), areaAfectadaNormatividadListAreaAfectadaNormatividadToAttach.getId());
                attachedAreaAfectadaNormatividadList.add(areaAfectadaNormatividadListAreaAfectadaNormatividadToAttach);
            }
            areaAfectada.setAreaAfectadaNormatividadList(attachedAreaAfectadaNormatividadList);
            em.persist(areaAfectada);
            if (idTipo != null) {
                idTipo.getAreaAfectadaList().add(areaAfectada);
                idTipo = em.merge(idTipo);
            }
            for (AreaAfectadaNormatividad areaAfectadaNormatividadListAreaAfectadaNormatividad : areaAfectada.getAreaAfectadaNormatividadList()) {
                AreaAfectada oldIdAreaOfAreaAfectadaNormatividadListAreaAfectadaNormatividad = areaAfectadaNormatividadListAreaAfectadaNormatividad.getIdArea();
                areaAfectadaNormatividadListAreaAfectadaNormatividad.setIdArea(areaAfectada);
                areaAfectadaNormatividadListAreaAfectadaNormatividad = em.merge(areaAfectadaNormatividadListAreaAfectadaNormatividad);
                if (oldIdAreaOfAreaAfectadaNormatividadListAreaAfectadaNormatividad != null) {
                    oldIdAreaOfAreaAfectadaNormatividadListAreaAfectadaNormatividad.getAreaAfectadaNormatividadList().remove(areaAfectadaNormatividadListAreaAfectadaNormatividad);
                    oldIdAreaOfAreaAfectadaNormatividadListAreaAfectadaNormatividad = em.merge(oldIdAreaOfAreaAfectadaNormatividadListAreaAfectadaNormatividad);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAreaAfectada(areaAfectada.getId()) != null) {
                throw new PreexistingEntityException("AreaAfectada " + areaAfectada + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AreaAfectada areaAfectada) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AreaAfectada persistentAreaAfectada = em.find(AreaAfectada.class, areaAfectada.getId());
            TipoAreaAfectadaNorma idTipoOld = persistentAreaAfectada.getIdTipo();
            TipoAreaAfectadaNorma idTipoNew = areaAfectada.getIdTipo();
            List<AreaAfectadaNormatividad> areaAfectadaNormatividadListOld = persistentAreaAfectada.getAreaAfectadaNormatividadList();
            List<AreaAfectadaNormatividad> areaAfectadaNormatividadListNew = areaAfectada.getAreaAfectadaNormatividadList();
            if (idTipoNew != null) {
                idTipoNew = em.getReference(idTipoNew.getClass(), idTipoNew.getId());
                areaAfectada.setIdTipo(idTipoNew);
            }
            List<AreaAfectadaNormatividad> attachedAreaAfectadaNormatividadListNew = new ArrayList<AreaAfectadaNormatividad>();
            for (AreaAfectadaNormatividad areaAfectadaNormatividadListNewAreaAfectadaNormatividadToAttach : areaAfectadaNormatividadListNew) {
                areaAfectadaNormatividadListNewAreaAfectadaNormatividadToAttach = em.getReference(areaAfectadaNormatividadListNewAreaAfectadaNormatividadToAttach.getClass(), areaAfectadaNormatividadListNewAreaAfectadaNormatividadToAttach.getId());
                attachedAreaAfectadaNormatividadListNew.add(areaAfectadaNormatividadListNewAreaAfectadaNormatividadToAttach);
            }
            areaAfectadaNormatividadListNew = attachedAreaAfectadaNormatividadListNew;
            areaAfectada.setAreaAfectadaNormatividadList(areaAfectadaNormatividadListNew);
            areaAfectada = em.merge(areaAfectada);
            if (idTipoOld != null && !idTipoOld.equals(idTipoNew)) {
                idTipoOld.getAreaAfectadaList().remove(areaAfectada);
                idTipoOld = em.merge(idTipoOld);
            }
            if (idTipoNew != null && !idTipoNew.equals(idTipoOld)) {
                idTipoNew.getAreaAfectadaList().add(areaAfectada);
                idTipoNew = em.merge(idTipoNew);
            }
            for (AreaAfectadaNormatividad areaAfectadaNormatividadListOldAreaAfectadaNormatividad : areaAfectadaNormatividadListOld) {
                if (!areaAfectadaNormatividadListNew.contains(areaAfectadaNormatividadListOldAreaAfectadaNormatividad)) {
                    areaAfectadaNormatividadListOldAreaAfectadaNormatividad.setIdArea(null);
                    areaAfectadaNormatividadListOldAreaAfectadaNormatividad = em.merge(areaAfectadaNormatividadListOldAreaAfectadaNormatividad);
                }
            }
            for (AreaAfectadaNormatividad areaAfectadaNormatividadListNewAreaAfectadaNormatividad : areaAfectadaNormatividadListNew) {
                if (!areaAfectadaNormatividadListOld.contains(areaAfectadaNormatividadListNewAreaAfectadaNormatividad)) {
                    AreaAfectada oldIdAreaOfAreaAfectadaNormatividadListNewAreaAfectadaNormatividad = areaAfectadaNormatividadListNewAreaAfectadaNormatividad.getIdArea();
                    areaAfectadaNormatividadListNewAreaAfectadaNormatividad.setIdArea(areaAfectada);
                    areaAfectadaNormatividadListNewAreaAfectadaNormatividad = em.merge(areaAfectadaNormatividadListNewAreaAfectadaNormatividad);
                    if (oldIdAreaOfAreaAfectadaNormatividadListNewAreaAfectadaNormatividad != null && !oldIdAreaOfAreaAfectadaNormatividadListNewAreaAfectadaNormatividad.equals(areaAfectada)) {
                        oldIdAreaOfAreaAfectadaNormatividadListNewAreaAfectadaNormatividad.getAreaAfectadaNormatividadList().remove(areaAfectadaNormatividadListNewAreaAfectadaNormatividad);
                        oldIdAreaOfAreaAfectadaNormatividadListNewAreaAfectadaNormatividad = em.merge(oldIdAreaOfAreaAfectadaNormatividadListNewAreaAfectadaNormatividad);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = areaAfectada.getId();
                if (findAreaAfectada(id) == null) {
                    throw new NonexistentEntityException("The areaAfectada with id " + id + " no longer exists.");
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
            AreaAfectada areaAfectada;
            try {
                areaAfectada = em.getReference(AreaAfectada.class, id);
                areaAfectada.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The areaAfectada with id " + id + " no longer exists.", enfe);
            }
            TipoAreaAfectadaNorma idTipo = areaAfectada.getIdTipo();
            if (idTipo != null) {
                idTipo.getAreaAfectadaList().remove(areaAfectada);
                idTipo = em.merge(idTipo);
            }
            List<AreaAfectadaNormatividad> areaAfectadaNormatividadList = areaAfectada.getAreaAfectadaNormatividadList();
            for (AreaAfectadaNormatividad areaAfectadaNormatividadListAreaAfectadaNormatividad : areaAfectadaNormatividadList) {
                areaAfectadaNormatividadListAreaAfectadaNormatividad.setIdArea(null);
                areaAfectadaNormatividadListAreaAfectadaNormatividad = em.merge(areaAfectadaNormatividadListAreaAfectadaNormatividad);
            }
            em.remove(areaAfectada);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AreaAfectada> findAreaAfectadaEntities() {
        return findAreaAfectadaEntities(true, -1, -1);
    }

    public List<AreaAfectada> findAreaAfectadaEntities(int maxResults, int firstResult) {
        return findAreaAfectadaEntities(false, maxResults, firstResult);
    }

    private List<AreaAfectada> findAreaAfectadaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AreaAfectada.class));
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

    public AreaAfectada findAreaAfectada(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AreaAfectada.class, id);
        } finally {
            em.close();
        }
    }

    public int getAreaAfectadaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AreaAfectada> rt = cq.from(AreaAfectada.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
