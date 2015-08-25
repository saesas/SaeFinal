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
import com.sae.persistence.domain.SocioConvocatoria;
import com.sae.persistence.domain.IndicadorConvocatoria;
import com.sae.persistence.domain.RupEstadoFinancieroSocioConvocatoria;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class RupEstadoFinancieroSocioConvocatoriaJpaController implements Serializable {

    public RupEstadoFinancieroSocioConvocatoriaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RupEstadoFinancieroSocioConvocatoria rupEstadoFinancieroSocioConvocatoria) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SocioConvocatoria idSocioConvocatoria = rupEstadoFinancieroSocioConvocatoria.getIdSocioConvocatoria();
            if (idSocioConvocatoria != null) {
                idSocioConvocatoria = em.getReference(idSocioConvocatoria.getClass(), idSocioConvocatoria.getId());
                rupEstadoFinancieroSocioConvocatoria.setIdSocioConvocatoria(idSocioConvocatoria);
            }
            IndicadorConvocatoria idIndicador = rupEstadoFinancieroSocioConvocatoria.getIdIndicador();
            if (idIndicador != null) {
                idIndicador = em.getReference(idIndicador.getClass(), idIndicador.getId());
                rupEstadoFinancieroSocioConvocatoria.setIdIndicador(idIndicador);
            }
            em.persist(rupEstadoFinancieroSocioConvocatoria);
            if (idSocioConvocatoria != null) {
                idSocioConvocatoria.getRupEstadoFinancieroSocioConvocatoriaList().add(rupEstadoFinancieroSocioConvocatoria);
                idSocioConvocatoria = em.merge(idSocioConvocatoria);
            }
            if (idIndicador != null) {
                idIndicador.getRupEstadoFinancieroSocioConvocatoriaList().add(rupEstadoFinancieroSocioConvocatoria);
                idIndicador = em.merge(idIndicador);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRupEstadoFinancieroSocioConvocatoria(rupEstadoFinancieroSocioConvocatoria.getId()) != null) {
                throw new PreexistingEntityException("RupEstadoFinancieroSocioConvocatoria " + rupEstadoFinancieroSocioConvocatoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RupEstadoFinancieroSocioConvocatoria rupEstadoFinancieroSocioConvocatoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RupEstadoFinancieroSocioConvocatoria persistentRupEstadoFinancieroSocioConvocatoria = em.find(RupEstadoFinancieroSocioConvocatoria.class, rupEstadoFinancieroSocioConvocatoria.getId());
            SocioConvocatoria idSocioConvocatoriaOld = persistentRupEstadoFinancieroSocioConvocatoria.getIdSocioConvocatoria();
            SocioConvocatoria idSocioConvocatoriaNew = rupEstadoFinancieroSocioConvocatoria.getIdSocioConvocatoria();
            IndicadorConvocatoria idIndicadorOld = persistentRupEstadoFinancieroSocioConvocatoria.getIdIndicador();
            IndicadorConvocatoria idIndicadorNew = rupEstadoFinancieroSocioConvocatoria.getIdIndicador();
            if (idSocioConvocatoriaNew != null) {
                idSocioConvocatoriaNew = em.getReference(idSocioConvocatoriaNew.getClass(), idSocioConvocatoriaNew.getId());
                rupEstadoFinancieroSocioConvocatoria.setIdSocioConvocatoria(idSocioConvocatoriaNew);
            }
            if (idIndicadorNew != null) {
                idIndicadorNew = em.getReference(idIndicadorNew.getClass(), idIndicadorNew.getId());
                rupEstadoFinancieroSocioConvocatoria.setIdIndicador(idIndicadorNew);
            }
            rupEstadoFinancieroSocioConvocatoria = em.merge(rupEstadoFinancieroSocioConvocatoria);
            if (idSocioConvocatoriaOld != null && !idSocioConvocatoriaOld.equals(idSocioConvocatoriaNew)) {
                idSocioConvocatoriaOld.getRupEstadoFinancieroSocioConvocatoriaList().remove(rupEstadoFinancieroSocioConvocatoria);
                idSocioConvocatoriaOld = em.merge(idSocioConvocatoriaOld);
            }
            if (idSocioConvocatoriaNew != null && !idSocioConvocatoriaNew.equals(idSocioConvocatoriaOld)) {
                idSocioConvocatoriaNew.getRupEstadoFinancieroSocioConvocatoriaList().add(rupEstadoFinancieroSocioConvocatoria);
                idSocioConvocatoriaNew = em.merge(idSocioConvocatoriaNew);
            }
            if (idIndicadorOld != null && !idIndicadorOld.equals(idIndicadorNew)) {
                idIndicadorOld.getRupEstadoFinancieroSocioConvocatoriaList().remove(rupEstadoFinancieroSocioConvocatoria);
                idIndicadorOld = em.merge(idIndicadorOld);
            }
            if (idIndicadorNew != null && !idIndicadorNew.equals(idIndicadorOld)) {
                idIndicadorNew.getRupEstadoFinancieroSocioConvocatoriaList().add(rupEstadoFinancieroSocioConvocatoria);
                idIndicadorNew = em.merge(idIndicadorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rupEstadoFinancieroSocioConvocatoria.getId();
                if (findRupEstadoFinancieroSocioConvocatoria(id) == null) {
                    throw new NonexistentEntityException("The rupEstadoFinancieroSocioConvocatoria with id " + id + " no longer exists.");
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
            RupEstadoFinancieroSocioConvocatoria rupEstadoFinancieroSocioConvocatoria;
            try {
                rupEstadoFinancieroSocioConvocatoria = em.getReference(RupEstadoFinancieroSocioConvocatoria.class, id);
                rupEstadoFinancieroSocioConvocatoria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rupEstadoFinancieroSocioConvocatoria with id " + id + " no longer exists.", enfe);
            }
            SocioConvocatoria idSocioConvocatoria = rupEstadoFinancieroSocioConvocatoria.getIdSocioConvocatoria();
            if (idSocioConvocatoria != null) {
                idSocioConvocatoria.getRupEstadoFinancieroSocioConvocatoriaList().remove(rupEstadoFinancieroSocioConvocatoria);
                idSocioConvocatoria = em.merge(idSocioConvocatoria);
            }
            IndicadorConvocatoria idIndicador = rupEstadoFinancieroSocioConvocatoria.getIdIndicador();
            if (idIndicador != null) {
                idIndicador.getRupEstadoFinancieroSocioConvocatoriaList().remove(rupEstadoFinancieroSocioConvocatoria);
                idIndicador = em.merge(idIndicador);
            }
            em.remove(rupEstadoFinancieroSocioConvocatoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RupEstadoFinancieroSocioConvocatoria> findRupEstadoFinancieroSocioConvocatoriaEntities() {
        return findRupEstadoFinancieroSocioConvocatoriaEntities(true, -1, -1);
    }

    public List<RupEstadoFinancieroSocioConvocatoria> findRupEstadoFinancieroSocioConvocatoriaEntities(int maxResults, int firstResult) {
        return findRupEstadoFinancieroSocioConvocatoriaEntities(false, maxResults, firstResult);
    }

    private List<RupEstadoFinancieroSocioConvocatoria> findRupEstadoFinancieroSocioConvocatoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RupEstadoFinancieroSocioConvocatoria.class));
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

    public RupEstadoFinancieroSocioConvocatoria findRupEstadoFinancieroSocioConvocatoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RupEstadoFinancieroSocioConvocatoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getRupEstadoFinancieroSocioConvocatoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RupEstadoFinancieroSocioConvocatoria> rt = cq.from(RupEstadoFinancieroSocioConvocatoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
