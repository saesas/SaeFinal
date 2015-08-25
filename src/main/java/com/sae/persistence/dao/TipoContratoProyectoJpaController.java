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
import com.sae.persistence.domain.TipoContratista;
import com.sae.persistence.domain.TipoContratante;
import com.sae.persistence.domain.ClaseContratoProyecto;
import com.sae.persistence.domain.ContratoProyectoProveedor;
import com.sae.persistence.domain.TipoContratoProyecto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoContratoProyectoJpaController implements Serializable {

    public TipoContratoProyectoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoContratoProyecto tipoContratoProyecto) throws PreexistingEntityException, Exception {
        if (tipoContratoProyecto.getContratoProyectoProveedorList() == null) {
            tipoContratoProyecto.setContratoProyectoProveedorList(new ArrayList<ContratoProyectoProveedor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoContratista idTipoContratista = tipoContratoProyecto.getIdTipoContratista();
            if (idTipoContratista != null) {
                idTipoContratista = em.getReference(idTipoContratista.getClass(), idTipoContratista.getId());
                tipoContratoProyecto.setIdTipoContratista(idTipoContratista);
            }
            TipoContratante idTipoContratante = tipoContratoProyecto.getIdTipoContratante();
            if (idTipoContratante != null) {
                idTipoContratante = em.getReference(idTipoContratante.getClass(), idTipoContratante.getId());
                tipoContratoProyecto.setIdTipoContratante(idTipoContratante);
            }
            ClaseContratoProyecto idClase = tipoContratoProyecto.getIdClase();
            if (idClase != null) {
                idClase = em.getReference(idClase.getClass(), idClase.getId());
                tipoContratoProyecto.setIdClase(idClase);
            }
            List<ContratoProyectoProveedor> attachedContratoProyectoProveedorList = new ArrayList<ContratoProyectoProveedor>();
            for (ContratoProyectoProveedor contratoProyectoProveedorListContratoProyectoProveedorToAttach : tipoContratoProyecto.getContratoProyectoProveedorList()) {
                contratoProyectoProveedorListContratoProyectoProveedorToAttach = em.getReference(contratoProyectoProveedorListContratoProyectoProveedorToAttach.getClass(), contratoProyectoProveedorListContratoProyectoProveedorToAttach.getId());
                attachedContratoProyectoProveedorList.add(contratoProyectoProveedorListContratoProyectoProveedorToAttach);
            }
            tipoContratoProyecto.setContratoProyectoProveedorList(attachedContratoProyectoProveedorList);
            em.persist(tipoContratoProyecto);
            if (idTipoContratista != null) {
                idTipoContratista.getTipoContratoProyectoList().add(tipoContratoProyecto);
                idTipoContratista = em.merge(idTipoContratista);
            }
            if (idTipoContratante != null) {
                idTipoContratante.getTipoContratoProyectoList().add(tipoContratoProyecto);
                idTipoContratante = em.merge(idTipoContratante);
            }
            if (idClase != null) {
                idClase.getTipoContratoProyectoList().add(tipoContratoProyecto);
                idClase = em.merge(idClase);
            }
            for (ContratoProyectoProveedor contratoProyectoProveedorListContratoProyectoProveedor : tipoContratoProyecto.getContratoProyectoProveedorList()) {
                TipoContratoProyecto oldIdTipoOfContratoProyectoProveedorListContratoProyectoProveedor = contratoProyectoProveedorListContratoProyectoProveedor.getIdTipo();
                contratoProyectoProveedorListContratoProyectoProveedor.setIdTipo(tipoContratoProyecto);
                contratoProyectoProveedorListContratoProyectoProveedor = em.merge(contratoProyectoProveedorListContratoProyectoProveedor);
                if (oldIdTipoOfContratoProyectoProveedorListContratoProyectoProveedor != null) {
                    oldIdTipoOfContratoProyectoProveedorListContratoProyectoProveedor.getContratoProyectoProveedorList().remove(contratoProyectoProveedorListContratoProyectoProveedor);
                    oldIdTipoOfContratoProyectoProveedorListContratoProyectoProveedor = em.merge(oldIdTipoOfContratoProyectoProveedorListContratoProyectoProveedor);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoContratoProyecto(tipoContratoProyecto.getId()) != null) {
                throw new PreexistingEntityException("TipoContratoProyecto " + tipoContratoProyecto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoContratoProyecto tipoContratoProyecto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoContratoProyecto persistentTipoContratoProyecto = em.find(TipoContratoProyecto.class, tipoContratoProyecto.getId());
            TipoContratista idTipoContratistaOld = persistentTipoContratoProyecto.getIdTipoContratista();
            TipoContratista idTipoContratistaNew = tipoContratoProyecto.getIdTipoContratista();
            TipoContratante idTipoContratanteOld = persistentTipoContratoProyecto.getIdTipoContratante();
            TipoContratante idTipoContratanteNew = tipoContratoProyecto.getIdTipoContratante();
            ClaseContratoProyecto idClaseOld = persistentTipoContratoProyecto.getIdClase();
            ClaseContratoProyecto idClaseNew = tipoContratoProyecto.getIdClase();
            List<ContratoProyectoProveedor> contratoProyectoProveedorListOld = persistentTipoContratoProyecto.getContratoProyectoProveedorList();
            List<ContratoProyectoProveedor> contratoProyectoProveedorListNew = tipoContratoProyecto.getContratoProyectoProveedorList();
            if (idTipoContratistaNew != null) {
                idTipoContratistaNew = em.getReference(idTipoContratistaNew.getClass(), idTipoContratistaNew.getId());
                tipoContratoProyecto.setIdTipoContratista(idTipoContratistaNew);
            }
            if (idTipoContratanteNew != null) {
                idTipoContratanteNew = em.getReference(idTipoContratanteNew.getClass(), idTipoContratanteNew.getId());
                tipoContratoProyecto.setIdTipoContratante(idTipoContratanteNew);
            }
            if (idClaseNew != null) {
                idClaseNew = em.getReference(idClaseNew.getClass(), idClaseNew.getId());
                tipoContratoProyecto.setIdClase(idClaseNew);
            }
            List<ContratoProyectoProveedor> attachedContratoProyectoProveedorListNew = new ArrayList<ContratoProyectoProveedor>();
            for (ContratoProyectoProveedor contratoProyectoProveedorListNewContratoProyectoProveedorToAttach : contratoProyectoProveedorListNew) {
                contratoProyectoProveedorListNewContratoProyectoProveedorToAttach = em.getReference(contratoProyectoProveedorListNewContratoProyectoProveedorToAttach.getClass(), contratoProyectoProveedorListNewContratoProyectoProveedorToAttach.getId());
                attachedContratoProyectoProveedorListNew.add(contratoProyectoProveedorListNewContratoProyectoProveedorToAttach);
            }
            contratoProyectoProveedorListNew = attachedContratoProyectoProveedorListNew;
            tipoContratoProyecto.setContratoProyectoProveedorList(contratoProyectoProveedorListNew);
            tipoContratoProyecto = em.merge(tipoContratoProyecto);
            if (idTipoContratistaOld != null && !idTipoContratistaOld.equals(idTipoContratistaNew)) {
                idTipoContratistaOld.getTipoContratoProyectoList().remove(tipoContratoProyecto);
                idTipoContratistaOld = em.merge(idTipoContratistaOld);
            }
            if (idTipoContratistaNew != null && !idTipoContratistaNew.equals(idTipoContratistaOld)) {
                idTipoContratistaNew.getTipoContratoProyectoList().add(tipoContratoProyecto);
                idTipoContratistaNew = em.merge(idTipoContratistaNew);
            }
            if (idTipoContratanteOld != null && !idTipoContratanteOld.equals(idTipoContratanteNew)) {
                idTipoContratanteOld.getTipoContratoProyectoList().remove(tipoContratoProyecto);
                idTipoContratanteOld = em.merge(idTipoContratanteOld);
            }
            if (idTipoContratanteNew != null && !idTipoContratanteNew.equals(idTipoContratanteOld)) {
                idTipoContratanteNew.getTipoContratoProyectoList().add(tipoContratoProyecto);
                idTipoContratanteNew = em.merge(idTipoContratanteNew);
            }
            if (idClaseOld != null && !idClaseOld.equals(idClaseNew)) {
                idClaseOld.getTipoContratoProyectoList().remove(tipoContratoProyecto);
                idClaseOld = em.merge(idClaseOld);
            }
            if (idClaseNew != null && !idClaseNew.equals(idClaseOld)) {
                idClaseNew.getTipoContratoProyectoList().add(tipoContratoProyecto);
                idClaseNew = em.merge(idClaseNew);
            }
            for (ContratoProyectoProveedor contratoProyectoProveedorListOldContratoProyectoProveedor : contratoProyectoProveedorListOld) {
                if (!contratoProyectoProveedorListNew.contains(contratoProyectoProveedorListOldContratoProyectoProveedor)) {
                    contratoProyectoProveedorListOldContratoProyectoProveedor.setIdTipo(null);
                    contratoProyectoProveedorListOldContratoProyectoProveedor = em.merge(contratoProyectoProveedorListOldContratoProyectoProveedor);
                }
            }
            for (ContratoProyectoProveedor contratoProyectoProveedorListNewContratoProyectoProveedor : contratoProyectoProveedorListNew) {
                if (!contratoProyectoProveedorListOld.contains(contratoProyectoProveedorListNewContratoProyectoProveedor)) {
                    TipoContratoProyecto oldIdTipoOfContratoProyectoProveedorListNewContratoProyectoProveedor = contratoProyectoProveedorListNewContratoProyectoProveedor.getIdTipo();
                    contratoProyectoProveedorListNewContratoProyectoProveedor.setIdTipo(tipoContratoProyecto);
                    contratoProyectoProveedorListNewContratoProyectoProveedor = em.merge(contratoProyectoProveedorListNewContratoProyectoProveedor);
                    if (oldIdTipoOfContratoProyectoProveedorListNewContratoProyectoProveedor != null && !oldIdTipoOfContratoProyectoProveedorListNewContratoProyectoProveedor.equals(tipoContratoProyecto)) {
                        oldIdTipoOfContratoProyectoProveedorListNewContratoProyectoProveedor.getContratoProyectoProveedorList().remove(contratoProyectoProveedorListNewContratoProyectoProveedor);
                        oldIdTipoOfContratoProyectoProveedorListNewContratoProyectoProveedor = em.merge(oldIdTipoOfContratoProyectoProveedorListNewContratoProyectoProveedor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoContratoProyecto.getId();
                if (findTipoContratoProyecto(id) == null) {
                    throw new NonexistentEntityException("The tipoContratoProyecto with id " + id + " no longer exists.");
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
            TipoContratoProyecto tipoContratoProyecto;
            try {
                tipoContratoProyecto = em.getReference(TipoContratoProyecto.class, id);
                tipoContratoProyecto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoContratoProyecto with id " + id + " no longer exists.", enfe);
            }
            TipoContratista idTipoContratista = tipoContratoProyecto.getIdTipoContratista();
            if (idTipoContratista != null) {
                idTipoContratista.getTipoContratoProyectoList().remove(tipoContratoProyecto);
                idTipoContratista = em.merge(idTipoContratista);
            }
            TipoContratante idTipoContratante = tipoContratoProyecto.getIdTipoContratante();
            if (idTipoContratante != null) {
                idTipoContratante.getTipoContratoProyectoList().remove(tipoContratoProyecto);
                idTipoContratante = em.merge(idTipoContratante);
            }
            ClaseContratoProyecto idClase = tipoContratoProyecto.getIdClase();
            if (idClase != null) {
                idClase.getTipoContratoProyectoList().remove(tipoContratoProyecto);
                idClase = em.merge(idClase);
            }
            List<ContratoProyectoProveedor> contratoProyectoProveedorList = tipoContratoProyecto.getContratoProyectoProveedorList();
            for (ContratoProyectoProveedor contratoProyectoProveedorListContratoProyectoProveedor : contratoProyectoProveedorList) {
                contratoProyectoProveedorListContratoProyectoProveedor.setIdTipo(null);
                contratoProyectoProveedorListContratoProyectoProveedor = em.merge(contratoProyectoProveedorListContratoProyectoProveedor);
            }
            em.remove(tipoContratoProyecto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoContratoProyecto> findTipoContratoProyectoEntities() {
        return findTipoContratoProyectoEntities(true, -1, -1);
    }

    public List<TipoContratoProyecto> findTipoContratoProyectoEntities(int maxResults, int firstResult) {
        return findTipoContratoProyectoEntities(false, maxResults, firstResult);
    }

    private List<TipoContratoProyecto> findTipoContratoProyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoContratoProyecto.class));
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

    public TipoContratoProyecto findTipoContratoProyecto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoContratoProyecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoContratoProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoContratoProyecto> rt = cq.from(TipoContratoProyecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
