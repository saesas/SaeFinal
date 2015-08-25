/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.ContactoTercero;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.SucursalRazonSocialTercero;
import com.sae.persistence.domain.DependenciaSucursal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ContactoTerceroJpaController implements Serializable {

    public ContactoTerceroJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ContactoTercero contactoTercero) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SucursalRazonSocialTercero idSucursal = contactoTercero.getIdSucursal();
            if (idSucursal != null) {
                idSucursal = em.getReference(idSucursal.getClass(), idSucursal.getId());
                contactoTercero.setIdSucursal(idSucursal);
            }
            DependenciaSucursal idDependencia = contactoTercero.getIdDependencia();
            if (idDependencia != null) {
                idDependencia = em.getReference(idDependencia.getClass(), idDependencia.getId());
                contactoTercero.setIdDependencia(idDependencia);
            }
            em.persist(contactoTercero);
            if (idSucursal != null) {
                idSucursal.getContactoTerceroList().add(contactoTercero);
                idSucursal = em.merge(idSucursal);
            }
            if (idDependencia != null) {
                idDependencia.getContactoTerceroList().add(contactoTercero);
                idDependencia = em.merge(idDependencia);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findContactoTercero(contactoTercero.getId()) != null) {
                throw new PreexistingEntityException("ContactoTercero " + contactoTercero + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ContactoTercero contactoTercero) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ContactoTercero persistentContactoTercero = em.find(ContactoTercero.class, contactoTercero.getId());
            SucursalRazonSocialTercero idSucursalOld = persistentContactoTercero.getIdSucursal();
            SucursalRazonSocialTercero idSucursalNew = contactoTercero.getIdSucursal();
            DependenciaSucursal idDependenciaOld = persistentContactoTercero.getIdDependencia();
            DependenciaSucursal idDependenciaNew = contactoTercero.getIdDependencia();
            if (idSucursalNew != null) {
                idSucursalNew = em.getReference(idSucursalNew.getClass(), idSucursalNew.getId());
                contactoTercero.setIdSucursal(idSucursalNew);
            }
            if (idDependenciaNew != null) {
                idDependenciaNew = em.getReference(idDependenciaNew.getClass(), idDependenciaNew.getId());
                contactoTercero.setIdDependencia(idDependenciaNew);
            }
            contactoTercero = em.merge(contactoTercero);
            if (idSucursalOld != null && !idSucursalOld.equals(idSucursalNew)) {
                idSucursalOld.getContactoTerceroList().remove(contactoTercero);
                idSucursalOld = em.merge(idSucursalOld);
            }
            if (idSucursalNew != null && !idSucursalNew.equals(idSucursalOld)) {
                idSucursalNew.getContactoTerceroList().add(contactoTercero);
                idSucursalNew = em.merge(idSucursalNew);
            }
            if (idDependenciaOld != null && !idDependenciaOld.equals(idDependenciaNew)) {
                idDependenciaOld.getContactoTerceroList().remove(contactoTercero);
                idDependenciaOld = em.merge(idDependenciaOld);
            }
            if (idDependenciaNew != null && !idDependenciaNew.equals(idDependenciaOld)) {
                idDependenciaNew.getContactoTerceroList().add(contactoTercero);
                idDependenciaNew = em.merge(idDependenciaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contactoTercero.getId();
                if (findContactoTercero(id) == null) {
                    throw new NonexistentEntityException("The contactoTercero with id " + id + " no longer exists.");
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
            ContactoTercero contactoTercero;
            try {
                contactoTercero = em.getReference(ContactoTercero.class, id);
                contactoTercero.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contactoTercero with id " + id + " no longer exists.", enfe);
            }
            SucursalRazonSocialTercero idSucursal = contactoTercero.getIdSucursal();
            if (idSucursal != null) {
                idSucursal.getContactoTerceroList().remove(contactoTercero);
                idSucursal = em.merge(idSucursal);
            }
            DependenciaSucursal idDependencia = contactoTercero.getIdDependencia();
            if (idDependencia != null) {
                idDependencia.getContactoTerceroList().remove(contactoTercero);
                idDependencia = em.merge(idDependencia);
            }
            em.remove(contactoTercero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ContactoTercero> findContactoTerceroEntities() {
        return findContactoTerceroEntities(true, -1, -1);
    }

    public List<ContactoTercero> findContactoTerceroEntities(int maxResults, int firstResult) {
        return findContactoTerceroEntities(false, maxResults, firstResult);
    }

    private List<ContactoTercero> findContactoTerceroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ContactoTercero.class));
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

    public ContactoTercero findContactoTercero(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ContactoTercero.class, id);
        } finally {
            em.close();
        }
    }

    public int getContactoTerceroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ContactoTercero> rt = cq.from(ContactoTercero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
