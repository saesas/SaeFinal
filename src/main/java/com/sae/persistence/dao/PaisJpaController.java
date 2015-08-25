/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.IllegalOrphanException;
import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.Departamento;
import com.sae.persistence.domain.Pais;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class PaisJpaController implements Serializable {

    public PaisJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pais pais) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento departamento = pais.getDepartamento();
            if (departamento != null) {
                departamento = em.getReference(departamento.getClass(), departamento.getId());
                pais.setDepartamento(departamento);
            }
            em.persist(pais);
            if (departamento != null) {
                Pais oldPaisOfDepartamento = departamento.getPais();
                if (oldPaisOfDepartamento != null) {
                    oldPaisOfDepartamento.setDepartamento(null);
                    oldPaisOfDepartamento = em.merge(oldPaisOfDepartamento);
                }
                departamento.setPais(pais);
                departamento = em.merge(departamento);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPais(pais.getId()) != null) {
                throw new PreexistingEntityException("Pais " + pais + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pais pais) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais persistentPais = em.find(Pais.class, pais.getId());
            Departamento departamentoOld = persistentPais.getDepartamento();
            Departamento departamentoNew = pais.getDepartamento();
            List<String> illegalOrphanMessages = null;
            if (departamentoOld != null && !departamentoOld.equals(departamentoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Departamento " + departamentoOld + " since its pais field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (departamentoNew != null) {
                departamentoNew = em.getReference(departamentoNew.getClass(), departamentoNew.getId());
                pais.setDepartamento(departamentoNew);
            }
            pais = em.merge(pais);
            if (departamentoNew != null && !departamentoNew.equals(departamentoOld)) {
                Pais oldPaisOfDepartamento = departamentoNew.getPais();
                if (oldPaisOfDepartamento != null) {
                    oldPaisOfDepartamento.setDepartamento(null);
                    oldPaisOfDepartamento = em.merge(oldPaisOfDepartamento);
                }
                departamentoNew.setPais(pais);
                departamentoNew = em.merge(departamentoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pais.getId();
                if (findPais(id) == null) {
                    throw new NonexistentEntityException("The pais with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais pais;
            try {
                pais = em.getReference(Pais.class, id);
                pais.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pais with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Departamento departamentoOrphanCheck = pais.getDepartamento();
            if (departamentoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pais (" + pais + ") cannot be destroyed since the Departamento " + departamentoOrphanCheck + " in its departamento field has a non-nullable pais field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(pais);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pais> findPaisEntities() {
        return findPaisEntities(true, -1, -1);
    }

    public List<Pais> findPaisEntities(int maxResults, int firstResult) {
        return findPaisEntities(false, maxResults, firstResult);
    }

    private List<Pais> findPaisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pais.class));
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

    public Pais findPais(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pais.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pais> rt = cq.from(Pais.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
