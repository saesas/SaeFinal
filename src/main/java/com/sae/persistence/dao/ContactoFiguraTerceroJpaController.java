/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.ContactoFiguraTercero;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.FiguraPoder;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ContactoFiguraTerceroJpaController implements Serializable {

    public ContactoFiguraTerceroJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ContactoFiguraTercero contactoFiguraTercero) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FiguraPoder idFiguraPoder = contactoFiguraTercero.getIdFiguraPoder();
            if (idFiguraPoder != null) {
                idFiguraPoder = em.getReference(idFiguraPoder.getClass(), idFiguraPoder.getId());
                contactoFiguraTercero.setIdFiguraPoder(idFiguraPoder);
            }
            em.persist(contactoFiguraTercero);
            if (idFiguraPoder != null) {
                idFiguraPoder.getContactoFiguraTerceroList().add(contactoFiguraTercero);
                idFiguraPoder = em.merge(idFiguraPoder);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findContactoFiguraTercero(contactoFiguraTercero.getId()) != null) {
                throw new PreexistingEntityException("ContactoFiguraTercero " + contactoFiguraTercero + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ContactoFiguraTercero contactoFiguraTercero) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ContactoFiguraTercero persistentContactoFiguraTercero = em.find(ContactoFiguraTercero.class, contactoFiguraTercero.getId());
            FiguraPoder idFiguraPoderOld = persistentContactoFiguraTercero.getIdFiguraPoder();
            FiguraPoder idFiguraPoderNew = contactoFiguraTercero.getIdFiguraPoder();
            if (idFiguraPoderNew != null) {
                idFiguraPoderNew = em.getReference(idFiguraPoderNew.getClass(), idFiguraPoderNew.getId());
                contactoFiguraTercero.setIdFiguraPoder(idFiguraPoderNew);
            }
            contactoFiguraTercero = em.merge(contactoFiguraTercero);
            if (idFiguraPoderOld != null && !idFiguraPoderOld.equals(idFiguraPoderNew)) {
                idFiguraPoderOld.getContactoFiguraTerceroList().remove(contactoFiguraTercero);
                idFiguraPoderOld = em.merge(idFiguraPoderOld);
            }
            if (idFiguraPoderNew != null && !idFiguraPoderNew.equals(idFiguraPoderOld)) {
                idFiguraPoderNew.getContactoFiguraTerceroList().add(contactoFiguraTercero);
                idFiguraPoderNew = em.merge(idFiguraPoderNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contactoFiguraTercero.getId();
                if (findContactoFiguraTercero(id) == null) {
                    throw new NonexistentEntityException("The contactoFiguraTercero with id " + id + " no longer exists.");
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
            ContactoFiguraTercero contactoFiguraTercero;
            try {
                contactoFiguraTercero = em.getReference(ContactoFiguraTercero.class, id);
                contactoFiguraTercero.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contactoFiguraTercero with id " + id + " no longer exists.", enfe);
            }
            FiguraPoder idFiguraPoder = contactoFiguraTercero.getIdFiguraPoder();
            if (idFiguraPoder != null) {
                idFiguraPoder.getContactoFiguraTerceroList().remove(contactoFiguraTercero);
                idFiguraPoder = em.merge(idFiguraPoder);
            }
            em.remove(contactoFiguraTercero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ContactoFiguraTercero> findContactoFiguraTerceroEntities() {
        return findContactoFiguraTerceroEntities(true, -1, -1);
    }

    public List<ContactoFiguraTercero> findContactoFiguraTerceroEntities(int maxResults, int firstResult) {
        return findContactoFiguraTerceroEntities(false, maxResults, firstResult);
    }

    private List<ContactoFiguraTercero> findContactoFiguraTerceroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ContactoFiguraTercero.class));
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

    public ContactoFiguraTercero findContactoFiguraTercero(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ContactoFiguraTercero.class, id);
        } finally {
            em.close();
        }
    }

    public int getContactoFiguraTerceroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ContactoFiguraTercero> rt = cq.from(ContactoFiguraTercero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
