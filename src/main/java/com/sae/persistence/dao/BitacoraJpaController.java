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
import com.sae.persistence.domain.Proyecto;
import com.sae.persistence.domain.InsumoGastado;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.AdjuntoBitacora;
import com.sae.persistence.domain.Bitacora;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class BitacoraJpaController implements Serializable {

    public BitacoraJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bitacora bitacora) throws PreexistingEntityException, Exception {
        if (bitacora.getInsumoGastadoList() == null) {
            bitacora.setInsumoGastadoList(new ArrayList<InsumoGastado>());
        }
        if (bitacora.getAdjuntoBitacoraList() == null) {
            bitacora.setAdjuntoBitacoraList(new ArrayList<AdjuntoBitacora>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proyecto idProyecto = bitacora.getIdProyecto();
            if (idProyecto != null) {
                idProyecto = em.getReference(idProyecto.getClass(), idProyecto.getId());
                bitacora.setIdProyecto(idProyecto);
            }
            List<InsumoGastado> attachedInsumoGastadoList = new ArrayList<InsumoGastado>();
            for (InsumoGastado insumoGastadoListInsumoGastadoToAttach : bitacora.getInsumoGastadoList()) {
                insumoGastadoListInsumoGastadoToAttach = em.getReference(insumoGastadoListInsumoGastadoToAttach.getClass(), insumoGastadoListInsumoGastadoToAttach.getId());
                attachedInsumoGastadoList.add(insumoGastadoListInsumoGastadoToAttach);
            }
            bitacora.setInsumoGastadoList(attachedInsumoGastadoList);
            List<AdjuntoBitacora> attachedAdjuntoBitacoraList = new ArrayList<AdjuntoBitacora>();
            for (AdjuntoBitacora adjuntoBitacoraListAdjuntoBitacoraToAttach : bitacora.getAdjuntoBitacoraList()) {
                adjuntoBitacoraListAdjuntoBitacoraToAttach = em.getReference(adjuntoBitacoraListAdjuntoBitacoraToAttach.getClass(), adjuntoBitacoraListAdjuntoBitacoraToAttach.getId());
                attachedAdjuntoBitacoraList.add(adjuntoBitacoraListAdjuntoBitacoraToAttach);
            }
            bitacora.setAdjuntoBitacoraList(attachedAdjuntoBitacoraList);
            em.persist(bitacora);
            if (idProyecto != null) {
                idProyecto.getBitacoraList().add(bitacora);
                idProyecto = em.merge(idProyecto);
            }
            for (InsumoGastado insumoGastadoListInsumoGastado : bitacora.getInsumoGastadoList()) {
                Bitacora oldIdBitacoraOfInsumoGastadoListInsumoGastado = insumoGastadoListInsumoGastado.getIdBitacora();
                insumoGastadoListInsumoGastado.setIdBitacora(bitacora);
                insumoGastadoListInsumoGastado = em.merge(insumoGastadoListInsumoGastado);
                if (oldIdBitacoraOfInsumoGastadoListInsumoGastado != null) {
                    oldIdBitacoraOfInsumoGastadoListInsumoGastado.getInsumoGastadoList().remove(insumoGastadoListInsumoGastado);
                    oldIdBitacoraOfInsumoGastadoListInsumoGastado = em.merge(oldIdBitacoraOfInsumoGastadoListInsumoGastado);
                }
            }
            for (AdjuntoBitacora adjuntoBitacoraListAdjuntoBitacora : bitacora.getAdjuntoBitacoraList()) {
                Bitacora oldIdBitacoraOfAdjuntoBitacoraListAdjuntoBitacora = adjuntoBitacoraListAdjuntoBitacora.getIdBitacora();
                adjuntoBitacoraListAdjuntoBitacora.setIdBitacora(bitacora);
                adjuntoBitacoraListAdjuntoBitacora = em.merge(adjuntoBitacoraListAdjuntoBitacora);
                if (oldIdBitacoraOfAdjuntoBitacoraListAdjuntoBitacora != null) {
                    oldIdBitacoraOfAdjuntoBitacoraListAdjuntoBitacora.getAdjuntoBitacoraList().remove(adjuntoBitacoraListAdjuntoBitacora);
                    oldIdBitacoraOfAdjuntoBitacoraListAdjuntoBitacora = em.merge(oldIdBitacoraOfAdjuntoBitacoraListAdjuntoBitacora);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBitacora(bitacora.getId()) != null) {
                throw new PreexistingEntityException("Bitacora " + bitacora + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bitacora bitacora) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bitacora persistentBitacora = em.find(Bitacora.class, bitacora.getId());
            Proyecto idProyectoOld = persistentBitacora.getIdProyecto();
            Proyecto idProyectoNew = bitacora.getIdProyecto();
            List<InsumoGastado> insumoGastadoListOld = persistentBitacora.getInsumoGastadoList();
            List<InsumoGastado> insumoGastadoListNew = bitacora.getInsumoGastadoList();
            List<AdjuntoBitacora> adjuntoBitacoraListOld = persistentBitacora.getAdjuntoBitacoraList();
            List<AdjuntoBitacora> adjuntoBitacoraListNew = bitacora.getAdjuntoBitacoraList();
            if (idProyectoNew != null) {
                idProyectoNew = em.getReference(idProyectoNew.getClass(), idProyectoNew.getId());
                bitacora.setIdProyecto(idProyectoNew);
            }
            List<InsumoGastado> attachedInsumoGastadoListNew = new ArrayList<InsumoGastado>();
            for (InsumoGastado insumoGastadoListNewInsumoGastadoToAttach : insumoGastadoListNew) {
                insumoGastadoListNewInsumoGastadoToAttach = em.getReference(insumoGastadoListNewInsumoGastadoToAttach.getClass(), insumoGastadoListNewInsumoGastadoToAttach.getId());
                attachedInsumoGastadoListNew.add(insumoGastadoListNewInsumoGastadoToAttach);
            }
            insumoGastadoListNew = attachedInsumoGastadoListNew;
            bitacora.setInsumoGastadoList(insumoGastadoListNew);
            List<AdjuntoBitacora> attachedAdjuntoBitacoraListNew = new ArrayList<AdjuntoBitacora>();
            for (AdjuntoBitacora adjuntoBitacoraListNewAdjuntoBitacoraToAttach : adjuntoBitacoraListNew) {
                adjuntoBitacoraListNewAdjuntoBitacoraToAttach = em.getReference(adjuntoBitacoraListNewAdjuntoBitacoraToAttach.getClass(), adjuntoBitacoraListNewAdjuntoBitacoraToAttach.getId());
                attachedAdjuntoBitacoraListNew.add(adjuntoBitacoraListNewAdjuntoBitacoraToAttach);
            }
            adjuntoBitacoraListNew = attachedAdjuntoBitacoraListNew;
            bitacora.setAdjuntoBitacoraList(adjuntoBitacoraListNew);
            bitacora = em.merge(bitacora);
            if (idProyectoOld != null && !idProyectoOld.equals(idProyectoNew)) {
                idProyectoOld.getBitacoraList().remove(bitacora);
                idProyectoOld = em.merge(idProyectoOld);
            }
            if (idProyectoNew != null && !idProyectoNew.equals(idProyectoOld)) {
                idProyectoNew.getBitacoraList().add(bitacora);
                idProyectoNew = em.merge(idProyectoNew);
            }
            for (InsumoGastado insumoGastadoListOldInsumoGastado : insumoGastadoListOld) {
                if (!insumoGastadoListNew.contains(insumoGastadoListOldInsumoGastado)) {
                    insumoGastadoListOldInsumoGastado.setIdBitacora(null);
                    insumoGastadoListOldInsumoGastado = em.merge(insumoGastadoListOldInsumoGastado);
                }
            }
            for (InsumoGastado insumoGastadoListNewInsumoGastado : insumoGastadoListNew) {
                if (!insumoGastadoListOld.contains(insumoGastadoListNewInsumoGastado)) {
                    Bitacora oldIdBitacoraOfInsumoGastadoListNewInsumoGastado = insumoGastadoListNewInsumoGastado.getIdBitacora();
                    insumoGastadoListNewInsumoGastado.setIdBitacora(bitacora);
                    insumoGastadoListNewInsumoGastado = em.merge(insumoGastadoListNewInsumoGastado);
                    if (oldIdBitacoraOfInsumoGastadoListNewInsumoGastado != null && !oldIdBitacoraOfInsumoGastadoListNewInsumoGastado.equals(bitacora)) {
                        oldIdBitacoraOfInsumoGastadoListNewInsumoGastado.getInsumoGastadoList().remove(insumoGastadoListNewInsumoGastado);
                        oldIdBitacoraOfInsumoGastadoListNewInsumoGastado = em.merge(oldIdBitacoraOfInsumoGastadoListNewInsumoGastado);
                    }
                }
            }
            for (AdjuntoBitacora adjuntoBitacoraListOldAdjuntoBitacora : adjuntoBitacoraListOld) {
                if (!adjuntoBitacoraListNew.contains(adjuntoBitacoraListOldAdjuntoBitacora)) {
                    adjuntoBitacoraListOldAdjuntoBitacora.setIdBitacora(null);
                    adjuntoBitacoraListOldAdjuntoBitacora = em.merge(adjuntoBitacoraListOldAdjuntoBitacora);
                }
            }
            for (AdjuntoBitacora adjuntoBitacoraListNewAdjuntoBitacora : adjuntoBitacoraListNew) {
                if (!adjuntoBitacoraListOld.contains(adjuntoBitacoraListNewAdjuntoBitacora)) {
                    Bitacora oldIdBitacoraOfAdjuntoBitacoraListNewAdjuntoBitacora = adjuntoBitacoraListNewAdjuntoBitacora.getIdBitacora();
                    adjuntoBitacoraListNewAdjuntoBitacora.setIdBitacora(bitacora);
                    adjuntoBitacoraListNewAdjuntoBitacora = em.merge(adjuntoBitacoraListNewAdjuntoBitacora);
                    if (oldIdBitacoraOfAdjuntoBitacoraListNewAdjuntoBitacora != null && !oldIdBitacoraOfAdjuntoBitacoraListNewAdjuntoBitacora.equals(bitacora)) {
                        oldIdBitacoraOfAdjuntoBitacoraListNewAdjuntoBitacora.getAdjuntoBitacoraList().remove(adjuntoBitacoraListNewAdjuntoBitacora);
                        oldIdBitacoraOfAdjuntoBitacoraListNewAdjuntoBitacora = em.merge(oldIdBitacoraOfAdjuntoBitacoraListNewAdjuntoBitacora);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bitacora.getId();
                if (findBitacora(id) == null) {
                    throw new NonexistentEntityException("The bitacora with id " + id + " no longer exists.");
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
            Bitacora bitacora;
            try {
                bitacora = em.getReference(Bitacora.class, id);
                bitacora.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bitacora with id " + id + " no longer exists.", enfe);
            }
            Proyecto idProyecto = bitacora.getIdProyecto();
            if (idProyecto != null) {
                idProyecto.getBitacoraList().remove(bitacora);
                idProyecto = em.merge(idProyecto);
            }
            List<InsumoGastado> insumoGastadoList = bitacora.getInsumoGastadoList();
            for (InsumoGastado insumoGastadoListInsumoGastado : insumoGastadoList) {
                insumoGastadoListInsumoGastado.setIdBitacora(null);
                insumoGastadoListInsumoGastado = em.merge(insumoGastadoListInsumoGastado);
            }
            List<AdjuntoBitacora> adjuntoBitacoraList = bitacora.getAdjuntoBitacoraList();
            for (AdjuntoBitacora adjuntoBitacoraListAdjuntoBitacora : adjuntoBitacoraList) {
                adjuntoBitacoraListAdjuntoBitacora.setIdBitacora(null);
                adjuntoBitacoraListAdjuntoBitacora = em.merge(adjuntoBitacoraListAdjuntoBitacora);
            }
            em.remove(bitacora);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bitacora> findBitacoraEntities() {
        return findBitacoraEntities(true, -1, -1);
    }

    public List<Bitacora> findBitacoraEntities(int maxResults, int firstResult) {
        return findBitacoraEntities(false, maxResults, firstResult);
    }

    private List<Bitacora> findBitacoraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bitacora.class));
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

    public Bitacora findBitacora(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bitacora.class, id);
        } finally {
            em.close();
        }
    }

    public int getBitacoraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bitacora> rt = cq.from(Bitacora.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
