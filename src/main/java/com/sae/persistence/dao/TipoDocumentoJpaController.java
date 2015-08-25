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
import com.sae.persistence.domain.IdentificacionTercero;
import com.sae.persistence.domain.TipoDocumento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoDocumentoJpaController implements Serializable {

    public TipoDocumentoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoDocumento tipoDocumento) throws PreexistingEntityException, Exception {
        if (tipoDocumento.getIdentificacionTerceroList() == null) {
            tipoDocumento.setIdentificacionTerceroList(new ArrayList<IdentificacionTercero>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<IdentificacionTercero> attachedIdentificacionTerceroList = new ArrayList<IdentificacionTercero>();
            for (IdentificacionTercero identificacionTerceroListIdentificacionTerceroToAttach : tipoDocumento.getIdentificacionTerceroList()) {
                identificacionTerceroListIdentificacionTerceroToAttach = em.getReference(identificacionTerceroListIdentificacionTerceroToAttach.getClass(), identificacionTerceroListIdentificacionTerceroToAttach.getId());
                attachedIdentificacionTerceroList.add(identificacionTerceroListIdentificacionTerceroToAttach);
            }
            tipoDocumento.setIdentificacionTerceroList(attachedIdentificacionTerceroList);
            em.persist(tipoDocumento);
            for (IdentificacionTercero identificacionTerceroListIdentificacionTercero : tipoDocumento.getIdentificacionTerceroList()) {
                TipoDocumento oldIdTipoIdentificacionOfIdentificacionTerceroListIdentificacionTercero = identificacionTerceroListIdentificacionTercero.getIdTipoIdentificacion();
                identificacionTerceroListIdentificacionTercero.setIdTipoIdentificacion(tipoDocumento);
                identificacionTerceroListIdentificacionTercero = em.merge(identificacionTerceroListIdentificacionTercero);
                if (oldIdTipoIdentificacionOfIdentificacionTerceroListIdentificacionTercero != null) {
                    oldIdTipoIdentificacionOfIdentificacionTerceroListIdentificacionTercero.getIdentificacionTerceroList().remove(identificacionTerceroListIdentificacionTercero);
                    oldIdTipoIdentificacionOfIdentificacionTerceroListIdentificacionTercero = em.merge(oldIdTipoIdentificacionOfIdentificacionTerceroListIdentificacionTercero);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoDocumento(tipoDocumento.getId()) != null) {
                throw new PreexistingEntityException("TipoDocumento " + tipoDocumento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoDocumento tipoDocumento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoDocumento persistentTipoDocumento = em.find(TipoDocumento.class, tipoDocumento.getId());
            List<IdentificacionTercero> identificacionTerceroListOld = persistentTipoDocumento.getIdentificacionTerceroList();
            List<IdentificacionTercero> identificacionTerceroListNew = tipoDocumento.getIdentificacionTerceroList();
            List<IdentificacionTercero> attachedIdentificacionTerceroListNew = new ArrayList<IdentificacionTercero>();
            for (IdentificacionTercero identificacionTerceroListNewIdentificacionTerceroToAttach : identificacionTerceroListNew) {
                identificacionTerceroListNewIdentificacionTerceroToAttach = em.getReference(identificacionTerceroListNewIdentificacionTerceroToAttach.getClass(), identificacionTerceroListNewIdentificacionTerceroToAttach.getId());
                attachedIdentificacionTerceroListNew.add(identificacionTerceroListNewIdentificacionTerceroToAttach);
            }
            identificacionTerceroListNew = attachedIdentificacionTerceroListNew;
            tipoDocumento.setIdentificacionTerceroList(identificacionTerceroListNew);
            tipoDocumento = em.merge(tipoDocumento);
            for (IdentificacionTercero identificacionTerceroListOldIdentificacionTercero : identificacionTerceroListOld) {
                if (!identificacionTerceroListNew.contains(identificacionTerceroListOldIdentificacionTercero)) {
                    identificacionTerceroListOldIdentificacionTercero.setIdTipoIdentificacion(null);
                    identificacionTerceroListOldIdentificacionTercero = em.merge(identificacionTerceroListOldIdentificacionTercero);
                }
            }
            for (IdentificacionTercero identificacionTerceroListNewIdentificacionTercero : identificacionTerceroListNew) {
                if (!identificacionTerceroListOld.contains(identificacionTerceroListNewIdentificacionTercero)) {
                    TipoDocumento oldIdTipoIdentificacionOfIdentificacionTerceroListNewIdentificacionTercero = identificacionTerceroListNewIdentificacionTercero.getIdTipoIdentificacion();
                    identificacionTerceroListNewIdentificacionTercero.setIdTipoIdentificacion(tipoDocumento);
                    identificacionTerceroListNewIdentificacionTercero = em.merge(identificacionTerceroListNewIdentificacionTercero);
                    if (oldIdTipoIdentificacionOfIdentificacionTerceroListNewIdentificacionTercero != null && !oldIdTipoIdentificacionOfIdentificacionTerceroListNewIdentificacionTercero.equals(tipoDocumento)) {
                        oldIdTipoIdentificacionOfIdentificacionTerceroListNewIdentificacionTercero.getIdentificacionTerceroList().remove(identificacionTerceroListNewIdentificacionTercero);
                        oldIdTipoIdentificacionOfIdentificacionTerceroListNewIdentificacionTercero = em.merge(oldIdTipoIdentificacionOfIdentificacionTerceroListNewIdentificacionTercero);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoDocumento.getId();
                if (findTipoDocumento(id) == null) {
                    throw new NonexistentEntityException("The tipoDocumento with id " + id + " no longer exists.");
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
            TipoDocumento tipoDocumento;
            try {
                tipoDocumento = em.getReference(TipoDocumento.class, id);
                tipoDocumento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoDocumento with id " + id + " no longer exists.", enfe);
            }
            List<IdentificacionTercero> identificacionTerceroList = tipoDocumento.getIdentificacionTerceroList();
            for (IdentificacionTercero identificacionTerceroListIdentificacionTercero : identificacionTerceroList) {
                identificacionTerceroListIdentificacionTercero.setIdTipoIdentificacion(null);
                identificacionTerceroListIdentificacionTercero = em.merge(identificacionTerceroListIdentificacionTercero);
            }
            em.remove(tipoDocumento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoDocumento> findTipoDocumentoEntities() {
        return findTipoDocumentoEntities(true, -1, -1);
    }

    public List<TipoDocumento> findTipoDocumentoEntities(int maxResults, int firstResult) {
        return findTipoDocumentoEntities(false, maxResults, firstResult);
    }

    private List<TipoDocumento> findTipoDocumentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoDocumento.class));
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

    public TipoDocumento findTipoDocumento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoDocumento.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoDocumentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoDocumento> rt = cq.from(TipoDocumento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
