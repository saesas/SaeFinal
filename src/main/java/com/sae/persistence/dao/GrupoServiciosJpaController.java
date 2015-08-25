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
import com.sae.persistence.domain.Puc;
import com.sae.persistence.domain.ConceptoRetencion;
import com.sae.persistence.domain.GrupoServicioConceptoRetencion;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.GrupoAsociadoServicio;
import com.sae.persistence.domain.GrupoServicios;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class GrupoServiciosJpaController implements Serializable {

    public GrupoServiciosJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GrupoServicios grupoServicios) throws PreexistingEntityException, Exception {
        if (grupoServicios.getGrupoServicioConceptoRetencionList() == null) {
            grupoServicios.setGrupoServicioConceptoRetencionList(new ArrayList<GrupoServicioConceptoRetencion>());
        }
        if (grupoServicios.getGrupoAsociadoServicioList() == null) {
            grupoServicios.setGrupoAsociadoServicioList(new ArrayList<GrupoAsociadoServicio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Puc idPucGastosAdministrativos = grupoServicios.getIdPucGastosAdministrativos();
            if (idPucGastosAdministrativos != null) {
                idPucGastosAdministrativos = em.getReference(idPucGastosAdministrativos.getClass(), idPucGastosAdministrativos.getId());
                grupoServicios.setIdPucGastosAdministrativos(idPucGastosAdministrativos);
            }
            Puc idPucCostosProduccion = grupoServicios.getIdPucCostosProduccion();
            if (idPucCostosProduccion != null) {
                idPucCostosProduccion = em.getReference(idPucCostosProduccion.getClass(), idPucCostosProduccion.getId());
                grupoServicios.setIdPucCostosProduccion(idPucCostosProduccion);
            }
            ConceptoRetencion idConceptoRetecree = grupoServicios.getIdConceptoRetecree();
            if (idConceptoRetecree != null) {
                idConceptoRetecree = em.getReference(idConceptoRetecree.getClass(), idConceptoRetecree.getId());
                grupoServicios.setIdConceptoRetecree(idConceptoRetecree);
            }
            ConceptoRetencion idConceptoRetefuente = grupoServicios.getIdConceptoRetefuente();
            if (idConceptoRetefuente != null) {
                idConceptoRetefuente = em.getReference(idConceptoRetefuente.getClass(), idConceptoRetefuente.getId());
                grupoServicios.setIdConceptoRetefuente(idConceptoRetefuente);
            }
            List<GrupoServicioConceptoRetencion> attachedGrupoServicioConceptoRetencionList = new ArrayList<GrupoServicioConceptoRetencion>();
            for (GrupoServicioConceptoRetencion grupoServicioConceptoRetencionListGrupoServicioConceptoRetencionToAttach : grupoServicios.getGrupoServicioConceptoRetencionList()) {
                grupoServicioConceptoRetencionListGrupoServicioConceptoRetencionToAttach = em.getReference(grupoServicioConceptoRetencionListGrupoServicioConceptoRetencionToAttach.getClass(), grupoServicioConceptoRetencionListGrupoServicioConceptoRetencionToAttach.getId());
                attachedGrupoServicioConceptoRetencionList.add(grupoServicioConceptoRetencionListGrupoServicioConceptoRetencionToAttach);
            }
            grupoServicios.setGrupoServicioConceptoRetencionList(attachedGrupoServicioConceptoRetencionList);
            List<GrupoAsociadoServicio> attachedGrupoAsociadoServicioList = new ArrayList<GrupoAsociadoServicio>();
            for (GrupoAsociadoServicio grupoAsociadoServicioListGrupoAsociadoServicioToAttach : grupoServicios.getGrupoAsociadoServicioList()) {
                grupoAsociadoServicioListGrupoAsociadoServicioToAttach = em.getReference(grupoAsociadoServicioListGrupoAsociadoServicioToAttach.getClass(), grupoAsociadoServicioListGrupoAsociadoServicioToAttach.getId());
                attachedGrupoAsociadoServicioList.add(grupoAsociadoServicioListGrupoAsociadoServicioToAttach);
            }
            grupoServicios.setGrupoAsociadoServicioList(attachedGrupoAsociadoServicioList);
            em.persist(grupoServicios);
            if (idPucGastosAdministrativos != null) {
                idPucGastosAdministrativos.getGrupoServiciosList().add(grupoServicios);
                idPucGastosAdministrativos = em.merge(idPucGastosAdministrativos);
            }
            if (idPucCostosProduccion != null) {
                idPucCostosProduccion.getGrupoServiciosList().add(grupoServicios);
                idPucCostosProduccion = em.merge(idPucCostosProduccion);
            }
            if (idConceptoRetecree != null) {
                idConceptoRetecree.getGrupoServiciosList().add(grupoServicios);
                idConceptoRetecree = em.merge(idConceptoRetecree);
            }
            if (idConceptoRetefuente != null) {
                idConceptoRetefuente.getGrupoServiciosList().add(grupoServicios);
                idConceptoRetefuente = em.merge(idConceptoRetefuente);
            }
            for (GrupoServicioConceptoRetencion grupoServicioConceptoRetencionListGrupoServicioConceptoRetencion : grupoServicios.getGrupoServicioConceptoRetencionList()) {
                GrupoServicios oldGrupoOfGrupoServicioConceptoRetencionListGrupoServicioConceptoRetencion = grupoServicioConceptoRetencionListGrupoServicioConceptoRetencion.getGrupo();
                grupoServicioConceptoRetencionListGrupoServicioConceptoRetencion.setGrupo(grupoServicios);
                grupoServicioConceptoRetencionListGrupoServicioConceptoRetencion = em.merge(grupoServicioConceptoRetencionListGrupoServicioConceptoRetencion);
                if (oldGrupoOfGrupoServicioConceptoRetencionListGrupoServicioConceptoRetencion != null) {
                    oldGrupoOfGrupoServicioConceptoRetencionListGrupoServicioConceptoRetencion.getGrupoServicioConceptoRetencionList().remove(grupoServicioConceptoRetencionListGrupoServicioConceptoRetencion);
                    oldGrupoOfGrupoServicioConceptoRetencionListGrupoServicioConceptoRetencion = em.merge(oldGrupoOfGrupoServicioConceptoRetencionListGrupoServicioConceptoRetencion);
                }
            }
            for (GrupoAsociadoServicio grupoAsociadoServicioListGrupoAsociadoServicio : grupoServicios.getGrupoAsociadoServicioList()) {
                GrupoServicios oldIdGrupoOfGrupoAsociadoServicioListGrupoAsociadoServicio = grupoAsociadoServicioListGrupoAsociadoServicio.getIdGrupo();
                grupoAsociadoServicioListGrupoAsociadoServicio.setIdGrupo(grupoServicios);
                grupoAsociadoServicioListGrupoAsociadoServicio = em.merge(grupoAsociadoServicioListGrupoAsociadoServicio);
                if (oldIdGrupoOfGrupoAsociadoServicioListGrupoAsociadoServicio != null) {
                    oldIdGrupoOfGrupoAsociadoServicioListGrupoAsociadoServicio.getGrupoAsociadoServicioList().remove(grupoAsociadoServicioListGrupoAsociadoServicio);
                    oldIdGrupoOfGrupoAsociadoServicioListGrupoAsociadoServicio = em.merge(oldIdGrupoOfGrupoAsociadoServicioListGrupoAsociadoServicio);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGrupoServicios(grupoServicios.getId()) != null) {
                throw new PreexistingEntityException("GrupoServicios " + grupoServicios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GrupoServicios grupoServicios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GrupoServicios persistentGrupoServicios = em.find(GrupoServicios.class, grupoServicios.getId());
            Puc idPucGastosAdministrativosOld = persistentGrupoServicios.getIdPucGastosAdministrativos();
            Puc idPucGastosAdministrativosNew = grupoServicios.getIdPucGastosAdministrativos();
            Puc idPucCostosProduccionOld = persistentGrupoServicios.getIdPucCostosProduccion();
            Puc idPucCostosProduccionNew = grupoServicios.getIdPucCostosProduccion();
            ConceptoRetencion idConceptoRetecreeOld = persistentGrupoServicios.getIdConceptoRetecree();
            ConceptoRetencion idConceptoRetecreeNew = grupoServicios.getIdConceptoRetecree();
            ConceptoRetencion idConceptoRetefuenteOld = persistentGrupoServicios.getIdConceptoRetefuente();
            ConceptoRetencion idConceptoRetefuenteNew = grupoServicios.getIdConceptoRetefuente();
            List<GrupoServicioConceptoRetencion> grupoServicioConceptoRetencionListOld = persistentGrupoServicios.getGrupoServicioConceptoRetencionList();
            List<GrupoServicioConceptoRetencion> grupoServicioConceptoRetencionListNew = grupoServicios.getGrupoServicioConceptoRetencionList();
            List<GrupoAsociadoServicio> grupoAsociadoServicioListOld = persistentGrupoServicios.getGrupoAsociadoServicioList();
            List<GrupoAsociadoServicio> grupoAsociadoServicioListNew = grupoServicios.getGrupoAsociadoServicioList();
            if (idPucGastosAdministrativosNew != null) {
                idPucGastosAdministrativosNew = em.getReference(idPucGastosAdministrativosNew.getClass(), idPucGastosAdministrativosNew.getId());
                grupoServicios.setIdPucGastosAdministrativos(idPucGastosAdministrativosNew);
            }
            if (idPucCostosProduccionNew != null) {
                idPucCostosProduccionNew = em.getReference(idPucCostosProduccionNew.getClass(), idPucCostosProduccionNew.getId());
                grupoServicios.setIdPucCostosProduccion(idPucCostosProduccionNew);
            }
            if (idConceptoRetecreeNew != null) {
                idConceptoRetecreeNew = em.getReference(idConceptoRetecreeNew.getClass(), idConceptoRetecreeNew.getId());
                grupoServicios.setIdConceptoRetecree(idConceptoRetecreeNew);
            }
            if (idConceptoRetefuenteNew != null) {
                idConceptoRetefuenteNew = em.getReference(idConceptoRetefuenteNew.getClass(), idConceptoRetefuenteNew.getId());
                grupoServicios.setIdConceptoRetefuente(idConceptoRetefuenteNew);
            }
            List<GrupoServicioConceptoRetencion> attachedGrupoServicioConceptoRetencionListNew = new ArrayList<GrupoServicioConceptoRetencion>();
            for (GrupoServicioConceptoRetencion grupoServicioConceptoRetencionListNewGrupoServicioConceptoRetencionToAttach : grupoServicioConceptoRetencionListNew) {
                grupoServicioConceptoRetencionListNewGrupoServicioConceptoRetencionToAttach = em.getReference(grupoServicioConceptoRetencionListNewGrupoServicioConceptoRetencionToAttach.getClass(), grupoServicioConceptoRetencionListNewGrupoServicioConceptoRetencionToAttach.getId());
                attachedGrupoServicioConceptoRetencionListNew.add(grupoServicioConceptoRetencionListNewGrupoServicioConceptoRetencionToAttach);
            }
            grupoServicioConceptoRetencionListNew = attachedGrupoServicioConceptoRetencionListNew;
            grupoServicios.setGrupoServicioConceptoRetencionList(grupoServicioConceptoRetencionListNew);
            List<GrupoAsociadoServicio> attachedGrupoAsociadoServicioListNew = new ArrayList<GrupoAsociadoServicio>();
            for (GrupoAsociadoServicio grupoAsociadoServicioListNewGrupoAsociadoServicioToAttach : grupoAsociadoServicioListNew) {
                grupoAsociadoServicioListNewGrupoAsociadoServicioToAttach = em.getReference(grupoAsociadoServicioListNewGrupoAsociadoServicioToAttach.getClass(), grupoAsociadoServicioListNewGrupoAsociadoServicioToAttach.getId());
                attachedGrupoAsociadoServicioListNew.add(grupoAsociadoServicioListNewGrupoAsociadoServicioToAttach);
            }
            grupoAsociadoServicioListNew = attachedGrupoAsociadoServicioListNew;
            grupoServicios.setGrupoAsociadoServicioList(grupoAsociadoServicioListNew);
            grupoServicios = em.merge(grupoServicios);
            if (idPucGastosAdministrativosOld != null && !idPucGastosAdministrativosOld.equals(idPucGastosAdministrativosNew)) {
                idPucGastosAdministrativosOld.getGrupoServiciosList().remove(grupoServicios);
                idPucGastosAdministrativosOld = em.merge(idPucGastosAdministrativosOld);
            }
            if (idPucGastosAdministrativosNew != null && !idPucGastosAdministrativosNew.equals(idPucGastosAdministrativosOld)) {
                idPucGastosAdministrativosNew.getGrupoServiciosList().add(grupoServicios);
                idPucGastosAdministrativosNew = em.merge(idPucGastosAdministrativosNew);
            }
            if (idPucCostosProduccionOld != null && !idPucCostosProduccionOld.equals(idPucCostosProduccionNew)) {
                idPucCostosProduccionOld.getGrupoServiciosList().remove(grupoServicios);
                idPucCostosProduccionOld = em.merge(idPucCostosProduccionOld);
            }
            if (idPucCostosProduccionNew != null && !idPucCostosProduccionNew.equals(idPucCostosProduccionOld)) {
                idPucCostosProduccionNew.getGrupoServiciosList().add(grupoServicios);
                idPucCostosProduccionNew = em.merge(idPucCostosProduccionNew);
            }
            if (idConceptoRetecreeOld != null && !idConceptoRetecreeOld.equals(idConceptoRetecreeNew)) {
                idConceptoRetecreeOld.getGrupoServiciosList().remove(grupoServicios);
                idConceptoRetecreeOld = em.merge(idConceptoRetecreeOld);
            }
            if (idConceptoRetecreeNew != null && !idConceptoRetecreeNew.equals(idConceptoRetecreeOld)) {
                idConceptoRetecreeNew.getGrupoServiciosList().add(grupoServicios);
                idConceptoRetecreeNew = em.merge(idConceptoRetecreeNew);
            }
            if (idConceptoRetefuenteOld != null && !idConceptoRetefuenteOld.equals(idConceptoRetefuenteNew)) {
                idConceptoRetefuenteOld.getGrupoServiciosList().remove(grupoServicios);
                idConceptoRetefuenteOld = em.merge(idConceptoRetefuenteOld);
            }
            if (idConceptoRetefuenteNew != null && !idConceptoRetefuenteNew.equals(idConceptoRetefuenteOld)) {
                idConceptoRetefuenteNew.getGrupoServiciosList().add(grupoServicios);
                idConceptoRetefuenteNew = em.merge(idConceptoRetefuenteNew);
            }
            for (GrupoServicioConceptoRetencion grupoServicioConceptoRetencionListOldGrupoServicioConceptoRetencion : grupoServicioConceptoRetencionListOld) {
                if (!grupoServicioConceptoRetencionListNew.contains(grupoServicioConceptoRetencionListOldGrupoServicioConceptoRetencion)) {
                    grupoServicioConceptoRetencionListOldGrupoServicioConceptoRetencion.setGrupo(null);
                    grupoServicioConceptoRetencionListOldGrupoServicioConceptoRetencion = em.merge(grupoServicioConceptoRetencionListOldGrupoServicioConceptoRetencion);
                }
            }
            for (GrupoServicioConceptoRetencion grupoServicioConceptoRetencionListNewGrupoServicioConceptoRetencion : grupoServicioConceptoRetencionListNew) {
                if (!grupoServicioConceptoRetencionListOld.contains(grupoServicioConceptoRetencionListNewGrupoServicioConceptoRetencion)) {
                    GrupoServicios oldGrupoOfGrupoServicioConceptoRetencionListNewGrupoServicioConceptoRetencion = grupoServicioConceptoRetencionListNewGrupoServicioConceptoRetencion.getGrupo();
                    grupoServicioConceptoRetencionListNewGrupoServicioConceptoRetencion.setGrupo(grupoServicios);
                    grupoServicioConceptoRetencionListNewGrupoServicioConceptoRetencion = em.merge(grupoServicioConceptoRetencionListNewGrupoServicioConceptoRetencion);
                    if (oldGrupoOfGrupoServicioConceptoRetencionListNewGrupoServicioConceptoRetencion != null && !oldGrupoOfGrupoServicioConceptoRetencionListNewGrupoServicioConceptoRetencion.equals(grupoServicios)) {
                        oldGrupoOfGrupoServicioConceptoRetencionListNewGrupoServicioConceptoRetencion.getGrupoServicioConceptoRetencionList().remove(grupoServicioConceptoRetencionListNewGrupoServicioConceptoRetencion);
                        oldGrupoOfGrupoServicioConceptoRetencionListNewGrupoServicioConceptoRetencion = em.merge(oldGrupoOfGrupoServicioConceptoRetencionListNewGrupoServicioConceptoRetencion);
                    }
                }
            }
            for (GrupoAsociadoServicio grupoAsociadoServicioListOldGrupoAsociadoServicio : grupoAsociadoServicioListOld) {
                if (!grupoAsociadoServicioListNew.contains(grupoAsociadoServicioListOldGrupoAsociadoServicio)) {
                    grupoAsociadoServicioListOldGrupoAsociadoServicio.setIdGrupo(null);
                    grupoAsociadoServicioListOldGrupoAsociadoServicio = em.merge(grupoAsociadoServicioListOldGrupoAsociadoServicio);
                }
            }
            for (GrupoAsociadoServicio grupoAsociadoServicioListNewGrupoAsociadoServicio : grupoAsociadoServicioListNew) {
                if (!grupoAsociadoServicioListOld.contains(grupoAsociadoServicioListNewGrupoAsociadoServicio)) {
                    GrupoServicios oldIdGrupoOfGrupoAsociadoServicioListNewGrupoAsociadoServicio = grupoAsociadoServicioListNewGrupoAsociadoServicio.getIdGrupo();
                    grupoAsociadoServicioListNewGrupoAsociadoServicio.setIdGrupo(grupoServicios);
                    grupoAsociadoServicioListNewGrupoAsociadoServicio = em.merge(grupoAsociadoServicioListNewGrupoAsociadoServicio);
                    if (oldIdGrupoOfGrupoAsociadoServicioListNewGrupoAsociadoServicio != null && !oldIdGrupoOfGrupoAsociadoServicioListNewGrupoAsociadoServicio.equals(grupoServicios)) {
                        oldIdGrupoOfGrupoAsociadoServicioListNewGrupoAsociadoServicio.getGrupoAsociadoServicioList().remove(grupoAsociadoServicioListNewGrupoAsociadoServicio);
                        oldIdGrupoOfGrupoAsociadoServicioListNewGrupoAsociadoServicio = em.merge(oldIdGrupoOfGrupoAsociadoServicioListNewGrupoAsociadoServicio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = grupoServicios.getId();
                if (findGrupoServicios(id) == null) {
                    throw new NonexistentEntityException("The grupoServicios with id " + id + " no longer exists.");
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
            GrupoServicios grupoServicios;
            try {
                grupoServicios = em.getReference(GrupoServicios.class, id);
                grupoServicios.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupoServicios with id " + id + " no longer exists.", enfe);
            }
            Puc idPucGastosAdministrativos = grupoServicios.getIdPucGastosAdministrativos();
            if (idPucGastosAdministrativos != null) {
                idPucGastosAdministrativos.getGrupoServiciosList().remove(grupoServicios);
                idPucGastosAdministrativos = em.merge(idPucGastosAdministrativos);
            }
            Puc idPucCostosProduccion = grupoServicios.getIdPucCostosProduccion();
            if (idPucCostosProduccion != null) {
                idPucCostosProduccion.getGrupoServiciosList().remove(grupoServicios);
                idPucCostosProduccion = em.merge(idPucCostosProduccion);
            }
            ConceptoRetencion idConceptoRetecree = grupoServicios.getIdConceptoRetecree();
            if (idConceptoRetecree != null) {
                idConceptoRetecree.getGrupoServiciosList().remove(grupoServicios);
                idConceptoRetecree = em.merge(idConceptoRetecree);
            }
            ConceptoRetencion idConceptoRetefuente = grupoServicios.getIdConceptoRetefuente();
            if (idConceptoRetefuente != null) {
                idConceptoRetefuente.getGrupoServiciosList().remove(grupoServicios);
                idConceptoRetefuente = em.merge(idConceptoRetefuente);
            }
            List<GrupoServicioConceptoRetencion> grupoServicioConceptoRetencionList = grupoServicios.getGrupoServicioConceptoRetencionList();
            for (GrupoServicioConceptoRetencion grupoServicioConceptoRetencionListGrupoServicioConceptoRetencion : grupoServicioConceptoRetencionList) {
                grupoServicioConceptoRetencionListGrupoServicioConceptoRetencion.setGrupo(null);
                grupoServicioConceptoRetencionListGrupoServicioConceptoRetencion = em.merge(grupoServicioConceptoRetencionListGrupoServicioConceptoRetencion);
            }
            List<GrupoAsociadoServicio> grupoAsociadoServicioList = grupoServicios.getGrupoAsociadoServicioList();
            for (GrupoAsociadoServicio grupoAsociadoServicioListGrupoAsociadoServicio : grupoAsociadoServicioList) {
                grupoAsociadoServicioListGrupoAsociadoServicio.setIdGrupo(null);
                grupoAsociadoServicioListGrupoAsociadoServicio = em.merge(grupoAsociadoServicioListGrupoAsociadoServicio);
            }
            em.remove(grupoServicios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GrupoServicios> findGrupoServiciosEntities() {
        return findGrupoServiciosEntities(true, -1, -1);
    }

    public List<GrupoServicios> findGrupoServiciosEntities(int maxResults, int firstResult) {
        return findGrupoServiciosEntities(false, maxResults, firstResult);
    }

    private List<GrupoServicios> findGrupoServiciosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GrupoServicios.class));
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

    public GrupoServicios findGrupoServicios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GrupoServicios.class, id);
        } finally {
            em.close();
        }
    }

    public int getGrupoServiciosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GrupoServicios> rt = cq.from(GrupoServicios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
