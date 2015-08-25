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
import com.sae.persistence.domain.TipoTarifaPuc;
import com.sae.persistence.domain.Regimen;
import com.sae.persistence.domain.RegimenTipoTarifaIva;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class RegimenTipoTarifaIvaJpaController implements Serializable {

    public RegimenTipoTarifaIvaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RegimenTipoTarifaIva regimenTipoTarifaIva) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoTarifaPuc idTipoTarifaIva = regimenTipoTarifaIva.getIdTipoTarifaIva();
            if (idTipoTarifaIva != null) {
                idTipoTarifaIva = em.getReference(idTipoTarifaIva.getClass(), idTipoTarifaIva.getId());
                regimenTipoTarifaIva.setIdTipoTarifaIva(idTipoTarifaIva);
            }
            Regimen idRegimen = regimenTipoTarifaIva.getIdRegimen();
            if (idRegimen != null) {
                idRegimen = em.getReference(idRegimen.getClass(), idRegimen.getId());
                regimenTipoTarifaIva.setIdRegimen(idRegimen);
            }
            em.persist(regimenTipoTarifaIva);
            if (idTipoTarifaIva != null) {
                idTipoTarifaIva.getRegimenTipoTarifaIvaList().add(regimenTipoTarifaIva);
                idTipoTarifaIva = em.merge(idTipoTarifaIva);
            }
            if (idRegimen != null) {
                idRegimen.getRegimenTipoTarifaIvaList().add(regimenTipoTarifaIva);
                idRegimen = em.merge(idRegimen);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRegimenTipoTarifaIva(regimenTipoTarifaIva.getId()) != null) {
                throw new PreexistingEntityException("RegimenTipoTarifaIva " + regimenTipoTarifaIva + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RegimenTipoTarifaIva regimenTipoTarifaIva) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RegimenTipoTarifaIva persistentRegimenTipoTarifaIva = em.find(RegimenTipoTarifaIva.class, regimenTipoTarifaIva.getId());
            TipoTarifaPuc idTipoTarifaIvaOld = persistentRegimenTipoTarifaIva.getIdTipoTarifaIva();
            TipoTarifaPuc idTipoTarifaIvaNew = regimenTipoTarifaIva.getIdTipoTarifaIva();
            Regimen idRegimenOld = persistentRegimenTipoTarifaIva.getIdRegimen();
            Regimen idRegimenNew = regimenTipoTarifaIva.getIdRegimen();
            if (idTipoTarifaIvaNew != null) {
                idTipoTarifaIvaNew = em.getReference(idTipoTarifaIvaNew.getClass(), idTipoTarifaIvaNew.getId());
                regimenTipoTarifaIva.setIdTipoTarifaIva(idTipoTarifaIvaNew);
            }
            if (idRegimenNew != null) {
                idRegimenNew = em.getReference(idRegimenNew.getClass(), idRegimenNew.getId());
                regimenTipoTarifaIva.setIdRegimen(idRegimenNew);
            }
            regimenTipoTarifaIva = em.merge(regimenTipoTarifaIva);
            if (idTipoTarifaIvaOld != null && !idTipoTarifaIvaOld.equals(idTipoTarifaIvaNew)) {
                idTipoTarifaIvaOld.getRegimenTipoTarifaIvaList().remove(regimenTipoTarifaIva);
                idTipoTarifaIvaOld = em.merge(idTipoTarifaIvaOld);
            }
            if (idTipoTarifaIvaNew != null && !idTipoTarifaIvaNew.equals(idTipoTarifaIvaOld)) {
                idTipoTarifaIvaNew.getRegimenTipoTarifaIvaList().add(regimenTipoTarifaIva);
                idTipoTarifaIvaNew = em.merge(idTipoTarifaIvaNew);
            }
            if (idRegimenOld != null && !idRegimenOld.equals(idRegimenNew)) {
                idRegimenOld.getRegimenTipoTarifaIvaList().remove(regimenTipoTarifaIva);
                idRegimenOld = em.merge(idRegimenOld);
            }
            if (idRegimenNew != null && !idRegimenNew.equals(idRegimenOld)) {
                idRegimenNew.getRegimenTipoTarifaIvaList().add(regimenTipoTarifaIva);
                idRegimenNew = em.merge(idRegimenNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = regimenTipoTarifaIva.getId();
                if (findRegimenTipoTarifaIva(id) == null) {
                    throw new NonexistentEntityException("The regimenTipoTarifaIva with id " + id + " no longer exists.");
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
            RegimenTipoTarifaIva regimenTipoTarifaIva;
            try {
                regimenTipoTarifaIva = em.getReference(RegimenTipoTarifaIva.class, id);
                regimenTipoTarifaIva.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The regimenTipoTarifaIva with id " + id + " no longer exists.", enfe);
            }
            TipoTarifaPuc idTipoTarifaIva = regimenTipoTarifaIva.getIdTipoTarifaIva();
            if (idTipoTarifaIva != null) {
                idTipoTarifaIva.getRegimenTipoTarifaIvaList().remove(regimenTipoTarifaIva);
                idTipoTarifaIva = em.merge(idTipoTarifaIva);
            }
            Regimen idRegimen = regimenTipoTarifaIva.getIdRegimen();
            if (idRegimen != null) {
                idRegimen.getRegimenTipoTarifaIvaList().remove(regimenTipoTarifaIva);
                idRegimen = em.merge(idRegimen);
            }
            em.remove(regimenTipoTarifaIva);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RegimenTipoTarifaIva> findRegimenTipoTarifaIvaEntities() {
        return findRegimenTipoTarifaIvaEntities(true, -1, -1);
    }

    public List<RegimenTipoTarifaIva> findRegimenTipoTarifaIvaEntities(int maxResults, int firstResult) {
        return findRegimenTipoTarifaIvaEntities(false, maxResults, firstResult);
    }

    private List<RegimenTipoTarifaIva> findRegimenTipoTarifaIvaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RegimenTipoTarifaIva.class));
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

    public RegimenTipoTarifaIva findRegimenTipoTarifaIva(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RegimenTipoTarifaIva.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegimenTipoTarifaIvaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RegimenTipoTarifaIva> rt = cq.from(RegimenTipoTarifaIva.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
