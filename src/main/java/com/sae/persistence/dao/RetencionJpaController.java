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
import com.sae.persistence.domain.ConceptoRetencion;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.FacturaVentaRetencion;
import com.sae.persistence.domain.AplicacionTributaria;
import com.sae.persistence.domain.Retencion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class RetencionJpaController implements Serializable {

    public RetencionJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Retencion retencion) throws PreexistingEntityException, Exception {
        if (retencion.getConceptoRetencionList() == null) {
            retencion.setConceptoRetencionList(new ArrayList<ConceptoRetencion>());
        }
        if (retencion.getFacturaVentaRetencionList() == null) {
            retencion.setFacturaVentaRetencionList(new ArrayList<FacturaVentaRetencion>());
        }
        if (retencion.getAplicacionTributariaList() == null) {
            retencion.setAplicacionTributariaList(new ArrayList<AplicacionTributaria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ConceptoRetencion> attachedConceptoRetencionList = new ArrayList<ConceptoRetencion>();
            for (ConceptoRetencion conceptoRetencionListConceptoRetencionToAttach : retencion.getConceptoRetencionList()) {
                conceptoRetencionListConceptoRetencionToAttach = em.getReference(conceptoRetencionListConceptoRetencionToAttach.getClass(), conceptoRetencionListConceptoRetencionToAttach.getId());
                attachedConceptoRetencionList.add(conceptoRetencionListConceptoRetencionToAttach);
            }
            retencion.setConceptoRetencionList(attachedConceptoRetencionList);
            List<FacturaVentaRetencion> attachedFacturaVentaRetencionList = new ArrayList<FacturaVentaRetencion>();
            for (FacturaVentaRetencion facturaVentaRetencionListFacturaVentaRetencionToAttach : retencion.getFacturaVentaRetencionList()) {
                facturaVentaRetencionListFacturaVentaRetencionToAttach = em.getReference(facturaVentaRetencionListFacturaVentaRetencionToAttach.getClass(), facturaVentaRetencionListFacturaVentaRetencionToAttach.getId());
                attachedFacturaVentaRetencionList.add(facturaVentaRetencionListFacturaVentaRetencionToAttach);
            }
            retencion.setFacturaVentaRetencionList(attachedFacturaVentaRetencionList);
            List<AplicacionTributaria> attachedAplicacionTributariaList = new ArrayList<AplicacionTributaria>();
            for (AplicacionTributaria aplicacionTributariaListAplicacionTributariaToAttach : retencion.getAplicacionTributariaList()) {
                aplicacionTributariaListAplicacionTributariaToAttach = em.getReference(aplicacionTributariaListAplicacionTributariaToAttach.getClass(), aplicacionTributariaListAplicacionTributariaToAttach.getId());
                attachedAplicacionTributariaList.add(aplicacionTributariaListAplicacionTributariaToAttach);
            }
            retencion.setAplicacionTributariaList(attachedAplicacionTributariaList);
            em.persist(retencion);
            for (ConceptoRetencion conceptoRetencionListConceptoRetencion : retencion.getConceptoRetencionList()) {
                Retencion oldIdTipoRetencionOfConceptoRetencionListConceptoRetencion = conceptoRetencionListConceptoRetencion.getIdTipoRetencion();
                conceptoRetencionListConceptoRetencion.setIdTipoRetencion(retencion);
                conceptoRetencionListConceptoRetencion = em.merge(conceptoRetencionListConceptoRetencion);
                if (oldIdTipoRetencionOfConceptoRetencionListConceptoRetencion != null) {
                    oldIdTipoRetencionOfConceptoRetencionListConceptoRetencion.getConceptoRetencionList().remove(conceptoRetencionListConceptoRetencion);
                    oldIdTipoRetencionOfConceptoRetencionListConceptoRetencion = em.merge(oldIdTipoRetencionOfConceptoRetencionListConceptoRetencion);
                }
            }
            for (FacturaVentaRetencion facturaVentaRetencionListFacturaVentaRetencion : retencion.getFacturaVentaRetencionList()) {
                Retencion oldIdRetencionOfFacturaVentaRetencionListFacturaVentaRetencion = facturaVentaRetencionListFacturaVentaRetencion.getIdRetencion();
                facturaVentaRetencionListFacturaVentaRetencion.setIdRetencion(retencion);
                facturaVentaRetencionListFacturaVentaRetencion = em.merge(facturaVentaRetencionListFacturaVentaRetencion);
                if (oldIdRetencionOfFacturaVentaRetencionListFacturaVentaRetencion != null) {
                    oldIdRetencionOfFacturaVentaRetencionListFacturaVentaRetencion.getFacturaVentaRetencionList().remove(facturaVentaRetencionListFacturaVentaRetencion);
                    oldIdRetencionOfFacturaVentaRetencionListFacturaVentaRetencion = em.merge(oldIdRetencionOfFacturaVentaRetencionListFacturaVentaRetencion);
                }
            }
            for (AplicacionTributaria aplicacionTributariaListAplicacionTributaria : retencion.getAplicacionTributariaList()) {
                Retencion oldIdRetencionOfAplicacionTributariaListAplicacionTributaria = aplicacionTributariaListAplicacionTributaria.getIdRetencion();
                aplicacionTributariaListAplicacionTributaria.setIdRetencion(retencion);
                aplicacionTributariaListAplicacionTributaria = em.merge(aplicacionTributariaListAplicacionTributaria);
                if (oldIdRetencionOfAplicacionTributariaListAplicacionTributaria != null) {
                    oldIdRetencionOfAplicacionTributariaListAplicacionTributaria.getAplicacionTributariaList().remove(aplicacionTributariaListAplicacionTributaria);
                    oldIdRetencionOfAplicacionTributariaListAplicacionTributaria = em.merge(oldIdRetencionOfAplicacionTributariaListAplicacionTributaria);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRetencion(retencion.getId()) != null) {
                throw new PreexistingEntityException("Retencion " + retencion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Retencion retencion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Retencion persistentRetencion = em.find(Retencion.class, retencion.getId());
            List<ConceptoRetencion> conceptoRetencionListOld = persistentRetencion.getConceptoRetencionList();
            List<ConceptoRetencion> conceptoRetencionListNew = retencion.getConceptoRetencionList();
            List<FacturaVentaRetencion> facturaVentaRetencionListOld = persistentRetencion.getFacturaVentaRetencionList();
            List<FacturaVentaRetencion> facturaVentaRetencionListNew = retencion.getFacturaVentaRetencionList();
            List<AplicacionTributaria> aplicacionTributariaListOld = persistentRetencion.getAplicacionTributariaList();
            List<AplicacionTributaria> aplicacionTributariaListNew = retencion.getAplicacionTributariaList();
            List<ConceptoRetencion> attachedConceptoRetencionListNew = new ArrayList<ConceptoRetencion>();
            for (ConceptoRetencion conceptoRetencionListNewConceptoRetencionToAttach : conceptoRetencionListNew) {
                conceptoRetencionListNewConceptoRetencionToAttach = em.getReference(conceptoRetencionListNewConceptoRetencionToAttach.getClass(), conceptoRetencionListNewConceptoRetencionToAttach.getId());
                attachedConceptoRetencionListNew.add(conceptoRetencionListNewConceptoRetencionToAttach);
            }
            conceptoRetencionListNew = attachedConceptoRetencionListNew;
            retencion.setConceptoRetencionList(conceptoRetencionListNew);
            List<FacturaVentaRetencion> attachedFacturaVentaRetencionListNew = new ArrayList<FacturaVentaRetencion>();
            for (FacturaVentaRetencion facturaVentaRetencionListNewFacturaVentaRetencionToAttach : facturaVentaRetencionListNew) {
                facturaVentaRetencionListNewFacturaVentaRetencionToAttach = em.getReference(facturaVentaRetencionListNewFacturaVentaRetencionToAttach.getClass(), facturaVentaRetencionListNewFacturaVentaRetencionToAttach.getId());
                attachedFacturaVentaRetencionListNew.add(facturaVentaRetencionListNewFacturaVentaRetencionToAttach);
            }
            facturaVentaRetencionListNew = attachedFacturaVentaRetencionListNew;
            retencion.setFacturaVentaRetencionList(facturaVentaRetencionListNew);
            List<AplicacionTributaria> attachedAplicacionTributariaListNew = new ArrayList<AplicacionTributaria>();
            for (AplicacionTributaria aplicacionTributariaListNewAplicacionTributariaToAttach : aplicacionTributariaListNew) {
                aplicacionTributariaListNewAplicacionTributariaToAttach = em.getReference(aplicacionTributariaListNewAplicacionTributariaToAttach.getClass(), aplicacionTributariaListNewAplicacionTributariaToAttach.getId());
                attachedAplicacionTributariaListNew.add(aplicacionTributariaListNewAplicacionTributariaToAttach);
            }
            aplicacionTributariaListNew = attachedAplicacionTributariaListNew;
            retencion.setAplicacionTributariaList(aplicacionTributariaListNew);
            retencion = em.merge(retencion);
            for (ConceptoRetencion conceptoRetencionListOldConceptoRetencion : conceptoRetencionListOld) {
                if (!conceptoRetencionListNew.contains(conceptoRetencionListOldConceptoRetencion)) {
                    conceptoRetencionListOldConceptoRetencion.setIdTipoRetencion(null);
                    conceptoRetencionListOldConceptoRetencion = em.merge(conceptoRetencionListOldConceptoRetencion);
                }
            }
            for (ConceptoRetencion conceptoRetencionListNewConceptoRetencion : conceptoRetencionListNew) {
                if (!conceptoRetencionListOld.contains(conceptoRetencionListNewConceptoRetencion)) {
                    Retencion oldIdTipoRetencionOfConceptoRetencionListNewConceptoRetencion = conceptoRetencionListNewConceptoRetencion.getIdTipoRetencion();
                    conceptoRetencionListNewConceptoRetencion.setIdTipoRetencion(retencion);
                    conceptoRetencionListNewConceptoRetencion = em.merge(conceptoRetencionListNewConceptoRetencion);
                    if (oldIdTipoRetencionOfConceptoRetencionListNewConceptoRetencion != null && !oldIdTipoRetencionOfConceptoRetencionListNewConceptoRetencion.equals(retencion)) {
                        oldIdTipoRetencionOfConceptoRetencionListNewConceptoRetencion.getConceptoRetencionList().remove(conceptoRetencionListNewConceptoRetencion);
                        oldIdTipoRetencionOfConceptoRetencionListNewConceptoRetencion = em.merge(oldIdTipoRetencionOfConceptoRetencionListNewConceptoRetencion);
                    }
                }
            }
            for (FacturaVentaRetencion facturaVentaRetencionListOldFacturaVentaRetencion : facturaVentaRetencionListOld) {
                if (!facturaVentaRetencionListNew.contains(facturaVentaRetencionListOldFacturaVentaRetencion)) {
                    facturaVentaRetencionListOldFacturaVentaRetencion.setIdRetencion(null);
                    facturaVentaRetencionListOldFacturaVentaRetencion = em.merge(facturaVentaRetencionListOldFacturaVentaRetencion);
                }
            }
            for (FacturaVentaRetencion facturaVentaRetencionListNewFacturaVentaRetencion : facturaVentaRetencionListNew) {
                if (!facturaVentaRetencionListOld.contains(facturaVentaRetencionListNewFacturaVentaRetencion)) {
                    Retencion oldIdRetencionOfFacturaVentaRetencionListNewFacturaVentaRetencion = facturaVentaRetencionListNewFacturaVentaRetencion.getIdRetencion();
                    facturaVentaRetencionListNewFacturaVentaRetencion.setIdRetencion(retencion);
                    facturaVentaRetencionListNewFacturaVentaRetencion = em.merge(facturaVentaRetencionListNewFacturaVentaRetencion);
                    if (oldIdRetencionOfFacturaVentaRetencionListNewFacturaVentaRetencion != null && !oldIdRetencionOfFacturaVentaRetencionListNewFacturaVentaRetencion.equals(retencion)) {
                        oldIdRetencionOfFacturaVentaRetencionListNewFacturaVentaRetencion.getFacturaVentaRetencionList().remove(facturaVentaRetencionListNewFacturaVentaRetencion);
                        oldIdRetencionOfFacturaVentaRetencionListNewFacturaVentaRetencion = em.merge(oldIdRetencionOfFacturaVentaRetencionListNewFacturaVentaRetencion);
                    }
                }
            }
            for (AplicacionTributaria aplicacionTributariaListOldAplicacionTributaria : aplicacionTributariaListOld) {
                if (!aplicacionTributariaListNew.contains(aplicacionTributariaListOldAplicacionTributaria)) {
                    aplicacionTributariaListOldAplicacionTributaria.setIdRetencion(null);
                    aplicacionTributariaListOldAplicacionTributaria = em.merge(aplicacionTributariaListOldAplicacionTributaria);
                }
            }
            for (AplicacionTributaria aplicacionTributariaListNewAplicacionTributaria : aplicacionTributariaListNew) {
                if (!aplicacionTributariaListOld.contains(aplicacionTributariaListNewAplicacionTributaria)) {
                    Retencion oldIdRetencionOfAplicacionTributariaListNewAplicacionTributaria = aplicacionTributariaListNewAplicacionTributaria.getIdRetencion();
                    aplicacionTributariaListNewAplicacionTributaria.setIdRetencion(retencion);
                    aplicacionTributariaListNewAplicacionTributaria = em.merge(aplicacionTributariaListNewAplicacionTributaria);
                    if (oldIdRetencionOfAplicacionTributariaListNewAplicacionTributaria != null && !oldIdRetencionOfAplicacionTributariaListNewAplicacionTributaria.equals(retencion)) {
                        oldIdRetencionOfAplicacionTributariaListNewAplicacionTributaria.getAplicacionTributariaList().remove(aplicacionTributariaListNewAplicacionTributaria);
                        oldIdRetencionOfAplicacionTributariaListNewAplicacionTributaria = em.merge(oldIdRetencionOfAplicacionTributariaListNewAplicacionTributaria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = retencion.getId();
                if (findRetencion(id) == null) {
                    throw new NonexistentEntityException("The retencion with id " + id + " no longer exists.");
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
            Retencion retencion;
            try {
                retencion = em.getReference(Retencion.class, id);
                retencion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The retencion with id " + id + " no longer exists.", enfe);
            }
            List<ConceptoRetencion> conceptoRetencionList = retencion.getConceptoRetencionList();
            for (ConceptoRetencion conceptoRetencionListConceptoRetencion : conceptoRetencionList) {
                conceptoRetencionListConceptoRetencion.setIdTipoRetencion(null);
                conceptoRetencionListConceptoRetencion = em.merge(conceptoRetencionListConceptoRetencion);
            }
            List<FacturaVentaRetencion> facturaVentaRetencionList = retencion.getFacturaVentaRetencionList();
            for (FacturaVentaRetencion facturaVentaRetencionListFacturaVentaRetencion : facturaVentaRetencionList) {
                facturaVentaRetencionListFacturaVentaRetencion.setIdRetencion(null);
                facturaVentaRetencionListFacturaVentaRetencion = em.merge(facturaVentaRetencionListFacturaVentaRetencion);
            }
            List<AplicacionTributaria> aplicacionTributariaList = retencion.getAplicacionTributariaList();
            for (AplicacionTributaria aplicacionTributariaListAplicacionTributaria : aplicacionTributariaList) {
                aplicacionTributariaListAplicacionTributaria.setIdRetencion(null);
                aplicacionTributariaListAplicacionTributaria = em.merge(aplicacionTributariaListAplicacionTributaria);
            }
            em.remove(retencion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Retencion> findRetencionEntities() {
        return findRetencionEntities(true, -1, -1);
    }

    public List<Retencion> findRetencionEntities(int maxResults, int firstResult) {
        return findRetencionEntities(false, maxResults, firstResult);
    }

    private List<Retencion> findRetencionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Retencion.class));
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

    public Retencion findRetencion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Retencion.class, id);
        } finally {
            em.close();
        }
    }

    public int getRetencionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Retencion> rt = cq.from(Retencion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
