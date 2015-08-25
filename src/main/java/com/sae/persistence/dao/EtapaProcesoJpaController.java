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
import com.sae.persistence.domain.Clase;
import com.sae.persistence.domain.EtapaProceso;
import com.sae.persistence.domain.SeguimientoProceso;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class EtapaProcesoJpaController implements Serializable {

    public EtapaProcesoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EtapaProceso etapaProceso) throws PreexistingEntityException, Exception {
        if (etapaProceso.getSeguimientoProcesoList() == null) {
            etapaProceso.setSeguimientoProcesoList(new ArrayList<SeguimientoProceso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clase idInstancia = etapaProceso.getIdInstancia();
            if (idInstancia != null) {
                idInstancia = em.getReference(idInstancia.getClass(), idInstancia.getId());
                etapaProceso.setIdInstancia(idInstancia);
            }
            List<SeguimientoProceso> attachedSeguimientoProcesoList = new ArrayList<SeguimientoProceso>();
            for (SeguimientoProceso seguimientoProcesoListSeguimientoProcesoToAttach : etapaProceso.getSeguimientoProcesoList()) {
                seguimientoProcesoListSeguimientoProcesoToAttach = em.getReference(seguimientoProcesoListSeguimientoProcesoToAttach.getClass(), seguimientoProcesoListSeguimientoProcesoToAttach.getId());
                attachedSeguimientoProcesoList.add(seguimientoProcesoListSeguimientoProcesoToAttach);
            }
            etapaProceso.setSeguimientoProcesoList(attachedSeguimientoProcesoList);
            em.persist(etapaProceso);
            if (idInstancia != null) {
                idInstancia.getEtapaProcesoList().add(etapaProceso);
                idInstancia = em.merge(idInstancia);
            }
            for (SeguimientoProceso seguimientoProcesoListSeguimientoProceso : etapaProceso.getSeguimientoProcesoList()) {
                EtapaProceso oldIdEtapaOfSeguimientoProcesoListSeguimientoProceso = seguimientoProcesoListSeguimientoProceso.getIdEtapa();
                seguimientoProcesoListSeguimientoProceso.setIdEtapa(etapaProceso);
                seguimientoProcesoListSeguimientoProceso = em.merge(seguimientoProcesoListSeguimientoProceso);
                if (oldIdEtapaOfSeguimientoProcesoListSeguimientoProceso != null) {
                    oldIdEtapaOfSeguimientoProcesoListSeguimientoProceso.getSeguimientoProcesoList().remove(seguimientoProcesoListSeguimientoProceso);
                    oldIdEtapaOfSeguimientoProcesoListSeguimientoProceso = em.merge(oldIdEtapaOfSeguimientoProcesoListSeguimientoProceso);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEtapaProceso(etapaProceso.getId()) != null) {
                throw new PreexistingEntityException("EtapaProceso " + etapaProceso + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EtapaProceso etapaProceso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EtapaProceso persistentEtapaProceso = em.find(EtapaProceso.class, etapaProceso.getId());
            Clase idInstanciaOld = persistentEtapaProceso.getIdInstancia();
            Clase idInstanciaNew = etapaProceso.getIdInstancia();
            List<SeguimientoProceso> seguimientoProcesoListOld = persistentEtapaProceso.getSeguimientoProcesoList();
            List<SeguimientoProceso> seguimientoProcesoListNew = etapaProceso.getSeguimientoProcesoList();
            if (idInstanciaNew != null) {
                idInstanciaNew = em.getReference(idInstanciaNew.getClass(), idInstanciaNew.getId());
                etapaProceso.setIdInstancia(idInstanciaNew);
            }
            List<SeguimientoProceso> attachedSeguimientoProcesoListNew = new ArrayList<SeguimientoProceso>();
            for (SeguimientoProceso seguimientoProcesoListNewSeguimientoProcesoToAttach : seguimientoProcesoListNew) {
                seguimientoProcesoListNewSeguimientoProcesoToAttach = em.getReference(seguimientoProcesoListNewSeguimientoProcesoToAttach.getClass(), seguimientoProcesoListNewSeguimientoProcesoToAttach.getId());
                attachedSeguimientoProcesoListNew.add(seguimientoProcesoListNewSeguimientoProcesoToAttach);
            }
            seguimientoProcesoListNew = attachedSeguimientoProcesoListNew;
            etapaProceso.setSeguimientoProcesoList(seguimientoProcesoListNew);
            etapaProceso = em.merge(etapaProceso);
            if (idInstanciaOld != null && !idInstanciaOld.equals(idInstanciaNew)) {
                idInstanciaOld.getEtapaProcesoList().remove(etapaProceso);
                idInstanciaOld = em.merge(idInstanciaOld);
            }
            if (idInstanciaNew != null && !idInstanciaNew.equals(idInstanciaOld)) {
                idInstanciaNew.getEtapaProcesoList().add(etapaProceso);
                idInstanciaNew = em.merge(idInstanciaNew);
            }
            for (SeguimientoProceso seguimientoProcesoListOldSeguimientoProceso : seguimientoProcesoListOld) {
                if (!seguimientoProcesoListNew.contains(seguimientoProcesoListOldSeguimientoProceso)) {
                    seguimientoProcesoListOldSeguimientoProceso.setIdEtapa(null);
                    seguimientoProcesoListOldSeguimientoProceso = em.merge(seguimientoProcesoListOldSeguimientoProceso);
                }
            }
            for (SeguimientoProceso seguimientoProcesoListNewSeguimientoProceso : seguimientoProcesoListNew) {
                if (!seguimientoProcesoListOld.contains(seguimientoProcesoListNewSeguimientoProceso)) {
                    EtapaProceso oldIdEtapaOfSeguimientoProcesoListNewSeguimientoProceso = seguimientoProcesoListNewSeguimientoProceso.getIdEtapa();
                    seguimientoProcesoListNewSeguimientoProceso.setIdEtapa(etapaProceso);
                    seguimientoProcesoListNewSeguimientoProceso = em.merge(seguimientoProcesoListNewSeguimientoProceso);
                    if (oldIdEtapaOfSeguimientoProcesoListNewSeguimientoProceso != null && !oldIdEtapaOfSeguimientoProcesoListNewSeguimientoProceso.equals(etapaProceso)) {
                        oldIdEtapaOfSeguimientoProcesoListNewSeguimientoProceso.getSeguimientoProcesoList().remove(seguimientoProcesoListNewSeguimientoProceso);
                        oldIdEtapaOfSeguimientoProcesoListNewSeguimientoProceso = em.merge(oldIdEtapaOfSeguimientoProcesoListNewSeguimientoProceso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = etapaProceso.getId();
                if (findEtapaProceso(id) == null) {
                    throw new NonexistentEntityException("The etapaProceso with id " + id + " no longer exists.");
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
            EtapaProceso etapaProceso;
            try {
                etapaProceso = em.getReference(EtapaProceso.class, id);
                etapaProceso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The etapaProceso with id " + id + " no longer exists.", enfe);
            }
            Clase idInstancia = etapaProceso.getIdInstancia();
            if (idInstancia != null) {
                idInstancia.getEtapaProcesoList().remove(etapaProceso);
                idInstancia = em.merge(idInstancia);
            }
            List<SeguimientoProceso> seguimientoProcesoList = etapaProceso.getSeguimientoProcesoList();
            for (SeguimientoProceso seguimientoProcesoListSeguimientoProceso : seguimientoProcesoList) {
                seguimientoProcesoListSeguimientoProceso.setIdEtapa(null);
                seguimientoProcesoListSeguimientoProceso = em.merge(seguimientoProcesoListSeguimientoProceso);
            }
            em.remove(etapaProceso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EtapaProceso> findEtapaProcesoEntities() {
        return findEtapaProcesoEntities(true, -1, -1);
    }

    public List<EtapaProceso> findEtapaProcesoEntities(int maxResults, int firstResult) {
        return findEtapaProcesoEntities(false, maxResults, firstResult);
    }

    private List<EtapaProceso> findEtapaProcesoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EtapaProceso.class));
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

    public EtapaProceso findEtapaProceso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EtapaProceso.class, id);
        } finally {
            em.close();
        }
    }

    public int getEtapaProcesoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EtapaProceso> rt = cq.from(EtapaProceso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
