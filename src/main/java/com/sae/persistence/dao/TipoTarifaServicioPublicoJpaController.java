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
import com.sae.persistence.domain.ConsumoFacturaServicioPublico;
import com.sae.persistence.domain.TipoTarifaServicioPublico;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoTarifaServicioPublicoJpaController implements Serializable {

    public TipoTarifaServicioPublicoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoTarifaServicioPublico tipoTarifaServicioPublico) throws PreexistingEntityException, Exception {
        if (tipoTarifaServicioPublico.getConsumoFacturaServicioPublicoList() == null) {
            tipoTarifaServicioPublico.setConsumoFacturaServicioPublicoList(new ArrayList<ConsumoFacturaServicioPublico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ConsumoFacturaServicioPublico> attachedConsumoFacturaServicioPublicoList = new ArrayList<ConsumoFacturaServicioPublico>();
            for (ConsumoFacturaServicioPublico consumoFacturaServicioPublicoListConsumoFacturaServicioPublicoToAttach : tipoTarifaServicioPublico.getConsumoFacturaServicioPublicoList()) {
                consumoFacturaServicioPublicoListConsumoFacturaServicioPublicoToAttach = em.getReference(consumoFacturaServicioPublicoListConsumoFacturaServicioPublicoToAttach.getClass(), consumoFacturaServicioPublicoListConsumoFacturaServicioPublicoToAttach.getId());
                attachedConsumoFacturaServicioPublicoList.add(consumoFacturaServicioPublicoListConsumoFacturaServicioPublicoToAttach);
            }
            tipoTarifaServicioPublico.setConsumoFacturaServicioPublicoList(attachedConsumoFacturaServicioPublicoList);
            em.persist(tipoTarifaServicioPublico);
            for (ConsumoFacturaServicioPublico consumoFacturaServicioPublicoListConsumoFacturaServicioPublico : tipoTarifaServicioPublico.getConsumoFacturaServicioPublicoList()) {
                TipoTarifaServicioPublico oldIdTarifaOfConsumoFacturaServicioPublicoListConsumoFacturaServicioPublico = consumoFacturaServicioPublicoListConsumoFacturaServicioPublico.getIdTarifa();
                consumoFacturaServicioPublicoListConsumoFacturaServicioPublico.setIdTarifa(tipoTarifaServicioPublico);
                consumoFacturaServicioPublicoListConsumoFacturaServicioPublico = em.merge(consumoFacturaServicioPublicoListConsumoFacturaServicioPublico);
                if (oldIdTarifaOfConsumoFacturaServicioPublicoListConsumoFacturaServicioPublico != null) {
                    oldIdTarifaOfConsumoFacturaServicioPublicoListConsumoFacturaServicioPublico.getConsumoFacturaServicioPublicoList().remove(consumoFacturaServicioPublicoListConsumoFacturaServicioPublico);
                    oldIdTarifaOfConsumoFacturaServicioPublicoListConsumoFacturaServicioPublico = em.merge(oldIdTarifaOfConsumoFacturaServicioPublicoListConsumoFacturaServicioPublico);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoTarifaServicioPublico(tipoTarifaServicioPublico.getId()) != null) {
                throw new PreexistingEntityException("TipoTarifaServicioPublico " + tipoTarifaServicioPublico + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoTarifaServicioPublico tipoTarifaServicioPublico) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoTarifaServicioPublico persistentTipoTarifaServicioPublico = em.find(TipoTarifaServicioPublico.class, tipoTarifaServicioPublico.getId());
            List<ConsumoFacturaServicioPublico> consumoFacturaServicioPublicoListOld = persistentTipoTarifaServicioPublico.getConsumoFacturaServicioPublicoList();
            List<ConsumoFacturaServicioPublico> consumoFacturaServicioPublicoListNew = tipoTarifaServicioPublico.getConsumoFacturaServicioPublicoList();
            List<ConsumoFacturaServicioPublico> attachedConsumoFacturaServicioPublicoListNew = new ArrayList<ConsumoFacturaServicioPublico>();
            for (ConsumoFacturaServicioPublico consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublicoToAttach : consumoFacturaServicioPublicoListNew) {
                consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublicoToAttach = em.getReference(consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublicoToAttach.getClass(), consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublicoToAttach.getId());
                attachedConsumoFacturaServicioPublicoListNew.add(consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublicoToAttach);
            }
            consumoFacturaServicioPublicoListNew = attachedConsumoFacturaServicioPublicoListNew;
            tipoTarifaServicioPublico.setConsumoFacturaServicioPublicoList(consumoFacturaServicioPublicoListNew);
            tipoTarifaServicioPublico = em.merge(tipoTarifaServicioPublico);
            for (ConsumoFacturaServicioPublico consumoFacturaServicioPublicoListOldConsumoFacturaServicioPublico : consumoFacturaServicioPublicoListOld) {
                if (!consumoFacturaServicioPublicoListNew.contains(consumoFacturaServicioPublicoListOldConsumoFacturaServicioPublico)) {
                    consumoFacturaServicioPublicoListOldConsumoFacturaServicioPublico.setIdTarifa(null);
                    consumoFacturaServicioPublicoListOldConsumoFacturaServicioPublico = em.merge(consumoFacturaServicioPublicoListOldConsumoFacturaServicioPublico);
                }
            }
            for (ConsumoFacturaServicioPublico consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico : consumoFacturaServicioPublicoListNew) {
                if (!consumoFacturaServicioPublicoListOld.contains(consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico)) {
                    TipoTarifaServicioPublico oldIdTarifaOfConsumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico = consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico.getIdTarifa();
                    consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico.setIdTarifa(tipoTarifaServicioPublico);
                    consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico = em.merge(consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico);
                    if (oldIdTarifaOfConsumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico != null && !oldIdTarifaOfConsumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico.equals(tipoTarifaServicioPublico)) {
                        oldIdTarifaOfConsumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico.getConsumoFacturaServicioPublicoList().remove(consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico);
                        oldIdTarifaOfConsumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico = em.merge(oldIdTarifaOfConsumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoTarifaServicioPublico.getId();
                if (findTipoTarifaServicioPublico(id) == null) {
                    throw new NonexistentEntityException("The tipoTarifaServicioPublico with id " + id + " no longer exists.");
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
            TipoTarifaServicioPublico tipoTarifaServicioPublico;
            try {
                tipoTarifaServicioPublico = em.getReference(TipoTarifaServicioPublico.class, id);
                tipoTarifaServicioPublico.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoTarifaServicioPublico with id " + id + " no longer exists.", enfe);
            }
            List<ConsumoFacturaServicioPublico> consumoFacturaServicioPublicoList = tipoTarifaServicioPublico.getConsumoFacturaServicioPublicoList();
            for (ConsumoFacturaServicioPublico consumoFacturaServicioPublicoListConsumoFacturaServicioPublico : consumoFacturaServicioPublicoList) {
                consumoFacturaServicioPublicoListConsumoFacturaServicioPublico.setIdTarifa(null);
                consumoFacturaServicioPublicoListConsumoFacturaServicioPublico = em.merge(consumoFacturaServicioPublicoListConsumoFacturaServicioPublico);
            }
            em.remove(tipoTarifaServicioPublico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoTarifaServicioPublico> findTipoTarifaServicioPublicoEntities() {
        return findTipoTarifaServicioPublicoEntities(true, -1, -1);
    }

    public List<TipoTarifaServicioPublico> findTipoTarifaServicioPublicoEntities(int maxResults, int firstResult) {
        return findTipoTarifaServicioPublicoEntities(false, maxResults, firstResult);
    }

    private List<TipoTarifaServicioPublico> findTipoTarifaServicioPublicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoTarifaServicioPublico.class));
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

    public TipoTarifaServicioPublico findTipoTarifaServicioPublico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoTarifaServicioPublico.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoTarifaServicioPublicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoTarifaServicioPublico> rt = cq.from(TipoTarifaServicioPublico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
