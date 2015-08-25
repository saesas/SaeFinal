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
import com.sae.persistence.domain.TipoServicioPublico;
import com.sae.persistence.domain.Puc;
import com.sae.persistence.domain.PucServicioPublico;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class PucServicioPublicoJpaController implements Serializable {

    public PucServicioPublicoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PucServicioPublico pucServicioPublico) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoServicioPublico idTipoServicio = pucServicioPublico.getIdTipoServicio();
            if (idTipoServicio != null) {
                idTipoServicio = em.getReference(idTipoServicio.getClass(), idTipoServicio.getId());
                pucServicioPublico.setIdTipoServicio(idTipoServicio);
            }
            Puc idPuc = pucServicioPublico.getIdPuc();
            if (idPuc != null) {
                idPuc = em.getReference(idPuc.getClass(), idPuc.getId());
                pucServicioPublico.setIdPuc(idPuc);
            }
            em.persist(pucServicioPublico);
            if (idTipoServicio != null) {
                idTipoServicio.getPucServicioPublicoList().add(pucServicioPublico);
                idTipoServicio = em.merge(idTipoServicio);
            }
            if (idPuc != null) {
                idPuc.getPucServicioPublicoList().add(pucServicioPublico);
                idPuc = em.merge(idPuc);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPucServicioPublico(pucServicioPublico.getId()) != null) {
                throw new PreexistingEntityException("PucServicioPublico " + pucServicioPublico + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PucServicioPublico pucServicioPublico) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PucServicioPublico persistentPucServicioPublico = em.find(PucServicioPublico.class, pucServicioPublico.getId());
            TipoServicioPublico idTipoServicioOld = persistentPucServicioPublico.getIdTipoServicio();
            TipoServicioPublico idTipoServicioNew = pucServicioPublico.getIdTipoServicio();
            Puc idPucOld = persistentPucServicioPublico.getIdPuc();
            Puc idPucNew = pucServicioPublico.getIdPuc();
            if (idTipoServicioNew != null) {
                idTipoServicioNew = em.getReference(idTipoServicioNew.getClass(), idTipoServicioNew.getId());
                pucServicioPublico.setIdTipoServicio(idTipoServicioNew);
            }
            if (idPucNew != null) {
                idPucNew = em.getReference(idPucNew.getClass(), idPucNew.getId());
                pucServicioPublico.setIdPuc(idPucNew);
            }
            pucServicioPublico = em.merge(pucServicioPublico);
            if (idTipoServicioOld != null && !idTipoServicioOld.equals(idTipoServicioNew)) {
                idTipoServicioOld.getPucServicioPublicoList().remove(pucServicioPublico);
                idTipoServicioOld = em.merge(idTipoServicioOld);
            }
            if (idTipoServicioNew != null && !idTipoServicioNew.equals(idTipoServicioOld)) {
                idTipoServicioNew.getPucServicioPublicoList().add(pucServicioPublico);
                idTipoServicioNew = em.merge(idTipoServicioNew);
            }
            if (idPucOld != null && !idPucOld.equals(idPucNew)) {
                idPucOld.getPucServicioPublicoList().remove(pucServicioPublico);
                idPucOld = em.merge(idPucOld);
            }
            if (idPucNew != null && !idPucNew.equals(idPucOld)) {
                idPucNew.getPucServicioPublicoList().add(pucServicioPublico);
                idPucNew = em.merge(idPucNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pucServicioPublico.getId();
                if (findPucServicioPublico(id) == null) {
                    throw new NonexistentEntityException("The pucServicioPublico with id " + id + " no longer exists.");
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
            PucServicioPublico pucServicioPublico;
            try {
                pucServicioPublico = em.getReference(PucServicioPublico.class, id);
                pucServicioPublico.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pucServicioPublico with id " + id + " no longer exists.", enfe);
            }
            TipoServicioPublico idTipoServicio = pucServicioPublico.getIdTipoServicio();
            if (idTipoServicio != null) {
                idTipoServicio.getPucServicioPublicoList().remove(pucServicioPublico);
                idTipoServicio = em.merge(idTipoServicio);
            }
            Puc idPuc = pucServicioPublico.getIdPuc();
            if (idPuc != null) {
                idPuc.getPucServicioPublicoList().remove(pucServicioPublico);
                idPuc = em.merge(idPuc);
            }
            em.remove(pucServicioPublico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PucServicioPublico> findPucServicioPublicoEntities() {
        return findPucServicioPublicoEntities(true, -1, -1);
    }

    public List<PucServicioPublico> findPucServicioPublicoEntities(int maxResults, int firstResult) {
        return findPucServicioPublicoEntities(false, maxResults, firstResult);
    }

    private List<PucServicioPublico> findPucServicioPublicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PucServicioPublico.class));
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

    public PucServicioPublico findPucServicioPublico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PucServicioPublico.class, id);
        } finally {
            em.close();
        }
    }

    public int getPucServicioPublicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PucServicioPublico> rt = cq.from(PucServicioPublico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
