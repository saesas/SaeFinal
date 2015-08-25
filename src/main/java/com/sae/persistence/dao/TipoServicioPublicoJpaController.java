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
import com.sae.persistence.domain.FacturaServicioPublico;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.PucServicioPublico;
import com.sae.persistence.domain.TipoServicioPublico;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoServicioPublicoJpaController implements Serializable {

    public TipoServicioPublicoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoServicioPublico tipoServicioPublico) throws PreexistingEntityException, Exception {
        if (tipoServicioPublico.getFacturaServicioPublicoList() == null) {
            tipoServicioPublico.setFacturaServicioPublicoList(new ArrayList<FacturaServicioPublico>());
        }
        if (tipoServicioPublico.getPucServicioPublicoList() == null) {
            tipoServicioPublico.setPucServicioPublicoList(new ArrayList<PucServicioPublico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<FacturaServicioPublico> attachedFacturaServicioPublicoList = new ArrayList<FacturaServicioPublico>();
            for (FacturaServicioPublico facturaServicioPublicoListFacturaServicioPublicoToAttach : tipoServicioPublico.getFacturaServicioPublicoList()) {
                facturaServicioPublicoListFacturaServicioPublicoToAttach = em.getReference(facturaServicioPublicoListFacturaServicioPublicoToAttach.getClass(), facturaServicioPublicoListFacturaServicioPublicoToAttach.getId());
                attachedFacturaServicioPublicoList.add(facturaServicioPublicoListFacturaServicioPublicoToAttach);
            }
            tipoServicioPublico.setFacturaServicioPublicoList(attachedFacturaServicioPublicoList);
            List<PucServicioPublico> attachedPucServicioPublicoList = new ArrayList<PucServicioPublico>();
            for (PucServicioPublico pucServicioPublicoListPucServicioPublicoToAttach : tipoServicioPublico.getPucServicioPublicoList()) {
                pucServicioPublicoListPucServicioPublicoToAttach = em.getReference(pucServicioPublicoListPucServicioPublicoToAttach.getClass(), pucServicioPublicoListPucServicioPublicoToAttach.getId());
                attachedPucServicioPublicoList.add(pucServicioPublicoListPucServicioPublicoToAttach);
            }
            tipoServicioPublico.setPucServicioPublicoList(attachedPucServicioPublicoList);
            em.persist(tipoServicioPublico);
            for (FacturaServicioPublico facturaServicioPublicoListFacturaServicioPublico : tipoServicioPublico.getFacturaServicioPublicoList()) {
                TipoServicioPublico oldIdTipoOfFacturaServicioPublicoListFacturaServicioPublico = facturaServicioPublicoListFacturaServicioPublico.getIdTipo();
                facturaServicioPublicoListFacturaServicioPublico.setIdTipo(tipoServicioPublico);
                facturaServicioPublicoListFacturaServicioPublico = em.merge(facturaServicioPublicoListFacturaServicioPublico);
                if (oldIdTipoOfFacturaServicioPublicoListFacturaServicioPublico != null) {
                    oldIdTipoOfFacturaServicioPublicoListFacturaServicioPublico.getFacturaServicioPublicoList().remove(facturaServicioPublicoListFacturaServicioPublico);
                    oldIdTipoOfFacturaServicioPublicoListFacturaServicioPublico = em.merge(oldIdTipoOfFacturaServicioPublicoListFacturaServicioPublico);
                }
            }
            for (PucServicioPublico pucServicioPublicoListPucServicioPublico : tipoServicioPublico.getPucServicioPublicoList()) {
                TipoServicioPublico oldIdTipoServicioOfPucServicioPublicoListPucServicioPublico = pucServicioPublicoListPucServicioPublico.getIdTipoServicio();
                pucServicioPublicoListPucServicioPublico.setIdTipoServicio(tipoServicioPublico);
                pucServicioPublicoListPucServicioPublico = em.merge(pucServicioPublicoListPucServicioPublico);
                if (oldIdTipoServicioOfPucServicioPublicoListPucServicioPublico != null) {
                    oldIdTipoServicioOfPucServicioPublicoListPucServicioPublico.getPucServicioPublicoList().remove(pucServicioPublicoListPucServicioPublico);
                    oldIdTipoServicioOfPucServicioPublicoListPucServicioPublico = em.merge(oldIdTipoServicioOfPucServicioPublicoListPucServicioPublico);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoServicioPublico(tipoServicioPublico.getId()) != null) {
                throw new PreexistingEntityException("TipoServicioPublico " + tipoServicioPublico + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoServicioPublico tipoServicioPublico) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoServicioPublico persistentTipoServicioPublico = em.find(TipoServicioPublico.class, tipoServicioPublico.getId());
            List<FacturaServicioPublico> facturaServicioPublicoListOld = persistentTipoServicioPublico.getFacturaServicioPublicoList();
            List<FacturaServicioPublico> facturaServicioPublicoListNew = tipoServicioPublico.getFacturaServicioPublicoList();
            List<PucServicioPublico> pucServicioPublicoListOld = persistentTipoServicioPublico.getPucServicioPublicoList();
            List<PucServicioPublico> pucServicioPublicoListNew = tipoServicioPublico.getPucServicioPublicoList();
            List<FacturaServicioPublico> attachedFacturaServicioPublicoListNew = new ArrayList<FacturaServicioPublico>();
            for (FacturaServicioPublico facturaServicioPublicoListNewFacturaServicioPublicoToAttach : facturaServicioPublicoListNew) {
                facturaServicioPublicoListNewFacturaServicioPublicoToAttach = em.getReference(facturaServicioPublicoListNewFacturaServicioPublicoToAttach.getClass(), facturaServicioPublicoListNewFacturaServicioPublicoToAttach.getId());
                attachedFacturaServicioPublicoListNew.add(facturaServicioPublicoListNewFacturaServicioPublicoToAttach);
            }
            facturaServicioPublicoListNew = attachedFacturaServicioPublicoListNew;
            tipoServicioPublico.setFacturaServicioPublicoList(facturaServicioPublicoListNew);
            List<PucServicioPublico> attachedPucServicioPublicoListNew = new ArrayList<PucServicioPublico>();
            for (PucServicioPublico pucServicioPublicoListNewPucServicioPublicoToAttach : pucServicioPublicoListNew) {
                pucServicioPublicoListNewPucServicioPublicoToAttach = em.getReference(pucServicioPublicoListNewPucServicioPublicoToAttach.getClass(), pucServicioPublicoListNewPucServicioPublicoToAttach.getId());
                attachedPucServicioPublicoListNew.add(pucServicioPublicoListNewPucServicioPublicoToAttach);
            }
            pucServicioPublicoListNew = attachedPucServicioPublicoListNew;
            tipoServicioPublico.setPucServicioPublicoList(pucServicioPublicoListNew);
            tipoServicioPublico = em.merge(tipoServicioPublico);
            for (FacturaServicioPublico facturaServicioPublicoListOldFacturaServicioPublico : facturaServicioPublicoListOld) {
                if (!facturaServicioPublicoListNew.contains(facturaServicioPublicoListOldFacturaServicioPublico)) {
                    facturaServicioPublicoListOldFacturaServicioPublico.setIdTipo(null);
                    facturaServicioPublicoListOldFacturaServicioPublico = em.merge(facturaServicioPublicoListOldFacturaServicioPublico);
                }
            }
            for (FacturaServicioPublico facturaServicioPublicoListNewFacturaServicioPublico : facturaServicioPublicoListNew) {
                if (!facturaServicioPublicoListOld.contains(facturaServicioPublicoListNewFacturaServicioPublico)) {
                    TipoServicioPublico oldIdTipoOfFacturaServicioPublicoListNewFacturaServicioPublico = facturaServicioPublicoListNewFacturaServicioPublico.getIdTipo();
                    facturaServicioPublicoListNewFacturaServicioPublico.setIdTipo(tipoServicioPublico);
                    facturaServicioPublicoListNewFacturaServicioPublico = em.merge(facturaServicioPublicoListNewFacturaServicioPublico);
                    if (oldIdTipoOfFacturaServicioPublicoListNewFacturaServicioPublico != null && !oldIdTipoOfFacturaServicioPublicoListNewFacturaServicioPublico.equals(tipoServicioPublico)) {
                        oldIdTipoOfFacturaServicioPublicoListNewFacturaServicioPublico.getFacturaServicioPublicoList().remove(facturaServicioPublicoListNewFacturaServicioPublico);
                        oldIdTipoOfFacturaServicioPublicoListNewFacturaServicioPublico = em.merge(oldIdTipoOfFacturaServicioPublicoListNewFacturaServicioPublico);
                    }
                }
            }
            for (PucServicioPublico pucServicioPublicoListOldPucServicioPublico : pucServicioPublicoListOld) {
                if (!pucServicioPublicoListNew.contains(pucServicioPublicoListOldPucServicioPublico)) {
                    pucServicioPublicoListOldPucServicioPublico.setIdTipoServicio(null);
                    pucServicioPublicoListOldPucServicioPublico = em.merge(pucServicioPublicoListOldPucServicioPublico);
                }
            }
            for (PucServicioPublico pucServicioPublicoListNewPucServicioPublico : pucServicioPublicoListNew) {
                if (!pucServicioPublicoListOld.contains(pucServicioPublicoListNewPucServicioPublico)) {
                    TipoServicioPublico oldIdTipoServicioOfPucServicioPublicoListNewPucServicioPublico = pucServicioPublicoListNewPucServicioPublico.getIdTipoServicio();
                    pucServicioPublicoListNewPucServicioPublico.setIdTipoServicio(tipoServicioPublico);
                    pucServicioPublicoListNewPucServicioPublico = em.merge(pucServicioPublicoListNewPucServicioPublico);
                    if (oldIdTipoServicioOfPucServicioPublicoListNewPucServicioPublico != null && !oldIdTipoServicioOfPucServicioPublicoListNewPucServicioPublico.equals(tipoServicioPublico)) {
                        oldIdTipoServicioOfPucServicioPublicoListNewPucServicioPublico.getPucServicioPublicoList().remove(pucServicioPublicoListNewPucServicioPublico);
                        oldIdTipoServicioOfPucServicioPublicoListNewPucServicioPublico = em.merge(oldIdTipoServicioOfPucServicioPublicoListNewPucServicioPublico);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoServicioPublico.getId();
                if (findTipoServicioPublico(id) == null) {
                    throw new NonexistentEntityException("The tipoServicioPublico with id " + id + " no longer exists.");
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
            TipoServicioPublico tipoServicioPublico;
            try {
                tipoServicioPublico = em.getReference(TipoServicioPublico.class, id);
                tipoServicioPublico.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoServicioPublico with id " + id + " no longer exists.", enfe);
            }
            List<FacturaServicioPublico> facturaServicioPublicoList = tipoServicioPublico.getFacturaServicioPublicoList();
            for (FacturaServicioPublico facturaServicioPublicoListFacturaServicioPublico : facturaServicioPublicoList) {
                facturaServicioPublicoListFacturaServicioPublico.setIdTipo(null);
                facturaServicioPublicoListFacturaServicioPublico = em.merge(facturaServicioPublicoListFacturaServicioPublico);
            }
            List<PucServicioPublico> pucServicioPublicoList = tipoServicioPublico.getPucServicioPublicoList();
            for (PucServicioPublico pucServicioPublicoListPucServicioPublico : pucServicioPublicoList) {
                pucServicioPublicoListPucServicioPublico.setIdTipoServicio(null);
                pucServicioPublicoListPucServicioPublico = em.merge(pucServicioPublicoListPucServicioPublico);
            }
            em.remove(tipoServicioPublico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoServicioPublico> findTipoServicioPublicoEntities() {
        return findTipoServicioPublicoEntities(true, -1, -1);
    }

    public List<TipoServicioPublico> findTipoServicioPublicoEntities(int maxResults, int firstResult) {
        return findTipoServicioPublicoEntities(false, maxResults, firstResult);
    }

    private List<TipoServicioPublico> findTipoServicioPublicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoServicioPublico.class));
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

    public TipoServicioPublico findTipoServicioPublico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoServicioPublico.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoServicioPublicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoServicioPublico> rt = cq.from(TipoServicioPublico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
