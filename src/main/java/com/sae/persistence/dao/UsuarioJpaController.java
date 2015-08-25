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
import com.sae.persistence.domain.Visibilidad;
import com.sae.persistence.domain.UsuarioPerfil;
import com.sae.persistence.domain.Perfil;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getOpcionList() == null) {
            usuario.setOpcionList(new ArrayList<Opcion>());
        }
        if (usuario.getOpcionPerfilVisibilidadList() == null) {
            usuario.setOpcionPerfilVisibilidadList(new ArrayList<OpcionPerfilVisibilidad>());
        }
        if (usuario.getUsuarioList() == null) {
            usuario.setUsuarioList(new ArrayList<Usuario>());
        }
        if (usuario.getVisibilidadList() == null) {
            usuario.setVisibilidadList(new ArrayList<Visibilidad>());
        }
        if (usuario.getUsuarioPerfilList() == null) {
            usuario.setUsuarioPerfilList(new ArrayList<UsuarioPerfil>());
        }
        if (usuario.getUsuarioPerfilList1() == null) {
            usuario.setUsuarioPerfilList1(new ArrayList<UsuarioPerfil>());
        }
        if (usuario.getPerfilList() == null) {
            usuario.setPerfilList(new ArrayList<Perfil>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario idUsuario = usuario.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getId());
                usuario.setIdUsuario(idUsuario);
            }
            List<Opcion> attachedOpcionList = new ArrayList<Opcion>();
            for (Opcion opcionListOpcionToAttach : usuario.getOpcionList()) {
                opcionListOpcionToAttach = em.getReference(opcionListOpcionToAttach.getClass(), opcionListOpcionToAttach.getId());
                attachedOpcionList.add(opcionListOpcionToAttach);
            }
            usuario.setOpcionList(attachedOpcionList);
            List<OpcionPerfilVisibilidad> attachedOpcionPerfilVisibilidadList = new ArrayList<OpcionPerfilVisibilidad>();
            for (OpcionPerfilVisibilidad opcionPerfilVisibilidadListOpcionPerfilVisibilidadToAttach : usuario.getOpcionPerfilVisibilidadList()) {
                opcionPerfilVisibilidadListOpcionPerfilVisibilidadToAttach = em.getReference(opcionPerfilVisibilidadListOpcionPerfilVisibilidadToAttach.getClass(), opcionPerfilVisibilidadListOpcionPerfilVisibilidadToAttach.getId());
                attachedOpcionPerfilVisibilidadList.add(opcionPerfilVisibilidadListOpcionPerfilVisibilidadToAttach);
            }
            usuario.setOpcionPerfilVisibilidadList(attachedOpcionPerfilVisibilidadList);
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : usuario.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getId());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            usuario.setUsuarioList(attachedUsuarioList);
            List<Visibilidad> attachedVisibilidadList = new ArrayList<Visibilidad>();
            for (Visibilidad visibilidadListVisibilidadToAttach : usuario.getVisibilidadList()) {
                visibilidadListVisibilidadToAttach = em.getReference(visibilidadListVisibilidadToAttach.getClass(), visibilidadListVisibilidadToAttach.getId());
                attachedVisibilidadList.add(visibilidadListVisibilidadToAttach);
            }
            usuario.setVisibilidadList(attachedVisibilidadList);
            List<UsuarioPerfil> attachedUsuarioPerfilList = new ArrayList<UsuarioPerfil>();
            for (UsuarioPerfil usuarioPerfilListUsuarioPerfilToAttach : usuario.getUsuarioPerfilList()) {
                usuarioPerfilListUsuarioPerfilToAttach = em.getReference(usuarioPerfilListUsuarioPerfilToAttach.getClass(), usuarioPerfilListUsuarioPerfilToAttach.getId());
                attachedUsuarioPerfilList.add(usuarioPerfilListUsuarioPerfilToAttach);
            }
            usuario.setUsuarioPerfilList(attachedUsuarioPerfilList);
            List<UsuarioPerfil> attachedUsuarioPerfilList1 = new ArrayList<UsuarioPerfil>();
            for (UsuarioPerfil usuarioPerfilList1UsuarioPerfilToAttach : usuario.getUsuarioPerfilList1()) {
                usuarioPerfilList1UsuarioPerfilToAttach = em.getReference(usuarioPerfilList1UsuarioPerfilToAttach.getClass(), usuarioPerfilList1UsuarioPerfilToAttach.getId());
                attachedUsuarioPerfilList1.add(usuarioPerfilList1UsuarioPerfilToAttach);
            }
            usuario.setUsuarioPerfilList1(attachedUsuarioPerfilList1);
            List<Perfil> attachedPerfilList = new ArrayList<Perfil>();
            for (Perfil perfilListPerfilToAttach : usuario.getPerfilList()) {
                perfilListPerfilToAttach = em.getReference(perfilListPerfilToAttach.getClass(), perfilListPerfilToAttach.getId());
                attachedPerfilList.add(perfilListPerfilToAttach);
            }
            usuario.setPerfilList(attachedPerfilList);
            em.persist(usuario);
            if (idUsuario != null) {
                idUsuario.getUsuarioList().add(usuario);
                idUsuario = em.merge(idUsuario);
            }
            for (Opcion opcionListOpcion : usuario.getOpcionList()) {
                Usuario oldIdUsuarioOfOpcionListOpcion = opcionListOpcion.getIdUsuario();
                opcionListOpcion.setIdUsuario(usuario);
                opcionListOpcion = em.merge(opcionListOpcion);
                if (oldIdUsuarioOfOpcionListOpcion != null) {
                    oldIdUsuarioOfOpcionListOpcion.getOpcionList().remove(opcionListOpcion);
                    oldIdUsuarioOfOpcionListOpcion = em.merge(oldIdUsuarioOfOpcionListOpcion);
                }
            }
            for (OpcionPerfilVisibilidad opcionPerfilVisibilidadListOpcionPerfilVisibilidad : usuario.getOpcionPerfilVisibilidadList()) {
                Usuario oldIdUsuarioOfOpcionPerfilVisibilidadListOpcionPerfilVisibilidad = opcionPerfilVisibilidadListOpcionPerfilVisibilidad.getIdUsuario();
                opcionPerfilVisibilidadListOpcionPerfilVisibilidad.setIdUsuario(usuario);
                opcionPerfilVisibilidadListOpcionPerfilVisibilidad = em.merge(opcionPerfilVisibilidadListOpcionPerfilVisibilidad);
                if (oldIdUsuarioOfOpcionPerfilVisibilidadListOpcionPerfilVisibilidad != null) {
                    oldIdUsuarioOfOpcionPerfilVisibilidadListOpcionPerfilVisibilidad.getOpcionPerfilVisibilidadList().remove(opcionPerfilVisibilidadListOpcionPerfilVisibilidad);
                    oldIdUsuarioOfOpcionPerfilVisibilidadListOpcionPerfilVisibilidad = em.merge(oldIdUsuarioOfOpcionPerfilVisibilidadListOpcionPerfilVisibilidad);
                }
            }
            for (Usuario usuarioListUsuario : usuario.getUsuarioList()) {
                Usuario oldIdUsuarioOfUsuarioListUsuario = usuarioListUsuario.getIdUsuario();
                usuarioListUsuario.setIdUsuario(usuario);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldIdUsuarioOfUsuarioListUsuario != null) {
                    oldIdUsuarioOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldIdUsuarioOfUsuarioListUsuario = em.merge(oldIdUsuarioOfUsuarioListUsuario);
                }
            }
            for (Visibilidad visibilidadListVisibilidad : usuario.getVisibilidadList()) {
                Usuario oldIdUsuarioOfVisibilidadListVisibilidad = visibilidadListVisibilidad.getIdUsuario();
                visibilidadListVisibilidad.setIdUsuario(usuario);
                visibilidadListVisibilidad = em.merge(visibilidadListVisibilidad);
                if (oldIdUsuarioOfVisibilidadListVisibilidad != null) {
                    oldIdUsuarioOfVisibilidadListVisibilidad.getVisibilidadList().remove(visibilidadListVisibilidad);
                    oldIdUsuarioOfVisibilidadListVisibilidad = em.merge(oldIdUsuarioOfVisibilidadListVisibilidad);
                }
            }
            for (UsuarioPerfil usuarioPerfilListUsuarioPerfil : usuario.getUsuarioPerfilList()) {
                Usuario oldIdUsuarioRegistroOfUsuarioPerfilListUsuarioPerfil = usuarioPerfilListUsuarioPerfil.getIdUsuarioRegistro();
                usuarioPerfilListUsuarioPerfil.setIdUsuarioRegistro(usuario);
                usuarioPerfilListUsuarioPerfil = em.merge(usuarioPerfilListUsuarioPerfil);
                if (oldIdUsuarioRegistroOfUsuarioPerfilListUsuarioPerfil != null) {
                    oldIdUsuarioRegistroOfUsuarioPerfilListUsuarioPerfil.getUsuarioPerfilList().remove(usuarioPerfilListUsuarioPerfil);
                    oldIdUsuarioRegistroOfUsuarioPerfilListUsuarioPerfil = em.merge(oldIdUsuarioRegistroOfUsuarioPerfilListUsuarioPerfil);
                }
            }
            for (UsuarioPerfil usuarioPerfilList1UsuarioPerfil : usuario.getUsuarioPerfilList1()) {
                Usuario oldIdUsuarioOfUsuarioPerfilList1UsuarioPerfil = usuarioPerfilList1UsuarioPerfil.getIdUsuario();
                usuarioPerfilList1UsuarioPerfil.setIdUsuario(usuario);
                usuarioPerfilList1UsuarioPerfil = em.merge(usuarioPerfilList1UsuarioPerfil);
                if (oldIdUsuarioOfUsuarioPerfilList1UsuarioPerfil != null) {
                    oldIdUsuarioOfUsuarioPerfilList1UsuarioPerfil.getUsuarioPerfilList1().remove(usuarioPerfilList1UsuarioPerfil);
                    oldIdUsuarioOfUsuarioPerfilList1UsuarioPerfil = em.merge(oldIdUsuarioOfUsuarioPerfilList1UsuarioPerfil);
                }
            }
            for (Perfil perfilListPerfil : usuario.getPerfilList()) {
                Usuario oldIdUsuarioOfPerfilListPerfil = perfilListPerfil.getIdUsuario();
                perfilListPerfil.setIdUsuario(usuario);
                perfilListPerfil = em.merge(perfilListPerfil);
                if (oldIdUsuarioOfPerfilListPerfil != null) {
                    oldIdUsuarioOfPerfilListPerfil.getPerfilList().remove(perfilListPerfil);
                    oldIdUsuarioOfPerfilListPerfil = em.merge(oldIdUsuarioOfPerfilListPerfil);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getId()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getId());
            Usuario idUsuarioOld = persistentUsuario.getIdUsuario();
            Usuario idUsuarioNew = usuario.getIdUsuario();
            List<Opcion> opcionListOld = persistentUsuario.getOpcionList();
            List<Opcion> opcionListNew = usuario.getOpcionList();
            List<OpcionPerfilVisibilidad> opcionPerfilVisibilidadListOld = persistentUsuario.getOpcionPerfilVisibilidadList();
            List<OpcionPerfilVisibilidad> opcionPerfilVisibilidadListNew = usuario.getOpcionPerfilVisibilidadList();
            List<Usuario> usuarioListOld = persistentUsuario.getUsuarioList();
            List<Usuario> usuarioListNew = usuario.getUsuarioList();
            List<Visibilidad> visibilidadListOld = persistentUsuario.getVisibilidadList();
            List<Visibilidad> visibilidadListNew = usuario.getVisibilidadList();
            List<UsuarioPerfil> usuarioPerfilListOld = persistentUsuario.getUsuarioPerfilList();
            List<UsuarioPerfil> usuarioPerfilListNew = usuario.getUsuarioPerfilList();
            List<UsuarioPerfil> usuarioPerfilList1Old = persistentUsuario.getUsuarioPerfilList1();
            List<UsuarioPerfil> usuarioPerfilList1New = usuario.getUsuarioPerfilList1();
            List<Perfil> perfilListOld = persistentUsuario.getPerfilList();
            List<Perfil> perfilListNew = usuario.getPerfilList();
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getId());
                usuario.setIdUsuario(idUsuarioNew);
            }
            List<Opcion> attachedOpcionListNew = new ArrayList<Opcion>();
            for (Opcion opcionListNewOpcionToAttach : opcionListNew) {
                opcionListNewOpcionToAttach = em.getReference(opcionListNewOpcionToAttach.getClass(), opcionListNewOpcionToAttach.getId());
                attachedOpcionListNew.add(opcionListNewOpcionToAttach);
            }
            opcionListNew = attachedOpcionListNew;
            usuario.setOpcionList(opcionListNew);
            List<OpcionPerfilVisibilidad> attachedOpcionPerfilVisibilidadListNew = new ArrayList<OpcionPerfilVisibilidad>();
            for (OpcionPerfilVisibilidad opcionPerfilVisibilidadListNewOpcionPerfilVisibilidadToAttach : opcionPerfilVisibilidadListNew) {
                opcionPerfilVisibilidadListNewOpcionPerfilVisibilidadToAttach = em.getReference(opcionPerfilVisibilidadListNewOpcionPerfilVisibilidadToAttach.getClass(), opcionPerfilVisibilidadListNewOpcionPerfilVisibilidadToAttach.getId());
                attachedOpcionPerfilVisibilidadListNew.add(opcionPerfilVisibilidadListNewOpcionPerfilVisibilidadToAttach);
            }
            opcionPerfilVisibilidadListNew = attachedOpcionPerfilVisibilidadListNew;
            usuario.setOpcionPerfilVisibilidadList(opcionPerfilVisibilidadListNew);
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getId());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            usuario.setUsuarioList(usuarioListNew);
            List<Visibilidad> attachedVisibilidadListNew = new ArrayList<Visibilidad>();
            for (Visibilidad visibilidadListNewVisibilidadToAttach : visibilidadListNew) {
                visibilidadListNewVisibilidadToAttach = em.getReference(visibilidadListNewVisibilidadToAttach.getClass(), visibilidadListNewVisibilidadToAttach.getId());
                attachedVisibilidadListNew.add(visibilidadListNewVisibilidadToAttach);
            }
            visibilidadListNew = attachedVisibilidadListNew;
            usuario.setVisibilidadList(visibilidadListNew);
            List<UsuarioPerfil> attachedUsuarioPerfilListNew = new ArrayList<UsuarioPerfil>();
            for (UsuarioPerfil usuarioPerfilListNewUsuarioPerfilToAttach : usuarioPerfilListNew) {
                usuarioPerfilListNewUsuarioPerfilToAttach = em.getReference(usuarioPerfilListNewUsuarioPerfilToAttach.getClass(), usuarioPerfilListNewUsuarioPerfilToAttach.getId());
                attachedUsuarioPerfilListNew.add(usuarioPerfilListNewUsuarioPerfilToAttach);
            }
            usuarioPerfilListNew = attachedUsuarioPerfilListNew;
            usuario.setUsuarioPerfilList(usuarioPerfilListNew);
            List<UsuarioPerfil> attachedUsuarioPerfilList1New = new ArrayList<UsuarioPerfil>();
            for (UsuarioPerfil usuarioPerfilList1NewUsuarioPerfilToAttach : usuarioPerfilList1New) {
                usuarioPerfilList1NewUsuarioPerfilToAttach = em.getReference(usuarioPerfilList1NewUsuarioPerfilToAttach.getClass(), usuarioPerfilList1NewUsuarioPerfilToAttach.getId());
                attachedUsuarioPerfilList1New.add(usuarioPerfilList1NewUsuarioPerfilToAttach);
            }
            usuarioPerfilList1New = attachedUsuarioPerfilList1New;
            usuario.setUsuarioPerfilList1(usuarioPerfilList1New);
            List<Perfil> attachedPerfilListNew = new ArrayList<Perfil>();
            for (Perfil perfilListNewPerfilToAttach : perfilListNew) {
                perfilListNewPerfilToAttach = em.getReference(perfilListNewPerfilToAttach.getClass(), perfilListNewPerfilToAttach.getId());
                attachedPerfilListNew.add(perfilListNewPerfilToAttach);
            }
            perfilListNew = attachedPerfilListNew;
            usuario.setPerfilList(perfilListNew);
            usuario = em.merge(usuario);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getUsuarioList().remove(usuario);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getUsuarioList().add(usuario);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            for (Opcion opcionListOldOpcion : opcionListOld) {
                if (!opcionListNew.contains(opcionListOldOpcion)) {
                    opcionListOldOpcion.setIdUsuario(null);
                    opcionListOldOpcion = em.merge(opcionListOldOpcion);
                }
            }
            for (Opcion opcionListNewOpcion : opcionListNew) {
                if (!opcionListOld.contains(opcionListNewOpcion)) {
                    Usuario oldIdUsuarioOfOpcionListNewOpcion = opcionListNewOpcion.getIdUsuario();
                    opcionListNewOpcion.setIdUsuario(usuario);
                    opcionListNewOpcion = em.merge(opcionListNewOpcion);
                    if (oldIdUsuarioOfOpcionListNewOpcion != null && !oldIdUsuarioOfOpcionListNewOpcion.equals(usuario)) {
                        oldIdUsuarioOfOpcionListNewOpcion.getOpcionList().remove(opcionListNewOpcion);
                        oldIdUsuarioOfOpcionListNewOpcion = em.merge(oldIdUsuarioOfOpcionListNewOpcion);
                    }
                }
            }
            for (OpcionPerfilVisibilidad opcionPerfilVisibilidadListOldOpcionPerfilVisibilidad : opcionPerfilVisibilidadListOld) {
                if (!opcionPerfilVisibilidadListNew.contains(opcionPerfilVisibilidadListOldOpcionPerfilVisibilidad)) {
                    opcionPerfilVisibilidadListOldOpcionPerfilVisibilidad.setIdUsuario(null);
                    opcionPerfilVisibilidadListOldOpcionPerfilVisibilidad = em.merge(opcionPerfilVisibilidadListOldOpcionPerfilVisibilidad);
                }
            }
            for (OpcionPerfilVisibilidad opcionPerfilVisibilidadListNewOpcionPerfilVisibilidad : opcionPerfilVisibilidadListNew) {
                if (!opcionPerfilVisibilidadListOld.contains(opcionPerfilVisibilidadListNewOpcionPerfilVisibilidad)) {
                    Usuario oldIdUsuarioOfOpcionPerfilVisibilidadListNewOpcionPerfilVisibilidad = opcionPerfilVisibilidadListNewOpcionPerfilVisibilidad.getIdUsuario();
                    opcionPerfilVisibilidadListNewOpcionPerfilVisibilidad.setIdUsuario(usuario);
                    opcionPerfilVisibilidadListNewOpcionPerfilVisibilidad = em.merge(opcionPerfilVisibilidadListNewOpcionPerfilVisibilidad);
                    if (oldIdUsuarioOfOpcionPerfilVisibilidadListNewOpcionPerfilVisibilidad != null && !oldIdUsuarioOfOpcionPerfilVisibilidadListNewOpcionPerfilVisibilidad.equals(usuario)) {
                        oldIdUsuarioOfOpcionPerfilVisibilidadListNewOpcionPerfilVisibilidad.getOpcionPerfilVisibilidadList().remove(opcionPerfilVisibilidadListNewOpcionPerfilVisibilidad);
                        oldIdUsuarioOfOpcionPerfilVisibilidadListNewOpcionPerfilVisibilidad = em.merge(oldIdUsuarioOfOpcionPerfilVisibilidadListNewOpcionPerfilVisibilidad);
                    }
                }
            }
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    usuarioListOldUsuario.setIdUsuario(null);
                    usuarioListOldUsuario = em.merge(usuarioListOldUsuario);
                }
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    Usuario oldIdUsuarioOfUsuarioListNewUsuario = usuarioListNewUsuario.getIdUsuario();
                    usuarioListNewUsuario.setIdUsuario(usuario);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldIdUsuarioOfUsuarioListNewUsuario != null && !oldIdUsuarioOfUsuarioListNewUsuario.equals(usuario)) {
                        oldIdUsuarioOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldIdUsuarioOfUsuarioListNewUsuario = em.merge(oldIdUsuarioOfUsuarioListNewUsuario);
                    }
                }
            }
            for (Visibilidad visibilidadListOldVisibilidad : visibilidadListOld) {
                if (!visibilidadListNew.contains(visibilidadListOldVisibilidad)) {
                    visibilidadListOldVisibilidad.setIdUsuario(null);
                    visibilidadListOldVisibilidad = em.merge(visibilidadListOldVisibilidad);
                }
            }
            for (Visibilidad visibilidadListNewVisibilidad : visibilidadListNew) {
                if (!visibilidadListOld.contains(visibilidadListNewVisibilidad)) {
                    Usuario oldIdUsuarioOfVisibilidadListNewVisibilidad = visibilidadListNewVisibilidad.getIdUsuario();
                    visibilidadListNewVisibilidad.setIdUsuario(usuario);
                    visibilidadListNewVisibilidad = em.merge(visibilidadListNewVisibilidad);
                    if (oldIdUsuarioOfVisibilidadListNewVisibilidad != null && !oldIdUsuarioOfVisibilidadListNewVisibilidad.equals(usuario)) {
                        oldIdUsuarioOfVisibilidadListNewVisibilidad.getVisibilidadList().remove(visibilidadListNewVisibilidad);
                        oldIdUsuarioOfVisibilidadListNewVisibilidad = em.merge(oldIdUsuarioOfVisibilidadListNewVisibilidad);
                    }
                }
            }
            for (UsuarioPerfil usuarioPerfilListOldUsuarioPerfil : usuarioPerfilListOld) {
                if (!usuarioPerfilListNew.contains(usuarioPerfilListOldUsuarioPerfil)) {
                    usuarioPerfilListOldUsuarioPerfil.setIdUsuarioRegistro(null);
                    usuarioPerfilListOldUsuarioPerfil = em.merge(usuarioPerfilListOldUsuarioPerfil);
                }
            }
            for (UsuarioPerfil usuarioPerfilListNewUsuarioPerfil : usuarioPerfilListNew) {
                if (!usuarioPerfilListOld.contains(usuarioPerfilListNewUsuarioPerfil)) {
                    Usuario oldIdUsuarioRegistroOfUsuarioPerfilListNewUsuarioPerfil = usuarioPerfilListNewUsuarioPerfil.getIdUsuarioRegistro();
                    usuarioPerfilListNewUsuarioPerfil.setIdUsuarioRegistro(usuario);
                    usuarioPerfilListNewUsuarioPerfil = em.merge(usuarioPerfilListNewUsuarioPerfil);
                    if (oldIdUsuarioRegistroOfUsuarioPerfilListNewUsuarioPerfil != null && !oldIdUsuarioRegistroOfUsuarioPerfilListNewUsuarioPerfil.equals(usuario)) {
                        oldIdUsuarioRegistroOfUsuarioPerfilListNewUsuarioPerfil.getUsuarioPerfilList().remove(usuarioPerfilListNewUsuarioPerfil);
                        oldIdUsuarioRegistroOfUsuarioPerfilListNewUsuarioPerfil = em.merge(oldIdUsuarioRegistroOfUsuarioPerfilListNewUsuarioPerfil);
                    }
                }
            }
            for (UsuarioPerfil usuarioPerfilList1OldUsuarioPerfil : usuarioPerfilList1Old) {
                if (!usuarioPerfilList1New.contains(usuarioPerfilList1OldUsuarioPerfil)) {
                    usuarioPerfilList1OldUsuarioPerfil.setIdUsuario(null);
                    usuarioPerfilList1OldUsuarioPerfil = em.merge(usuarioPerfilList1OldUsuarioPerfil);
                }
            }
            for (UsuarioPerfil usuarioPerfilList1NewUsuarioPerfil : usuarioPerfilList1New) {
                if (!usuarioPerfilList1Old.contains(usuarioPerfilList1NewUsuarioPerfil)) {
                    Usuario oldIdUsuarioOfUsuarioPerfilList1NewUsuarioPerfil = usuarioPerfilList1NewUsuarioPerfil.getIdUsuario();
                    usuarioPerfilList1NewUsuarioPerfil.setIdUsuario(usuario);
                    usuarioPerfilList1NewUsuarioPerfil = em.merge(usuarioPerfilList1NewUsuarioPerfil);
                    if (oldIdUsuarioOfUsuarioPerfilList1NewUsuarioPerfil != null && !oldIdUsuarioOfUsuarioPerfilList1NewUsuarioPerfil.equals(usuario)) {
                        oldIdUsuarioOfUsuarioPerfilList1NewUsuarioPerfil.getUsuarioPerfilList1().remove(usuarioPerfilList1NewUsuarioPerfil);
                        oldIdUsuarioOfUsuarioPerfilList1NewUsuarioPerfil = em.merge(oldIdUsuarioOfUsuarioPerfilList1NewUsuarioPerfil);
                    }
                }
            }
            for (Perfil perfilListOldPerfil : perfilListOld) {
                if (!perfilListNew.contains(perfilListOldPerfil)) {
                    perfilListOldPerfil.setIdUsuario(null);
                    perfilListOldPerfil = em.merge(perfilListOldPerfil);
                }
            }
            for (Perfil perfilListNewPerfil : perfilListNew) {
                if (!perfilListOld.contains(perfilListNewPerfil)) {
                    Usuario oldIdUsuarioOfPerfilListNewPerfil = perfilListNewPerfil.getIdUsuario();
                    perfilListNewPerfil.setIdUsuario(usuario);
                    perfilListNewPerfil = em.merge(perfilListNewPerfil);
                    if (oldIdUsuarioOfPerfilListNewPerfil != null && !oldIdUsuarioOfPerfilListNewPerfil.equals(usuario)) {
                        oldIdUsuarioOfPerfilListNewPerfil.getPerfilList().remove(perfilListNewPerfil);
                        oldIdUsuarioOfPerfilListNewPerfil = em.merge(oldIdUsuarioOfPerfilListNewPerfil);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            Usuario idUsuario = usuario.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getUsuarioList().remove(usuario);
                idUsuario = em.merge(idUsuario);
            }
            List<Opcion> opcionList = usuario.getOpcionList();
            for (Opcion opcionListOpcion : opcionList) {
                opcionListOpcion.setIdUsuario(null);
                opcionListOpcion = em.merge(opcionListOpcion);
            }
            List<OpcionPerfilVisibilidad> opcionPerfilVisibilidadList = usuario.getOpcionPerfilVisibilidadList();
            for (OpcionPerfilVisibilidad opcionPerfilVisibilidadListOpcionPerfilVisibilidad : opcionPerfilVisibilidadList) {
                opcionPerfilVisibilidadListOpcionPerfilVisibilidad.setIdUsuario(null);
                opcionPerfilVisibilidadListOpcionPerfilVisibilidad = em.merge(opcionPerfilVisibilidadListOpcionPerfilVisibilidad);
            }
            List<Usuario> usuarioList = usuario.getUsuarioList();
            for (Usuario usuarioListUsuario : usuarioList) {
                usuarioListUsuario.setIdUsuario(null);
                usuarioListUsuario = em.merge(usuarioListUsuario);
            }
            List<Visibilidad> visibilidadList = usuario.getVisibilidadList();
            for (Visibilidad visibilidadListVisibilidad : visibilidadList) {
                visibilidadListVisibilidad.setIdUsuario(null);
                visibilidadListVisibilidad = em.merge(visibilidadListVisibilidad);
            }
            List<UsuarioPerfil> usuarioPerfilList = usuario.getUsuarioPerfilList();
            for (UsuarioPerfil usuarioPerfilListUsuarioPerfil : usuarioPerfilList) {
                usuarioPerfilListUsuarioPerfil.setIdUsuarioRegistro(null);
                usuarioPerfilListUsuarioPerfil = em.merge(usuarioPerfilListUsuarioPerfil);
            }
            List<UsuarioPerfil> usuarioPerfilList1 = usuario.getUsuarioPerfilList1();
            for (UsuarioPerfil usuarioPerfilList1UsuarioPerfil : usuarioPerfilList1) {
                usuarioPerfilList1UsuarioPerfil.setIdUsuario(null);
                usuarioPerfilList1UsuarioPerfil = em.merge(usuarioPerfilList1UsuarioPerfil);
            }
            List<Perfil> perfilList = usuario.getPerfilList();
            for (Perfil perfilListPerfil : perfilList) {
                perfilListPerfil.setIdUsuario(null);
                perfilListPerfil = em.merge(perfilListPerfil);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
