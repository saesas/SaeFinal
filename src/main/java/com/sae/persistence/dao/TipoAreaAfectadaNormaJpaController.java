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
import com.sae.persistence.domain.AreaAfectada;
import com.sae.persistence.domain.TipoAreaAfectadaNorma;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoAreaAfectadaNormaJpaController implements Serializable {

    public TipoAreaAfectadaNormaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoAreaAfectadaNorma tipoAreaAfectadaNorma) throws PreexistingEntityException, Exception {
        if (tipoAreaAfectadaNorma.getAreaAfectadaList() == null) {
            tipoAreaAfectadaNorma.setAreaAfectadaList(new ArrayList<AreaAfectada>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<AreaAfectada> attachedAreaAfectadaList = new ArrayList<AreaAfectada>();
            for (AreaAfectada areaAfectadaListAreaAfectadaToAttach : tipoAreaAfectadaNorma.getAreaAfectadaList()) {
                areaAfectadaListAreaAfectadaToAttach = em.getReference(areaAfectadaListAreaAfectadaToAttach.getClass(), areaAfectadaListAreaAfectadaToAttach.getId());
                attachedAreaAfectadaList.add(areaAfectadaListAreaAfectadaToAttach);
            }
            tipoAreaAfectadaNorma.setAreaAfectadaList(attachedAreaAfectadaList);
            em.persist(tipoAreaAfectadaNorma);
            for (AreaAfectada areaAfectadaListAreaAfectada : tipoAreaAfectadaNorma.getAreaAfectadaList()) {
                TipoAreaAfectadaNorma oldIdTipoOfAreaAfectadaListAreaAfectada = areaAfectadaListAreaAfectada.getIdTipo();
                areaAfectadaListAreaAfectada.setIdTipo(tipoAreaAfectadaNorma);
                areaAfectadaListAreaAfectada = em.merge(areaAfectadaListAreaAfectada);
                if (oldIdTipoOfAreaAfectadaListAreaAfectada != null) {
                    oldIdTipoOfAreaAfectadaListAreaAfectada.getAreaAfectadaList().remove(areaAfectadaListAreaAfectada);
                    oldIdTipoOfAreaAfectadaListAreaAfectada = em.merge(oldIdTipoOfAreaAfectadaListAreaAfectada);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoAreaAfectadaNorma(tipoAreaAfectadaNorma.getId()) != null) {
                throw new PreexistingEntityException("TipoAreaAfectadaNorma " + tipoAreaAfectadaNorma + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoAreaAfectadaNorma tipoAreaAfectadaNorma) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoAreaAfectadaNorma persistentTipoAreaAfectadaNorma = em.find(TipoAreaAfectadaNorma.class, tipoAreaAfectadaNorma.getId());
            List<AreaAfectada> areaAfectadaListOld = persistentTipoAreaAfectadaNorma.getAreaAfectadaList();
            List<AreaAfectada> areaAfectadaListNew = tipoAreaAfectadaNorma.getAreaAfectadaList();
            List<AreaAfectada> attachedAreaAfectadaListNew = new ArrayList<AreaAfectada>();
            for (AreaAfectada areaAfectadaListNewAreaAfectadaToAttach : areaAfectadaListNew) {
                areaAfectadaListNewAreaAfectadaToAttach = em.getReference(areaAfectadaListNewAreaAfectadaToAttach.getClass(), areaAfectadaListNewAreaAfectadaToAttach.getId());
                attachedAreaAfectadaListNew.add(areaAfectadaListNewAreaAfectadaToAttach);
            }
            areaAfectadaListNew = attachedAreaAfectadaListNew;
            tipoAreaAfectadaNorma.setAreaAfectadaList(areaAfectadaListNew);
            tipoAreaAfectadaNorma = em.merge(tipoAreaAfectadaNorma);
            for (AreaAfectada areaAfectadaListOldAreaAfectada : areaAfectadaListOld) {
                if (!areaAfectadaListNew.contains(areaAfectadaListOldAreaAfectada)) {
                    areaAfectadaListOldAreaAfectada.setIdTipo(null);
                    areaAfectadaListOldAreaAfectada = em.merge(areaAfectadaListOldAreaAfectada);
                }
            }
            for (AreaAfectada areaAfectadaListNewAreaAfectada : areaAfectadaListNew) {
                if (!areaAfectadaListOld.contains(areaAfectadaListNewAreaAfectada)) {
                    TipoAreaAfectadaNorma oldIdTipoOfAreaAfectadaListNewAreaAfectada = areaAfectadaListNewAreaAfectada.getIdTipo();
                    areaAfectadaListNewAreaAfectada.setIdTipo(tipoAreaAfectadaNorma);
                    areaAfectadaListNewAreaAfectada = em.merge(areaAfectadaListNewAreaAfectada);
                    if (oldIdTipoOfAreaAfectadaListNewAreaAfectada != null && !oldIdTipoOfAreaAfectadaListNewAreaAfectada.equals(tipoAreaAfectadaNorma)) {
                        oldIdTipoOfAreaAfectadaListNewAreaAfectada.getAreaAfectadaList().remove(areaAfectadaListNewAreaAfectada);
                        oldIdTipoOfAreaAfectadaListNewAreaAfectada = em.merge(oldIdTipoOfAreaAfectadaListNewAreaAfectada);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoAreaAfectadaNorma.getId();
                if (findTipoAreaAfectadaNorma(id) == null) {
                    throw new NonexistentEntityException("The tipoAreaAfectadaNorma with id " + id + " no longer exists.");
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
            TipoAreaAfectadaNorma tipoAreaAfectadaNorma;
            try {
                tipoAreaAfectadaNorma = em.getReference(TipoAreaAfectadaNorma.class, id);
                tipoAreaAfectadaNorma.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoAreaAfectadaNorma with id " + id + " no longer exists.", enfe);
            }
            List<AreaAfectada> areaAfectadaList = tipoAreaAfectadaNorma.getAreaAfectadaList();
            for (AreaAfectada areaAfectadaListAreaAfectada : areaAfectadaList) {
                areaAfectadaListAreaAfectada.setIdTipo(null);
                areaAfectadaListAreaAfectada = em.merge(areaAfectadaListAreaAfectada);
            }
            em.remove(tipoAreaAfectadaNorma);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoAreaAfectadaNorma> findTipoAreaAfectadaNormaEntities() {
        return findTipoAreaAfectadaNormaEntities(true, -1, -1);
    }

    public List<TipoAreaAfectadaNorma> findTipoAreaAfectadaNormaEntities(int maxResults, int firstResult) {
        return findTipoAreaAfectadaNormaEntities(false, maxResults, firstResult);
    }

    private List<TipoAreaAfectadaNorma> findTipoAreaAfectadaNormaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoAreaAfectadaNorma.class));
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

    public TipoAreaAfectadaNorma findTipoAreaAfectadaNorma(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoAreaAfectadaNorma.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoAreaAfectadaNormaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoAreaAfectadaNorma> rt = cq.from(TipoAreaAfectadaNorma.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
