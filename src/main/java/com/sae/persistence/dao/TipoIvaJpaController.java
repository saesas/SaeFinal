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
import com.sae.persistence.domain.TipoTarifaPuc;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.ParametroContableProyecto;
import com.sae.persistence.domain.TipoIva;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoIvaJpaController implements Serializable {

    public TipoIvaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoIva tipoIva) throws PreexistingEntityException, Exception {
        if (tipoIva.getTipoTarifaPucList() == null) {
            tipoIva.setTipoTarifaPucList(new ArrayList<TipoTarifaPuc>());
        }
        if (tipoIva.getParametroContableProyectoList() == null) {
            tipoIva.setParametroContableProyectoList(new ArrayList<ParametroContableProyecto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<TipoTarifaPuc> attachedTipoTarifaPucList = new ArrayList<TipoTarifaPuc>();
            for (TipoTarifaPuc tipoTarifaPucListTipoTarifaPucToAttach : tipoIva.getTipoTarifaPucList()) {
                tipoTarifaPucListTipoTarifaPucToAttach = em.getReference(tipoTarifaPucListTipoTarifaPucToAttach.getClass(), tipoTarifaPucListTipoTarifaPucToAttach.getId());
                attachedTipoTarifaPucList.add(tipoTarifaPucListTipoTarifaPucToAttach);
            }
            tipoIva.setTipoTarifaPucList(attachedTipoTarifaPucList);
            List<ParametroContableProyecto> attachedParametroContableProyectoList = new ArrayList<ParametroContableProyecto>();
            for (ParametroContableProyecto parametroContableProyectoListParametroContableProyectoToAttach : tipoIva.getParametroContableProyectoList()) {
                parametroContableProyectoListParametroContableProyectoToAttach = em.getReference(parametroContableProyectoListParametroContableProyectoToAttach.getClass(), parametroContableProyectoListParametroContableProyectoToAttach.getId());
                attachedParametroContableProyectoList.add(parametroContableProyectoListParametroContableProyectoToAttach);
            }
            tipoIva.setParametroContableProyectoList(attachedParametroContableProyectoList);
            em.persist(tipoIva);
            for (TipoTarifaPuc tipoTarifaPucListTipoTarifaPuc : tipoIva.getTipoTarifaPucList()) {
                TipoIva oldIdTipoIvaOfTipoTarifaPucListTipoTarifaPuc = tipoTarifaPucListTipoTarifaPuc.getIdTipoIva();
                tipoTarifaPucListTipoTarifaPuc.setIdTipoIva(tipoIva);
                tipoTarifaPucListTipoTarifaPuc = em.merge(tipoTarifaPucListTipoTarifaPuc);
                if (oldIdTipoIvaOfTipoTarifaPucListTipoTarifaPuc != null) {
                    oldIdTipoIvaOfTipoTarifaPucListTipoTarifaPuc.getTipoTarifaPucList().remove(tipoTarifaPucListTipoTarifaPuc);
                    oldIdTipoIvaOfTipoTarifaPucListTipoTarifaPuc = em.merge(oldIdTipoIvaOfTipoTarifaPucListTipoTarifaPuc);
                }
            }
            for (ParametroContableProyecto parametroContableProyectoListParametroContableProyecto : tipoIva.getParametroContableProyectoList()) {
                TipoIva oldIdCausacionIvaOfParametroContableProyectoListParametroContableProyecto = parametroContableProyectoListParametroContableProyecto.getIdCausacionIva();
                parametroContableProyectoListParametroContableProyecto.setIdCausacionIva(tipoIva);
                parametroContableProyectoListParametroContableProyecto = em.merge(parametroContableProyectoListParametroContableProyecto);
                if (oldIdCausacionIvaOfParametroContableProyectoListParametroContableProyecto != null) {
                    oldIdCausacionIvaOfParametroContableProyectoListParametroContableProyecto.getParametroContableProyectoList().remove(parametroContableProyectoListParametroContableProyecto);
                    oldIdCausacionIvaOfParametroContableProyectoListParametroContableProyecto = em.merge(oldIdCausacionIvaOfParametroContableProyectoListParametroContableProyecto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoIva(tipoIva.getId()) != null) {
                throw new PreexistingEntityException("TipoIva " + tipoIva + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoIva tipoIva) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoIva persistentTipoIva = em.find(TipoIva.class, tipoIva.getId());
            List<TipoTarifaPuc> tipoTarifaPucListOld = persistentTipoIva.getTipoTarifaPucList();
            List<TipoTarifaPuc> tipoTarifaPucListNew = tipoIva.getTipoTarifaPucList();
            List<ParametroContableProyecto> parametroContableProyectoListOld = persistentTipoIva.getParametroContableProyectoList();
            List<ParametroContableProyecto> parametroContableProyectoListNew = tipoIva.getParametroContableProyectoList();
            List<TipoTarifaPuc> attachedTipoTarifaPucListNew = new ArrayList<TipoTarifaPuc>();
            for (TipoTarifaPuc tipoTarifaPucListNewTipoTarifaPucToAttach : tipoTarifaPucListNew) {
                tipoTarifaPucListNewTipoTarifaPucToAttach = em.getReference(tipoTarifaPucListNewTipoTarifaPucToAttach.getClass(), tipoTarifaPucListNewTipoTarifaPucToAttach.getId());
                attachedTipoTarifaPucListNew.add(tipoTarifaPucListNewTipoTarifaPucToAttach);
            }
            tipoTarifaPucListNew = attachedTipoTarifaPucListNew;
            tipoIva.setTipoTarifaPucList(tipoTarifaPucListNew);
            List<ParametroContableProyecto> attachedParametroContableProyectoListNew = new ArrayList<ParametroContableProyecto>();
            for (ParametroContableProyecto parametroContableProyectoListNewParametroContableProyectoToAttach : parametroContableProyectoListNew) {
                parametroContableProyectoListNewParametroContableProyectoToAttach = em.getReference(parametroContableProyectoListNewParametroContableProyectoToAttach.getClass(), parametroContableProyectoListNewParametroContableProyectoToAttach.getId());
                attachedParametroContableProyectoListNew.add(parametroContableProyectoListNewParametroContableProyectoToAttach);
            }
            parametroContableProyectoListNew = attachedParametroContableProyectoListNew;
            tipoIva.setParametroContableProyectoList(parametroContableProyectoListNew);
            tipoIva = em.merge(tipoIva);
            for (TipoTarifaPuc tipoTarifaPucListOldTipoTarifaPuc : tipoTarifaPucListOld) {
                if (!tipoTarifaPucListNew.contains(tipoTarifaPucListOldTipoTarifaPuc)) {
                    tipoTarifaPucListOldTipoTarifaPuc.setIdTipoIva(null);
                    tipoTarifaPucListOldTipoTarifaPuc = em.merge(tipoTarifaPucListOldTipoTarifaPuc);
                }
            }
            for (TipoTarifaPuc tipoTarifaPucListNewTipoTarifaPuc : tipoTarifaPucListNew) {
                if (!tipoTarifaPucListOld.contains(tipoTarifaPucListNewTipoTarifaPuc)) {
                    TipoIva oldIdTipoIvaOfTipoTarifaPucListNewTipoTarifaPuc = tipoTarifaPucListNewTipoTarifaPuc.getIdTipoIva();
                    tipoTarifaPucListNewTipoTarifaPuc.setIdTipoIva(tipoIva);
                    tipoTarifaPucListNewTipoTarifaPuc = em.merge(tipoTarifaPucListNewTipoTarifaPuc);
                    if (oldIdTipoIvaOfTipoTarifaPucListNewTipoTarifaPuc != null && !oldIdTipoIvaOfTipoTarifaPucListNewTipoTarifaPuc.equals(tipoIva)) {
                        oldIdTipoIvaOfTipoTarifaPucListNewTipoTarifaPuc.getTipoTarifaPucList().remove(tipoTarifaPucListNewTipoTarifaPuc);
                        oldIdTipoIvaOfTipoTarifaPucListNewTipoTarifaPuc = em.merge(oldIdTipoIvaOfTipoTarifaPucListNewTipoTarifaPuc);
                    }
                }
            }
            for (ParametroContableProyecto parametroContableProyectoListOldParametroContableProyecto : parametroContableProyectoListOld) {
                if (!parametroContableProyectoListNew.contains(parametroContableProyectoListOldParametroContableProyecto)) {
                    parametroContableProyectoListOldParametroContableProyecto.setIdCausacionIva(null);
                    parametroContableProyectoListOldParametroContableProyecto = em.merge(parametroContableProyectoListOldParametroContableProyecto);
                }
            }
            for (ParametroContableProyecto parametroContableProyectoListNewParametroContableProyecto : parametroContableProyectoListNew) {
                if (!parametroContableProyectoListOld.contains(parametroContableProyectoListNewParametroContableProyecto)) {
                    TipoIva oldIdCausacionIvaOfParametroContableProyectoListNewParametroContableProyecto = parametroContableProyectoListNewParametroContableProyecto.getIdCausacionIva();
                    parametroContableProyectoListNewParametroContableProyecto.setIdCausacionIva(tipoIva);
                    parametroContableProyectoListNewParametroContableProyecto = em.merge(parametroContableProyectoListNewParametroContableProyecto);
                    if (oldIdCausacionIvaOfParametroContableProyectoListNewParametroContableProyecto != null && !oldIdCausacionIvaOfParametroContableProyectoListNewParametroContableProyecto.equals(tipoIva)) {
                        oldIdCausacionIvaOfParametroContableProyectoListNewParametroContableProyecto.getParametroContableProyectoList().remove(parametroContableProyectoListNewParametroContableProyecto);
                        oldIdCausacionIvaOfParametroContableProyectoListNewParametroContableProyecto = em.merge(oldIdCausacionIvaOfParametroContableProyectoListNewParametroContableProyecto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoIva.getId();
                if (findTipoIva(id) == null) {
                    throw new NonexistentEntityException("The tipoIva with id " + id + " no longer exists.");
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
            TipoIva tipoIva;
            try {
                tipoIva = em.getReference(TipoIva.class, id);
                tipoIva.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoIva with id " + id + " no longer exists.", enfe);
            }
            List<TipoTarifaPuc> tipoTarifaPucList = tipoIva.getTipoTarifaPucList();
            for (TipoTarifaPuc tipoTarifaPucListTipoTarifaPuc : tipoTarifaPucList) {
                tipoTarifaPucListTipoTarifaPuc.setIdTipoIva(null);
                tipoTarifaPucListTipoTarifaPuc = em.merge(tipoTarifaPucListTipoTarifaPuc);
            }
            List<ParametroContableProyecto> parametroContableProyectoList = tipoIva.getParametroContableProyectoList();
            for (ParametroContableProyecto parametroContableProyectoListParametroContableProyecto : parametroContableProyectoList) {
                parametroContableProyectoListParametroContableProyecto.setIdCausacionIva(null);
                parametroContableProyectoListParametroContableProyecto = em.merge(parametroContableProyectoListParametroContableProyecto);
            }
            em.remove(tipoIva);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoIva> findTipoIvaEntities() {
        return findTipoIvaEntities(true, -1, -1);
    }

    public List<TipoIva> findTipoIvaEntities(int maxResults, int firstResult) {
        return findTipoIvaEntities(false, maxResults, firstResult);
    }

    private List<TipoIva> findTipoIvaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoIva.class));
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

    public TipoIva findTipoIva(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoIva.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoIvaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoIva> rt = cq.from(TipoIva.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
