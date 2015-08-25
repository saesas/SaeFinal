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
import com.sae.persistence.domain.Convocatoria;
import com.sae.persistence.domain.PersonalRequeridoConvocatoria;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class PersonalRequeridoConvocatoriaJpaController implements Serializable {

    public PersonalRequeridoConvocatoriaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PersonalRequeridoConvocatoria personalRequeridoConvocatoria) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Convocatoria idConvocatoria = personalRequeridoConvocatoria.getIdConvocatoria();
            if (idConvocatoria != null) {
                idConvocatoria = em.getReference(idConvocatoria.getClass(), idConvocatoria.getId());
                personalRequeridoConvocatoria.setIdConvocatoria(idConvocatoria);
            }
            em.persist(personalRequeridoConvocatoria);
            if (idConvocatoria != null) {
                idConvocatoria.getPersonalRequeridoConvocatoriaList().add(personalRequeridoConvocatoria);
                idConvocatoria = em.merge(idConvocatoria);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPersonalRequeridoConvocatoria(personalRequeridoConvocatoria.getId()) != null) {
                throw new PreexistingEntityException("PersonalRequeridoConvocatoria " + personalRequeridoConvocatoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PersonalRequeridoConvocatoria personalRequeridoConvocatoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PersonalRequeridoConvocatoria persistentPersonalRequeridoConvocatoria = em.find(PersonalRequeridoConvocatoria.class, personalRequeridoConvocatoria.getId());
            Convocatoria idConvocatoriaOld = persistentPersonalRequeridoConvocatoria.getIdConvocatoria();
            Convocatoria idConvocatoriaNew = personalRequeridoConvocatoria.getIdConvocatoria();
            if (idConvocatoriaNew != null) {
                idConvocatoriaNew = em.getReference(idConvocatoriaNew.getClass(), idConvocatoriaNew.getId());
                personalRequeridoConvocatoria.setIdConvocatoria(idConvocatoriaNew);
            }
            personalRequeridoConvocatoria = em.merge(personalRequeridoConvocatoria);
            if (idConvocatoriaOld != null && !idConvocatoriaOld.equals(idConvocatoriaNew)) {
                idConvocatoriaOld.getPersonalRequeridoConvocatoriaList().remove(personalRequeridoConvocatoria);
                idConvocatoriaOld = em.merge(idConvocatoriaOld);
            }
            if (idConvocatoriaNew != null && !idConvocatoriaNew.equals(idConvocatoriaOld)) {
                idConvocatoriaNew.getPersonalRequeridoConvocatoriaList().add(personalRequeridoConvocatoria);
                idConvocatoriaNew = em.merge(idConvocatoriaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = personalRequeridoConvocatoria.getId();
                if (findPersonalRequeridoConvocatoria(id) == null) {
                    throw new NonexistentEntityException("The personalRequeridoConvocatoria with id " + id + " no longer exists.");
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
            PersonalRequeridoConvocatoria personalRequeridoConvocatoria;
            try {
                personalRequeridoConvocatoria = em.getReference(PersonalRequeridoConvocatoria.class, id);
                personalRequeridoConvocatoria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The personalRequeridoConvocatoria with id " + id + " no longer exists.", enfe);
            }
            Convocatoria idConvocatoria = personalRequeridoConvocatoria.getIdConvocatoria();
            if (idConvocatoria != null) {
                idConvocatoria.getPersonalRequeridoConvocatoriaList().remove(personalRequeridoConvocatoria);
                idConvocatoria = em.merge(idConvocatoria);
            }
            em.remove(personalRequeridoConvocatoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PersonalRequeridoConvocatoria> findPersonalRequeridoConvocatoriaEntities() {
        return findPersonalRequeridoConvocatoriaEntities(true, -1, -1);
    }

    public List<PersonalRequeridoConvocatoria> findPersonalRequeridoConvocatoriaEntities(int maxResults, int firstResult) {
        return findPersonalRequeridoConvocatoriaEntities(false, maxResults, firstResult);
    }

    private List<PersonalRequeridoConvocatoria> findPersonalRequeridoConvocatoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PersonalRequeridoConvocatoria.class));
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

    public PersonalRequeridoConvocatoria findPersonalRequeridoConvocatoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PersonalRequeridoConvocatoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonalRequeridoConvocatoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PersonalRequeridoConvocatoria> rt = cq.from(PersonalRequeridoConvocatoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
