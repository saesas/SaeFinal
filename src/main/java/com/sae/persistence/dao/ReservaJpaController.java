/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.Reserva;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.TipoGasto;
import com.sae.persistence.domain.ReservaEmail;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.Tiquete;
import com.sae.persistence.domain.ReservaCosto;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ReservaJpaController implements Serializable {

    public ReservaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reserva reserva) throws PreexistingEntityException, Exception {
        if (reserva.getReservaEmailList() == null) {
            reserva.setReservaEmailList(new ArrayList<ReservaEmail>());
        }
        if (reserva.getTiqueteList() == null) {
            reserva.setTiqueteList(new ArrayList<Tiquete>());
        }
        if (reserva.getReservaCostoList() == null) {
            reserva.setReservaCostoList(new ArrayList<ReservaCosto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoGasto idGasto = reserva.getIdGasto();
            if (idGasto != null) {
                idGasto = em.getReference(idGasto.getClass(), idGasto.getId());
                reserva.setIdGasto(idGasto);
            }
            List<ReservaEmail> attachedReservaEmailList = new ArrayList<ReservaEmail>();
            for (ReservaEmail reservaEmailListReservaEmailToAttach : reserva.getReservaEmailList()) {
                reservaEmailListReservaEmailToAttach = em.getReference(reservaEmailListReservaEmailToAttach.getClass(), reservaEmailListReservaEmailToAttach.getId());
                attachedReservaEmailList.add(reservaEmailListReservaEmailToAttach);
            }
            reserva.setReservaEmailList(attachedReservaEmailList);
            List<Tiquete> attachedTiqueteList = new ArrayList<Tiquete>();
            for (Tiquete tiqueteListTiqueteToAttach : reserva.getTiqueteList()) {
                tiqueteListTiqueteToAttach = em.getReference(tiqueteListTiqueteToAttach.getClass(), tiqueteListTiqueteToAttach.getId());
                attachedTiqueteList.add(tiqueteListTiqueteToAttach);
            }
            reserva.setTiqueteList(attachedTiqueteList);
            List<ReservaCosto> attachedReservaCostoList = new ArrayList<ReservaCosto>();
            for (ReservaCosto reservaCostoListReservaCostoToAttach : reserva.getReservaCostoList()) {
                reservaCostoListReservaCostoToAttach = em.getReference(reservaCostoListReservaCostoToAttach.getClass(), reservaCostoListReservaCostoToAttach.getId());
                attachedReservaCostoList.add(reservaCostoListReservaCostoToAttach);
            }
            reserva.setReservaCostoList(attachedReservaCostoList);
            em.persist(reserva);
            if (idGasto != null) {
                idGasto.getReservaList().add(reserva);
                idGasto = em.merge(idGasto);
            }
            for (ReservaEmail reservaEmailListReservaEmail : reserva.getReservaEmailList()) {
                Reserva oldIdReservaOfReservaEmailListReservaEmail = reservaEmailListReservaEmail.getIdReserva();
                reservaEmailListReservaEmail.setIdReserva(reserva);
                reservaEmailListReservaEmail = em.merge(reservaEmailListReservaEmail);
                if (oldIdReservaOfReservaEmailListReservaEmail != null) {
                    oldIdReservaOfReservaEmailListReservaEmail.getReservaEmailList().remove(reservaEmailListReservaEmail);
                    oldIdReservaOfReservaEmailListReservaEmail = em.merge(oldIdReservaOfReservaEmailListReservaEmail);
                }
            }
            for (Tiquete tiqueteListTiquete : reserva.getTiqueteList()) {
                Reserva oldIdReservaOfTiqueteListTiquete = tiqueteListTiquete.getIdReserva();
                tiqueteListTiquete.setIdReserva(reserva);
                tiqueteListTiquete = em.merge(tiqueteListTiquete);
                if (oldIdReservaOfTiqueteListTiquete != null) {
                    oldIdReservaOfTiqueteListTiquete.getTiqueteList().remove(tiqueteListTiquete);
                    oldIdReservaOfTiqueteListTiquete = em.merge(oldIdReservaOfTiqueteListTiquete);
                }
            }
            for (ReservaCosto reservaCostoListReservaCosto : reserva.getReservaCostoList()) {
                Reserva oldIdReservaOfReservaCostoListReservaCosto = reservaCostoListReservaCosto.getIdReserva();
                reservaCostoListReservaCosto.setIdReserva(reserva);
                reservaCostoListReservaCosto = em.merge(reservaCostoListReservaCosto);
                if (oldIdReservaOfReservaCostoListReservaCosto != null) {
                    oldIdReservaOfReservaCostoListReservaCosto.getReservaCostoList().remove(reservaCostoListReservaCosto);
                    oldIdReservaOfReservaCostoListReservaCosto = em.merge(oldIdReservaOfReservaCostoListReservaCosto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findReserva(reserva.getId()) != null) {
                throw new PreexistingEntityException("Reserva " + reserva + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reserva reserva) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reserva persistentReserva = em.find(Reserva.class, reserva.getId());
            TipoGasto idGastoOld = persistentReserva.getIdGasto();
            TipoGasto idGastoNew = reserva.getIdGasto();
            List<ReservaEmail> reservaEmailListOld = persistentReserva.getReservaEmailList();
            List<ReservaEmail> reservaEmailListNew = reserva.getReservaEmailList();
            List<Tiquete> tiqueteListOld = persistentReserva.getTiqueteList();
            List<Tiquete> tiqueteListNew = reserva.getTiqueteList();
            List<ReservaCosto> reservaCostoListOld = persistentReserva.getReservaCostoList();
            List<ReservaCosto> reservaCostoListNew = reserva.getReservaCostoList();
            if (idGastoNew != null) {
                idGastoNew = em.getReference(idGastoNew.getClass(), idGastoNew.getId());
                reserva.setIdGasto(idGastoNew);
            }
            List<ReservaEmail> attachedReservaEmailListNew = new ArrayList<ReservaEmail>();
            for (ReservaEmail reservaEmailListNewReservaEmailToAttach : reservaEmailListNew) {
                reservaEmailListNewReservaEmailToAttach = em.getReference(reservaEmailListNewReservaEmailToAttach.getClass(), reservaEmailListNewReservaEmailToAttach.getId());
                attachedReservaEmailListNew.add(reservaEmailListNewReservaEmailToAttach);
            }
            reservaEmailListNew = attachedReservaEmailListNew;
            reserva.setReservaEmailList(reservaEmailListNew);
            List<Tiquete> attachedTiqueteListNew = new ArrayList<Tiquete>();
            for (Tiquete tiqueteListNewTiqueteToAttach : tiqueteListNew) {
                tiqueteListNewTiqueteToAttach = em.getReference(tiqueteListNewTiqueteToAttach.getClass(), tiqueteListNewTiqueteToAttach.getId());
                attachedTiqueteListNew.add(tiqueteListNewTiqueteToAttach);
            }
            tiqueteListNew = attachedTiqueteListNew;
            reserva.setTiqueteList(tiqueteListNew);
            List<ReservaCosto> attachedReservaCostoListNew = new ArrayList<ReservaCosto>();
            for (ReservaCosto reservaCostoListNewReservaCostoToAttach : reservaCostoListNew) {
                reservaCostoListNewReservaCostoToAttach = em.getReference(reservaCostoListNewReservaCostoToAttach.getClass(), reservaCostoListNewReservaCostoToAttach.getId());
                attachedReservaCostoListNew.add(reservaCostoListNewReservaCostoToAttach);
            }
            reservaCostoListNew = attachedReservaCostoListNew;
            reserva.setReservaCostoList(reservaCostoListNew);
            reserva = em.merge(reserva);
            if (idGastoOld != null && !idGastoOld.equals(idGastoNew)) {
                idGastoOld.getReservaList().remove(reserva);
                idGastoOld = em.merge(idGastoOld);
            }
            if (idGastoNew != null && !idGastoNew.equals(idGastoOld)) {
                idGastoNew.getReservaList().add(reserva);
                idGastoNew = em.merge(idGastoNew);
            }
            for (ReservaEmail reservaEmailListOldReservaEmail : reservaEmailListOld) {
                if (!reservaEmailListNew.contains(reservaEmailListOldReservaEmail)) {
                    reservaEmailListOldReservaEmail.setIdReserva(null);
                    reservaEmailListOldReservaEmail = em.merge(reservaEmailListOldReservaEmail);
                }
            }
            for (ReservaEmail reservaEmailListNewReservaEmail : reservaEmailListNew) {
                if (!reservaEmailListOld.contains(reservaEmailListNewReservaEmail)) {
                    Reserva oldIdReservaOfReservaEmailListNewReservaEmail = reservaEmailListNewReservaEmail.getIdReserva();
                    reservaEmailListNewReservaEmail.setIdReserva(reserva);
                    reservaEmailListNewReservaEmail = em.merge(reservaEmailListNewReservaEmail);
                    if (oldIdReservaOfReservaEmailListNewReservaEmail != null && !oldIdReservaOfReservaEmailListNewReservaEmail.equals(reserva)) {
                        oldIdReservaOfReservaEmailListNewReservaEmail.getReservaEmailList().remove(reservaEmailListNewReservaEmail);
                        oldIdReservaOfReservaEmailListNewReservaEmail = em.merge(oldIdReservaOfReservaEmailListNewReservaEmail);
                    }
                }
            }
            for (Tiquete tiqueteListOldTiquete : tiqueteListOld) {
                if (!tiqueteListNew.contains(tiqueteListOldTiquete)) {
                    tiqueteListOldTiquete.setIdReserva(null);
                    tiqueteListOldTiquete = em.merge(tiqueteListOldTiquete);
                }
            }
            for (Tiquete tiqueteListNewTiquete : tiqueteListNew) {
                if (!tiqueteListOld.contains(tiqueteListNewTiquete)) {
                    Reserva oldIdReservaOfTiqueteListNewTiquete = tiqueteListNewTiquete.getIdReserva();
                    tiqueteListNewTiquete.setIdReserva(reserva);
                    tiqueteListNewTiquete = em.merge(tiqueteListNewTiquete);
                    if (oldIdReservaOfTiqueteListNewTiquete != null && !oldIdReservaOfTiqueteListNewTiquete.equals(reserva)) {
                        oldIdReservaOfTiqueteListNewTiquete.getTiqueteList().remove(tiqueteListNewTiquete);
                        oldIdReservaOfTiqueteListNewTiquete = em.merge(oldIdReservaOfTiqueteListNewTiquete);
                    }
                }
            }
            for (ReservaCosto reservaCostoListOldReservaCosto : reservaCostoListOld) {
                if (!reservaCostoListNew.contains(reservaCostoListOldReservaCosto)) {
                    reservaCostoListOldReservaCosto.setIdReserva(null);
                    reservaCostoListOldReservaCosto = em.merge(reservaCostoListOldReservaCosto);
                }
            }
            for (ReservaCosto reservaCostoListNewReservaCosto : reservaCostoListNew) {
                if (!reservaCostoListOld.contains(reservaCostoListNewReservaCosto)) {
                    Reserva oldIdReservaOfReservaCostoListNewReservaCosto = reservaCostoListNewReservaCosto.getIdReserva();
                    reservaCostoListNewReservaCosto.setIdReserva(reserva);
                    reservaCostoListNewReservaCosto = em.merge(reservaCostoListNewReservaCosto);
                    if (oldIdReservaOfReservaCostoListNewReservaCosto != null && !oldIdReservaOfReservaCostoListNewReservaCosto.equals(reserva)) {
                        oldIdReservaOfReservaCostoListNewReservaCosto.getReservaCostoList().remove(reservaCostoListNewReservaCosto);
                        oldIdReservaOfReservaCostoListNewReservaCosto = em.merge(oldIdReservaOfReservaCostoListNewReservaCosto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = reserva.getId();
                if (findReserva(id) == null) {
                    throw new NonexistentEntityException("The reserva with id " + id + " no longer exists.");
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
            Reserva reserva;
            try {
                reserva = em.getReference(Reserva.class, id);
                reserva.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reserva with id " + id + " no longer exists.", enfe);
            }
            TipoGasto idGasto = reserva.getIdGasto();
            if (idGasto != null) {
                idGasto.getReservaList().remove(reserva);
                idGasto = em.merge(idGasto);
            }
            List<ReservaEmail> reservaEmailList = reserva.getReservaEmailList();
            for (ReservaEmail reservaEmailListReservaEmail : reservaEmailList) {
                reservaEmailListReservaEmail.setIdReserva(null);
                reservaEmailListReservaEmail = em.merge(reservaEmailListReservaEmail);
            }
            List<Tiquete> tiqueteList = reserva.getTiqueteList();
            for (Tiquete tiqueteListTiquete : tiqueteList) {
                tiqueteListTiquete.setIdReserva(null);
                tiqueteListTiquete = em.merge(tiqueteListTiquete);
            }
            List<ReservaCosto> reservaCostoList = reserva.getReservaCostoList();
            for (ReservaCosto reservaCostoListReservaCosto : reservaCostoList) {
                reservaCostoListReservaCosto.setIdReserva(null);
                reservaCostoListReservaCosto = em.merge(reservaCostoListReservaCosto);
            }
            em.remove(reserva);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reserva> findReservaEntities() {
        return findReservaEntities(true, -1, -1);
    }

    public List<Reserva> findReservaEntities(int maxResults, int firstResult) {
        return findReservaEntities(false, maxResults, firstResult);
    }

    private List<Reserva> findReservaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reserva.class));
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

    public Reserva findReserva(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reserva.class, id);
        } finally {
            em.close();
        }
    }

    public int getReservaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reserva> rt = cq.from(Reserva.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
