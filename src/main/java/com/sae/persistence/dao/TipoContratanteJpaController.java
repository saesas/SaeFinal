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
import com.sae.persistence.domain.TipoContratoProyecto;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.ContratoProyectoProveedor;
import com.sae.persistence.domain.TipoContratante;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoContratanteJpaController implements Serializable {

    public TipoContratanteJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoContratante tipoContratante) throws PreexistingEntityException, Exception {
        if (tipoContratante.getTipoContratoProyectoList() == null) {
            tipoContratante.setTipoContratoProyectoList(new ArrayList<TipoContratoProyecto>());
        }
        if (tipoContratante.getContratoProyectoProveedorList() == null) {
            tipoContratante.setContratoProyectoProveedorList(new ArrayList<ContratoProyectoProveedor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<TipoContratoProyecto> attachedTipoContratoProyectoList = new ArrayList<TipoContratoProyecto>();
            for (TipoContratoProyecto tipoContratoProyectoListTipoContratoProyectoToAttach : tipoContratante.getTipoContratoProyectoList()) {
                tipoContratoProyectoListTipoContratoProyectoToAttach = em.getReference(tipoContratoProyectoListTipoContratoProyectoToAttach.getClass(), tipoContratoProyectoListTipoContratoProyectoToAttach.getId());
                attachedTipoContratoProyectoList.add(tipoContratoProyectoListTipoContratoProyectoToAttach);
            }
            tipoContratante.setTipoContratoProyectoList(attachedTipoContratoProyectoList);
            List<ContratoProyectoProveedor> attachedContratoProyectoProveedorList = new ArrayList<ContratoProyectoProveedor>();
            for (ContratoProyectoProveedor contratoProyectoProveedorListContratoProyectoProveedorToAttach : tipoContratante.getContratoProyectoProveedorList()) {
                contratoProyectoProveedorListContratoProyectoProveedorToAttach = em.getReference(contratoProyectoProveedorListContratoProyectoProveedorToAttach.getClass(), contratoProyectoProveedorListContratoProyectoProveedorToAttach.getId());
                attachedContratoProyectoProveedorList.add(contratoProyectoProveedorListContratoProyectoProveedorToAttach);
            }
            tipoContratante.setContratoProyectoProveedorList(attachedContratoProyectoProveedorList);
            em.persist(tipoContratante);
            for (TipoContratoProyecto tipoContratoProyectoListTipoContratoProyecto : tipoContratante.getTipoContratoProyectoList()) {
                TipoContratante oldIdTipoContratanteOfTipoContratoProyectoListTipoContratoProyecto = tipoContratoProyectoListTipoContratoProyecto.getIdTipoContratante();
                tipoContratoProyectoListTipoContratoProyecto.setIdTipoContratante(tipoContratante);
                tipoContratoProyectoListTipoContratoProyecto = em.merge(tipoContratoProyectoListTipoContratoProyecto);
                if (oldIdTipoContratanteOfTipoContratoProyectoListTipoContratoProyecto != null) {
                    oldIdTipoContratanteOfTipoContratoProyectoListTipoContratoProyecto.getTipoContratoProyectoList().remove(tipoContratoProyectoListTipoContratoProyecto);
                    oldIdTipoContratanteOfTipoContratoProyectoListTipoContratoProyecto = em.merge(oldIdTipoContratanteOfTipoContratoProyectoListTipoContratoProyecto);
                }
            }
            for (ContratoProyectoProveedor contratoProyectoProveedorListContratoProyectoProveedor : tipoContratante.getContratoProyectoProveedorList()) {
                TipoContratante oldIdTipoContratanteOfContratoProyectoProveedorListContratoProyectoProveedor = contratoProyectoProveedorListContratoProyectoProveedor.getIdTipoContratante();
                contratoProyectoProveedorListContratoProyectoProveedor.setIdTipoContratante(tipoContratante);
                contratoProyectoProveedorListContratoProyectoProveedor = em.merge(contratoProyectoProveedorListContratoProyectoProveedor);
                if (oldIdTipoContratanteOfContratoProyectoProveedorListContratoProyectoProveedor != null) {
                    oldIdTipoContratanteOfContratoProyectoProveedorListContratoProyectoProveedor.getContratoProyectoProveedorList().remove(contratoProyectoProveedorListContratoProyectoProveedor);
                    oldIdTipoContratanteOfContratoProyectoProveedorListContratoProyectoProveedor = em.merge(oldIdTipoContratanteOfContratoProyectoProveedorListContratoProyectoProveedor);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoContratante(tipoContratante.getId()) != null) {
                throw new PreexistingEntityException("TipoContratante " + tipoContratante + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoContratante tipoContratante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoContratante persistentTipoContratante = em.find(TipoContratante.class, tipoContratante.getId());
            List<TipoContratoProyecto> tipoContratoProyectoListOld = persistentTipoContratante.getTipoContratoProyectoList();
            List<TipoContratoProyecto> tipoContratoProyectoListNew = tipoContratante.getTipoContratoProyectoList();
            List<ContratoProyectoProveedor> contratoProyectoProveedorListOld = persistentTipoContratante.getContratoProyectoProveedorList();
            List<ContratoProyectoProveedor> contratoProyectoProveedorListNew = tipoContratante.getContratoProyectoProveedorList();
            List<TipoContratoProyecto> attachedTipoContratoProyectoListNew = new ArrayList<TipoContratoProyecto>();
            for (TipoContratoProyecto tipoContratoProyectoListNewTipoContratoProyectoToAttach : tipoContratoProyectoListNew) {
                tipoContratoProyectoListNewTipoContratoProyectoToAttach = em.getReference(tipoContratoProyectoListNewTipoContratoProyectoToAttach.getClass(), tipoContratoProyectoListNewTipoContratoProyectoToAttach.getId());
                attachedTipoContratoProyectoListNew.add(tipoContratoProyectoListNewTipoContratoProyectoToAttach);
            }
            tipoContratoProyectoListNew = attachedTipoContratoProyectoListNew;
            tipoContratante.setTipoContratoProyectoList(tipoContratoProyectoListNew);
            List<ContratoProyectoProveedor> attachedContratoProyectoProveedorListNew = new ArrayList<ContratoProyectoProveedor>();
            for (ContratoProyectoProveedor contratoProyectoProveedorListNewContratoProyectoProveedorToAttach : contratoProyectoProveedorListNew) {
                contratoProyectoProveedorListNewContratoProyectoProveedorToAttach = em.getReference(contratoProyectoProveedorListNewContratoProyectoProveedorToAttach.getClass(), contratoProyectoProveedorListNewContratoProyectoProveedorToAttach.getId());
                attachedContratoProyectoProveedorListNew.add(contratoProyectoProveedorListNewContratoProyectoProveedorToAttach);
            }
            contratoProyectoProveedorListNew = attachedContratoProyectoProveedorListNew;
            tipoContratante.setContratoProyectoProveedorList(contratoProyectoProveedorListNew);
            tipoContratante = em.merge(tipoContratante);
            for (TipoContratoProyecto tipoContratoProyectoListOldTipoContratoProyecto : tipoContratoProyectoListOld) {
                if (!tipoContratoProyectoListNew.contains(tipoContratoProyectoListOldTipoContratoProyecto)) {
                    tipoContratoProyectoListOldTipoContratoProyecto.setIdTipoContratante(null);
                    tipoContratoProyectoListOldTipoContratoProyecto = em.merge(tipoContratoProyectoListOldTipoContratoProyecto);
                }
            }
            for (TipoContratoProyecto tipoContratoProyectoListNewTipoContratoProyecto : tipoContratoProyectoListNew) {
                if (!tipoContratoProyectoListOld.contains(tipoContratoProyectoListNewTipoContratoProyecto)) {
                    TipoContratante oldIdTipoContratanteOfTipoContratoProyectoListNewTipoContratoProyecto = tipoContratoProyectoListNewTipoContratoProyecto.getIdTipoContratante();
                    tipoContratoProyectoListNewTipoContratoProyecto.setIdTipoContratante(tipoContratante);
                    tipoContratoProyectoListNewTipoContratoProyecto = em.merge(tipoContratoProyectoListNewTipoContratoProyecto);
                    if (oldIdTipoContratanteOfTipoContratoProyectoListNewTipoContratoProyecto != null && !oldIdTipoContratanteOfTipoContratoProyectoListNewTipoContratoProyecto.equals(tipoContratante)) {
                        oldIdTipoContratanteOfTipoContratoProyectoListNewTipoContratoProyecto.getTipoContratoProyectoList().remove(tipoContratoProyectoListNewTipoContratoProyecto);
                        oldIdTipoContratanteOfTipoContratoProyectoListNewTipoContratoProyecto = em.merge(oldIdTipoContratanteOfTipoContratoProyectoListNewTipoContratoProyecto);
                    }
                }
            }
            for (ContratoProyectoProveedor contratoProyectoProveedorListOldContratoProyectoProveedor : contratoProyectoProveedorListOld) {
                if (!contratoProyectoProveedorListNew.contains(contratoProyectoProveedorListOldContratoProyectoProveedor)) {
                    contratoProyectoProveedorListOldContratoProyectoProveedor.setIdTipoContratante(null);
                    contratoProyectoProveedorListOldContratoProyectoProveedor = em.merge(contratoProyectoProveedorListOldContratoProyectoProveedor);
                }
            }
            for (ContratoProyectoProveedor contratoProyectoProveedorListNewContratoProyectoProveedor : contratoProyectoProveedorListNew) {
                if (!contratoProyectoProveedorListOld.contains(contratoProyectoProveedorListNewContratoProyectoProveedor)) {
                    TipoContratante oldIdTipoContratanteOfContratoProyectoProveedorListNewContratoProyectoProveedor = contratoProyectoProveedorListNewContratoProyectoProveedor.getIdTipoContratante();
                    contratoProyectoProveedorListNewContratoProyectoProveedor.setIdTipoContratante(tipoContratante);
                    contratoProyectoProveedorListNewContratoProyectoProveedor = em.merge(contratoProyectoProveedorListNewContratoProyectoProveedor);
                    if (oldIdTipoContratanteOfContratoProyectoProveedorListNewContratoProyectoProveedor != null && !oldIdTipoContratanteOfContratoProyectoProveedorListNewContratoProyectoProveedor.equals(tipoContratante)) {
                        oldIdTipoContratanteOfContratoProyectoProveedorListNewContratoProyectoProveedor.getContratoProyectoProveedorList().remove(contratoProyectoProveedorListNewContratoProyectoProveedor);
                        oldIdTipoContratanteOfContratoProyectoProveedorListNewContratoProyectoProveedor = em.merge(oldIdTipoContratanteOfContratoProyectoProveedorListNewContratoProyectoProveedor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoContratante.getId();
                if (findTipoContratante(id) == null) {
                    throw new NonexistentEntityException("The tipoContratante with id " + id + " no longer exists.");
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
            TipoContratante tipoContratante;
            try {
                tipoContratante = em.getReference(TipoContratante.class, id);
                tipoContratante.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoContratante with id " + id + " no longer exists.", enfe);
            }
            List<TipoContratoProyecto> tipoContratoProyectoList = tipoContratante.getTipoContratoProyectoList();
            for (TipoContratoProyecto tipoContratoProyectoListTipoContratoProyecto : tipoContratoProyectoList) {
                tipoContratoProyectoListTipoContratoProyecto.setIdTipoContratante(null);
                tipoContratoProyectoListTipoContratoProyecto = em.merge(tipoContratoProyectoListTipoContratoProyecto);
            }
            List<ContratoProyectoProveedor> contratoProyectoProveedorList = tipoContratante.getContratoProyectoProveedorList();
            for (ContratoProyectoProveedor contratoProyectoProveedorListContratoProyectoProveedor : contratoProyectoProveedorList) {
                contratoProyectoProveedorListContratoProyectoProveedor.setIdTipoContratante(null);
                contratoProyectoProveedorListContratoProyectoProveedor = em.merge(contratoProyectoProveedorListContratoProyectoProveedor);
            }
            em.remove(tipoContratante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoContratante> findTipoContratanteEntities() {
        return findTipoContratanteEntities(true, -1, -1);
    }

    public List<TipoContratante> findTipoContratanteEntities(int maxResults, int firstResult) {
        return findTipoContratanteEntities(false, maxResults, firstResult);
    }

    private List<TipoContratante> findTipoContratanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoContratante.class));
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

    public TipoContratante findTipoContratante(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoContratante.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoContratanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoContratante> rt = cq.from(TipoContratante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
