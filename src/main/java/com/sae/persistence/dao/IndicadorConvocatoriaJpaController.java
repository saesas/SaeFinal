/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.IndicadorConvocatoria;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.RupEstadoFinancieroConsolidadoConvocatoria;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.RupEstadoFinancieroSocioConvocatoria;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class IndicadorConvocatoriaJpaController implements Serializable {

    public IndicadorConvocatoriaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(IndicadorConvocatoria indicadorConvocatoria) throws PreexistingEntityException, Exception {
        if (indicadorConvocatoria.getRupEstadoFinancieroConsolidadoConvocatoriaList() == null) {
            indicadorConvocatoria.setRupEstadoFinancieroConsolidadoConvocatoriaList(new ArrayList<RupEstadoFinancieroConsolidadoConvocatoria>());
        }
        if (indicadorConvocatoria.getRupEstadoFinancieroSocioConvocatoriaList() == null) {
            indicadorConvocatoria.setRupEstadoFinancieroSocioConvocatoriaList(new ArrayList<RupEstadoFinancieroSocioConvocatoria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<RupEstadoFinancieroConsolidadoConvocatoria> attachedRupEstadoFinancieroConsolidadoConvocatoriaList = new ArrayList<RupEstadoFinancieroConsolidadoConvocatoria>();
            for (RupEstadoFinancieroConsolidadoConvocatoria rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoriaToAttach : indicadorConvocatoria.getRupEstadoFinancieroConsolidadoConvocatoriaList()) {
                rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoriaToAttach = em.getReference(rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoriaToAttach.getClass(), rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoriaToAttach.getId());
                attachedRupEstadoFinancieroConsolidadoConvocatoriaList.add(rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoriaToAttach);
            }
            indicadorConvocatoria.setRupEstadoFinancieroConsolidadoConvocatoriaList(attachedRupEstadoFinancieroConsolidadoConvocatoriaList);
            List<RupEstadoFinancieroSocioConvocatoria> attachedRupEstadoFinancieroSocioConvocatoriaList = new ArrayList<RupEstadoFinancieroSocioConvocatoria>();
            for (RupEstadoFinancieroSocioConvocatoria rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoriaToAttach : indicadorConvocatoria.getRupEstadoFinancieroSocioConvocatoriaList()) {
                rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoriaToAttach = em.getReference(rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoriaToAttach.getClass(), rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoriaToAttach.getId());
                attachedRupEstadoFinancieroSocioConvocatoriaList.add(rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoriaToAttach);
            }
            indicadorConvocatoria.setRupEstadoFinancieroSocioConvocatoriaList(attachedRupEstadoFinancieroSocioConvocatoriaList);
            em.persist(indicadorConvocatoria);
            for (RupEstadoFinancieroConsolidadoConvocatoria rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria : indicadorConvocatoria.getRupEstadoFinancieroConsolidadoConvocatoriaList()) {
                IndicadorConvocatoria oldIdIndicadorOfRupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria = rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria.getIdIndicador();
                rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria.setIdIndicador(indicadorConvocatoria);
                rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria = em.merge(rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria);
                if (oldIdIndicadorOfRupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria != null) {
                    oldIdIndicadorOfRupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria.getRupEstadoFinancieroConsolidadoConvocatoriaList().remove(rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria);
                    oldIdIndicadorOfRupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria = em.merge(oldIdIndicadorOfRupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria);
                }
            }
            for (RupEstadoFinancieroSocioConvocatoria rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria : indicadorConvocatoria.getRupEstadoFinancieroSocioConvocatoriaList()) {
                IndicadorConvocatoria oldIdIndicadorOfRupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria = rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria.getIdIndicador();
                rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria.setIdIndicador(indicadorConvocatoria);
                rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria = em.merge(rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria);
                if (oldIdIndicadorOfRupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria != null) {
                    oldIdIndicadorOfRupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria.getRupEstadoFinancieroSocioConvocatoriaList().remove(rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria);
                    oldIdIndicadorOfRupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria = em.merge(oldIdIndicadorOfRupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIndicadorConvocatoria(indicadorConvocatoria.getId()) != null) {
                throw new PreexistingEntityException("IndicadorConvocatoria " + indicadorConvocatoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(IndicadorConvocatoria indicadorConvocatoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IndicadorConvocatoria persistentIndicadorConvocatoria = em.find(IndicadorConvocatoria.class, indicadorConvocatoria.getId());
            List<RupEstadoFinancieroConsolidadoConvocatoria> rupEstadoFinancieroConsolidadoConvocatoriaListOld = persistentIndicadorConvocatoria.getRupEstadoFinancieroConsolidadoConvocatoriaList();
            List<RupEstadoFinancieroConsolidadoConvocatoria> rupEstadoFinancieroConsolidadoConvocatoriaListNew = indicadorConvocatoria.getRupEstadoFinancieroConsolidadoConvocatoriaList();
            List<RupEstadoFinancieroSocioConvocatoria> rupEstadoFinancieroSocioConvocatoriaListOld = persistentIndicadorConvocatoria.getRupEstadoFinancieroSocioConvocatoriaList();
            List<RupEstadoFinancieroSocioConvocatoria> rupEstadoFinancieroSocioConvocatoriaListNew = indicadorConvocatoria.getRupEstadoFinancieroSocioConvocatoriaList();
            List<RupEstadoFinancieroConsolidadoConvocatoria> attachedRupEstadoFinancieroConsolidadoConvocatoriaListNew = new ArrayList<RupEstadoFinancieroConsolidadoConvocatoria>();
            for (RupEstadoFinancieroConsolidadoConvocatoria rupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoriaToAttach : rupEstadoFinancieroConsolidadoConvocatoriaListNew) {
                rupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoriaToAttach = em.getReference(rupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoriaToAttach.getClass(), rupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoriaToAttach.getId());
                attachedRupEstadoFinancieroConsolidadoConvocatoriaListNew.add(rupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoriaToAttach);
            }
            rupEstadoFinancieroConsolidadoConvocatoriaListNew = attachedRupEstadoFinancieroConsolidadoConvocatoriaListNew;
            indicadorConvocatoria.setRupEstadoFinancieroConsolidadoConvocatoriaList(rupEstadoFinancieroConsolidadoConvocatoriaListNew);
            List<RupEstadoFinancieroSocioConvocatoria> attachedRupEstadoFinancieroSocioConvocatoriaListNew = new ArrayList<RupEstadoFinancieroSocioConvocatoria>();
            for (RupEstadoFinancieroSocioConvocatoria rupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoriaToAttach : rupEstadoFinancieroSocioConvocatoriaListNew) {
                rupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoriaToAttach = em.getReference(rupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoriaToAttach.getClass(), rupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoriaToAttach.getId());
                attachedRupEstadoFinancieroSocioConvocatoriaListNew.add(rupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoriaToAttach);
            }
            rupEstadoFinancieroSocioConvocatoriaListNew = attachedRupEstadoFinancieroSocioConvocatoriaListNew;
            indicadorConvocatoria.setRupEstadoFinancieroSocioConvocatoriaList(rupEstadoFinancieroSocioConvocatoriaListNew);
            indicadorConvocatoria = em.merge(indicadorConvocatoria);
            for (RupEstadoFinancieroConsolidadoConvocatoria rupEstadoFinancieroConsolidadoConvocatoriaListOldRupEstadoFinancieroConsolidadoConvocatoria : rupEstadoFinancieroConsolidadoConvocatoriaListOld) {
                if (!rupEstadoFinancieroConsolidadoConvocatoriaListNew.contains(rupEstadoFinancieroConsolidadoConvocatoriaListOldRupEstadoFinancieroConsolidadoConvocatoria)) {
                    rupEstadoFinancieroConsolidadoConvocatoriaListOldRupEstadoFinancieroConsolidadoConvocatoria.setIdIndicador(null);
                    rupEstadoFinancieroConsolidadoConvocatoriaListOldRupEstadoFinancieroConsolidadoConvocatoria = em.merge(rupEstadoFinancieroConsolidadoConvocatoriaListOldRupEstadoFinancieroConsolidadoConvocatoria);
                }
            }
            for (RupEstadoFinancieroConsolidadoConvocatoria rupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoria : rupEstadoFinancieroConsolidadoConvocatoriaListNew) {
                if (!rupEstadoFinancieroConsolidadoConvocatoriaListOld.contains(rupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoria)) {
                    IndicadorConvocatoria oldIdIndicadorOfRupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoria = rupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoria.getIdIndicador();
                    rupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoria.setIdIndicador(indicadorConvocatoria);
                    rupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoria = em.merge(rupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoria);
                    if (oldIdIndicadorOfRupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoria != null && !oldIdIndicadorOfRupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoria.equals(indicadorConvocatoria)) {
                        oldIdIndicadorOfRupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoria.getRupEstadoFinancieroConsolidadoConvocatoriaList().remove(rupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoria);
                        oldIdIndicadorOfRupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoria = em.merge(oldIdIndicadorOfRupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoria);
                    }
                }
            }
            for (RupEstadoFinancieroSocioConvocatoria rupEstadoFinancieroSocioConvocatoriaListOldRupEstadoFinancieroSocioConvocatoria : rupEstadoFinancieroSocioConvocatoriaListOld) {
                if (!rupEstadoFinancieroSocioConvocatoriaListNew.contains(rupEstadoFinancieroSocioConvocatoriaListOldRupEstadoFinancieroSocioConvocatoria)) {
                    rupEstadoFinancieroSocioConvocatoriaListOldRupEstadoFinancieroSocioConvocatoria.setIdIndicador(null);
                    rupEstadoFinancieroSocioConvocatoriaListOldRupEstadoFinancieroSocioConvocatoria = em.merge(rupEstadoFinancieroSocioConvocatoriaListOldRupEstadoFinancieroSocioConvocatoria);
                }
            }
            for (RupEstadoFinancieroSocioConvocatoria rupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoria : rupEstadoFinancieroSocioConvocatoriaListNew) {
                if (!rupEstadoFinancieroSocioConvocatoriaListOld.contains(rupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoria)) {
                    IndicadorConvocatoria oldIdIndicadorOfRupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoria = rupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoria.getIdIndicador();
                    rupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoria.setIdIndicador(indicadorConvocatoria);
                    rupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoria = em.merge(rupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoria);
                    if (oldIdIndicadorOfRupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoria != null && !oldIdIndicadorOfRupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoria.equals(indicadorConvocatoria)) {
                        oldIdIndicadorOfRupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoria.getRupEstadoFinancieroSocioConvocatoriaList().remove(rupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoria);
                        oldIdIndicadorOfRupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoria = em.merge(oldIdIndicadorOfRupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = indicadorConvocatoria.getId();
                if (findIndicadorConvocatoria(id) == null) {
                    throw new NonexistentEntityException("The indicadorConvocatoria with id " + id + " no longer exists.");
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
            IndicadorConvocatoria indicadorConvocatoria;
            try {
                indicadorConvocatoria = em.getReference(IndicadorConvocatoria.class, id);
                indicadorConvocatoria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The indicadorConvocatoria with id " + id + " no longer exists.", enfe);
            }
            List<RupEstadoFinancieroConsolidadoConvocatoria> rupEstadoFinancieroConsolidadoConvocatoriaList = indicadorConvocatoria.getRupEstadoFinancieroConsolidadoConvocatoriaList();
            for (RupEstadoFinancieroConsolidadoConvocatoria rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria : rupEstadoFinancieroConsolidadoConvocatoriaList) {
                rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria.setIdIndicador(null);
                rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria = em.merge(rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria);
            }
            List<RupEstadoFinancieroSocioConvocatoria> rupEstadoFinancieroSocioConvocatoriaList = indicadorConvocatoria.getRupEstadoFinancieroSocioConvocatoriaList();
            for (RupEstadoFinancieroSocioConvocatoria rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria : rupEstadoFinancieroSocioConvocatoriaList) {
                rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria.setIdIndicador(null);
                rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria = em.merge(rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria);
            }
            em.remove(indicadorConvocatoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<IndicadorConvocatoria> findIndicadorConvocatoriaEntities() {
        return findIndicadorConvocatoriaEntities(true, -1, -1);
    }

    public List<IndicadorConvocatoria> findIndicadorConvocatoriaEntities(int maxResults, int firstResult) {
        return findIndicadorConvocatoriaEntities(false, maxResults, firstResult);
    }

    private List<IndicadorConvocatoria> findIndicadorConvocatoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(IndicadorConvocatoria.class));
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

    public IndicadorConvocatoria findIndicadorConvocatoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(IndicadorConvocatoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getIndicadorConvocatoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<IndicadorConvocatoria> rt = cq.from(IndicadorConvocatoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
