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
import com.sae.persistence.domain.InsumoFacturaCompra;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.AdjuntoFactura;
import com.sae.persistence.domain.FacturaCompra;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class FacturaCompraJpaController implements Serializable {

    public FacturaCompraJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FacturaCompra facturaCompra) throws PreexistingEntityException, Exception {
        if (facturaCompra.getInsumoFacturaCompraList() == null) {
            facturaCompra.setInsumoFacturaCompraList(new ArrayList<InsumoFacturaCompra>());
        }
        if (facturaCompra.getAdjuntoFacturaList() == null) {
            facturaCompra.setAdjuntoFacturaList(new ArrayList<AdjuntoFactura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrdenCompra idOrdenCompra = facturaCompra.getIdOrdenCompra();
            if (idOrdenCompra != null) {
                idOrdenCompra = em.getReference(idOrdenCompra.getClass(), idOrdenCompra.getId());
                facturaCompra.setIdOrdenCompra(idOrdenCompra);
            }
            List<InsumoFacturaCompra> attachedInsumoFacturaCompraList = new ArrayList<InsumoFacturaCompra>();
            for (InsumoFacturaCompra insumoFacturaCompraListInsumoFacturaCompraToAttach : facturaCompra.getInsumoFacturaCompraList()) {
                insumoFacturaCompraListInsumoFacturaCompraToAttach = em.getReference(insumoFacturaCompraListInsumoFacturaCompraToAttach.getClass(), insumoFacturaCompraListInsumoFacturaCompraToAttach.getId());
                attachedInsumoFacturaCompraList.add(insumoFacturaCompraListInsumoFacturaCompraToAttach);
            }
            facturaCompra.setInsumoFacturaCompraList(attachedInsumoFacturaCompraList);
            List<AdjuntoFactura> attachedAdjuntoFacturaList = new ArrayList<AdjuntoFactura>();
            for (AdjuntoFactura adjuntoFacturaListAdjuntoFacturaToAttach : facturaCompra.getAdjuntoFacturaList()) {
                adjuntoFacturaListAdjuntoFacturaToAttach = em.getReference(adjuntoFacturaListAdjuntoFacturaToAttach.getClass(), adjuntoFacturaListAdjuntoFacturaToAttach.getId());
                attachedAdjuntoFacturaList.add(adjuntoFacturaListAdjuntoFacturaToAttach);
            }
            facturaCompra.setAdjuntoFacturaList(attachedAdjuntoFacturaList);
            em.persist(facturaCompra);
            if (idOrdenCompra != null) {
                idOrdenCompra.getFacturaCompraList().add(facturaCompra);
                idOrdenCompra = em.merge(idOrdenCompra);
            }
            for (InsumoFacturaCompra insumoFacturaCompraListInsumoFacturaCompra : facturaCompra.getInsumoFacturaCompraList()) {
                FacturaCompra oldIdFacturaOfInsumoFacturaCompraListInsumoFacturaCompra = insumoFacturaCompraListInsumoFacturaCompra.getIdFactura();
                insumoFacturaCompraListInsumoFacturaCompra.setIdFactura(facturaCompra);
                insumoFacturaCompraListInsumoFacturaCompra = em.merge(insumoFacturaCompraListInsumoFacturaCompra);
                if (oldIdFacturaOfInsumoFacturaCompraListInsumoFacturaCompra != null) {
                    oldIdFacturaOfInsumoFacturaCompraListInsumoFacturaCompra.getInsumoFacturaCompraList().remove(insumoFacturaCompraListInsumoFacturaCompra);
                    oldIdFacturaOfInsumoFacturaCompraListInsumoFacturaCompra = em.merge(oldIdFacturaOfInsumoFacturaCompraListInsumoFacturaCompra);
                }
            }
            for (AdjuntoFactura adjuntoFacturaListAdjuntoFactura : facturaCompra.getAdjuntoFacturaList()) {
                FacturaCompra oldIdFacturaOfAdjuntoFacturaListAdjuntoFactura = adjuntoFacturaListAdjuntoFactura.getIdFactura();
                adjuntoFacturaListAdjuntoFactura.setIdFactura(facturaCompra);
                adjuntoFacturaListAdjuntoFactura = em.merge(adjuntoFacturaListAdjuntoFactura);
                if (oldIdFacturaOfAdjuntoFacturaListAdjuntoFactura != null) {
                    oldIdFacturaOfAdjuntoFacturaListAdjuntoFactura.getAdjuntoFacturaList().remove(adjuntoFacturaListAdjuntoFactura);
                    oldIdFacturaOfAdjuntoFacturaListAdjuntoFactura = em.merge(oldIdFacturaOfAdjuntoFacturaListAdjuntoFactura);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFacturaCompra(facturaCompra.getId()) != null) {
                throw new PreexistingEntityException("FacturaCompra " + facturaCompra + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FacturaCompra facturaCompra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FacturaCompra persistentFacturaCompra = em.find(FacturaCompra.class, facturaCompra.getId());
            OrdenCompra idOrdenCompraOld = persistentFacturaCompra.getIdOrdenCompra();
            OrdenCompra idOrdenCompraNew = facturaCompra.getIdOrdenCompra();
            List<InsumoFacturaCompra> insumoFacturaCompraListOld = persistentFacturaCompra.getInsumoFacturaCompraList();
            List<InsumoFacturaCompra> insumoFacturaCompraListNew = facturaCompra.getInsumoFacturaCompraList();
            List<AdjuntoFactura> adjuntoFacturaListOld = persistentFacturaCompra.getAdjuntoFacturaList();
            List<AdjuntoFactura> adjuntoFacturaListNew = facturaCompra.getAdjuntoFacturaList();
            if (idOrdenCompraNew != null) {
                idOrdenCompraNew = em.getReference(idOrdenCompraNew.getClass(), idOrdenCompraNew.getId());
                facturaCompra.setIdOrdenCompra(idOrdenCompraNew);
            }
            List<InsumoFacturaCompra> attachedInsumoFacturaCompraListNew = new ArrayList<InsumoFacturaCompra>();
            for (InsumoFacturaCompra insumoFacturaCompraListNewInsumoFacturaCompraToAttach : insumoFacturaCompraListNew) {
                insumoFacturaCompraListNewInsumoFacturaCompraToAttach = em.getReference(insumoFacturaCompraListNewInsumoFacturaCompraToAttach.getClass(), insumoFacturaCompraListNewInsumoFacturaCompraToAttach.getId());
                attachedInsumoFacturaCompraListNew.add(insumoFacturaCompraListNewInsumoFacturaCompraToAttach);
            }
            insumoFacturaCompraListNew = attachedInsumoFacturaCompraListNew;
            facturaCompra.setInsumoFacturaCompraList(insumoFacturaCompraListNew);
            List<AdjuntoFactura> attachedAdjuntoFacturaListNew = new ArrayList<AdjuntoFactura>();
            for (AdjuntoFactura adjuntoFacturaListNewAdjuntoFacturaToAttach : adjuntoFacturaListNew) {
                adjuntoFacturaListNewAdjuntoFacturaToAttach = em.getReference(adjuntoFacturaListNewAdjuntoFacturaToAttach.getClass(), adjuntoFacturaListNewAdjuntoFacturaToAttach.getId());
                attachedAdjuntoFacturaListNew.add(adjuntoFacturaListNewAdjuntoFacturaToAttach);
            }
            adjuntoFacturaListNew = attachedAdjuntoFacturaListNew;
            facturaCompra.setAdjuntoFacturaList(adjuntoFacturaListNew);
            facturaCompra = em.merge(facturaCompra);
            if (idOrdenCompraOld != null && !idOrdenCompraOld.equals(idOrdenCompraNew)) {
                idOrdenCompraOld.getFacturaCompraList().remove(facturaCompra);
                idOrdenCompraOld = em.merge(idOrdenCompraOld);
            }
            if (idOrdenCompraNew != null && !idOrdenCompraNew.equals(idOrdenCompraOld)) {
                idOrdenCompraNew.getFacturaCompraList().add(facturaCompra);
                idOrdenCompraNew = em.merge(idOrdenCompraNew);
            }
            for (InsumoFacturaCompra insumoFacturaCompraListOldInsumoFacturaCompra : insumoFacturaCompraListOld) {
                if (!insumoFacturaCompraListNew.contains(insumoFacturaCompraListOldInsumoFacturaCompra)) {
                    insumoFacturaCompraListOldInsumoFacturaCompra.setIdFactura(null);
                    insumoFacturaCompraListOldInsumoFacturaCompra = em.merge(insumoFacturaCompraListOldInsumoFacturaCompra);
                }
            }
            for (InsumoFacturaCompra insumoFacturaCompraListNewInsumoFacturaCompra : insumoFacturaCompraListNew) {
                if (!insumoFacturaCompraListOld.contains(insumoFacturaCompraListNewInsumoFacturaCompra)) {
                    FacturaCompra oldIdFacturaOfInsumoFacturaCompraListNewInsumoFacturaCompra = insumoFacturaCompraListNewInsumoFacturaCompra.getIdFactura();
                    insumoFacturaCompraListNewInsumoFacturaCompra.setIdFactura(facturaCompra);
                    insumoFacturaCompraListNewInsumoFacturaCompra = em.merge(insumoFacturaCompraListNewInsumoFacturaCompra);
                    if (oldIdFacturaOfInsumoFacturaCompraListNewInsumoFacturaCompra != null && !oldIdFacturaOfInsumoFacturaCompraListNewInsumoFacturaCompra.equals(facturaCompra)) {
                        oldIdFacturaOfInsumoFacturaCompraListNewInsumoFacturaCompra.getInsumoFacturaCompraList().remove(insumoFacturaCompraListNewInsumoFacturaCompra);
                        oldIdFacturaOfInsumoFacturaCompraListNewInsumoFacturaCompra = em.merge(oldIdFacturaOfInsumoFacturaCompraListNewInsumoFacturaCompra);
                    }
                }
            }
            for (AdjuntoFactura adjuntoFacturaListOldAdjuntoFactura : adjuntoFacturaListOld) {
                if (!adjuntoFacturaListNew.contains(adjuntoFacturaListOldAdjuntoFactura)) {
                    adjuntoFacturaListOldAdjuntoFactura.setIdFactura(null);
                    adjuntoFacturaListOldAdjuntoFactura = em.merge(adjuntoFacturaListOldAdjuntoFactura);
                }
            }
            for (AdjuntoFactura adjuntoFacturaListNewAdjuntoFactura : adjuntoFacturaListNew) {
                if (!adjuntoFacturaListOld.contains(adjuntoFacturaListNewAdjuntoFactura)) {
                    FacturaCompra oldIdFacturaOfAdjuntoFacturaListNewAdjuntoFactura = adjuntoFacturaListNewAdjuntoFactura.getIdFactura();
                    adjuntoFacturaListNewAdjuntoFactura.setIdFactura(facturaCompra);
                    adjuntoFacturaListNewAdjuntoFactura = em.merge(adjuntoFacturaListNewAdjuntoFactura);
                    if (oldIdFacturaOfAdjuntoFacturaListNewAdjuntoFactura != null && !oldIdFacturaOfAdjuntoFacturaListNewAdjuntoFactura.equals(facturaCompra)) {
                        oldIdFacturaOfAdjuntoFacturaListNewAdjuntoFactura.getAdjuntoFacturaList().remove(adjuntoFacturaListNewAdjuntoFactura);
                        oldIdFacturaOfAdjuntoFacturaListNewAdjuntoFactura = em.merge(oldIdFacturaOfAdjuntoFacturaListNewAdjuntoFactura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = facturaCompra.getId();
                if (findFacturaCompra(id) == null) {
                    throw new NonexistentEntityException("The facturaCompra with id " + id + " no longer exists.");
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
            FacturaCompra facturaCompra;
            try {
                facturaCompra = em.getReference(FacturaCompra.class, id);
                facturaCompra.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facturaCompra with id " + id + " no longer exists.", enfe);
            }
            OrdenCompra idOrdenCompra = facturaCompra.getIdOrdenCompra();
            if (idOrdenCompra != null) {
                idOrdenCompra.getFacturaCompraList().remove(facturaCompra);
                idOrdenCompra = em.merge(idOrdenCompra);
            }
            List<InsumoFacturaCompra> insumoFacturaCompraList = facturaCompra.getInsumoFacturaCompraList();
            for (InsumoFacturaCompra insumoFacturaCompraListInsumoFacturaCompra : insumoFacturaCompraList) {
                insumoFacturaCompraListInsumoFacturaCompra.setIdFactura(null);
                insumoFacturaCompraListInsumoFacturaCompra = em.merge(insumoFacturaCompraListInsumoFacturaCompra);
            }
            List<AdjuntoFactura> adjuntoFacturaList = facturaCompra.getAdjuntoFacturaList();
            for (AdjuntoFactura adjuntoFacturaListAdjuntoFactura : adjuntoFacturaList) {
                adjuntoFacturaListAdjuntoFactura.setIdFactura(null);
                adjuntoFacturaListAdjuntoFactura = em.merge(adjuntoFacturaListAdjuntoFactura);
            }
            em.remove(facturaCompra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FacturaCompra> findFacturaCompraEntities() {
        return findFacturaCompraEntities(true, -1, -1);
    }

    public List<FacturaCompra> findFacturaCompraEntities(int maxResults, int firstResult) {
        return findFacturaCompraEntities(false, maxResults, firstResult);
    }

    private List<FacturaCompra> findFacturaCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FacturaCompra.class));
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

    public FacturaCompra findFacturaCompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FacturaCompra.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FacturaCompra> rt = cq.from(FacturaCompra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
