/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.Comprobante;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.TipoComprobante;
import com.sae.persistence.domain.ProcesoComprobante;
import com.sae.persistence.domain.Reintegro;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.ComprobanteCierreDetalle;
import com.sae.persistence.domain.MovimientoPuc;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ComprobanteJpaController implements Serializable {

    public ComprobanteJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comprobante comprobante) throws PreexistingEntityException, Exception {
        if (comprobante.getReintegroList() == null) {
            comprobante.setReintegroList(new ArrayList<Reintegro>());
        }
        if (comprobante.getComprobanteCierreDetalleList() == null) {
            comprobante.setComprobanteCierreDetalleList(new ArrayList<ComprobanteCierreDetalle>());
        }
        if (comprobante.getMovimientoPucList() == null) {
            comprobante.setMovimientoPucList(new ArrayList<MovimientoPuc>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoComprobante idTipo = comprobante.getIdTipo();
            if (idTipo != null) {
                idTipo = em.getReference(idTipo.getClass(), idTipo.getId());
                comprobante.setIdTipo(idTipo);
            }
            ProcesoComprobante idProceso = comprobante.getIdProceso();
            if (idProceso != null) {
                idProceso = em.getReference(idProceso.getClass(), idProceso.getId());
                comprobante.setIdProceso(idProceso);
            }
            List<Reintegro> attachedReintegroList = new ArrayList<Reintegro>();
            for (Reintegro reintegroListReintegroToAttach : comprobante.getReintegroList()) {
                reintegroListReintegroToAttach = em.getReference(reintegroListReintegroToAttach.getClass(), reintegroListReintegroToAttach.getId());
                attachedReintegroList.add(reintegroListReintegroToAttach);
            }
            comprobante.setReintegroList(attachedReintegroList);
            List<ComprobanteCierreDetalle> attachedComprobanteCierreDetalleList = new ArrayList<ComprobanteCierreDetalle>();
            for (ComprobanteCierreDetalle comprobanteCierreDetalleListComprobanteCierreDetalleToAttach : comprobante.getComprobanteCierreDetalleList()) {
                comprobanteCierreDetalleListComprobanteCierreDetalleToAttach = em.getReference(comprobanteCierreDetalleListComprobanteCierreDetalleToAttach.getClass(), comprobanteCierreDetalleListComprobanteCierreDetalleToAttach.getId());
                attachedComprobanteCierreDetalleList.add(comprobanteCierreDetalleListComprobanteCierreDetalleToAttach);
            }
            comprobante.setComprobanteCierreDetalleList(attachedComprobanteCierreDetalleList);
            List<MovimientoPuc> attachedMovimientoPucList = new ArrayList<MovimientoPuc>();
            for (MovimientoPuc movimientoPucListMovimientoPucToAttach : comprobante.getMovimientoPucList()) {
                movimientoPucListMovimientoPucToAttach = em.getReference(movimientoPucListMovimientoPucToAttach.getClass(), movimientoPucListMovimientoPucToAttach.getId());
                attachedMovimientoPucList.add(movimientoPucListMovimientoPucToAttach);
            }
            comprobante.setMovimientoPucList(attachedMovimientoPucList);
            em.persist(comprobante);
            if (idTipo != null) {
                idTipo.getComprobanteList().add(comprobante);
                idTipo = em.merge(idTipo);
            }
            if (idProceso != null) {
                idProceso.getComprobanteList().add(comprobante);
                idProceso = em.merge(idProceso);
            }
            for (Reintegro reintegroListReintegro : comprobante.getReintegroList()) {
                Comprobante oldIdComprobanteOfReintegroListReintegro = reintegroListReintegro.getIdComprobante();
                reintegroListReintegro.setIdComprobante(comprobante);
                reintegroListReintegro = em.merge(reintegroListReintegro);
                if (oldIdComprobanteOfReintegroListReintegro != null) {
                    oldIdComprobanteOfReintegroListReintegro.getReintegroList().remove(reintegroListReintegro);
                    oldIdComprobanteOfReintegroListReintegro = em.merge(oldIdComprobanteOfReintegroListReintegro);
                }
            }
            for (ComprobanteCierreDetalle comprobanteCierreDetalleListComprobanteCierreDetalle : comprobante.getComprobanteCierreDetalleList()) {
                Comprobante oldIdComprobanteOfComprobanteCierreDetalleListComprobanteCierreDetalle = comprobanteCierreDetalleListComprobanteCierreDetalle.getIdComprobante();
                comprobanteCierreDetalleListComprobanteCierreDetalle.setIdComprobante(comprobante);
                comprobanteCierreDetalleListComprobanteCierreDetalle = em.merge(comprobanteCierreDetalleListComprobanteCierreDetalle);
                if (oldIdComprobanteOfComprobanteCierreDetalleListComprobanteCierreDetalle != null) {
                    oldIdComprobanteOfComprobanteCierreDetalleListComprobanteCierreDetalle.getComprobanteCierreDetalleList().remove(comprobanteCierreDetalleListComprobanteCierreDetalle);
                    oldIdComprobanteOfComprobanteCierreDetalleListComprobanteCierreDetalle = em.merge(oldIdComprobanteOfComprobanteCierreDetalleListComprobanteCierreDetalle);
                }
            }
            for (MovimientoPuc movimientoPucListMovimientoPuc : comprobante.getMovimientoPucList()) {
                Comprobante oldIdComprobanteOfMovimientoPucListMovimientoPuc = movimientoPucListMovimientoPuc.getIdComprobante();
                movimientoPucListMovimientoPuc.setIdComprobante(comprobante);
                movimientoPucListMovimientoPuc = em.merge(movimientoPucListMovimientoPuc);
                if (oldIdComprobanteOfMovimientoPucListMovimientoPuc != null) {
                    oldIdComprobanteOfMovimientoPucListMovimientoPuc.getMovimientoPucList().remove(movimientoPucListMovimientoPuc);
                    oldIdComprobanteOfMovimientoPucListMovimientoPuc = em.merge(oldIdComprobanteOfMovimientoPucListMovimientoPuc);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findComprobante(comprobante.getId()) != null) {
                throw new PreexistingEntityException("Comprobante " + comprobante + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comprobante comprobante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comprobante persistentComprobante = em.find(Comprobante.class, comprobante.getId());
            TipoComprobante idTipoOld = persistentComprobante.getIdTipo();
            TipoComprobante idTipoNew = comprobante.getIdTipo();
            ProcesoComprobante idProcesoOld = persistentComprobante.getIdProceso();
            ProcesoComprobante idProcesoNew = comprobante.getIdProceso();
            List<Reintegro> reintegroListOld = persistentComprobante.getReintegroList();
            List<Reintegro> reintegroListNew = comprobante.getReintegroList();
            List<ComprobanteCierreDetalle> comprobanteCierreDetalleListOld = persistentComprobante.getComprobanteCierreDetalleList();
            List<ComprobanteCierreDetalle> comprobanteCierreDetalleListNew = comprobante.getComprobanteCierreDetalleList();
            List<MovimientoPuc> movimientoPucListOld = persistentComprobante.getMovimientoPucList();
            List<MovimientoPuc> movimientoPucListNew = comprobante.getMovimientoPucList();
            if (idTipoNew != null) {
                idTipoNew = em.getReference(idTipoNew.getClass(), idTipoNew.getId());
                comprobante.setIdTipo(idTipoNew);
            }
            if (idProcesoNew != null) {
                idProcesoNew = em.getReference(idProcesoNew.getClass(), idProcesoNew.getId());
                comprobante.setIdProceso(idProcesoNew);
            }
            List<Reintegro> attachedReintegroListNew = new ArrayList<Reintegro>();
            for (Reintegro reintegroListNewReintegroToAttach : reintegroListNew) {
                reintegroListNewReintegroToAttach = em.getReference(reintegroListNewReintegroToAttach.getClass(), reintegroListNewReintegroToAttach.getId());
                attachedReintegroListNew.add(reintegroListNewReintegroToAttach);
            }
            reintegroListNew = attachedReintegroListNew;
            comprobante.setReintegroList(reintegroListNew);
            List<ComprobanteCierreDetalle> attachedComprobanteCierreDetalleListNew = new ArrayList<ComprobanteCierreDetalle>();
            for (ComprobanteCierreDetalle comprobanteCierreDetalleListNewComprobanteCierreDetalleToAttach : comprobanteCierreDetalleListNew) {
                comprobanteCierreDetalleListNewComprobanteCierreDetalleToAttach = em.getReference(comprobanteCierreDetalleListNewComprobanteCierreDetalleToAttach.getClass(), comprobanteCierreDetalleListNewComprobanteCierreDetalleToAttach.getId());
                attachedComprobanteCierreDetalleListNew.add(comprobanteCierreDetalleListNewComprobanteCierreDetalleToAttach);
            }
            comprobanteCierreDetalleListNew = attachedComprobanteCierreDetalleListNew;
            comprobante.setComprobanteCierreDetalleList(comprobanteCierreDetalleListNew);
            List<MovimientoPuc> attachedMovimientoPucListNew = new ArrayList<MovimientoPuc>();
            for (MovimientoPuc movimientoPucListNewMovimientoPucToAttach : movimientoPucListNew) {
                movimientoPucListNewMovimientoPucToAttach = em.getReference(movimientoPucListNewMovimientoPucToAttach.getClass(), movimientoPucListNewMovimientoPucToAttach.getId());
                attachedMovimientoPucListNew.add(movimientoPucListNewMovimientoPucToAttach);
            }
            movimientoPucListNew = attachedMovimientoPucListNew;
            comprobante.setMovimientoPucList(movimientoPucListNew);
            comprobante = em.merge(comprobante);
            if (idTipoOld != null && !idTipoOld.equals(idTipoNew)) {
                idTipoOld.getComprobanteList().remove(comprobante);
                idTipoOld = em.merge(idTipoOld);
            }
            if (idTipoNew != null && !idTipoNew.equals(idTipoOld)) {
                idTipoNew.getComprobanteList().add(comprobante);
                idTipoNew = em.merge(idTipoNew);
            }
            if (idProcesoOld != null && !idProcesoOld.equals(idProcesoNew)) {
                idProcesoOld.getComprobanteList().remove(comprobante);
                idProcesoOld = em.merge(idProcesoOld);
            }
            if (idProcesoNew != null && !idProcesoNew.equals(idProcesoOld)) {
                idProcesoNew.getComprobanteList().add(comprobante);
                idProcesoNew = em.merge(idProcesoNew);
            }
            for (Reintegro reintegroListOldReintegro : reintegroListOld) {
                if (!reintegroListNew.contains(reintegroListOldReintegro)) {
                    reintegroListOldReintegro.setIdComprobante(null);
                    reintegroListOldReintegro = em.merge(reintegroListOldReintegro);
                }
            }
            for (Reintegro reintegroListNewReintegro : reintegroListNew) {
                if (!reintegroListOld.contains(reintegroListNewReintegro)) {
                    Comprobante oldIdComprobanteOfReintegroListNewReintegro = reintegroListNewReintegro.getIdComprobante();
                    reintegroListNewReintegro.setIdComprobante(comprobante);
                    reintegroListNewReintegro = em.merge(reintegroListNewReintegro);
                    if (oldIdComprobanteOfReintegroListNewReintegro != null && !oldIdComprobanteOfReintegroListNewReintegro.equals(comprobante)) {
                        oldIdComprobanteOfReintegroListNewReintegro.getReintegroList().remove(reintegroListNewReintegro);
                        oldIdComprobanteOfReintegroListNewReintegro = em.merge(oldIdComprobanteOfReintegroListNewReintegro);
                    }
                }
            }
            for (ComprobanteCierreDetalle comprobanteCierreDetalleListOldComprobanteCierreDetalle : comprobanteCierreDetalleListOld) {
                if (!comprobanteCierreDetalleListNew.contains(comprobanteCierreDetalleListOldComprobanteCierreDetalle)) {
                    comprobanteCierreDetalleListOldComprobanteCierreDetalle.setIdComprobante(null);
                    comprobanteCierreDetalleListOldComprobanteCierreDetalle = em.merge(comprobanteCierreDetalleListOldComprobanteCierreDetalle);
                }
            }
            for (ComprobanteCierreDetalle comprobanteCierreDetalleListNewComprobanteCierreDetalle : comprobanteCierreDetalleListNew) {
                if (!comprobanteCierreDetalleListOld.contains(comprobanteCierreDetalleListNewComprobanteCierreDetalle)) {
                    Comprobante oldIdComprobanteOfComprobanteCierreDetalleListNewComprobanteCierreDetalle = comprobanteCierreDetalleListNewComprobanteCierreDetalle.getIdComprobante();
                    comprobanteCierreDetalleListNewComprobanteCierreDetalle.setIdComprobante(comprobante);
                    comprobanteCierreDetalleListNewComprobanteCierreDetalle = em.merge(comprobanteCierreDetalleListNewComprobanteCierreDetalle);
                    if (oldIdComprobanteOfComprobanteCierreDetalleListNewComprobanteCierreDetalle != null && !oldIdComprobanteOfComprobanteCierreDetalleListNewComprobanteCierreDetalle.equals(comprobante)) {
                        oldIdComprobanteOfComprobanteCierreDetalleListNewComprobanteCierreDetalle.getComprobanteCierreDetalleList().remove(comprobanteCierreDetalleListNewComprobanteCierreDetalle);
                        oldIdComprobanteOfComprobanteCierreDetalleListNewComprobanteCierreDetalle = em.merge(oldIdComprobanteOfComprobanteCierreDetalleListNewComprobanteCierreDetalle);
                    }
                }
            }
            for (MovimientoPuc movimientoPucListOldMovimientoPuc : movimientoPucListOld) {
                if (!movimientoPucListNew.contains(movimientoPucListOldMovimientoPuc)) {
                    movimientoPucListOldMovimientoPuc.setIdComprobante(null);
                    movimientoPucListOldMovimientoPuc = em.merge(movimientoPucListOldMovimientoPuc);
                }
            }
            for (MovimientoPuc movimientoPucListNewMovimientoPuc : movimientoPucListNew) {
                if (!movimientoPucListOld.contains(movimientoPucListNewMovimientoPuc)) {
                    Comprobante oldIdComprobanteOfMovimientoPucListNewMovimientoPuc = movimientoPucListNewMovimientoPuc.getIdComprobante();
                    movimientoPucListNewMovimientoPuc.setIdComprobante(comprobante);
                    movimientoPucListNewMovimientoPuc = em.merge(movimientoPucListNewMovimientoPuc);
                    if (oldIdComprobanteOfMovimientoPucListNewMovimientoPuc != null && !oldIdComprobanteOfMovimientoPucListNewMovimientoPuc.equals(comprobante)) {
                        oldIdComprobanteOfMovimientoPucListNewMovimientoPuc.getMovimientoPucList().remove(movimientoPucListNewMovimientoPuc);
                        oldIdComprobanteOfMovimientoPucListNewMovimientoPuc = em.merge(oldIdComprobanteOfMovimientoPucListNewMovimientoPuc);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comprobante.getId();
                if (findComprobante(id) == null) {
                    throw new NonexistentEntityException("The comprobante with id " + id + " no longer exists.");
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
            Comprobante comprobante;
            try {
                comprobante = em.getReference(Comprobante.class, id);
                comprobante.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comprobante with id " + id + " no longer exists.", enfe);
            }
            TipoComprobante idTipo = comprobante.getIdTipo();
            if (idTipo != null) {
                idTipo.getComprobanteList().remove(comprobante);
                idTipo = em.merge(idTipo);
            }
            ProcesoComprobante idProceso = comprobante.getIdProceso();
            if (idProceso != null) {
                idProceso.getComprobanteList().remove(comprobante);
                idProceso = em.merge(idProceso);
            }
            List<Reintegro> reintegroList = comprobante.getReintegroList();
            for (Reintegro reintegroListReintegro : reintegroList) {
                reintegroListReintegro.setIdComprobante(null);
                reintegroListReintegro = em.merge(reintegroListReintegro);
            }
            List<ComprobanteCierreDetalle> comprobanteCierreDetalleList = comprobante.getComprobanteCierreDetalleList();
            for (ComprobanteCierreDetalle comprobanteCierreDetalleListComprobanteCierreDetalle : comprobanteCierreDetalleList) {
                comprobanteCierreDetalleListComprobanteCierreDetalle.setIdComprobante(null);
                comprobanteCierreDetalleListComprobanteCierreDetalle = em.merge(comprobanteCierreDetalleListComprobanteCierreDetalle);
            }
            List<MovimientoPuc> movimientoPucList = comprobante.getMovimientoPucList();
            for (MovimientoPuc movimientoPucListMovimientoPuc : movimientoPucList) {
                movimientoPucListMovimientoPuc.setIdComprobante(null);
                movimientoPucListMovimientoPuc = em.merge(movimientoPucListMovimientoPuc);
            }
            em.remove(comprobante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comprobante> findComprobanteEntities() {
        return findComprobanteEntities(true, -1, -1);
    }

    public List<Comprobante> findComprobanteEntities(int maxResults, int firstResult) {
        return findComprobanteEntities(false, maxResults, firstResult);
    }

    private List<Comprobante> findComprobanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comprobante.class));
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

    public Comprobante findComprobante(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comprobante.class, id);
        } finally {
            em.close();
        }
    }

    public int getComprobanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comprobante> rt = cq.from(Comprobante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
