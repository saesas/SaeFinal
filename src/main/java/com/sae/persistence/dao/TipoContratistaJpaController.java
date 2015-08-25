/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.TipoContratista;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.TipoContratoProyecto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoContratistaJpaController implements Serializable {

    public TipoContratistaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoContratista tipoContratista) throws PreexistingEntityException, Exception {
        if (tipoContratista.getTipoContratoProyectoList() == null) {
            tipoContratista.setTipoContratoProyectoList(new ArrayList<TipoContratoProyecto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<TipoContratoProyecto> attachedTipoContratoProyectoList = new ArrayList<TipoContratoProyecto>();
            for (TipoContratoProyecto tipoContratoProyectoListTipoContratoProyectoToAttach : tipoContratista.getTipoContratoProyectoList()) {
                tipoContratoProyectoListTipoContratoProyectoToAttach = em.getReference(tipoContratoProyectoListTipoContratoProyectoToAttach.getClass(), tipoContratoProyectoListTipoContratoProyectoToAttach.getId());
                attachedTipoContratoProyectoList.add(tipoContratoProyectoListTipoContratoProyectoToAttach);
            }
            tipoContratista.setTipoContratoProyectoList(attachedTipoContratoProyectoList);
            em.persist(tipoContratista);
            for (TipoContratoProyecto tipoContratoProyectoListTipoContratoProyecto : tipoContratista.getTipoContratoProyectoList()) {
                TipoContratista oldIdTipoContratistaOfTipoContratoProyectoListTipoContratoProyecto = tipoContratoProyectoListTipoContratoProyecto.getIdTipoContratista();
                tipoContratoProyectoListTipoContratoProyecto.setIdTipoContratista(tipoContratista);
                tipoContratoProyectoListTipoContratoProyecto = em.merge(tipoContratoProyectoListTipoContratoProyecto);
                if (oldIdTipoContratistaOfTipoContratoProyectoListTipoContratoProyecto != null) {
                    oldIdTipoContratistaOfTipoContratoProyectoListTipoContratoProyecto.getTipoContratoProyectoList().remove(tipoContratoProyectoListTipoContratoProyecto);
                    oldIdTipoContratistaOfTipoContratoProyectoListTipoContratoProyecto = em.merge(oldIdTipoContratistaOfTipoContratoProyectoListTipoContratoProyecto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoContratista(tipoContratista.getId()) != null) {
                throw new PreexistingEntityException("TipoContratista " + tipoContratista + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoContratista tipoContratista) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoContratista persistentTipoContratista = em.find(TipoContratista.class, tipoContratista.getId());
            List<TipoContratoProyecto> tipoContratoProyectoListOld = persistentTipoContratista.getTipoContratoProyectoList();
            List<TipoContratoProyecto> tipoContratoProyectoListNew = tipoContratista.getTipoContratoProyectoList();
            List<TipoContratoProyecto> attachedTipoContratoProyectoListNew = new ArrayList<TipoContratoProyecto>();
            for (TipoContratoProyecto tipoContratoProyectoListNewTipoContratoProyectoToAttach : tipoContratoProyectoListNew) {
                tipoContratoProyectoListNewTipoContratoProyectoToAttach = em.getReference(tipoContratoProyectoListNewTipoContratoProyectoToAttach.getClass(), tipoContratoProyectoListNewTipoContratoProyectoToAttach.getId());
                attachedTipoContratoProyectoListNew.add(tipoContratoProyectoListNewTipoContratoProyectoToAttach);
            }
            tipoContratoProyectoListNew = attachedTipoContratoProyectoListNew;
            tipoContratista.setTipoContratoProyectoList(tipoContratoProyectoListNew);
            tipoContratista = em.merge(tipoContratista);
            for (TipoContratoProyecto tipoContratoProyectoListOldTipoContratoProyecto : tipoContratoProyectoListOld) {
                if (!tipoContratoProyectoListNew.contains(tipoContratoProyectoListOldTipoContratoProyecto)) {
                    tipoContratoProyectoListOldTipoContratoProyecto.setIdTipoContratista(null);
                    tipoContratoProyectoListOldTipoContratoProyecto = em.merge(tipoContratoProyectoListOldTipoContratoProyecto);
                }
            }
            for (TipoContratoProyecto tipoContratoProyectoListNewTipoContratoProyecto : tipoContratoProyectoListNew) {
                if (!tipoContratoProyectoListOld.contains(tipoContratoProyectoListNewTipoContratoProyecto)) {
                    TipoContratista oldIdTipoContratistaOfTipoContratoProyectoListNewTipoContratoProyecto = tipoContratoProyectoListNewTipoContratoProyecto.getIdTipoContratista();
                    tipoContratoProyectoListNewTipoContratoProyecto.setIdTipoContratista(tipoContratista);
                    tipoContratoProyectoListNewTipoContratoProyecto = em.merge(tipoContratoProyectoListNewTipoContratoProyecto);
                    if (oldIdTipoContratistaOfTipoContratoProyectoListNewTipoContratoProyecto != null && !oldIdTipoContratistaOfTipoContratoProyectoListNewTipoContratoProyecto.equals(tipoContratista)) {
                        oldIdTipoContratistaOfTipoContratoProyectoListNewTipoContratoProyecto.getTipoContratoProyectoList().remove(tipoContratoProyectoListNewTipoContratoProyecto);
                        oldIdTipoContratistaOfTipoContratoProyectoListNewTipoContratoProyecto = em.merge(oldIdTipoContratistaOfTipoContratoProyectoListNewTipoContratoProyecto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoContratista.getId();
                if (findTipoContratista(id) == null) {
                    throw new NonexistentEntityException("The tipoContratista with id " + id + " no longer exists.");
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
            TipoContratista tipoContratista;
            try {
                tipoContratista = em.getReference(TipoContratista.class, id);
                tipoContratista.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoContratista with id " + id + " no longer exists.", enfe);
            }
            List<TipoContratoProyecto> tipoContratoProyectoList = tipoContratista.getTipoContratoProyectoList();
            for (TipoContratoProyecto tipoContratoProyectoListTipoContratoProyecto : tipoContratoProyectoList) {
                tipoContratoProyectoListTipoContratoProyecto.setIdTipoContratista(null);
                tipoContratoProyectoListTipoContratoProyecto = em.merge(tipoContratoProyectoListTipoContratoProyecto);
            }
            em.remove(tipoContratista);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoContratista> findTipoContratistaEntities() {
        return findTipoContratistaEntities(true, -1, -1);
    }

    public List<TipoContratista> findTipoContratistaEntities(int maxResults, int firstResult) {
        return findTipoContratistaEntities(false, maxResults, firstResult);
    }

    private List<TipoContratista> findTipoContratistaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoContratista.class));
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

    public TipoContratista findTipoContratista(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoContratista.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoContratistaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoContratista> rt = cq.from(TipoContratista.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
