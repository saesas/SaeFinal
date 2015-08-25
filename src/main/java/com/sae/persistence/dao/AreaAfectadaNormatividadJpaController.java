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
import com.sae.persistence.domain.SeguimientoNormatividad;
import com.sae.persistence.domain.AreaAfectada;
import com.sae.persistence.domain.AreaAfectadaNormatividad;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class AreaAfectadaNormatividadJpaController implements Serializable {

    public AreaAfectadaNormatividadJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AreaAfectadaNormatividad areaAfectadaNormatividad) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SeguimientoNormatividad idSeguimiento = areaAfectadaNormatividad.getIdSeguimiento();
            if (idSeguimiento != null) {
                idSeguimiento = em.getReference(idSeguimiento.getClass(), idSeguimiento.getId());
                areaAfectadaNormatividad.setIdSeguimiento(idSeguimiento);
            }
            AreaAfectada idArea = areaAfectadaNormatividad.getIdArea();
            if (idArea != null) {
                idArea = em.getReference(idArea.getClass(), idArea.getId());
                areaAfectadaNormatividad.setIdArea(idArea);
            }
            em.persist(areaAfectadaNormatividad);
            if (idSeguimiento != null) {
                idSeguimiento.getAreaAfectadaNormatividadList().add(areaAfectadaNormatividad);
                idSeguimiento = em.merge(idSeguimiento);
            }
            if (idArea != null) {
                idArea.getAreaAfectadaNormatividadList().add(areaAfectadaNormatividad);
                idArea = em.merge(idArea);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAreaAfectadaNormatividad(areaAfectadaNormatividad.getId()) != null) {
                throw new PreexistingEntityException("AreaAfectadaNormatividad " + areaAfectadaNormatividad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AreaAfectadaNormatividad areaAfectadaNormatividad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AreaAfectadaNormatividad persistentAreaAfectadaNormatividad = em.find(AreaAfectadaNormatividad.class, areaAfectadaNormatividad.getId());
            SeguimientoNormatividad idSeguimientoOld = persistentAreaAfectadaNormatividad.getIdSeguimiento();
            SeguimientoNormatividad idSeguimientoNew = areaAfectadaNormatividad.getIdSeguimiento();
            AreaAfectada idAreaOld = persistentAreaAfectadaNormatividad.getIdArea();
            AreaAfectada idAreaNew = areaAfectadaNormatividad.getIdArea();
            if (idSeguimientoNew != null) {
                idSeguimientoNew = em.getReference(idSeguimientoNew.getClass(), idSeguimientoNew.getId());
                areaAfectadaNormatividad.setIdSeguimiento(idSeguimientoNew);
            }
            if (idAreaNew != null) {
                idAreaNew = em.getReference(idAreaNew.getClass(), idAreaNew.getId());
                areaAfectadaNormatividad.setIdArea(idAreaNew);
            }
            areaAfectadaNormatividad = em.merge(areaAfectadaNormatividad);
            if (idSeguimientoOld != null && !idSeguimientoOld.equals(idSeguimientoNew)) {
                idSeguimientoOld.getAreaAfectadaNormatividadList().remove(areaAfectadaNormatividad);
                idSeguimientoOld = em.merge(idSeguimientoOld);
            }
            if (idSeguimientoNew != null && !idSeguimientoNew.equals(idSeguimientoOld)) {
                idSeguimientoNew.getAreaAfectadaNormatividadList().add(areaAfectadaNormatividad);
                idSeguimientoNew = em.merge(idSeguimientoNew);
            }
            if (idAreaOld != null && !idAreaOld.equals(idAreaNew)) {
                idAreaOld.getAreaAfectadaNormatividadList().remove(areaAfectadaNormatividad);
                idAreaOld = em.merge(idAreaOld);
            }
            if (idAreaNew != null && !idAreaNew.equals(idAreaOld)) {
                idAreaNew.getAreaAfectadaNormatividadList().add(areaAfectadaNormatividad);
                idAreaNew = em.merge(idAreaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = areaAfectadaNormatividad.getId();
                if (findAreaAfectadaNormatividad(id) == null) {
                    throw new NonexistentEntityException("The areaAfectadaNormatividad with id " + id + " no longer exists.");
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
            AreaAfectadaNormatividad areaAfectadaNormatividad;
            try {
                areaAfectadaNormatividad = em.getReference(AreaAfectadaNormatividad.class, id);
                areaAfectadaNormatividad.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The areaAfectadaNormatividad with id " + id + " no longer exists.", enfe);
            }
            SeguimientoNormatividad idSeguimiento = areaAfectadaNormatividad.getIdSeguimiento();
            if (idSeguimiento != null) {
                idSeguimiento.getAreaAfectadaNormatividadList().remove(areaAfectadaNormatividad);
                idSeguimiento = em.merge(idSeguimiento);
            }
            AreaAfectada idArea = areaAfectadaNormatividad.getIdArea();
            if (idArea != null) {
                idArea.getAreaAfectadaNormatividadList().remove(areaAfectadaNormatividad);
                idArea = em.merge(idArea);
            }
            em.remove(areaAfectadaNormatividad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AreaAfectadaNormatividad> findAreaAfectadaNormatividadEntities() {
        return findAreaAfectadaNormatividadEntities(true, -1, -1);
    }

    public List<AreaAfectadaNormatividad> findAreaAfectadaNormatividadEntities(int maxResults, int firstResult) {
        return findAreaAfectadaNormatividadEntities(false, maxResults, firstResult);
    }

    private List<AreaAfectadaNormatividad> findAreaAfectadaNormatividadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AreaAfectadaNormatividad.class));
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

    public AreaAfectadaNormatividad findAreaAfectadaNormatividad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AreaAfectadaNormatividad.class, id);
        } finally {
            em.close();
        }
    }

    public int getAreaAfectadaNormatividadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AreaAfectadaNormatividad> rt = cq.from(AreaAfectadaNormatividad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
