/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.FormaProceso;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.SeguimientoProceso;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class FormaProcesoJpaController implements Serializable {

    public FormaProcesoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FormaProceso formaProceso) throws PreexistingEntityException, Exception {
        if (formaProceso.getSeguimientoProcesoList() == null) {
            formaProceso.setSeguimientoProcesoList(new ArrayList<SeguimientoProceso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SeguimientoProceso> attachedSeguimientoProcesoList = new ArrayList<SeguimientoProceso>();
            for (SeguimientoProceso seguimientoProcesoListSeguimientoProcesoToAttach : formaProceso.getSeguimientoProcesoList()) {
                seguimientoProcesoListSeguimientoProcesoToAttach = em.getReference(seguimientoProcesoListSeguimientoProcesoToAttach.getClass(), seguimientoProcesoListSeguimientoProcesoToAttach.getId());
                attachedSeguimientoProcesoList.add(seguimientoProcesoListSeguimientoProcesoToAttach);
            }
            formaProceso.setSeguimientoProcesoList(attachedSeguimientoProcesoList);
            em.persist(formaProceso);
            for (SeguimientoProceso seguimientoProcesoListSeguimientoProceso : formaProceso.getSeguimientoProcesoList()) {
                FormaProceso oldIdFormaOfSeguimientoProcesoListSeguimientoProceso = seguimientoProcesoListSeguimientoProceso.getIdForma();
                seguimientoProcesoListSeguimientoProceso.setIdForma(formaProceso);
                seguimientoProcesoListSeguimientoProceso = em.merge(seguimientoProcesoListSeguimientoProceso);
                if (oldIdFormaOfSeguimientoProcesoListSeguimientoProceso != null) {
                    oldIdFormaOfSeguimientoProcesoListSeguimientoProceso.getSeguimientoProcesoList().remove(seguimientoProcesoListSeguimientoProceso);
                    oldIdFormaOfSeguimientoProcesoListSeguimientoProceso = em.merge(oldIdFormaOfSeguimientoProcesoListSeguimientoProceso);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFormaProceso(formaProceso.getId()) != null) {
                throw new PreexistingEntityException("FormaProceso " + formaProceso + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FormaProceso formaProceso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FormaProceso persistentFormaProceso = em.find(FormaProceso.class, formaProceso.getId());
            List<SeguimientoProceso> seguimientoProcesoListOld = persistentFormaProceso.getSeguimientoProcesoList();
            List<SeguimientoProceso> seguimientoProcesoListNew = formaProceso.getSeguimientoProcesoList();
            List<SeguimientoProceso> attachedSeguimientoProcesoListNew = new ArrayList<SeguimientoProceso>();
            for (SeguimientoProceso seguimientoProcesoListNewSeguimientoProcesoToAttach : seguimientoProcesoListNew) {
                seguimientoProcesoListNewSeguimientoProcesoToAttach = em.getReference(seguimientoProcesoListNewSeguimientoProcesoToAttach.getClass(), seguimientoProcesoListNewSeguimientoProcesoToAttach.getId());
                attachedSeguimientoProcesoListNew.add(seguimientoProcesoListNewSeguimientoProcesoToAttach);
            }
            seguimientoProcesoListNew = attachedSeguimientoProcesoListNew;
            formaProceso.setSeguimientoProcesoList(seguimientoProcesoListNew);
            formaProceso = em.merge(formaProceso);
            for (SeguimientoProceso seguimientoProcesoListOldSeguimientoProceso : seguimientoProcesoListOld) {
                if (!seguimientoProcesoListNew.contains(seguimientoProcesoListOldSeguimientoProceso)) {
                    seguimientoProcesoListOldSeguimientoProceso.setIdForma(null);
                    seguimientoProcesoListOldSeguimientoProceso = em.merge(seguimientoProcesoListOldSeguimientoProceso);
                }
            }
            for (SeguimientoProceso seguimientoProcesoListNewSeguimientoProceso : seguimientoProcesoListNew) {
                if (!seguimientoProcesoListOld.contains(seguimientoProcesoListNewSeguimientoProceso)) {
                    FormaProceso oldIdFormaOfSeguimientoProcesoListNewSeguimientoProceso = seguimientoProcesoListNewSeguimientoProceso.getIdForma();
                    seguimientoProcesoListNewSeguimientoProceso.setIdForma(formaProceso);
                    seguimientoProcesoListNewSeguimientoProceso = em.merge(seguimientoProcesoListNewSeguimientoProceso);
                    if (oldIdFormaOfSeguimientoProcesoListNewSeguimientoProceso != null && !oldIdFormaOfSeguimientoProcesoListNewSeguimientoProceso.equals(formaProceso)) {
                        oldIdFormaOfSeguimientoProcesoListNewSeguimientoProceso.getSeguimientoProcesoList().remove(seguimientoProcesoListNewSeguimientoProceso);
                        oldIdFormaOfSeguimientoProcesoListNewSeguimientoProceso = em.merge(oldIdFormaOfSeguimientoProcesoListNewSeguimientoProceso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = formaProceso.getId();
                if (findFormaProceso(id) == null) {
                    throw new NonexistentEntityException("The formaProceso with id " + id + " no longer exists.");
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
            FormaProceso formaProceso;
            try {
                formaProceso = em.getReference(FormaProceso.class, id);
                formaProceso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The formaProceso with id " + id + " no longer exists.", enfe);
            }
            List<SeguimientoProceso> seguimientoProcesoList = formaProceso.getSeguimientoProcesoList();
            for (SeguimientoProceso seguimientoProcesoListSeguimientoProceso : seguimientoProcesoList) {
                seguimientoProcesoListSeguimientoProceso.setIdForma(null);
                seguimientoProcesoListSeguimientoProceso = em.merge(seguimientoProcesoListSeguimientoProceso);
            }
            em.remove(formaProceso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FormaProceso> findFormaProcesoEntities() {
        return findFormaProcesoEntities(true, -1, -1);
    }

    public List<FormaProceso> findFormaProcesoEntities(int maxResults, int firstResult) {
        return findFormaProcesoEntities(false, maxResults, firstResult);
    }

    private List<FormaProceso> findFormaProcesoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FormaProceso.class));
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

    public FormaProceso findFormaProceso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FormaProceso.class, id);
        } finally {
            em.close();
        }
    }

    public int getFormaProcesoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FormaProceso> rt = cq.from(FormaProceso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
