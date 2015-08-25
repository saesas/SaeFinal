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
import com.sae.persistence.domain.Figura;
import com.sae.persistence.domain.ContactoFiguraTercero;
import com.sae.persistence.domain.FiguraPoder;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class FiguraPoderJpaController implements Serializable {

    public FiguraPoderJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FiguraPoder figuraPoder) throws PreexistingEntityException, Exception {
        if (figuraPoder.getContactoFiguraTerceroList() == null) {
            figuraPoder.setContactoFiguraTerceroList(new ArrayList<ContactoFiguraTercero>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Figura idFigura = figuraPoder.getIdFigura();
            if (idFigura != null) {
                idFigura = em.getReference(idFigura.getClass(), idFigura.getId());
                figuraPoder.setIdFigura(idFigura);
            }
            List<ContactoFiguraTercero> attachedContactoFiguraTerceroList = new ArrayList<ContactoFiguraTercero>();
            for (ContactoFiguraTercero contactoFiguraTerceroListContactoFiguraTerceroToAttach : figuraPoder.getContactoFiguraTerceroList()) {
                contactoFiguraTerceroListContactoFiguraTerceroToAttach = em.getReference(contactoFiguraTerceroListContactoFiguraTerceroToAttach.getClass(), contactoFiguraTerceroListContactoFiguraTerceroToAttach.getId());
                attachedContactoFiguraTerceroList.add(contactoFiguraTerceroListContactoFiguraTerceroToAttach);
            }
            figuraPoder.setContactoFiguraTerceroList(attachedContactoFiguraTerceroList);
            em.persist(figuraPoder);
            if (idFigura != null) {
                idFigura.getFiguraPoderList().add(figuraPoder);
                idFigura = em.merge(idFigura);
            }
            for (ContactoFiguraTercero contactoFiguraTerceroListContactoFiguraTercero : figuraPoder.getContactoFiguraTerceroList()) {
                FiguraPoder oldIdFiguraPoderOfContactoFiguraTerceroListContactoFiguraTercero = contactoFiguraTerceroListContactoFiguraTercero.getIdFiguraPoder();
                contactoFiguraTerceroListContactoFiguraTercero.setIdFiguraPoder(figuraPoder);
                contactoFiguraTerceroListContactoFiguraTercero = em.merge(contactoFiguraTerceroListContactoFiguraTercero);
                if (oldIdFiguraPoderOfContactoFiguraTerceroListContactoFiguraTercero != null) {
                    oldIdFiguraPoderOfContactoFiguraTerceroListContactoFiguraTercero.getContactoFiguraTerceroList().remove(contactoFiguraTerceroListContactoFiguraTercero);
                    oldIdFiguraPoderOfContactoFiguraTerceroListContactoFiguraTercero = em.merge(oldIdFiguraPoderOfContactoFiguraTerceroListContactoFiguraTercero);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFiguraPoder(figuraPoder.getId()) != null) {
                throw new PreexistingEntityException("FiguraPoder " + figuraPoder + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FiguraPoder figuraPoder) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FiguraPoder persistentFiguraPoder = em.find(FiguraPoder.class, figuraPoder.getId());
            Figura idFiguraOld = persistentFiguraPoder.getIdFigura();
            Figura idFiguraNew = figuraPoder.getIdFigura();
            List<ContactoFiguraTercero> contactoFiguraTerceroListOld = persistentFiguraPoder.getContactoFiguraTerceroList();
            List<ContactoFiguraTercero> contactoFiguraTerceroListNew = figuraPoder.getContactoFiguraTerceroList();
            if (idFiguraNew != null) {
                idFiguraNew = em.getReference(idFiguraNew.getClass(), idFiguraNew.getId());
                figuraPoder.setIdFigura(idFiguraNew);
            }
            List<ContactoFiguraTercero> attachedContactoFiguraTerceroListNew = new ArrayList<ContactoFiguraTercero>();
            for (ContactoFiguraTercero contactoFiguraTerceroListNewContactoFiguraTerceroToAttach : contactoFiguraTerceroListNew) {
                contactoFiguraTerceroListNewContactoFiguraTerceroToAttach = em.getReference(contactoFiguraTerceroListNewContactoFiguraTerceroToAttach.getClass(), contactoFiguraTerceroListNewContactoFiguraTerceroToAttach.getId());
                attachedContactoFiguraTerceroListNew.add(contactoFiguraTerceroListNewContactoFiguraTerceroToAttach);
            }
            contactoFiguraTerceroListNew = attachedContactoFiguraTerceroListNew;
            figuraPoder.setContactoFiguraTerceroList(contactoFiguraTerceroListNew);
            figuraPoder = em.merge(figuraPoder);
            if (idFiguraOld != null && !idFiguraOld.equals(idFiguraNew)) {
                idFiguraOld.getFiguraPoderList().remove(figuraPoder);
                idFiguraOld = em.merge(idFiguraOld);
            }
            if (idFiguraNew != null && !idFiguraNew.equals(idFiguraOld)) {
                idFiguraNew.getFiguraPoderList().add(figuraPoder);
                idFiguraNew = em.merge(idFiguraNew);
            }
            for (ContactoFiguraTercero contactoFiguraTerceroListOldContactoFiguraTercero : contactoFiguraTerceroListOld) {
                if (!contactoFiguraTerceroListNew.contains(contactoFiguraTerceroListOldContactoFiguraTercero)) {
                    contactoFiguraTerceroListOldContactoFiguraTercero.setIdFiguraPoder(null);
                    contactoFiguraTerceroListOldContactoFiguraTercero = em.merge(contactoFiguraTerceroListOldContactoFiguraTercero);
                }
            }
            for (ContactoFiguraTercero contactoFiguraTerceroListNewContactoFiguraTercero : contactoFiguraTerceroListNew) {
                if (!contactoFiguraTerceroListOld.contains(contactoFiguraTerceroListNewContactoFiguraTercero)) {
                    FiguraPoder oldIdFiguraPoderOfContactoFiguraTerceroListNewContactoFiguraTercero = contactoFiguraTerceroListNewContactoFiguraTercero.getIdFiguraPoder();
                    contactoFiguraTerceroListNewContactoFiguraTercero.setIdFiguraPoder(figuraPoder);
                    contactoFiguraTerceroListNewContactoFiguraTercero = em.merge(contactoFiguraTerceroListNewContactoFiguraTercero);
                    if (oldIdFiguraPoderOfContactoFiguraTerceroListNewContactoFiguraTercero != null && !oldIdFiguraPoderOfContactoFiguraTerceroListNewContactoFiguraTercero.equals(figuraPoder)) {
                        oldIdFiguraPoderOfContactoFiguraTerceroListNewContactoFiguraTercero.getContactoFiguraTerceroList().remove(contactoFiguraTerceroListNewContactoFiguraTercero);
                        oldIdFiguraPoderOfContactoFiguraTerceroListNewContactoFiguraTercero = em.merge(oldIdFiguraPoderOfContactoFiguraTerceroListNewContactoFiguraTercero);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = figuraPoder.getId();
                if (findFiguraPoder(id) == null) {
                    throw new NonexistentEntityException("The figuraPoder with id " + id + " no longer exists.");
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
            FiguraPoder figuraPoder;
            try {
                figuraPoder = em.getReference(FiguraPoder.class, id);
                figuraPoder.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The figuraPoder with id " + id + " no longer exists.", enfe);
            }
            Figura idFigura = figuraPoder.getIdFigura();
            if (idFigura != null) {
                idFigura.getFiguraPoderList().remove(figuraPoder);
                idFigura = em.merge(idFigura);
            }
            List<ContactoFiguraTercero> contactoFiguraTerceroList = figuraPoder.getContactoFiguraTerceroList();
            for (ContactoFiguraTercero contactoFiguraTerceroListContactoFiguraTercero : contactoFiguraTerceroList) {
                contactoFiguraTerceroListContactoFiguraTercero.setIdFiguraPoder(null);
                contactoFiguraTerceroListContactoFiguraTercero = em.merge(contactoFiguraTerceroListContactoFiguraTercero);
            }
            em.remove(figuraPoder);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FiguraPoder> findFiguraPoderEntities() {
        return findFiguraPoderEntities(true, -1, -1);
    }

    public List<FiguraPoder> findFiguraPoderEntities(int maxResults, int firstResult) {
        return findFiguraPoderEntities(false, maxResults, firstResult);
    }

    private List<FiguraPoder> findFiguraPoderEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FiguraPoder.class));
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

    public FiguraPoder findFiguraPoder(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FiguraPoder.class, id);
        } finally {
            em.close();
        }
    }

    public int getFiguraPoderCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FiguraPoder> rt = cq.from(FiguraPoder.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
