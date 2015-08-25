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
import com.sae.persistence.domain.TipoProcesoConvocatoria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoProcesoConvocatoriaJpaController implements Serializable {

    public TipoProcesoConvocatoriaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoProcesoConvocatoria tipoProcesoConvocatoria) throws PreexistingEntityException, Exception {
        if (tipoProcesoConvocatoria.getConvocatoriaList() == null) {
            tipoProcesoConvocatoria.setConvocatoriaList(new ArrayList<Convocatoria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Convocatoria> attachedConvocatoriaList = new ArrayList<Convocatoria>();
            for (Convocatoria convocatoriaListConvocatoriaToAttach : tipoProcesoConvocatoria.getConvocatoriaList()) {
                convocatoriaListConvocatoriaToAttach = em.getReference(convocatoriaListConvocatoriaToAttach.getClass(), convocatoriaListConvocatoriaToAttach.getId());
                attachedConvocatoriaList.add(convocatoriaListConvocatoriaToAttach);
            }
            tipoProcesoConvocatoria.setConvocatoriaList(attachedConvocatoriaList);
            em.persist(tipoProcesoConvocatoria);
            for (Convocatoria convocatoriaListConvocatoria : tipoProcesoConvocatoria.getConvocatoriaList()) {
                TipoProcesoConvocatoria oldIdTipoProcesoOfConvocatoriaListConvocatoria = convocatoriaListConvocatoria.getIdTipoProceso();
                convocatoriaListConvocatoria.setIdTipoProceso(tipoProcesoConvocatoria);
                convocatoriaListConvocatoria = em.merge(convocatoriaListConvocatoria);
                if (oldIdTipoProcesoOfConvocatoriaListConvocatoria != null) {
                    oldIdTipoProcesoOfConvocatoriaListConvocatoria.getConvocatoriaList().remove(convocatoriaListConvocatoria);
                    oldIdTipoProcesoOfConvocatoriaListConvocatoria = em.merge(oldIdTipoProcesoOfConvocatoriaListConvocatoria);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoProcesoConvocatoria(tipoProcesoConvocatoria.getId()) != null) {
                throw new PreexistingEntityException("TipoProcesoConvocatoria " + tipoProcesoConvocatoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoProcesoConvocatoria tipoProcesoConvocatoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoProcesoConvocatoria persistentTipoProcesoConvocatoria = em.find(TipoProcesoConvocatoria.class, tipoProcesoConvocatoria.getId());
            List<Convocatoria> convocatoriaListOld = persistentTipoProcesoConvocatoria.getConvocatoriaList();
            List<Convocatoria> convocatoriaListNew = tipoProcesoConvocatoria.getConvocatoriaList();
            List<Convocatoria> attachedConvocatoriaListNew = new ArrayList<Convocatoria>();
            for (Convocatoria convocatoriaListNewConvocatoriaToAttach : convocatoriaListNew) {
                convocatoriaListNewConvocatoriaToAttach = em.getReference(convocatoriaListNewConvocatoriaToAttach.getClass(), convocatoriaListNewConvocatoriaToAttach.getId());
                attachedConvocatoriaListNew.add(convocatoriaListNewConvocatoriaToAttach);
            }
            convocatoriaListNew = attachedConvocatoriaListNew;
            tipoProcesoConvocatoria.setConvocatoriaList(convocatoriaListNew);
            tipoProcesoConvocatoria = em.merge(tipoProcesoConvocatoria);
            for (Convocatoria convocatoriaListOldConvocatoria : convocatoriaListOld) {
                if (!convocatoriaListNew.contains(convocatoriaListOldConvocatoria)) {
                    convocatoriaListOldConvocatoria.setIdTipoProceso(null);
                    convocatoriaListOldConvocatoria = em.merge(convocatoriaListOldConvocatoria);
                }
            }
            for (Convocatoria convocatoriaListNewConvocatoria : convocatoriaListNew) {
                if (!convocatoriaListOld.contains(convocatoriaListNewConvocatoria)) {
                    TipoProcesoConvocatoria oldIdTipoProcesoOfConvocatoriaListNewConvocatoria = convocatoriaListNewConvocatoria.getIdTipoProceso();
                    convocatoriaListNewConvocatoria.setIdTipoProceso(tipoProcesoConvocatoria);
                    convocatoriaListNewConvocatoria = em.merge(convocatoriaListNewConvocatoria);
                    if (oldIdTipoProcesoOfConvocatoriaListNewConvocatoria != null && !oldIdTipoProcesoOfConvocatoriaListNewConvocatoria.equals(tipoProcesoConvocatoria)) {
                        oldIdTipoProcesoOfConvocatoriaListNewConvocatoria.getConvocatoriaList().remove(convocatoriaListNewConvocatoria);
                        oldIdTipoProcesoOfConvocatoriaListNewConvocatoria = em.merge(oldIdTipoProcesoOfConvocatoriaListNewConvocatoria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoProcesoConvocatoria.getId();
                if (findTipoProcesoConvocatoria(id) == null) {
                    throw new NonexistentEntityException("The tipoProcesoConvocatoria with id " + id + " no longer exists.");
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
            TipoProcesoConvocatoria tipoProcesoConvocatoria;
            try {
                tipoProcesoConvocatoria = em.getReference(TipoProcesoConvocatoria.class, id);
                tipoProcesoConvocatoria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoProcesoConvocatoria with id " + id + " no longer exists.", enfe);
            }
            List<Convocatoria> convocatoriaList = tipoProcesoConvocatoria.getConvocatoriaList();
            for (Convocatoria convocatoriaListConvocatoria : convocatoriaList) {
                convocatoriaListConvocatoria.setIdTipoProceso(null);
                convocatoriaListConvocatoria = em.merge(convocatoriaListConvocatoria);
            }
            em.remove(tipoProcesoConvocatoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoProcesoConvocatoria> findTipoProcesoConvocatoriaEntities() {
        return findTipoProcesoConvocatoriaEntities(true, -1, -1);
    }

    public List<TipoProcesoConvocatoria> findTipoProcesoConvocatoriaEntities(int maxResults, int firstResult) {
        return findTipoProcesoConvocatoriaEntities(false, maxResults, firstResult);
    }

    private List<TipoProcesoConvocatoria> findTipoProcesoConvocatoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoProcesoConvocatoria.class));
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

    public TipoProcesoConvocatoria findTipoProcesoConvocatoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoProcesoConvocatoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoProcesoConvocatoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoProcesoConvocatoria> rt = cq.from(TipoProcesoConvocatoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
