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
import com.sae.persistence.domain.TipoAnalisisNorma;
import com.sae.persistence.domain.Normatividad;
import com.sae.persistence.domain.AreaAfectadaNormatividad;
import com.sae.persistence.domain.SeguimientoNormatividad;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class SeguimientoNormatividadJpaController implements Serializable {

    public SeguimientoNormatividadJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SeguimientoNormatividad seguimientoNormatividad) throws PreexistingEntityException, Exception {
        if (seguimientoNormatividad.getAreaAfectadaNormatividadList() == null) {
            seguimientoNormatividad.setAreaAfectadaNormatividadList(new ArrayList<AreaAfectadaNormatividad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoEstadoNormatividad idEstado = seguimientoNormatividad.getIdEstado();
            if (idEstado != null) {
                idEstado = em.getReference(idEstado.getClass(), idEstado.getId());
                seguimientoNormatividad.setIdEstado(idEstado);
            }
            TipoAnalisisNorma idTipoAnalisis = seguimientoNormatividad.getIdTipoAnalisis();
            if (idTipoAnalisis != null) {
                idTipoAnalisis = em.getReference(idTipoAnalisis.getClass(), idTipoAnalisis.getId());
                seguimientoNormatividad.setIdTipoAnalisis(idTipoAnalisis);
            }
            Normatividad idNormatividad = seguimientoNormatividad.getIdNormatividad();
            if (idNormatividad != null) {
                idNormatividad = em.getReference(idNormatividad.getClass(), idNormatividad.getId());
                seguimientoNormatividad.setIdNormatividad(idNormatividad);
            }
            List<AreaAfectadaNormatividad> attachedAreaAfectadaNormatividadList = new ArrayList<AreaAfectadaNormatividad>();
            for (AreaAfectadaNormatividad areaAfectadaNormatividadListAreaAfectadaNormatividadToAttach : seguimientoNormatividad.getAreaAfectadaNormatividadList()) {
                areaAfectadaNormatividadListAreaAfectadaNormatividadToAttach = em.getReference(areaAfectadaNormatividadListAreaAfectadaNormatividadToAttach.getClass(), areaAfectadaNormatividadListAreaAfectadaNormatividadToAttach.getId());
                attachedAreaAfectadaNormatividadList.add(areaAfectadaNormatividadListAreaAfectadaNormatividadToAttach);
            }
            seguimientoNormatividad.setAreaAfectadaNormatividadList(attachedAreaAfectadaNormatividadList);
            em.persist(seguimientoNormatividad);
            if (idEstado != null) {
                idEstado.getSeguimientoNormatividadList().add(seguimientoNormatividad);
                idEstado = em.merge(idEstado);
            }
            if (idTipoAnalisis != null) {
                idTipoAnalisis.getSeguimientoNormatividadList().add(seguimientoNormatividad);
                idTipoAnalisis = em.merge(idTipoAnalisis);
            }
            if (idNormatividad != null) {
                idNormatividad.getSeguimientoNormatividadList().add(seguimientoNormatividad);
                idNormatividad = em.merge(idNormatividad);
            }
            for (AreaAfectadaNormatividad areaAfectadaNormatividadListAreaAfectadaNormatividad : seguimientoNormatividad.getAreaAfectadaNormatividadList()) {
                SeguimientoNormatividad oldIdSeguimientoOfAreaAfectadaNormatividadListAreaAfectadaNormatividad = areaAfectadaNormatividadListAreaAfectadaNormatividad.getIdSeguimiento();
                areaAfectadaNormatividadListAreaAfectadaNormatividad.setIdSeguimiento(seguimientoNormatividad);
                areaAfectadaNormatividadListAreaAfectadaNormatividad = em.merge(areaAfectadaNormatividadListAreaAfectadaNormatividad);
                if (oldIdSeguimientoOfAreaAfectadaNormatividadListAreaAfectadaNormatividad != null) {
                    oldIdSeguimientoOfAreaAfectadaNormatividadListAreaAfectadaNormatividad.getAreaAfectadaNormatividadList().remove(areaAfectadaNormatividadListAreaAfectadaNormatividad);
                    oldIdSeguimientoOfAreaAfectadaNormatividadListAreaAfectadaNormatividad = em.merge(oldIdSeguimientoOfAreaAfectadaNormatividadListAreaAfectadaNormatividad);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSeguimientoNormatividad(seguimientoNormatividad.getId()) != null) {
                throw new PreexistingEntityException("SeguimientoNormatividad " + seguimientoNormatividad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SeguimientoNormatividad seguimientoNormatividad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SeguimientoNormatividad persistentSeguimientoNormatividad = em.find(SeguimientoNormatividad.class, seguimientoNormatividad.getId());
            TipoEstadoNormatividad idEstadoOld = persistentSeguimientoNormatividad.getIdEstado();
            TipoEstadoNormatividad idEstadoNew = seguimientoNormatividad.getIdEstado();
            TipoAnalisisNorma idTipoAnalisisOld = persistentSeguimientoNormatividad.getIdTipoAnalisis();
            TipoAnalisisNorma idTipoAnalisisNew = seguimientoNormatividad.getIdTipoAnalisis();
            Normatividad idNormatividadOld = persistentSeguimientoNormatividad.getIdNormatividad();
            Normatividad idNormatividadNew = seguimientoNormatividad.getIdNormatividad();
            List<AreaAfectadaNormatividad> areaAfectadaNormatividadListOld = persistentSeguimientoNormatividad.getAreaAfectadaNormatividadList();
            List<AreaAfectadaNormatividad> areaAfectadaNormatividadListNew = seguimientoNormatividad.getAreaAfectadaNormatividadList();
            if (idEstadoNew != null) {
                idEstadoNew = em.getReference(idEstadoNew.getClass(), idEstadoNew.getId());
                seguimientoNormatividad.setIdEstado(idEstadoNew);
            }
            if (idTipoAnalisisNew != null) {
                idTipoAnalisisNew = em.getReference(idTipoAnalisisNew.getClass(), idTipoAnalisisNew.getId());
                seguimientoNormatividad.setIdTipoAnalisis(idTipoAnalisisNew);
            }
            if (idNormatividadNew != null) {
                idNormatividadNew = em.getReference(idNormatividadNew.getClass(), idNormatividadNew.getId());
                seguimientoNormatividad.setIdNormatividad(idNormatividadNew);
            }
            List<AreaAfectadaNormatividad> attachedAreaAfectadaNormatividadListNew = new ArrayList<AreaAfectadaNormatividad>();
            for (AreaAfectadaNormatividad areaAfectadaNormatividadListNewAreaAfectadaNormatividadToAttach : areaAfectadaNormatividadListNew) {
                areaAfectadaNormatividadListNewAreaAfectadaNormatividadToAttach = em.getReference(areaAfectadaNormatividadListNewAreaAfectadaNormatividadToAttach.getClass(), areaAfectadaNormatividadListNewAreaAfectadaNormatividadToAttach.getId());
                attachedAreaAfectadaNormatividadListNew.add(areaAfectadaNormatividadListNewAreaAfectadaNormatividadToAttach);
            }
            areaAfectadaNormatividadListNew = attachedAreaAfectadaNormatividadListNew;
            seguimientoNormatividad.setAreaAfectadaNormatividadList(areaAfectadaNormatividadListNew);
            seguimientoNormatividad = em.merge(seguimientoNormatividad);
            if (idEstadoOld != null && !idEstadoOld.equals(idEstadoNew)) {
                idEstadoOld.getSeguimientoNormatividadList().remove(seguimientoNormatividad);
                idEstadoOld = em.merge(idEstadoOld);
            }
            if (idEstadoNew != null && !idEstadoNew.equals(idEstadoOld)) {
                idEstadoNew.getSeguimientoNormatividadList().add(seguimientoNormatividad);
                idEstadoNew = em.merge(idEstadoNew);
            }
            if (idTipoAnalisisOld != null && !idTipoAnalisisOld.equals(idTipoAnalisisNew)) {
                idTipoAnalisisOld.getSeguimientoNormatividadList().remove(seguimientoNormatividad);
                idTipoAnalisisOld = em.merge(idTipoAnalisisOld);
            }
            if (idTipoAnalisisNew != null && !idTipoAnalisisNew.equals(idTipoAnalisisOld)) {
                idTipoAnalisisNew.getSeguimientoNormatividadList().add(seguimientoNormatividad);
                idTipoAnalisisNew = em.merge(idTipoAnalisisNew);
            }
            if (idNormatividadOld != null && !idNormatividadOld.equals(idNormatividadNew)) {
                idNormatividadOld.getSeguimientoNormatividadList().remove(seguimientoNormatividad);
                idNormatividadOld = em.merge(idNormatividadOld);
            }
            if (idNormatividadNew != null && !idNormatividadNew.equals(idNormatividadOld)) {
                idNormatividadNew.getSeguimientoNormatividadList().add(seguimientoNormatividad);
                idNormatividadNew = em.merge(idNormatividadNew);
            }
            for (AreaAfectadaNormatividad areaAfectadaNormatividadListOldAreaAfectadaNormatividad : areaAfectadaNormatividadListOld) {
                if (!areaAfectadaNormatividadListNew.contains(areaAfectadaNormatividadListOldAreaAfectadaNormatividad)) {
                    areaAfectadaNormatividadListOldAreaAfectadaNormatividad.setIdSeguimiento(null);
                    areaAfectadaNormatividadListOldAreaAfectadaNormatividad = em.merge(areaAfectadaNormatividadListOldAreaAfectadaNormatividad);
                }
            }
            for (AreaAfectadaNormatividad areaAfectadaNormatividadListNewAreaAfectadaNormatividad : areaAfectadaNormatividadListNew) {
                if (!areaAfectadaNormatividadListOld.contains(areaAfectadaNormatividadListNewAreaAfectadaNormatividad)) {
                    SeguimientoNormatividad oldIdSeguimientoOfAreaAfectadaNormatividadListNewAreaAfectadaNormatividad = areaAfectadaNormatividadListNewAreaAfectadaNormatividad.getIdSeguimiento();
                    areaAfectadaNormatividadListNewAreaAfectadaNormatividad.setIdSeguimiento(seguimientoNormatividad);
                    areaAfectadaNormatividadListNewAreaAfectadaNormatividad = em.merge(areaAfectadaNormatividadListNewAreaAfectadaNormatividad);
                    if (oldIdSeguimientoOfAreaAfectadaNormatividadListNewAreaAfectadaNormatividad != null && !oldIdSeguimientoOfAreaAfectadaNormatividadListNewAreaAfectadaNormatividad.equals(seguimientoNormatividad)) {
                        oldIdSeguimientoOfAreaAfectadaNormatividadListNewAreaAfectadaNormatividad.getAreaAfectadaNormatividadList().remove(areaAfectadaNormatividadListNewAreaAfectadaNormatividad);
                        oldIdSeguimientoOfAreaAfectadaNormatividadListNewAreaAfectadaNormatividad = em.merge(oldIdSeguimientoOfAreaAfectadaNormatividadListNewAreaAfectadaNormatividad);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = seguimientoNormatividad.getId();
                if (findSeguimientoNormatividad(id) == null) {
                    throw new NonexistentEntityException("The seguimientoNormatividad with id " + id + " no longer exists.");
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
            SeguimientoNormatividad seguimientoNormatividad;
            try {
                seguimientoNormatividad = em.getReference(SeguimientoNormatividad.class, id);
                seguimientoNormatividad.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The seguimientoNormatividad with id " + id + " no longer exists.", enfe);
            }
            TipoEstadoNormatividad idEstado = seguimientoNormatividad.getIdEstado();
            if (idEstado != null) {
                idEstado.getSeguimientoNormatividadList().remove(seguimientoNormatividad);
                idEstado = em.merge(idEstado);
            }
            TipoAnalisisNorma idTipoAnalisis = seguimientoNormatividad.getIdTipoAnalisis();
            if (idTipoAnalisis != null) {
                idTipoAnalisis.getSeguimientoNormatividadList().remove(seguimientoNormatividad);
                idTipoAnalisis = em.merge(idTipoAnalisis);
            }
            Normatividad idNormatividad = seguimientoNormatividad.getIdNormatividad();
            if (idNormatividad != null) {
                idNormatividad.getSeguimientoNormatividadList().remove(seguimientoNormatividad);
                idNormatividad = em.merge(idNormatividad);
            }
            List<AreaAfectadaNormatividad> areaAfectadaNormatividadList = seguimientoNormatividad.getAreaAfectadaNormatividadList();
            for (AreaAfectadaNormatividad areaAfectadaNormatividadListAreaAfectadaNormatividad : areaAfectadaNormatividadList) {
                areaAfectadaNormatividadListAreaAfectadaNormatividad.setIdSeguimiento(null);
                areaAfectadaNormatividadListAreaAfectadaNormatividad = em.merge(areaAfectadaNormatividadListAreaAfectadaNormatividad);
            }
            em.remove(seguimientoNormatividad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SeguimientoNormatividad> findSeguimientoNormatividadEntities() {
        return findSeguimientoNormatividadEntities(true, -1, -1);
    }

    public List<SeguimientoNormatividad> findSeguimientoNormatividadEntities(int maxResults, int firstResult) {
        return findSeguimientoNormatividadEntities(false, maxResults, firstResult);
    }

    private List<SeguimientoNormatividad> findSeguimientoNormatividadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SeguimientoNormatividad.class));
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

    public SeguimientoNormatividad findSeguimientoNormatividad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SeguimientoNormatividad.class, id);
        } finally {
            em.close();
        }
    }

    public int getSeguimientoNormatividadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SeguimientoNormatividad> rt = cq.from(SeguimientoNormatividad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
