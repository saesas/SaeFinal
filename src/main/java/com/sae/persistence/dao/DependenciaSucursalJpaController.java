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
import com.sae.persistence.domain.ContactoTercero;
import com.sae.persistence.domain.DependenciaSucursal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class DependenciaSucursalJpaController implements Serializable {

    public DependenciaSucursalJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DependenciaSucursal dependenciaSucursal) throws PreexistingEntityException, Exception {
        if (dependenciaSucursal.getContactoTerceroList() == null) {
            dependenciaSucursal.setContactoTerceroList(new ArrayList<ContactoTercero>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ContactoTercero> attachedContactoTerceroList = new ArrayList<ContactoTercero>();
            for (ContactoTercero contactoTerceroListContactoTerceroToAttach : dependenciaSucursal.getContactoTerceroList()) {
                contactoTerceroListContactoTerceroToAttach = em.getReference(contactoTerceroListContactoTerceroToAttach.getClass(), contactoTerceroListContactoTerceroToAttach.getId());
                attachedContactoTerceroList.add(contactoTerceroListContactoTerceroToAttach);
            }
            dependenciaSucursal.setContactoTerceroList(attachedContactoTerceroList);
            em.persist(dependenciaSucursal);
            for (ContactoTercero contactoTerceroListContactoTercero : dependenciaSucursal.getContactoTerceroList()) {
                DependenciaSucursal oldIdDependenciaOfContactoTerceroListContactoTercero = contactoTerceroListContactoTercero.getIdDependencia();
                contactoTerceroListContactoTercero.setIdDependencia(dependenciaSucursal);
                contactoTerceroListContactoTercero = em.merge(contactoTerceroListContactoTercero);
                if (oldIdDependenciaOfContactoTerceroListContactoTercero != null) {
                    oldIdDependenciaOfContactoTerceroListContactoTercero.getContactoTerceroList().remove(contactoTerceroListContactoTercero);
                    oldIdDependenciaOfContactoTerceroListContactoTercero = em.merge(oldIdDependenciaOfContactoTerceroListContactoTercero);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDependenciaSucursal(dependenciaSucursal.getId()) != null) {
                throw new PreexistingEntityException("DependenciaSucursal " + dependenciaSucursal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DependenciaSucursal dependenciaSucursal) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DependenciaSucursal persistentDependenciaSucursal = em.find(DependenciaSucursal.class, dependenciaSucursal.getId());
            List<ContactoTercero> contactoTerceroListOld = persistentDependenciaSucursal.getContactoTerceroList();
            List<ContactoTercero> contactoTerceroListNew = dependenciaSucursal.getContactoTerceroList();
            List<ContactoTercero> attachedContactoTerceroListNew = new ArrayList<ContactoTercero>();
            for (ContactoTercero contactoTerceroListNewContactoTerceroToAttach : contactoTerceroListNew) {
                contactoTerceroListNewContactoTerceroToAttach = em.getReference(contactoTerceroListNewContactoTerceroToAttach.getClass(), contactoTerceroListNewContactoTerceroToAttach.getId());
                attachedContactoTerceroListNew.add(contactoTerceroListNewContactoTerceroToAttach);
            }
            contactoTerceroListNew = attachedContactoTerceroListNew;
            dependenciaSucursal.setContactoTerceroList(contactoTerceroListNew);
            dependenciaSucursal = em.merge(dependenciaSucursal);
            for (ContactoTercero contactoTerceroListOldContactoTercero : contactoTerceroListOld) {
                if (!contactoTerceroListNew.contains(contactoTerceroListOldContactoTercero)) {
                    contactoTerceroListOldContactoTercero.setIdDependencia(null);
                    contactoTerceroListOldContactoTercero = em.merge(contactoTerceroListOldContactoTercero);
                }
            }
            for (ContactoTercero contactoTerceroListNewContactoTercero : contactoTerceroListNew) {
                if (!contactoTerceroListOld.contains(contactoTerceroListNewContactoTercero)) {
                    DependenciaSucursal oldIdDependenciaOfContactoTerceroListNewContactoTercero = contactoTerceroListNewContactoTercero.getIdDependencia();
                    contactoTerceroListNewContactoTercero.setIdDependencia(dependenciaSucursal);
                    contactoTerceroListNewContactoTercero = em.merge(contactoTerceroListNewContactoTercero);
                    if (oldIdDependenciaOfContactoTerceroListNewContactoTercero != null && !oldIdDependenciaOfContactoTerceroListNewContactoTercero.equals(dependenciaSucursal)) {
                        oldIdDependenciaOfContactoTerceroListNewContactoTercero.getContactoTerceroList().remove(contactoTerceroListNewContactoTercero);
                        oldIdDependenciaOfContactoTerceroListNewContactoTercero = em.merge(oldIdDependenciaOfContactoTerceroListNewContactoTercero);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = dependenciaSucursal.getId();
                if (findDependenciaSucursal(id) == null) {
                    throw new NonexistentEntityException("The dependenciaSucursal with id " + id + " no longer exists.");
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
            DependenciaSucursal dependenciaSucursal;
            try {
                dependenciaSucursal = em.getReference(DependenciaSucursal.class, id);
                dependenciaSucursal.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dependenciaSucursal with id " + id + " no longer exists.", enfe);
            }
            List<ContactoTercero> contactoTerceroList = dependenciaSucursal.getContactoTerceroList();
            for (ContactoTercero contactoTerceroListContactoTercero : contactoTerceroList) {
                contactoTerceroListContactoTercero.setIdDependencia(null);
                contactoTerceroListContactoTercero = em.merge(contactoTerceroListContactoTercero);
            }
            em.remove(dependenciaSucursal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DependenciaSucursal> findDependenciaSucursalEntities() {
        return findDependenciaSucursalEntities(true, -1, -1);
    }

    public List<DependenciaSucursal> findDependenciaSucursalEntities(int maxResults, int firstResult) {
        return findDependenciaSucursalEntities(false, maxResults, firstResult);
    }

    private List<DependenciaSucursal> findDependenciaSucursalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DependenciaSucursal.class));
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

    public DependenciaSucursal findDependenciaSucursal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DependenciaSucursal.class, id);
        } finally {
            em.close();
        }
    }

    public int getDependenciaSucursalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DependenciaSucursal> rt = cq.from(DependenciaSucursal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
