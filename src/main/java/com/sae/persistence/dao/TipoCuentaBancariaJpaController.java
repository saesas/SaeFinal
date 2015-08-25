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
import com.sae.persistence.domain.CuentaBancariaTercero;
import com.sae.persistence.domain.TipoCuentaBancaria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoCuentaBancariaJpaController implements Serializable {

    public TipoCuentaBancariaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoCuentaBancaria tipoCuentaBancaria) throws PreexistingEntityException, Exception {
        if (tipoCuentaBancaria.getCuentaBancariaTerceroList() == null) {
            tipoCuentaBancaria.setCuentaBancariaTerceroList(new ArrayList<CuentaBancariaTercero>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<CuentaBancariaTercero> attachedCuentaBancariaTerceroList = new ArrayList<CuentaBancariaTercero>();
            for (CuentaBancariaTercero cuentaBancariaTerceroListCuentaBancariaTerceroToAttach : tipoCuentaBancaria.getCuentaBancariaTerceroList()) {
                cuentaBancariaTerceroListCuentaBancariaTerceroToAttach = em.getReference(cuentaBancariaTerceroListCuentaBancariaTerceroToAttach.getClass(), cuentaBancariaTerceroListCuentaBancariaTerceroToAttach.getId());
                attachedCuentaBancariaTerceroList.add(cuentaBancariaTerceroListCuentaBancariaTerceroToAttach);
            }
            tipoCuentaBancaria.setCuentaBancariaTerceroList(attachedCuentaBancariaTerceroList);
            em.persist(tipoCuentaBancaria);
            for (CuentaBancariaTercero cuentaBancariaTerceroListCuentaBancariaTercero : tipoCuentaBancaria.getCuentaBancariaTerceroList()) {
                TipoCuentaBancaria oldIdTipoCuentaOfCuentaBancariaTerceroListCuentaBancariaTercero = cuentaBancariaTerceroListCuentaBancariaTercero.getIdTipoCuenta();
                cuentaBancariaTerceroListCuentaBancariaTercero.setIdTipoCuenta(tipoCuentaBancaria);
                cuentaBancariaTerceroListCuentaBancariaTercero = em.merge(cuentaBancariaTerceroListCuentaBancariaTercero);
                if (oldIdTipoCuentaOfCuentaBancariaTerceroListCuentaBancariaTercero != null) {
                    oldIdTipoCuentaOfCuentaBancariaTerceroListCuentaBancariaTercero.getCuentaBancariaTerceroList().remove(cuentaBancariaTerceroListCuentaBancariaTercero);
                    oldIdTipoCuentaOfCuentaBancariaTerceroListCuentaBancariaTercero = em.merge(oldIdTipoCuentaOfCuentaBancariaTerceroListCuentaBancariaTercero);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoCuentaBancaria(tipoCuentaBancaria.getId()) != null) {
                throw new PreexistingEntityException("TipoCuentaBancaria " + tipoCuentaBancaria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoCuentaBancaria tipoCuentaBancaria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoCuentaBancaria persistentTipoCuentaBancaria = em.find(TipoCuentaBancaria.class, tipoCuentaBancaria.getId());
            List<CuentaBancariaTercero> cuentaBancariaTerceroListOld = persistentTipoCuentaBancaria.getCuentaBancariaTerceroList();
            List<CuentaBancariaTercero> cuentaBancariaTerceroListNew = tipoCuentaBancaria.getCuentaBancariaTerceroList();
            List<CuentaBancariaTercero> attachedCuentaBancariaTerceroListNew = new ArrayList<CuentaBancariaTercero>();
            for (CuentaBancariaTercero cuentaBancariaTerceroListNewCuentaBancariaTerceroToAttach : cuentaBancariaTerceroListNew) {
                cuentaBancariaTerceroListNewCuentaBancariaTerceroToAttach = em.getReference(cuentaBancariaTerceroListNewCuentaBancariaTerceroToAttach.getClass(), cuentaBancariaTerceroListNewCuentaBancariaTerceroToAttach.getId());
                attachedCuentaBancariaTerceroListNew.add(cuentaBancariaTerceroListNewCuentaBancariaTerceroToAttach);
            }
            cuentaBancariaTerceroListNew = attachedCuentaBancariaTerceroListNew;
            tipoCuentaBancaria.setCuentaBancariaTerceroList(cuentaBancariaTerceroListNew);
            tipoCuentaBancaria = em.merge(tipoCuentaBancaria);
            for (CuentaBancariaTercero cuentaBancariaTerceroListOldCuentaBancariaTercero : cuentaBancariaTerceroListOld) {
                if (!cuentaBancariaTerceroListNew.contains(cuentaBancariaTerceroListOldCuentaBancariaTercero)) {
                    cuentaBancariaTerceroListOldCuentaBancariaTercero.setIdTipoCuenta(null);
                    cuentaBancariaTerceroListOldCuentaBancariaTercero = em.merge(cuentaBancariaTerceroListOldCuentaBancariaTercero);
                }
            }
            for (CuentaBancariaTercero cuentaBancariaTerceroListNewCuentaBancariaTercero : cuentaBancariaTerceroListNew) {
                if (!cuentaBancariaTerceroListOld.contains(cuentaBancariaTerceroListNewCuentaBancariaTercero)) {
                    TipoCuentaBancaria oldIdTipoCuentaOfCuentaBancariaTerceroListNewCuentaBancariaTercero = cuentaBancariaTerceroListNewCuentaBancariaTercero.getIdTipoCuenta();
                    cuentaBancariaTerceroListNewCuentaBancariaTercero.setIdTipoCuenta(tipoCuentaBancaria);
                    cuentaBancariaTerceroListNewCuentaBancariaTercero = em.merge(cuentaBancariaTerceroListNewCuentaBancariaTercero);
                    if (oldIdTipoCuentaOfCuentaBancariaTerceroListNewCuentaBancariaTercero != null && !oldIdTipoCuentaOfCuentaBancariaTerceroListNewCuentaBancariaTercero.equals(tipoCuentaBancaria)) {
                        oldIdTipoCuentaOfCuentaBancariaTerceroListNewCuentaBancariaTercero.getCuentaBancariaTerceroList().remove(cuentaBancariaTerceroListNewCuentaBancariaTercero);
                        oldIdTipoCuentaOfCuentaBancariaTerceroListNewCuentaBancariaTercero = em.merge(oldIdTipoCuentaOfCuentaBancariaTerceroListNewCuentaBancariaTercero);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoCuentaBancaria.getId();
                if (findTipoCuentaBancaria(id) == null) {
                    throw new NonexistentEntityException("The tipoCuentaBancaria with id " + id + " no longer exists.");
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
            TipoCuentaBancaria tipoCuentaBancaria;
            try {
                tipoCuentaBancaria = em.getReference(TipoCuentaBancaria.class, id);
                tipoCuentaBancaria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoCuentaBancaria with id " + id + " no longer exists.", enfe);
            }
            List<CuentaBancariaTercero> cuentaBancariaTerceroList = tipoCuentaBancaria.getCuentaBancariaTerceroList();
            for (CuentaBancariaTercero cuentaBancariaTerceroListCuentaBancariaTercero : cuentaBancariaTerceroList) {
                cuentaBancariaTerceroListCuentaBancariaTercero.setIdTipoCuenta(null);
                cuentaBancariaTerceroListCuentaBancariaTercero = em.merge(cuentaBancariaTerceroListCuentaBancariaTercero);
            }
            em.remove(tipoCuentaBancaria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoCuentaBancaria> findTipoCuentaBancariaEntities() {
        return findTipoCuentaBancariaEntities(true, -1, -1);
    }

    public List<TipoCuentaBancaria> findTipoCuentaBancariaEntities(int maxResults, int firstResult) {
        return findTipoCuentaBancariaEntities(false, maxResults, firstResult);
    }

    private List<TipoCuentaBancaria> findTipoCuentaBancariaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoCuentaBancaria.class));
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

    public TipoCuentaBancaria findTipoCuentaBancaria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoCuentaBancaria.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoCuentaBancariaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoCuentaBancaria> rt = cq.from(TipoCuentaBancaria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
