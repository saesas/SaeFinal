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
import com.sae.persistence.domain.PagoNomina;
import com.sae.persistence.domain.PagoNominaComprobante;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class PagoNominaComprobanteJpaController implements Serializable {

    public PagoNominaComprobanteJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PagoNominaComprobante pagoNominaComprobante) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PagoNomina idPagoNomina = pagoNominaComprobante.getIdPagoNomina();
            if (idPagoNomina != null) {
                idPagoNomina = em.getReference(idPagoNomina.getClass(), idPagoNomina.getId());
                pagoNominaComprobante.setIdPagoNomina(idPagoNomina);
            }
            em.persist(pagoNominaComprobante);
            if (idPagoNomina != null) {
                idPagoNomina.getPagoNominaComprobanteList().add(pagoNominaComprobante);
                idPagoNomina = em.merge(idPagoNomina);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPagoNominaComprobante(pagoNominaComprobante.getId()) != null) {
                throw new PreexistingEntityException("PagoNominaComprobante " + pagoNominaComprobante + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PagoNominaComprobante pagoNominaComprobante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PagoNominaComprobante persistentPagoNominaComprobante = em.find(PagoNominaComprobante.class, pagoNominaComprobante.getId());
            PagoNomina idPagoNominaOld = persistentPagoNominaComprobante.getIdPagoNomina();
            PagoNomina idPagoNominaNew = pagoNominaComprobante.getIdPagoNomina();
            if (idPagoNominaNew != null) {
                idPagoNominaNew = em.getReference(idPagoNominaNew.getClass(), idPagoNominaNew.getId());
                pagoNominaComprobante.setIdPagoNomina(idPagoNominaNew);
            }
            pagoNominaComprobante = em.merge(pagoNominaComprobante);
            if (idPagoNominaOld != null && !idPagoNominaOld.equals(idPagoNominaNew)) {
                idPagoNominaOld.getPagoNominaComprobanteList().remove(pagoNominaComprobante);
                idPagoNominaOld = em.merge(idPagoNominaOld);
            }
            if (idPagoNominaNew != null && !idPagoNominaNew.equals(idPagoNominaOld)) {
                idPagoNominaNew.getPagoNominaComprobanteList().add(pagoNominaComprobante);
                idPagoNominaNew = em.merge(idPagoNominaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pagoNominaComprobante.getId();
                if (findPagoNominaComprobante(id) == null) {
                    throw new NonexistentEntityException("The pagoNominaComprobante with id " + id + " no longer exists.");
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
            PagoNominaComprobante pagoNominaComprobante;
            try {
                pagoNominaComprobante = em.getReference(PagoNominaComprobante.class, id);
                pagoNominaComprobante.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagoNominaComprobante with id " + id + " no longer exists.", enfe);
            }
            PagoNomina idPagoNomina = pagoNominaComprobante.getIdPagoNomina();
            if (idPagoNomina != null) {
                idPagoNomina.getPagoNominaComprobanteList().remove(pagoNominaComprobante);
                idPagoNomina = em.merge(idPagoNomina);
            }
            em.remove(pagoNominaComprobante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PagoNominaComprobante> findPagoNominaComprobanteEntities() {
        return findPagoNominaComprobanteEntities(true, -1, -1);
    }

    public List<PagoNominaComprobante> findPagoNominaComprobanteEntities(int maxResults, int firstResult) {
        return findPagoNominaComprobanteEntities(false, maxResults, firstResult);
    }

    private List<PagoNominaComprobante> findPagoNominaComprobanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PagoNominaComprobante.class));
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

    public PagoNominaComprobante findPagoNominaComprobante(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PagoNominaComprobante.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagoNominaComprobanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PagoNominaComprobante> rt = cq.from(PagoNominaComprobante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
