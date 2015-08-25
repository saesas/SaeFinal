/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.Credito;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.TipoCredito;
import com.sae.persistence.domain.PagoCredito;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class CreditoJpaController implements Serializable {

    public CreditoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Credito credito) throws PreexistingEntityException, Exception {
        if (credito.getPagoCreditoList() == null) {
            credito.setPagoCreditoList(new ArrayList<PagoCredito>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoCredito idTipo = credito.getIdTipo();
            if (idTipo != null) {
                idTipo = em.getReference(idTipo.getClass(), idTipo.getId());
                credito.setIdTipo(idTipo);
            }
            List<PagoCredito> attachedPagoCreditoList = new ArrayList<PagoCredito>();
            for (PagoCredito pagoCreditoListPagoCreditoToAttach : credito.getPagoCreditoList()) {
                pagoCreditoListPagoCreditoToAttach = em.getReference(pagoCreditoListPagoCreditoToAttach.getClass(), pagoCreditoListPagoCreditoToAttach.getId());
                attachedPagoCreditoList.add(pagoCreditoListPagoCreditoToAttach);
            }
            credito.setPagoCreditoList(attachedPagoCreditoList);
            em.persist(credito);
            if (idTipo != null) {
                idTipo.getCreditoList().add(credito);
                idTipo = em.merge(idTipo);
            }
            for (PagoCredito pagoCreditoListPagoCredito : credito.getPagoCreditoList()) {
                Credito oldIdCreditoOfPagoCreditoListPagoCredito = pagoCreditoListPagoCredito.getIdCredito();
                pagoCreditoListPagoCredito.setIdCredito(credito);
                pagoCreditoListPagoCredito = em.merge(pagoCreditoListPagoCredito);
                if (oldIdCreditoOfPagoCreditoListPagoCredito != null) {
                    oldIdCreditoOfPagoCreditoListPagoCredito.getPagoCreditoList().remove(pagoCreditoListPagoCredito);
                    oldIdCreditoOfPagoCreditoListPagoCredito = em.merge(oldIdCreditoOfPagoCreditoListPagoCredito);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCredito(credito.getId()) != null) {
                throw new PreexistingEntityException("Credito " + credito + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Credito credito) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Credito persistentCredito = em.find(Credito.class, credito.getId());
            TipoCredito idTipoOld = persistentCredito.getIdTipo();
            TipoCredito idTipoNew = credito.getIdTipo();
            List<PagoCredito> pagoCreditoListOld = persistentCredito.getPagoCreditoList();
            List<PagoCredito> pagoCreditoListNew = credito.getPagoCreditoList();
            if (idTipoNew != null) {
                idTipoNew = em.getReference(idTipoNew.getClass(), idTipoNew.getId());
                credito.setIdTipo(idTipoNew);
            }
            List<PagoCredito> attachedPagoCreditoListNew = new ArrayList<PagoCredito>();
            for (PagoCredito pagoCreditoListNewPagoCreditoToAttach : pagoCreditoListNew) {
                pagoCreditoListNewPagoCreditoToAttach = em.getReference(pagoCreditoListNewPagoCreditoToAttach.getClass(), pagoCreditoListNewPagoCreditoToAttach.getId());
                attachedPagoCreditoListNew.add(pagoCreditoListNewPagoCreditoToAttach);
            }
            pagoCreditoListNew = attachedPagoCreditoListNew;
            credito.setPagoCreditoList(pagoCreditoListNew);
            credito = em.merge(credito);
            if (idTipoOld != null && !idTipoOld.equals(idTipoNew)) {
                idTipoOld.getCreditoList().remove(credito);
                idTipoOld = em.merge(idTipoOld);
            }
            if (idTipoNew != null && !idTipoNew.equals(idTipoOld)) {
                idTipoNew.getCreditoList().add(credito);
                idTipoNew = em.merge(idTipoNew);
            }
            for (PagoCredito pagoCreditoListOldPagoCredito : pagoCreditoListOld) {
                if (!pagoCreditoListNew.contains(pagoCreditoListOldPagoCredito)) {
                    pagoCreditoListOldPagoCredito.setIdCredito(null);
                    pagoCreditoListOldPagoCredito = em.merge(pagoCreditoListOldPagoCredito);
                }
            }
            for (PagoCredito pagoCreditoListNewPagoCredito : pagoCreditoListNew) {
                if (!pagoCreditoListOld.contains(pagoCreditoListNewPagoCredito)) {
                    Credito oldIdCreditoOfPagoCreditoListNewPagoCredito = pagoCreditoListNewPagoCredito.getIdCredito();
                    pagoCreditoListNewPagoCredito.setIdCredito(credito);
                    pagoCreditoListNewPagoCredito = em.merge(pagoCreditoListNewPagoCredito);
                    if (oldIdCreditoOfPagoCreditoListNewPagoCredito != null && !oldIdCreditoOfPagoCreditoListNewPagoCredito.equals(credito)) {
                        oldIdCreditoOfPagoCreditoListNewPagoCredito.getPagoCreditoList().remove(pagoCreditoListNewPagoCredito);
                        oldIdCreditoOfPagoCreditoListNewPagoCredito = em.merge(oldIdCreditoOfPagoCreditoListNewPagoCredito);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = credito.getId();
                if (findCredito(id) == null) {
                    throw new NonexistentEntityException("The credito with id " + id + " no longer exists.");
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
            Credito credito;
            try {
                credito = em.getReference(Credito.class, id);
                credito.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The credito with id " + id + " no longer exists.", enfe);
            }
            TipoCredito idTipo = credito.getIdTipo();
            if (idTipo != null) {
                idTipo.getCreditoList().remove(credito);
                idTipo = em.merge(idTipo);
            }
            List<PagoCredito> pagoCreditoList = credito.getPagoCreditoList();
            for (PagoCredito pagoCreditoListPagoCredito : pagoCreditoList) {
                pagoCreditoListPagoCredito.setIdCredito(null);
                pagoCreditoListPagoCredito = em.merge(pagoCreditoListPagoCredito);
            }
            em.remove(credito);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Credito> findCreditoEntities() {
        return findCreditoEntities(true, -1, -1);
    }

    public List<Credito> findCreditoEntities(int maxResults, int firstResult) {
        return findCreditoEntities(false, maxResults, firstResult);
    }

    private List<Credito> findCreditoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Credito.class));
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

    public Credito findCredito(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Credito.class, id);
        } finally {
            em.close();
        }
    }

    public int getCreditoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Credito> rt = cq.from(Credito.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
