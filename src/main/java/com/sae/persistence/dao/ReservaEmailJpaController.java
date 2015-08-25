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
import com.sae.persistence.domain.Reserva;
import com.sae.persistence.domain.ReservaEmail;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ReservaEmailJpaController implements Serializable {

    public ReservaEmailJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ReservaEmail reservaEmail) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reserva idReserva = reservaEmail.getIdReserva();
            if (idReserva != null) {
                idReserva = em.getReference(idReserva.getClass(), idReserva.getId());
                reservaEmail.setIdReserva(idReserva);
            }
            em.persist(reservaEmail);
            if (idReserva != null) {
                idReserva.getReservaEmailList().add(reservaEmail);
                idReserva = em.merge(idReserva);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findReservaEmail(reservaEmail.getId()) != null) {
                throw new PreexistingEntityException("ReservaEmail " + reservaEmail + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ReservaEmail reservaEmail) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ReservaEmail persistentReservaEmail = em.find(ReservaEmail.class, reservaEmail.getId());
            Reserva idReservaOld = persistentReservaEmail.getIdReserva();
            Reserva idReservaNew = reservaEmail.getIdReserva();
            if (idReservaNew != null) {
                idReservaNew = em.getReference(idReservaNew.getClass(), idReservaNew.getId());
                reservaEmail.setIdReserva(idReservaNew);
            }
            reservaEmail = em.merge(reservaEmail);
            if (idReservaOld != null && !idReservaOld.equals(idReservaNew)) {
                idReservaOld.getReservaEmailList().remove(reservaEmail);
                idReservaOld = em.merge(idReservaOld);
            }
            if (idReservaNew != null && !idReservaNew.equals(idReservaOld)) {
                idReservaNew.getReservaEmailList().add(reservaEmail);
                idReservaNew = em.merge(idReservaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = reservaEmail.getId();
                if (findReservaEmail(id) == null) {
                    throw new NonexistentEntityException("The reservaEmail with id " + id + " no longer exists.");
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
            ReservaEmail reservaEmail;
            try {
                reservaEmail = em.getReference(ReservaEmail.class, id);
                reservaEmail.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reservaEmail with id " + id + " no longer exists.", enfe);
            }
            Reserva idReserva = reservaEmail.getIdReserva();
            if (idReserva != null) {
                idReserva.getReservaEmailList().remove(reservaEmail);
                idReserva = em.merge(idReserva);
            }
            em.remove(reservaEmail);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ReservaEmail> findReservaEmailEntities() {
        return findReservaEmailEntities(true, -1, -1);
    }

    public List<ReservaEmail> findReservaEmailEntities(int maxResults, int firstResult) {
        return findReservaEmailEntities(false, maxResults, firstResult);
    }

    private List<ReservaEmail> findReservaEmailEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ReservaEmail.class));
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

    public ReservaEmail findReservaEmail(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ReservaEmail.class, id);
        } finally {
            em.close();
        }
    }

    public int getReservaEmailCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ReservaEmail> rt = cq.from(ReservaEmail.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
