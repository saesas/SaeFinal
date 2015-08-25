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
import com.sae.persistence.domain.TipoProyectoConvocatoria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoProyectoConvocatoriaJpaController implements Serializable {

    public TipoProyectoConvocatoriaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoProyectoConvocatoria tipoProyectoConvocatoria) throws PreexistingEntityException, Exception {
        if (tipoProyectoConvocatoria.getConvocatoriaList() == null) {
            tipoProyectoConvocatoria.setConvocatoriaList(new ArrayList<Convocatoria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Convocatoria> attachedConvocatoriaList = new ArrayList<Convocatoria>();
            for (Convocatoria convocatoriaListConvocatoriaToAttach : tipoProyectoConvocatoria.getConvocatoriaList()) {
                convocatoriaListConvocatoriaToAttach = em.getReference(convocatoriaListConvocatoriaToAttach.getClass(), convocatoriaListConvocatoriaToAttach.getId());
                attachedConvocatoriaList.add(convocatoriaListConvocatoriaToAttach);
            }
            tipoProyectoConvocatoria.setConvocatoriaList(attachedConvocatoriaList);
            em.persist(tipoProyectoConvocatoria);
            for (Convocatoria convocatoriaListConvocatoria : tipoProyectoConvocatoria.getConvocatoriaList()) {
                TipoProyectoConvocatoria oldIdTipoProyectoOfConvocatoriaListConvocatoria = convocatoriaListConvocatoria.getIdTipoProyecto();
                convocatoriaListConvocatoria.setIdTipoProyecto(tipoProyectoConvocatoria);
                convocatoriaListConvocatoria = em.merge(convocatoriaListConvocatoria);
                if (oldIdTipoProyectoOfConvocatoriaListConvocatoria != null) {
                    oldIdTipoProyectoOfConvocatoriaListConvocatoria.getConvocatoriaList().remove(convocatoriaListConvocatoria);
                    oldIdTipoProyectoOfConvocatoriaListConvocatoria = em.merge(oldIdTipoProyectoOfConvocatoriaListConvocatoria);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoProyectoConvocatoria(tipoProyectoConvocatoria.getId()) != null) {
                throw new PreexistingEntityException("TipoProyectoConvocatoria " + tipoProyectoConvocatoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoProyectoConvocatoria tipoProyectoConvocatoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoProyectoConvocatoria persistentTipoProyectoConvocatoria = em.find(TipoProyectoConvocatoria.class, tipoProyectoConvocatoria.getId());
            List<Convocatoria> convocatoriaListOld = persistentTipoProyectoConvocatoria.getConvocatoriaList();
            List<Convocatoria> convocatoriaListNew = tipoProyectoConvocatoria.getConvocatoriaList();
            List<Convocatoria> attachedConvocatoriaListNew = new ArrayList<Convocatoria>();
            for (Convocatoria convocatoriaListNewConvocatoriaToAttach : convocatoriaListNew) {
                convocatoriaListNewConvocatoriaToAttach = em.getReference(convocatoriaListNewConvocatoriaToAttach.getClass(), convocatoriaListNewConvocatoriaToAttach.getId());
                attachedConvocatoriaListNew.add(convocatoriaListNewConvocatoriaToAttach);
            }
            convocatoriaListNew = attachedConvocatoriaListNew;
            tipoProyectoConvocatoria.setConvocatoriaList(convocatoriaListNew);
            tipoProyectoConvocatoria = em.merge(tipoProyectoConvocatoria);
            for (Convocatoria convocatoriaListOldConvocatoria : convocatoriaListOld) {
                if (!convocatoriaListNew.contains(convocatoriaListOldConvocatoria)) {
                    convocatoriaListOldConvocatoria.setIdTipoProyecto(null);
                    convocatoriaListOldConvocatoria = em.merge(convocatoriaListOldConvocatoria);
                }
            }
            for (Convocatoria convocatoriaListNewConvocatoria : convocatoriaListNew) {
                if (!convocatoriaListOld.contains(convocatoriaListNewConvocatoria)) {
                    TipoProyectoConvocatoria oldIdTipoProyectoOfConvocatoriaListNewConvocatoria = convocatoriaListNewConvocatoria.getIdTipoProyecto();
                    convocatoriaListNewConvocatoria.setIdTipoProyecto(tipoProyectoConvocatoria);
                    convocatoriaListNewConvocatoria = em.merge(convocatoriaListNewConvocatoria);
                    if (oldIdTipoProyectoOfConvocatoriaListNewConvocatoria != null && !oldIdTipoProyectoOfConvocatoriaListNewConvocatoria.equals(tipoProyectoConvocatoria)) {
                        oldIdTipoProyectoOfConvocatoriaListNewConvocatoria.getConvocatoriaList().remove(convocatoriaListNewConvocatoria);
                        oldIdTipoProyectoOfConvocatoriaListNewConvocatoria = em.merge(oldIdTipoProyectoOfConvocatoriaListNewConvocatoria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoProyectoConvocatoria.getId();
                if (findTipoProyectoConvocatoria(id) == null) {
                    throw new NonexistentEntityException("The tipoProyectoConvocatoria with id " + id + " no longer exists.");
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
            TipoProyectoConvocatoria tipoProyectoConvocatoria;
            try {
                tipoProyectoConvocatoria = em.getReference(TipoProyectoConvocatoria.class, id);
                tipoProyectoConvocatoria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoProyectoConvocatoria with id " + id + " no longer exists.", enfe);
            }
            List<Convocatoria> convocatoriaList = tipoProyectoConvocatoria.getConvocatoriaList();
            for (Convocatoria convocatoriaListConvocatoria : convocatoriaList) {
                convocatoriaListConvocatoria.setIdTipoProyecto(null);
                convocatoriaListConvocatoria = em.merge(convocatoriaListConvocatoria);
            }
            em.remove(tipoProyectoConvocatoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoProyectoConvocatoria> findTipoProyectoConvocatoriaEntities() {
        return findTipoProyectoConvocatoriaEntities(true, -1, -1);
    }

    public List<TipoProyectoConvocatoria> findTipoProyectoConvocatoriaEntities(int maxResults, int firstResult) {
        return findTipoProyectoConvocatoriaEntities(false, maxResults, firstResult);
    }

    private List<TipoProyectoConvocatoria> findTipoProyectoConvocatoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoProyectoConvocatoria.class));
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

    public TipoProyectoConvocatoria findTipoProyectoConvocatoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoProyectoConvocatoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoProyectoConvocatoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoProyectoConvocatoria> rt = cq.from(TipoProyectoConvocatoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
