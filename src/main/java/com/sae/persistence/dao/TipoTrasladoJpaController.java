/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.TipoTraslado;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.Traslado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoTrasladoJpaController implements Serializable {

    public TipoTrasladoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoTraslado tipoTraslado) throws PreexistingEntityException, Exception {
        if (tipoTraslado.getTrasladoList() == null) {
            tipoTraslado.setTrasladoList(new ArrayList<Traslado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Traslado> attachedTrasladoList = new ArrayList<Traslado>();
            for (Traslado trasladoListTrasladoToAttach : tipoTraslado.getTrasladoList()) {
                trasladoListTrasladoToAttach = em.getReference(trasladoListTrasladoToAttach.getClass(), trasladoListTrasladoToAttach.getId());
                attachedTrasladoList.add(trasladoListTrasladoToAttach);
            }
            tipoTraslado.setTrasladoList(attachedTrasladoList);
            em.persist(tipoTraslado);
            for (Traslado trasladoListTraslado : tipoTraslado.getTrasladoList()) {
                TipoTraslado oldIdTipoOfTrasladoListTraslado = trasladoListTraslado.getIdTipo();
                trasladoListTraslado.setIdTipo(tipoTraslado);
                trasladoListTraslado = em.merge(trasladoListTraslado);
                if (oldIdTipoOfTrasladoListTraslado != null) {
                    oldIdTipoOfTrasladoListTraslado.getTrasladoList().remove(trasladoListTraslado);
                    oldIdTipoOfTrasladoListTraslado = em.merge(oldIdTipoOfTrasladoListTraslado);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoTraslado(tipoTraslado.getId()) != null) {
                throw new PreexistingEntityException("TipoTraslado " + tipoTraslado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoTraslado tipoTraslado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoTraslado persistentTipoTraslado = em.find(TipoTraslado.class, tipoTraslado.getId());
            List<Traslado> trasladoListOld = persistentTipoTraslado.getTrasladoList();
            List<Traslado> trasladoListNew = tipoTraslado.getTrasladoList();
            List<Traslado> attachedTrasladoListNew = new ArrayList<Traslado>();
            for (Traslado trasladoListNewTrasladoToAttach : trasladoListNew) {
                trasladoListNewTrasladoToAttach = em.getReference(trasladoListNewTrasladoToAttach.getClass(), trasladoListNewTrasladoToAttach.getId());
                attachedTrasladoListNew.add(trasladoListNewTrasladoToAttach);
            }
            trasladoListNew = attachedTrasladoListNew;
            tipoTraslado.setTrasladoList(trasladoListNew);
            tipoTraslado = em.merge(tipoTraslado);
            for (Traslado trasladoListOldTraslado : trasladoListOld) {
                if (!trasladoListNew.contains(trasladoListOldTraslado)) {
                    trasladoListOldTraslado.setIdTipo(null);
                    trasladoListOldTraslado = em.merge(trasladoListOldTraslado);
                }
            }
            for (Traslado trasladoListNewTraslado : trasladoListNew) {
                if (!trasladoListOld.contains(trasladoListNewTraslado)) {
                    TipoTraslado oldIdTipoOfTrasladoListNewTraslado = trasladoListNewTraslado.getIdTipo();
                    trasladoListNewTraslado.setIdTipo(tipoTraslado);
                    trasladoListNewTraslado = em.merge(trasladoListNewTraslado);
                    if (oldIdTipoOfTrasladoListNewTraslado != null && !oldIdTipoOfTrasladoListNewTraslado.equals(tipoTraslado)) {
                        oldIdTipoOfTrasladoListNewTraslado.getTrasladoList().remove(trasladoListNewTraslado);
                        oldIdTipoOfTrasladoListNewTraslado = em.merge(oldIdTipoOfTrasladoListNewTraslado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoTraslado.getId();
                if (findTipoTraslado(id) == null) {
                    throw new NonexistentEntityException("The tipoTraslado with id " + id + " no longer exists.");
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
            TipoTraslado tipoTraslado;
            try {
                tipoTraslado = em.getReference(TipoTraslado.class, id);
                tipoTraslado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoTraslado with id " + id + " no longer exists.", enfe);
            }
            List<Traslado> trasladoList = tipoTraslado.getTrasladoList();
            for (Traslado trasladoListTraslado : trasladoList) {
                trasladoListTraslado.setIdTipo(null);
                trasladoListTraslado = em.merge(trasladoListTraslado);
            }
            em.remove(tipoTraslado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoTraslado> findTipoTrasladoEntities() {
        return findTipoTrasladoEntities(true, -1, -1);
    }

    public List<TipoTraslado> findTipoTrasladoEntities(int maxResults, int firstResult) {
        return findTipoTrasladoEntities(false, maxResults, firstResult);
    }

    private List<TipoTraslado> findTipoTrasladoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoTraslado.class));
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

    public TipoTraslado findTipoTraslado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoTraslado.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoTrasladoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoTraslado> rt = cq.from(TipoTraslado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
