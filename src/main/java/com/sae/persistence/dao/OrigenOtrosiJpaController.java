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
import com.sae.persistence.domain.ContratoProyectoProveedor;
import com.sae.persistence.domain.OrigenOtrosi;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class OrigenOtrosiJpaController implements Serializable {

    public OrigenOtrosiJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OrigenOtrosi origenOtrosi) throws PreexistingEntityException, Exception {
        if (origenOtrosi.getContratoProyectoProveedorList() == null) {
            origenOtrosi.setContratoProyectoProveedorList(new ArrayList<ContratoProyectoProveedor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ContratoProyectoProveedor> attachedContratoProyectoProveedorList = new ArrayList<ContratoProyectoProveedor>();
            for (ContratoProyectoProveedor contratoProyectoProveedorListContratoProyectoProveedorToAttach : origenOtrosi.getContratoProyectoProveedorList()) {
                contratoProyectoProveedorListContratoProyectoProveedorToAttach = em.getReference(contratoProyectoProveedorListContratoProyectoProveedorToAttach.getClass(), contratoProyectoProveedorListContratoProyectoProveedorToAttach.getId());
                attachedContratoProyectoProveedorList.add(contratoProyectoProveedorListContratoProyectoProveedorToAttach);
            }
            origenOtrosi.setContratoProyectoProveedorList(attachedContratoProyectoProveedorList);
            em.persist(origenOtrosi);
            for (ContratoProyectoProveedor contratoProyectoProveedorListContratoProyectoProveedor : origenOtrosi.getContratoProyectoProveedorList()) {
                OrigenOtrosi oldIdOrigenOtrosiOfContratoProyectoProveedorListContratoProyectoProveedor = contratoProyectoProveedorListContratoProyectoProveedor.getIdOrigenOtrosi();
                contratoProyectoProveedorListContratoProyectoProveedor.setIdOrigenOtrosi(origenOtrosi);
                contratoProyectoProveedorListContratoProyectoProveedor = em.merge(contratoProyectoProveedorListContratoProyectoProveedor);
                if (oldIdOrigenOtrosiOfContratoProyectoProveedorListContratoProyectoProveedor != null) {
                    oldIdOrigenOtrosiOfContratoProyectoProveedorListContratoProyectoProveedor.getContratoProyectoProveedorList().remove(contratoProyectoProveedorListContratoProyectoProveedor);
                    oldIdOrigenOtrosiOfContratoProyectoProveedorListContratoProyectoProveedor = em.merge(oldIdOrigenOtrosiOfContratoProyectoProveedorListContratoProyectoProveedor);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOrigenOtrosi(origenOtrosi.getId()) != null) {
                throw new PreexistingEntityException("OrigenOtrosi " + origenOtrosi + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(OrigenOtrosi origenOtrosi) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrigenOtrosi persistentOrigenOtrosi = em.find(OrigenOtrosi.class, origenOtrosi.getId());
            List<ContratoProyectoProveedor> contratoProyectoProveedorListOld = persistentOrigenOtrosi.getContratoProyectoProveedorList();
            List<ContratoProyectoProveedor> contratoProyectoProveedorListNew = origenOtrosi.getContratoProyectoProveedorList();
            List<ContratoProyectoProveedor> attachedContratoProyectoProveedorListNew = new ArrayList<ContratoProyectoProveedor>();
            for (ContratoProyectoProveedor contratoProyectoProveedorListNewContratoProyectoProveedorToAttach : contratoProyectoProveedorListNew) {
                contratoProyectoProveedorListNewContratoProyectoProveedorToAttach = em.getReference(contratoProyectoProveedorListNewContratoProyectoProveedorToAttach.getClass(), contratoProyectoProveedorListNewContratoProyectoProveedorToAttach.getId());
                attachedContratoProyectoProveedorListNew.add(contratoProyectoProveedorListNewContratoProyectoProveedorToAttach);
            }
            contratoProyectoProveedorListNew = attachedContratoProyectoProveedorListNew;
            origenOtrosi.setContratoProyectoProveedorList(contratoProyectoProveedorListNew);
            origenOtrosi = em.merge(origenOtrosi);
            for (ContratoProyectoProveedor contratoProyectoProveedorListOldContratoProyectoProveedor : contratoProyectoProveedorListOld) {
                if (!contratoProyectoProveedorListNew.contains(contratoProyectoProveedorListOldContratoProyectoProveedor)) {
                    contratoProyectoProveedorListOldContratoProyectoProveedor.setIdOrigenOtrosi(null);
                    contratoProyectoProveedorListOldContratoProyectoProveedor = em.merge(contratoProyectoProveedorListOldContratoProyectoProveedor);
                }
            }
            for (ContratoProyectoProveedor contratoProyectoProveedorListNewContratoProyectoProveedor : contratoProyectoProveedorListNew) {
                if (!contratoProyectoProveedorListOld.contains(contratoProyectoProveedorListNewContratoProyectoProveedor)) {
                    OrigenOtrosi oldIdOrigenOtrosiOfContratoProyectoProveedorListNewContratoProyectoProveedor = contratoProyectoProveedorListNewContratoProyectoProveedor.getIdOrigenOtrosi();
                    contratoProyectoProveedorListNewContratoProyectoProveedor.setIdOrigenOtrosi(origenOtrosi);
                    contratoProyectoProveedorListNewContratoProyectoProveedor = em.merge(contratoProyectoProveedorListNewContratoProyectoProveedor);
                    if (oldIdOrigenOtrosiOfContratoProyectoProveedorListNewContratoProyectoProveedor != null && !oldIdOrigenOtrosiOfContratoProyectoProveedorListNewContratoProyectoProveedor.equals(origenOtrosi)) {
                        oldIdOrigenOtrosiOfContratoProyectoProveedorListNewContratoProyectoProveedor.getContratoProyectoProveedorList().remove(contratoProyectoProveedorListNewContratoProyectoProveedor);
                        oldIdOrigenOtrosiOfContratoProyectoProveedorListNewContratoProyectoProveedor = em.merge(oldIdOrigenOtrosiOfContratoProyectoProveedorListNewContratoProyectoProveedor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = origenOtrosi.getId();
                if (findOrigenOtrosi(id) == null) {
                    throw new NonexistentEntityException("The origenOtrosi with id " + id + " no longer exists.");
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
            OrigenOtrosi origenOtrosi;
            try {
                origenOtrosi = em.getReference(OrigenOtrosi.class, id);
                origenOtrosi.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The origenOtrosi with id " + id + " no longer exists.", enfe);
            }
            List<ContratoProyectoProveedor> contratoProyectoProveedorList = origenOtrosi.getContratoProyectoProveedorList();
            for (ContratoProyectoProveedor contratoProyectoProveedorListContratoProyectoProveedor : contratoProyectoProveedorList) {
                contratoProyectoProveedorListContratoProyectoProveedor.setIdOrigenOtrosi(null);
                contratoProyectoProveedorListContratoProyectoProveedor = em.merge(contratoProyectoProveedorListContratoProyectoProveedor);
            }
            em.remove(origenOtrosi);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<OrigenOtrosi> findOrigenOtrosiEntities() {
        return findOrigenOtrosiEntities(true, -1, -1);
    }

    public List<OrigenOtrosi> findOrigenOtrosiEntities(int maxResults, int firstResult) {
        return findOrigenOtrosiEntities(false, maxResults, firstResult);
    }

    private List<OrigenOtrosi> findOrigenOtrosiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OrigenOtrosi.class));
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

    public OrigenOtrosi findOrigenOtrosi(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OrigenOtrosi.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrigenOtrosiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OrigenOtrosi> rt = cq.from(OrigenOtrosi.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
