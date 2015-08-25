/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.InformacionSobrePropuesta;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.SobrePropuesta;
import com.sae.persistence.domain.Propuesta;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class InformacionSobrePropuestaJpaController implements Serializable {

    public InformacionSobrePropuestaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InformacionSobrePropuesta informacionSobrePropuesta) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SobrePropuesta idItem = informacionSobrePropuesta.getIdItem();
            if (idItem != null) {
                idItem = em.getReference(idItem.getClass(), idItem.getId());
                informacionSobrePropuesta.setIdItem(idItem);
            }
            Propuesta idPropuesta = informacionSobrePropuesta.getIdPropuesta();
            if (idPropuesta != null) {
                idPropuesta = em.getReference(idPropuesta.getClass(), idPropuesta.getId());
                informacionSobrePropuesta.setIdPropuesta(idPropuesta);
            }
            em.persist(informacionSobrePropuesta);
            if (idItem != null) {
                idItem.getInformacionSobrePropuestaList().add(informacionSobrePropuesta);
                idItem = em.merge(idItem);
            }
            if (idPropuesta != null) {
                idPropuesta.getInformacionSobrePropuestaList().add(informacionSobrePropuesta);
                idPropuesta = em.merge(idPropuesta);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInformacionSobrePropuesta(informacionSobrePropuesta.getId()) != null) {
                throw new PreexistingEntityException("InformacionSobrePropuesta " + informacionSobrePropuesta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InformacionSobrePropuesta informacionSobrePropuesta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InformacionSobrePropuesta persistentInformacionSobrePropuesta = em.find(InformacionSobrePropuesta.class, informacionSobrePropuesta.getId());
            SobrePropuesta idItemOld = persistentInformacionSobrePropuesta.getIdItem();
            SobrePropuesta idItemNew = informacionSobrePropuesta.getIdItem();
            Propuesta idPropuestaOld = persistentInformacionSobrePropuesta.getIdPropuesta();
            Propuesta idPropuestaNew = informacionSobrePropuesta.getIdPropuesta();
            if (idItemNew != null) {
                idItemNew = em.getReference(idItemNew.getClass(), idItemNew.getId());
                informacionSobrePropuesta.setIdItem(idItemNew);
            }
            if (idPropuestaNew != null) {
                idPropuestaNew = em.getReference(idPropuestaNew.getClass(), idPropuestaNew.getId());
                informacionSobrePropuesta.setIdPropuesta(idPropuestaNew);
            }
            informacionSobrePropuesta = em.merge(informacionSobrePropuesta);
            if (idItemOld != null && !idItemOld.equals(idItemNew)) {
                idItemOld.getInformacionSobrePropuestaList().remove(informacionSobrePropuesta);
                idItemOld = em.merge(idItemOld);
            }
            if (idItemNew != null && !idItemNew.equals(idItemOld)) {
                idItemNew.getInformacionSobrePropuestaList().add(informacionSobrePropuesta);
                idItemNew = em.merge(idItemNew);
            }
            if (idPropuestaOld != null && !idPropuestaOld.equals(idPropuestaNew)) {
                idPropuestaOld.getInformacionSobrePropuestaList().remove(informacionSobrePropuesta);
                idPropuestaOld = em.merge(idPropuestaOld);
            }
            if (idPropuestaNew != null && !idPropuestaNew.equals(idPropuestaOld)) {
                idPropuestaNew.getInformacionSobrePropuestaList().add(informacionSobrePropuesta);
                idPropuestaNew = em.merge(idPropuestaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = informacionSobrePropuesta.getId();
                if (findInformacionSobrePropuesta(id) == null) {
                    throw new NonexistentEntityException("The informacionSobrePropuesta with id " + id + " no longer exists.");
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
            InformacionSobrePropuesta informacionSobrePropuesta;
            try {
                informacionSobrePropuesta = em.getReference(InformacionSobrePropuesta.class, id);
                informacionSobrePropuesta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The informacionSobrePropuesta with id " + id + " no longer exists.", enfe);
            }
            SobrePropuesta idItem = informacionSobrePropuesta.getIdItem();
            if (idItem != null) {
                idItem.getInformacionSobrePropuestaList().remove(informacionSobrePropuesta);
                idItem = em.merge(idItem);
            }
            Propuesta idPropuesta = informacionSobrePropuesta.getIdPropuesta();
            if (idPropuesta != null) {
                idPropuesta.getInformacionSobrePropuestaList().remove(informacionSobrePropuesta);
                idPropuesta = em.merge(idPropuesta);
            }
            em.remove(informacionSobrePropuesta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<InformacionSobrePropuesta> findInformacionSobrePropuestaEntities() {
        return findInformacionSobrePropuestaEntities(true, -1, -1);
    }

    public List<InformacionSobrePropuesta> findInformacionSobrePropuestaEntities(int maxResults, int firstResult) {
        return findInformacionSobrePropuestaEntities(false, maxResults, firstResult);
    }

    private List<InformacionSobrePropuesta> findInformacionSobrePropuestaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InformacionSobrePropuesta.class));
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

    public InformacionSobrePropuesta findInformacionSobrePropuesta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InformacionSobrePropuesta.class, id);
        } finally {
            em.close();
        }
    }

    public int getInformacionSobrePropuestaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InformacionSobrePropuesta> rt = cq.from(InformacionSobrePropuesta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
