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
import com.sae.persistence.domain.OrdenCompra;
import com.sae.persistence.domain.AdjuntoCuentaCobro;
import com.sae.persistence.domain.CuentaCobro;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class CuentaCobroJpaController implements Serializable {

    public CuentaCobroJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CuentaCobro cuentaCobro) throws PreexistingEntityException, Exception {
        if (cuentaCobro.getAdjuntoCuentaCobroList() == null) {
            cuentaCobro.setAdjuntoCuentaCobroList(new ArrayList<AdjuntoCuentaCobro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrdenCompra idOrdenCompra = cuentaCobro.getIdOrdenCompra();
            if (idOrdenCompra != null) {
                idOrdenCompra = em.getReference(idOrdenCompra.getClass(), idOrdenCompra.getId());
                cuentaCobro.setIdOrdenCompra(idOrdenCompra);
            }
            List<AdjuntoCuentaCobro> attachedAdjuntoCuentaCobroList = new ArrayList<AdjuntoCuentaCobro>();
            for (AdjuntoCuentaCobro adjuntoCuentaCobroListAdjuntoCuentaCobroToAttach : cuentaCobro.getAdjuntoCuentaCobroList()) {
                adjuntoCuentaCobroListAdjuntoCuentaCobroToAttach = em.getReference(adjuntoCuentaCobroListAdjuntoCuentaCobroToAttach.getClass(), adjuntoCuentaCobroListAdjuntoCuentaCobroToAttach.getId());
                attachedAdjuntoCuentaCobroList.add(adjuntoCuentaCobroListAdjuntoCuentaCobroToAttach);
            }
            cuentaCobro.setAdjuntoCuentaCobroList(attachedAdjuntoCuentaCobroList);
            em.persist(cuentaCobro);
            if (idOrdenCompra != null) {
                idOrdenCompra.getCuentaCobroList().add(cuentaCobro);
                idOrdenCompra = em.merge(idOrdenCompra);
            }
            for (AdjuntoCuentaCobro adjuntoCuentaCobroListAdjuntoCuentaCobro : cuentaCobro.getAdjuntoCuentaCobroList()) {
                CuentaCobro oldIdCuentaCobroOfAdjuntoCuentaCobroListAdjuntoCuentaCobro = adjuntoCuentaCobroListAdjuntoCuentaCobro.getIdCuentaCobro();
                adjuntoCuentaCobroListAdjuntoCuentaCobro.setIdCuentaCobro(cuentaCobro);
                adjuntoCuentaCobroListAdjuntoCuentaCobro = em.merge(adjuntoCuentaCobroListAdjuntoCuentaCobro);
                if (oldIdCuentaCobroOfAdjuntoCuentaCobroListAdjuntoCuentaCobro != null) {
                    oldIdCuentaCobroOfAdjuntoCuentaCobroListAdjuntoCuentaCobro.getAdjuntoCuentaCobroList().remove(adjuntoCuentaCobroListAdjuntoCuentaCobro);
                    oldIdCuentaCobroOfAdjuntoCuentaCobroListAdjuntoCuentaCobro = em.merge(oldIdCuentaCobroOfAdjuntoCuentaCobroListAdjuntoCuentaCobro);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCuentaCobro(cuentaCobro.getId()) != null) {
                throw new PreexistingEntityException("CuentaCobro " + cuentaCobro + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CuentaCobro cuentaCobro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CuentaCobro persistentCuentaCobro = em.find(CuentaCobro.class, cuentaCobro.getId());
            OrdenCompra idOrdenCompraOld = persistentCuentaCobro.getIdOrdenCompra();
            OrdenCompra idOrdenCompraNew = cuentaCobro.getIdOrdenCompra();
            List<AdjuntoCuentaCobro> adjuntoCuentaCobroListOld = persistentCuentaCobro.getAdjuntoCuentaCobroList();
            List<AdjuntoCuentaCobro> adjuntoCuentaCobroListNew = cuentaCobro.getAdjuntoCuentaCobroList();
            if (idOrdenCompraNew != null) {
                idOrdenCompraNew = em.getReference(idOrdenCompraNew.getClass(), idOrdenCompraNew.getId());
                cuentaCobro.setIdOrdenCompra(idOrdenCompraNew);
            }
            List<AdjuntoCuentaCobro> attachedAdjuntoCuentaCobroListNew = new ArrayList<AdjuntoCuentaCobro>();
            for (AdjuntoCuentaCobro adjuntoCuentaCobroListNewAdjuntoCuentaCobroToAttach : adjuntoCuentaCobroListNew) {
                adjuntoCuentaCobroListNewAdjuntoCuentaCobroToAttach = em.getReference(adjuntoCuentaCobroListNewAdjuntoCuentaCobroToAttach.getClass(), adjuntoCuentaCobroListNewAdjuntoCuentaCobroToAttach.getId());
                attachedAdjuntoCuentaCobroListNew.add(adjuntoCuentaCobroListNewAdjuntoCuentaCobroToAttach);
            }
            adjuntoCuentaCobroListNew = attachedAdjuntoCuentaCobroListNew;
            cuentaCobro.setAdjuntoCuentaCobroList(adjuntoCuentaCobroListNew);
            cuentaCobro = em.merge(cuentaCobro);
            if (idOrdenCompraOld != null && !idOrdenCompraOld.equals(idOrdenCompraNew)) {
                idOrdenCompraOld.getCuentaCobroList().remove(cuentaCobro);
                idOrdenCompraOld = em.merge(idOrdenCompraOld);
            }
            if (idOrdenCompraNew != null && !idOrdenCompraNew.equals(idOrdenCompraOld)) {
                idOrdenCompraNew.getCuentaCobroList().add(cuentaCobro);
                idOrdenCompraNew = em.merge(idOrdenCompraNew);
            }
            for (AdjuntoCuentaCobro adjuntoCuentaCobroListOldAdjuntoCuentaCobro : adjuntoCuentaCobroListOld) {
                if (!adjuntoCuentaCobroListNew.contains(adjuntoCuentaCobroListOldAdjuntoCuentaCobro)) {
                    adjuntoCuentaCobroListOldAdjuntoCuentaCobro.setIdCuentaCobro(null);
                    adjuntoCuentaCobroListOldAdjuntoCuentaCobro = em.merge(adjuntoCuentaCobroListOldAdjuntoCuentaCobro);
                }
            }
            for (AdjuntoCuentaCobro adjuntoCuentaCobroListNewAdjuntoCuentaCobro : adjuntoCuentaCobroListNew) {
                if (!adjuntoCuentaCobroListOld.contains(adjuntoCuentaCobroListNewAdjuntoCuentaCobro)) {
                    CuentaCobro oldIdCuentaCobroOfAdjuntoCuentaCobroListNewAdjuntoCuentaCobro = adjuntoCuentaCobroListNewAdjuntoCuentaCobro.getIdCuentaCobro();
                    adjuntoCuentaCobroListNewAdjuntoCuentaCobro.setIdCuentaCobro(cuentaCobro);
                    adjuntoCuentaCobroListNewAdjuntoCuentaCobro = em.merge(adjuntoCuentaCobroListNewAdjuntoCuentaCobro);
                    if (oldIdCuentaCobroOfAdjuntoCuentaCobroListNewAdjuntoCuentaCobro != null && !oldIdCuentaCobroOfAdjuntoCuentaCobroListNewAdjuntoCuentaCobro.equals(cuentaCobro)) {
                        oldIdCuentaCobroOfAdjuntoCuentaCobroListNewAdjuntoCuentaCobro.getAdjuntoCuentaCobroList().remove(adjuntoCuentaCobroListNewAdjuntoCuentaCobro);
                        oldIdCuentaCobroOfAdjuntoCuentaCobroListNewAdjuntoCuentaCobro = em.merge(oldIdCuentaCobroOfAdjuntoCuentaCobroListNewAdjuntoCuentaCobro);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cuentaCobro.getId();
                if (findCuentaCobro(id) == null) {
                    throw new NonexistentEntityException("The cuentaCobro with id " + id + " no longer exists.");
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
            CuentaCobro cuentaCobro;
            try {
                cuentaCobro = em.getReference(CuentaCobro.class, id);
                cuentaCobro.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuentaCobro with id " + id + " no longer exists.", enfe);
            }
            OrdenCompra idOrdenCompra = cuentaCobro.getIdOrdenCompra();
            if (idOrdenCompra != null) {
                idOrdenCompra.getCuentaCobroList().remove(cuentaCobro);
                idOrdenCompra = em.merge(idOrdenCompra);
            }
            List<AdjuntoCuentaCobro> adjuntoCuentaCobroList = cuentaCobro.getAdjuntoCuentaCobroList();
            for (AdjuntoCuentaCobro adjuntoCuentaCobroListAdjuntoCuentaCobro : adjuntoCuentaCobroList) {
                adjuntoCuentaCobroListAdjuntoCuentaCobro.setIdCuentaCobro(null);
                adjuntoCuentaCobroListAdjuntoCuentaCobro = em.merge(adjuntoCuentaCobroListAdjuntoCuentaCobro);
            }
            em.remove(cuentaCobro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CuentaCobro> findCuentaCobroEntities() {
        return findCuentaCobroEntities(true, -1, -1);
    }

    public List<CuentaCobro> findCuentaCobroEntities(int maxResults, int firstResult) {
        return findCuentaCobroEntities(false, maxResults, firstResult);
    }

    private List<CuentaCobro> findCuentaCobroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CuentaCobro.class));
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

    public CuentaCobro findCuentaCobro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CuentaCobro.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuentaCobroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CuentaCobro> rt = cq.from(CuentaCobro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
