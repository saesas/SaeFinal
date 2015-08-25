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
import com.sae.persistence.domain.Tercero;
import com.sae.persistence.domain.RazonSocial;
import com.sae.persistence.domain.RepresentateLegalRazonSocial;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class RepresentateLegalRazonSocialJpaController implements Serializable {

    public RepresentateLegalRazonSocialJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RepresentateLegalRazonSocial representateLegalRazonSocial) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tercero idTercero = representateLegalRazonSocial.getIdTercero();
            if (idTercero != null) {
                idTercero = em.getReference(idTercero.getClass(), idTercero.getId());
                representateLegalRazonSocial.setIdTercero(idTercero);
            }
            RazonSocial idRazonSocial = representateLegalRazonSocial.getIdRazonSocial();
            if (idRazonSocial != null) {
                idRazonSocial = em.getReference(idRazonSocial.getClass(), idRazonSocial.getId());
                representateLegalRazonSocial.setIdRazonSocial(idRazonSocial);
            }
            em.persist(representateLegalRazonSocial);
            if (idTercero != null) {
                idTercero.getRepresentateLegalRazonSocialList().add(representateLegalRazonSocial);
                idTercero = em.merge(idTercero);
            }
            if (idRazonSocial != null) {
                idRazonSocial.getRepresentateLegalRazonSocialList().add(representateLegalRazonSocial);
                idRazonSocial = em.merge(idRazonSocial);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRepresentateLegalRazonSocial(representateLegalRazonSocial.getId()) != null) {
                throw new PreexistingEntityException("RepresentateLegalRazonSocial " + representateLegalRazonSocial + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RepresentateLegalRazonSocial representateLegalRazonSocial) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RepresentateLegalRazonSocial persistentRepresentateLegalRazonSocial = em.find(RepresentateLegalRazonSocial.class, representateLegalRazonSocial.getId());
            Tercero idTerceroOld = persistentRepresentateLegalRazonSocial.getIdTercero();
            Tercero idTerceroNew = representateLegalRazonSocial.getIdTercero();
            RazonSocial idRazonSocialOld = persistentRepresentateLegalRazonSocial.getIdRazonSocial();
            RazonSocial idRazonSocialNew = representateLegalRazonSocial.getIdRazonSocial();
            if (idTerceroNew != null) {
                idTerceroNew = em.getReference(idTerceroNew.getClass(), idTerceroNew.getId());
                representateLegalRazonSocial.setIdTercero(idTerceroNew);
            }
            if (idRazonSocialNew != null) {
                idRazonSocialNew = em.getReference(idRazonSocialNew.getClass(), idRazonSocialNew.getId());
                representateLegalRazonSocial.setIdRazonSocial(idRazonSocialNew);
            }
            representateLegalRazonSocial = em.merge(representateLegalRazonSocial);
            if (idTerceroOld != null && !idTerceroOld.equals(idTerceroNew)) {
                idTerceroOld.getRepresentateLegalRazonSocialList().remove(representateLegalRazonSocial);
                idTerceroOld = em.merge(idTerceroOld);
            }
            if (idTerceroNew != null && !idTerceroNew.equals(idTerceroOld)) {
                idTerceroNew.getRepresentateLegalRazonSocialList().add(representateLegalRazonSocial);
                idTerceroNew = em.merge(idTerceroNew);
            }
            if (idRazonSocialOld != null && !idRazonSocialOld.equals(idRazonSocialNew)) {
                idRazonSocialOld.getRepresentateLegalRazonSocialList().remove(representateLegalRazonSocial);
                idRazonSocialOld = em.merge(idRazonSocialOld);
            }
            if (idRazonSocialNew != null && !idRazonSocialNew.equals(idRazonSocialOld)) {
                idRazonSocialNew.getRepresentateLegalRazonSocialList().add(representateLegalRazonSocial);
                idRazonSocialNew = em.merge(idRazonSocialNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = representateLegalRazonSocial.getId();
                if (findRepresentateLegalRazonSocial(id) == null) {
                    throw new NonexistentEntityException("The representateLegalRazonSocial with id " + id + " no longer exists.");
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
            RepresentateLegalRazonSocial representateLegalRazonSocial;
            try {
                representateLegalRazonSocial = em.getReference(RepresentateLegalRazonSocial.class, id);
                representateLegalRazonSocial.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The representateLegalRazonSocial with id " + id + " no longer exists.", enfe);
            }
            Tercero idTercero = representateLegalRazonSocial.getIdTercero();
            if (idTercero != null) {
                idTercero.getRepresentateLegalRazonSocialList().remove(representateLegalRazonSocial);
                idTercero = em.merge(idTercero);
            }
            RazonSocial idRazonSocial = representateLegalRazonSocial.getIdRazonSocial();
            if (idRazonSocial != null) {
                idRazonSocial.getRepresentateLegalRazonSocialList().remove(representateLegalRazonSocial);
                idRazonSocial = em.merge(idRazonSocial);
            }
            em.remove(representateLegalRazonSocial);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RepresentateLegalRazonSocial> findRepresentateLegalRazonSocialEntities() {
        return findRepresentateLegalRazonSocialEntities(true, -1, -1);
    }

    public List<RepresentateLegalRazonSocial> findRepresentateLegalRazonSocialEntities(int maxResults, int firstResult) {
        return findRepresentateLegalRazonSocialEntities(false, maxResults, firstResult);
    }

    private List<RepresentateLegalRazonSocial> findRepresentateLegalRazonSocialEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RepresentateLegalRazonSocial.class));
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

    public RepresentateLegalRazonSocial findRepresentateLegalRazonSocial(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RepresentateLegalRazonSocial.class, id);
        } finally {
            em.close();
        }
    }

    public int getRepresentateLegalRazonSocialCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RepresentateLegalRazonSocial> rt = cq.from(RepresentateLegalRazonSocial.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
