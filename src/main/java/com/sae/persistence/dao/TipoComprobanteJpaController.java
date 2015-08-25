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
import com.sae.persistence.domain.Comprobante;
import com.sae.persistence.domain.TipoComprobante;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoComprobanteJpaController implements Serializable {

    public TipoComprobanteJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoComprobante tipoComprobante) throws PreexistingEntityException, Exception {
        if (tipoComprobante.getComprobanteList() == null) {
            tipoComprobante.setComprobanteList(new ArrayList<Comprobante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Comprobante> attachedComprobanteList = new ArrayList<Comprobante>();
            for (Comprobante comprobanteListComprobanteToAttach : tipoComprobante.getComprobanteList()) {
                comprobanteListComprobanteToAttach = em.getReference(comprobanteListComprobanteToAttach.getClass(), comprobanteListComprobanteToAttach.getId());
                attachedComprobanteList.add(comprobanteListComprobanteToAttach);
            }
            tipoComprobante.setComprobanteList(attachedComprobanteList);
            em.persist(tipoComprobante);
            for (Comprobante comprobanteListComprobante : tipoComprobante.getComprobanteList()) {
                TipoComprobante oldIdTipoOfComprobanteListComprobante = comprobanteListComprobante.getIdTipo();
                comprobanteListComprobante.setIdTipo(tipoComprobante);
                comprobanteListComprobante = em.merge(comprobanteListComprobante);
                if (oldIdTipoOfComprobanteListComprobante != null) {
                    oldIdTipoOfComprobanteListComprobante.getComprobanteList().remove(comprobanteListComprobante);
                    oldIdTipoOfComprobanteListComprobante = em.merge(oldIdTipoOfComprobanteListComprobante);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoComprobante(tipoComprobante.getId()) != null) {
                throw new PreexistingEntityException("TipoComprobante " + tipoComprobante + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoComprobante tipoComprobante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoComprobante persistentTipoComprobante = em.find(TipoComprobante.class, tipoComprobante.getId());
            List<Comprobante> comprobanteListOld = persistentTipoComprobante.getComprobanteList();
            List<Comprobante> comprobanteListNew = tipoComprobante.getComprobanteList();
            List<Comprobante> attachedComprobanteListNew = new ArrayList<Comprobante>();
            for (Comprobante comprobanteListNewComprobanteToAttach : comprobanteListNew) {
                comprobanteListNewComprobanteToAttach = em.getReference(comprobanteListNewComprobanteToAttach.getClass(), comprobanteListNewComprobanteToAttach.getId());
                attachedComprobanteListNew.add(comprobanteListNewComprobanteToAttach);
            }
            comprobanteListNew = attachedComprobanteListNew;
            tipoComprobante.setComprobanteList(comprobanteListNew);
            tipoComprobante = em.merge(tipoComprobante);
            for (Comprobante comprobanteListOldComprobante : comprobanteListOld) {
                if (!comprobanteListNew.contains(comprobanteListOldComprobante)) {
                    comprobanteListOldComprobante.setIdTipo(null);
                    comprobanteListOldComprobante = em.merge(comprobanteListOldComprobante);
                }
            }
            for (Comprobante comprobanteListNewComprobante : comprobanteListNew) {
                if (!comprobanteListOld.contains(comprobanteListNewComprobante)) {
                    TipoComprobante oldIdTipoOfComprobanteListNewComprobante = comprobanteListNewComprobante.getIdTipo();
                    comprobanteListNewComprobante.setIdTipo(tipoComprobante);
                    comprobanteListNewComprobante = em.merge(comprobanteListNewComprobante);
                    if (oldIdTipoOfComprobanteListNewComprobante != null && !oldIdTipoOfComprobanteListNewComprobante.equals(tipoComprobante)) {
                        oldIdTipoOfComprobanteListNewComprobante.getComprobanteList().remove(comprobanteListNewComprobante);
                        oldIdTipoOfComprobanteListNewComprobante = em.merge(oldIdTipoOfComprobanteListNewComprobante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoComprobante.getId();
                if (findTipoComprobante(id) == null) {
                    throw new NonexistentEntityException("The tipoComprobante with id " + id + " no longer exists.");
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
            TipoComprobante tipoComprobante;
            try {
                tipoComprobante = em.getReference(TipoComprobante.class, id);
                tipoComprobante.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoComprobante with id " + id + " no longer exists.", enfe);
            }
            List<Comprobante> comprobanteList = tipoComprobante.getComprobanteList();
            for (Comprobante comprobanteListComprobante : comprobanteList) {
                comprobanteListComprobante.setIdTipo(null);
                comprobanteListComprobante = em.merge(comprobanteListComprobante);
            }
            em.remove(tipoComprobante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoComprobante> findTipoComprobanteEntities() {
        return findTipoComprobanteEntities(true, -1, -1);
    }

    public List<TipoComprobante> findTipoComprobanteEntities(int maxResults, int firstResult) {
        return findTipoComprobanteEntities(false, maxResults, firstResult);
    }

    private List<TipoComprobante> findTipoComprobanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoComprobante.class));
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

    public TipoComprobante findTipoComprobante(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoComprobante.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoComprobanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoComprobante> rt = cq.from(TipoComprobante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
