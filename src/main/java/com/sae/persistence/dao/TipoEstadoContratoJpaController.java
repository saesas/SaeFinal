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
import com.sae.persistence.domain.EstadoContrato;
import com.sae.persistence.domain.TipoEstadoContrato;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoEstadoContratoJpaController implements Serializable {

    public TipoEstadoContratoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoEstadoContrato tipoEstadoContrato) throws PreexistingEntityException, Exception {
        if (tipoEstadoContrato.getEstadoContratoList() == null) {
            tipoEstadoContrato.setEstadoContratoList(new ArrayList<EstadoContrato>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<EstadoContrato> attachedEstadoContratoList = new ArrayList<EstadoContrato>();
            for (EstadoContrato estadoContratoListEstadoContratoToAttach : tipoEstadoContrato.getEstadoContratoList()) {
                estadoContratoListEstadoContratoToAttach = em.getReference(estadoContratoListEstadoContratoToAttach.getClass(), estadoContratoListEstadoContratoToAttach.getId());
                attachedEstadoContratoList.add(estadoContratoListEstadoContratoToAttach);
            }
            tipoEstadoContrato.setEstadoContratoList(attachedEstadoContratoList);
            em.persist(tipoEstadoContrato);
            for (EstadoContrato estadoContratoListEstadoContrato : tipoEstadoContrato.getEstadoContratoList()) {
                TipoEstadoContrato oldIdEstadoOfEstadoContratoListEstadoContrato = estadoContratoListEstadoContrato.getIdEstado();
                estadoContratoListEstadoContrato.setIdEstado(tipoEstadoContrato);
                estadoContratoListEstadoContrato = em.merge(estadoContratoListEstadoContrato);
                if (oldIdEstadoOfEstadoContratoListEstadoContrato != null) {
                    oldIdEstadoOfEstadoContratoListEstadoContrato.getEstadoContratoList().remove(estadoContratoListEstadoContrato);
                    oldIdEstadoOfEstadoContratoListEstadoContrato = em.merge(oldIdEstadoOfEstadoContratoListEstadoContrato);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoEstadoContrato(tipoEstadoContrato.getId()) != null) {
                throw new PreexistingEntityException("TipoEstadoContrato " + tipoEstadoContrato + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoEstadoContrato tipoEstadoContrato) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoEstadoContrato persistentTipoEstadoContrato = em.find(TipoEstadoContrato.class, tipoEstadoContrato.getId());
            List<EstadoContrato> estadoContratoListOld = persistentTipoEstadoContrato.getEstadoContratoList();
            List<EstadoContrato> estadoContratoListNew = tipoEstadoContrato.getEstadoContratoList();
            List<EstadoContrato> attachedEstadoContratoListNew = new ArrayList<EstadoContrato>();
            for (EstadoContrato estadoContratoListNewEstadoContratoToAttach : estadoContratoListNew) {
                estadoContratoListNewEstadoContratoToAttach = em.getReference(estadoContratoListNewEstadoContratoToAttach.getClass(), estadoContratoListNewEstadoContratoToAttach.getId());
                attachedEstadoContratoListNew.add(estadoContratoListNewEstadoContratoToAttach);
            }
            estadoContratoListNew = attachedEstadoContratoListNew;
            tipoEstadoContrato.setEstadoContratoList(estadoContratoListNew);
            tipoEstadoContrato = em.merge(tipoEstadoContrato);
            for (EstadoContrato estadoContratoListOldEstadoContrato : estadoContratoListOld) {
                if (!estadoContratoListNew.contains(estadoContratoListOldEstadoContrato)) {
                    estadoContratoListOldEstadoContrato.setIdEstado(null);
                    estadoContratoListOldEstadoContrato = em.merge(estadoContratoListOldEstadoContrato);
                }
            }
            for (EstadoContrato estadoContratoListNewEstadoContrato : estadoContratoListNew) {
                if (!estadoContratoListOld.contains(estadoContratoListNewEstadoContrato)) {
                    TipoEstadoContrato oldIdEstadoOfEstadoContratoListNewEstadoContrato = estadoContratoListNewEstadoContrato.getIdEstado();
                    estadoContratoListNewEstadoContrato.setIdEstado(tipoEstadoContrato);
                    estadoContratoListNewEstadoContrato = em.merge(estadoContratoListNewEstadoContrato);
                    if (oldIdEstadoOfEstadoContratoListNewEstadoContrato != null && !oldIdEstadoOfEstadoContratoListNewEstadoContrato.equals(tipoEstadoContrato)) {
                        oldIdEstadoOfEstadoContratoListNewEstadoContrato.getEstadoContratoList().remove(estadoContratoListNewEstadoContrato);
                        oldIdEstadoOfEstadoContratoListNewEstadoContrato = em.merge(oldIdEstadoOfEstadoContratoListNewEstadoContrato);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoEstadoContrato.getId();
                if (findTipoEstadoContrato(id) == null) {
                    throw new NonexistentEntityException("The tipoEstadoContrato with id " + id + " no longer exists.");
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
            TipoEstadoContrato tipoEstadoContrato;
            try {
                tipoEstadoContrato = em.getReference(TipoEstadoContrato.class, id);
                tipoEstadoContrato.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoEstadoContrato with id " + id + " no longer exists.", enfe);
            }
            List<EstadoContrato> estadoContratoList = tipoEstadoContrato.getEstadoContratoList();
            for (EstadoContrato estadoContratoListEstadoContrato : estadoContratoList) {
                estadoContratoListEstadoContrato.setIdEstado(null);
                estadoContratoListEstadoContrato = em.merge(estadoContratoListEstadoContrato);
            }
            em.remove(tipoEstadoContrato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoEstadoContrato> findTipoEstadoContratoEntities() {
        return findTipoEstadoContratoEntities(true, -1, -1);
    }

    public List<TipoEstadoContrato> findTipoEstadoContratoEntities(int maxResults, int firstResult) {
        return findTipoEstadoContratoEntities(false, maxResults, firstResult);
    }

    private List<TipoEstadoContrato> findTipoEstadoContratoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoEstadoContrato.class));
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

    public TipoEstadoContrato findTipoEstadoContrato(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoEstadoContrato.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoEstadoContratoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoEstadoContrato> rt = cq.from(TipoEstadoContrato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
