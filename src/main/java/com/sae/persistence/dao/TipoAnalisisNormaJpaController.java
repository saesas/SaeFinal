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
import com.sae.persistence.domain.TipoAnalisisNorma;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoAnalisisNormaJpaController implements Serializable {

    public TipoAnalisisNormaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoAnalisisNorma tipoAnalisisNorma) throws PreexistingEntityException, Exception {
        if (tipoAnalisisNorma.getSeguimientoNormatividadList() == null) {
            tipoAnalisisNorma.setSeguimientoNormatividadList(new ArrayList<SeguimientoNormatividad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SeguimientoNormatividad> attachedSeguimientoNormatividadList = new ArrayList<SeguimientoNormatividad>();
            for (SeguimientoNormatividad seguimientoNormatividadListSeguimientoNormatividadToAttach : tipoAnalisisNorma.getSeguimientoNormatividadList()) {
                seguimientoNormatividadListSeguimientoNormatividadToAttach = em.getReference(seguimientoNormatividadListSeguimientoNormatividadToAttach.getClass(), seguimientoNormatividadListSeguimientoNormatividadToAttach.getId());
                attachedSeguimientoNormatividadList.add(seguimientoNormatividadListSeguimientoNormatividadToAttach);
            }
            tipoAnalisisNorma.setSeguimientoNormatividadList(attachedSeguimientoNormatividadList);
            em.persist(tipoAnalisisNorma);
            for (SeguimientoNormatividad seguimientoNormatividadListSeguimientoNormatividad : tipoAnalisisNorma.getSeguimientoNormatividadList()) {
                TipoAnalisisNorma oldIdTipoAnalisisOfSeguimientoNormatividadListSeguimientoNormatividad = seguimientoNormatividadListSeguimientoNormatividad.getIdTipoAnalisis();
                seguimientoNormatividadListSeguimientoNormatividad.setIdTipoAnalisis(tipoAnalisisNorma);
                seguimientoNormatividadListSeguimientoNormatividad = em.merge(seguimientoNormatividadListSeguimientoNormatividad);
                if (oldIdTipoAnalisisOfSeguimientoNormatividadListSeguimientoNormatividad != null) {
                    oldIdTipoAnalisisOfSeguimientoNormatividadListSeguimientoNormatividad.getSeguimientoNormatividadList().remove(seguimientoNormatividadListSeguimientoNormatividad);
                    oldIdTipoAnalisisOfSeguimientoNormatividadListSeguimientoNormatividad = em.merge(oldIdTipoAnalisisOfSeguimientoNormatividadListSeguimientoNormatividad);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoAnalisisNorma(tipoAnalisisNorma.getId()) != null) {
                throw new PreexistingEntityException("TipoAnalisisNorma " + tipoAnalisisNorma + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoAnalisisNorma tipoAnalisisNorma) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoAnalisisNorma persistentTipoAnalisisNorma = em.find(TipoAnalisisNorma.class, tipoAnalisisNorma.getId());
            List<SeguimientoNormatividad> seguimientoNormatividadListOld = persistentTipoAnalisisNorma.getSeguimientoNormatividadList();
            List<SeguimientoNormatividad> seguimientoNormatividadListNew = tipoAnalisisNorma.getSeguimientoNormatividadList();
            List<SeguimientoNormatividad> attachedSeguimientoNormatividadListNew = new ArrayList<SeguimientoNormatividad>();
            for (SeguimientoNormatividad seguimientoNormatividadListNewSeguimientoNormatividadToAttach : seguimientoNormatividadListNew) {
                seguimientoNormatividadListNewSeguimientoNormatividadToAttach = em.getReference(seguimientoNormatividadListNewSeguimientoNormatividadToAttach.getClass(), seguimientoNormatividadListNewSeguimientoNormatividadToAttach.getId());
                attachedSeguimientoNormatividadListNew.add(seguimientoNormatividadListNewSeguimientoNormatividadToAttach);
            }
            seguimientoNormatividadListNew = attachedSeguimientoNormatividadListNew;
            tipoAnalisisNorma.setSeguimientoNormatividadList(seguimientoNormatividadListNew);
            tipoAnalisisNorma = em.merge(tipoAnalisisNorma);
            for (SeguimientoNormatividad seguimientoNormatividadListOldSeguimientoNormatividad : seguimientoNormatividadListOld) {
                if (!seguimientoNormatividadListNew.contains(seguimientoNormatividadListOldSeguimientoNormatividad)) {
                    seguimientoNormatividadListOldSeguimientoNormatividad.setIdTipoAnalisis(null);
                    seguimientoNormatividadListOldSeguimientoNormatividad = em.merge(seguimientoNormatividadListOldSeguimientoNormatividad);
                }
            }
            for (SeguimientoNormatividad seguimientoNormatividadListNewSeguimientoNormatividad : seguimientoNormatividadListNew) {
                if (!seguimientoNormatividadListOld.contains(seguimientoNormatividadListNewSeguimientoNormatividad)) {
                    TipoAnalisisNorma oldIdTipoAnalisisOfSeguimientoNormatividadListNewSeguimientoNormatividad = seguimientoNormatividadListNewSeguimientoNormatividad.getIdTipoAnalisis();
                    seguimientoNormatividadListNewSeguimientoNormatividad.setIdTipoAnalisis(tipoAnalisisNorma);
                    seguimientoNormatividadListNewSeguimientoNormatividad = em.merge(seguimientoNormatividadListNewSeguimientoNormatividad);
                    if (oldIdTipoAnalisisOfSeguimientoNormatividadListNewSeguimientoNormatividad != null && !oldIdTipoAnalisisOfSeguimientoNormatividadListNewSeguimientoNormatividad.equals(tipoAnalisisNorma)) {
                        oldIdTipoAnalisisOfSeguimientoNormatividadListNewSeguimientoNormatividad.getSeguimientoNormatividadList().remove(seguimientoNormatividadListNewSeguimientoNormatividad);
                        oldIdTipoAnalisisOfSeguimientoNormatividadListNewSeguimientoNormatividad = em.merge(oldIdTipoAnalisisOfSeguimientoNormatividadListNewSeguimientoNormatividad);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoAnalisisNorma.getId();
                if (findTipoAnalisisNorma(id) == null) {
                    throw new NonexistentEntityException("The tipoAnalisisNorma with id " + id + " no longer exists.");
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
            TipoAnalisisNorma tipoAnalisisNorma;
            try {
                tipoAnalisisNorma = em.getReference(TipoAnalisisNorma.class, id);
                tipoAnalisisNorma.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoAnalisisNorma with id " + id + " no longer exists.", enfe);
            }
            List<SeguimientoNormatividad> seguimientoNormatividadList = tipoAnalisisNorma.getSeguimientoNormatividadList();
            for (SeguimientoNormatividad seguimientoNormatividadListSeguimientoNormatividad : seguimientoNormatividadList) {
                seguimientoNormatividadListSeguimientoNormatividad.setIdTipoAnalisis(null);
                seguimientoNormatividadListSeguimientoNormatividad = em.merge(seguimientoNormatividadListSeguimientoNormatividad);
            }
            em.remove(tipoAnalisisNorma);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoAnalisisNorma> findTipoAnalisisNormaEntities() {
        return findTipoAnalisisNormaEntities(true, -1, -1);
    }

    public List<TipoAnalisisNorma> findTipoAnalisisNormaEntities(int maxResults, int firstResult) {
        return findTipoAnalisisNormaEntities(false, maxResults, firstResult);
    }

    private List<TipoAnalisisNorma> findTipoAnalisisNormaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoAnalisisNorma.class));
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

    public TipoAnalisisNorma findTipoAnalisisNorma(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoAnalisisNorma.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoAnalisisNormaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoAnalisisNorma> rt = cq.from(TipoAnalisisNorma.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
