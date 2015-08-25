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
import com.sae.persistence.domain.Comunicado;
import com.sae.persistence.domain.CopiaComunicado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class CopiaComunicadoJpaController implements Serializable {

    public CopiaComunicadoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CopiaComunicado copiaComunicado) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comunicado idComunicado = copiaComunicado.getIdComunicado();
            if (idComunicado != null) {
                idComunicado = em.getReference(idComunicado.getClass(), idComunicado.getId());
                copiaComunicado.setIdComunicado(idComunicado);
            }
            em.persist(copiaComunicado);
            if (idComunicado != null) {
                idComunicado.getCopiaComunicadoList().add(copiaComunicado);
                idComunicado = em.merge(idComunicado);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCopiaComunicado(copiaComunicado.getId()) != null) {
                throw new PreexistingEntityException("CopiaComunicado " + copiaComunicado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CopiaComunicado copiaComunicado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CopiaComunicado persistentCopiaComunicado = em.find(CopiaComunicado.class, copiaComunicado.getId());
            Comunicado idComunicadoOld = persistentCopiaComunicado.getIdComunicado();
            Comunicado idComunicadoNew = copiaComunicado.getIdComunicado();
            if (idComunicadoNew != null) {
                idComunicadoNew = em.getReference(idComunicadoNew.getClass(), idComunicadoNew.getId());
                copiaComunicado.setIdComunicado(idComunicadoNew);
            }
            copiaComunicado = em.merge(copiaComunicado);
            if (idComunicadoOld != null && !idComunicadoOld.equals(idComunicadoNew)) {
                idComunicadoOld.getCopiaComunicadoList().remove(copiaComunicado);
                idComunicadoOld = em.merge(idComunicadoOld);
            }
            if (idComunicadoNew != null && !idComunicadoNew.equals(idComunicadoOld)) {
                idComunicadoNew.getCopiaComunicadoList().add(copiaComunicado);
                idComunicadoNew = em.merge(idComunicadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = copiaComunicado.getId();
                if (findCopiaComunicado(id) == null) {
                    throw new NonexistentEntityException("The copiaComunicado with id " + id + " no longer exists.");
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
            CopiaComunicado copiaComunicado;
            try {
                copiaComunicado = em.getReference(CopiaComunicado.class, id);
                copiaComunicado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The copiaComunicado with id " + id + " no longer exists.", enfe);
            }
            Comunicado idComunicado = copiaComunicado.getIdComunicado();
            if (idComunicado != null) {
                idComunicado.getCopiaComunicadoList().remove(copiaComunicado);
                idComunicado = em.merge(idComunicado);
            }
            em.remove(copiaComunicado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CopiaComunicado> findCopiaComunicadoEntities() {
        return findCopiaComunicadoEntities(true, -1, -1);
    }

    public List<CopiaComunicado> findCopiaComunicadoEntities(int maxResults, int firstResult) {
        return findCopiaComunicadoEntities(false, maxResults, firstResult);
    }

    private List<CopiaComunicado> findCopiaComunicadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CopiaComunicado.class));
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

    public CopiaComunicado findCopiaComunicado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CopiaComunicado.class, id);
        } finally {
            em.close();
        }
    }

    public int getCopiaComunicadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CopiaComunicado> rt = cq.from(CopiaComunicado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
