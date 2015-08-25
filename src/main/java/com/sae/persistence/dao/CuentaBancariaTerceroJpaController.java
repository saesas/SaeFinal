/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.CuentaBancariaTercero;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.TipoCuentaBancaria;
import com.sae.persistence.domain.Tercero;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class CuentaBancariaTerceroJpaController implements Serializable {

    public CuentaBancariaTerceroJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CuentaBancariaTercero cuentaBancariaTercero) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoCuentaBancaria idTipoCuenta = cuentaBancariaTercero.getIdTipoCuenta();
            if (idTipoCuenta != null) {
                idTipoCuenta = em.getReference(idTipoCuenta.getClass(), idTipoCuenta.getId());
                cuentaBancariaTercero.setIdTipoCuenta(idTipoCuenta);
            }
            Tercero idTercero = cuentaBancariaTercero.getIdTercero();
            if (idTercero != null) {
                idTercero = em.getReference(idTercero.getClass(), idTercero.getId());
                cuentaBancariaTercero.setIdTercero(idTercero);
            }
            Tercero idNombreBanco = cuentaBancariaTercero.getIdNombreBanco();
            if (idNombreBanco != null) {
                idNombreBanco = em.getReference(idNombreBanco.getClass(), idNombreBanco.getId());
                cuentaBancariaTercero.setIdNombreBanco(idNombreBanco);
            }
            em.persist(cuentaBancariaTercero);
            if (idTipoCuenta != null) {
                idTipoCuenta.getCuentaBancariaTerceroList().add(cuentaBancariaTercero);
                idTipoCuenta = em.merge(idTipoCuenta);
            }
            if (idTercero != null) {
                idTercero.getCuentaBancariaTerceroList().add(cuentaBancariaTercero);
                idTercero = em.merge(idTercero);
            }
            if (idNombreBanco != null) {
                idNombreBanco.getCuentaBancariaTerceroList().add(cuentaBancariaTercero);
                idNombreBanco = em.merge(idNombreBanco);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCuentaBancariaTercero(cuentaBancariaTercero.getId()) != null) {
                throw new PreexistingEntityException("CuentaBancariaTercero " + cuentaBancariaTercero + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CuentaBancariaTercero cuentaBancariaTercero) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CuentaBancariaTercero persistentCuentaBancariaTercero = em.find(CuentaBancariaTercero.class, cuentaBancariaTercero.getId());
            TipoCuentaBancaria idTipoCuentaOld = persistentCuentaBancariaTercero.getIdTipoCuenta();
            TipoCuentaBancaria idTipoCuentaNew = cuentaBancariaTercero.getIdTipoCuenta();
            Tercero idTerceroOld = persistentCuentaBancariaTercero.getIdTercero();
            Tercero idTerceroNew = cuentaBancariaTercero.getIdTercero();
            Tercero idNombreBancoOld = persistentCuentaBancariaTercero.getIdNombreBanco();
            Tercero idNombreBancoNew = cuentaBancariaTercero.getIdNombreBanco();
            if (idTipoCuentaNew != null) {
                idTipoCuentaNew = em.getReference(idTipoCuentaNew.getClass(), idTipoCuentaNew.getId());
                cuentaBancariaTercero.setIdTipoCuenta(idTipoCuentaNew);
            }
            if (idTerceroNew != null) {
                idTerceroNew = em.getReference(idTerceroNew.getClass(), idTerceroNew.getId());
                cuentaBancariaTercero.setIdTercero(idTerceroNew);
            }
            if (idNombreBancoNew != null) {
                idNombreBancoNew = em.getReference(idNombreBancoNew.getClass(), idNombreBancoNew.getId());
                cuentaBancariaTercero.setIdNombreBanco(idNombreBancoNew);
            }
            cuentaBancariaTercero = em.merge(cuentaBancariaTercero);
            if (idTipoCuentaOld != null && !idTipoCuentaOld.equals(idTipoCuentaNew)) {
                idTipoCuentaOld.getCuentaBancariaTerceroList().remove(cuentaBancariaTercero);
                idTipoCuentaOld = em.merge(idTipoCuentaOld);
            }
            if (idTipoCuentaNew != null && !idTipoCuentaNew.equals(idTipoCuentaOld)) {
                idTipoCuentaNew.getCuentaBancariaTerceroList().add(cuentaBancariaTercero);
                idTipoCuentaNew = em.merge(idTipoCuentaNew);
            }
            if (idTerceroOld != null && !idTerceroOld.equals(idTerceroNew)) {
                idTerceroOld.getCuentaBancariaTerceroList().remove(cuentaBancariaTercero);
                idTerceroOld = em.merge(idTerceroOld);
            }
            if (idTerceroNew != null && !idTerceroNew.equals(idTerceroOld)) {
                idTerceroNew.getCuentaBancariaTerceroList().add(cuentaBancariaTercero);
                idTerceroNew = em.merge(idTerceroNew);
            }
            if (idNombreBancoOld != null && !idNombreBancoOld.equals(idNombreBancoNew)) {
                idNombreBancoOld.getCuentaBancariaTerceroList().remove(cuentaBancariaTercero);
                idNombreBancoOld = em.merge(idNombreBancoOld);
            }
            if (idNombreBancoNew != null && !idNombreBancoNew.equals(idNombreBancoOld)) {
                idNombreBancoNew.getCuentaBancariaTerceroList().add(cuentaBancariaTercero);
                idNombreBancoNew = em.merge(idNombreBancoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cuentaBancariaTercero.getId();
                if (findCuentaBancariaTercero(id) == null) {
                    throw new NonexistentEntityException("The cuentaBancariaTercero with id " + id + " no longer exists.");
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
            CuentaBancariaTercero cuentaBancariaTercero;
            try {
                cuentaBancariaTercero = em.getReference(CuentaBancariaTercero.class, id);
                cuentaBancariaTercero.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuentaBancariaTercero with id " + id + " no longer exists.", enfe);
            }
            TipoCuentaBancaria idTipoCuenta = cuentaBancariaTercero.getIdTipoCuenta();
            if (idTipoCuenta != null) {
                idTipoCuenta.getCuentaBancariaTerceroList().remove(cuentaBancariaTercero);
                idTipoCuenta = em.merge(idTipoCuenta);
            }
            Tercero idTercero = cuentaBancariaTercero.getIdTercero();
            if (idTercero != null) {
                idTercero.getCuentaBancariaTerceroList().remove(cuentaBancariaTercero);
                idTercero = em.merge(idTercero);
            }
            Tercero idNombreBanco = cuentaBancariaTercero.getIdNombreBanco();
            if (idNombreBanco != null) {
                idNombreBanco.getCuentaBancariaTerceroList().remove(cuentaBancariaTercero);
                idNombreBanco = em.merge(idNombreBanco);
            }
            em.remove(cuentaBancariaTercero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CuentaBancariaTercero> findCuentaBancariaTerceroEntities() {
        return findCuentaBancariaTerceroEntities(true, -1, -1);
    }

    public List<CuentaBancariaTercero> findCuentaBancariaTerceroEntities(int maxResults, int firstResult) {
        return findCuentaBancariaTerceroEntities(false, maxResults, firstResult);
    }

    private List<CuentaBancariaTercero> findCuentaBancariaTerceroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CuentaBancariaTercero.class));
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

    public CuentaBancariaTercero findCuentaBancariaTercero(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CuentaBancariaTercero.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuentaBancariaTerceroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CuentaBancariaTercero> rt = cq.from(CuentaBancariaTercero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
