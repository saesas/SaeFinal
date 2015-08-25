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
import com.sae.persistence.domain.RazonSocial;
import com.sae.persistence.domain.TipoOrganizacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoOrganizacionJpaController implements Serializable {

    public TipoOrganizacionJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoOrganizacion tipoOrganizacion) throws PreexistingEntityException, Exception {
        if (tipoOrganizacion.getRazonSocialList() == null) {
            tipoOrganizacion.setRazonSocialList(new ArrayList<RazonSocial>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<RazonSocial> attachedRazonSocialList = new ArrayList<RazonSocial>();
            for (RazonSocial razonSocialListRazonSocialToAttach : tipoOrganizacion.getRazonSocialList()) {
                razonSocialListRazonSocialToAttach = em.getReference(razonSocialListRazonSocialToAttach.getClass(), razonSocialListRazonSocialToAttach.getId());
                attachedRazonSocialList.add(razonSocialListRazonSocialToAttach);
            }
            tipoOrganizacion.setRazonSocialList(attachedRazonSocialList);
            em.persist(tipoOrganizacion);
            for (RazonSocial razonSocialListRazonSocial : tipoOrganizacion.getRazonSocialList()) {
                TipoOrganizacion oldTipoOrganizacionOfRazonSocialListRazonSocial = razonSocialListRazonSocial.getTipoOrganizacion();
                razonSocialListRazonSocial.setTipoOrganizacion(tipoOrganizacion);
                razonSocialListRazonSocial = em.merge(razonSocialListRazonSocial);
                if (oldTipoOrganizacionOfRazonSocialListRazonSocial != null) {
                    oldTipoOrganizacionOfRazonSocialListRazonSocial.getRazonSocialList().remove(razonSocialListRazonSocial);
                    oldTipoOrganizacionOfRazonSocialListRazonSocial = em.merge(oldTipoOrganizacionOfRazonSocialListRazonSocial);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoOrganizacion(tipoOrganizacion.getId()) != null) {
                throw new PreexistingEntityException("TipoOrganizacion " + tipoOrganizacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoOrganizacion tipoOrganizacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoOrganizacion persistentTipoOrganizacion = em.find(TipoOrganizacion.class, tipoOrganizacion.getId());
            List<RazonSocial> razonSocialListOld = persistentTipoOrganizacion.getRazonSocialList();
            List<RazonSocial> razonSocialListNew = tipoOrganizacion.getRazonSocialList();
            List<RazonSocial> attachedRazonSocialListNew = new ArrayList<RazonSocial>();
            for (RazonSocial razonSocialListNewRazonSocialToAttach : razonSocialListNew) {
                razonSocialListNewRazonSocialToAttach = em.getReference(razonSocialListNewRazonSocialToAttach.getClass(), razonSocialListNewRazonSocialToAttach.getId());
                attachedRazonSocialListNew.add(razonSocialListNewRazonSocialToAttach);
            }
            razonSocialListNew = attachedRazonSocialListNew;
            tipoOrganizacion.setRazonSocialList(razonSocialListNew);
            tipoOrganizacion = em.merge(tipoOrganizacion);
            for (RazonSocial razonSocialListOldRazonSocial : razonSocialListOld) {
                if (!razonSocialListNew.contains(razonSocialListOldRazonSocial)) {
                    razonSocialListOldRazonSocial.setTipoOrganizacion(null);
                    razonSocialListOldRazonSocial = em.merge(razonSocialListOldRazonSocial);
                }
            }
            for (RazonSocial razonSocialListNewRazonSocial : razonSocialListNew) {
                if (!razonSocialListOld.contains(razonSocialListNewRazonSocial)) {
                    TipoOrganizacion oldTipoOrganizacionOfRazonSocialListNewRazonSocial = razonSocialListNewRazonSocial.getTipoOrganizacion();
                    razonSocialListNewRazonSocial.setTipoOrganizacion(tipoOrganizacion);
                    razonSocialListNewRazonSocial = em.merge(razonSocialListNewRazonSocial);
                    if (oldTipoOrganizacionOfRazonSocialListNewRazonSocial != null && !oldTipoOrganizacionOfRazonSocialListNewRazonSocial.equals(tipoOrganizacion)) {
                        oldTipoOrganizacionOfRazonSocialListNewRazonSocial.getRazonSocialList().remove(razonSocialListNewRazonSocial);
                        oldTipoOrganizacionOfRazonSocialListNewRazonSocial = em.merge(oldTipoOrganizacionOfRazonSocialListNewRazonSocial);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoOrganizacion.getId();
                if (findTipoOrganizacion(id) == null) {
                    throw new NonexistentEntityException("The tipoOrganizacion with id " + id + " no longer exists.");
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
            TipoOrganizacion tipoOrganizacion;
            try {
                tipoOrganizacion = em.getReference(TipoOrganizacion.class, id);
                tipoOrganizacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoOrganizacion with id " + id + " no longer exists.", enfe);
            }
            List<RazonSocial> razonSocialList = tipoOrganizacion.getRazonSocialList();
            for (RazonSocial razonSocialListRazonSocial : razonSocialList) {
                razonSocialListRazonSocial.setTipoOrganizacion(null);
                razonSocialListRazonSocial = em.merge(razonSocialListRazonSocial);
            }
            em.remove(tipoOrganizacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoOrganizacion> findTipoOrganizacionEntities() {
        return findTipoOrganizacionEntities(true, -1, -1);
    }

    public List<TipoOrganizacion> findTipoOrganizacionEntities(int maxResults, int firstResult) {
        return findTipoOrganizacionEntities(false, maxResults, firstResult);
    }

    private List<TipoOrganizacion> findTipoOrganizacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoOrganizacion.class));
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

    public TipoOrganizacion findTipoOrganizacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoOrganizacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoOrganizacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoOrganizacion> rt = cq.from(TipoOrganizacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
