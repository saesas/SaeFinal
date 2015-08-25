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
import com.sae.persistence.domain.TipoGasto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoGastoJpaController implements Serializable {

    public TipoGastoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoGasto tipoGasto) throws PreexistingEntityException, Exception {
        if (tipoGasto.getReservaList() == null) {
            tipoGasto.setReservaList(new ArrayList<Reserva>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Reserva> attachedReservaList = new ArrayList<Reserva>();
            for (Reserva reservaListReservaToAttach : tipoGasto.getReservaList()) {
                reservaListReservaToAttach = em.getReference(reservaListReservaToAttach.getClass(), reservaListReservaToAttach.getId());
                attachedReservaList.add(reservaListReservaToAttach);
            }
            tipoGasto.setReservaList(attachedReservaList);
            em.persist(tipoGasto);
            for (Reserva reservaListReserva : tipoGasto.getReservaList()) {
                TipoGasto oldIdGastoOfReservaListReserva = reservaListReserva.getIdGasto();
                reservaListReserva.setIdGasto(tipoGasto);
                reservaListReserva = em.merge(reservaListReserva);
                if (oldIdGastoOfReservaListReserva != null) {
                    oldIdGastoOfReservaListReserva.getReservaList().remove(reservaListReserva);
                    oldIdGastoOfReservaListReserva = em.merge(oldIdGastoOfReservaListReserva);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoGasto(tipoGasto.getId()) != null) {
                throw new PreexistingEntityException("TipoGasto " + tipoGasto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoGasto tipoGasto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoGasto persistentTipoGasto = em.find(TipoGasto.class, tipoGasto.getId());
            List<Reserva> reservaListOld = persistentTipoGasto.getReservaList();
            List<Reserva> reservaListNew = tipoGasto.getReservaList();
            List<Reserva> attachedReservaListNew = new ArrayList<Reserva>();
            for (Reserva reservaListNewReservaToAttach : reservaListNew) {
                reservaListNewReservaToAttach = em.getReference(reservaListNewReservaToAttach.getClass(), reservaListNewReservaToAttach.getId());
                attachedReservaListNew.add(reservaListNewReservaToAttach);
            }
            reservaListNew = attachedReservaListNew;
            tipoGasto.setReservaList(reservaListNew);
            tipoGasto = em.merge(tipoGasto);
            for (Reserva reservaListOldReserva : reservaListOld) {
                if (!reservaListNew.contains(reservaListOldReserva)) {
                    reservaListOldReserva.setIdGasto(null);
                    reservaListOldReserva = em.merge(reservaListOldReserva);
                }
            }
            for (Reserva reservaListNewReserva : reservaListNew) {
                if (!reservaListOld.contains(reservaListNewReserva)) {
                    TipoGasto oldIdGastoOfReservaListNewReserva = reservaListNewReserva.getIdGasto();
                    reservaListNewReserva.setIdGasto(tipoGasto);
                    reservaListNewReserva = em.merge(reservaListNewReserva);
                    if (oldIdGastoOfReservaListNewReserva != null && !oldIdGastoOfReservaListNewReserva.equals(tipoGasto)) {
                        oldIdGastoOfReservaListNewReserva.getReservaList().remove(reservaListNewReserva);
                        oldIdGastoOfReservaListNewReserva = em.merge(oldIdGastoOfReservaListNewReserva);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoGasto.getId();
                if (findTipoGasto(id) == null) {
                    throw new NonexistentEntityException("The tipoGasto with id " + id + " no longer exists.");
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
            TipoGasto tipoGasto;
            try {
                tipoGasto = em.getReference(TipoGasto.class, id);
                tipoGasto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoGasto with id " + id + " no longer exists.", enfe);
            }
            List<Reserva> reservaList = tipoGasto.getReservaList();
            for (Reserva reservaListReserva : reservaList) {
                reservaListReserva.setIdGasto(null);
                reservaListReserva = em.merge(reservaListReserva);
            }
            em.remove(tipoGasto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoGasto> findTipoGastoEntities() {
        return findTipoGastoEntities(true, -1, -1);
    }

    public List<TipoGasto> findTipoGastoEntities(int maxResults, int firstResult) {
        return findTipoGastoEntities(false, maxResults, firstResult);
    }

    private List<TipoGasto> findTipoGastoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoGasto.class));
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

    public TipoGasto findTipoGasto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoGasto.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoGastoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoGasto> rt = cq.from(TipoGasto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
