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
import com.sae.persistence.domain.Perfil;
import com.sae.persistence.domain.UsuarioPerfil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class UsuarioPerfilJpaController implements Serializable {

    public UsuarioPerfilJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsuarioPerfil usuarioPerfil) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario idUsuarioRegistro = usuarioPerfil.getIdUsuarioRegistro();
            if (idUsuarioRegistro != null) {
                idUsuarioRegistro = em.getReference(idUsuarioRegistro.getClass(), idUsuarioRegistro.getId());
                usuarioPerfil.setIdUsuarioRegistro(idUsuarioRegistro);
            }
            Usuario idUsuario = usuarioPerfil.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getId());
                usuarioPerfil.setIdUsuario(idUsuario);
            }
            Perfil idPerfil = usuarioPerfil.getIdPerfil();
            if (idPerfil != null) {
                idPerfil = em.getReference(idPerfil.getClass(), idPerfil.getId());
                usuarioPerfil.setIdPerfil(idPerfil);
            }
            em.persist(usuarioPerfil);
            if (idUsuarioRegistro != null) {
                idUsuarioRegistro.getUsuarioPerfilList().add(usuarioPerfil);
                idUsuarioRegistro = em.merge(idUsuarioRegistro);
            }
            if (idUsuario != null) {
                idUsuario.getUsuarioPerfilList().add(usuarioPerfil);
                idUsuario = em.merge(idUsuario);
            }
            if (idPerfil != null) {
                idPerfil.getUsuarioPerfilList().add(usuarioPerfil);
                idPerfil = em.merge(idPerfil);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuarioPerfil(usuarioPerfil.getId()) != null) {
                throw new PreexistingEntityException("UsuarioPerfil " + usuarioPerfil + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UsuarioPerfil usuarioPerfil) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UsuarioPerfil persistentUsuarioPerfil = em.find(UsuarioPerfil.class, usuarioPerfil.getId());
            Usuario idUsuarioRegistroOld = persistentUsuarioPerfil.getIdUsuarioRegistro();
            Usuario idUsuarioRegistroNew = usuarioPerfil.getIdUsuarioRegistro();
            Usuario idUsuarioOld = persistentUsuarioPerfil.getIdUsuario();
            Usuario idUsuarioNew = usuarioPerfil.getIdUsuario();
            Perfil idPerfilOld = persistentUsuarioPerfil.getIdPerfil();
            Perfil idPerfilNew = usuarioPerfil.getIdPerfil();
            if (idUsuarioRegistroNew != null) {
                idUsuarioRegistroNew = em.getReference(idUsuarioRegistroNew.getClass(), idUsuarioRegistroNew.getId());
                usuarioPerfil.setIdUsuarioRegistro(idUsuarioRegistroNew);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getId());
                usuarioPerfil.setIdUsuario(idUsuarioNew);
            }
            if (idPerfilNew != null) {
                idPerfilNew = em.getReference(idPerfilNew.getClass(), idPerfilNew.getId());
                usuarioPerfil.setIdPerfil(idPerfilNew);
            }
            usuarioPerfil = em.merge(usuarioPerfil);
            if (idUsuarioRegistroOld != null && !idUsuarioRegistroOld.equals(idUsuarioRegistroNew)) {
                idUsuarioRegistroOld.getUsuarioPerfilList().remove(usuarioPerfil);
                idUsuarioRegistroOld = em.merge(idUsuarioRegistroOld);
            }
            if (idUsuarioRegistroNew != null && !idUsuarioRegistroNew.equals(idUsuarioRegistroOld)) {
                idUsuarioRegistroNew.getUsuarioPerfilList().add(usuarioPerfil);
                idUsuarioRegistroNew = em.merge(idUsuarioRegistroNew);
            }
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getUsuarioPerfilList().remove(usuarioPerfil);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getUsuarioPerfilList().add(usuarioPerfil);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            if (idPerfilOld != null && !idPerfilOld.equals(idPerfilNew)) {
                idPerfilOld.getUsuarioPerfilList().remove(usuarioPerfil);
                idPerfilOld = em.merge(idPerfilOld);
            }
            if (idPerfilNew != null && !idPerfilNew.equals(idPerfilOld)) {
                idPerfilNew.getUsuarioPerfilList().add(usuarioPerfil);
                idPerfilNew = em.merge(idPerfilNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuarioPerfil.getId();
                if (findUsuarioPerfil(id) == null) {
                    throw new NonexistentEntityException("The usuarioPerfil with id " + id + " no longer exists.");
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
            UsuarioPerfil usuarioPerfil;
            try {
                usuarioPerfil = em.getReference(UsuarioPerfil.class, id);
                usuarioPerfil.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarioPerfil with id " + id + " no longer exists.", enfe);
            }
            Usuario idUsuarioRegistro = usuarioPerfil.getIdUsuarioRegistro();
            if (idUsuarioRegistro != null) {
                idUsuarioRegistro.getUsuarioPerfilList().remove(usuarioPerfil);
                idUsuarioRegistro = em.merge(idUsuarioRegistro);
            }
            Usuario idUsuario = usuarioPerfil.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getUsuarioPerfilList().remove(usuarioPerfil);
                idUsuario = em.merge(idUsuario);
            }
            Perfil idPerfil = usuarioPerfil.getIdPerfil();
            if (idPerfil != null) {
                idPerfil.getUsuarioPerfilList().remove(usuarioPerfil);
                idPerfil = em.merge(idPerfil);
            }
            em.remove(usuarioPerfil);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UsuarioPerfil> findUsuarioPerfilEntities() {
        return findUsuarioPerfilEntities(true, -1, -1);
    }

    public List<UsuarioPerfil> findUsuarioPerfilEntities(int maxResults, int firstResult) {
        return findUsuarioPerfilEntities(false, maxResults, firstResult);
    }

    private List<UsuarioPerfil> findUsuarioPerfilEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsuarioPerfil.class));
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

    public UsuarioPerfil findUsuarioPerfil(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsuarioPerfil.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioPerfilCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsuarioPerfil> rt = cq.from(UsuarioPerfil.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
