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
import com.sae.persistence.domain.IndicadorConsumoServicio;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class IndicadorConsumoServicioJpaController implements Serializable {

    public IndicadorConsumoServicioJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(IndicadorConsumoServicio indicadorConsumoServicio) throws PreexistingEntityException, Exception {
        if (indicadorConsumoServicio.getConsumoFacturaServicioPublicoList() == null) {
            indicadorConsumoServicio.setConsumoFacturaServicioPublicoList(new ArrayList<ConsumoFacturaServicioPublico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ConsumoFacturaServicioPublico> attachedConsumoFacturaServicioPublicoList = new ArrayList<ConsumoFacturaServicioPublico>();
            for (ConsumoFacturaServicioPublico consumoFacturaServicioPublicoListConsumoFacturaServicioPublicoToAttach : indicadorConsumoServicio.getConsumoFacturaServicioPublicoList()) {
                consumoFacturaServicioPublicoListConsumoFacturaServicioPublicoToAttach = em.getReference(consumoFacturaServicioPublicoListConsumoFacturaServicioPublicoToAttach.getClass(), consumoFacturaServicioPublicoListConsumoFacturaServicioPublicoToAttach.getId());
                attachedConsumoFacturaServicioPublicoList.add(consumoFacturaServicioPublicoListConsumoFacturaServicioPublicoToAttach);
            }
            indicadorConsumoServicio.setConsumoFacturaServicioPublicoList(attachedConsumoFacturaServicioPublicoList);
            em.persist(indicadorConsumoServicio);
            for (ConsumoFacturaServicioPublico consumoFacturaServicioPublicoListConsumoFacturaServicioPublico : indicadorConsumoServicio.getConsumoFacturaServicioPublicoList()) {
                IndicadorConsumoServicio oldIdIndicadorOfConsumoFacturaServicioPublicoListConsumoFacturaServicioPublico = consumoFacturaServicioPublicoListConsumoFacturaServicioPublico.getIdIndicador();
                consumoFacturaServicioPublicoListConsumoFacturaServicioPublico.setIdIndicador(indicadorConsumoServicio);
                consumoFacturaServicioPublicoListConsumoFacturaServicioPublico = em.merge(consumoFacturaServicioPublicoListConsumoFacturaServicioPublico);
                if (oldIdIndicadorOfConsumoFacturaServicioPublicoListConsumoFacturaServicioPublico != null) {
                    oldIdIndicadorOfConsumoFacturaServicioPublicoListConsumoFacturaServicioPublico.getConsumoFacturaServicioPublicoList().remove(consumoFacturaServicioPublicoListConsumoFacturaServicioPublico);
                    oldIdIndicadorOfConsumoFacturaServicioPublicoListConsumoFacturaServicioPublico = em.merge(oldIdIndicadorOfConsumoFacturaServicioPublicoListConsumoFacturaServicioPublico);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIndicadorConsumoServicio(indicadorConsumoServicio.getId()) != null) {
                throw new PreexistingEntityException("IndicadorConsumoServicio " + indicadorConsumoServicio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(IndicadorConsumoServicio indicadorConsumoServicio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IndicadorConsumoServicio persistentIndicadorConsumoServicio = em.find(IndicadorConsumoServicio.class, indicadorConsumoServicio.getId());
            List<ConsumoFacturaServicioPublico> consumoFacturaServicioPublicoListOld = persistentIndicadorConsumoServicio.getConsumoFacturaServicioPublicoList();
            List<ConsumoFacturaServicioPublico> consumoFacturaServicioPublicoListNew = indicadorConsumoServicio.getConsumoFacturaServicioPublicoList();
            List<ConsumoFacturaServicioPublico> attachedConsumoFacturaServicioPublicoListNew = new ArrayList<ConsumoFacturaServicioPublico>();
            for (ConsumoFacturaServicioPublico consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublicoToAttach : consumoFacturaServicioPublicoListNew) {
                consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublicoToAttach = em.getReference(consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublicoToAttach.getClass(), consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublicoToAttach.getId());
                attachedConsumoFacturaServicioPublicoListNew.add(consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublicoToAttach);
            }
            consumoFacturaServicioPublicoListNew = attachedConsumoFacturaServicioPublicoListNew;
            indicadorConsumoServicio.setConsumoFacturaServicioPublicoList(consumoFacturaServicioPublicoListNew);
            indicadorConsumoServicio = em.merge(indicadorConsumoServicio);
            for (ConsumoFacturaServicioPublico consumoFacturaServicioPublicoListOldConsumoFacturaServicioPublico : consumoFacturaServicioPublicoListOld) {
                if (!consumoFacturaServicioPublicoListNew.contains(consumoFacturaServicioPublicoListOldConsumoFacturaServicioPublico)) {
                    consumoFacturaServicioPublicoListOldConsumoFacturaServicioPublico.setIdIndicador(null);
                    consumoFacturaServicioPublicoListOldConsumoFacturaServicioPublico = em.merge(consumoFacturaServicioPublicoListOldConsumoFacturaServicioPublico);
                }
            }
            for (ConsumoFacturaServicioPublico consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico : consumoFacturaServicioPublicoListNew) {
                if (!consumoFacturaServicioPublicoListOld.contains(consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico)) {
                    IndicadorConsumoServicio oldIdIndicadorOfConsumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico = consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico.getIdIndicador();
                    consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico.setIdIndicador(indicadorConsumoServicio);
                    consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico = em.merge(consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico);
                    if (oldIdIndicadorOfConsumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico != null && !oldIdIndicadorOfConsumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico.equals(indicadorConsumoServicio)) {
                        oldIdIndicadorOfConsumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico.getConsumoFacturaServicioPublicoList().remove(consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico);
                        oldIdIndicadorOfConsumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico = em.merge(oldIdIndicadorOfConsumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = indicadorConsumoServicio.getId();
                if (findIndicadorConsumoServicio(id) == null) {
                    throw new NonexistentEntityException("The indicadorConsumoServicio with id " + id + " no longer exists.");
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
            IndicadorConsumoServicio indicadorConsumoServicio;
            try {
                indicadorConsumoServicio = em.getReference(IndicadorConsumoServicio.class, id);
                indicadorConsumoServicio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The indicadorConsumoServicio with id " + id + " no longer exists.", enfe);
            }
            List<ConsumoFacturaServicioPublico> consumoFacturaServicioPublicoList = indicadorConsumoServicio.getConsumoFacturaServicioPublicoList();
            for (ConsumoFacturaServicioPublico consumoFacturaServicioPublicoListConsumoFacturaServicioPublico : consumoFacturaServicioPublicoList) {
                consumoFacturaServicioPublicoListConsumoFacturaServicioPublico.setIdIndicador(null);
                consumoFacturaServicioPublicoListConsumoFacturaServicioPublico = em.merge(consumoFacturaServicioPublicoListConsumoFacturaServicioPublico);
            }
            em.remove(indicadorConsumoServicio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<IndicadorConsumoServicio> findIndicadorConsumoServicioEntities() {
        return findIndicadorConsumoServicioEntities(true, -1, -1);
    }

    public List<IndicadorConsumoServicio> findIndicadorConsumoServicioEntities(int maxResults, int firstResult) {
        return findIndicadorConsumoServicioEntities(false, maxResults, firstResult);
    }

    private List<IndicadorConsumoServicio> findIndicadorConsumoServicioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(IndicadorConsumoServicio.class));
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

    public IndicadorConsumoServicio findIndicadorConsumoServicio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(IndicadorConsumoServicio.class, id);
        } finally {
            em.close();
        }
    }

    public int getIndicadorConsumoServicioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<IndicadorConsumoServicio> rt = cq.from(IndicadorConsumoServicio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
