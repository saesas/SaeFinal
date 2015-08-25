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
import com.sae.persistence.domain.Usuario;
import com.sae.persistence.domain.Opcion;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.OpcionPerfilVisibilidad;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class OpcionJpaController implements Serializable {

    public OpcionJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Opcion opcion) throws PreexistingEntityException, Exception {
        if (opcion.getOpcionList() == null) {
            opcion.setOpcionList(new ArrayList<Opcion>());
        }
        if (opcion.getOpcionPerfilVisibilidadList() == null) {
            opcion.setOpcionPerfilVisibilidadList(new ArrayList<OpcionPerfilVisibilidad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario idUsuario = opcion.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getId());
                opcion.setIdUsuario(idUsuario);
            }
            Opcion idOpcion = opcion.getIdOpcion();
            if (idOpcion != null) {
                idOpcion = em.getReference(idOpcion.getClass(), idOpcion.getId());
                opcion.setIdOpcion(idOpcion);
            }
            List<Opcion> attachedOpcionList = new ArrayList<Opcion>();
            for (Opcion opcionListOpcionToAttach : opcion.getOpcionList()) {
                opcionListOpcionToAttach = em.getReference(opcionListOpcionToAttach.getClass(), opcionListOpcionToAttach.getId());
                attachedOpcionList.add(opcionListOpcionToAttach);
            }
            opcion.setOpcionList(attachedOpcionList);
            List<OpcionPerfilVisibilidad> attachedOpcionPerfilVisibilidadList = new ArrayList<OpcionPerfilVisibilidad>();
            for (OpcionPerfilVisibilidad opcionPerfilVisibilidadListOpcionPerfilVisibilidadToAttach : opcion.getOpcionPerfilVisibilidadList()) {
                opcionPerfilVisibilidadListOpcionPerfilVisibilidadToAttach = em.getReference(opcionPerfilVisibilidadListOpcionPerfilVisibilidadToAttach.getClass(), opcionPerfilVisibilidadListOpcionPerfilVisibilidadToAttach.getId());
                attachedOpcionPerfilVisibilidadList.add(opcionPerfilVisibilidadListOpcionPerfilVisibilidadToAttach);
            }
            opcion.setOpcionPerfilVisibilidadList(attachedOpcionPerfilVisibilidadList);
            em.persist(opcion);
            if (idUsuario != null) {
                idUsuario.getOpcionList().add(opcion);
                idUsuario = em.merge(idUsuario);
            }
            if (idOpcion != null) {
                idOpcion.getOpcionList().add(opcion);
                idOpcion = em.merge(idOpcion);
            }
            for (Opcion opcionListOpcion : opcion.getOpcionList()) {
                Opcion oldIdOpcionOfOpcionListOpcion = opcionListOpcion.getIdOpcion();
                opcionListOpcion.setIdOpcion(opcion);
                opcionListOpcion = em.merge(opcionListOpcion);
                if (oldIdOpcionOfOpcionListOpcion != null) {
                    oldIdOpcionOfOpcionListOpcion.getOpcionList().remove(opcionListOpcion);
                    oldIdOpcionOfOpcionListOpcion = em.merge(oldIdOpcionOfOpcionListOpcion);
                }
            }
            for (OpcionPerfilVisibilidad opcionPerfilVisibilidadListOpcionPerfilVisibilidad : opcion.getOpcionPerfilVisibilidadList()) {
                Opcion oldIdOpcionOfOpcionPerfilVisibilidadListOpcionPerfilVisibilidad = opcionPerfilVisibilidadListOpcionPerfilVisibilidad.getIdOpcion();
                opcionPerfilVisibilidadListOpcionPerfilVisibilidad.setIdOpcion(opcion);
                opcionPerfilVisibilidadListOpcionPerfilVisibilidad = em.merge(opcionPerfilVisibilidadListOpcionPerfilVisibilidad);
                if (oldIdOpcionOfOpcionPerfilVisibilidadListOpcionPerfilVisibilidad != null) {
                    oldIdOpcionOfOpcionPerfilVisibilidadListOpcionPerfilVisibilidad.getOpcionPerfilVisibilidadList().remove(opcionPerfilVisibilidadListOpcionPerfilVisibilidad);
                    oldIdOpcionOfOpcionPerfilVisibilidadListOpcionPerfilVisibilidad = em.merge(oldIdOpcionOfOpcionPerfilVisibilidadListOpcionPerfilVisibilidad);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOpcion(opcion.getId()) != null) {
                throw new PreexistingEntityException("Opcion " + opcion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Opcion opcion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Opcion persistentOpcion = em.find(Opcion.class, opcion.getId());
            Usuario idUsuarioOld = persistentOpcion.getIdUsuario();
            Usuario idUsuarioNew = opcion.getIdUsuario();
            Opcion idOpcionOld = persistentOpcion.getIdOpcion();
            Opcion idOpcionNew = opcion.getIdOpcion();
            List<Opcion> opcionListOld = persistentOpcion.getOpcionList();
            List<Opcion> opcionListNew = opcion.getOpcionList();
            List<OpcionPerfilVisibilidad> opcionPerfilVisibilidadListOld = persistentOpcion.getOpcionPerfilVisibilidadList();
            List<OpcionPerfilVisibilidad> opcionPerfilVisibilidadListNew = opcion.getOpcionPerfilVisibilidadList();
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getId());
                opcion.setIdUsuario(idUsuarioNew);
            }
            if (idOpcionNew != null) {
                idOpcionNew = em.getReference(idOpcionNew.getClass(), idOpcionNew.getId());
                opcion.setIdOpcion(idOpcionNew);
            }
            List<Opcion> attachedOpcionListNew = new ArrayList<Opcion>();
            for (Opcion opcionListNewOpcionToAttach : opcionListNew) {
                opcionListNewOpcionToAttach = em.getReference(opcionListNewOpcionToAttach.getClass(), opcionListNewOpcionToAttach.getId());
                attachedOpcionListNew.add(opcionListNewOpcionToAttach);
            }
            opcionListNew = attachedOpcionListNew;
            opcion.setOpcionList(opcionListNew);
            List<OpcionPerfilVisibilidad> attachedOpcionPerfilVisibilidadListNew = new ArrayList<OpcionPerfilVisibilidad>();
            for (OpcionPerfilVisibilidad opcionPerfilVisibilidadListNewOpcionPerfilVisibilidadToAttach : opcionPerfilVisibilidadListNew) {
                opcionPerfilVisibilidadListNewOpcionPerfilVisibilidadToAttach = em.getReference(opcionPerfilVisibilidadListNewOpcionPerfilVisibilidadToAttach.getClass(), opcionPerfilVisibilidadListNewOpcionPerfilVisibilidadToAttach.getId());
                attachedOpcionPerfilVisibilidadListNew.add(opcionPerfilVisibilidadListNewOpcionPerfilVisibilidadToAttach);
            }
            opcionPerfilVisibilidadListNew = attachedOpcionPerfilVisibilidadListNew;
            opcion.setOpcionPerfilVisibilidadList(opcionPerfilVisibilidadListNew);
            opcion = em.merge(opcion);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getOpcionList().remove(opcion);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getOpcionList().add(opcion);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            if (idOpcionOld != null && !idOpcionOld.equals(idOpcionNew)) {
                idOpcionOld.getOpcionList().remove(opcion);
                idOpcionOld = em.merge(idOpcionOld);
            }
            if (idOpcionNew != null && !idOpcionNew.equals(idOpcionOld)) {
                idOpcionNew.getOpcionList().add(opcion);
                idOpcionNew = em.merge(idOpcionNew);
            }
            for (Opcion opcionListOldOpcion : opcionListOld) {
                if (!opcionListNew.contains(opcionListOldOpcion)) {
                    opcionListOldOpcion.setIdOpcion(null);
                    opcionListOldOpcion = em.merge(opcionListOldOpcion);
                }
            }
            for (Opcion opcionListNewOpcion : opcionListNew) {
                if (!opcionListOld.contains(opcionListNewOpcion)) {
                    Opcion oldIdOpcionOfOpcionListNewOpcion = opcionListNewOpcion.getIdOpcion();
                    opcionListNewOpcion.setIdOpcion(opcion);
                    opcionListNewOpcion = em.merge(opcionListNewOpcion);
                    if (oldIdOpcionOfOpcionListNewOpcion != null && !oldIdOpcionOfOpcionListNewOpcion.equals(opcion)) {
                        oldIdOpcionOfOpcionListNewOpcion.getOpcionList().remove(opcionListNewOpcion);
                        oldIdOpcionOfOpcionListNewOpcion = em.merge(oldIdOpcionOfOpcionListNewOpcion);
                    }
                }
            }
            for (OpcionPerfilVisibilidad opcionPerfilVisibilidadListOldOpcionPerfilVisibilidad : opcionPerfilVisibilidadListOld) {
                if (!opcionPerfilVisibilidadListNew.contains(opcionPerfilVisibilidadListOldOpcionPerfilVisibilidad)) {
                    opcionPerfilVisibilidadListOldOpcionPerfilVisibilidad.setIdOpcion(null);
                    opcionPerfilVisibilidadListOldOpcionPerfilVisibilidad = em.merge(opcionPerfilVisibilidadListOldOpcionPerfilVisibilidad);
                }
            }
            for (OpcionPerfilVisibilidad opcionPerfilVisibilidadListNewOpcionPerfilVisibilidad : opcionPerfilVisibilidadListNew) {
                if (!opcionPerfilVisibilidadListOld.contains(opcionPerfilVisibilidadListNewOpcionPerfilVisibilidad)) {
                    Opcion oldIdOpcionOfOpcionPerfilVisibilidadListNewOpcionPerfilVisibilidad = opcionPerfilVisibilidadListNewOpcionPerfilVisibilidad.getIdOpcion();
                    opcionPerfilVisibilidadListNewOpcionPerfilVisibilidad.setIdOpcion(opcion);
                    opcionPerfilVisibilidadListNewOpcionPerfilVisibilidad = em.merge(opcionPerfilVisibilidadListNewOpcionPerfilVisibilidad);
                    if (oldIdOpcionOfOpcionPerfilVisibilidadListNewOpcionPerfilVisibilidad != null && !oldIdOpcionOfOpcionPerfilVisibilidadListNewOpcionPerfilVisibilidad.equals(opcion)) {
                        oldIdOpcionOfOpcionPerfilVisibilidadListNewOpcionPerfilVisibilidad.getOpcionPerfilVisibilidadList().remove(opcionPerfilVisibilidadListNewOpcionPerfilVisibilidad);
                        oldIdOpcionOfOpcionPerfilVisibilidadListNewOpcionPerfilVisibilidad = em.merge(oldIdOpcionOfOpcionPerfilVisibilidadListNewOpcionPerfilVisibilidad);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = opcion.getId();
                if (findOpcion(id) == null) {
                    throw new NonexistentEntityException("The opcion with id " + id + " no longer exists.");
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
            Opcion opcion;
            try {
                opcion = em.getReference(Opcion.class, id);
                opcion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The opcion with id " + id + " no longer exists.", enfe);
            }
            Usuario idUsuario = opcion.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getOpcionList().remove(opcion);
                idUsuario = em.merge(idUsuario);
            }
            Opcion idOpcion = opcion.getIdOpcion();
            if (idOpcion != null) {
                idOpcion.getOpcionList().remove(opcion);
                idOpcion = em.merge(idOpcion);
            }
            List<Opcion> opcionList = opcion.getOpcionList();
            for (Opcion opcionListOpcion : opcionList) {
                opcionListOpcion.setIdOpcion(null);
                opcionListOpcion = em.merge(opcionListOpcion);
            }
            List<OpcionPerfilVisibilidad> opcionPerfilVisibilidadList = opcion.getOpcionPerfilVisibilidadList();
            for (OpcionPerfilVisibilidad opcionPerfilVisibilidadListOpcionPerfilVisibilidad : opcionPerfilVisibilidadList) {
                opcionPerfilVisibilidadListOpcionPerfilVisibilidad.setIdOpcion(null);
                opcionPerfilVisibilidadListOpcionPerfilVisibilidad = em.merge(opcionPerfilVisibilidadListOpcionPerfilVisibilidad);
            }
            em.remove(opcion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Opcion> findOpcionEntities() {
        return findOpcionEntities(true, -1, -1);
    }

    public List<Opcion> findOpcionEntities(int maxResults, int firstResult) {
        return findOpcionEntities(false, maxResults, firstResult);
    }

    private List<Opcion> findOpcionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Opcion.class));
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

    public Opcion findOpcion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Opcion.class, id);
        } finally {
            em.close();
        }
    }

    public int getOpcionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Opcion> rt = cq.from(Opcion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
