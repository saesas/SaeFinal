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
import com.sae.persistence.domain.Nomina;
import com.sae.persistence.domain.ContratoTercero;
import com.sae.persistence.domain.ValorPagarNomina;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ValorPagarNominaJpaController implements Serializable {

    public ValorPagarNominaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ValorPagarNomina valorPagarNomina) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Nomina idNomina = valorPagarNomina.getIdNomina();
            if (idNomina != null) {
                idNomina = em.getReference(idNomina.getClass(), idNomina.getId());
                valorPagarNomina.setIdNomina(idNomina);
            }
            ContratoTercero idContrato = valorPagarNomina.getIdContrato();
            if (idContrato != null) {
                idContrato = em.getReference(idContrato.getClass(), idContrato.getId());
                valorPagarNomina.setIdContrato(idContrato);
            }
            em.persist(valorPagarNomina);
            if (idNomina != null) {
                idNomina.getValorPagarNominaList().add(valorPagarNomina);
                idNomina = em.merge(idNomina);
            }
            if (idContrato != null) {
                idContrato.getValorPagarNominaList().add(valorPagarNomina);
                idContrato = em.merge(idContrato);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findValorPagarNomina(valorPagarNomina.getId()) != null) {
                throw new PreexistingEntityException("ValorPagarNomina " + valorPagarNomina + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ValorPagarNomina valorPagarNomina) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ValorPagarNomina persistentValorPagarNomina = em.find(ValorPagarNomina.class, valorPagarNomina.getId());
            Nomina idNominaOld = persistentValorPagarNomina.getIdNomina();
            Nomina idNominaNew = valorPagarNomina.getIdNomina();
            ContratoTercero idContratoOld = persistentValorPagarNomina.getIdContrato();
            ContratoTercero idContratoNew = valorPagarNomina.getIdContrato();
            if (idNominaNew != null) {
                idNominaNew = em.getReference(idNominaNew.getClass(), idNominaNew.getId());
                valorPagarNomina.setIdNomina(idNominaNew);
            }
            if (idContratoNew != null) {
                idContratoNew = em.getReference(idContratoNew.getClass(), idContratoNew.getId());
                valorPagarNomina.setIdContrato(idContratoNew);
            }
            valorPagarNomina = em.merge(valorPagarNomina);
            if (idNominaOld != null && !idNominaOld.equals(idNominaNew)) {
                idNominaOld.getValorPagarNominaList().remove(valorPagarNomina);
                idNominaOld = em.merge(idNominaOld);
            }
            if (idNominaNew != null && !idNominaNew.equals(idNominaOld)) {
                idNominaNew.getValorPagarNominaList().add(valorPagarNomina);
                idNominaNew = em.merge(idNominaNew);
            }
            if (idContratoOld != null && !idContratoOld.equals(idContratoNew)) {
                idContratoOld.getValorPagarNominaList().remove(valorPagarNomina);
                idContratoOld = em.merge(idContratoOld);
            }
            if (idContratoNew != null && !idContratoNew.equals(idContratoOld)) {
                idContratoNew.getValorPagarNominaList().add(valorPagarNomina);
                idContratoNew = em.merge(idContratoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = valorPagarNomina.getId();
                if (findValorPagarNomina(id) == null) {
                    throw new NonexistentEntityException("The valorPagarNomina with id " + id + " no longer exists.");
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
            ValorPagarNomina valorPagarNomina;
            try {
                valorPagarNomina = em.getReference(ValorPagarNomina.class, id);
                valorPagarNomina.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The valorPagarNomina with id " + id + " no longer exists.", enfe);
            }
            Nomina idNomina = valorPagarNomina.getIdNomina();
            if (idNomina != null) {
                idNomina.getValorPagarNominaList().remove(valorPagarNomina);
                idNomina = em.merge(idNomina);
            }
            ContratoTercero idContrato = valorPagarNomina.getIdContrato();
            if (idContrato != null) {
                idContrato.getValorPagarNominaList().remove(valorPagarNomina);
                idContrato = em.merge(idContrato);
            }
            em.remove(valorPagarNomina);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ValorPagarNomina> findValorPagarNominaEntities() {
        return findValorPagarNominaEntities(true, -1, -1);
    }

    public List<ValorPagarNomina> findValorPagarNominaEntities(int maxResults, int firstResult) {
        return findValorPagarNominaEntities(false, maxResults, firstResult);
    }

    private List<ValorPagarNomina> findValorPagarNominaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ValorPagarNomina.class));
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

    public ValorPagarNomina findValorPagarNomina(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ValorPagarNomina.class, id);
        } finally {
            em.close();
        }
    }

    public int getValorPagarNominaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ValorPagarNomina> rt = cq.from(ValorPagarNomina.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
