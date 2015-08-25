/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.RazonSocial;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.TipoOrganizacion;
import com.sae.persistence.domain.SucursalRazonSocialTercero;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.TerceroRazonSocial;
import com.sae.persistence.domain.RepresentateLegalRazonSocial;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class RazonSocialJpaController implements Serializable {

    public RazonSocialJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RazonSocial razonSocial) throws PreexistingEntityException, Exception {
        if (razonSocial.getSucursalRazonSocialTerceroList() == null) {
            razonSocial.setSucursalRazonSocialTerceroList(new ArrayList<SucursalRazonSocialTercero>());
        }
        if (razonSocial.getTerceroRazonSocialList() == null) {
            razonSocial.setTerceroRazonSocialList(new ArrayList<TerceroRazonSocial>());
        }
        if (razonSocial.getRepresentateLegalRazonSocialList() == null) {
            razonSocial.setRepresentateLegalRazonSocialList(new ArrayList<RepresentateLegalRazonSocial>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoOrganizacion tipoOrganizacion = razonSocial.getTipoOrganizacion();
            if (tipoOrganizacion != null) {
                tipoOrganizacion = em.getReference(tipoOrganizacion.getClass(), tipoOrganizacion.getId());
                razonSocial.setTipoOrganizacion(tipoOrganizacion);
            }
            List<SucursalRazonSocialTercero> attachedSucursalRazonSocialTerceroList = new ArrayList<SucursalRazonSocialTercero>();
            for (SucursalRazonSocialTercero sucursalRazonSocialTerceroListSucursalRazonSocialTerceroToAttach : razonSocial.getSucursalRazonSocialTerceroList()) {
                sucursalRazonSocialTerceroListSucursalRazonSocialTerceroToAttach = em.getReference(sucursalRazonSocialTerceroListSucursalRazonSocialTerceroToAttach.getClass(), sucursalRazonSocialTerceroListSucursalRazonSocialTerceroToAttach.getId());
                attachedSucursalRazonSocialTerceroList.add(sucursalRazonSocialTerceroListSucursalRazonSocialTerceroToAttach);
            }
            razonSocial.setSucursalRazonSocialTerceroList(attachedSucursalRazonSocialTerceroList);
            List<TerceroRazonSocial> attachedTerceroRazonSocialList = new ArrayList<TerceroRazonSocial>();
            for (TerceroRazonSocial terceroRazonSocialListTerceroRazonSocialToAttach : razonSocial.getTerceroRazonSocialList()) {
                terceroRazonSocialListTerceroRazonSocialToAttach = em.getReference(terceroRazonSocialListTerceroRazonSocialToAttach.getClass(), terceroRazonSocialListTerceroRazonSocialToAttach.getId());
                attachedTerceroRazonSocialList.add(terceroRazonSocialListTerceroRazonSocialToAttach);
            }
            razonSocial.setTerceroRazonSocialList(attachedTerceroRazonSocialList);
            List<RepresentateLegalRazonSocial> attachedRepresentateLegalRazonSocialList = new ArrayList<RepresentateLegalRazonSocial>();
            for (RepresentateLegalRazonSocial representateLegalRazonSocialListRepresentateLegalRazonSocialToAttach : razonSocial.getRepresentateLegalRazonSocialList()) {
                representateLegalRazonSocialListRepresentateLegalRazonSocialToAttach = em.getReference(representateLegalRazonSocialListRepresentateLegalRazonSocialToAttach.getClass(), representateLegalRazonSocialListRepresentateLegalRazonSocialToAttach.getId());
                attachedRepresentateLegalRazonSocialList.add(representateLegalRazonSocialListRepresentateLegalRazonSocialToAttach);
            }
            razonSocial.setRepresentateLegalRazonSocialList(attachedRepresentateLegalRazonSocialList);
            em.persist(razonSocial);
            if (tipoOrganizacion != null) {
                tipoOrganizacion.getRazonSocialList().add(razonSocial);
                tipoOrganizacion = em.merge(tipoOrganizacion);
            }
            for (SucursalRazonSocialTercero sucursalRazonSocialTerceroListSucursalRazonSocialTercero : razonSocial.getSucursalRazonSocialTerceroList()) {
                RazonSocial oldIdRazonSocialOfSucursalRazonSocialTerceroListSucursalRazonSocialTercero = sucursalRazonSocialTerceroListSucursalRazonSocialTercero.getIdRazonSocial();
                sucursalRazonSocialTerceroListSucursalRazonSocialTercero.setIdRazonSocial(razonSocial);
                sucursalRazonSocialTerceroListSucursalRazonSocialTercero = em.merge(sucursalRazonSocialTerceroListSucursalRazonSocialTercero);
                if (oldIdRazonSocialOfSucursalRazonSocialTerceroListSucursalRazonSocialTercero != null) {
                    oldIdRazonSocialOfSucursalRazonSocialTerceroListSucursalRazonSocialTercero.getSucursalRazonSocialTerceroList().remove(sucursalRazonSocialTerceroListSucursalRazonSocialTercero);
                    oldIdRazonSocialOfSucursalRazonSocialTerceroListSucursalRazonSocialTercero = em.merge(oldIdRazonSocialOfSucursalRazonSocialTerceroListSucursalRazonSocialTercero);
                }
            }
            for (TerceroRazonSocial terceroRazonSocialListTerceroRazonSocial : razonSocial.getTerceroRazonSocialList()) {
                RazonSocial oldIdRazonSocialOfTerceroRazonSocialListTerceroRazonSocial = terceroRazonSocialListTerceroRazonSocial.getIdRazonSocial();
                terceroRazonSocialListTerceroRazonSocial.setIdRazonSocial(razonSocial);
                terceroRazonSocialListTerceroRazonSocial = em.merge(terceroRazonSocialListTerceroRazonSocial);
                if (oldIdRazonSocialOfTerceroRazonSocialListTerceroRazonSocial != null) {
                    oldIdRazonSocialOfTerceroRazonSocialListTerceroRazonSocial.getTerceroRazonSocialList().remove(terceroRazonSocialListTerceroRazonSocial);
                    oldIdRazonSocialOfTerceroRazonSocialListTerceroRazonSocial = em.merge(oldIdRazonSocialOfTerceroRazonSocialListTerceroRazonSocial);
                }
            }
            for (RepresentateLegalRazonSocial representateLegalRazonSocialListRepresentateLegalRazonSocial : razonSocial.getRepresentateLegalRazonSocialList()) {
                RazonSocial oldIdRazonSocialOfRepresentateLegalRazonSocialListRepresentateLegalRazonSocial = representateLegalRazonSocialListRepresentateLegalRazonSocial.getIdRazonSocial();
                representateLegalRazonSocialListRepresentateLegalRazonSocial.setIdRazonSocial(razonSocial);
                representateLegalRazonSocialListRepresentateLegalRazonSocial = em.merge(representateLegalRazonSocialListRepresentateLegalRazonSocial);
                if (oldIdRazonSocialOfRepresentateLegalRazonSocialListRepresentateLegalRazonSocial != null) {
                    oldIdRazonSocialOfRepresentateLegalRazonSocialListRepresentateLegalRazonSocial.getRepresentateLegalRazonSocialList().remove(representateLegalRazonSocialListRepresentateLegalRazonSocial);
                    oldIdRazonSocialOfRepresentateLegalRazonSocialListRepresentateLegalRazonSocial = em.merge(oldIdRazonSocialOfRepresentateLegalRazonSocialListRepresentateLegalRazonSocial);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRazonSocial(razonSocial.getId()) != null) {
                throw new PreexistingEntityException("RazonSocial " + razonSocial + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RazonSocial razonSocial) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RazonSocial persistentRazonSocial = em.find(RazonSocial.class, razonSocial.getId());
            TipoOrganizacion tipoOrganizacionOld = persistentRazonSocial.getTipoOrganizacion();
            TipoOrganizacion tipoOrganizacionNew = razonSocial.getTipoOrganizacion();
            List<SucursalRazonSocialTercero> sucursalRazonSocialTerceroListOld = persistentRazonSocial.getSucursalRazonSocialTerceroList();
            List<SucursalRazonSocialTercero> sucursalRazonSocialTerceroListNew = razonSocial.getSucursalRazonSocialTerceroList();
            List<TerceroRazonSocial> terceroRazonSocialListOld = persistentRazonSocial.getTerceroRazonSocialList();
            List<TerceroRazonSocial> terceroRazonSocialListNew = razonSocial.getTerceroRazonSocialList();
            List<RepresentateLegalRazonSocial> representateLegalRazonSocialListOld = persistentRazonSocial.getRepresentateLegalRazonSocialList();
            List<RepresentateLegalRazonSocial> representateLegalRazonSocialListNew = razonSocial.getRepresentateLegalRazonSocialList();
            if (tipoOrganizacionNew != null) {
                tipoOrganizacionNew = em.getReference(tipoOrganizacionNew.getClass(), tipoOrganizacionNew.getId());
                razonSocial.setTipoOrganizacion(tipoOrganizacionNew);
            }
            List<SucursalRazonSocialTercero> attachedSucursalRazonSocialTerceroListNew = new ArrayList<SucursalRazonSocialTercero>();
            for (SucursalRazonSocialTercero sucursalRazonSocialTerceroListNewSucursalRazonSocialTerceroToAttach : sucursalRazonSocialTerceroListNew) {
                sucursalRazonSocialTerceroListNewSucursalRazonSocialTerceroToAttach = em.getReference(sucursalRazonSocialTerceroListNewSucursalRazonSocialTerceroToAttach.getClass(), sucursalRazonSocialTerceroListNewSucursalRazonSocialTerceroToAttach.getId());
                attachedSucursalRazonSocialTerceroListNew.add(sucursalRazonSocialTerceroListNewSucursalRazonSocialTerceroToAttach);
            }
            sucursalRazonSocialTerceroListNew = attachedSucursalRazonSocialTerceroListNew;
            razonSocial.setSucursalRazonSocialTerceroList(sucursalRazonSocialTerceroListNew);
            List<TerceroRazonSocial> attachedTerceroRazonSocialListNew = new ArrayList<TerceroRazonSocial>();
            for (TerceroRazonSocial terceroRazonSocialListNewTerceroRazonSocialToAttach : terceroRazonSocialListNew) {
                terceroRazonSocialListNewTerceroRazonSocialToAttach = em.getReference(terceroRazonSocialListNewTerceroRazonSocialToAttach.getClass(), terceroRazonSocialListNewTerceroRazonSocialToAttach.getId());
                attachedTerceroRazonSocialListNew.add(terceroRazonSocialListNewTerceroRazonSocialToAttach);
            }
            terceroRazonSocialListNew = attachedTerceroRazonSocialListNew;
            razonSocial.setTerceroRazonSocialList(terceroRazonSocialListNew);
            List<RepresentateLegalRazonSocial> attachedRepresentateLegalRazonSocialListNew = new ArrayList<RepresentateLegalRazonSocial>();
            for (RepresentateLegalRazonSocial representateLegalRazonSocialListNewRepresentateLegalRazonSocialToAttach : representateLegalRazonSocialListNew) {
                representateLegalRazonSocialListNewRepresentateLegalRazonSocialToAttach = em.getReference(representateLegalRazonSocialListNewRepresentateLegalRazonSocialToAttach.getClass(), representateLegalRazonSocialListNewRepresentateLegalRazonSocialToAttach.getId());
                attachedRepresentateLegalRazonSocialListNew.add(representateLegalRazonSocialListNewRepresentateLegalRazonSocialToAttach);
            }
            representateLegalRazonSocialListNew = attachedRepresentateLegalRazonSocialListNew;
            razonSocial.setRepresentateLegalRazonSocialList(representateLegalRazonSocialListNew);
            razonSocial = em.merge(razonSocial);
            if (tipoOrganizacionOld != null && !tipoOrganizacionOld.equals(tipoOrganizacionNew)) {
                tipoOrganizacionOld.getRazonSocialList().remove(razonSocial);
                tipoOrganizacionOld = em.merge(tipoOrganizacionOld);
            }
            if (tipoOrganizacionNew != null && !tipoOrganizacionNew.equals(tipoOrganizacionOld)) {
                tipoOrganizacionNew.getRazonSocialList().add(razonSocial);
                tipoOrganizacionNew = em.merge(tipoOrganizacionNew);
            }
            for (SucursalRazonSocialTercero sucursalRazonSocialTerceroListOldSucursalRazonSocialTercero : sucursalRazonSocialTerceroListOld) {
                if (!sucursalRazonSocialTerceroListNew.contains(sucursalRazonSocialTerceroListOldSucursalRazonSocialTercero)) {
                    sucursalRazonSocialTerceroListOldSucursalRazonSocialTercero.setIdRazonSocial(null);
                    sucursalRazonSocialTerceroListOldSucursalRazonSocialTercero = em.merge(sucursalRazonSocialTerceroListOldSucursalRazonSocialTercero);
                }
            }
            for (SucursalRazonSocialTercero sucursalRazonSocialTerceroListNewSucursalRazonSocialTercero : sucursalRazonSocialTerceroListNew) {
                if (!sucursalRazonSocialTerceroListOld.contains(sucursalRazonSocialTerceroListNewSucursalRazonSocialTercero)) {
                    RazonSocial oldIdRazonSocialOfSucursalRazonSocialTerceroListNewSucursalRazonSocialTercero = sucursalRazonSocialTerceroListNewSucursalRazonSocialTercero.getIdRazonSocial();
                    sucursalRazonSocialTerceroListNewSucursalRazonSocialTercero.setIdRazonSocial(razonSocial);
                    sucursalRazonSocialTerceroListNewSucursalRazonSocialTercero = em.merge(sucursalRazonSocialTerceroListNewSucursalRazonSocialTercero);
                    if (oldIdRazonSocialOfSucursalRazonSocialTerceroListNewSucursalRazonSocialTercero != null && !oldIdRazonSocialOfSucursalRazonSocialTerceroListNewSucursalRazonSocialTercero.equals(razonSocial)) {
                        oldIdRazonSocialOfSucursalRazonSocialTerceroListNewSucursalRazonSocialTercero.getSucursalRazonSocialTerceroList().remove(sucursalRazonSocialTerceroListNewSucursalRazonSocialTercero);
                        oldIdRazonSocialOfSucursalRazonSocialTerceroListNewSucursalRazonSocialTercero = em.merge(oldIdRazonSocialOfSucursalRazonSocialTerceroListNewSucursalRazonSocialTercero);
                    }
                }
            }
            for (TerceroRazonSocial terceroRazonSocialListOldTerceroRazonSocial : terceroRazonSocialListOld) {
                if (!terceroRazonSocialListNew.contains(terceroRazonSocialListOldTerceroRazonSocial)) {
                    terceroRazonSocialListOldTerceroRazonSocial.setIdRazonSocial(null);
                    terceroRazonSocialListOldTerceroRazonSocial = em.merge(terceroRazonSocialListOldTerceroRazonSocial);
                }
            }
            for (TerceroRazonSocial terceroRazonSocialListNewTerceroRazonSocial : terceroRazonSocialListNew) {
                if (!terceroRazonSocialListOld.contains(terceroRazonSocialListNewTerceroRazonSocial)) {
                    RazonSocial oldIdRazonSocialOfTerceroRazonSocialListNewTerceroRazonSocial = terceroRazonSocialListNewTerceroRazonSocial.getIdRazonSocial();
                    terceroRazonSocialListNewTerceroRazonSocial.setIdRazonSocial(razonSocial);
                    terceroRazonSocialListNewTerceroRazonSocial = em.merge(terceroRazonSocialListNewTerceroRazonSocial);
                    if (oldIdRazonSocialOfTerceroRazonSocialListNewTerceroRazonSocial != null && !oldIdRazonSocialOfTerceroRazonSocialListNewTerceroRazonSocial.equals(razonSocial)) {
                        oldIdRazonSocialOfTerceroRazonSocialListNewTerceroRazonSocial.getTerceroRazonSocialList().remove(terceroRazonSocialListNewTerceroRazonSocial);
                        oldIdRazonSocialOfTerceroRazonSocialListNewTerceroRazonSocial = em.merge(oldIdRazonSocialOfTerceroRazonSocialListNewTerceroRazonSocial);
                    }
                }
            }
            for (RepresentateLegalRazonSocial representateLegalRazonSocialListOldRepresentateLegalRazonSocial : representateLegalRazonSocialListOld) {
                if (!representateLegalRazonSocialListNew.contains(representateLegalRazonSocialListOldRepresentateLegalRazonSocial)) {
                    representateLegalRazonSocialListOldRepresentateLegalRazonSocial.setIdRazonSocial(null);
                    representateLegalRazonSocialListOldRepresentateLegalRazonSocial = em.merge(representateLegalRazonSocialListOldRepresentateLegalRazonSocial);
                }
            }
            for (RepresentateLegalRazonSocial representateLegalRazonSocialListNewRepresentateLegalRazonSocial : representateLegalRazonSocialListNew) {
                if (!representateLegalRazonSocialListOld.contains(representateLegalRazonSocialListNewRepresentateLegalRazonSocial)) {
                    RazonSocial oldIdRazonSocialOfRepresentateLegalRazonSocialListNewRepresentateLegalRazonSocial = representateLegalRazonSocialListNewRepresentateLegalRazonSocial.getIdRazonSocial();
                    representateLegalRazonSocialListNewRepresentateLegalRazonSocial.setIdRazonSocial(razonSocial);
                    representateLegalRazonSocialListNewRepresentateLegalRazonSocial = em.merge(representateLegalRazonSocialListNewRepresentateLegalRazonSocial);
                    if (oldIdRazonSocialOfRepresentateLegalRazonSocialListNewRepresentateLegalRazonSocial != null && !oldIdRazonSocialOfRepresentateLegalRazonSocialListNewRepresentateLegalRazonSocial.equals(razonSocial)) {
                        oldIdRazonSocialOfRepresentateLegalRazonSocialListNewRepresentateLegalRazonSocial.getRepresentateLegalRazonSocialList().remove(representateLegalRazonSocialListNewRepresentateLegalRazonSocial);
                        oldIdRazonSocialOfRepresentateLegalRazonSocialListNewRepresentateLegalRazonSocial = em.merge(oldIdRazonSocialOfRepresentateLegalRazonSocialListNewRepresentateLegalRazonSocial);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = razonSocial.getId();
                if (findRazonSocial(id) == null) {
                    throw new NonexistentEntityException("The razonSocial with id " + id + " no longer exists.");
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
            RazonSocial razonSocial;
            try {
                razonSocial = em.getReference(RazonSocial.class, id);
                razonSocial.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The razonSocial with id " + id + " no longer exists.", enfe);
            }
            TipoOrganizacion tipoOrganizacion = razonSocial.getTipoOrganizacion();
            if (tipoOrganizacion != null) {
                tipoOrganizacion.getRazonSocialList().remove(razonSocial);
                tipoOrganizacion = em.merge(tipoOrganizacion);
            }
            List<SucursalRazonSocialTercero> sucursalRazonSocialTerceroList = razonSocial.getSucursalRazonSocialTerceroList();
            for (SucursalRazonSocialTercero sucursalRazonSocialTerceroListSucursalRazonSocialTercero : sucursalRazonSocialTerceroList) {
                sucursalRazonSocialTerceroListSucursalRazonSocialTercero.setIdRazonSocial(null);
                sucursalRazonSocialTerceroListSucursalRazonSocialTercero = em.merge(sucursalRazonSocialTerceroListSucursalRazonSocialTercero);
            }
            List<TerceroRazonSocial> terceroRazonSocialList = razonSocial.getTerceroRazonSocialList();
            for (TerceroRazonSocial terceroRazonSocialListTerceroRazonSocial : terceroRazonSocialList) {
                terceroRazonSocialListTerceroRazonSocial.setIdRazonSocial(null);
                terceroRazonSocialListTerceroRazonSocial = em.merge(terceroRazonSocialListTerceroRazonSocial);
            }
            List<RepresentateLegalRazonSocial> representateLegalRazonSocialList = razonSocial.getRepresentateLegalRazonSocialList();
            for (RepresentateLegalRazonSocial representateLegalRazonSocialListRepresentateLegalRazonSocial : representateLegalRazonSocialList) {
                representateLegalRazonSocialListRepresentateLegalRazonSocial.setIdRazonSocial(null);
                representateLegalRazonSocialListRepresentateLegalRazonSocial = em.merge(representateLegalRazonSocialListRepresentateLegalRazonSocial);
            }
            em.remove(razonSocial);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RazonSocial> findRazonSocialEntities() {
        return findRazonSocialEntities(true, -1, -1);
    }

    public List<RazonSocial> findRazonSocialEntities(int maxResults, int firstResult) {
        return findRazonSocialEntities(false, maxResults, firstResult);
    }

    private List<RazonSocial> findRazonSocialEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RazonSocial.class));
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

    public RazonSocial findRazonSocial(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RazonSocial.class, id);
        } finally {
            em.close();
        }
    }

    public int getRazonSocialCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RazonSocial> rt = cq.from(RazonSocial.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
