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
import com.sae.persistence.domain.OpcionPerfilVisibilidad;
import com.sae.persistence.domain.Perfil;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.UsuarioPerfil;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class PerfilJpaController implements Serializable {

    public PerfilJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Perfil perfil) throws PreexistingEntityException, Exception {
        if (perfil.getOpcionPerfilVisibilidadList() == null) {
            perfil.setOpcionPerfilVisibilidadList(new ArrayList<OpcionPerfilVisibilidad>());
        }
        if (perfil.getUsuarioPerfilList() == null) {
            perfil.setUsuarioPerfilList(new ArrayList<UsuarioPerfil>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario idUsuario = perfil.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getId());
                perfil.setIdUsuario(idUsuario);
            }
            List<OpcionPerfilVisibilidad> attachedOpcionPerfilVisibilidadList = new ArrayList<OpcionPerfilVisibilidad>();
            for (OpcionPerfilVisibilidad opcionPerfilVisibilidadListOpcionPerfilVisibilidadToAttach : perfil.getOpcionPerfilVisibilidadList()) {
                opcionPerfilVisibilidadListOpcionPerfilVisibilidadToAttach = em.getReference(opcionPerfilVisibilidadListOpcionPerfilVisibilidadToAttach.getClass(), opcionPerfilVisibilidadListOpcionPerfilVisibilidadToAttach.getId());
                attachedOpcionPerfilVisibilidadList.add(opcionPerfilVisibilidadListOpcionPerfilVisibilidadToAttach);
            }
            perfil.setOpcionPerfilVisibilidadList(attachedOpcionPerfilVisibilidadList);
            List<UsuarioPerfil> attachedUsuarioPerfilList = new ArrayList<UsuarioPerfil>();
            for (UsuarioPerfil usuarioPerfilListUsuarioPerfilToAttach : perfil.getUsuarioPerfilList()) {
                usuarioPerfilListUsuarioPerfilToAttach = em.getReference(usuarioPerfilListUsuarioPerfilToAttach.getClass(), usuarioPerfilListUsuarioPerfilToAttach.getId());
                attachedUsuarioPerfilList.add(usuarioPerfilListUsuarioPerfilToAttach);
            }
            perfil.setUsuarioPerfilList(attachedUsuarioPerfilList);
            em.persist(perfil);
            if (idUsuario != null) {
                idUsuario.getPerfilList().add(perfil);
                idUsuario = em.merge(idUsuario);
            }
            for (OpcionPerfilVisibilidad opcionPerfilVisibilidadListOpcionPerfilVisibilidad : perfil.getOpcionPerfilVisibilidadList()) {
                Perfil oldIdPerfilOfOpcionPerfilVisibilidadListOpcionPerfilVisibilidad = opcionPerfilVisibilidadListOpcionPerfilVisibilidad.getIdPerfil();
                opcionPerfilVisibilidadListOpcionPerfilVisibilidad.setIdPerfil(perfil);
                opcionPerfilVisibilidadListOpcionPerfilVisibilidad = em.merge(opcionPerfilVisibilidadListOpcionPerfilVisibilidad);
                if (oldIdPerfilOfOpcionPerfilVisibilidadListOpcionPerfilVisibilidad != null) {
                    oldIdPerfilOfOpcionPerfilVisibilidadListOpcionPerfilVisibilidad.getOpcionPerfilVisibilidadList().remove(opcionPerfilVisibilidadListOpcionPerfilVisibilidad);
                    oldIdPerfilOfOpcionPerfilVisibilidadListOpcionPerfilVisibilidad = em.merge(oldIdPerfilOfOpcionPerfilVisibilidadListOpcionPerfilVisibilidad);
                }
            }
            for (UsuarioPerfil usuarioPerfilListUsuarioPerfil : perfil.getUsuarioPerfilList()) {
                Perfil oldIdPerfilOfUsuarioPerfilListUsuarioPerfil = usuarioPerfilListUsuarioPerfil.getIdPerfil();
                usuarioPerfilListUsuarioPerfil.setIdPerfil(perfil);
                usuarioPerfilListUsuarioPerfil = em.merge(usuarioPerfilListUsuarioPerfil);
                if (oldIdPerfilOfUsuarioPerfilListUsuarioPerfil != null) {
                    oldIdPerfilOfUsuarioPerfilListUsuarioPerfil.getUsuarioPerfilList().remove(usuarioPerfilListUsuarioPerfil);
                    oldIdPerfilOfUsuarioPerfilListUsuarioPerfil = em.merge(oldIdPerfilOfUsuarioPerfilListUsuarioPerfil);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPerfil(perfil.getId()) != null) {
                throw new PreexistingEntityException("Perfil " + perfil + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Perfil perfil) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Perfil persistentPerfil = em.find(Perfil.class, perfil.getId());
            Usuario idUsuarioOld = persistentPerfil.getIdUsuario();
            Usuario idUsuarioNew = perfil.getIdUsuario();
            List<OpcionPerfilVisibilidad> opcionPerfilVisibilidadListOld = persistentPerfil.getOpcionPerfilVisibilidadList();
            List<OpcionPerfilVisibilidad> opcionPerfilVisibilidadListNew = perfil.getOpcionPerfilVisibilidadList();
            List<UsuarioPerfil> usuarioPerfilListOld = persistentPerfil.getUsuarioPerfilList();
            List<UsuarioPerfil> usuarioPerfilListNew = perfil.getUsuarioPerfilList();
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getId());
                perfil.setIdUsuario(idUsuarioNew);
            }
            List<OpcionPerfilVisibilidad> attachedOpcionPerfilVisibilidadListNew = new ArrayList<OpcionPerfilVisibilidad>();
            for (OpcionPerfilVisibilidad opcionPerfilVisibilidadListNewOpcionPerfilVisibilidadToAttach : opcionPerfilVisibilidadListNew) {
                opcionPerfilVisibilidadListNewOpcionPerfilVisibilidadToAttach = em.getReference(opcionPerfilVisibilidadListNewOpcionPerfilVisibilidadToAttach.getClass(), opcionPerfilVisibilidadListNewOpcionPerfilVisibilidadToAttach.getId());
                attachedOpcionPerfilVisibilidadListNew.add(opcionPerfilVisibilidadListNewOpcionPerfilVisibilidadToAttach);
            }
            opcionPerfilVisibilidadListNew = attachedOpcionPerfilVisibilidadListNew;
            perfil.setOpcionPerfilVisibilidadList(opcionPerfilVisibilidadListNew);
            List<UsuarioPerfil> attachedUsuarioPerfilListNew = new ArrayList<UsuarioPerfil>();
            for (UsuarioPerfil usuarioPerfilListNewUsuarioPerfilToAttach : usuarioPerfilListNew) {
                usuarioPerfilListNewUsuarioPerfilToAttach = em.getReference(usuarioPerfilListNewUsuarioPerfilToAttach.getClass(), usuarioPerfilListNewUsuarioPerfilToAttach.getId());
                attachedUsuarioPerfilListNew.add(usuarioPerfilListNewUsuarioPerfilToAttach);
            }
            usuarioPerfilListNew = attachedUsuarioPerfilListNew;
            perfil.setUsuarioPerfilList(usuarioPerfilListNew);
            perfil = em.merge(perfil);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getPerfilList().remove(perfil);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getPerfilList().add(perfil);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            for (OpcionPerfilVisibilidad opcionPerfilVisibilidadListOldOpcionPerfilVisibilidad : opcionPerfilVisibilidadListOld) {
                if (!opcionPerfilVisibilidadListNew.contains(opcionPerfilVisibilidadListOldOpcionPerfilVisibilidad)) {
                    opcionPerfilVisibilidadListOldOpcionPerfilVisibilidad.setIdPerfil(null);
                    opcionPerfilVisibilidadListOldOpcionPerfilVisibilidad = em.merge(opcionPerfilVisibilidadListOldOpcionPerfilVisibilidad);
                }
            }
            for (OpcionPerfilVisibilidad opcionPerfilVisibilidadListNewOpcionPerfilVisibilidad : opcionPerfilVisibilidadListNew) {
                if (!opcionPerfilVisibilidadListOld.contains(opcionPerfilVisibilidadListNewOpcionPerfilVisibilidad)) {
                    Perfil oldIdPerfilOfOpcionPerfilVisibilidadListNewOpcionPerfilVisibilidad = opcionPerfilVisibilidadListNewOpcionPerfilVisibilidad.getIdPerfil();
                    opcionPerfilVisibilidadListNewOpcionPerfilVisibilidad.setIdPerfil(perfil);
                    opcionPerfilVisibilidadListNewOpcionPerfilVisibilidad = em.merge(opcionPerfilVisibilidadListNewOpcionPerfilVisibilidad);
                    if (oldIdPerfilOfOpcionPerfilVisibilidadListNewOpcionPerfilVisibilidad != null && !oldIdPerfilOfOpcionPerfilVisibilidadListNewOpcionPerfilVisibilidad.equals(perfil)) {
                        oldIdPerfilOfOpcionPerfilVisibilidadListNewOpcionPerfilVisibilidad.getOpcionPerfilVisibilidadList().remove(opcionPerfilVisibilidadListNewOpcionPerfilVisibilidad);
                        oldIdPerfilOfOpcionPerfilVisibilidadListNewOpcionPerfilVisibilidad = em.merge(oldIdPerfilOfOpcionPerfilVisibilidadListNewOpcionPerfilVisibilidad);
                    }
                }
            }
            for (UsuarioPerfil usuarioPerfilListOldUsuarioPerfil : usuarioPerfilListOld) {
                if (!usuarioPerfilListNew.contains(usuarioPerfilListOldUsuarioPerfil)) {
                    usuarioPerfilListOldUsuarioPerfil.setIdPerfil(null);
                    usuarioPerfilListOldUsuarioPerfil = em.merge(usuarioPerfilListOldUsuarioPerfil);
                }
            }
            for (UsuarioPerfil usuarioPerfilListNewUsuarioPerfil : usuarioPerfilListNew) {
                if (!usuarioPerfilListOld.contains(usuarioPerfilListNewUsuarioPerfil)) {
                    Perfil oldIdPerfilOfUsuarioPerfilListNewUsuarioPerfil = usuarioPerfilListNewUsuarioPerfil.getIdPerfil();
                    usuarioPerfilListNewUsuarioPerfil.setIdPerfil(perfil);
                    usuarioPerfilListNewUsuarioPerfil = em.merge(usuarioPerfilListNewUsuarioPerfil);
                    if (oldIdPerfilOfUsuarioPerfilListNewUsuarioPerfil != null && !oldIdPerfilOfUsuarioPerfilListNewUsuarioPerfil.equals(perfil)) {
                        oldIdPerfilOfUsuarioPerfilListNewUsuarioPerfil.getUsuarioPerfilList().remove(usuarioPerfilListNewUsuarioPerfil);
                        oldIdPerfilOfUsuarioPerfilListNewUsuarioPerfil = em.merge(oldIdPerfilOfUsuarioPerfilListNewUsuarioPerfil);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = perfil.getId();
                if (findPerfil(id) == null) {
                    throw new NonexistentEntityException("The perfil with id " + id + " no longer exists.");
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
            Perfil perfil;
            try {
                perfil = em.getReference(Perfil.class, id);
                perfil.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The perfil with id " + id + " no longer exists.", enfe);
            }
            Usuario idUsuario = perfil.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getPerfilList().remove(perfil);
                idUsuario = em.merge(idUsuario);
            }
            List<OpcionPerfilVisibilidad> opcionPerfilVisibilidadList = perfil.getOpcionPerfilVisibilidadList();
            for (OpcionPerfilVisibilidad opcionPerfilVisibilidadListOpcionPerfilVisibilidad : opcionPerfilVisibilidadList) {
                opcionPerfilVisibilidadListOpcionPerfilVisibilidad.setIdPerfil(null);
                opcionPerfilVisibilidadListOpcionPerfilVisibilidad = em.merge(opcionPerfilVisibilidadListOpcionPerfilVisibilidad);
            }
            List<UsuarioPerfil> usuarioPerfilList = perfil.getUsuarioPerfilList();
            for (UsuarioPerfil usuarioPerfilListUsuarioPerfil : usuarioPerfilList) {
                usuarioPerfilListUsuarioPerfil.setIdPerfil(null);
                usuarioPerfilListUsuarioPerfil = em.merge(usuarioPerfilListUsuarioPerfil);
            }
            em.remove(perfil);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Perfil> findPerfilEntities() {
        return findPerfilEntities(true, -1, -1);
    }

    public List<Perfil> findPerfilEntities(int maxResults, int firstResult) {
        return findPerfilEntities(false, maxResults, firstResult);
    }

    private List<Perfil> findPerfilEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Perfil.class));
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

    public Perfil findPerfil(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Perfil.class, id);
        } finally {
            em.close();
        }
    }

    public int getPerfilCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Perfil> rt = cq.from(Perfil.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
