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
import com.sae.persistence.domain.PeriodoPago;
import com.sae.persistence.domain.ContratoTercero;
import com.sae.persistence.domain.GrupoNominal;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.Nomina;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class GrupoNominalJpaController implements Serializable {

    public GrupoNominalJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GrupoNominal grupoNominal) throws PreexistingEntityException, Exception {
        if (grupoNominal.getContratoTerceroList() == null) {
            grupoNominal.setContratoTerceroList(new ArrayList<ContratoTercero>());
        }
        if (grupoNominal.getNominaList() == null) {
            grupoNominal.setNominaList(new ArrayList<Nomina>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PeriodoPago idPeriodo = grupoNominal.getIdPeriodo();
            if (idPeriodo != null) {
                idPeriodo = em.getReference(idPeriodo.getClass(), idPeriodo.getId());
                grupoNominal.setIdPeriodo(idPeriodo);
            }
            List<ContratoTercero> attachedContratoTerceroList = new ArrayList<ContratoTercero>();
            for (ContratoTercero contratoTerceroListContratoTerceroToAttach : grupoNominal.getContratoTerceroList()) {
                contratoTerceroListContratoTerceroToAttach = em.getReference(contratoTerceroListContratoTerceroToAttach.getClass(), contratoTerceroListContratoTerceroToAttach.getId());
                attachedContratoTerceroList.add(contratoTerceroListContratoTerceroToAttach);
            }
            grupoNominal.setContratoTerceroList(attachedContratoTerceroList);
            List<Nomina> attachedNominaList = new ArrayList<Nomina>();
            for (Nomina nominaListNominaToAttach : grupoNominal.getNominaList()) {
                nominaListNominaToAttach = em.getReference(nominaListNominaToAttach.getClass(), nominaListNominaToAttach.getId());
                attachedNominaList.add(nominaListNominaToAttach);
            }
            grupoNominal.setNominaList(attachedNominaList);
            em.persist(grupoNominal);
            if (idPeriodo != null) {
                idPeriodo.getGrupoNominalList().add(grupoNominal);
                idPeriodo = em.merge(idPeriodo);
            }
            for (ContratoTercero contratoTerceroListContratoTercero : grupoNominal.getContratoTerceroList()) {
                GrupoNominal oldIdGrupoNominalOfContratoTerceroListContratoTercero = contratoTerceroListContratoTercero.getIdGrupoNominal();
                contratoTerceroListContratoTercero.setIdGrupoNominal(grupoNominal);
                contratoTerceroListContratoTercero = em.merge(contratoTerceroListContratoTercero);
                if (oldIdGrupoNominalOfContratoTerceroListContratoTercero != null) {
                    oldIdGrupoNominalOfContratoTerceroListContratoTercero.getContratoTerceroList().remove(contratoTerceroListContratoTercero);
                    oldIdGrupoNominalOfContratoTerceroListContratoTercero = em.merge(oldIdGrupoNominalOfContratoTerceroListContratoTercero);
                }
            }
            for (Nomina nominaListNomina : grupoNominal.getNominaList()) {
                GrupoNominal oldIdGrupoNominalOfNominaListNomina = nominaListNomina.getIdGrupoNominal();
                nominaListNomina.setIdGrupoNominal(grupoNominal);
                nominaListNomina = em.merge(nominaListNomina);
                if (oldIdGrupoNominalOfNominaListNomina != null) {
                    oldIdGrupoNominalOfNominaListNomina.getNominaList().remove(nominaListNomina);
                    oldIdGrupoNominalOfNominaListNomina = em.merge(oldIdGrupoNominalOfNominaListNomina);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGrupoNominal(grupoNominal.getId()) != null) {
                throw new PreexistingEntityException("GrupoNominal " + grupoNominal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GrupoNominal grupoNominal) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GrupoNominal persistentGrupoNominal = em.find(GrupoNominal.class, grupoNominal.getId());
            PeriodoPago idPeriodoOld = persistentGrupoNominal.getIdPeriodo();
            PeriodoPago idPeriodoNew = grupoNominal.getIdPeriodo();
            List<ContratoTercero> contratoTerceroListOld = persistentGrupoNominal.getContratoTerceroList();
            List<ContratoTercero> contratoTerceroListNew = grupoNominal.getContratoTerceroList();
            List<Nomina> nominaListOld = persistentGrupoNominal.getNominaList();
            List<Nomina> nominaListNew = grupoNominal.getNominaList();
            if (idPeriodoNew != null) {
                idPeriodoNew = em.getReference(idPeriodoNew.getClass(), idPeriodoNew.getId());
                grupoNominal.setIdPeriodo(idPeriodoNew);
            }
            List<ContratoTercero> attachedContratoTerceroListNew = new ArrayList<ContratoTercero>();
            for (ContratoTercero contratoTerceroListNewContratoTerceroToAttach : contratoTerceroListNew) {
                contratoTerceroListNewContratoTerceroToAttach = em.getReference(contratoTerceroListNewContratoTerceroToAttach.getClass(), contratoTerceroListNewContratoTerceroToAttach.getId());
                attachedContratoTerceroListNew.add(contratoTerceroListNewContratoTerceroToAttach);
            }
            contratoTerceroListNew = attachedContratoTerceroListNew;
            grupoNominal.setContratoTerceroList(contratoTerceroListNew);
            List<Nomina> attachedNominaListNew = new ArrayList<Nomina>();
            for (Nomina nominaListNewNominaToAttach : nominaListNew) {
                nominaListNewNominaToAttach = em.getReference(nominaListNewNominaToAttach.getClass(), nominaListNewNominaToAttach.getId());
                attachedNominaListNew.add(nominaListNewNominaToAttach);
            }
            nominaListNew = attachedNominaListNew;
            grupoNominal.setNominaList(nominaListNew);
            grupoNominal = em.merge(grupoNominal);
            if (idPeriodoOld != null && !idPeriodoOld.equals(idPeriodoNew)) {
                idPeriodoOld.getGrupoNominalList().remove(grupoNominal);
                idPeriodoOld = em.merge(idPeriodoOld);
            }
            if (idPeriodoNew != null && !idPeriodoNew.equals(idPeriodoOld)) {
                idPeriodoNew.getGrupoNominalList().add(grupoNominal);
                idPeriodoNew = em.merge(idPeriodoNew);
            }
            for (ContratoTercero contratoTerceroListOldContratoTercero : contratoTerceroListOld) {
                if (!contratoTerceroListNew.contains(contratoTerceroListOldContratoTercero)) {
                    contratoTerceroListOldContratoTercero.setIdGrupoNominal(null);
                    contratoTerceroListOldContratoTercero = em.merge(contratoTerceroListOldContratoTercero);
                }
            }
            for (ContratoTercero contratoTerceroListNewContratoTercero : contratoTerceroListNew) {
                if (!contratoTerceroListOld.contains(contratoTerceroListNewContratoTercero)) {
                    GrupoNominal oldIdGrupoNominalOfContratoTerceroListNewContratoTercero = contratoTerceroListNewContratoTercero.getIdGrupoNominal();
                    contratoTerceroListNewContratoTercero.setIdGrupoNominal(grupoNominal);
                    contratoTerceroListNewContratoTercero = em.merge(contratoTerceroListNewContratoTercero);
                    if (oldIdGrupoNominalOfContratoTerceroListNewContratoTercero != null && !oldIdGrupoNominalOfContratoTerceroListNewContratoTercero.equals(grupoNominal)) {
                        oldIdGrupoNominalOfContratoTerceroListNewContratoTercero.getContratoTerceroList().remove(contratoTerceroListNewContratoTercero);
                        oldIdGrupoNominalOfContratoTerceroListNewContratoTercero = em.merge(oldIdGrupoNominalOfContratoTerceroListNewContratoTercero);
                    }
                }
            }
            for (Nomina nominaListOldNomina : nominaListOld) {
                if (!nominaListNew.contains(nominaListOldNomina)) {
                    nominaListOldNomina.setIdGrupoNominal(null);
                    nominaListOldNomina = em.merge(nominaListOldNomina);
                }
            }
            for (Nomina nominaListNewNomina : nominaListNew) {
                if (!nominaListOld.contains(nominaListNewNomina)) {
                    GrupoNominal oldIdGrupoNominalOfNominaListNewNomina = nominaListNewNomina.getIdGrupoNominal();
                    nominaListNewNomina.setIdGrupoNominal(grupoNominal);
                    nominaListNewNomina = em.merge(nominaListNewNomina);
                    if (oldIdGrupoNominalOfNominaListNewNomina != null && !oldIdGrupoNominalOfNominaListNewNomina.equals(grupoNominal)) {
                        oldIdGrupoNominalOfNominaListNewNomina.getNominaList().remove(nominaListNewNomina);
                        oldIdGrupoNominalOfNominaListNewNomina = em.merge(oldIdGrupoNominalOfNominaListNewNomina);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = grupoNominal.getId();
                if (findGrupoNominal(id) == null) {
                    throw new NonexistentEntityException("The grupoNominal with id " + id + " no longer exists.");
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
            GrupoNominal grupoNominal;
            try {
                grupoNominal = em.getReference(GrupoNominal.class, id);
                grupoNominal.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupoNominal with id " + id + " no longer exists.", enfe);
            }
            PeriodoPago idPeriodo = grupoNominal.getIdPeriodo();
            if (idPeriodo != null) {
                idPeriodo.getGrupoNominalList().remove(grupoNominal);
                idPeriodo = em.merge(idPeriodo);
            }
            List<ContratoTercero> contratoTerceroList = grupoNominal.getContratoTerceroList();
            for (ContratoTercero contratoTerceroListContratoTercero : contratoTerceroList) {
                contratoTerceroListContratoTercero.setIdGrupoNominal(null);
                contratoTerceroListContratoTercero = em.merge(contratoTerceroListContratoTercero);
            }
            List<Nomina> nominaList = grupoNominal.getNominaList();
            for (Nomina nominaListNomina : nominaList) {
                nominaListNomina.setIdGrupoNominal(null);
                nominaListNomina = em.merge(nominaListNomina);
            }
            em.remove(grupoNominal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GrupoNominal> findGrupoNominalEntities() {
        return findGrupoNominalEntities(true, -1, -1);
    }

    public List<GrupoNominal> findGrupoNominalEntities(int maxResults, int firstResult) {
        return findGrupoNominalEntities(false, maxResults, firstResult);
    }

    private List<GrupoNominal> findGrupoNominalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GrupoNominal.class));
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

    public GrupoNominal findGrupoNominal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GrupoNominal.class, id);
        } finally {
            em.close();
        }
    }

    public int getGrupoNominalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GrupoNominal> rt = cq.from(GrupoNominal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
