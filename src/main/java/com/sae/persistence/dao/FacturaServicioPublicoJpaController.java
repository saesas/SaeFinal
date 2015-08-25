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
import com.sae.persistence.domain.TipoServicioPublico;
import com.sae.persistence.domain.ConsumoFacturaServicioPublico;
import com.sae.persistence.domain.FacturaServicioPublico;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class FacturaServicioPublicoJpaController implements Serializable {

    public FacturaServicioPublicoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FacturaServicioPublico facturaServicioPublico) throws PreexistingEntityException, Exception {
        if (facturaServicioPublico.getConsumoFacturaServicioPublicoList() == null) {
            facturaServicioPublico.setConsumoFacturaServicioPublicoList(new ArrayList<ConsumoFacturaServicioPublico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoServicioPublico idTipo = facturaServicioPublico.getIdTipo();
            if (idTipo != null) {
                idTipo = em.getReference(idTipo.getClass(), idTipo.getId());
                facturaServicioPublico.setIdTipo(idTipo);
            }
            List<ConsumoFacturaServicioPublico> attachedConsumoFacturaServicioPublicoList = new ArrayList<ConsumoFacturaServicioPublico>();
            for (ConsumoFacturaServicioPublico consumoFacturaServicioPublicoListConsumoFacturaServicioPublicoToAttach : facturaServicioPublico.getConsumoFacturaServicioPublicoList()) {
                consumoFacturaServicioPublicoListConsumoFacturaServicioPublicoToAttach = em.getReference(consumoFacturaServicioPublicoListConsumoFacturaServicioPublicoToAttach.getClass(), consumoFacturaServicioPublicoListConsumoFacturaServicioPublicoToAttach.getId());
                attachedConsumoFacturaServicioPublicoList.add(consumoFacturaServicioPublicoListConsumoFacturaServicioPublicoToAttach);
            }
            facturaServicioPublico.setConsumoFacturaServicioPublicoList(attachedConsumoFacturaServicioPublicoList);
            em.persist(facturaServicioPublico);
            if (idTipo != null) {
                idTipo.getFacturaServicioPublicoList().add(facturaServicioPublico);
                idTipo = em.merge(idTipo);
            }
            for (ConsumoFacturaServicioPublico consumoFacturaServicioPublicoListConsumoFacturaServicioPublico : facturaServicioPublico.getConsumoFacturaServicioPublicoList()) {
                FacturaServicioPublico oldIdFacturaOfConsumoFacturaServicioPublicoListConsumoFacturaServicioPublico = consumoFacturaServicioPublicoListConsumoFacturaServicioPublico.getIdFactura();
                consumoFacturaServicioPublicoListConsumoFacturaServicioPublico.setIdFactura(facturaServicioPublico);
                consumoFacturaServicioPublicoListConsumoFacturaServicioPublico = em.merge(consumoFacturaServicioPublicoListConsumoFacturaServicioPublico);
                if (oldIdFacturaOfConsumoFacturaServicioPublicoListConsumoFacturaServicioPublico != null) {
                    oldIdFacturaOfConsumoFacturaServicioPublicoListConsumoFacturaServicioPublico.getConsumoFacturaServicioPublicoList().remove(consumoFacturaServicioPublicoListConsumoFacturaServicioPublico);
                    oldIdFacturaOfConsumoFacturaServicioPublicoListConsumoFacturaServicioPublico = em.merge(oldIdFacturaOfConsumoFacturaServicioPublicoListConsumoFacturaServicioPublico);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFacturaServicioPublico(facturaServicioPublico.getId()) != null) {
                throw new PreexistingEntityException("FacturaServicioPublico " + facturaServicioPublico + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FacturaServicioPublico facturaServicioPublico) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FacturaServicioPublico persistentFacturaServicioPublico = em.find(FacturaServicioPublico.class, facturaServicioPublico.getId());
            TipoServicioPublico idTipoOld = persistentFacturaServicioPublico.getIdTipo();
            TipoServicioPublico idTipoNew = facturaServicioPublico.getIdTipo();
            List<ConsumoFacturaServicioPublico> consumoFacturaServicioPublicoListOld = persistentFacturaServicioPublico.getConsumoFacturaServicioPublicoList();
            List<ConsumoFacturaServicioPublico> consumoFacturaServicioPublicoListNew = facturaServicioPublico.getConsumoFacturaServicioPublicoList();
            if (idTipoNew != null) {
                idTipoNew = em.getReference(idTipoNew.getClass(), idTipoNew.getId());
                facturaServicioPublico.setIdTipo(idTipoNew);
            }
            List<ConsumoFacturaServicioPublico> attachedConsumoFacturaServicioPublicoListNew = new ArrayList<ConsumoFacturaServicioPublico>();
            for (ConsumoFacturaServicioPublico consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublicoToAttach : consumoFacturaServicioPublicoListNew) {
                consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublicoToAttach = em.getReference(consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublicoToAttach.getClass(), consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublicoToAttach.getId());
                attachedConsumoFacturaServicioPublicoListNew.add(consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublicoToAttach);
            }
            consumoFacturaServicioPublicoListNew = attachedConsumoFacturaServicioPublicoListNew;
            facturaServicioPublico.setConsumoFacturaServicioPublicoList(consumoFacturaServicioPublicoListNew);
            facturaServicioPublico = em.merge(facturaServicioPublico);
            if (idTipoOld != null && !idTipoOld.equals(idTipoNew)) {
                idTipoOld.getFacturaServicioPublicoList().remove(facturaServicioPublico);
                idTipoOld = em.merge(idTipoOld);
            }
            if (idTipoNew != null && !idTipoNew.equals(idTipoOld)) {
                idTipoNew.getFacturaServicioPublicoList().add(facturaServicioPublico);
                idTipoNew = em.merge(idTipoNew);
            }
            for (ConsumoFacturaServicioPublico consumoFacturaServicioPublicoListOldConsumoFacturaServicioPublico : consumoFacturaServicioPublicoListOld) {
                if (!consumoFacturaServicioPublicoListNew.contains(consumoFacturaServicioPublicoListOldConsumoFacturaServicioPublico)) {
                    consumoFacturaServicioPublicoListOldConsumoFacturaServicioPublico.setIdFactura(null);
                    consumoFacturaServicioPublicoListOldConsumoFacturaServicioPublico = em.merge(consumoFacturaServicioPublicoListOldConsumoFacturaServicioPublico);
                }
            }
            for (ConsumoFacturaServicioPublico consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico : consumoFacturaServicioPublicoListNew) {
                if (!consumoFacturaServicioPublicoListOld.contains(consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico)) {
                    FacturaServicioPublico oldIdFacturaOfConsumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico = consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico.getIdFactura();
                    consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico.setIdFactura(facturaServicioPublico);
                    consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico = em.merge(consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico);
                    if (oldIdFacturaOfConsumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico != null && !oldIdFacturaOfConsumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico.equals(facturaServicioPublico)) {
                        oldIdFacturaOfConsumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico.getConsumoFacturaServicioPublicoList().remove(consumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico);
                        oldIdFacturaOfConsumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico = em.merge(oldIdFacturaOfConsumoFacturaServicioPublicoListNewConsumoFacturaServicioPublico);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = facturaServicioPublico.getId();
                if (findFacturaServicioPublico(id) == null) {
                    throw new NonexistentEntityException("The facturaServicioPublico with id " + id + " no longer exists.");
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
            FacturaServicioPublico facturaServicioPublico;
            try {
                facturaServicioPublico = em.getReference(FacturaServicioPublico.class, id);
                facturaServicioPublico.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facturaServicioPublico with id " + id + " no longer exists.", enfe);
            }
            TipoServicioPublico idTipo = facturaServicioPublico.getIdTipo();
            if (idTipo != null) {
                idTipo.getFacturaServicioPublicoList().remove(facturaServicioPublico);
                idTipo = em.merge(idTipo);
            }
            List<ConsumoFacturaServicioPublico> consumoFacturaServicioPublicoList = facturaServicioPublico.getConsumoFacturaServicioPublicoList();
            for (ConsumoFacturaServicioPublico consumoFacturaServicioPublicoListConsumoFacturaServicioPublico : consumoFacturaServicioPublicoList) {
                consumoFacturaServicioPublicoListConsumoFacturaServicioPublico.setIdFactura(null);
                consumoFacturaServicioPublicoListConsumoFacturaServicioPublico = em.merge(consumoFacturaServicioPublicoListConsumoFacturaServicioPublico);
            }
            em.remove(facturaServicioPublico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FacturaServicioPublico> findFacturaServicioPublicoEntities() {
        return findFacturaServicioPublicoEntities(true, -1, -1);
    }

    public List<FacturaServicioPublico> findFacturaServicioPublicoEntities(int maxResults, int firstResult) {
        return findFacturaServicioPublicoEntities(false, maxResults, firstResult);
    }

    private List<FacturaServicioPublico> findFacturaServicioPublicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FacturaServicioPublico.class));
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

    public FacturaServicioPublico findFacturaServicioPublico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FacturaServicioPublico.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaServicioPublicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FacturaServicioPublico> rt = cq.from(FacturaServicioPublico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
