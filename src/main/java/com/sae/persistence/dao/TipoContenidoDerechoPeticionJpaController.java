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
import com.sae.persistence.domain.ContenidoDerechoPeticion;
import com.sae.persistence.domain.TipoContenidoDerechoPeticion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoContenidoDerechoPeticionJpaController implements Serializable {

    public TipoContenidoDerechoPeticionJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoContenidoDerechoPeticion tipoContenidoDerechoPeticion) throws PreexistingEntityException, Exception {
        if (tipoContenidoDerechoPeticion.getContenidoDerechoPeticionList() == null) {
            tipoContenidoDerechoPeticion.setContenidoDerechoPeticionList(new ArrayList<ContenidoDerechoPeticion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ContenidoDerechoPeticion> attachedContenidoDerechoPeticionList = new ArrayList<ContenidoDerechoPeticion>();
            for (ContenidoDerechoPeticion contenidoDerechoPeticionListContenidoDerechoPeticionToAttach : tipoContenidoDerechoPeticion.getContenidoDerechoPeticionList()) {
                contenidoDerechoPeticionListContenidoDerechoPeticionToAttach = em.getReference(contenidoDerechoPeticionListContenidoDerechoPeticionToAttach.getClass(), contenidoDerechoPeticionListContenidoDerechoPeticionToAttach.getId());
                attachedContenidoDerechoPeticionList.add(contenidoDerechoPeticionListContenidoDerechoPeticionToAttach);
            }
            tipoContenidoDerechoPeticion.setContenidoDerechoPeticionList(attachedContenidoDerechoPeticionList);
            em.persist(tipoContenidoDerechoPeticion);
            for (ContenidoDerechoPeticion contenidoDerechoPeticionListContenidoDerechoPeticion : tipoContenidoDerechoPeticion.getContenidoDerechoPeticionList()) {
                TipoContenidoDerechoPeticion oldIdTipoContenidoOfContenidoDerechoPeticionListContenidoDerechoPeticion = contenidoDerechoPeticionListContenidoDerechoPeticion.getIdTipoContenido();
                contenidoDerechoPeticionListContenidoDerechoPeticion.setIdTipoContenido(tipoContenidoDerechoPeticion);
                contenidoDerechoPeticionListContenidoDerechoPeticion = em.merge(contenidoDerechoPeticionListContenidoDerechoPeticion);
                if (oldIdTipoContenidoOfContenidoDerechoPeticionListContenidoDerechoPeticion != null) {
                    oldIdTipoContenidoOfContenidoDerechoPeticionListContenidoDerechoPeticion.getContenidoDerechoPeticionList().remove(contenidoDerechoPeticionListContenidoDerechoPeticion);
                    oldIdTipoContenidoOfContenidoDerechoPeticionListContenidoDerechoPeticion = em.merge(oldIdTipoContenidoOfContenidoDerechoPeticionListContenidoDerechoPeticion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoContenidoDerechoPeticion(tipoContenidoDerechoPeticion.getId()) != null) {
                throw new PreexistingEntityException("TipoContenidoDerechoPeticion " + tipoContenidoDerechoPeticion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoContenidoDerechoPeticion tipoContenidoDerechoPeticion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoContenidoDerechoPeticion persistentTipoContenidoDerechoPeticion = em.find(TipoContenidoDerechoPeticion.class, tipoContenidoDerechoPeticion.getId());
            List<ContenidoDerechoPeticion> contenidoDerechoPeticionListOld = persistentTipoContenidoDerechoPeticion.getContenidoDerechoPeticionList();
            List<ContenidoDerechoPeticion> contenidoDerechoPeticionListNew = tipoContenidoDerechoPeticion.getContenidoDerechoPeticionList();
            List<ContenidoDerechoPeticion> attachedContenidoDerechoPeticionListNew = new ArrayList<ContenidoDerechoPeticion>();
            for (ContenidoDerechoPeticion contenidoDerechoPeticionListNewContenidoDerechoPeticionToAttach : contenidoDerechoPeticionListNew) {
                contenidoDerechoPeticionListNewContenidoDerechoPeticionToAttach = em.getReference(contenidoDerechoPeticionListNewContenidoDerechoPeticionToAttach.getClass(), contenidoDerechoPeticionListNewContenidoDerechoPeticionToAttach.getId());
                attachedContenidoDerechoPeticionListNew.add(contenidoDerechoPeticionListNewContenidoDerechoPeticionToAttach);
            }
            contenidoDerechoPeticionListNew = attachedContenidoDerechoPeticionListNew;
            tipoContenidoDerechoPeticion.setContenidoDerechoPeticionList(contenidoDerechoPeticionListNew);
            tipoContenidoDerechoPeticion = em.merge(tipoContenidoDerechoPeticion);
            for (ContenidoDerechoPeticion contenidoDerechoPeticionListOldContenidoDerechoPeticion : contenidoDerechoPeticionListOld) {
                if (!contenidoDerechoPeticionListNew.contains(contenidoDerechoPeticionListOldContenidoDerechoPeticion)) {
                    contenidoDerechoPeticionListOldContenidoDerechoPeticion.setIdTipoContenido(null);
                    contenidoDerechoPeticionListOldContenidoDerechoPeticion = em.merge(contenidoDerechoPeticionListOldContenidoDerechoPeticion);
                }
            }
            for (ContenidoDerechoPeticion contenidoDerechoPeticionListNewContenidoDerechoPeticion : contenidoDerechoPeticionListNew) {
                if (!contenidoDerechoPeticionListOld.contains(contenidoDerechoPeticionListNewContenidoDerechoPeticion)) {
                    TipoContenidoDerechoPeticion oldIdTipoContenidoOfContenidoDerechoPeticionListNewContenidoDerechoPeticion = contenidoDerechoPeticionListNewContenidoDerechoPeticion.getIdTipoContenido();
                    contenidoDerechoPeticionListNewContenidoDerechoPeticion.setIdTipoContenido(tipoContenidoDerechoPeticion);
                    contenidoDerechoPeticionListNewContenidoDerechoPeticion = em.merge(contenidoDerechoPeticionListNewContenidoDerechoPeticion);
                    if (oldIdTipoContenidoOfContenidoDerechoPeticionListNewContenidoDerechoPeticion != null && !oldIdTipoContenidoOfContenidoDerechoPeticionListNewContenidoDerechoPeticion.equals(tipoContenidoDerechoPeticion)) {
                        oldIdTipoContenidoOfContenidoDerechoPeticionListNewContenidoDerechoPeticion.getContenidoDerechoPeticionList().remove(contenidoDerechoPeticionListNewContenidoDerechoPeticion);
                        oldIdTipoContenidoOfContenidoDerechoPeticionListNewContenidoDerechoPeticion = em.merge(oldIdTipoContenidoOfContenidoDerechoPeticionListNewContenidoDerechoPeticion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoContenidoDerechoPeticion.getId();
                if (findTipoContenidoDerechoPeticion(id) == null) {
                    throw new NonexistentEntityException("The tipoContenidoDerechoPeticion with id " + id + " no longer exists.");
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
            TipoContenidoDerechoPeticion tipoContenidoDerechoPeticion;
            try {
                tipoContenidoDerechoPeticion = em.getReference(TipoContenidoDerechoPeticion.class, id);
                tipoContenidoDerechoPeticion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoContenidoDerechoPeticion with id " + id + " no longer exists.", enfe);
            }
            List<ContenidoDerechoPeticion> contenidoDerechoPeticionList = tipoContenidoDerechoPeticion.getContenidoDerechoPeticionList();
            for (ContenidoDerechoPeticion contenidoDerechoPeticionListContenidoDerechoPeticion : contenidoDerechoPeticionList) {
                contenidoDerechoPeticionListContenidoDerechoPeticion.setIdTipoContenido(null);
                contenidoDerechoPeticionListContenidoDerechoPeticion = em.merge(contenidoDerechoPeticionListContenidoDerechoPeticion);
            }
            em.remove(tipoContenidoDerechoPeticion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoContenidoDerechoPeticion> findTipoContenidoDerechoPeticionEntities() {
        return findTipoContenidoDerechoPeticionEntities(true, -1, -1);
    }

    public List<TipoContenidoDerechoPeticion> findTipoContenidoDerechoPeticionEntities(int maxResults, int firstResult) {
        return findTipoContenidoDerechoPeticionEntities(false, maxResults, firstResult);
    }

    private List<TipoContenidoDerechoPeticion> findTipoContenidoDerechoPeticionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoContenidoDerechoPeticion.class));
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

    public TipoContenidoDerechoPeticion findTipoContenidoDerechoPeticion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoContenidoDerechoPeticion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoContenidoDerechoPeticionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoContenidoDerechoPeticion> rt = cq.from(TipoContenidoDerechoPeticion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
