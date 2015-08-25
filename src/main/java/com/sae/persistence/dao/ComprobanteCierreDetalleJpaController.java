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
import com.sae.persistence.domain.Puc;
import com.sae.persistence.domain.Comprobante;
import com.sae.persistence.domain.ComprobanteCierreDetalle;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ComprobanteCierreDetalleJpaController implements Serializable {

    public ComprobanteCierreDetalleJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ComprobanteCierreDetalle comprobanteCierreDetalle) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Puc idCuentaHasta = comprobanteCierreDetalle.getIdCuentaHasta();
            if (idCuentaHasta != null) {
                idCuentaHasta = em.getReference(idCuentaHasta.getClass(), idCuentaHasta.getId());
                comprobanteCierreDetalle.setIdCuentaHasta(idCuentaHasta);
            }
            Puc idCuentaDestino = comprobanteCierreDetalle.getIdCuentaDestino();
            if (idCuentaDestino != null) {
                idCuentaDestino = em.getReference(idCuentaDestino.getClass(), idCuentaDestino.getId());
                comprobanteCierreDetalle.setIdCuentaDestino(idCuentaDestino);
            }
            Puc idCuentaDesde = comprobanteCierreDetalle.getIdCuentaDesde();
            if (idCuentaDesde != null) {
                idCuentaDesde = em.getReference(idCuentaDesde.getClass(), idCuentaDesde.getId());
                comprobanteCierreDetalle.setIdCuentaDesde(idCuentaDesde);
            }
            Comprobante idComprobante = comprobanteCierreDetalle.getIdComprobante();
            if (idComprobante != null) {
                idComprobante = em.getReference(idComprobante.getClass(), idComprobante.getId());
                comprobanteCierreDetalle.setIdComprobante(idComprobante);
            }
            em.persist(comprobanteCierreDetalle);
            if (idCuentaHasta != null) {
                idCuentaHasta.getComprobanteCierreDetalleList().add(comprobanteCierreDetalle);
                idCuentaHasta = em.merge(idCuentaHasta);
            }
            if (idCuentaDestino != null) {
                idCuentaDestino.getComprobanteCierreDetalleList().add(comprobanteCierreDetalle);
                idCuentaDestino = em.merge(idCuentaDestino);
            }
            if (idCuentaDesde != null) {
                idCuentaDesde.getComprobanteCierreDetalleList().add(comprobanteCierreDetalle);
                idCuentaDesde = em.merge(idCuentaDesde);
            }
            if (idComprobante != null) {
                idComprobante.getComprobanteCierreDetalleList().add(comprobanteCierreDetalle);
                idComprobante = em.merge(idComprobante);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findComprobanteCierreDetalle(comprobanteCierreDetalle.getId()) != null) {
                throw new PreexistingEntityException("ComprobanteCierreDetalle " + comprobanteCierreDetalle + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ComprobanteCierreDetalle comprobanteCierreDetalle) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ComprobanteCierreDetalle persistentComprobanteCierreDetalle = em.find(ComprobanteCierreDetalle.class, comprobanteCierreDetalle.getId());
            Puc idCuentaHastaOld = persistentComprobanteCierreDetalle.getIdCuentaHasta();
            Puc idCuentaHastaNew = comprobanteCierreDetalle.getIdCuentaHasta();
            Puc idCuentaDestinoOld = persistentComprobanteCierreDetalle.getIdCuentaDestino();
            Puc idCuentaDestinoNew = comprobanteCierreDetalle.getIdCuentaDestino();
            Puc idCuentaDesdeOld = persistentComprobanteCierreDetalle.getIdCuentaDesde();
            Puc idCuentaDesdeNew = comprobanteCierreDetalle.getIdCuentaDesde();
            Comprobante idComprobanteOld = persistentComprobanteCierreDetalle.getIdComprobante();
            Comprobante idComprobanteNew = comprobanteCierreDetalle.getIdComprobante();
            if (idCuentaHastaNew != null) {
                idCuentaHastaNew = em.getReference(idCuentaHastaNew.getClass(), idCuentaHastaNew.getId());
                comprobanteCierreDetalle.setIdCuentaHasta(idCuentaHastaNew);
            }
            if (idCuentaDestinoNew != null) {
                idCuentaDestinoNew = em.getReference(idCuentaDestinoNew.getClass(), idCuentaDestinoNew.getId());
                comprobanteCierreDetalle.setIdCuentaDestino(idCuentaDestinoNew);
            }
            if (idCuentaDesdeNew != null) {
                idCuentaDesdeNew = em.getReference(idCuentaDesdeNew.getClass(), idCuentaDesdeNew.getId());
                comprobanteCierreDetalle.setIdCuentaDesde(idCuentaDesdeNew);
            }
            if (idComprobanteNew != null) {
                idComprobanteNew = em.getReference(idComprobanteNew.getClass(), idComprobanteNew.getId());
                comprobanteCierreDetalle.setIdComprobante(idComprobanteNew);
            }
            comprobanteCierreDetalle = em.merge(comprobanteCierreDetalle);
            if (idCuentaHastaOld != null && !idCuentaHastaOld.equals(idCuentaHastaNew)) {
                idCuentaHastaOld.getComprobanteCierreDetalleList().remove(comprobanteCierreDetalle);
                idCuentaHastaOld = em.merge(idCuentaHastaOld);
            }
            if (idCuentaHastaNew != null && !idCuentaHastaNew.equals(idCuentaHastaOld)) {
                idCuentaHastaNew.getComprobanteCierreDetalleList().add(comprobanteCierreDetalle);
                idCuentaHastaNew = em.merge(idCuentaHastaNew);
            }
            if (idCuentaDestinoOld != null && !idCuentaDestinoOld.equals(idCuentaDestinoNew)) {
                idCuentaDestinoOld.getComprobanteCierreDetalleList().remove(comprobanteCierreDetalle);
                idCuentaDestinoOld = em.merge(idCuentaDestinoOld);
            }
            if (idCuentaDestinoNew != null && !idCuentaDestinoNew.equals(idCuentaDestinoOld)) {
                idCuentaDestinoNew.getComprobanteCierreDetalleList().add(comprobanteCierreDetalle);
                idCuentaDestinoNew = em.merge(idCuentaDestinoNew);
            }
            if (idCuentaDesdeOld != null && !idCuentaDesdeOld.equals(idCuentaDesdeNew)) {
                idCuentaDesdeOld.getComprobanteCierreDetalleList().remove(comprobanteCierreDetalle);
                idCuentaDesdeOld = em.merge(idCuentaDesdeOld);
            }
            if (idCuentaDesdeNew != null && !idCuentaDesdeNew.equals(idCuentaDesdeOld)) {
                idCuentaDesdeNew.getComprobanteCierreDetalleList().add(comprobanteCierreDetalle);
                idCuentaDesdeNew = em.merge(idCuentaDesdeNew);
            }
            if (idComprobanteOld != null && !idComprobanteOld.equals(idComprobanteNew)) {
                idComprobanteOld.getComprobanteCierreDetalleList().remove(comprobanteCierreDetalle);
                idComprobanteOld = em.merge(idComprobanteOld);
            }
            if (idComprobanteNew != null && !idComprobanteNew.equals(idComprobanteOld)) {
                idComprobanteNew.getComprobanteCierreDetalleList().add(comprobanteCierreDetalle);
                idComprobanteNew = em.merge(idComprobanteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comprobanteCierreDetalle.getId();
                if (findComprobanteCierreDetalle(id) == null) {
                    throw new NonexistentEntityException("The comprobanteCierreDetalle with id " + id + " no longer exists.");
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
            ComprobanteCierreDetalle comprobanteCierreDetalle;
            try {
                comprobanteCierreDetalle = em.getReference(ComprobanteCierreDetalle.class, id);
                comprobanteCierreDetalle.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comprobanteCierreDetalle with id " + id + " no longer exists.", enfe);
            }
            Puc idCuentaHasta = comprobanteCierreDetalle.getIdCuentaHasta();
            if (idCuentaHasta != null) {
                idCuentaHasta.getComprobanteCierreDetalleList().remove(comprobanteCierreDetalle);
                idCuentaHasta = em.merge(idCuentaHasta);
            }
            Puc idCuentaDestino = comprobanteCierreDetalle.getIdCuentaDestino();
            if (idCuentaDestino != null) {
                idCuentaDestino.getComprobanteCierreDetalleList().remove(comprobanteCierreDetalle);
                idCuentaDestino = em.merge(idCuentaDestino);
            }
            Puc idCuentaDesde = comprobanteCierreDetalle.getIdCuentaDesde();
            if (idCuentaDesde != null) {
                idCuentaDesde.getComprobanteCierreDetalleList().remove(comprobanteCierreDetalle);
                idCuentaDesde = em.merge(idCuentaDesde);
            }
            Comprobante idComprobante = comprobanteCierreDetalle.getIdComprobante();
            if (idComprobante != null) {
                idComprobante.getComprobanteCierreDetalleList().remove(comprobanteCierreDetalle);
                idComprobante = em.merge(idComprobante);
            }
            em.remove(comprobanteCierreDetalle);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ComprobanteCierreDetalle> findComprobanteCierreDetalleEntities() {
        return findComprobanteCierreDetalleEntities(true, -1, -1);
    }

    public List<ComprobanteCierreDetalle> findComprobanteCierreDetalleEntities(int maxResults, int firstResult) {
        return findComprobanteCierreDetalleEntities(false, maxResults, firstResult);
    }

    private List<ComprobanteCierreDetalle> findComprobanteCierreDetalleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ComprobanteCierreDetalle.class));
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

    public ComprobanteCierreDetalle findComprobanteCierreDetalle(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ComprobanteCierreDetalle.class, id);
        } finally {
            em.close();
        }
    }

    public int getComprobanteCierreDetalleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ComprobanteCierreDetalle> rt = cq.from(ComprobanteCierreDetalle.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
