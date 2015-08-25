/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.TarifaIva;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.TipoTarifaPuc;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TarifaIvaJpaController implements Serializable {

    public TarifaIvaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TarifaIva tarifaIva) throws PreexistingEntityException, Exception {
        if (tarifaIva.getTipoTarifaPucList() == null) {
            tarifaIva.setTipoTarifaPucList(new ArrayList<TipoTarifaPuc>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<TipoTarifaPuc> attachedTipoTarifaPucList = new ArrayList<TipoTarifaPuc>();
            for (TipoTarifaPuc tipoTarifaPucListTipoTarifaPucToAttach : tarifaIva.getTipoTarifaPucList()) {
                tipoTarifaPucListTipoTarifaPucToAttach = em.getReference(tipoTarifaPucListTipoTarifaPucToAttach.getClass(), tipoTarifaPucListTipoTarifaPucToAttach.getId());
                attachedTipoTarifaPucList.add(tipoTarifaPucListTipoTarifaPucToAttach);
            }
            tarifaIva.setTipoTarifaPucList(attachedTipoTarifaPucList);
            em.persist(tarifaIva);
            for (TipoTarifaPuc tipoTarifaPucListTipoTarifaPuc : tarifaIva.getTipoTarifaPucList()) {
                TarifaIva oldIdTarifaIvaOfTipoTarifaPucListTipoTarifaPuc = tipoTarifaPucListTipoTarifaPuc.getIdTarifaIva();
                tipoTarifaPucListTipoTarifaPuc.setIdTarifaIva(tarifaIva);
                tipoTarifaPucListTipoTarifaPuc = em.merge(tipoTarifaPucListTipoTarifaPuc);
                if (oldIdTarifaIvaOfTipoTarifaPucListTipoTarifaPuc != null) {
                    oldIdTarifaIvaOfTipoTarifaPucListTipoTarifaPuc.getTipoTarifaPucList().remove(tipoTarifaPucListTipoTarifaPuc);
                    oldIdTarifaIvaOfTipoTarifaPucListTipoTarifaPuc = em.merge(oldIdTarifaIvaOfTipoTarifaPucListTipoTarifaPuc);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTarifaIva(tarifaIva.getId()) != null) {
                throw new PreexistingEntityException("TarifaIva " + tarifaIva + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TarifaIva tarifaIva) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TarifaIva persistentTarifaIva = em.find(TarifaIva.class, tarifaIva.getId());
            List<TipoTarifaPuc> tipoTarifaPucListOld = persistentTarifaIva.getTipoTarifaPucList();
            List<TipoTarifaPuc> tipoTarifaPucListNew = tarifaIva.getTipoTarifaPucList();
            List<TipoTarifaPuc> attachedTipoTarifaPucListNew = new ArrayList<TipoTarifaPuc>();
            for (TipoTarifaPuc tipoTarifaPucListNewTipoTarifaPucToAttach : tipoTarifaPucListNew) {
                tipoTarifaPucListNewTipoTarifaPucToAttach = em.getReference(tipoTarifaPucListNewTipoTarifaPucToAttach.getClass(), tipoTarifaPucListNewTipoTarifaPucToAttach.getId());
                attachedTipoTarifaPucListNew.add(tipoTarifaPucListNewTipoTarifaPucToAttach);
            }
            tipoTarifaPucListNew = attachedTipoTarifaPucListNew;
            tarifaIva.setTipoTarifaPucList(tipoTarifaPucListNew);
            tarifaIva = em.merge(tarifaIva);
            for (TipoTarifaPuc tipoTarifaPucListOldTipoTarifaPuc : tipoTarifaPucListOld) {
                if (!tipoTarifaPucListNew.contains(tipoTarifaPucListOldTipoTarifaPuc)) {
                    tipoTarifaPucListOldTipoTarifaPuc.setIdTarifaIva(null);
                    tipoTarifaPucListOldTipoTarifaPuc = em.merge(tipoTarifaPucListOldTipoTarifaPuc);
                }
            }
            for (TipoTarifaPuc tipoTarifaPucListNewTipoTarifaPuc : tipoTarifaPucListNew) {
                if (!tipoTarifaPucListOld.contains(tipoTarifaPucListNewTipoTarifaPuc)) {
                    TarifaIva oldIdTarifaIvaOfTipoTarifaPucListNewTipoTarifaPuc = tipoTarifaPucListNewTipoTarifaPuc.getIdTarifaIva();
                    tipoTarifaPucListNewTipoTarifaPuc.setIdTarifaIva(tarifaIva);
                    tipoTarifaPucListNewTipoTarifaPuc = em.merge(tipoTarifaPucListNewTipoTarifaPuc);
                    if (oldIdTarifaIvaOfTipoTarifaPucListNewTipoTarifaPuc != null && !oldIdTarifaIvaOfTipoTarifaPucListNewTipoTarifaPuc.equals(tarifaIva)) {
                        oldIdTarifaIvaOfTipoTarifaPucListNewTipoTarifaPuc.getTipoTarifaPucList().remove(tipoTarifaPucListNewTipoTarifaPuc);
                        oldIdTarifaIvaOfTipoTarifaPucListNewTipoTarifaPuc = em.merge(oldIdTarifaIvaOfTipoTarifaPucListNewTipoTarifaPuc);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tarifaIva.getId();
                if (findTarifaIva(id) == null) {
                    throw new NonexistentEntityException("The tarifaIva with id " + id + " no longer exists.");
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
            TarifaIva tarifaIva;
            try {
                tarifaIva = em.getReference(TarifaIva.class, id);
                tarifaIva.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tarifaIva with id " + id + " no longer exists.", enfe);
            }
            List<TipoTarifaPuc> tipoTarifaPucList = tarifaIva.getTipoTarifaPucList();
            for (TipoTarifaPuc tipoTarifaPucListTipoTarifaPuc : tipoTarifaPucList) {
                tipoTarifaPucListTipoTarifaPuc.setIdTarifaIva(null);
                tipoTarifaPucListTipoTarifaPuc = em.merge(tipoTarifaPucListTipoTarifaPuc);
            }
            em.remove(tarifaIva);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TarifaIva> findTarifaIvaEntities() {
        return findTarifaIvaEntities(true, -1, -1);
    }

    public List<TarifaIva> findTarifaIvaEntities(int maxResults, int firstResult) {
        return findTarifaIvaEntities(false, maxResults, firstResult);
    }

    private List<TarifaIva> findTarifaIvaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TarifaIva.class));
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

    public TarifaIva findTarifaIva(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TarifaIva.class, id);
        } finally {
            em.close();
        }
    }

    public int getTarifaIvaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TarifaIva> rt = cq.from(TarifaIva.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
