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
import com.sae.persistence.domain.CitacionAudiencia;
import com.sae.persistence.domain.TipoCitacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoCitacionJpaController implements Serializable {

    public TipoCitacionJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoCitacion tipoCitacion) throws PreexistingEntityException, Exception {
        if (tipoCitacion.getCitacionAudienciaList() == null) {
            tipoCitacion.setCitacionAudienciaList(new ArrayList<CitacionAudiencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<CitacionAudiencia> attachedCitacionAudienciaList = new ArrayList<CitacionAudiencia>();
            for (CitacionAudiencia citacionAudienciaListCitacionAudienciaToAttach : tipoCitacion.getCitacionAudienciaList()) {
                citacionAudienciaListCitacionAudienciaToAttach = em.getReference(citacionAudienciaListCitacionAudienciaToAttach.getClass(), citacionAudienciaListCitacionAudienciaToAttach.getId());
                attachedCitacionAudienciaList.add(citacionAudienciaListCitacionAudienciaToAttach);
            }
            tipoCitacion.setCitacionAudienciaList(attachedCitacionAudienciaList);
            em.persist(tipoCitacion);
            for (CitacionAudiencia citacionAudienciaListCitacionAudiencia : tipoCitacion.getCitacionAudienciaList()) {
                TipoCitacion oldIdTipoOfCitacionAudienciaListCitacionAudiencia = citacionAudienciaListCitacionAudiencia.getIdTipo();
                citacionAudienciaListCitacionAudiencia.setIdTipo(tipoCitacion);
                citacionAudienciaListCitacionAudiencia = em.merge(citacionAudienciaListCitacionAudiencia);
                if (oldIdTipoOfCitacionAudienciaListCitacionAudiencia != null) {
                    oldIdTipoOfCitacionAudienciaListCitacionAudiencia.getCitacionAudienciaList().remove(citacionAudienciaListCitacionAudiencia);
                    oldIdTipoOfCitacionAudienciaListCitacionAudiencia = em.merge(oldIdTipoOfCitacionAudienciaListCitacionAudiencia);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoCitacion(tipoCitacion.getId()) != null) {
                throw new PreexistingEntityException("TipoCitacion " + tipoCitacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoCitacion tipoCitacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoCitacion persistentTipoCitacion = em.find(TipoCitacion.class, tipoCitacion.getId());
            List<CitacionAudiencia> citacionAudienciaListOld = persistentTipoCitacion.getCitacionAudienciaList();
            List<CitacionAudiencia> citacionAudienciaListNew = tipoCitacion.getCitacionAudienciaList();
            List<CitacionAudiencia> attachedCitacionAudienciaListNew = new ArrayList<CitacionAudiencia>();
            for (CitacionAudiencia citacionAudienciaListNewCitacionAudienciaToAttach : citacionAudienciaListNew) {
                citacionAudienciaListNewCitacionAudienciaToAttach = em.getReference(citacionAudienciaListNewCitacionAudienciaToAttach.getClass(), citacionAudienciaListNewCitacionAudienciaToAttach.getId());
                attachedCitacionAudienciaListNew.add(citacionAudienciaListNewCitacionAudienciaToAttach);
            }
            citacionAudienciaListNew = attachedCitacionAudienciaListNew;
            tipoCitacion.setCitacionAudienciaList(citacionAudienciaListNew);
            tipoCitacion = em.merge(tipoCitacion);
            for (CitacionAudiencia citacionAudienciaListOldCitacionAudiencia : citacionAudienciaListOld) {
                if (!citacionAudienciaListNew.contains(citacionAudienciaListOldCitacionAudiencia)) {
                    citacionAudienciaListOldCitacionAudiencia.setIdTipo(null);
                    citacionAudienciaListOldCitacionAudiencia = em.merge(citacionAudienciaListOldCitacionAudiencia);
                }
            }
            for (CitacionAudiencia citacionAudienciaListNewCitacionAudiencia : citacionAudienciaListNew) {
                if (!citacionAudienciaListOld.contains(citacionAudienciaListNewCitacionAudiencia)) {
                    TipoCitacion oldIdTipoOfCitacionAudienciaListNewCitacionAudiencia = citacionAudienciaListNewCitacionAudiencia.getIdTipo();
                    citacionAudienciaListNewCitacionAudiencia.setIdTipo(tipoCitacion);
                    citacionAudienciaListNewCitacionAudiencia = em.merge(citacionAudienciaListNewCitacionAudiencia);
                    if (oldIdTipoOfCitacionAudienciaListNewCitacionAudiencia != null && !oldIdTipoOfCitacionAudienciaListNewCitacionAudiencia.equals(tipoCitacion)) {
                        oldIdTipoOfCitacionAudienciaListNewCitacionAudiencia.getCitacionAudienciaList().remove(citacionAudienciaListNewCitacionAudiencia);
                        oldIdTipoOfCitacionAudienciaListNewCitacionAudiencia = em.merge(oldIdTipoOfCitacionAudienciaListNewCitacionAudiencia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoCitacion.getId();
                if (findTipoCitacion(id) == null) {
                    throw new NonexistentEntityException("The tipoCitacion with id " + id + " no longer exists.");
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
            TipoCitacion tipoCitacion;
            try {
                tipoCitacion = em.getReference(TipoCitacion.class, id);
                tipoCitacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoCitacion with id " + id + " no longer exists.", enfe);
            }
            List<CitacionAudiencia> citacionAudienciaList = tipoCitacion.getCitacionAudienciaList();
            for (CitacionAudiencia citacionAudienciaListCitacionAudiencia : citacionAudienciaList) {
                citacionAudienciaListCitacionAudiencia.setIdTipo(null);
                citacionAudienciaListCitacionAudiencia = em.merge(citacionAudienciaListCitacionAudiencia);
            }
            em.remove(tipoCitacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoCitacion> findTipoCitacionEntities() {
        return findTipoCitacionEntities(true, -1, -1);
    }

    public List<TipoCitacion> findTipoCitacionEntities(int maxResults, int firstResult) {
        return findTipoCitacionEntities(false, maxResults, firstResult);
    }

    private List<TipoCitacion> findTipoCitacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoCitacion.class));
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

    public TipoCitacion findTipoCitacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoCitacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoCitacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoCitacion> rt = cq.from(TipoCitacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
