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
import com.sae.persistence.domain.OrigenDerechoPeticion;
import com.sae.persistence.domain.MotivoDerechoPeticion;
import com.sae.persistence.domain.ContenidoDerechoPeticion;
import com.sae.persistence.domain.DerechoPeticion;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.FiguraDerechoPeticion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class DerechoPeticionJpaController implements Serializable {

    public DerechoPeticionJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DerechoPeticion derechoPeticion) throws PreexistingEntityException, Exception {
        if (derechoPeticion.getContenidoDerechoPeticionList() == null) {
            derechoPeticion.setContenidoDerechoPeticionList(new ArrayList<ContenidoDerechoPeticion>());
        }
        if (derechoPeticion.getFiguraDerechoPeticionList() == null) {
            derechoPeticion.setFiguraDerechoPeticionList(new ArrayList<FiguraDerechoPeticion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrigenDerechoPeticion idOrigen = derechoPeticion.getIdOrigen();
            if (idOrigen != null) {
                idOrigen = em.getReference(idOrigen.getClass(), idOrigen.getId());
                derechoPeticion.setIdOrigen(idOrigen);
            }
            MotivoDerechoPeticion idMotivo = derechoPeticion.getIdMotivo();
            if (idMotivo != null) {
                idMotivo = em.getReference(idMotivo.getClass(), idMotivo.getId());
                derechoPeticion.setIdMotivo(idMotivo);
            }
            List<ContenidoDerechoPeticion> attachedContenidoDerechoPeticionList = new ArrayList<ContenidoDerechoPeticion>();
            for (ContenidoDerechoPeticion contenidoDerechoPeticionListContenidoDerechoPeticionToAttach : derechoPeticion.getContenidoDerechoPeticionList()) {
                contenidoDerechoPeticionListContenidoDerechoPeticionToAttach = em.getReference(contenidoDerechoPeticionListContenidoDerechoPeticionToAttach.getClass(), contenidoDerechoPeticionListContenidoDerechoPeticionToAttach.getId());
                attachedContenidoDerechoPeticionList.add(contenidoDerechoPeticionListContenidoDerechoPeticionToAttach);
            }
            derechoPeticion.setContenidoDerechoPeticionList(attachedContenidoDerechoPeticionList);
            List<FiguraDerechoPeticion> attachedFiguraDerechoPeticionList = new ArrayList<FiguraDerechoPeticion>();
            for (FiguraDerechoPeticion figuraDerechoPeticionListFiguraDerechoPeticionToAttach : derechoPeticion.getFiguraDerechoPeticionList()) {
                figuraDerechoPeticionListFiguraDerechoPeticionToAttach = em.getReference(figuraDerechoPeticionListFiguraDerechoPeticionToAttach.getClass(), figuraDerechoPeticionListFiguraDerechoPeticionToAttach.getId());
                attachedFiguraDerechoPeticionList.add(figuraDerechoPeticionListFiguraDerechoPeticionToAttach);
            }
            derechoPeticion.setFiguraDerechoPeticionList(attachedFiguraDerechoPeticionList);
            em.persist(derechoPeticion);
            if (idOrigen != null) {
                idOrigen.getDerechoPeticionList().add(derechoPeticion);
                idOrigen = em.merge(idOrigen);
            }
            if (idMotivo != null) {
                idMotivo.getDerechoPeticionList().add(derechoPeticion);
                idMotivo = em.merge(idMotivo);
            }
            for (ContenidoDerechoPeticion contenidoDerechoPeticionListContenidoDerechoPeticion : derechoPeticion.getContenidoDerechoPeticionList()) {
                DerechoPeticion oldIdDerechoOfContenidoDerechoPeticionListContenidoDerechoPeticion = contenidoDerechoPeticionListContenidoDerechoPeticion.getIdDerecho();
                contenidoDerechoPeticionListContenidoDerechoPeticion.setIdDerecho(derechoPeticion);
                contenidoDerechoPeticionListContenidoDerechoPeticion = em.merge(contenidoDerechoPeticionListContenidoDerechoPeticion);
                if (oldIdDerechoOfContenidoDerechoPeticionListContenidoDerechoPeticion != null) {
                    oldIdDerechoOfContenidoDerechoPeticionListContenidoDerechoPeticion.getContenidoDerechoPeticionList().remove(contenidoDerechoPeticionListContenidoDerechoPeticion);
                    oldIdDerechoOfContenidoDerechoPeticionListContenidoDerechoPeticion = em.merge(oldIdDerechoOfContenidoDerechoPeticionListContenidoDerechoPeticion);
                }
            }
            for (FiguraDerechoPeticion figuraDerechoPeticionListFiguraDerechoPeticion : derechoPeticion.getFiguraDerechoPeticionList()) {
                DerechoPeticion oldIdDerechoPeticionOfFiguraDerechoPeticionListFiguraDerechoPeticion = figuraDerechoPeticionListFiguraDerechoPeticion.getIdDerechoPeticion();
                figuraDerechoPeticionListFiguraDerechoPeticion.setIdDerechoPeticion(derechoPeticion);
                figuraDerechoPeticionListFiguraDerechoPeticion = em.merge(figuraDerechoPeticionListFiguraDerechoPeticion);
                if (oldIdDerechoPeticionOfFiguraDerechoPeticionListFiguraDerechoPeticion != null) {
                    oldIdDerechoPeticionOfFiguraDerechoPeticionListFiguraDerechoPeticion.getFiguraDerechoPeticionList().remove(figuraDerechoPeticionListFiguraDerechoPeticion);
                    oldIdDerechoPeticionOfFiguraDerechoPeticionListFiguraDerechoPeticion = em.merge(oldIdDerechoPeticionOfFiguraDerechoPeticionListFiguraDerechoPeticion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDerechoPeticion(derechoPeticion.getId()) != null) {
                throw new PreexistingEntityException("DerechoPeticion " + derechoPeticion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DerechoPeticion derechoPeticion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DerechoPeticion persistentDerechoPeticion = em.find(DerechoPeticion.class, derechoPeticion.getId());
            OrigenDerechoPeticion idOrigenOld = persistentDerechoPeticion.getIdOrigen();
            OrigenDerechoPeticion idOrigenNew = derechoPeticion.getIdOrigen();
            MotivoDerechoPeticion idMotivoOld = persistentDerechoPeticion.getIdMotivo();
            MotivoDerechoPeticion idMotivoNew = derechoPeticion.getIdMotivo();
            List<ContenidoDerechoPeticion> contenidoDerechoPeticionListOld = persistentDerechoPeticion.getContenidoDerechoPeticionList();
            List<ContenidoDerechoPeticion> contenidoDerechoPeticionListNew = derechoPeticion.getContenidoDerechoPeticionList();
            List<FiguraDerechoPeticion> figuraDerechoPeticionListOld = persistentDerechoPeticion.getFiguraDerechoPeticionList();
            List<FiguraDerechoPeticion> figuraDerechoPeticionListNew = derechoPeticion.getFiguraDerechoPeticionList();
            if (idOrigenNew != null) {
                idOrigenNew = em.getReference(idOrigenNew.getClass(), idOrigenNew.getId());
                derechoPeticion.setIdOrigen(idOrigenNew);
            }
            if (idMotivoNew != null) {
                idMotivoNew = em.getReference(idMotivoNew.getClass(), idMotivoNew.getId());
                derechoPeticion.setIdMotivo(idMotivoNew);
            }
            List<ContenidoDerechoPeticion> attachedContenidoDerechoPeticionListNew = new ArrayList<ContenidoDerechoPeticion>();
            for (ContenidoDerechoPeticion contenidoDerechoPeticionListNewContenidoDerechoPeticionToAttach : contenidoDerechoPeticionListNew) {
                contenidoDerechoPeticionListNewContenidoDerechoPeticionToAttach = em.getReference(contenidoDerechoPeticionListNewContenidoDerechoPeticionToAttach.getClass(), contenidoDerechoPeticionListNewContenidoDerechoPeticionToAttach.getId());
                attachedContenidoDerechoPeticionListNew.add(contenidoDerechoPeticionListNewContenidoDerechoPeticionToAttach);
            }
            contenidoDerechoPeticionListNew = attachedContenidoDerechoPeticionListNew;
            derechoPeticion.setContenidoDerechoPeticionList(contenidoDerechoPeticionListNew);
            List<FiguraDerechoPeticion> attachedFiguraDerechoPeticionListNew = new ArrayList<FiguraDerechoPeticion>();
            for (FiguraDerechoPeticion figuraDerechoPeticionListNewFiguraDerechoPeticionToAttach : figuraDerechoPeticionListNew) {
                figuraDerechoPeticionListNewFiguraDerechoPeticionToAttach = em.getReference(figuraDerechoPeticionListNewFiguraDerechoPeticionToAttach.getClass(), figuraDerechoPeticionListNewFiguraDerechoPeticionToAttach.getId());
                attachedFiguraDerechoPeticionListNew.add(figuraDerechoPeticionListNewFiguraDerechoPeticionToAttach);
            }
            figuraDerechoPeticionListNew = attachedFiguraDerechoPeticionListNew;
            derechoPeticion.setFiguraDerechoPeticionList(figuraDerechoPeticionListNew);
            derechoPeticion = em.merge(derechoPeticion);
            if (idOrigenOld != null && !idOrigenOld.equals(idOrigenNew)) {
                idOrigenOld.getDerechoPeticionList().remove(derechoPeticion);
                idOrigenOld = em.merge(idOrigenOld);
            }
            if (idOrigenNew != null && !idOrigenNew.equals(idOrigenOld)) {
                idOrigenNew.getDerechoPeticionList().add(derechoPeticion);
                idOrigenNew = em.merge(idOrigenNew);
            }
            if (idMotivoOld != null && !idMotivoOld.equals(idMotivoNew)) {
                idMotivoOld.getDerechoPeticionList().remove(derechoPeticion);
                idMotivoOld = em.merge(idMotivoOld);
            }
            if (idMotivoNew != null && !idMotivoNew.equals(idMotivoOld)) {
                idMotivoNew.getDerechoPeticionList().add(derechoPeticion);
                idMotivoNew = em.merge(idMotivoNew);
            }
            for (ContenidoDerechoPeticion contenidoDerechoPeticionListOldContenidoDerechoPeticion : contenidoDerechoPeticionListOld) {
                if (!contenidoDerechoPeticionListNew.contains(contenidoDerechoPeticionListOldContenidoDerechoPeticion)) {
                    contenidoDerechoPeticionListOldContenidoDerechoPeticion.setIdDerecho(null);
                    contenidoDerechoPeticionListOldContenidoDerechoPeticion = em.merge(contenidoDerechoPeticionListOldContenidoDerechoPeticion);
                }
            }
            for (ContenidoDerechoPeticion contenidoDerechoPeticionListNewContenidoDerechoPeticion : contenidoDerechoPeticionListNew) {
                if (!contenidoDerechoPeticionListOld.contains(contenidoDerechoPeticionListNewContenidoDerechoPeticion)) {
                    DerechoPeticion oldIdDerechoOfContenidoDerechoPeticionListNewContenidoDerechoPeticion = contenidoDerechoPeticionListNewContenidoDerechoPeticion.getIdDerecho();
                    contenidoDerechoPeticionListNewContenidoDerechoPeticion.setIdDerecho(derechoPeticion);
                    contenidoDerechoPeticionListNewContenidoDerechoPeticion = em.merge(contenidoDerechoPeticionListNewContenidoDerechoPeticion);
                    if (oldIdDerechoOfContenidoDerechoPeticionListNewContenidoDerechoPeticion != null && !oldIdDerechoOfContenidoDerechoPeticionListNewContenidoDerechoPeticion.equals(derechoPeticion)) {
                        oldIdDerechoOfContenidoDerechoPeticionListNewContenidoDerechoPeticion.getContenidoDerechoPeticionList().remove(contenidoDerechoPeticionListNewContenidoDerechoPeticion);
                        oldIdDerechoOfContenidoDerechoPeticionListNewContenidoDerechoPeticion = em.merge(oldIdDerechoOfContenidoDerechoPeticionListNewContenidoDerechoPeticion);
                    }
                }
            }
            for (FiguraDerechoPeticion figuraDerechoPeticionListOldFiguraDerechoPeticion : figuraDerechoPeticionListOld) {
                if (!figuraDerechoPeticionListNew.contains(figuraDerechoPeticionListOldFiguraDerechoPeticion)) {
                    figuraDerechoPeticionListOldFiguraDerechoPeticion.setIdDerechoPeticion(null);
                    figuraDerechoPeticionListOldFiguraDerechoPeticion = em.merge(figuraDerechoPeticionListOldFiguraDerechoPeticion);
                }
            }
            for (FiguraDerechoPeticion figuraDerechoPeticionListNewFiguraDerechoPeticion : figuraDerechoPeticionListNew) {
                if (!figuraDerechoPeticionListOld.contains(figuraDerechoPeticionListNewFiguraDerechoPeticion)) {
                    DerechoPeticion oldIdDerechoPeticionOfFiguraDerechoPeticionListNewFiguraDerechoPeticion = figuraDerechoPeticionListNewFiguraDerechoPeticion.getIdDerechoPeticion();
                    figuraDerechoPeticionListNewFiguraDerechoPeticion.setIdDerechoPeticion(derechoPeticion);
                    figuraDerechoPeticionListNewFiguraDerechoPeticion = em.merge(figuraDerechoPeticionListNewFiguraDerechoPeticion);
                    if (oldIdDerechoPeticionOfFiguraDerechoPeticionListNewFiguraDerechoPeticion != null && !oldIdDerechoPeticionOfFiguraDerechoPeticionListNewFiguraDerechoPeticion.equals(derechoPeticion)) {
                        oldIdDerechoPeticionOfFiguraDerechoPeticionListNewFiguraDerechoPeticion.getFiguraDerechoPeticionList().remove(figuraDerechoPeticionListNewFiguraDerechoPeticion);
                        oldIdDerechoPeticionOfFiguraDerechoPeticionListNewFiguraDerechoPeticion = em.merge(oldIdDerechoPeticionOfFiguraDerechoPeticionListNewFiguraDerechoPeticion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = derechoPeticion.getId();
                if (findDerechoPeticion(id) == null) {
                    throw new NonexistentEntityException("The derechoPeticion with id " + id + " no longer exists.");
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
            DerechoPeticion derechoPeticion;
            try {
                derechoPeticion = em.getReference(DerechoPeticion.class, id);
                derechoPeticion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The derechoPeticion with id " + id + " no longer exists.", enfe);
            }
            OrigenDerechoPeticion idOrigen = derechoPeticion.getIdOrigen();
            if (idOrigen != null) {
                idOrigen.getDerechoPeticionList().remove(derechoPeticion);
                idOrigen = em.merge(idOrigen);
            }
            MotivoDerechoPeticion idMotivo = derechoPeticion.getIdMotivo();
            if (idMotivo != null) {
                idMotivo.getDerechoPeticionList().remove(derechoPeticion);
                idMotivo = em.merge(idMotivo);
            }
            List<ContenidoDerechoPeticion> contenidoDerechoPeticionList = derechoPeticion.getContenidoDerechoPeticionList();
            for (ContenidoDerechoPeticion contenidoDerechoPeticionListContenidoDerechoPeticion : contenidoDerechoPeticionList) {
                contenidoDerechoPeticionListContenidoDerechoPeticion.setIdDerecho(null);
                contenidoDerechoPeticionListContenidoDerechoPeticion = em.merge(contenidoDerechoPeticionListContenidoDerechoPeticion);
            }
            List<FiguraDerechoPeticion> figuraDerechoPeticionList = derechoPeticion.getFiguraDerechoPeticionList();
            for (FiguraDerechoPeticion figuraDerechoPeticionListFiguraDerechoPeticion : figuraDerechoPeticionList) {
                figuraDerechoPeticionListFiguraDerechoPeticion.setIdDerechoPeticion(null);
                figuraDerechoPeticionListFiguraDerechoPeticion = em.merge(figuraDerechoPeticionListFiguraDerechoPeticion);
            }
            em.remove(derechoPeticion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DerechoPeticion> findDerechoPeticionEntities() {
        return findDerechoPeticionEntities(true, -1, -1);
    }

    public List<DerechoPeticion> findDerechoPeticionEntities(int maxResults, int firstResult) {
        return findDerechoPeticionEntities(false, maxResults, firstResult);
    }

    private List<DerechoPeticion> findDerechoPeticionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DerechoPeticion.class));
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

    public DerechoPeticion findDerechoPeticion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DerechoPeticion.class, id);
        } finally {
            em.close();
        }
    }

    public int getDerechoPeticionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DerechoPeticion> rt = cq.from(DerechoPeticion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
