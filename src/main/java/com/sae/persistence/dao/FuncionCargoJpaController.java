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
import com.sae.persistence.domain.Funcion;
import com.sae.persistence.domain.FuncionCargo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class FuncionCargoJpaController implements Serializable {

    public FuncionCargoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FuncionCargo funcionCargo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcion idFuncion = funcionCargo.getIdFuncion();
            if (idFuncion != null) {
                idFuncion = em.getReference(idFuncion.getClass(), idFuncion.getId());
                funcionCargo.setIdFuncion(idFuncion);
            }
            em.persist(funcionCargo);
            if (idFuncion != null) {
                idFuncion.getFuncionCargoList().add(funcionCargo);
                idFuncion = em.merge(idFuncion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFuncionCargo(funcionCargo.getId()) != null) {
                throw new PreexistingEntityException("FuncionCargo " + funcionCargo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FuncionCargo funcionCargo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FuncionCargo persistentFuncionCargo = em.find(FuncionCargo.class, funcionCargo.getId());
            Funcion idFuncionOld = persistentFuncionCargo.getIdFuncion();
            Funcion idFuncionNew = funcionCargo.getIdFuncion();
            if (idFuncionNew != null) {
                idFuncionNew = em.getReference(idFuncionNew.getClass(), idFuncionNew.getId());
                funcionCargo.setIdFuncion(idFuncionNew);
            }
            funcionCargo = em.merge(funcionCargo);
            if (idFuncionOld != null && !idFuncionOld.equals(idFuncionNew)) {
                idFuncionOld.getFuncionCargoList().remove(funcionCargo);
                idFuncionOld = em.merge(idFuncionOld);
            }
            if (idFuncionNew != null && !idFuncionNew.equals(idFuncionOld)) {
                idFuncionNew.getFuncionCargoList().add(funcionCargo);
                idFuncionNew = em.merge(idFuncionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = funcionCargo.getId();
                if (findFuncionCargo(id) == null) {
                    throw new NonexistentEntityException("The funcionCargo with id " + id + " no longer exists.");
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
            FuncionCargo funcionCargo;
            try {
                funcionCargo = em.getReference(FuncionCargo.class, id);
                funcionCargo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The funcionCargo with id " + id + " no longer exists.", enfe);
            }
            Funcion idFuncion = funcionCargo.getIdFuncion();
            if (idFuncion != null) {
                idFuncion.getFuncionCargoList().remove(funcionCargo);
                idFuncion = em.merge(idFuncion);
            }
            em.remove(funcionCargo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FuncionCargo> findFuncionCargoEntities() {
        return findFuncionCargoEntities(true, -1, -1);
    }

    public List<FuncionCargo> findFuncionCargoEntities(int maxResults, int firstResult) {
        return findFuncionCargoEntities(false, maxResults, firstResult);
    }

    private List<FuncionCargo> findFuncionCargoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FuncionCargo.class));
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

    public FuncionCargo findFuncionCargo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FuncionCargo.class, id);
        } finally {
            em.close();
        }
    }

    public int getFuncionCargoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FuncionCargo> rt = cq.from(FuncionCargo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
