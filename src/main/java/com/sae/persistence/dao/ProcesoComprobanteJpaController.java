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
import com.sae.persistence.domain.Comprobante;
import com.sae.persistence.domain.ProcesoComprobante;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ProcesoComprobanteJpaController implements Serializable {

    public ProcesoComprobanteJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProcesoComprobante procesoComprobante) throws PreexistingEntityException, Exception {
        if (procesoComprobante.getComprobanteList() == null) {
            procesoComprobante.setComprobanteList(new ArrayList<Comprobante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Comprobante> attachedComprobanteList = new ArrayList<Comprobante>();
            for (Comprobante comprobanteListComprobanteToAttach : procesoComprobante.getComprobanteList()) {
                comprobanteListComprobanteToAttach = em.getReference(comprobanteListComprobanteToAttach.getClass(), comprobanteListComprobanteToAttach.getId());
                attachedComprobanteList.add(comprobanteListComprobanteToAttach);
            }
            procesoComprobante.setComprobanteList(attachedComprobanteList);
            em.persist(procesoComprobante);
            for (Comprobante comprobanteListComprobante : procesoComprobante.getComprobanteList()) {
                ProcesoComprobante oldIdProcesoOfComprobanteListComprobante = comprobanteListComprobante.getIdProceso();
                comprobanteListComprobante.setIdProceso(procesoComprobante);
                comprobanteListComprobante = em.merge(comprobanteListComprobante);
                if (oldIdProcesoOfComprobanteListComprobante != null) {
                    oldIdProcesoOfComprobanteListComprobante.getComprobanteList().remove(comprobanteListComprobante);
                    oldIdProcesoOfComprobanteListComprobante = em.merge(oldIdProcesoOfComprobanteListComprobante);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProcesoComprobante(procesoComprobante.getId()) != null) {
                throw new PreexistingEntityException("ProcesoComprobante " + procesoComprobante + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProcesoComprobante procesoComprobante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProcesoComprobante persistentProcesoComprobante = em.find(ProcesoComprobante.class, procesoComprobante.getId());
            List<Comprobante> comprobanteListOld = persistentProcesoComprobante.getComprobanteList();
            List<Comprobante> comprobanteListNew = procesoComprobante.getComprobanteList();
            List<Comprobante> attachedComprobanteListNew = new ArrayList<Comprobante>();
            for (Comprobante comprobanteListNewComprobanteToAttach : comprobanteListNew) {
                comprobanteListNewComprobanteToAttach = em.getReference(comprobanteListNewComprobanteToAttach.getClass(), comprobanteListNewComprobanteToAttach.getId());
                attachedComprobanteListNew.add(comprobanteListNewComprobanteToAttach);
            }
            comprobanteListNew = attachedComprobanteListNew;
            procesoComprobante.setComprobanteList(comprobanteListNew);
            procesoComprobante = em.merge(procesoComprobante);
            for (Comprobante comprobanteListOldComprobante : comprobanteListOld) {
                if (!comprobanteListNew.contains(comprobanteListOldComprobante)) {
                    comprobanteListOldComprobante.setIdProceso(null);
                    comprobanteListOldComprobante = em.merge(comprobanteListOldComprobante);
                }
            }
            for (Comprobante comprobanteListNewComprobante : comprobanteListNew) {
                if (!comprobanteListOld.contains(comprobanteListNewComprobante)) {
                    ProcesoComprobante oldIdProcesoOfComprobanteListNewComprobante = comprobanteListNewComprobante.getIdProceso();
                    comprobanteListNewComprobante.setIdProceso(procesoComprobante);
                    comprobanteListNewComprobante = em.merge(comprobanteListNewComprobante);
                    if (oldIdProcesoOfComprobanteListNewComprobante != null && !oldIdProcesoOfComprobanteListNewComprobante.equals(procesoComprobante)) {
                        oldIdProcesoOfComprobanteListNewComprobante.getComprobanteList().remove(comprobanteListNewComprobante);
                        oldIdProcesoOfComprobanteListNewComprobante = em.merge(oldIdProcesoOfComprobanteListNewComprobante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = procesoComprobante.getId();
                if (findProcesoComprobante(id) == null) {
                    throw new NonexistentEntityException("The procesoComprobante with id " + id + " no longer exists.");
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
            ProcesoComprobante procesoComprobante;
            try {
                procesoComprobante = em.getReference(ProcesoComprobante.class, id);
                procesoComprobante.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The procesoComprobante with id " + id + " no longer exists.", enfe);
            }
            List<Comprobante> comprobanteList = procesoComprobante.getComprobanteList();
            for (Comprobante comprobanteListComprobante : comprobanteList) {
                comprobanteListComprobante.setIdProceso(null);
                comprobanteListComprobante = em.merge(comprobanteListComprobante);
            }
            em.remove(procesoComprobante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProcesoComprobante> findProcesoComprobanteEntities() {
        return findProcesoComprobanteEntities(true, -1, -1);
    }

    public List<ProcesoComprobante> findProcesoComprobanteEntities(int maxResults, int firstResult) {
        return findProcesoComprobanteEntities(false, maxResults, firstResult);
    }

    private List<ProcesoComprobante> findProcesoComprobanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProcesoComprobante.class));
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

    public ProcesoComprobante findProcesoComprobante(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProcesoComprobante.class, id);
        } finally {
            em.close();
        }
    }

    public int getProcesoComprobanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProcesoComprobante> rt = cq.from(ProcesoComprobante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
