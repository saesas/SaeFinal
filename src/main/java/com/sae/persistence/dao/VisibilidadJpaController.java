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
import com.sae.persistence.domain.Visibilidad;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class VisibilidadJpaController implements Serializable {

    public VisibilidadJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Visibilidad visibilidad) throws PreexistingEntityException, Exception {
        if (visibilidad.getOpcionPerfilVisibilidadList() == null) {
            visibilidad.setOpcionPerfilVisibilidadList(new ArrayList<OpcionPerfilVisibilidad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario idUsuario = visibilidad.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getId());
                visibilidad.setIdUsuario(idUsuario);
            }
            List<OpcionPerfilVisibilidad> attachedOpcionPerfilVisibilidadList = new ArrayList<OpcionPerfilVisibilidad>();
            for (OpcionPerfilVisibilidad opcionPerfilVisibilidadListOpcionPerfilVisibilidadToAttach : visibilidad.getOpcionPerfilVisibilidadList()) {
                opcionPerfilVisibilidadListOpcionPerfilVisibilidadToAttach = em.getReference(opcionPerfilVisibilidadListOpcionPerfilVisibilidadToAttach.getClass(), opcionPerfilVisibilidadListOpcionPerfilVisibilidadToAttach.getId());
                attachedOpcionPerfilVisibilidadList.add(opcionPerfilVisibilidadListOpcionPerfilVisibilidadToAttach);
            }
            visibilidad.setOpcionPerfilVisibilidadList(attachedOpcionPerfilVisibilidadList);
            em.persist(visibilidad);
            if (idUsuario != null) {
                idUsuario.getVisibilidadList().add(visibilidad);
                idUsuario = em.merge(idUsuario);
            }
            for (OpcionPerfilVisibilidad opcionPerfilVisibilidadListOpcionPerfilVisibilidad : visibilidad.getOpcionPerfilVisibilidadList()) {
                Visibilidad oldIdVisibilidadOfOpcionPerfilVisibilidadListOpcionPerfilVisibilidad = opcionPerfilVisibilidadListOpcionPerfilVisibilidad.getIdVisibilidad();
                opcionPerfilVisibilidadListOpcionPerfilVisibilidad.setIdVisibilidad(visibilidad);
                opcionPerfilVisibilidadListOpcionPerfilVisibilidad = em.merge(opcionPerfilVisibilidadListOpcionPerfilVisibilidad);
                if (oldIdVisibilidadOfOpcionPerfilVisibilidadListOpcionPerfilVisibilidad != null) {
                    oldIdVisibilidadOfOpcionPerfilVisibilidadListOpcionPerfilVisibilidad.getOpcionPerfilVisibilidadList().remove(opcionPerfilVisibilidadListOpcionPerfilVisibilidad);
                    oldIdVisibilidadOfOpcionPerfilVisibilidadListOpcionPerfilVisibilidad = em.merge(oldIdVisibilidadOfOpcionPerfilVisibilidadListOpcionPerfilVisibilidad);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVisibilidad(visibilidad.getId()) != null) {
                throw new PreexistingEntityException("Visibilidad " + visibilidad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Visibilidad visibilidad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Visibilidad persistentVisibilidad = em.find(Visibilidad.class, visibilidad.getId());
            Usuario idUsuarioOld = persistentVisibilidad.getIdUsuario();
            Usuario idUsuarioNew = visibilidad.getIdUsuario();
            List<OpcionPerfilVisibilidad> opcionPerfilVisibilidadListOld = persistentVisibilidad.getOpcionPerfilVisibilidadList();
            List<OpcionPerfilVisibilidad> opcionPerfilVisibilidadListNew = visibilidad.getOpcionPerfilVisibilidadList();
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getId());
                visibilidad.setIdUsuario(idUsuarioNew);
            }
            List<OpcionPerfilVisibilidad> attachedOpcionPerfilVisibilidadListNew = new ArrayList<OpcionPerfilVisibilidad>();
            for (OpcionPerfilVisibilidad opcionPerfilVisibilidadListNewOpcionPerfilVisibilidadToAttach : opcionPerfilVisibilidadListNew) {
                opcionPerfilVisibilidadListNewOpcionPerfilVisibilidadToAttach = em.getReference(opcionPerfilVisibilidadListNewOpcionPerfilVisibilidadToAttach.getClass(), opcionPerfilVisibilidadListNewOpcionPerfilVisibilidadToAttach.getId());
                attachedOpcionPerfilVisibilidadListNew.add(opcionPerfilVisibilidadListNewOpcionPerfilVisibilidadToAttach);
            }
            opcionPerfilVisibilidadListNew = attachedOpcionPerfilVisibilidadListNew;
            visibilidad.setOpcionPerfilVisibilidadList(opcionPerfilVisibilidadListNew);
            visibilidad = em.merge(visibilidad);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getVisibilidadList().remove(visibilidad);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getVisibilidadList().add(visibilidad);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            for (OpcionPerfilVisibilidad opcionPerfilVisibilidadListOldOpcionPerfilVisibilidad : opcionPerfilVisibilidadListOld) {
                if (!opcionPerfilVisibilidadListNew.contains(opcionPerfilVisibilidadListOldOpcionPerfilVisibilidad)) {
                    opcionPerfilVisibilidadListOldOpcionPerfilVisibilidad.setIdVisibilidad(null);
                    opcionPerfilVisibilidadListOldOpcionPerfilVisibilidad = em.merge(opcionPerfilVisibilidadListOldOpcionPerfilVisibilidad);
                }
            }
            for (OpcionPerfilVisibilidad opcionPerfilVisibilidadListNewOpcionPerfilVisibilidad : opcionPerfilVisibilidadListNew) {
                if (!opcionPerfilVisibilidadListOld.contains(opcionPerfilVisibilidadListNewOpcionPerfilVisibilidad)) {
                    Visibilidad oldIdVisibilidadOfOpcionPerfilVisibilidadListNewOpcionPerfilVisibilidad = opcionPerfilVisibilidadListNewOpcionPerfilVisibilidad.getIdVisibilidad();
                    opcionPerfilVisibilidadListNewOpcionPerfilVisibilidad.setIdVisibilidad(visibilidad);
                    opcionPerfilVisibilidadListNewOpcionPerfilVisibilidad = em.merge(opcionPerfilVisibilidadListNewOpcionPerfilVisibilidad);
                    if (oldIdVisibilidadOfOpcionPerfilVisibilidadListNewOpcionPerfilVisibilidad != null && !oldIdVisibilidadOfOpcionPerfilVisibilidadListNewOpcionPerfilVisibilidad.equals(visibilidad)) {
                        oldIdVisibilidadOfOpcionPerfilVisibilidadListNewOpcionPerfilVisibilidad.getOpcionPerfilVisibilidadList().remove(opcionPerfilVisibilidadListNewOpcionPerfilVisibilidad);
                        oldIdVisibilidadOfOpcionPerfilVisibilidadListNewOpcionPerfilVisibilidad = em.merge(oldIdVisibilidadOfOpcionPerfilVisibilidadListNewOpcionPerfilVisibilidad);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = visibilidad.getId();
                if (findVisibilidad(id) == null) {
                    throw new NonexistentEntityException("The visibilidad with id " + id + " no longer exists.");
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
            Visibilidad visibilidad;
            try {
                visibilidad = em.getReference(Visibilidad.class, id);
                visibilidad.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The visibilidad with id " + id + " no longer exists.", enfe);
            }
            Usuario idUsuario = visibilidad.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getVisibilidadList().remove(visibilidad);
                idUsuario = em.merge(idUsuario);
            }
            List<OpcionPerfilVisibilidad> opcionPerfilVisibilidadList = visibilidad.getOpcionPerfilVisibilidadList();
            for (OpcionPerfilVisibilidad opcionPerfilVisibilidadListOpcionPerfilVisibilidad : opcionPerfilVisibilidadList) {
                opcionPerfilVisibilidadListOpcionPerfilVisibilidad.setIdVisibilidad(null);
                opcionPerfilVisibilidadListOpcionPerfilVisibilidad = em.merge(opcionPerfilVisibilidadListOpcionPerfilVisibilidad);
            }
            em.remove(visibilidad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Visibilidad> findVisibilidadEntities() {
        return findVisibilidadEntities(true, -1, -1);
    }

    public List<Visibilidad> findVisibilidadEntities(int maxResults, int firstResult) {
        return findVisibilidadEntities(false, maxResults, firstResult);
    }

    private List<Visibilidad> findVisibilidadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Visibilidad.class));
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

    public Visibilidad findVisibilidad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Visibilidad.class, id);
        } finally {
            em.close();
        }
    }

    public int getVisibilidadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Visibilidad> rt = cq.from(Visibilidad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
