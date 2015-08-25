/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.ConsumoFacturaServicioPublico;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.TipoTarifaServicioPublico;
import com.sae.persistence.domain.IndicadorConsumoServicio;
import com.sae.persistence.domain.FacturaServicioPublico;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ConsumoFacturaServicioPublicoJpaController implements Serializable {

    public ConsumoFacturaServicioPublicoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConsumoFacturaServicioPublico consumoFacturaServicioPublico) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoTarifaServicioPublico idTarifa = consumoFacturaServicioPublico.getIdTarifa();
            if (idTarifa != null) {
                idTarifa = em.getReference(idTarifa.getClass(), idTarifa.getId());
                consumoFacturaServicioPublico.setIdTarifa(idTarifa);
            }
            IndicadorConsumoServicio idIndicador = consumoFacturaServicioPublico.getIdIndicador();
            if (idIndicador != null) {
                idIndicador = em.getReference(idIndicador.getClass(), idIndicador.getId());
                consumoFacturaServicioPublico.setIdIndicador(idIndicador);
            }
            FacturaServicioPublico idFactura = consumoFacturaServicioPublico.getIdFactura();
            if (idFactura != null) {
                idFactura = em.getReference(idFactura.getClass(), idFactura.getId());
                consumoFacturaServicioPublico.setIdFactura(idFactura);
            }
            em.persist(consumoFacturaServicioPublico);
            if (idTarifa != null) {
                idTarifa.getConsumoFacturaServicioPublicoList().add(consumoFacturaServicioPublico);
                idTarifa = em.merge(idTarifa);
            }
            if (idIndicador != null) {
                idIndicador.getConsumoFacturaServicioPublicoList().add(consumoFacturaServicioPublico);
                idIndicador = em.merge(idIndicador);
            }
            if (idFactura != null) {
                idFactura.getConsumoFacturaServicioPublicoList().add(consumoFacturaServicioPublico);
                idFactura = em.merge(idFactura);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findConsumoFacturaServicioPublico(consumoFacturaServicioPublico.getId()) != null) {
                throw new PreexistingEntityException("ConsumoFacturaServicioPublico " + consumoFacturaServicioPublico + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConsumoFacturaServicioPublico consumoFacturaServicioPublico) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ConsumoFacturaServicioPublico persistentConsumoFacturaServicioPublico = em.find(ConsumoFacturaServicioPublico.class, consumoFacturaServicioPublico.getId());
            TipoTarifaServicioPublico idTarifaOld = persistentConsumoFacturaServicioPublico.getIdTarifa();
            TipoTarifaServicioPublico idTarifaNew = consumoFacturaServicioPublico.getIdTarifa();
            IndicadorConsumoServicio idIndicadorOld = persistentConsumoFacturaServicioPublico.getIdIndicador();
            IndicadorConsumoServicio idIndicadorNew = consumoFacturaServicioPublico.getIdIndicador();
            FacturaServicioPublico idFacturaOld = persistentConsumoFacturaServicioPublico.getIdFactura();
            FacturaServicioPublico idFacturaNew = consumoFacturaServicioPublico.getIdFactura();
            if (idTarifaNew != null) {
                idTarifaNew = em.getReference(idTarifaNew.getClass(), idTarifaNew.getId());
                consumoFacturaServicioPublico.setIdTarifa(idTarifaNew);
            }
            if (idIndicadorNew != null) {
                idIndicadorNew = em.getReference(idIndicadorNew.getClass(), idIndicadorNew.getId());
                consumoFacturaServicioPublico.setIdIndicador(idIndicadorNew);
            }
            if (idFacturaNew != null) {
                idFacturaNew = em.getReference(idFacturaNew.getClass(), idFacturaNew.getId());
                consumoFacturaServicioPublico.setIdFactura(idFacturaNew);
            }
            consumoFacturaServicioPublico = em.merge(consumoFacturaServicioPublico);
            if (idTarifaOld != null && !idTarifaOld.equals(idTarifaNew)) {
                idTarifaOld.getConsumoFacturaServicioPublicoList().remove(consumoFacturaServicioPublico);
                idTarifaOld = em.merge(idTarifaOld);
            }
            if (idTarifaNew != null && !idTarifaNew.equals(idTarifaOld)) {
                idTarifaNew.getConsumoFacturaServicioPublicoList().add(consumoFacturaServicioPublico);
                idTarifaNew = em.merge(idTarifaNew);
            }
            if (idIndicadorOld != null && !idIndicadorOld.equals(idIndicadorNew)) {
                idIndicadorOld.getConsumoFacturaServicioPublicoList().remove(consumoFacturaServicioPublico);
                idIndicadorOld = em.merge(idIndicadorOld);
            }
            if (idIndicadorNew != null && !idIndicadorNew.equals(idIndicadorOld)) {
                idIndicadorNew.getConsumoFacturaServicioPublicoList().add(consumoFacturaServicioPublico);
                idIndicadorNew = em.merge(idIndicadorNew);
            }
            if (idFacturaOld != null && !idFacturaOld.equals(idFacturaNew)) {
                idFacturaOld.getConsumoFacturaServicioPublicoList().remove(consumoFacturaServicioPublico);
                idFacturaOld = em.merge(idFacturaOld);
            }
            if (idFacturaNew != null && !idFacturaNew.equals(idFacturaOld)) {
                idFacturaNew.getConsumoFacturaServicioPublicoList().add(consumoFacturaServicioPublico);
                idFacturaNew = em.merge(idFacturaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = consumoFacturaServicioPublico.getId();
                if (findConsumoFacturaServicioPublico(id) == null) {
                    throw new NonexistentEntityException("The consumoFacturaServicioPublico with id " + id + " no longer exists.");
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
            ConsumoFacturaServicioPublico consumoFacturaServicioPublico;
            try {
                consumoFacturaServicioPublico = em.getReference(ConsumoFacturaServicioPublico.class, id);
                consumoFacturaServicioPublico.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The consumoFacturaServicioPublico with id " + id + " no longer exists.", enfe);
            }
            TipoTarifaServicioPublico idTarifa = consumoFacturaServicioPublico.getIdTarifa();
            if (idTarifa != null) {
                idTarifa.getConsumoFacturaServicioPublicoList().remove(consumoFacturaServicioPublico);
                idTarifa = em.merge(idTarifa);
            }
            IndicadorConsumoServicio idIndicador = consumoFacturaServicioPublico.getIdIndicador();
            if (idIndicador != null) {
                idIndicador.getConsumoFacturaServicioPublicoList().remove(consumoFacturaServicioPublico);
                idIndicador = em.merge(idIndicador);
            }
            FacturaServicioPublico idFactura = consumoFacturaServicioPublico.getIdFactura();
            if (idFactura != null) {
                idFactura.getConsumoFacturaServicioPublicoList().remove(consumoFacturaServicioPublico);
                idFactura = em.merge(idFactura);
            }
            em.remove(consumoFacturaServicioPublico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ConsumoFacturaServicioPublico> findConsumoFacturaServicioPublicoEntities() {
        return findConsumoFacturaServicioPublicoEntities(true, -1, -1);
    }

    public List<ConsumoFacturaServicioPublico> findConsumoFacturaServicioPublicoEntities(int maxResults, int firstResult) {
        return findConsumoFacturaServicioPublicoEntities(false, maxResults, firstResult);
    }

    private List<ConsumoFacturaServicioPublico> findConsumoFacturaServicioPublicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConsumoFacturaServicioPublico.class));
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

    public ConsumoFacturaServicioPublico findConsumoFacturaServicioPublico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConsumoFacturaServicioPublico.class, id);
        } finally {
            em.close();
        }
    }

    public int getConsumoFacturaServicioPublicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConsumoFacturaServicioPublico> rt = cq.from(ConsumoFacturaServicioPublico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
