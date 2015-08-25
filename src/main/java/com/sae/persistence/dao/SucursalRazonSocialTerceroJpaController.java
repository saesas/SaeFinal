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
import com.sae.persistence.domain.TipoSucursal;
import com.sae.persistence.domain.Tercero;
import com.sae.persistence.domain.RazonSocial;
import com.sae.persistence.domain.Municipio;
import com.sae.persistence.domain.ContactoTercero;
import com.sae.persistence.domain.SucursalRazonSocialTercero;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class SucursalRazonSocialTerceroJpaController implements Serializable {

    public SucursalRazonSocialTerceroJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SucursalRazonSocialTercero sucursalRazonSocialTercero) throws PreexistingEntityException, Exception {
        if (sucursalRazonSocialTercero.getContactoTerceroList() == null) {
            sucursalRazonSocialTercero.setContactoTerceroList(new ArrayList<ContactoTercero>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoSucursal idTipoSucursal = sucursalRazonSocialTercero.getIdTipoSucursal();
            if (idTipoSucursal != null) {
                idTipoSucursal = em.getReference(idTipoSucursal.getClass(), idTipoSucursal.getId());
                sucursalRazonSocialTercero.setIdTipoSucursal(idTipoSucursal);
            }
            Tercero idTercero = sucursalRazonSocialTercero.getIdTercero();
            if (idTercero != null) {
                idTercero = em.getReference(idTercero.getClass(), idTercero.getId());
                sucursalRazonSocialTercero.setIdTercero(idTercero);
            }
            RazonSocial idRazonSocial = sucursalRazonSocialTercero.getIdRazonSocial();
            if (idRazonSocial != null) {
                idRazonSocial = em.getReference(idRazonSocial.getClass(), idRazonSocial.getId());
                sucursalRazonSocialTercero.setIdRazonSocial(idRazonSocial);
            }
            Municipio idMunicipio = sucursalRazonSocialTercero.getIdMunicipio();
            if (idMunicipio != null) {
                idMunicipio = em.getReference(idMunicipio.getClass(), idMunicipio.getId());
                sucursalRazonSocialTercero.setIdMunicipio(idMunicipio);
            }
            List<ContactoTercero> attachedContactoTerceroList = new ArrayList<ContactoTercero>();
            for (ContactoTercero contactoTerceroListContactoTerceroToAttach : sucursalRazonSocialTercero.getContactoTerceroList()) {
                contactoTerceroListContactoTerceroToAttach = em.getReference(contactoTerceroListContactoTerceroToAttach.getClass(), contactoTerceroListContactoTerceroToAttach.getId());
                attachedContactoTerceroList.add(contactoTerceroListContactoTerceroToAttach);
            }
            sucursalRazonSocialTercero.setContactoTerceroList(attachedContactoTerceroList);
            em.persist(sucursalRazonSocialTercero);
            if (idTipoSucursal != null) {
                idTipoSucursal.getSucursalRazonSocialTerceroList().add(sucursalRazonSocialTercero);
                idTipoSucursal = em.merge(idTipoSucursal);
            }
            if (idTercero != null) {
                idTercero.getSucursalRazonSocialTerceroList().add(sucursalRazonSocialTercero);
                idTercero = em.merge(idTercero);
            }
            if (idRazonSocial != null) {
                idRazonSocial.getSucursalRazonSocialTerceroList().add(sucursalRazonSocialTercero);
                idRazonSocial = em.merge(idRazonSocial);
            }
            if (idMunicipio != null) {
                idMunicipio.getSucursalRazonSocialTerceroList().add(sucursalRazonSocialTercero);
                idMunicipio = em.merge(idMunicipio);
            }
            for (ContactoTercero contactoTerceroListContactoTercero : sucursalRazonSocialTercero.getContactoTerceroList()) {
                SucursalRazonSocialTercero oldIdSucursalOfContactoTerceroListContactoTercero = contactoTerceroListContactoTercero.getIdSucursal();
                contactoTerceroListContactoTercero.setIdSucursal(sucursalRazonSocialTercero);
                contactoTerceroListContactoTercero = em.merge(contactoTerceroListContactoTercero);
                if (oldIdSucursalOfContactoTerceroListContactoTercero != null) {
                    oldIdSucursalOfContactoTerceroListContactoTercero.getContactoTerceroList().remove(contactoTerceroListContactoTercero);
                    oldIdSucursalOfContactoTerceroListContactoTercero = em.merge(oldIdSucursalOfContactoTerceroListContactoTercero);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSucursalRazonSocialTercero(sucursalRazonSocialTercero.getId()) != null) {
                throw new PreexistingEntityException("SucursalRazonSocialTercero " + sucursalRazonSocialTercero + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SucursalRazonSocialTercero sucursalRazonSocialTercero) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SucursalRazonSocialTercero persistentSucursalRazonSocialTercero = em.find(SucursalRazonSocialTercero.class, sucursalRazonSocialTercero.getId());
            TipoSucursal idTipoSucursalOld = persistentSucursalRazonSocialTercero.getIdTipoSucursal();
            TipoSucursal idTipoSucursalNew = sucursalRazonSocialTercero.getIdTipoSucursal();
            Tercero idTerceroOld = persistentSucursalRazonSocialTercero.getIdTercero();
            Tercero idTerceroNew = sucursalRazonSocialTercero.getIdTercero();
            RazonSocial idRazonSocialOld = persistentSucursalRazonSocialTercero.getIdRazonSocial();
            RazonSocial idRazonSocialNew = sucursalRazonSocialTercero.getIdRazonSocial();
            Municipio idMunicipioOld = persistentSucursalRazonSocialTercero.getIdMunicipio();
            Municipio idMunicipioNew = sucursalRazonSocialTercero.getIdMunicipio();
            List<ContactoTercero> contactoTerceroListOld = persistentSucursalRazonSocialTercero.getContactoTerceroList();
            List<ContactoTercero> contactoTerceroListNew = sucursalRazonSocialTercero.getContactoTerceroList();
            if (idTipoSucursalNew != null) {
                idTipoSucursalNew = em.getReference(idTipoSucursalNew.getClass(), idTipoSucursalNew.getId());
                sucursalRazonSocialTercero.setIdTipoSucursal(idTipoSucursalNew);
            }
            if (idTerceroNew != null) {
                idTerceroNew = em.getReference(idTerceroNew.getClass(), idTerceroNew.getId());
                sucursalRazonSocialTercero.setIdTercero(idTerceroNew);
            }
            if (idRazonSocialNew != null) {
                idRazonSocialNew = em.getReference(idRazonSocialNew.getClass(), idRazonSocialNew.getId());
                sucursalRazonSocialTercero.setIdRazonSocial(idRazonSocialNew);
            }
            if (idMunicipioNew != null) {
                idMunicipioNew = em.getReference(idMunicipioNew.getClass(), idMunicipioNew.getId());
                sucursalRazonSocialTercero.setIdMunicipio(idMunicipioNew);
            }
            List<ContactoTercero> attachedContactoTerceroListNew = new ArrayList<ContactoTercero>();
            for (ContactoTercero contactoTerceroListNewContactoTerceroToAttach : contactoTerceroListNew) {
                contactoTerceroListNewContactoTerceroToAttach = em.getReference(contactoTerceroListNewContactoTerceroToAttach.getClass(), contactoTerceroListNewContactoTerceroToAttach.getId());
                attachedContactoTerceroListNew.add(contactoTerceroListNewContactoTerceroToAttach);
            }
            contactoTerceroListNew = attachedContactoTerceroListNew;
            sucursalRazonSocialTercero.setContactoTerceroList(contactoTerceroListNew);
            sucursalRazonSocialTercero = em.merge(sucursalRazonSocialTercero);
            if (idTipoSucursalOld != null && !idTipoSucursalOld.equals(idTipoSucursalNew)) {
                idTipoSucursalOld.getSucursalRazonSocialTerceroList().remove(sucursalRazonSocialTercero);
                idTipoSucursalOld = em.merge(idTipoSucursalOld);
            }
            if (idTipoSucursalNew != null && !idTipoSucursalNew.equals(idTipoSucursalOld)) {
                idTipoSucursalNew.getSucursalRazonSocialTerceroList().add(sucursalRazonSocialTercero);
                idTipoSucursalNew = em.merge(idTipoSucursalNew);
            }
            if (idTerceroOld != null && !idTerceroOld.equals(idTerceroNew)) {
                idTerceroOld.getSucursalRazonSocialTerceroList().remove(sucursalRazonSocialTercero);
                idTerceroOld = em.merge(idTerceroOld);
            }
            if (idTerceroNew != null && !idTerceroNew.equals(idTerceroOld)) {
                idTerceroNew.getSucursalRazonSocialTerceroList().add(sucursalRazonSocialTercero);
                idTerceroNew = em.merge(idTerceroNew);
            }
            if (idRazonSocialOld != null && !idRazonSocialOld.equals(idRazonSocialNew)) {
                idRazonSocialOld.getSucursalRazonSocialTerceroList().remove(sucursalRazonSocialTercero);
                idRazonSocialOld = em.merge(idRazonSocialOld);
            }
            if (idRazonSocialNew != null && !idRazonSocialNew.equals(idRazonSocialOld)) {
                idRazonSocialNew.getSucursalRazonSocialTerceroList().add(sucursalRazonSocialTercero);
                idRazonSocialNew = em.merge(idRazonSocialNew);
            }
            if (idMunicipioOld != null && !idMunicipioOld.equals(idMunicipioNew)) {
                idMunicipioOld.getSucursalRazonSocialTerceroList().remove(sucursalRazonSocialTercero);
                idMunicipioOld = em.merge(idMunicipioOld);
            }
            if (idMunicipioNew != null && !idMunicipioNew.equals(idMunicipioOld)) {
                idMunicipioNew.getSucursalRazonSocialTerceroList().add(sucursalRazonSocialTercero);
                idMunicipioNew = em.merge(idMunicipioNew);
            }
            for (ContactoTercero contactoTerceroListOldContactoTercero : contactoTerceroListOld) {
                if (!contactoTerceroListNew.contains(contactoTerceroListOldContactoTercero)) {
                    contactoTerceroListOldContactoTercero.setIdSucursal(null);
                    contactoTerceroListOldContactoTercero = em.merge(contactoTerceroListOldContactoTercero);
                }
            }
            for (ContactoTercero contactoTerceroListNewContactoTercero : contactoTerceroListNew) {
                if (!contactoTerceroListOld.contains(contactoTerceroListNewContactoTercero)) {
                    SucursalRazonSocialTercero oldIdSucursalOfContactoTerceroListNewContactoTercero = contactoTerceroListNewContactoTercero.getIdSucursal();
                    contactoTerceroListNewContactoTercero.setIdSucursal(sucursalRazonSocialTercero);
                    contactoTerceroListNewContactoTercero = em.merge(contactoTerceroListNewContactoTercero);
                    if (oldIdSucursalOfContactoTerceroListNewContactoTercero != null && !oldIdSucursalOfContactoTerceroListNewContactoTercero.equals(sucursalRazonSocialTercero)) {
                        oldIdSucursalOfContactoTerceroListNewContactoTercero.getContactoTerceroList().remove(contactoTerceroListNewContactoTercero);
                        oldIdSucursalOfContactoTerceroListNewContactoTercero = em.merge(oldIdSucursalOfContactoTerceroListNewContactoTercero);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sucursalRazonSocialTercero.getId();
                if (findSucursalRazonSocialTercero(id) == null) {
                    throw new NonexistentEntityException("The sucursalRazonSocialTercero with id " + id + " no longer exists.");
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
            SucursalRazonSocialTercero sucursalRazonSocialTercero;
            try {
                sucursalRazonSocialTercero = em.getReference(SucursalRazonSocialTercero.class, id);
                sucursalRazonSocialTercero.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sucursalRazonSocialTercero with id " + id + " no longer exists.", enfe);
            }
            TipoSucursal idTipoSucursal = sucursalRazonSocialTercero.getIdTipoSucursal();
            if (idTipoSucursal != null) {
                idTipoSucursal.getSucursalRazonSocialTerceroList().remove(sucursalRazonSocialTercero);
                idTipoSucursal = em.merge(idTipoSucursal);
            }
            Tercero idTercero = sucursalRazonSocialTercero.getIdTercero();
            if (idTercero != null) {
                idTercero.getSucursalRazonSocialTerceroList().remove(sucursalRazonSocialTercero);
                idTercero = em.merge(idTercero);
            }
            RazonSocial idRazonSocial = sucursalRazonSocialTercero.getIdRazonSocial();
            if (idRazonSocial != null) {
                idRazonSocial.getSucursalRazonSocialTerceroList().remove(sucursalRazonSocialTercero);
                idRazonSocial = em.merge(idRazonSocial);
            }
            Municipio idMunicipio = sucursalRazonSocialTercero.getIdMunicipio();
            if (idMunicipio != null) {
                idMunicipio.getSucursalRazonSocialTerceroList().remove(sucursalRazonSocialTercero);
                idMunicipio = em.merge(idMunicipio);
            }
            List<ContactoTercero> contactoTerceroList = sucursalRazonSocialTercero.getContactoTerceroList();
            for (ContactoTercero contactoTerceroListContactoTercero : contactoTerceroList) {
                contactoTerceroListContactoTercero.setIdSucursal(null);
                contactoTerceroListContactoTercero = em.merge(contactoTerceroListContactoTercero);
            }
            em.remove(sucursalRazonSocialTercero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SucursalRazonSocialTercero> findSucursalRazonSocialTerceroEntities() {
        return findSucursalRazonSocialTerceroEntities(true, -1, -1);
    }

    public List<SucursalRazonSocialTercero> findSucursalRazonSocialTerceroEntities(int maxResults, int firstResult) {
        return findSucursalRazonSocialTerceroEntities(false, maxResults, firstResult);
    }

    private List<SucursalRazonSocialTercero> findSucursalRazonSocialTerceroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SucursalRazonSocialTercero.class));
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

    public SucursalRazonSocialTercero findSucursalRazonSocialTercero(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SucursalRazonSocialTercero.class, id);
        } finally {
            em.close();
        }
    }

    public int getSucursalRazonSocialTerceroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SucursalRazonSocialTercero> rt = cq.from(SucursalRazonSocialTercero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
