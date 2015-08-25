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
import com.sae.persistence.domain.TipoEstadoNormatividad;
import com.sae.persistence.domain.Tema;
import com.sae.persistence.domain.Clase;
import com.sae.persistence.domain.Normatividad;
import com.sae.persistence.domain.SeguimientoNormatividad;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class NormatividadJpaController implements Serializable {

    public NormatividadJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Normatividad normatividad) throws PreexistingEntityException, Exception {
        if (normatividad.getSeguimientoNormatividadList() == null) {
            normatividad.setSeguimientoNormatividadList(new ArrayList<SeguimientoNormatividad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoEstadoNormatividad idEstado = normatividad.getIdEstado();
            if (idEstado != null) {
                idEstado = em.getReference(idEstado.getClass(), idEstado.getId());
                normatividad.setIdEstado(idEstado);
            }
            Tema idTema = normatividad.getIdTema();
            if (idTema != null) {
                idTema = em.getReference(idTema.getClass(), idTema.getId());
                normatividad.setIdTema(idTema);
            }
            Clase idClaseNormatividad = normatividad.getIdClaseNormatividad();
            if (idClaseNormatividad != null) {
                idClaseNormatividad = em.getReference(idClaseNormatividad.getClass(), idClaseNormatividad.getId());
                normatividad.setIdClaseNormatividad(idClaseNormatividad);
            }
            Clase idAreaDerecho = normatividad.getIdAreaDerecho();
            if (idAreaDerecho != null) {
                idAreaDerecho = em.getReference(idAreaDerecho.getClass(), idAreaDerecho.getId());
                normatividad.setIdAreaDerecho(idAreaDerecho);
            }
            List<SeguimientoNormatividad> attachedSeguimientoNormatividadList = new ArrayList<SeguimientoNormatividad>();
            for (SeguimientoNormatividad seguimientoNormatividadListSeguimientoNormatividadToAttach : normatividad.getSeguimientoNormatividadList()) {
                seguimientoNormatividadListSeguimientoNormatividadToAttach = em.getReference(seguimientoNormatividadListSeguimientoNormatividadToAttach.getClass(), seguimientoNormatividadListSeguimientoNormatividadToAttach.getId());
                attachedSeguimientoNormatividadList.add(seguimientoNormatividadListSeguimientoNormatividadToAttach);
            }
            normatividad.setSeguimientoNormatividadList(attachedSeguimientoNormatividadList);
            em.persist(normatividad);
            if (idEstado != null) {
                idEstado.getNormatividadList().add(normatividad);
                idEstado = em.merge(idEstado);
            }
            if (idTema != null) {
                idTema.getNormatividadList().add(normatividad);
                idTema = em.merge(idTema);
            }
            if (idClaseNormatividad != null) {
                idClaseNormatividad.getNormatividadList().add(normatividad);
                idClaseNormatividad = em.merge(idClaseNormatividad);
            }
            if (idAreaDerecho != null) {
                idAreaDerecho.getNormatividadList().add(normatividad);
                idAreaDerecho = em.merge(idAreaDerecho);
            }
            for (SeguimientoNormatividad seguimientoNormatividadListSeguimientoNormatividad : normatividad.getSeguimientoNormatividadList()) {
                Normatividad oldIdNormatividadOfSeguimientoNormatividadListSeguimientoNormatividad = seguimientoNormatividadListSeguimientoNormatividad.getIdNormatividad();
                seguimientoNormatividadListSeguimientoNormatividad.setIdNormatividad(normatividad);
                seguimientoNormatividadListSeguimientoNormatividad = em.merge(seguimientoNormatividadListSeguimientoNormatividad);
                if (oldIdNormatividadOfSeguimientoNormatividadListSeguimientoNormatividad != null) {
                    oldIdNormatividadOfSeguimientoNormatividadListSeguimientoNormatividad.getSeguimientoNormatividadList().remove(seguimientoNormatividadListSeguimientoNormatividad);
                    oldIdNormatividadOfSeguimientoNormatividadListSeguimientoNormatividad = em.merge(oldIdNormatividadOfSeguimientoNormatividadListSeguimientoNormatividad);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNormatividad(normatividad.getId()) != null) {
                throw new PreexistingEntityException("Normatividad " + normatividad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Normatividad normatividad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Normatividad persistentNormatividad = em.find(Normatividad.class, normatividad.getId());
            TipoEstadoNormatividad idEstadoOld = persistentNormatividad.getIdEstado();
            TipoEstadoNormatividad idEstadoNew = normatividad.getIdEstado();
            Tema idTemaOld = persistentNormatividad.getIdTema();
            Tema idTemaNew = normatividad.getIdTema();
            Clase idClaseNormatividadOld = persistentNormatividad.getIdClaseNormatividad();
            Clase idClaseNormatividadNew = normatividad.getIdClaseNormatividad();
            Clase idAreaDerechoOld = persistentNormatividad.getIdAreaDerecho();
            Clase idAreaDerechoNew = normatividad.getIdAreaDerecho();
            List<SeguimientoNormatividad> seguimientoNormatividadListOld = persistentNormatividad.getSeguimientoNormatividadList();
            List<SeguimientoNormatividad> seguimientoNormatividadListNew = normatividad.getSeguimientoNormatividadList();
            if (idEstadoNew != null) {
                idEstadoNew = em.getReference(idEstadoNew.getClass(), idEstadoNew.getId());
                normatividad.setIdEstado(idEstadoNew);
            }
            if (idTemaNew != null) {
                idTemaNew = em.getReference(idTemaNew.getClass(), idTemaNew.getId());
                normatividad.setIdTema(idTemaNew);
            }
            if (idClaseNormatividadNew != null) {
                idClaseNormatividadNew = em.getReference(idClaseNormatividadNew.getClass(), idClaseNormatividadNew.getId());
                normatividad.setIdClaseNormatividad(idClaseNormatividadNew);
            }
            if (idAreaDerechoNew != null) {
                idAreaDerechoNew = em.getReference(idAreaDerechoNew.getClass(), idAreaDerechoNew.getId());
                normatividad.setIdAreaDerecho(idAreaDerechoNew);
            }
            List<SeguimientoNormatividad> attachedSeguimientoNormatividadListNew = new ArrayList<SeguimientoNormatividad>();
            for (SeguimientoNormatividad seguimientoNormatividadListNewSeguimientoNormatividadToAttach : seguimientoNormatividadListNew) {
                seguimientoNormatividadListNewSeguimientoNormatividadToAttach = em.getReference(seguimientoNormatividadListNewSeguimientoNormatividadToAttach.getClass(), seguimientoNormatividadListNewSeguimientoNormatividadToAttach.getId());
                attachedSeguimientoNormatividadListNew.add(seguimientoNormatividadListNewSeguimientoNormatividadToAttach);
            }
            seguimientoNormatividadListNew = attachedSeguimientoNormatividadListNew;
            normatividad.setSeguimientoNormatividadList(seguimientoNormatividadListNew);
            normatividad = em.merge(normatividad);
            if (idEstadoOld != null && !idEstadoOld.equals(idEstadoNew)) {
                idEstadoOld.getNormatividadList().remove(normatividad);
                idEstadoOld = em.merge(idEstadoOld);
            }
            if (idEstadoNew != null && !idEstadoNew.equals(idEstadoOld)) {
                idEstadoNew.getNormatividadList().add(normatividad);
                idEstadoNew = em.merge(idEstadoNew);
            }
            if (idTemaOld != null && !idTemaOld.equals(idTemaNew)) {
                idTemaOld.getNormatividadList().remove(normatividad);
                idTemaOld = em.merge(idTemaOld);
            }
            if (idTemaNew != null && !idTemaNew.equals(idTemaOld)) {
                idTemaNew.getNormatividadList().add(normatividad);
                idTemaNew = em.merge(idTemaNew);
            }
            if (idClaseNormatividadOld != null && !idClaseNormatividadOld.equals(idClaseNormatividadNew)) {
                idClaseNormatividadOld.getNormatividadList().remove(normatividad);
                idClaseNormatividadOld = em.merge(idClaseNormatividadOld);
            }
            if (idClaseNormatividadNew != null && !idClaseNormatividadNew.equals(idClaseNormatividadOld)) {
                idClaseNormatividadNew.getNormatividadList().add(normatividad);
                idClaseNormatividadNew = em.merge(idClaseNormatividadNew);
            }
            if (idAreaDerechoOld != null && !idAreaDerechoOld.equals(idAreaDerechoNew)) {
                idAreaDerechoOld.getNormatividadList().remove(normatividad);
                idAreaDerechoOld = em.merge(idAreaDerechoOld);
            }
            if (idAreaDerechoNew != null && !idAreaDerechoNew.equals(idAreaDerechoOld)) {
                idAreaDerechoNew.getNormatividadList().add(normatividad);
                idAreaDerechoNew = em.merge(idAreaDerechoNew);
            }
            for (SeguimientoNormatividad seguimientoNormatividadListOldSeguimientoNormatividad : seguimientoNormatividadListOld) {
                if (!seguimientoNormatividadListNew.contains(seguimientoNormatividadListOldSeguimientoNormatividad)) {
                    seguimientoNormatividadListOldSeguimientoNormatividad.setIdNormatividad(null);
                    seguimientoNormatividadListOldSeguimientoNormatividad = em.merge(seguimientoNormatividadListOldSeguimientoNormatividad);
                }
            }
            for (SeguimientoNormatividad seguimientoNormatividadListNewSeguimientoNormatividad : seguimientoNormatividadListNew) {
                if (!seguimientoNormatividadListOld.contains(seguimientoNormatividadListNewSeguimientoNormatividad)) {
                    Normatividad oldIdNormatividadOfSeguimientoNormatividadListNewSeguimientoNormatividad = seguimientoNormatividadListNewSeguimientoNormatividad.getIdNormatividad();
                    seguimientoNormatividadListNewSeguimientoNormatividad.setIdNormatividad(normatividad);
                    seguimientoNormatividadListNewSeguimientoNormatividad = em.merge(seguimientoNormatividadListNewSeguimientoNormatividad);
                    if (oldIdNormatividadOfSeguimientoNormatividadListNewSeguimientoNormatividad != null && !oldIdNormatividadOfSeguimientoNormatividadListNewSeguimientoNormatividad.equals(normatividad)) {
                        oldIdNormatividadOfSeguimientoNormatividadListNewSeguimientoNormatividad.getSeguimientoNormatividadList().remove(seguimientoNormatividadListNewSeguimientoNormatividad);
                        oldIdNormatividadOfSeguimientoNormatividadListNewSeguimientoNormatividad = em.merge(oldIdNormatividadOfSeguimientoNormatividadListNewSeguimientoNormatividad);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = normatividad.getId();
                if (findNormatividad(id) == null) {
                    throw new NonexistentEntityException("The normatividad with id " + id + " no longer exists.");
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
            Normatividad normatividad;
            try {
                normatividad = em.getReference(Normatividad.class, id);
                normatividad.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The normatividad with id " + id + " no longer exists.", enfe);
            }
            TipoEstadoNormatividad idEstado = normatividad.getIdEstado();
            if (idEstado != null) {
                idEstado.getNormatividadList().remove(normatividad);
                idEstado = em.merge(idEstado);
            }
            Tema idTema = normatividad.getIdTema();
            if (idTema != null) {
                idTema.getNormatividadList().remove(normatividad);
                idTema = em.merge(idTema);
            }
            Clase idClaseNormatividad = normatividad.getIdClaseNormatividad();
            if (idClaseNormatividad != null) {
                idClaseNormatividad.getNormatividadList().remove(normatividad);
                idClaseNormatividad = em.merge(idClaseNormatividad);
            }
            Clase idAreaDerecho = normatividad.getIdAreaDerecho();
            if (idAreaDerecho != null) {
                idAreaDerecho.getNormatividadList().remove(normatividad);
                idAreaDerecho = em.merge(idAreaDerecho);
            }
            List<SeguimientoNormatividad> seguimientoNormatividadList = normatividad.getSeguimientoNormatividadList();
            for (SeguimientoNormatividad seguimientoNormatividadListSeguimientoNormatividad : seguimientoNormatividadList) {
                seguimientoNormatividadListSeguimientoNormatividad.setIdNormatividad(null);
                seguimientoNormatividadListSeguimientoNormatividad = em.merge(seguimientoNormatividadListSeguimientoNormatividad);
            }
            em.remove(normatividad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Normatividad> findNormatividadEntities() {
        return findNormatividadEntities(true, -1, -1);
    }

    public List<Normatividad> findNormatividadEntities(int maxResults, int firstResult) {
        return findNormatividadEntities(false, maxResults, firstResult);
    }

    private List<Normatividad> findNormatividadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Normatividad.class));
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

    public Normatividad findNormatividad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Normatividad.class, id);
        } finally {
            em.close();
        }
    }

    public int getNormatividadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Normatividad> rt = cq.from(Normatividad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
