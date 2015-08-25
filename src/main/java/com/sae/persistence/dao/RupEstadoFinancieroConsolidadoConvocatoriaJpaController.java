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
import com.sae.persistence.domain.IndicadorConvocatoria;
import com.sae.persistence.domain.Convocatoria;
import com.sae.persistence.domain.RupEstadoFinancieroConsolidadoConvocatoria;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class RupEstadoFinancieroConsolidadoConvocatoriaJpaController implements Serializable {

    public RupEstadoFinancieroConsolidadoConvocatoriaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RupEstadoFinancieroConsolidadoConvocatoria rupEstadoFinancieroConsolidadoConvocatoria) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IndicadorConvocatoria idIndicador = rupEstadoFinancieroConsolidadoConvocatoria.getIdIndicador();
            if (idIndicador != null) {
                idIndicador = em.getReference(idIndicador.getClass(), idIndicador.getId());
                rupEstadoFinancieroConsolidadoConvocatoria.setIdIndicador(idIndicador);
            }
            Convocatoria idConvocatoria = rupEstadoFinancieroConsolidadoConvocatoria.getIdConvocatoria();
            if (idConvocatoria != null) {
                idConvocatoria = em.getReference(idConvocatoria.getClass(), idConvocatoria.getId());
                rupEstadoFinancieroConsolidadoConvocatoria.setIdConvocatoria(idConvocatoria);
            }
            em.persist(rupEstadoFinancieroConsolidadoConvocatoria);
            if (idIndicador != null) {
                idIndicador.getRupEstadoFinancieroConsolidadoConvocatoriaList().add(rupEstadoFinancieroConsolidadoConvocatoria);
                idIndicador = em.merge(idIndicador);
            }
            if (idConvocatoria != null) {
                idConvocatoria.getRupEstadoFinancieroConsolidadoConvocatoriaList().add(rupEstadoFinancieroConsolidadoConvocatoria);
                idConvocatoria = em.merge(idConvocatoria);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRupEstadoFinancieroConsolidadoConvocatoria(rupEstadoFinancieroConsolidadoConvocatoria.getId()) != null) {
                throw new PreexistingEntityException("RupEstadoFinancieroConsolidadoConvocatoria " + rupEstadoFinancieroConsolidadoConvocatoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RupEstadoFinancieroConsolidadoConvocatoria rupEstadoFinancieroConsolidadoConvocatoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RupEstadoFinancieroConsolidadoConvocatoria persistentRupEstadoFinancieroConsolidadoConvocatoria = em.find(RupEstadoFinancieroConsolidadoConvocatoria.class, rupEstadoFinancieroConsolidadoConvocatoria.getId());
            IndicadorConvocatoria idIndicadorOld = persistentRupEstadoFinancieroConsolidadoConvocatoria.getIdIndicador();
            IndicadorConvocatoria idIndicadorNew = rupEstadoFinancieroConsolidadoConvocatoria.getIdIndicador();
            Convocatoria idConvocatoriaOld = persistentRupEstadoFinancieroConsolidadoConvocatoria.getIdConvocatoria();
            Convocatoria idConvocatoriaNew = rupEstadoFinancieroConsolidadoConvocatoria.getIdConvocatoria();
            if (idIndicadorNew != null) {
                idIndicadorNew = em.getReference(idIndicadorNew.getClass(), idIndicadorNew.getId());
                rupEstadoFinancieroConsolidadoConvocatoria.setIdIndicador(idIndicadorNew);
            }
            if (idConvocatoriaNew != null) {
                idConvocatoriaNew = em.getReference(idConvocatoriaNew.getClass(), idConvocatoriaNew.getId());
                rupEstadoFinancieroConsolidadoConvocatoria.setIdConvocatoria(idConvocatoriaNew);
            }
            rupEstadoFinancieroConsolidadoConvocatoria = em.merge(rupEstadoFinancieroConsolidadoConvocatoria);
            if (idIndicadorOld != null && !idIndicadorOld.equals(idIndicadorNew)) {
                idIndicadorOld.getRupEstadoFinancieroConsolidadoConvocatoriaList().remove(rupEstadoFinancieroConsolidadoConvocatoria);
                idIndicadorOld = em.merge(idIndicadorOld);
            }
            if (idIndicadorNew != null && !idIndicadorNew.equals(idIndicadorOld)) {
                idIndicadorNew.getRupEstadoFinancieroConsolidadoConvocatoriaList().add(rupEstadoFinancieroConsolidadoConvocatoria);
                idIndicadorNew = em.merge(idIndicadorNew);
            }
            if (idConvocatoriaOld != null && !idConvocatoriaOld.equals(idConvocatoriaNew)) {
                idConvocatoriaOld.getRupEstadoFinancieroConsolidadoConvocatoriaList().remove(rupEstadoFinancieroConsolidadoConvocatoria);
                idConvocatoriaOld = em.merge(idConvocatoriaOld);
            }
            if (idConvocatoriaNew != null && !idConvocatoriaNew.equals(idConvocatoriaOld)) {
                idConvocatoriaNew.getRupEstadoFinancieroConsolidadoConvocatoriaList().add(rupEstadoFinancieroConsolidadoConvocatoria);
                idConvocatoriaNew = em.merge(idConvocatoriaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rupEstadoFinancieroConsolidadoConvocatoria.getId();
                if (findRupEstadoFinancieroConsolidadoConvocatoria(id) == null) {
                    throw new NonexistentEntityException("The rupEstadoFinancieroConsolidadoConvocatoria with id " + id + " no longer exists.");
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
            RupEstadoFinancieroConsolidadoConvocatoria rupEstadoFinancieroConsolidadoConvocatoria;
            try {
                rupEstadoFinancieroConsolidadoConvocatoria = em.getReference(RupEstadoFinancieroConsolidadoConvocatoria.class, id);
                rupEstadoFinancieroConsolidadoConvocatoria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rupEstadoFinancieroConsolidadoConvocatoria with id " + id + " no longer exists.", enfe);
            }
            IndicadorConvocatoria idIndicador = rupEstadoFinancieroConsolidadoConvocatoria.getIdIndicador();
            if (idIndicador != null) {
                idIndicador.getRupEstadoFinancieroConsolidadoConvocatoriaList().remove(rupEstadoFinancieroConsolidadoConvocatoria);
                idIndicador = em.merge(idIndicador);
            }
            Convocatoria idConvocatoria = rupEstadoFinancieroConsolidadoConvocatoria.getIdConvocatoria();
            if (idConvocatoria != null) {
                idConvocatoria.getRupEstadoFinancieroConsolidadoConvocatoriaList().remove(rupEstadoFinancieroConsolidadoConvocatoria);
                idConvocatoria = em.merge(idConvocatoria);
            }
            em.remove(rupEstadoFinancieroConsolidadoConvocatoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RupEstadoFinancieroConsolidadoConvocatoria> findRupEstadoFinancieroConsolidadoConvocatoriaEntities() {
        return findRupEstadoFinancieroConsolidadoConvocatoriaEntities(true, -1, -1);
    }

    public List<RupEstadoFinancieroConsolidadoConvocatoria> findRupEstadoFinancieroConsolidadoConvocatoriaEntities(int maxResults, int firstResult) {
        return findRupEstadoFinancieroConsolidadoConvocatoriaEntities(false, maxResults, firstResult);
    }

    private List<RupEstadoFinancieroConsolidadoConvocatoria> findRupEstadoFinancieroConsolidadoConvocatoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RupEstadoFinancieroConsolidadoConvocatoria.class));
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

    public RupEstadoFinancieroConsolidadoConvocatoria findRupEstadoFinancieroConsolidadoConvocatoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RupEstadoFinancieroConsolidadoConvocatoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getRupEstadoFinancieroConsolidadoConvocatoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RupEstadoFinancieroConsolidadoConvocatoria> rt = cq.from(RupEstadoFinancieroConsolidadoConvocatoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
