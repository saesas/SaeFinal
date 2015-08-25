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
import com.sae.persistence.domain.AdjuntoAfiliacion;
import com.sae.persistence.domain.Afiliacion;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.ContratoTercero;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class AfiliacionJpaController implements Serializable {

    public AfiliacionJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Afiliacion afiliacion) throws PreexistingEntityException, Exception {
        if (afiliacion.getAdjuntoAfiliacionList() == null) {
            afiliacion.setAdjuntoAfiliacionList(new ArrayList<AdjuntoAfiliacion>());
        }
        if (afiliacion.getContratoTerceroList() == null) {
            afiliacion.setContratoTerceroList(new ArrayList<ContratoTercero>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<AdjuntoAfiliacion> attachedAdjuntoAfiliacionList = new ArrayList<AdjuntoAfiliacion>();
            for (AdjuntoAfiliacion adjuntoAfiliacionListAdjuntoAfiliacionToAttach : afiliacion.getAdjuntoAfiliacionList()) {
                adjuntoAfiliacionListAdjuntoAfiliacionToAttach = em.getReference(adjuntoAfiliacionListAdjuntoAfiliacionToAttach.getClass(), adjuntoAfiliacionListAdjuntoAfiliacionToAttach.getId());
                attachedAdjuntoAfiliacionList.add(adjuntoAfiliacionListAdjuntoAfiliacionToAttach);
            }
            afiliacion.setAdjuntoAfiliacionList(attachedAdjuntoAfiliacionList);
            List<ContratoTercero> attachedContratoTerceroList = new ArrayList<ContratoTercero>();
            for (ContratoTercero contratoTerceroListContratoTerceroToAttach : afiliacion.getContratoTerceroList()) {
                contratoTerceroListContratoTerceroToAttach = em.getReference(contratoTerceroListContratoTerceroToAttach.getClass(), contratoTerceroListContratoTerceroToAttach.getId());
                attachedContratoTerceroList.add(contratoTerceroListContratoTerceroToAttach);
            }
            afiliacion.setContratoTerceroList(attachedContratoTerceroList);
            em.persist(afiliacion);
            for (AdjuntoAfiliacion adjuntoAfiliacionListAdjuntoAfiliacion : afiliacion.getAdjuntoAfiliacionList()) {
                Afiliacion oldIdAfiliacionOfAdjuntoAfiliacionListAdjuntoAfiliacion = adjuntoAfiliacionListAdjuntoAfiliacion.getIdAfiliacion();
                adjuntoAfiliacionListAdjuntoAfiliacion.setIdAfiliacion(afiliacion);
                adjuntoAfiliacionListAdjuntoAfiliacion = em.merge(adjuntoAfiliacionListAdjuntoAfiliacion);
                if (oldIdAfiliacionOfAdjuntoAfiliacionListAdjuntoAfiliacion != null) {
                    oldIdAfiliacionOfAdjuntoAfiliacionListAdjuntoAfiliacion.getAdjuntoAfiliacionList().remove(adjuntoAfiliacionListAdjuntoAfiliacion);
                    oldIdAfiliacionOfAdjuntoAfiliacionListAdjuntoAfiliacion = em.merge(oldIdAfiliacionOfAdjuntoAfiliacionListAdjuntoAfiliacion);
                }
            }
            for (ContratoTercero contratoTerceroListContratoTercero : afiliacion.getContratoTerceroList()) {
                Afiliacion oldIdAfiliacionOfContratoTerceroListContratoTercero = contratoTerceroListContratoTercero.getIdAfiliacion();
                contratoTerceroListContratoTercero.setIdAfiliacion(afiliacion);
                contratoTerceroListContratoTercero = em.merge(contratoTerceroListContratoTercero);
                if (oldIdAfiliacionOfContratoTerceroListContratoTercero != null) {
                    oldIdAfiliacionOfContratoTerceroListContratoTercero.getContratoTerceroList().remove(contratoTerceroListContratoTercero);
                    oldIdAfiliacionOfContratoTerceroListContratoTercero = em.merge(oldIdAfiliacionOfContratoTerceroListContratoTercero);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAfiliacion(afiliacion.getId()) != null) {
                throw new PreexistingEntityException("Afiliacion " + afiliacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Afiliacion afiliacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Afiliacion persistentAfiliacion = em.find(Afiliacion.class, afiliacion.getId());
            List<AdjuntoAfiliacion> adjuntoAfiliacionListOld = persistentAfiliacion.getAdjuntoAfiliacionList();
            List<AdjuntoAfiliacion> adjuntoAfiliacionListNew = afiliacion.getAdjuntoAfiliacionList();
            List<ContratoTercero> contratoTerceroListOld = persistentAfiliacion.getContratoTerceroList();
            List<ContratoTercero> contratoTerceroListNew = afiliacion.getContratoTerceroList();
            List<AdjuntoAfiliacion> attachedAdjuntoAfiliacionListNew = new ArrayList<AdjuntoAfiliacion>();
            for (AdjuntoAfiliacion adjuntoAfiliacionListNewAdjuntoAfiliacionToAttach : adjuntoAfiliacionListNew) {
                adjuntoAfiliacionListNewAdjuntoAfiliacionToAttach = em.getReference(adjuntoAfiliacionListNewAdjuntoAfiliacionToAttach.getClass(), adjuntoAfiliacionListNewAdjuntoAfiliacionToAttach.getId());
                attachedAdjuntoAfiliacionListNew.add(adjuntoAfiliacionListNewAdjuntoAfiliacionToAttach);
            }
            adjuntoAfiliacionListNew = attachedAdjuntoAfiliacionListNew;
            afiliacion.setAdjuntoAfiliacionList(adjuntoAfiliacionListNew);
            List<ContratoTercero> attachedContratoTerceroListNew = new ArrayList<ContratoTercero>();
            for (ContratoTercero contratoTerceroListNewContratoTerceroToAttach : contratoTerceroListNew) {
                contratoTerceroListNewContratoTerceroToAttach = em.getReference(contratoTerceroListNewContratoTerceroToAttach.getClass(), contratoTerceroListNewContratoTerceroToAttach.getId());
                attachedContratoTerceroListNew.add(contratoTerceroListNewContratoTerceroToAttach);
            }
            contratoTerceroListNew = attachedContratoTerceroListNew;
            afiliacion.setContratoTerceroList(contratoTerceroListNew);
            afiliacion = em.merge(afiliacion);
            for (AdjuntoAfiliacion adjuntoAfiliacionListOldAdjuntoAfiliacion : adjuntoAfiliacionListOld) {
                if (!adjuntoAfiliacionListNew.contains(adjuntoAfiliacionListOldAdjuntoAfiliacion)) {
                    adjuntoAfiliacionListOldAdjuntoAfiliacion.setIdAfiliacion(null);
                    adjuntoAfiliacionListOldAdjuntoAfiliacion = em.merge(adjuntoAfiliacionListOldAdjuntoAfiliacion);
                }
            }
            for (AdjuntoAfiliacion adjuntoAfiliacionListNewAdjuntoAfiliacion : adjuntoAfiliacionListNew) {
                if (!adjuntoAfiliacionListOld.contains(adjuntoAfiliacionListNewAdjuntoAfiliacion)) {
                    Afiliacion oldIdAfiliacionOfAdjuntoAfiliacionListNewAdjuntoAfiliacion = adjuntoAfiliacionListNewAdjuntoAfiliacion.getIdAfiliacion();
                    adjuntoAfiliacionListNewAdjuntoAfiliacion.setIdAfiliacion(afiliacion);
                    adjuntoAfiliacionListNewAdjuntoAfiliacion = em.merge(adjuntoAfiliacionListNewAdjuntoAfiliacion);
                    if (oldIdAfiliacionOfAdjuntoAfiliacionListNewAdjuntoAfiliacion != null && !oldIdAfiliacionOfAdjuntoAfiliacionListNewAdjuntoAfiliacion.equals(afiliacion)) {
                        oldIdAfiliacionOfAdjuntoAfiliacionListNewAdjuntoAfiliacion.getAdjuntoAfiliacionList().remove(adjuntoAfiliacionListNewAdjuntoAfiliacion);
                        oldIdAfiliacionOfAdjuntoAfiliacionListNewAdjuntoAfiliacion = em.merge(oldIdAfiliacionOfAdjuntoAfiliacionListNewAdjuntoAfiliacion);
                    }
                }
            }
            for (ContratoTercero contratoTerceroListOldContratoTercero : contratoTerceroListOld) {
                if (!contratoTerceroListNew.contains(contratoTerceroListOldContratoTercero)) {
                    contratoTerceroListOldContratoTercero.setIdAfiliacion(null);
                    contratoTerceroListOldContratoTercero = em.merge(contratoTerceroListOldContratoTercero);
                }
            }
            for (ContratoTercero contratoTerceroListNewContratoTercero : contratoTerceroListNew) {
                if (!contratoTerceroListOld.contains(contratoTerceroListNewContratoTercero)) {
                    Afiliacion oldIdAfiliacionOfContratoTerceroListNewContratoTercero = contratoTerceroListNewContratoTercero.getIdAfiliacion();
                    contratoTerceroListNewContratoTercero.setIdAfiliacion(afiliacion);
                    contratoTerceroListNewContratoTercero = em.merge(contratoTerceroListNewContratoTercero);
                    if (oldIdAfiliacionOfContratoTerceroListNewContratoTercero != null && !oldIdAfiliacionOfContratoTerceroListNewContratoTercero.equals(afiliacion)) {
                        oldIdAfiliacionOfContratoTerceroListNewContratoTercero.getContratoTerceroList().remove(contratoTerceroListNewContratoTercero);
                        oldIdAfiliacionOfContratoTerceroListNewContratoTercero = em.merge(oldIdAfiliacionOfContratoTerceroListNewContratoTercero);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = afiliacion.getId();
                if (findAfiliacion(id) == null) {
                    throw new NonexistentEntityException("The afiliacion with id " + id + " no longer exists.");
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
            Afiliacion afiliacion;
            try {
                afiliacion = em.getReference(Afiliacion.class, id);
                afiliacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The afiliacion with id " + id + " no longer exists.", enfe);
            }
            List<AdjuntoAfiliacion> adjuntoAfiliacionList = afiliacion.getAdjuntoAfiliacionList();
            for (AdjuntoAfiliacion adjuntoAfiliacionListAdjuntoAfiliacion : adjuntoAfiliacionList) {
                adjuntoAfiliacionListAdjuntoAfiliacion.setIdAfiliacion(null);
                adjuntoAfiliacionListAdjuntoAfiliacion = em.merge(adjuntoAfiliacionListAdjuntoAfiliacion);
            }
            List<ContratoTercero> contratoTerceroList = afiliacion.getContratoTerceroList();
            for (ContratoTercero contratoTerceroListContratoTercero : contratoTerceroList) {
                contratoTerceroListContratoTercero.setIdAfiliacion(null);
                contratoTerceroListContratoTercero = em.merge(contratoTerceroListContratoTercero);
            }
            em.remove(afiliacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Afiliacion> findAfiliacionEntities() {
        return findAfiliacionEntities(true, -1, -1);
    }

    public List<Afiliacion> findAfiliacionEntities(int maxResults, int firstResult) {
        return findAfiliacionEntities(false, maxResults, firstResult);
    }

    private List<Afiliacion> findAfiliacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Afiliacion.class));
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

    public Afiliacion findAfiliacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Afiliacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getAfiliacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Afiliacion> rt = cq.from(Afiliacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
