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
import com.sae.persistence.domain.Aiu;
import com.sae.persistence.domain.AiuEspecificoPresupuesto;
import com.sae.persistence.domain.ItemAiu;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ItemAiuJpaController implements Serializable {

    public ItemAiuJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ItemAiu itemAiu) throws PreexistingEntityException, Exception {
        if (itemAiu.getAiuEspecificoPresupuestoList() == null) {
            itemAiu.setAiuEspecificoPresupuestoList(new ArrayList<AiuEspecificoPresupuesto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Aiu idTipoAiu = itemAiu.getIdTipoAiu();
            if (idTipoAiu != null) {
                idTipoAiu = em.getReference(idTipoAiu.getClass(), idTipoAiu.getId());
                itemAiu.setIdTipoAiu(idTipoAiu);
            }
            List<AiuEspecificoPresupuesto> attachedAiuEspecificoPresupuestoList = new ArrayList<AiuEspecificoPresupuesto>();
            for (AiuEspecificoPresupuesto aiuEspecificoPresupuestoListAiuEspecificoPresupuestoToAttach : itemAiu.getAiuEspecificoPresupuestoList()) {
                aiuEspecificoPresupuestoListAiuEspecificoPresupuestoToAttach = em.getReference(aiuEspecificoPresupuestoListAiuEspecificoPresupuestoToAttach.getClass(), aiuEspecificoPresupuestoListAiuEspecificoPresupuestoToAttach.getId());
                attachedAiuEspecificoPresupuestoList.add(aiuEspecificoPresupuestoListAiuEspecificoPresupuestoToAttach);
            }
            itemAiu.setAiuEspecificoPresupuestoList(attachedAiuEspecificoPresupuestoList);
            em.persist(itemAiu);
            if (idTipoAiu != null) {
                idTipoAiu.getItemAiuList().add(itemAiu);
                idTipoAiu = em.merge(idTipoAiu);
            }
            for (AiuEspecificoPresupuesto aiuEspecificoPresupuestoListAiuEspecificoPresupuesto : itemAiu.getAiuEspecificoPresupuestoList()) {
                ItemAiu oldIdItemAiuOfAiuEspecificoPresupuestoListAiuEspecificoPresupuesto = aiuEspecificoPresupuestoListAiuEspecificoPresupuesto.getIdItemAiu();
                aiuEspecificoPresupuestoListAiuEspecificoPresupuesto.setIdItemAiu(itemAiu);
                aiuEspecificoPresupuestoListAiuEspecificoPresupuesto = em.merge(aiuEspecificoPresupuestoListAiuEspecificoPresupuesto);
                if (oldIdItemAiuOfAiuEspecificoPresupuestoListAiuEspecificoPresupuesto != null) {
                    oldIdItemAiuOfAiuEspecificoPresupuestoListAiuEspecificoPresupuesto.getAiuEspecificoPresupuestoList().remove(aiuEspecificoPresupuestoListAiuEspecificoPresupuesto);
                    oldIdItemAiuOfAiuEspecificoPresupuestoListAiuEspecificoPresupuesto = em.merge(oldIdItemAiuOfAiuEspecificoPresupuestoListAiuEspecificoPresupuesto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findItemAiu(itemAiu.getId()) != null) {
                throw new PreexistingEntityException("ItemAiu " + itemAiu + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ItemAiu itemAiu) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ItemAiu persistentItemAiu = em.find(ItemAiu.class, itemAiu.getId());
            Aiu idTipoAiuOld = persistentItemAiu.getIdTipoAiu();
            Aiu idTipoAiuNew = itemAiu.getIdTipoAiu();
            List<AiuEspecificoPresupuesto> aiuEspecificoPresupuestoListOld = persistentItemAiu.getAiuEspecificoPresupuestoList();
            List<AiuEspecificoPresupuesto> aiuEspecificoPresupuestoListNew = itemAiu.getAiuEspecificoPresupuestoList();
            if (idTipoAiuNew != null) {
                idTipoAiuNew = em.getReference(idTipoAiuNew.getClass(), idTipoAiuNew.getId());
                itemAiu.setIdTipoAiu(idTipoAiuNew);
            }
            List<AiuEspecificoPresupuesto> attachedAiuEspecificoPresupuestoListNew = new ArrayList<AiuEspecificoPresupuesto>();
            for (AiuEspecificoPresupuesto aiuEspecificoPresupuestoListNewAiuEspecificoPresupuestoToAttach : aiuEspecificoPresupuestoListNew) {
                aiuEspecificoPresupuestoListNewAiuEspecificoPresupuestoToAttach = em.getReference(aiuEspecificoPresupuestoListNewAiuEspecificoPresupuestoToAttach.getClass(), aiuEspecificoPresupuestoListNewAiuEspecificoPresupuestoToAttach.getId());
                attachedAiuEspecificoPresupuestoListNew.add(aiuEspecificoPresupuestoListNewAiuEspecificoPresupuestoToAttach);
            }
            aiuEspecificoPresupuestoListNew = attachedAiuEspecificoPresupuestoListNew;
            itemAiu.setAiuEspecificoPresupuestoList(aiuEspecificoPresupuestoListNew);
            itemAiu = em.merge(itemAiu);
            if (idTipoAiuOld != null && !idTipoAiuOld.equals(idTipoAiuNew)) {
                idTipoAiuOld.getItemAiuList().remove(itemAiu);
                idTipoAiuOld = em.merge(idTipoAiuOld);
            }
            if (idTipoAiuNew != null && !idTipoAiuNew.equals(idTipoAiuOld)) {
                idTipoAiuNew.getItemAiuList().add(itemAiu);
                idTipoAiuNew = em.merge(idTipoAiuNew);
            }
            for (AiuEspecificoPresupuesto aiuEspecificoPresupuestoListOldAiuEspecificoPresupuesto : aiuEspecificoPresupuestoListOld) {
                if (!aiuEspecificoPresupuestoListNew.contains(aiuEspecificoPresupuestoListOldAiuEspecificoPresupuesto)) {
                    aiuEspecificoPresupuestoListOldAiuEspecificoPresupuesto.setIdItemAiu(null);
                    aiuEspecificoPresupuestoListOldAiuEspecificoPresupuesto = em.merge(aiuEspecificoPresupuestoListOldAiuEspecificoPresupuesto);
                }
            }
            for (AiuEspecificoPresupuesto aiuEspecificoPresupuestoListNewAiuEspecificoPresupuesto : aiuEspecificoPresupuestoListNew) {
                if (!aiuEspecificoPresupuestoListOld.contains(aiuEspecificoPresupuestoListNewAiuEspecificoPresupuesto)) {
                    ItemAiu oldIdItemAiuOfAiuEspecificoPresupuestoListNewAiuEspecificoPresupuesto = aiuEspecificoPresupuestoListNewAiuEspecificoPresupuesto.getIdItemAiu();
                    aiuEspecificoPresupuestoListNewAiuEspecificoPresupuesto.setIdItemAiu(itemAiu);
                    aiuEspecificoPresupuestoListNewAiuEspecificoPresupuesto = em.merge(aiuEspecificoPresupuestoListNewAiuEspecificoPresupuesto);
                    if (oldIdItemAiuOfAiuEspecificoPresupuestoListNewAiuEspecificoPresupuesto != null && !oldIdItemAiuOfAiuEspecificoPresupuestoListNewAiuEspecificoPresupuesto.equals(itemAiu)) {
                        oldIdItemAiuOfAiuEspecificoPresupuestoListNewAiuEspecificoPresupuesto.getAiuEspecificoPresupuestoList().remove(aiuEspecificoPresupuestoListNewAiuEspecificoPresupuesto);
                        oldIdItemAiuOfAiuEspecificoPresupuestoListNewAiuEspecificoPresupuesto = em.merge(oldIdItemAiuOfAiuEspecificoPresupuestoListNewAiuEspecificoPresupuesto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = itemAiu.getId();
                if (findItemAiu(id) == null) {
                    throw new NonexistentEntityException("The itemAiu with id " + id + " no longer exists.");
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
            ItemAiu itemAiu;
            try {
                itemAiu = em.getReference(ItemAiu.class, id);
                itemAiu.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itemAiu with id " + id + " no longer exists.", enfe);
            }
            Aiu idTipoAiu = itemAiu.getIdTipoAiu();
            if (idTipoAiu != null) {
                idTipoAiu.getItemAiuList().remove(itemAiu);
                idTipoAiu = em.merge(idTipoAiu);
            }
            List<AiuEspecificoPresupuesto> aiuEspecificoPresupuestoList = itemAiu.getAiuEspecificoPresupuestoList();
            for (AiuEspecificoPresupuesto aiuEspecificoPresupuestoListAiuEspecificoPresupuesto : aiuEspecificoPresupuestoList) {
                aiuEspecificoPresupuestoListAiuEspecificoPresupuesto.setIdItemAiu(null);
                aiuEspecificoPresupuestoListAiuEspecificoPresupuesto = em.merge(aiuEspecificoPresupuestoListAiuEspecificoPresupuesto);
            }
            em.remove(itemAiu);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ItemAiu> findItemAiuEntities() {
        return findItemAiuEntities(true, -1, -1);
    }

    public List<ItemAiu> findItemAiuEntities(int maxResults, int firstResult) {
        return findItemAiuEntities(false, maxResults, firstResult);
    }

    private List<ItemAiu> findItemAiuEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ItemAiu.class));
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

    public ItemAiu findItemAiu(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ItemAiu.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemAiuCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ItemAiu> rt = cq.from(ItemAiu.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
