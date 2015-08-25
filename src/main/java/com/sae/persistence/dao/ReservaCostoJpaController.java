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
import com.sae.persistence.domain.ReservaCosto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ReservaCostoJpaController implements Serializable {

    public ReservaCostoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ReservaCosto reservaCosto) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reserva idReserva = reservaCosto.getIdReserva();
            if (idReserva != null) {
                idReserva = em.getReference(idReserva.getClass(), idReserva.getId());
                reservaCosto.setIdReserva(idReserva);
            }
            em.persist(reservaCosto);
            if (idReserva != null) {
                idReserva.getReservaCostoList().add(reservaCosto);
                idReserva = em.merge(idReserva);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findReservaCosto(reservaCosto.getId()) != null) {
                throw new PreexistingEntityException("ReservaCosto " + reservaCosto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ReservaCosto reservaCosto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ReservaCosto persistentReservaCosto = em.find(ReservaCosto.class, reservaCosto.getId());
            Reserva idReservaOld = persistentReservaCosto.getIdReserva();
            Reserva idReservaNew = reservaCosto.getIdReserva();
            if (idReservaNew != null) {
                idReservaNew = em.getReference(idReservaNew.getClass(), idReservaNew.getId());
                reservaCosto.setIdReserva(idReservaNew);
            }
            reservaCosto = em.merge(reservaCosto);
            if (idReservaOld != null && !idReservaOld.equals(idReservaNew)) {
                idReservaOld.getReservaCostoList().remove(reservaCosto);
                idReservaOld = em.merge(idReservaOld);
            }
            if (idReservaNew != null && !idReservaNew.equals(idReservaOld)) {
                idReservaNew.getReservaCostoList().add(reservaCosto);
                idReservaNew = em.merge(idReservaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = reservaCosto.getId();
                if (findReservaCosto(id) == null) {
                    throw new NonexistentEntityException("The reservaCosto with id " + id + " no longer exists.");
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
            ReservaCosto reservaCosto;
            try {
                reservaCosto = em.getReference(ReservaCosto.class, id);
                reservaCosto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reservaCosto with id " + id + " no longer exists.", enfe);
            }
            Reserva idReserva = reservaCosto.getIdReserva();
            if (idReserva != null) {
                idReserva.getReservaCostoList().remove(reservaCosto);
                idReserva = em.merge(idReserva);
            }
            em.remove(reservaCosto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ReservaCosto> findReservaCostoEntities() {
        return findReservaCostoEntities(true, -1, -1);
    }

    public List<ReservaCosto> findReservaCostoEntities(int maxResults, int firstResult) {
        return findReservaCostoEntities(false, maxResults, firstResult);
    }

    private List<ReservaCosto> findReservaCostoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ReservaCosto.class));
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

    public ReservaCosto findReservaCosto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ReservaCosto.class, id);
        } finally {
            em.close();
        }
    }

    public int getReservaCostoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ReservaCosto> rt = cq.from(ReservaCosto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
