/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.PagoNomina;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.PagoNominaComprobante;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class PagoNominaJpaController implements Serializable {

    public PagoNominaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PagoNomina pagoNomina) throws PreexistingEntityException, Exception {
        if (pagoNomina.getPagoNominaComprobanteList() == null) {
            pagoNomina.setPagoNominaComprobanteList(new ArrayList<PagoNominaComprobante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PagoNominaComprobante> attachedPagoNominaComprobanteList = new ArrayList<PagoNominaComprobante>();
            for (PagoNominaComprobante pagoNominaComprobanteListPagoNominaComprobanteToAttach : pagoNomina.getPagoNominaComprobanteList()) {
                pagoNominaComprobanteListPagoNominaComprobanteToAttach = em.getReference(pagoNominaComprobanteListPagoNominaComprobanteToAttach.getClass(), pagoNominaComprobanteListPagoNominaComprobanteToAttach.getId());
                attachedPagoNominaComprobanteList.add(pagoNominaComprobanteListPagoNominaComprobanteToAttach);
            }
            pagoNomina.setPagoNominaComprobanteList(attachedPagoNominaComprobanteList);
            em.persist(pagoNomina);
            for (PagoNominaComprobante pagoNominaComprobanteListPagoNominaComprobante : pagoNomina.getPagoNominaComprobanteList()) {
                PagoNomina oldIdPagoNominaOfPagoNominaComprobanteListPagoNominaComprobante = pagoNominaComprobanteListPagoNominaComprobante.getIdPagoNomina();
                pagoNominaComprobanteListPagoNominaComprobante.setIdPagoNomina(pagoNomina);
                pagoNominaComprobanteListPagoNominaComprobante = em.merge(pagoNominaComprobanteListPagoNominaComprobante);
                if (oldIdPagoNominaOfPagoNominaComprobanteListPagoNominaComprobante != null) {
                    oldIdPagoNominaOfPagoNominaComprobanteListPagoNominaComprobante.getPagoNominaComprobanteList().remove(pagoNominaComprobanteListPagoNominaComprobante);
                    oldIdPagoNominaOfPagoNominaComprobanteListPagoNominaComprobante = em.merge(oldIdPagoNominaOfPagoNominaComprobanteListPagoNominaComprobante);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPagoNomina(pagoNomina.getId()) != null) {
                throw new PreexistingEntityException("PagoNomina " + pagoNomina + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PagoNomina pagoNomina) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PagoNomina persistentPagoNomina = em.find(PagoNomina.class, pagoNomina.getId());
            List<PagoNominaComprobante> pagoNominaComprobanteListOld = persistentPagoNomina.getPagoNominaComprobanteList();
            List<PagoNominaComprobante> pagoNominaComprobanteListNew = pagoNomina.getPagoNominaComprobanteList();
            List<PagoNominaComprobante> attachedPagoNominaComprobanteListNew = new ArrayList<PagoNominaComprobante>();
            for (PagoNominaComprobante pagoNominaComprobanteListNewPagoNominaComprobanteToAttach : pagoNominaComprobanteListNew) {
                pagoNominaComprobanteListNewPagoNominaComprobanteToAttach = em.getReference(pagoNominaComprobanteListNewPagoNominaComprobanteToAttach.getClass(), pagoNominaComprobanteListNewPagoNominaComprobanteToAttach.getId());
                attachedPagoNominaComprobanteListNew.add(pagoNominaComprobanteListNewPagoNominaComprobanteToAttach);
            }
            pagoNominaComprobanteListNew = attachedPagoNominaComprobanteListNew;
            pagoNomina.setPagoNominaComprobanteList(pagoNominaComprobanteListNew);
            pagoNomina = em.merge(pagoNomina);
            for (PagoNominaComprobante pagoNominaComprobanteListOldPagoNominaComprobante : pagoNominaComprobanteListOld) {
                if (!pagoNominaComprobanteListNew.contains(pagoNominaComprobanteListOldPagoNominaComprobante)) {
                    pagoNominaComprobanteListOldPagoNominaComprobante.setIdPagoNomina(null);
                    pagoNominaComprobanteListOldPagoNominaComprobante = em.merge(pagoNominaComprobanteListOldPagoNominaComprobante);
                }
            }
            for (PagoNominaComprobante pagoNominaComprobanteListNewPagoNominaComprobante : pagoNominaComprobanteListNew) {
                if (!pagoNominaComprobanteListOld.contains(pagoNominaComprobanteListNewPagoNominaComprobante)) {
                    PagoNomina oldIdPagoNominaOfPagoNominaComprobanteListNewPagoNominaComprobante = pagoNominaComprobanteListNewPagoNominaComprobante.getIdPagoNomina();
                    pagoNominaComprobanteListNewPagoNominaComprobante.setIdPagoNomina(pagoNomina);
                    pagoNominaComprobanteListNewPagoNominaComprobante = em.merge(pagoNominaComprobanteListNewPagoNominaComprobante);
                    if (oldIdPagoNominaOfPagoNominaComprobanteListNewPagoNominaComprobante != null && !oldIdPagoNominaOfPagoNominaComprobanteListNewPagoNominaComprobante.equals(pagoNomina)) {
                        oldIdPagoNominaOfPagoNominaComprobanteListNewPagoNominaComprobante.getPagoNominaComprobanteList().remove(pagoNominaComprobanteListNewPagoNominaComprobante);
                        oldIdPagoNominaOfPagoNominaComprobanteListNewPagoNominaComprobante = em.merge(oldIdPagoNominaOfPagoNominaComprobanteListNewPagoNominaComprobante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pagoNomina.getId();
                if (findPagoNomina(id) == null) {
                    throw new NonexistentEntityException("The pagoNomina with id " + id + " no longer exists.");
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
            PagoNomina pagoNomina;
            try {
                pagoNomina = em.getReference(PagoNomina.class, id);
                pagoNomina.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagoNomina with id " + id + " no longer exists.", enfe);
            }
            List<PagoNominaComprobante> pagoNominaComprobanteList = pagoNomina.getPagoNominaComprobanteList();
            for (PagoNominaComprobante pagoNominaComprobanteListPagoNominaComprobante : pagoNominaComprobanteList) {
                pagoNominaComprobanteListPagoNominaComprobante.setIdPagoNomina(null);
                pagoNominaComprobanteListPagoNominaComprobante = em.merge(pagoNominaComprobanteListPagoNominaComprobante);
            }
            em.remove(pagoNomina);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PagoNomina> findPagoNominaEntities() {
        return findPagoNominaEntities(true, -1, -1);
    }

    public List<PagoNomina> findPagoNominaEntities(int maxResults, int firstResult) {
        return findPagoNominaEntities(false, maxResults, firstResult);
    }

    private List<PagoNomina> findPagoNominaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PagoNomina.class));
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

    public PagoNomina findPagoNomina(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PagoNomina.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagoNominaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PagoNomina> rt = cq.from(PagoNomina.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
