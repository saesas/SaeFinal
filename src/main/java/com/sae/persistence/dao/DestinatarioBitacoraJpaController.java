/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.DestinatarioBitacora;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author SAE2
 */
public class DestinatarioBitacoraJpaController implements Serializable {

    public DestinatarioBitacoraJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DestinatarioBitacora destinatarioBitacora) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(destinatarioBitacora);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDestinatarioBitacora(destinatarioBitacora.getId()) != null) {
                throw new PreexistingEntityException("DestinatarioBitacora " + destinatarioBitacora + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DestinatarioBitacora destinatarioBitacora) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            destinatarioBitacora = em.merge(destinatarioBitacora);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = destinatarioBitacora.getId();
                if (findDestinatarioBitacora(id) == null) {
                    throw new NonexistentEntityException("The destinatarioBitacora with id " + id + " no longer exists.");
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
            DestinatarioBitacora destinatarioBitacora;
            try {
                destinatarioBitacora = em.getReference(DestinatarioBitacora.class, id);
                destinatarioBitacora.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The destinatarioBitacora with id " + id + " no longer exists.", enfe);
            }
            em.remove(destinatarioBitacora);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DestinatarioBitacora> findDestinatarioBitacoraEntities() {
        return findDestinatarioBitacoraEntities(true, -1, -1);
    }

    public List<DestinatarioBitacora> findDestinatarioBitacoraEntities(int maxResults, int firstResult) {
        return findDestinatarioBitacoraEntities(false, maxResults, firstResult);
    }

    private List<DestinatarioBitacora> findDestinatarioBitacoraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DestinatarioBitacora.class));
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

    public DestinatarioBitacora findDestinatarioBitacora(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DestinatarioBitacora.class, id);
        } finally {
            em.close();
        }
    }

    public int getDestinatarioBitacoraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DestinatarioBitacora> rt = cq.from(DestinatarioBitacora.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
