/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.ActaInicio;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.ContratoProyectoProveedor;
import com.sae.persistence.domain.AdjuntoTecnica;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ActaInicioJpaController implements Serializable {

    public ActaInicioJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ActaInicio actaInicio) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ContratoProyectoProveedor idContrato = actaInicio.getIdContrato();
            if (idContrato != null) {
                idContrato = em.getReference(idContrato.getClass(), idContrato.getId());
                actaInicio.setIdContrato(idContrato);
            }
            AdjuntoTecnica adjuntoTecnica = actaInicio.getAdjuntoTecnica();
            if (adjuntoTecnica != null) {
                adjuntoTecnica = em.getReference(adjuntoTecnica.getClass(), adjuntoTecnica.getId());
                actaInicio.setAdjuntoTecnica(adjuntoTecnica);
            }
            em.persist(actaInicio);
            if (idContrato != null) {
                idContrato.getActaInicioList().add(actaInicio);
                idContrato = em.merge(idContrato);
            }
            if (adjuntoTecnica != null) {
                adjuntoTecnica.getActaInicioList().add(actaInicio);
                adjuntoTecnica = em.merge(adjuntoTecnica);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findActaInicio(actaInicio.getId()) != null) {
                throw new PreexistingEntityException("ActaInicio " + actaInicio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ActaInicio actaInicio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ActaInicio persistentActaInicio = em.find(ActaInicio.class, actaInicio.getId());
            ContratoProyectoProveedor idContratoOld = persistentActaInicio.getIdContrato();
            ContratoProyectoProveedor idContratoNew = actaInicio.getIdContrato();
            AdjuntoTecnica adjuntoTecnicaOld = persistentActaInicio.getAdjuntoTecnica();
            AdjuntoTecnica adjuntoTecnicaNew = actaInicio.getAdjuntoTecnica();
            if (idContratoNew != null) {
                idContratoNew = em.getReference(idContratoNew.getClass(), idContratoNew.getId());
                actaInicio.setIdContrato(idContratoNew);
            }
            if (adjuntoTecnicaNew != null) {
                adjuntoTecnicaNew = em.getReference(adjuntoTecnicaNew.getClass(), adjuntoTecnicaNew.getId());
                actaInicio.setAdjuntoTecnica(adjuntoTecnicaNew);
            }
            actaInicio = em.merge(actaInicio);
            if (idContratoOld != null && !idContratoOld.equals(idContratoNew)) {
                idContratoOld.getActaInicioList().remove(actaInicio);
                idContratoOld = em.merge(idContratoOld);
            }
            if (idContratoNew != null && !idContratoNew.equals(idContratoOld)) {
                idContratoNew.getActaInicioList().add(actaInicio);
                idContratoNew = em.merge(idContratoNew);
            }
            if (adjuntoTecnicaOld != null && !adjuntoTecnicaOld.equals(adjuntoTecnicaNew)) {
                adjuntoTecnicaOld.getActaInicioList().remove(actaInicio);
                adjuntoTecnicaOld = em.merge(adjuntoTecnicaOld);
            }
            if (adjuntoTecnicaNew != null && !adjuntoTecnicaNew.equals(adjuntoTecnicaOld)) {
                adjuntoTecnicaNew.getActaInicioList().add(actaInicio);
                adjuntoTecnicaNew = em.merge(adjuntoTecnicaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = actaInicio.getId();
                if (findActaInicio(id) == null) {
                    throw new NonexistentEntityException("The actaInicio with id " + id + " no longer exists.");
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
            ActaInicio actaInicio;
            try {
                actaInicio = em.getReference(ActaInicio.class, id);
                actaInicio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actaInicio with id " + id + " no longer exists.", enfe);
            }
            ContratoProyectoProveedor idContrato = actaInicio.getIdContrato();
            if (idContrato != null) {
                idContrato.getActaInicioList().remove(actaInicio);
                idContrato = em.merge(idContrato);
            }
            AdjuntoTecnica adjuntoTecnica = actaInicio.getAdjuntoTecnica();
            if (adjuntoTecnica != null) {
                adjuntoTecnica.getActaInicioList().remove(actaInicio);
                adjuntoTecnica = em.merge(adjuntoTecnica);
            }
            em.remove(actaInicio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ActaInicio> findActaInicioEntities() {
        return findActaInicioEntities(true, -1, -1);
    }

    public List<ActaInicio> findActaInicioEntities(int maxResults, int firstResult) {
        return findActaInicioEntities(false, maxResults, firstResult);
    }

    private List<ActaInicio> findActaInicioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ActaInicio.class));
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

    public ActaInicio findActaInicio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ActaInicio.class, id);
        } finally {
            em.close();
        }
    }

    public int getActaInicioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ActaInicio> rt = cq.from(ActaInicio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
