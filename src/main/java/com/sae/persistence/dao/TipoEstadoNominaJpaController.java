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
import com.sae.persistence.domain.EstadoNomina;
import com.sae.persistence.domain.TipoEstadoNomina;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoEstadoNominaJpaController implements Serializable {

    public TipoEstadoNominaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoEstadoNomina tipoEstadoNomina) throws PreexistingEntityException, Exception {
        if (tipoEstadoNomina.getEstadoNominaList() == null) {
            tipoEstadoNomina.setEstadoNominaList(new ArrayList<EstadoNomina>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<EstadoNomina> attachedEstadoNominaList = new ArrayList<EstadoNomina>();
            for (EstadoNomina estadoNominaListEstadoNominaToAttach : tipoEstadoNomina.getEstadoNominaList()) {
                estadoNominaListEstadoNominaToAttach = em.getReference(estadoNominaListEstadoNominaToAttach.getClass(), estadoNominaListEstadoNominaToAttach.getId());
                attachedEstadoNominaList.add(estadoNominaListEstadoNominaToAttach);
            }
            tipoEstadoNomina.setEstadoNominaList(attachedEstadoNominaList);
            em.persist(tipoEstadoNomina);
            for (EstadoNomina estadoNominaListEstadoNomina : tipoEstadoNomina.getEstadoNominaList()) {
                TipoEstadoNomina oldIdEstadoNominaOfEstadoNominaListEstadoNomina = estadoNominaListEstadoNomina.getIdEstadoNomina();
                estadoNominaListEstadoNomina.setIdEstadoNomina(tipoEstadoNomina);
                estadoNominaListEstadoNomina = em.merge(estadoNominaListEstadoNomina);
                if (oldIdEstadoNominaOfEstadoNominaListEstadoNomina != null) {
                    oldIdEstadoNominaOfEstadoNominaListEstadoNomina.getEstadoNominaList().remove(estadoNominaListEstadoNomina);
                    oldIdEstadoNominaOfEstadoNominaListEstadoNomina = em.merge(oldIdEstadoNominaOfEstadoNominaListEstadoNomina);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoEstadoNomina(tipoEstadoNomina.getId()) != null) {
                throw new PreexistingEntityException("TipoEstadoNomina " + tipoEstadoNomina + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoEstadoNomina tipoEstadoNomina) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoEstadoNomina persistentTipoEstadoNomina = em.find(TipoEstadoNomina.class, tipoEstadoNomina.getId());
            List<EstadoNomina> estadoNominaListOld = persistentTipoEstadoNomina.getEstadoNominaList();
            List<EstadoNomina> estadoNominaListNew = tipoEstadoNomina.getEstadoNominaList();
            List<EstadoNomina> attachedEstadoNominaListNew = new ArrayList<EstadoNomina>();
            for (EstadoNomina estadoNominaListNewEstadoNominaToAttach : estadoNominaListNew) {
                estadoNominaListNewEstadoNominaToAttach = em.getReference(estadoNominaListNewEstadoNominaToAttach.getClass(), estadoNominaListNewEstadoNominaToAttach.getId());
                attachedEstadoNominaListNew.add(estadoNominaListNewEstadoNominaToAttach);
            }
            estadoNominaListNew = attachedEstadoNominaListNew;
            tipoEstadoNomina.setEstadoNominaList(estadoNominaListNew);
            tipoEstadoNomina = em.merge(tipoEstadoNomina);
            for (EstadoNomina estadoNominaListOldEstadoNomina : estadoNominaListOld) {
                if (!estadoNominaListNew.contains(estadoNominaListOldEstadoNomina)) {
                    estadoNominaListOldEstadoNomina.setIdEstadoNomina(null);
                    estadoNominaListOldEstadoNomina = em.merge(estadoNominaListOldEstadoNomina);
                }
            }
            for (EstadoNomina estadoNominaListNewEstadoNomina : estadoNominaListNew) {
                if (!estadoNominaListOld.contains(estadoNominaListNewEstadoNomina)) {
                    TipoEstadoNomina oldIdEstadoNominaOfEstadoNominaListNewEstadoNomina = estadoNominaListNewEstadoNomina.getIdEstadoNomina();
                    estadoNominaListNewEstadoNomina.setIdEstadoNomina(tipoEstadoNomina);
                    estadoNominaListNewEstadoNomina = em.merge(estadoNominaListNewEstadoNomina);
                    if (oldIdEstadoNominaOfEstadoNominaListNewEstadoNomina != null && !oldIdEstadoNominaOfEstadoNominaListNewEstadoNomina.equals(tipoEstadoNomina)) {
                        oldIdEstadoNominaOfEstadoNominaListNewEstadoNomina.getEstadoNominaList().remove(estadoNominaListNewEstadoNomina);
                        oldIdEstadoNominaOfEstadoNominaListNewEstadoNomina = em.merge(oldIdEstadoNominaOfEstadoNominaListNewEstadoNomina);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoEstadoNomina.getId();
                if (findTipoEstadoNomina(id) == null) {
                    throw new NonexistentEntityException("The tipoEstadoNomina with id " + id + " no longer exists.");
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
            TipoEstadoNomina tipoEstadoNomina;
            try {
                tipoEstadoNomina = em.getReference(TipoEstadoNomina.class, id);
                tipoEstadoNomina.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoEstadoNomina with id " + id + " no longer exists.", enfe);
            }
            List<EstadoNomina> estadoNominaList = tipoEstadoNomina.getEstadoNominaList();
            for (EstadoNomina estadoNominaListEstadoNomina : estadoNominaList) {
                estadoNominaListEstadoNomina.setIdEstadoNomina(null);
                estadoNominaListEstadoNomina = em.merge(estadoNominaListEstadoNomina);
            }
            em.remove(tipoEstadoNomina);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoEstadoNomina> findTipoEstadoNominaEntities() {
        return findTipoEstadoNominaEntities(true, -1, -1);
    }

    public List<TipoEstadoNomina> findTipoEstadoNominaEntities(int maxResults, int firstResult) {
        return findTipoEstadoNominaEntities(false, maxResults, firstResult);
    }

    private List<TipoEstadoNomina> findTipoEstadoNominaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoEstadoNomina.class));
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

    public TipoEstadoNomina findTipoEstadoNomina(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoEstadoNomina.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoEstadoNominaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoEstadoNomina> rt = cq.from(TipoEstadoNomina.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
