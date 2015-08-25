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
import com.sae.persistence.domain.Tiquete;
import com.sae.persistence.domain.Vuelo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TiqueteJpaController implements Serializable {

    public TiqueteJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tiquete tiquete) throws PreexistingEntityException, Exception {
        if (tiquete.getVueloList() == null) {
            tiquete.setVueloList(new ArrayList<Vuelo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reserva idReserva = tiquete.getIdReserva();
            if (idReserva != null) {
                idReserva = em.getReference(idReserva.getClass(), idReserva.getId());
                tiquete.setIdReserva(idReserva);
            }
            List<Vuelo> attachedVueloList = new ArrayList<Vuelo>();
            for (Vuelo vueloListVueloToAttach : tiquete.getVueloList()) {
                vueloListVueloToAttach = em.getReference(vueloListVueloToAttach.getClass(), vueloListVueloToAttach.getId());
                attachedVueloList.add(vueloListVueloToAttach);
            }
            tiquete.setVueloList(attachedVueloList);
            em.persist(tiquete);
            if (idReserva != null) {
                idReserva.getTiqueteList().add(tiquete);
                idReserva = em.merge(idReserva);
            }
            for (Vuelo vueloListVuelo : tiquete.getVueloList()) {
                Tiquete oldIdTiqueteOfVueloListVuelo = vueloListVuelo.getIdTiquete();
                vueloListVuelo.setIdTiquete(tiquete);
                vueloListVuelo = em.merge(vueloListVuelo);
                if (oldIdTiqueteOfVueloListVuelo != null) {
                    oldIdTiqueteOfVueloListVuelo.getVueloList().remove(vueloListVuelo);
                    oldIdTiqueteOfVueloListVuelo = em.merge(oldIdTiqueteOfVueloListVuelo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTiquete(tiquete.getId()) != null) {
                throw new PreexistingEntityException("Tiquete " + tiquete + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tiquete tiquete) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tiquete persistentTiquete = em.find(Tiquete.class, tiquete.getId());
            Reserva idReservaOld = persistentTiquete.getIdReserva();
            Reserva idReservaNew = tiquete.getIdReserva();
            List<Vuelo> vueloListOld = persistentTiquete.getVueloList();
            List<Vuelo> vueloListNew = tiquete.getVueloList();
            if (idReservaNew != null) {
                idReservaNew = em.getReference(idReservaNew.getClass(), idReservaNew.getId());
                tiquete.setIdReserva(idReservaNew);
            }
            List<Vuelo> attachedVueloListNew = new ArrayList<Vuelo>();
            for (Vuelo vueloListNewVueloToAttach : vueloListNew) {
                vueloListNewVueloToAttach = em.getReference(vueloListNewVueloToAttach.getClass(), vueloListNewVueloToAttach.getId());
                attachedVueloListNew.add(vueloListNewVueloToAttach);
            }
            vueloListNew = attachedVueloListNew;
            tiquete.setVueloList(vueloListNew);
            tiquete = em.merge(tiquete);
            if (idReservaOld != null && !idReservaOld.equals(idReservaNew)) {
                idReservaOld.getTiqueteList().remove(tiquete);
                idReservaOld = em.merge(idReservaOld);
            }
            if (idReservaNew != null && !idReservaNew.equals(idReservaOld)) {
                idReservaNew.getTiqueteList().add(tiquete);
                idReservaNew = em.merge(idReservaNew);
            }
            for (Vuelo vueloListOldVuelo : vueloListOld) {
                if (!vueloListNew.contains(vueloListOldVuelo)) {
                    vueloListOldVuelo.setIdTiquete(null);
                    vueloListOldVuelo = em.merge(vueloListOldVuelo);
                }
            }
            for (Vuelo vueloListNewVuelo : vueloListNew) {
                if (!vueloListOld.contains(vueloListNewVuelo)) {
                    Tiquete oldIdTiqueteOfVueloListNewVuelo = vueloListNewVuelo.getIdTiquete();
                    vueloListNewVuelo.setIdTiquete(tiquete);
                    vueloListNewVuelo = em.merge(vueloListNewVuelo);
                    if (oldIdTiqueteOfVueloListNewVuelo != null && !oldIdTiqueteOfVueloListNewVuelo.equals(tiquete)) {
                        oldIdTiqueteOfVueloListNewVuelo.getVueloList().remove(vueloListNewVuelo);
                        oldIdTiqueteOfVueloListNewVuelo = em.merge(oldIdTiqueteOfVueloListNewVuelo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tiquete.getId();
                if (findTiquete(id) == null) {
                    throw new NonexistentEntityException("The tiquete with id " + id + " no longer exists.");
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
            Tiquete tiquete;
            try {
                tiquete = em.getReference(Tiquete.class, id);
                tiquete.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tiquete with id " + id + " no longer exists.", enfe);
            }
            Reserva idReserva = tiquete.getIdReserva();
            if (idReserva != null) {
                idReserva.getTiqueteList().remove(tiquete);
                idReserva = em.merge(idReserva);
            }
            List<Vuelo> vueloList = tiquete.getVueloList();
            for (Vuelo vueloListVuelo : vueloList) {
                vueloListVuelo.setIdTiquete(null);
                vueloListVuelo = em.merge(vueloListVuelo);
            }
            em.remove(tiquete);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tiquete> findTiqueteEntities() {
        return findTiqueteEntities(true, -1, -1);
    }

    public List<Tiquete> findTiqueteEntities(int maxResults, int firstResult) {
        return findTiqueteEntities(false, maxResults, firstResult);
    }

    private List<Tiquete> findTiqueteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tiquete.class));
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

    public Tiquete findTiquete(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tiquete.class, id);
        } finally {
            em.close();
        }
    }

    public int getTiqueteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tiquete> rt = cq.from(Tiquete.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
