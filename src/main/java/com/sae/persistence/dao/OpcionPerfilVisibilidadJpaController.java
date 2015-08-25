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
import com.sae.persistence.domain.Visibilidad;
import com.sae.persistence.domain.Usuario;
import com.sae.persistence.domain.Perfil;
import com.sae.persistence.domain.Opcion;
import com.sae.persistence.domain.OpcionPerfilVisibilidad;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class OpcionPerfilVisibilidadJpaController implements Serializable {

    public OpcionPerfilVisibilidadJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OpcionPerfilVisibilidad opcionPerfilVisibilidad) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Visibilidad idVisibilidad = opcionPerfilVisibilidad.getIdVisibilidad();
            if (idVisibilidad != null) {
                idVisibilidad = em.getReference(idVisibilidad.getClass(), idVisibilidad.getId());
                opcionPerfilVisibilidad.setIdVisibilidad(idVisibilidad);
            }
            Usuario idUsuario = opcionPerfilVisibilidad.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getId());
                opcionPerfilVisibilidad.setIdUsuario(idUsuario);
            }
            Perfil idPerfil = opcionPerfilVisibilidad.getIdPerfil();
            if (idPerfil != null) {
                idPerfil = em.getReference(idPerfil.getClass(), idPerfil.getId());
                opcionPerfilVisibilidad.setIdPerfil(idPerfil);
            }
            Opcion idOpcion = opcionPerfilVisibilidad.getIdOpcion();
            if (idOpcion != null) {
                idOpcion = em.getReference(idOpcion.getClass(), idOpcion.getId());
                opcionPerfilVisibilidad.setIdOpcion(idOpcion);
            }
            em.persist(opcionPerfilVisibilidad);
            if (idVisibilidad != null) {
                idVisibilidad.getOpcionPerfilVisibilidadList().add(opcionPerfilVisibilidad);
                idVisibilidad = em.merge(idVisibilidad);
            }
            if (idUsuario != null) {
                idUsuario.getOpcionPerfilVisibilidadList().add(opcionPerfilVisibilidad);
                idUsuario = em.merge(idUsuario);
            }
            if (idPerfil != null) {
                idPerfil.getOpcionPerfilVisibilidadList().add(opcionPerfilVisibilidad);
                idPerfil = em.merge(idPerfil);
            }
            if (idOpcion != null) {
                idOpcion.getOpcionPerfilVisibilidadList().add(opcionPerfilVisibilidad);
                idOpcion = em.merge(idOpcion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOpcionPerfilVisibilidad(opcionPerfilVisibilidad.getId()) != null) {
                throw new PreexistingEntityException("OpcionPerfilVisibilidad " + opcionPerfilVisibilidad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(OpcionPerfilVisibilidad opcionPerfilVisibilidad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OpcionPerfilVisibilidad persistentOpcionPerfilVisibilidad = em.find(OpcionPerfilVisibilidad.class, opcionPerfilVisibilidad.getId());
            Visibilidad idVisibilidadOld = persistentOpcionPerfilVisibilidad.getIdVisibilidad();
            Visibilidad idVisibilidadNew = opcionPerfilVisibilidad.getIdVisibilidad();
            Usuario idUsuarioOld = persistentOpcionPerfilVisibilidad.getIdUsuario();
            Usuario idUsuarioNew = opcionPerfilVisibilidad.getIdUsuario();
            Perfil idPerfilOld = persistentOpcionPerfilVisibilidad.getIdPerfil();
            Perfil idPerfilNew = opcionPerfilVisibilidad.getIdPerfil();
            Opcion idOpcionOld = persistentOpcionPerfilVisibilidad.getIdOpcion();
            Opcion idOpcionNew = opcionPerfilVisibilidad.getIdOpcion();
            if (idVisibilidadNew != null) {
                idVisibilidadNew = em.getReference(idVisibilidadNew.getClass(), idVisibilidadNew.getId());
                opcionPerfilVisibilidad.setIdVisibilidad(idVisibilidadNew);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getId());
                opcionPerfilVisibilidad.setIdUsuario(idUsuarioNew);
            }
            if (idPerfilNew != null) {
                idPerfilNew = em.getReference(idPerfilNew.getClass(), idPerfilNew.getId());
                opcionPerfilVisibilidad.setIdPerfil(idPerfilNew);
            }
            if (idOpcionNew != null) {
                idOpcionNew = em.getReference(idOpcionNew.getClass(), idOpcionNew.getId());
                opcionPerfilVisibilidad.setIdOpcion(idOpcionNew);
            }
            opcionPerfilVisibilidad = em.merge(opcionPerfilVisibilidad);
            if (idVisibilidadOld != null && !idVisibilidadOld.equals(idVisibilidadNew)) {
                idVisibilidadOld.getOpcionPerfilVisibilidadList().remove(opcionPerfilVisibilidad);
                idVisibilidadOld = em.merge(idVisibilidadOld);
            }
            if (idVisibilidadNew != null && !idVisibilidadNew.equals(idVisibilidadOld)) {
                idVisibilidadNew.getOpcionPerfilVisibilidadList().add(opcionPerfilVisibilidad);
                idVisibilidadNew = em.merge(idVisibilidadNew);
            }
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getOpcionPerfilVisibilidadList().remove(opcionPerfilVisibilidad);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getOpcionPerfilVisibilidadList().add(opcionPerfilVisibilidad);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            if (idPerfilOld != null && !idPerfilOld.equals(idPerfilNew)) {
                idPerfilOld.getOpcionPerfilVisibilidadList().remove(opcionPerfilVisibilidad);
                idPerfilOld = em.merge(idPerfilOld);
            }
            if (idPerfilNew != null && !idPerfilNew.equals(idPerfilOld)) {
                idPerfilNew.getOpcionPerfilVisibilidadList().add(opcionPerfilVisibilidad);
                idPerfilNew = em.merge(idPerfilNew);
            }
            if (idOpcionOld != null && !idOpcionOld.equals(idOpcionNew)) {
                idOpcionOld.getOpcionPerfilVisibilidadList().remove(opcionPerfilVisibilidad);
                idOpcionOld = em.merge(idOpcionOld);
            }
            if (idOpcionNew != null && !idOpcionNew.equals(idOpcionOld)) {
                idOpcionNew.getOpcionPerfilVisibilidadList().add(opcionPerfilVisibilidad);
                idOpcionNew = em.merge(idOpcionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = opcionPerfilVisibilidad.getId();
                if (findOpcionPerfilVisibilidad(id) == null) {
                    throw new NonexistentEntityException("The opcionPerfilVisibilidad with id " + id + " no longer exists.");
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
            OpcionPerfilVisibilidad opcionPerfilVisibilidad;
            try {
                opcionPerfilVisibilidad = em.getReference(OpcionPerfilVisibilidad.class, id);
                opcionPerfilVisibilidad.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The opcionPerfilVisibilidad with id " + id + " no longer exists.", enfe);
            }
            Visibilidad idVisibilidad = opcionPerfilVisibilidad.getIdVisibilidad();
            if (idVisibilidad != null) {
                idVisibilidad.getOpcionPerfilVisibilidadList().remove(opcionPerfilVisibilidad);
                idVisibilidad = em.merge(idVisibilidad);
            }
            Usuario idUsuario = opcionPerfilVisibilidad.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getOpcionPerfilVisibilidadList().remove(opcionPerfilVisibilidad);
                idUsuario = em.merge(idUsuario);
            }
            Perfil idPerfil = opcionPerfilVisibilidad.getIdPerfil();
            if (idPerfil != null) {
                idPerfil.getOpcionPerfilVisibilidadList().remove(opcionPerfilVisibilidad);
                idPerfil = em.merge(idPerfil);
            }
            Opcion idOpcion = opcionPerfilVisibilidad.getIdOpcion();
            if (idOpcion != null) {
                idOpcion.getOpcionPerfilVisibilidadList().remove(opcionPerfilVisibilidad);
                idOpcion = em.merge(idOpcion);
            }
            em.remove(opcionPerfilVisibilidad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<OpcionPerfilVisibilidad> findOpcionPerfilVisibilidadEntities() {
        return findOpcionPerfilVisibilidadEntities(true, -1, -1);
    }

    public List<OpcionPerfilVisibilidad> findOpcionPerfilVisibilidadEntities(int maxResults, int firstResult) {
        return findOpcionPerfilVisibilidadEntities(false, maxResults, firstResult);
    }

    private List<OpcionPerfilVisibilidad> findOpcionPerfilVisibilidadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OpcionPerfilVisibilidad.class));
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

    public OpcionPerfilVisibilidad findOpcionPerfilVisibilidad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OpcionPerfilVisibilidad.class, id);
        } finally {
            em.close();
        }
    }

    public int getOpcionPerfilVisibilidadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OpcionPerfilVisibilidad> rt = cq.from(OpcionPerfilVisibilidad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
