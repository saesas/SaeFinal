/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.Aiu;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.AiuPresupuesto;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.ItemAiu;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class AiuJpaController implements Serializable {

    public AiuJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Aiu aiu) throws PreexistingEntityException, Exception {
        if (aiu.getAiuPresupuestoList() == null) {
            aiu.setAiuPresupuestoList(new ArrayList<AiuPresupuesto>());
        }
        if (aiu.getItemAiuList() == null) {
            aiu.setItemAiuList(new ArrayList<ItemAiu>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<AiuPresupuesto> attachedAiuPresupuestoList = new ArrayList<AiuPresupuesto>();
            for (AiuPresupuesto aiuPresupuestoListAiuPresupuestoToAttach : aiu.getAiuPresupuestoList()) {
                aiuPresupuestoListAiuPresupuestoToAttach = em.getReference(aiuPresupuestoListAiuPresupuestoToAttach.getClass(), aiuPresupuestoListAiuPresupuestoToAttach.getId());
                attachedAiuPresupuestoList.add(aiuPresupuestoListAiuPresupuestoToAttach);
            }
            aiu.setAiuPresupuestoList(attachedAiuPresupuestoList);
            List<ItemAiu> attachedItemAiuList = new ArrayList<ItemAiu>();
            for (ItemAiu itemAiuListItemAiuToAttach : aiu.getItemAiuList()) {
                itemAiuListItemAiuToAttach = em.getReference(itemAiuListItemAiuToAttach.getClass(), itemAiuListItemAiuToAttach.getId());
                attachedItemAiuList.add(itemAiuListItemAiuToAttach);
            }
            aiu.setItemAiuList(attachedItemAiuList);
            em.persist(aiu);
            for (AiuPresupuesto aiuPresupuestoListAiuPresupuesto : aiu.getAiuPresupuestoList()) {
                Aiu oldIdTipoAiuOfAiuPresupuestoListAiuPresupuesto = aiuPresupuestoListAiuPresupuesto.getIdTipoAiu();
                aiuPresupuestoListAiuPresupuesto.setIdTipoAiu(aiu);
                aiuPresupuestoListAiuPresupuesto = em.merge(aiuPresupuestoListAiuPresupuesto);
                if (oldIdTipoAiuOfAiuPresupuestoListAiuPresupuesto != null) {
                    oldIdTipoAiuOfAiuPresupuestoListAiuPresupuesto.getAiuPresupuestoList().remove(aiuPresupuestoListAiuPresupuesto);
                    oldIdTipoAiuOfAiuPresupuestoListAiuPresupuesto = em.merge(oldIdTipoAiuOfAiuPresupuestoListAiuPresupuesto);
                }
            }
            for (ItemAiu itemAiuListItemAiu : aiu.getItemAiuList()) {
                Aiu oldIdTipoAiuOfItemAiuListItemAiu = itemAiuListItemAiu.getIdTipoAiu();
                itemAiuListItemAiu.setIdTipoAiu(aiu);
                itemAiuListItemAiu = em.merge(itemAiuListItemAiu);
                if (oldIdTipoAiuOfItemAiuListItemAiu != null) {
                    oldIdTipoAiuOfItemAiuListItemAiu.getItemAiuList().remove(itemAiuListItemAiu);
                    oldIdTipoAiuOfItemAiuListItemAiu = em.merge(oldIdTipoAiuOfItemAiuListItemAiu);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAiu(aiu.getId()) != null) {
                throw new PreexistingEntityException("Aiu " + aiu + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Aiu aiu) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Aiu persistentAiu = em.find(Aiu.class, aiu.getId());
            List<AiuPresupuesto> aiuPresupuestoListOld = persistentAiu.getAiuPresupuestoList();
            List<AiuPresupuesto> aiuPresupuestoListNew = aiu.getAiuPresupuestoList();
            List<ItemAiu> itemAiuListOld = persistentAiu.getItemAiuList();
            List<ItemAiu> itemAiuListNew = aiu.getItemAiuList();
            List<AiuPresupuesto> attachedAiuPresupuestoListNew = new ArrayList<AiuPresupuesto>();
            for (AiuPresupuesto aiuPresupuestoListNewAiuPresupuestoToAttach : aiuPresupuestoListNew) {
                aiuPresupuestoListNewAiuPresupuestoToAttach = em.getReference(aiuPresupuestoListNewAiuPresupuestoToAttach.getClass(), aiuPresupuestoListNewAiuPresupuestoToAttach.getId());
                attachedAiuPresupuestoListNew.add(aiuPresupuestoListNewAiuPresupuestoToAttach);
            }
            aiuPresupuestoListNew = attachedAiuPresupuestoListNew;
            aiu.setAiuPresupuestoList(aiuPresupuestoListNew);
            List<ItemAiu> attachedItemAiuListNew = new ArrayList<ItemAiu>();
            for (ItemAiu itemAiuListNewItemAiuToAttach : itemAiuListNew) {
                itemAiuListNewItemAiuToAttach = em.getReference(itemAiuListNewItemAiuToAttach.getClass(), itemAiuListNewItemAiuToAttach.getId());
                attachedItemAiuListNew.add(itemAiuListNewItemAiuToAttach);
            }
            itemAiuListNew = attachedItemAiuListNew;
            aiu.setItemAiuList(itemAiuListNew);
            aiu = em.merge(aiu);
            for (AiuPresupuesto aiuPresupuestoListOldAiuPresupuesto : aiuPresupuestoListOld) {
                if (!aiuPresupuestoListNew.contains(aiuPresupuestoListOldAiuPresupuesto)) {
                    aiuPresupuestoListOldAiuPresupuesto.setIdTipoAiu(null);
                    aiuPresupuestoListOldAiuPresupuesto = em.merge(aiuPresupuestoListOldAiuPresupuesto);
                }
            }
            for (AiuPresupuesto aiuPresupuestoListNewAiuPresupuesto : aiuPresupuestoListNew) {
                if (!aiuPresupuestoListOld.contains(aiuPresupuestoListNewAiuPresupuesto)) {
                    Aiu oldIdTipoAiuOfAiuPresupuestoListNewAiuPresupuesto = aiuPresupuestoListNewAiuPresupuesto.getIdTipoAiu();
                    aiuPresupuestoListNewAiuPresupuesto.setIdTipoAiu(aiu);
                    aiuPresupuestoListNewAiuPresupuesto = em.merge(aiuPresupuestoListNewAiuPresupuesto);
                    if (oldIdTipoAiuOfAiuPresupuestoListNewAiuPresupuesto != null && !oldIdTipoAiuOfAiuPresupuestoListNewAiuPresupuesto.equals(aiu)) {
                        oldIdTipoAiuOfAiuPresupuestoListNewAiuPresupuesto.getAiuPresupuestoList().remove(aiuPresupuestoListNewAiuPresupuesto);
                        oldIdTipoAiuOfAiuPresupuestoListNewAiuPresupuesto = em.merge(oldIdTipoAiuOfAiuPresupuestoListNewAiuPresupuesto);
                    }
                }
            }
            for (ItemAiu itemAiuListOldItemAiu : itemAiuListOld) {
                if (!itemAiuListNew.contains(itemAiuListOldItemAiu)) {
                    itemAiuListOldItemAiu.setIdTipoAiu(null);
                    itemAiuListOldItemAiu = em.merge(itemAiuListOldItemAiu);
                }
            }
            for (ItemAiu itemAiuListNewItemAiu : itemAiuListNew) {
                if (!itemAiuListOld.contains(itemAiuListNewItemAiu)) {
                    Aiu oldIdTipoAiuOfItemAiuListNewItemAiu = itemAiuListNewItemAiu.getIdTipoAiu();
                    itemAiuListNewItemAiu.setIdTipoAiu(aiu);
                    itemAiuListNewItemAiu = em.merge(itemAiuListNewItemAiu);
                    if (oldIdTipoAiuOfItemAiuListNewItemAiu != null && !oldIdTipoAiuOfItemAiuListNewItemAiu.equals(aiu)) {
                        oldIdTipoAiuOfItemAiuListNewItemAiu.getItemAiuList().remove(itemAiuListNewItemAiu);
                        oldIdTipoAiuOfItemAiuListNewItemAiu = em.merge(oldIdTipoAiuOfItemAiuListNewItemAiu);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = aiu.getId();
                if (findAiu(id) == null) {
                    throw new NonexistentEntityException("The aiu with id " + id + " no longer exists.");
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
            Aiu aiu;
            try {
                aiu = em.getReference(Aiu.class, id);
                aiu.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The aiu with id " + id + " no longer exists.", enfe);
            }
            List<AiuPresupuesto> aiuPresupuestoList = aiu.getAiuPresupuestoList();
            for (AiuPresupuesto aiuPresupuestoListAiuPresupuesto : aiuPresupuestoList) {
                aiuPresupuestoListAiuPresupuesto.setIdTipoAiu(null);
                aiuPresupuestoListAiuPresupuesto = em.merge(aiuPresupuestoListAiuPresupuesto);
            }
            List<ItemAiu> itemAiuList = aiu.getItemAiuList();
            for (ItemAiu itemAiuListItemAiu : itemAiuList) {
                itemAiuListItemAiu.setIdTipoAiu(null);
                itemAiuListItemAiu = em.merge(itemAiuListItemAiu);
            }
            em.remove(aiu);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Aiu> findAiuEntities() {
        return findAiuEntities(true, -1, -1);
    }

    public List<Aiu> findAiuEntities(int maxResults, int firstResult) {
        return findAiuEntities(false, maxResults, firstResult);
    }

    private List<Aiu> findAiuEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Aiu.class));
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

    public Aiu findAiu(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Aiu.class, id);
        } finally {
            em.close();
        }
    }

    public int getAiuCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Aiu> rt = cq.from(Aiu.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
