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
import com.sae.persistence.domain.Regimen;
import com.sae.persistence.domain.ConceptoRetencion;
import com.sae.persistence.domain.CuentaRetencion;
import com.sae.persistence.domain.ValorRetencion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ValorRetencionJpaController implements Serializable {

    public ValorRetencionJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ValorRetencion valorRetencion) throws PreexistingEntityException, Exception {
        if (valorRetencion.getCuentaRetencionList() == null) {
            valorRetencion.setCuentaRetencionList(new ArrayList<CuentaRetencion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Regimen idRegimenNodeclarante = valorRetencion.getIdRegimenNodeclarante();
            if (idRegimenNodeclarante != null) {
                idRegimenNodeclarante = em.getReference(idRegimenNodeclarante.getClass(), idRegimenNodeclarante.getId());
                valorRetencion.setIdRegimenNodeclarante(idRegimenNodeclarante);
            }
            ConceptoRetencion idConcepto = valorRetencion.getIdConcepto();
            if (idConcepto != null) {
                idConcepto = em.getReference(idConcepto.getClass(), idConcepto.getId());
                valorRetencion.setIdConcepto(idConcepto);
            }
            List<CuentaRetencion> attachedCuentaRetencionList = new ArrayList<CuentaRetencion>();
            for (CuentaRetencion cuentaRetencionListCuentaRetencionToAttach : valorRetencion.getCuentaRetencionList()) {
                cuentaRetencionListCuentaRetencionToAttach = em.getReference(cuentaRetencionListCuentaRetencionToAttach.getClass(), cuentaRetencionListCuentaRetencionToAttach.getId());
                attachedCuentaRetencionList.add(cuentaRetencionListCuentaRetencionToAttach);
            }
            valorRetencion.setCuentaRetencionList(attachedCuentaRetencionList);
            em.persist(valorRetencion);
            if (idRegimenNodeclarante != null) {
                idRegimenNodeclarante.getValorRetencionList().add(valorRetencion);
                idRegimenNodeclarante = em.merge(idRegimenNodeclarante);
            }
            if (idConcepto != null) {
                idConcepto.getValorRetencionList().add(valorRetencion);
                idConcepto = em.merge(idConcepto);
            }
            for (CuentaRetencion cuentaRetencionListCuentaRetencion : valorRetencion.getCuentaRetencionList()) {
                ValorRetencion oldIdValorRetencionOfCuentaRetencionListCuentaRetencion = cuentaRetencionListCuentaRetencion.getIdValorRetencion();
                cuentaRetencionListCuentaRetencion.setIdValorRetencion(valorRetencion);
                cuentaRetencionListCuentaRetencion = em.merge(cuentaRetencionListCuentaRetencion);
                if (oldIdValorRetencionOfCuentaRetencionListCuentaRetencion != null) {
                    oldIdValorRetencionOfCuentaRetencionListCuentaRetencion.getCuentaRetencionList().remove(cuentaRetencionListCuentaRetencion);
                    oldIdValorRetencionOfCuentaRetencionListCuentaRetencion = em.merge(oldIdValorRetencionOfCuentaRetencionListCuentaRetencion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findValorRetencion(valorRetencion.getId()) != null) {
                throw new PreexistingEntityException("ValorRetencion " + valorRetencion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ValorRetencion valorRetencion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ValorRetencion persistentValorRetencion = em.find(ValorRetencion.class, valorRetencion.getId());
            Regimen idRegimenNodeclaranteOld = persistentValorRetencion.getIdRegimenNodeclarante();
            Regimen idRegimenNodeclaranteNew = valorRetencion.getIdRegimenNodeclarante();
            ConceptoRetencion idConceptoOld = persistentValorRetencion.getIdConcepto();
            ConceptoRetencion idConceptoNew = valorRetencion.getIdConcepto();
            List<CuentaRetencion> cuentaRetencionListOld = persistentValorRetencion.getCuentaRetencionList();
            List<CuentaRetencion> cuentaRetencionListNew = valorRetencion.getCuentaRetencionList();
            if (idRegimenNodeclaranteNew != null) {
                idRegimenNodeclaranteNew = em.getReference(idRegimenNodeclaranteNew.getClass(), idRegimenNodeclaranteNew.getId());
                valorRetencion.setIdRegimenNodeclarante(idRegimenNodeclaranteNew);
            }
            if (idConceptoNew != null) {
                idConceptoNew = em.getReference(idConceptoNew.getClass(), idConceptoNew.getId());
                valorRetencion.setIdConcepto(idConceptoNew);
            }
            List<CuentaRetencion> attachedCuentaRetencionListNew = new ArrayList<CuentaRetencion>();
            for (CuentaRetencion cuentaRetencionListNewCuentaRetencionToAttach : cuentaRetencionListNew) {
                cuentaRetencionListNewCuentaRetencionToAttach = em.getReference(cuentaRetencionListNewCuentaRetencionToAttach.getClass(), cuentaRetencionListNewCuentaRetencionToAttach.getId());
                attachedCuentaRetencionListNew.add(cuentaRetencionListNewCuentaRetencionToAttach);
            }
            cuentaRetencionListNew = attachedCuentaRetencionListNew;
            valorRetencion.setCuentaRetencionList(cuentaRetencionListNew);
            valorRetencion = em.merge(valorRetencion);
            if (idRegimenNodeclaranteOld != null && !idRegimenNodeclaranteOld.equals(idRegimenNodeclaranteNew)) {
                idRegimenNodeclaranteOld.getValorRetencionList().remove(valorRetencion);
                idRegimenNodeclaranteOld = em.merge(idRegimenNodeclaranteOld);
            }
            if (idRegimenNodeclaranteNew != null && !idRegimenNodeclaranteNew.equals(idRegimenNodeclaranteOld)) {
                idRegimenNodeclaranteNew.getValorRetencionList().add(valorRetencion);
                idRegimenNodeclaranteNew = em.merge(idRegimenNodeclaranteNew);
            }
            if (idConceptoOld != null && !idConceptoOld.equals(idConceptoNew)) {
                idConceptoOld.getValorRetencionList().remove(valorRetencion);
                idConceptoOld = em.merge(idConceptoOld);
            }
            if (idConceptoNew != null && !idConceptoNew.equals(idConceptoOld)) {
                idConceptoNew.getValorRetencionList().add(valorRetencion);
                idConceptoNew = em.merge(idConceptoNew);
            }
            for (CuentaRetencion cuentaRetencionListOldCuentaRetencion : cuentaRetencionListOld) {
                if (!cuentaRetencionListNew.contains(cuentaRetencionListOldCuentaRetencion)) {
                    cuentaRetencionListOldCuentaRetencion.setIdValorRetencion(null);
                    cuentaRetencionListOldCuentaRetencion = em.merge(cuentaRetencionListOldCuentaRetencion);
                }
            }
            for (CuentaRetencion cuentaRetencionListNewCuentaRetencion : cuentaRetencionListNew) {
                if (!cuentaRetencionListOld.contains(cuentaRetencionListNewCuentaRetencion)) {
                    ValorRetencion oldIdValorRetencionOfCuentaRetencionListNewCuentaRetencion = cuentaRetencionListNewCuentaRetencion.getIdValorRetencion();
                    cuentaRetencionListNewCuentaRetencion.setIdValorRetencion(valorRetencion);
                    cuentaRetencionListNewCuentaRetencion = em.merge(cuentaRetencionListNewCuentaRetencion);
                    if (oldIdValorRetencionOfCuentaRetencionListNewCuentaRetencion != null && !oldIdValorRetencionOfCuentaRetencionListNewCuentaRetencion.equals(valorRetencion)) {
                        oldIdValorRetencionOfCuentaRetencionListNewCuentaRetencion.getCuentaRetencionList().remove(cuentaRetencionListNewCuentaRetencion);
                        oldIdValorRetencionOfCuentaRetencionListNewCuentaRetencion = em.merge(oldIdValorRetencionOfCuentaRetencionListNewCuentaRetencion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = valorRetencion.getId();
                if (findValorRetencion(id) == null) {
                    throw new NonexistentEntityException("The valorRetencion with id " + id + " no longer exists.");
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
            ValorRetencion valorRetencion;
            try {
                valorRetencion = em.getReference(ValorRetencion.class, id);
                valorRetencion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The valorRetencion with id " + id + " no longer exists.", enfe);
            }
            Regimen idRegimenNodeclarante = valorRetencion.getIdRegimenNodeclarante();
            if (idRegimenNodeclarante != null) {
                idRegimenNodeclarante.getValorRetencionList().remove(valorRetencion);
                idRegimenNodeclarante = em.merge(idRegimenNodeclarante);
            }
            ConceptoRetencion idConcepto = valorRetencion.getIdConcepto();
            if (idConcepto != null) {
                idConcepto.getValorRetencionList().remove(valorRetencion);
                idConcepto = em.merge(idConcepto);
            }
            List<CuentaRetencion> cuentaRetencionList = valorRetencion.getCuentaRetencionList();
            for (CuentaRetencion cuentaRetencionListCuentaRetencion : cuentaRetencionList) {
                cuentaRetencionListCuentaRetencion.setIdValorRetencion(null);
                cuentaRetencionListCuentaRetencion = em.merge(cuentaRetencionListCuentaRetencion);
            }
            em.remove(valorRetencion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ValorRetencion> findValorRetencionEntities() {
        return findValorRetencionEntities(true, -1, -1);
    }

    public List<ValorRetencion> findValorRetencionEntities(int maxResults, int firstResult) {
        return findValorRetencionEntities(false, maxResults, firstResult);
    }

    private List<ValorRetencion> findValorRetencionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ValorRetencion.class));
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

    public ValorRetencion findValorRetencion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ValorRetencion.class, id);
        } finally {
            em.close();
        }
    }

    public int getValorRetencionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ValorRetencion> rt = cq.from(ValorRetencion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
