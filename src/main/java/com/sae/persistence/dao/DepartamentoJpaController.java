/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.IllegalOrphanException;
import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.Departamento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.Municipio;
import com.sae.persistence.domain.Pais;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class DepartamentoJpaController implements Serializable {

    public DepartamentoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Departamento departamento) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Pais paisOrphanCheck = departamento.getPais();
        if (paisOrphanCheck != null) {
            Departamento oldDepartamentoOfPais = paisOrphanCheck.getDepartamento();
            if (oldDepartamentoOfPais != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Pais " + paisOrphanCheck + " already has an item of type Departamento whose pais column cannot be null. Please make another selection for the pais field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Municipio municipio = departamento.getMunicipio();
            if (municipio != null) {
                municipio = em.getReference(municipio.getClass(), municipio.getId());
                departamento.setMunicipio(municipio);
            }
            Pais pais = departamento.getPais();
            if (pais != null) {
                pais = em.getReference(pais.getClass(), pais.getId());
                departamento.setPais(pais);
            }
            em.persist(departamento);
            if (municipio != null) {
                Departamento oldDepartamentoOfMunicipio = municipio.getDepartamento();
                if (oldDepartamentoOfMunicipio != null) {
                    oldDepartamentoOfMunicipio.setMunicipio(null);
                    oldDepartamentoOfMunicipio = em.merge(oldDepartamentoOfMunicipio);
                }
                municipio.setDepartamento(departamento);
                municipio = em.merge(municipio);
            }
            if (pais != null) {
                pais.setDepartamento(departamento);
                pais = em.merge(pais);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDepartamento(departamento.getId()) != null) {
                throw new PreexistingEntityException("Departamento " + departamento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Departamento departamento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento persistentDepartamento = em.find(Departamento.class, departamento.getId());
            Municipio municipioOld = persistentDepartamento.getMunicipio();
            Municipio municipioNew = departamento.getMunicipio();
            Pais paisOld = persistentDepartamento.getPais();
            Pais paisNew = departamento.getPais();
            List<String> illegalOrphanMessages = null;
            if (municipioOld != null && !municipioOld.equals(municipioNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Municipio " + municipioOld + " since its departamento field is not nullable.");
            }
            if (paisNew != null && !paisNew.equals(paisOld)) {
                Departamento oldDepartamentoOfPais = paisNew.getDepartamento();
                if (oldDepartamentoOfPais != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Pais " + paisNew + " already has an item of type Departamento whose pais column cannot be null. Please make another selection for the pais field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (municipioNew != null) {
                municipioNew = em.getReference(municipioNew.getClass(), municipioNew.getId());
                departamento.setMunicipio(municipioNew);
            }
            if (paisNew != null) {
                paisNew = em.getReference(paisNew.getClass(), paisNew.getId());
                departamento.setPais(paisNew);
            }
            departamento = em.merge(departamento);
            if (municipioNew != null && !municipioNew.equals(municipioOld)) {
                Departamento oldDepartamentoOfMunicipio = municipioNew.getDepartamento();
                if (oldDepartamentoOfMunicipio != null) {
                    oldDepartamentoOfMunicipio.setMunicipio(null);
                    oldDepartamentoOfMunicipio = em.merge(oldDepartamentoOfMunicipio);
                }
                municipioNew.setDepartamento(departamento);
                municipioNew = em.merge(municipioNew);
            }
            if (paisOld != null && !paisOld.equals(paisNew)) {
                paisOld.setDepartamento(null);
                paisOld = em.merge(paisOld);
            }
            if (paisNew != null && !paisNew.equals(paisOld)) {
                paisNew.setDepartamento(departamento);
                paisNew = em.merge(paisNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = departamento.getId();
                if (findDepartamento(id) == null) {
                    throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.");
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
            Departamento departamento;
            try {
                departamento = em.getReference(Departamento.class, id);
                departamento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Municipio municipioOrphanCheck = departamento.getMunicipio();
            if (municipioOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamento (" + departamento + ") cannot be destroyed since the Municipio " + municipioOrphanCheck + " in its municipio field has a non-nullable departamento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Pais pais = departamento.getPais();
            if (pais != null) {
                pais.setDepartamento(null);
                pais = em.merge(pais);
            }
            em.remove(departamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Departamento> findDepartamentoEntities() {
        return findDepartamentoEntities(true, -1, -1);
    }

    public List<Departamento> findDepartamentoEntities(int maxResults, int firstResult) {
        return findDepartamentoEntities(false, maxResults, firstResult);
    }

    private List<Departamento> findDepartamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Departamento.class));
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

    public Departamento findDepartamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Departamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getDepartamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Departamento> rt = cq.from(Departamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
