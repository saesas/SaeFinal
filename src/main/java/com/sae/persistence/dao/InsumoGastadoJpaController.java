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
import com.sae.persistence.domain.Bitacora;
import com.sae.persistence.domain.InsumoGastado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class InsumoGastadoJpaController implements Serializable {

    public InsumoGastadoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InsumoGastado insumoGastado) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bitacora idBitacora = insumoGastado.getIdBitacora();
            if (idBitacora != null) {
                idBitacora = em.getReference(idBitacora.getClass(), idBitacora.getId());
                insumoGastado.setIdBitacora(idBitacora);
            }
            em.persist(insumoGastado);
            if (idBitacora != null) {
                idBitacora.getInsumoGastadoList().add(insumoGastado);
                idBitacora = em.merge(idBitacora);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInsumoGastado(insumoGastado.getId()) != null) {
                throw new PreexistingEntityException("InsumoGastado " + insumoGastado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InsumoGastado insumoGastado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InsumoGastado persistentInsumoGastado = em.find(InsumoGastado.class, insumoGastado.getId());
            Bitacora idBitacoraOld = persistentInsumoGastado.getIdBitacora();
            Bitacora idBitacoraNew = insumoGastado.getIdBitacora();
            if (idBitacoraNew != null) {
                idBitacoraNew = em.getReference(idBitacoraNew.getClass(), idBitacoraNew.getId());
                insumoGastado.setIdBitacora(idBitacoraNew);
            }
            insumoGastado = em.merge(insumoGastado);
            if (idBitacoraOld != null && !idBitacoraOld.equals(idBitacoraNew)) {
                idBitacoraOld.getInsumoGastadoList().remove(insumoGastado);
                idBitacoraOld = em.merge(idBitacoraOld);
            }
            if (idBitacoraNew != null && !idBitacoraNew.equals(idBitacoraOld)) {
                idBitacoraNew.getInsumoGastadoList().add(insumoGastado);
                idBitacoraNew = em.merge(idBitacoraNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = insumoGastado.getId();
                if (findInsumoGastado(id) == null) {
                    throw new NonexistentEntityException("The insumoGastado with id " + id + " no longer exists.");
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
            InsumoGastado insumoGastado;
            try {
                insumoGastado = em.getReference(InsumoGastado.class, id);
                insumoGastado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The insumoGastado with id " + id + " no longer exists.", enfe);
            }
            Bitacora idBitacora = insumoGastado.getIdBitacora();
            if (idBitacora != null) {
                idBitacora.getInsumoGastadoList().remove(insumoGastado);
                idBitacora = em.merge(idBitacora);
            }
            em.remove(insumoGastado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<InsumoGastado> findInsumoGastadoEntities() {
        return findInsumoGastadoEntities(true, -1, -1);
    }

    public List<InsumoGastado> findInsumoGastadoEntities(int maxResults, int firstResult) {
        return findInsumoGastadoEntities(false, maxResults, firstResult);
    }

    private List<InsumoGastado> findInsumoGastadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InsumoGastado.class));
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

    public InsumoGastado findInsumoGastado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InsumoGastado.class, id);
        } finally {
            em.close();
        }
    }

    public int getInsumoGastadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InsumoGastado> rt = cq.from(InsumoGastado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
