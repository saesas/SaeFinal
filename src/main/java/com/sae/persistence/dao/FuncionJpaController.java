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
import com.sae.persistence.domain.ContratoFuncion;
import com.sae.persistence.domain.Funcion;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.FuncionCargo;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class FuncionJpaController implements Serializable {

    public FuncionJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Funcion funcion) throws PreexistingEntityException, Exception {
        if (funcion.getContratoFuncionList() == null) {
            funcion.setContratoFuncionList(new ArrayList<ContratoFuncion>());
        }
        if (funcion.getFuncionCargoList() == null) {
            funcion.setFuncionCargoList(new ArrayList<FuncionCargo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ContratoFuncion> attachedContratoFuncionList = new ArrayList<ContratoFuncion>();
            for (ContratoFuncion contratoFuncionListContratoFuncionToAttach : funcion.getContratoFuncionList()) {
                contratoFuncionListContratoFuncionToAttach = em.getReference(contratoFuncionListContratoFuncionToAttach.getClass(), contratoFuncionListContratoFuncionToAttach.getId());
                attachedContratoFuncionList.add(contratoFuncionListContratoFuncionToAttach);
            }
            funcion.setContratoFuncionList(attachedContratoFuncionList);
            List<FuncionCargo> attachedFuncionCargoList = new ArrayList<FuncionCargo>();
            for (FuncionCargo funcionCargoListFuncionCargoToAttach : funcion.getFuncionCargoList()) {
                funcionCargoListFuncionCargoToAttach = em.getReference(funcionCargoListFuncionCargoToAttach.getClass(), funcionCargoListFuncionCargoToAttach.getId());
                attachedFuncionCargoList.add(funcionCargoListFuncionCargoToAttach);
            }
            funcion.setFuncionCargoList(attachedFuncionCargoList);
            em.persist(funcion);
            for (ContratoFuncion contratoFuncionListContratoFuncion : funcion.getContratoFuncionList()) {
                Funcion oldIdFuncionOfContratoFuncionListContratoFuncion = contratoFuncionListContratoFuncion.getIdFuncion();
                contratoFuncionListContratoFuncion.setIdFuncion(funcion);
                contratoFuncionListContratoFuncion = em.merge(contratoFuncionListContratoFuncion);
                if (oldIdFuncionOfContratoFuncionListContratoFuncion != null) {
                    oldIdFuncionOfContratoFuncionListContratoFuncion.getContratoFuncionList().remove(contratoFuncionListContratoFuncion);
                    oldIdFuncionOfContratoFuncionListContratoFuncion = em.merge(oldIdFuncionOfContratoFuncionListContratoFuncion);
                }
            }
            for (FuncionCargo funcionCargoListFuncionCargo : funcion.getFuncionCargoList()) {
                Funcion oldIdFuncionOfFuncionCargoListFuncionCargo = funcionCargoListFuncionCargo.getIdFuncion();
                funcionCargoListFuncionCargo.setIdFuncion(funcion);
                funcionCargoListFuncionCargo = em.merge(funcionCargoListFuncionCargo);
                if (oldIdFuncionOfFuncionCargoListFuncionCargo != null) {
                    oldIdFuncionOfFuncionCargoListFuncionCargo.getFuncionCargoList().remove(funcionCargoListFuncionCargo);
                    oldIdFuncionOfFuncionCargoListFuncionCargo = em.merge(oldIdFuncionOfFuncionCargoListFuncionCargo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFuncion(funcion.getId()) != null) {
                throw new PreexistingEntityException("Funcion " + funcion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Funcion funcion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcion persistentFuncion = em.find(Funcion.class, funcion.getId());
            List<ContratoFuncion> contratoFuncionListOld = persistentFuncion.getContratoFuncionList();
            List<ContratoFuncion> contratoFuncionListNew = funcion.getContratoFuncionList();
            List<FuncionCargo> funcionCargoListOld = persistentFuncion.getFuncionCargoList();
            List<FuncionCargo> funcionCargoListNew = funcion.getFuncionCargoList();
            List<ContratoFuncion> attachedContratoFuncionListNew = new ArrayList<ContratoFuncion>();
            for (ContratoFuncion contratoFuncionListNewContratoFuncionToAttach : contratoFuncionListNew) {
                contratoFuncionListNewContratoFuncionToAttach = em.getReference(contratoFuncionListNewContratoFuncionToAttach.getClass(), contratoFuncionListNewContratoFuncionToAttach.getId());
                attachedContratoFuncionListNew.add(contratoFuncionListNewContratoFuncionToAttach);
            }
            contratoFuncionListNew = attachedContratoFuncionListNew;
            funcion.setContratoFuncionList(contratoFuncionListNew);
            List<FuncionCargo> attachedFuncionCargoListNew = new ArrayList<FuncionCargo>();
            for (FuncionCargo funcionCargoListNewFuncionCargoToAttach : funcionCargoListNew) {
                funcionCargoListNewFuncionCargoToAttach = em.getReference(funcionCargoListNewFuncionCargoToAttach.getClass(), funcionCargoListNewFuncionCargoToAttach.getId());
                attachedFuncionCargoListNew.add(funcionCargoListNewFuncionCargoToAttach);
            }
            funcionCargoListNew = attachedFuncionCargoListNew;
            funcion.setFuncionCargoList(funcionCargoListNew);
            funcion = em.merge(funcion);
            for (ContratoFuncion contratoFuncionListOldContratoFuncion : contratoFuncionListOld) {
                if (!contratoFuncionListNew.contains(contratoFuncionListOldContratoFuncion)) {
                    contratoFuncionListOldContratoFuncion.setIdFuncion(null);
                    contratoFuncionListOldContratoFuncion = em.merge(contratoFuncionListOldContratoFuncion);
                }
            }
            for (ContratoFuncion contratoFuncionListNewContratoFuncion : contratoFuncionListNew) {
                if (!contratoFuncionListOld.contains(contratoFuncionListNewContratoFuncion)) {
                    Funcion oldIdFuncionOfContratoFuncionListNewContratoFuncion = contratoFuncionListNewContratoFuncion.getIdFuncion();
                    contratoFuncionListNewContratoFuncion.setIdFuncion(funcion);
                    contratoFuncionListNewContratoFuncion = em.merge(contratoFuncionListNewContratoFuncion);
                    if (oldIdFuncionOfContratoFuncionListNewContratoFuncion != null && !oldIdFuncionOfContratoFuncionListNewContratoFuncion.equals(funcion)) {
                        oldIdFuncionOfContratoFuncionListNewContratoFuncion.getContratoFuncionList().remove(contratoFuncionListNewContratoFuncion);
                        oldIdFuncionOfContratoFuncionListNewContratoFuncion = em.merge(oldIdFuncionOfContratoFuncionListNewContratoFuncion);
                    }
                }
            }
            for (FuncionCargo funcionCargoListOldFuncionCargo : funcionCargoListOld) {
                if (!funcionCargoListNew.contains(funcionCargoListOldFuncionCargo)) {
                    funcionCargoListOldFuncionCargo.setIdFuncion(null);
                    funcionCargoListOldFuncionCargo = em.merge(funcionCargoListOldFuncionCargo);
                }
            }
            for (FuncionCargo funcionCargoListNewFuncionCargo : funcionCargoListNew) {
                if (!funcionCargoListOld.contains(funcionCargoListNewFuncionCargo)) {
                    Funcion oldIdFuncionOfFuncionCargoListNewFuncionCargo = funcionCargoListNewFuncionCargo.getIdFuncion();
                    funcionCargoListNewFuncionCargo.setIdFuncion(funcion);
                    funcionCargoListNewFuncionCargo = em.merge(funcionCargoListNewFuncionCargo);
                    if (oldIdFuncionOfFuncionCargoListNewFuncionCargo != null && !oldIdFuncionOfFuncionCargoListNewFuncionCargo.equals(funcion)) {
                        oldIdFuncionOfFuncionCargoListNewFuncionCargo.getFuncionCargoList().remove(funcionCargoListNewFuncionCargo);
                        oldIdFuncionOfFuncionCargoListNewFuncionCargo = em.merge(oldIdFuncionOfFuncionCargoListNewFuncionCargo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = funcion.getId();
                if (findFuncion(id) == null) {
                    throw new NonexistentEntityException("The funcion with id " + id + " no longer exists.");
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
            Funcion funcion;
            try {
                funcion = em.getReference(Funcion.class, id);
                funcion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The funcion with id " + id + " no longer exists.", enfe);
            }
            List<ContratoFuncion> contratoFuncionList = funcion.getContratoFuncionList();
            for (ContratoFuncion contratoFuncionListContratoFuncion : contratoFuncionList) {
                contratoFuncionListContratoFuncion.setIdFuncion(null);
                contratoFuncionListContratoFuncion = em.merge(contratoFuncionListContratoFuncion);
            }
            List<FuncionCargo> funcionCargoList = funcion.getFuncionCargoList();
            for (FuncionCargo funcionCargoListFuncionCargo : funcionCargoList) {
                funcionCargoListFuncionCargo.setIdFuncion(null);
                funcionCargoListFuncionCargo = em.merge(funcionCargoListFuncionCargo);
            }
            em.remove(funcion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Funcion> findFuncionEntities() {
        return findFuncionEntities(true, -1, -1);
    }

    public List<Funcion> findFuncionEntities(int maxResults, int firstResult) {
        return findFuncionEntities(false, maxResults, firstResult);
    }

    private List<Funcion> findFuncionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Funcion.class));
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

    public Funcion findFuncion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Funcion.class, id);
        } finally {
            em.close();
        }
    }

    public int getFuncionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Funcion> rt = cq.from(Funcion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
