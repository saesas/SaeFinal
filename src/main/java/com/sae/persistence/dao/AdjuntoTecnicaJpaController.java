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
import com.sae.persistence.domain.ActaInicio;
import com.sae.persistence.domain.AdjuntoTecnica;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.ContratoProyectoProveedor;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class AdjuntoTecnicaJpaController implements Serializable {

    public AdjuntoTecnicaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AdjuntoTecnica adjuntoTecnica) throws PreexistingEntityException, Exception {
        if (adjuntoTecnica.getActaInicioList() == null) {
            adjuntoTecnica.setActaInicioList(new ArrayList<ActaInicio>());
        }
        if (adjuntoTecnica.getContratoProyectoProveedorList() == null) {
            adjuntoTecnica.setContratoProyectoProveedorList(new ArrayList<ContratoProyectoProveedor>());
        }
        if (adjuntoTecnica.getContratoProyectoProveedorList1() == null) {
            adjuntoTecnica.setContratoProyectoProveedorList1(new ArrayList<ContratoProyectoProveedor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ActaInicio> attachedActaInicioList = new ArrayList<ActaInicio>();
            for (ActaInicio actaInicioListActaInicioToAttach : adjuntoTecnica.getActaInicioList()) {
                actaInicioListActaInicioToAttach = em.getReference(actaInicioListActaInicioToAttach.getClass(), actaInicioListActaInicioToAttach.getId());
                attachedActaInicioList.add(actaInicioListActaInicioToAttach);
            }
            adjuntoTecnica.setActaInicioList(attachedActaInicioList);
            List<ContratoProyectoProveedor> attachedContratoProyectoProveedorList = new ArrayList<ContratoProyectoProveedor>();
            for (ContratoProyectoProveedor contratoProyectoProveedorListContratoProyectoProveedorToAttach : adjuntoTecnica.getContratoProyectoProveedorList()) {
                contratoProyectoProveedorListContratoProyectoProveedorToAttach = em.getReference(contratoProyectoProveedorListContratoProyectoProveedorToAttach.getClass(), contratoProyectoProveedorListContratoProyectoProveedorToAttach.getId());
                attachedContratoProyectoProveedorList.add(contratoProyectoProveedorListContratoProyectoProveedorToAttach);
            }
            adjuntoTecnica.setContratoProyectoProveedorList(attachedContratoProyectoProveedorList);
            List<ContratoProyectoProveedor> attachedContratoProyectoProveedorList1 = new ArrayList<ContratoProyectoProveedor>();
            for (ContratoProyectoProveedor contratoProyectoProveedorList1ContratoProyectoProveedorToAttach : adjuntoTecnica.getContratoProyectoProveedorList1()) {
                contratoProyectoProveedorList1ContratoProyectoProveedorToAttach = em.getReference(contratoProyectoProveedorList1ContratoProyectoProveedorToAttach.getClass(), contratoProyectoProveedorList1ContratoProyectoProveedorToAttach.getId());
                attachedContratoProyectoProveedorList1.add(contratoProyectoProveedorList1ContratoProyectoProveedorToAttach);
            }
            adjuntoTecnica.setContratoProyectoProveedorList1(attachedContratoProyectoProveedorList1);
            em.persist(adjuntoTecnica);
            for (ActaInicio actaInicioListActaInicio : adjuntoTecnica.getActaInicioList()) {
                AdjuntoTecnica oldAdjuntoTecnicaOfActaInicioListActaInicio = actaInicioListActaInicio.getAdjuntoTecnica();
                actaInicioListActaInicio.setAdjuntoTecnica(adjuntoTecnica);
                actaInicioListActaInicio = em.merge(actaInicioListActaInicio);
                if (oldAdjuntoTecnicaOfActaInicioListActaInicio != null) {
                    oldAdjuntoTecnicaOfActaInicioListActaInicio.getActaInicioList().remove(actaInicioListActaInicio);
                    oldAdjuntoTecnicaOfActaInicioListActaInicio = em.merge(oldAdjuntoTecnicaOfActaInicioListActaInicio);
                }
            }
            for (ContratoProyectoProveedor contratoProyectoProveedorListContratoProyectoProveedor : adjuntoTecnica.getContratoProyectoProveedorList()) {
                AdjuntoTecnica oldIdAdjuntoLegalizacionOfContratoProyectoProveedorListContratoProyectoProveedor = contratoProyectoProveedorListContratoProyectoProveedor.getIdAdjuntoLegalizacion();
                contratoProyectoProveedorListContratoProyectoProveedor.setIdAdjuntoLegalizacion(adjuntoTecnica);
                contratoProyectoProveedorListContratoProyectoProveedor = em.merge(contratoProyectoProveedorListContratoProyectoProveedor);
                if (oldIdAdjuntoLegalizacionOfContratoProyectoProveedorListContratoProyectoProveedor != null) {
                    oldIdAdjuntoLegalizacionOfContratoProyectoProveedorListContratoProyectoProveedor.getContratoProyectoProveedorList().remove(contratoProyectoProveedorListContratoProyectoProveedor);
                    oldIdAdjuntoLegalizacionOfContratoProyectoProveedorListContratoProyectoProveedor = em.merge(oldIdAdjuntoLegalizacionOfContratoProyectoProveedorListContratoProyectoProveedor);
                }
            }
            for (ContratoProyectoProveedor contratoProyectoProveedorList1ContratoProyectoProveedor : adjuntoTecnica.getContratoProyectoProveedorList1()) {
                AdjuntoTecnica oldIdAdjuntoOfContratoProyectoProveedorList1ContratoProyectoProveedor = contratoProyectoProveedorList1ContratoProyectoProveedor.getIdAdjunto();
                contratoProyectoProveedorList1ContratoProyectoProveedor.setIdAdjunto(adjuntoTecnica);
                contratoProyectoProveedorList1ContratoProyectoProveedor = em.merge(contratoProyectoProveedorList1ContratoProyectoProveedor);
                if (oldIdAdjuntoOfContratoProyectoProveedorList1ContratoProyectoProveedor != null) {
                    oldIdAdjuntoOfContratoProyectoProveedorList1ContratoProyectoProveedor.getContratoProyectoProveedorList1().remove(contratoProyectoProveedorList1ContratoProyectoProveedor);
                    oldIdAdjuntoOfContratoProyectoProveedorList1ContratoProyectoProveedor = em.merge(oldIdAdjuntoOfContratoProyectoProveedorList1ContratoProyectoProveedor);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAdjuntoTecnica(adjuntoTecnica.getId()) != null) {
                throw new PreexistingEntityException("AdjuntoTecnica " + adjuntoTecnica + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AdjuntoTecnica adjuntoTecnica) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AdjuntoTecnica persistentAdjuntoTecnica = em.find(AdjuntoTecnica.class, adjuntoTecnica.getId());
            List<ActaInicio> actaInicioListOld = persistentAdjuntoTecnica.getActaInicioList();
            List<ActaInicio> actaInicioListNew = adjuntoTecnica.getActaInicioList();
            List<ContratoProyectoProveedor> contratoProyectoProveedorListOld = persistentAdjuntoTecnica.getContratoProyectoProveedorList();
            List<ContratoProyectoProveedor> contratoProyectoProveedorListNew = adjuntoTecnica.getContratoProyectoProveedorList();
            List<ContratoProyectoProveedor> contratoProyectoProveedorList1Old = persistentAdjuntoTecnica.getContratoProyectoProveedorList1();
            List<ContratoProyectoProveedor> contratoProyectoProveedorList1New = adjuntoTecnica.getContratoProyectoProveedorList1();
            List<ActaInicio> attachedActaInicioListNew = new ArrayList<ActaInicio>();
            for (ActaInicio actaInicioListNewActaInicioToAttach : actaInicioListNew) {
                actaInicioListNewActaInicioToAttach = em.getReference(actaInicioListNewActaInicioToAttach.getClass(), actaInicioListNewActaInicioToAttach.getId());
                attachedActaInicioListNew.add(actaInicioListNewActaInicioToAttach);
            }
            actaInicioListNew = attachedActaInicioListNew;
            adjuntoTecnica.setActaInicioList(actaInicioListNew);
            List<ContratoProyectoProveedor> attachedContratoProyectoProveedorListNew = new ArrayList<ContratoProyectoProveedor>();
            for (ContratoProyectoProveedor contratoProyectoProveedorListNewContratoProyectoProveedorToAttach : contratoProyectoProveedorListNew) {
                contratoProyectoProveedorListNewContratoProyectoProveedorToAttach = em.getReference(contratoProyectoProveedorListNewContratoProyectoProveedorToAttach.getClass(), contratoProyectoProveedorListNewContratoProyectoProveedorToAttach.getId());
                attachedContratoProyectoProveedorListNew.add(contratoProyectoProveedorListNewContratoProyectoProveedorToAttach);
            }
            contratoProyectoProveedorListNew = attachedContratoProyectoProveedorListNew;
            adjuntoTecnica.setContratoProyectoProveedorList(contratoProyectoProveedorListNew);
            List<ContratoProyectoProveedor> attachedContratoProyectoProveedorList1New = new ArrayList<ContratoProyectoProveedor>();
            for (ContratoProyectoProveedor contratoProyectoProveedorList1NewContratoProyectoProveedorToAttach : contratoProyectoProveedorList1New) {
                contratoProyectoProveedorList1NewContratoProyectoProveedorToAttach = em.getReference(contratoProyectoProveedorList1NewContratoProyectoProveedorToAttach.getClass(), contratoProyectoProveedorList1NewContratoProyectoProveedorToAttach.getId());
                attachedContratoProyectoProveedorList1New.add(contratoProyectoProveedorList1NewContratoProyectoProveedorToAttach);
            }
            contratoProyectoProveedorList1New = attachedContratoProyectoProveedorList1New;
            adjuntoTecnica.setContratoProyectoProveedorList1(contratoProyectoProveedorList1New);
            adjuntoTecnica = em.merge(adjuntoTecnica);
            for (ActaInicio actaInicioListOldActaInicio : actaInicioListOld) {
                if (!actaInicioListNew.contains(actaInicioListOldActaInicio)) {
                    actaInicioListOldActaInicio.setAdjuntoTecnica(null);
                    actaInicioListOldActaInicio = em.merge(actaInicioListOldActaInicio);
                }
            }
            for (ActaInicio actaInicioListNewActaInicio : actaInicioListNew) {
                if (!actaInicioListOld.contains(actaInicioListNewActaInicio)) {
                    AdjuntoTecnica oldAdjuntoTecnicaOfActaInicioListNewActaInicio = actaInicioListNewActaInicio.getAdjuntoTecnica();
                    actaInicioListNewActaInicio.setAdjuntoTecnica(adjuntoTecnica);
                    actaInicioListNewActaInicio = em.merge(actaInicioListNewActaInicio);
                    if (oldAdjuntoTecnicaOfActaInicioListNewActaInicio != null && !oldAdjuntoTecnicaOfActaInicioListNewActaInicio.equals(adjuntoTecnica)) {
                        oldAdjuntoTecnicaOfActaInicioListNewActaInicio.getActaInicioList().remove(actaInicioListNewActaInicio);
                        oldAdjuntoTecnicaOfActaInicioListNewActaInicio = em.merge(oldAdjuntoTecnicaOfActaInicioListNewActaInicio);
                    }
                }
            }
            for (ContratoProyectoProveedor contratoProyectoProveedorListOldContratoProyectoProveedor : contratoProyectoProveedorListOld) {
                if (!contratoProyectoProveedorListNew.contains(contratoProyectoProveedorListOldContratoProyectoProveedor)) {
                    contratoProyectoProveedorListOldContratoProyectoProveedor.setIdAdjuntoLegalizacion(null);
                    contratoProyectoProveedorListOldContratoProyectoProveedor = em.merge(contratoProyectoProveedorListOldContratoProyectoProveedor);
                }
            }
            for (ContratoProyectoProveedor contratoProyectoProveedorListNewContratoProyectoProveedor : contratoProyectoProveedorListNew) {
                if (!contratoProyectoProveedorListOld.contains(contratoProyectoProveedorListNewContratoProyectoProveedor)) {
                    AdjuntoTecnica oldIdAdjuntoLegalizacionOfContratoProyectoProveedorListNewContratoProyectoProveedor = contratoProyectoProveedorListNewContratoProyectoProveedor.getIdAdjuntoLegalizacion();
                    contratoProyectoProveedorListNewContratoProyectoProveedor.setIdAdjuntoLegalizacion(adjuntoTecnica);
                    contratoProyectoProveedorListNewContratoProyectoProveedor = em.merge(contratoProyectoProveedorListNewContratoProyectoProveedor);
                    if (oldIdAdjuntoLegalizacionOfContratoProyectoProveedorListNewContratoProyectoProveedor != null && !oldIdAdjuntoLegalizacionOfContratoProyectoProveedorListNewContratoProyectoProveedor.equals(adjuntoTecnica)) {
                        oldIdAdjuntoLegalizacionOfContratoProyectoProveedorListNewContratoProyectoProveedor.getContratoProyectoProveedorList().remove(contratoProyectoProveedorListNewContratoProyectoProveedor);
                        oldIdAdjuntoLegalizacionOfContratoProyectoProveedorListNewContratoProyectoProveedor = em.merge(oldIdAdjuntoLegalizacionOfContratoProyectoProveedorListNewContratoProyectoProveedor);
                    }
                }
            }
            for (ContratoProyectoProveedor contratoProyectoProveedorList1OldContratoProyectoProveedor : contratoProyectoProveedorList1Old) {
                if (!contratoProyectoProveedorList1New.contains(contratoProyectoProveedorList1OldContratoProyectoProveedor)) {
                    contratoProyectoProveedorList1OldContratoProyectoProveedor.setIdAdjunto(null);
                    contratoProyectoProveedorList1OldContratoProyectoProveedor = em.merge(contratoProyectoProveedorList1OldContratoProyectoProveedor);
                }
            }
            for (ContratoProyectoProveedor contratoProyectoProveedorList1NewContratoProyectoProveedor : contratoProyectoProveedorList1New) {
                if (!contratoProyectoProveedorList1Old.contains(contratoProyectoProveedorList1NewContratoProyectoProveedor)) {
                    AdjuntoTecnica oldIdAdjuntoOfContratoProyectoProveedorList1NewContratoProyectoProveedor = contratoProyectoProveedorList1NewContratoProyectoProveedor.getIdAdjunto();
                    contratoProyectoProveedorList1NewContratoProyectoProveedor.setIdAdjunto(adjuntoTecnica);
                    contratoProyectoProveedorList1NewContratoProyectoProveedor = em.merge(contratoProyectoProveedorList1NewContratoProyectoProveedor);
                    if (oldIdAdjuntoOfContratoProyectoProveedorList1NewContratoProyectoProveedor != null && !oldIdAdjuntoOfContratoProyectoProveedorList1NewContratoProyectoProveedor.equals(adjuntoTecnica)) {
                        oldIdAdjuntoOfContratoProyectoProveedorList1NewContratoProyectoProveedor.getContratoProyectoProveedorList1().remove(contratoProyectoProveedorList1NewContratoProyectoProveedor);
                        oldIdAdjuntoOfContratoProyectoProveedorList1NewContratoProyectoProveedor = em.merge(oldIdAdjuntoOfContratoProyectoProveedorList1NewContratoProyectoProveedor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = adjuntoTecnica.getId();
                if (findAdjuntoTecnica(id) == null) {
                    throw new NonexistentEntityException("The adjuntoTecnica with id " + id + " no longer exists.");
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
            AdjuntoTecnica adjuntoTecnica;
            try {
                adjuntoTecnica = em.getReference(AdjuntoTecnica.class, id);
                adjuntoTecnica.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The adjuntoTecnica with id " + id + " no longer exists.", enfe);
            }
            List<ActaInicio> actaInicioList = adjuntoTecnica.getActaInicioList();
            for (ActaInicio actaInicioListActaInicio : actaInicioList) {
                actaInicioListActaInicio.setAdjuntoTecnica(null);
                actaInicioListActaInicio = em.merge(actaInicioListActaInicio);
            }
            List<ContratoProyectoProveedor> contratoProyectoProveedorList = adjuntoTecnica.getContratoProyectoProveedorList();
            for (ContratoProyectoProveedor contratoProyectoProveedorListContratoProyectoProveedor : contratoProyectoProveedorList) {
                contratoProyectoProveedorListContratoProyectoProveedor.setIdAdjuntoLegalizacion(null);
                contratoProyectoProveedorListContratoProyectoProveedor = em.merge(contratoProyectoProveedorListContratoProyectoProveedor);
            }
            List<ContratoProyectoProveedor> contratoProyectoProveedorList1 = adjuntoTecnica.getContratoProyectoProveedorList1();
            for (ContratoProyectoProveedor contratoProyectoProveedorList1ContratoProyectoProveedor : contratoProyectoProveedorList1) {
                contratoProyectoProveedorList1ContratoProyectoProveedor.setIdAdjunto(null);
                contratoProyectoProveedorList1ContratoProyectoProveedor = em.merge(contratoProyectoProveedorList1ContratoProyectoProveedor);
            }
            em.remove(adjuntoTecnica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AdjuntoTecnica> findAdjuntoTecnicaEntities() {
        return findAdjuntoTecnicaEntities(true, -1, -1);
    }

    public List<AdjuntoTecnica> findAdjuntoTecnicaEntities(int maxResults, int firstResult) {
        return findAdjuntoTecnicaEntities(false, maxResults, firstResult);
    }

    private List<AdjuntoTecnica> findAdjuntoTecnicaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AdjuntoTecnica.class));
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

    public AdjuntoTecnica findAdjuntoTecnica(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AdjuntoTecnica.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdjuntoTecnicaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AdjuntoTecnica> rt = cq.from(AdjuntoTecnica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
