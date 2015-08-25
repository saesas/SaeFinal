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
import com.sae.persistence.domain.GrupoNominal;
import com.sae.persistence.domain.ValorPagarNomina;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.EstadoNomina;
import com.sae.persistence.domain.Nomina;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class NominaJpaController implements Serializable {

    public NominaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Nomina nomina) throws PreexistingEntityException, Exception {
        if (nomina.getValorPagarNominaList() == null) {
            nomina.setValorPagarNominaList(new ArrayList<ValorPagarNomina>());
        }
        if (nomina.getEstadoNominaList() == null) {
            nomina.setEstadoNominaList(new ArrayList<EstadoNomina>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GrupoNominal idGrupoNominal = nomina.getIdGrupoNominal();
            if (idGrupoNominal != null) {
                idGrupoNominal = em.getReference(idGrupoNominal.getClass(), idGrupoNominal.getId());
                nomina.setIdGrupoNominal(idGrupoNominal);
            }
            List<ValorPagarNomina> attachedValorPagarNominaList = new ArrayList<ValorPagarNomina>();
            for (ValorPagarNomina valorPagarNominaListValorPagarNominaToAttach : nomina.getValorPagarNominaList()) {
                valorPagarNominaListValorPagarNominaToAttach = em.getReference(valorPagarNominaListValorPagarNominaToAttach.getClass(), valorPagarNominaListValorPagarNominaToAttach.getId());
                attachedValorPagarNominaList.add(valorPagarNominaListValorPagarNominaToAttach);
            }
            nomina.setValorPagarNominaList(attachedValorPagarNominaList);
            List<EstadoNomina> attachedEstadoNominaList = new ArrayList<EstadoNomina>();
            for (EstadoNomina estadoNominaListEstadoNominaToAttach : nomina.getEstadoNominaList()) {
                estadoNominaListEstadoNominaToAttach = em.getReference(estadoNominaListEstadoNominaToAttach.getClass(), estadoNominaListEstadoNominaToAttach.getId());
                attachedEstadoNominaList.add(estadoNominaListEstadoNominaToAttach);
            }
            nomina.setEstadoNominaList(attachedEstadoNominaList);
            em.persist(nomina);
            if (idGrupoNominal != null) {
                idGrupoNominal.getNominaList().add(nomina);
                idGrupoNominal = em.merge(idGrupoNominal);
            }
            for (ValorPagarNomina valorPagarNominaListValorPagarNomina : nomina.getValorPagarNominaList()) {
                Nomina oldIdNominaOfValorPagarNominaListValorPagarNomina = valorPagarNominaListValorPagarNomina.getIdNomina();
                valorPagarNominaListValorPagarNomina.setIdNomina(nomina);
                valorPagarNominaListValorPagarNomina = em.merge(valorPagarNominaListValorPagarNomina);
                if (oldIdNominaOfValorPagarNominaListValorPagarNomina != null) {
                    oldIdNominaOfValorPagarNominaListValorPagarNomina.getValorPagarNominaList().remove(valorPagarNominaListValorPagarNomina);
                    oldIdNominaOfValorPagarNominaListValorPagarNomina = em.merge(oldIdNominaOfValorPagarNominaListValorPagarNomina);
                }
            }
            for (EstadoNomina estadoNominaListEstadoNomina : nomina.getEstadoNominaList()) {
                Nomina oldIdNominaOfEstadoNominaListEstadoNomina = estadoNominaListEstadoNomina.getIdNomina();
                estadoNominaListEstadoNomina.setIdNomina(nomina);
                estadoNominaListEstadoNomina = em.merge(estadoNominaListEstadoNomina);
                if (oldIdNominaOfEstadoNominaListEstadoNomina != null) {
                    oldIdNominaOfEstadoNominaListEstadoNomina.getEstadoNominaList().remove(estadoNominaListEstadoNomina);
                    oldIdNominaOfEstadoNominaListEstadoNomina = em.merge(oldIdNominaOfEstadoNominaListEstadoNomina);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNomina(nomina.getId()) != null) {
                throw new PreexistingEntityException("Nomina " + nomina + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Nomina nomina) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Nomina persistentNomina = em.find(Nomina.class, nomina.getId());
            GrupoNominal idGrupoNominalOld = persistentNomina.getIdGrupoNominal();
            GrupoNominal idGrupoNominalNew = nomina.getIdGrupoNominal();
            List<ValorPagarNomina> valorPagarNominaListOld = persistentNomina.getValorPagarNominaList();
            List<ValorPagarNomina> valorPagarNominaListNew = nomina.getValorPagarNominaList();
            List<EstadoNomina> estadoNominaListOld = persistentNomina.getEstadoNominaList();
            List<EstadoNomina> estadoNominaListNew = nomina.getEstadoNominaList();
            if (idGrupoNominalNew != null) {
                idGrupoNominalNew = em.getReference(idGrupoNominalNew.getClass(), idGrupoNominalNew.getId());
                nomina.setIdGrupoNominal(idGrupoNominalNew);
            }
            List<ValorPagarNomina> attachedValorPagarNominaListNew = new ArrayList<ValorPagarNomina>();
            for (ValorPagarNomina valorPagarNominaListNewValorPagarNominaToAttach : valorPagarNominaListNew) {
                valorPagarNominaListNewValorPagarNominaToAttach = em.getReference(valorPagarNominaListNewValorPagarNominaToAttach.getClass(), valorPagarNominaListNewValorPagarNominaToAttach.getId());
                attachedValorPagarNominaListNew.add(valorPagarNominaListNewValorPagarNominaToAttach);
            }
            valorPagarNominaListNew = attachedValorPagarNominaListNew;
            nomina.setValorPagarNominaList(valorPagarNominaListNew);
            List<EstadoNomina> attachedEstadoNominaListNew = new ArrayList<EstadoNomina>();
            for (EstadoNomina estadoNominaListNewEstadoNominaToAttach : estadoNominaListNew) {
                estadoNominaListNewEstadoNominaToAttach = em.getReference(estadoNominaListNewEstadoNominaToAttach.getClass(), estadoNominaListNewEstadoNominaToAttach.getId());
                attachedEstadoNominaListNew.add(estadoNominaListNewEstadoNominaToAttach);
            }
            estadoNominaListNew = attachedEstadoNominaListNew;
            nomina.setEstadoNominaList(estadoNominaListNew);
            nomina = em.merge(nomina);
            if (idGrupoNominalOld != null && !idGrupoNominalOld.equals(idGrupoNominalNew)) {
                idGrupoNominalOld.getNominaList().remove(nomina);
                idGrupoNominalOld = em.merge(idGrupoNominalOld);
            }
            if (idGrupoNominalNew != null && !idGrupoNominalNew.equals(idGrupoNominalOld)) {
                idGrupoNominalNew.getNominaList().add(nomina);
                idGrupoNominalNew = em.merge(idGrupoNominalNew);
            }
            for (ValorPagarNomina valorPagarNominaListOldValorPagarNomina : valorPagarNominaListOld) {
                if (!valorPagarNominaListNew.contains(valorPagarNominaListOldValorPagarNomina)) {
                    valorPagarNominaListOldValorPagarNomina.setIdNomina(null);
                    valorPagarNominaListOldValorPagarNomina = em.merge(valorPagarNominaListOldValorPagarNomina);
                }
            }
            for (ValorPagarNomina valorPagarNominaListNewValorPagarNomina : valorPagarNominaListNew) {
                if (!valorPagarNominaListOld.contains(valorPagarNominaListNewValorPagarNomina)) {
                    Nomina oldIdNominaOfValorPagarNominaListNewValorPagarNomina = valorPagarNominaListNewValorPagarNomina.getIdNomina();
                    valorPagarNominaListNewValorPagarNomina.setIdNomina(nomina);
                    valorPagarNominaListNewValorPagarNomina = em.merge(valorPagarNominaListNewValorPagarNomina);
                    if (oldIdNominaOfValorPagarNominaListNewValorPagarNomina != null && !oldIdNominaOfValorPagarNominaListNewValorPagarNomina.equals(nomina)) {
                        oldIdNominaOfValorPagarNominaListNewValorPagarNomina.getValorPagarNominaList().remove(valorPagarNominaListNewValorPagarNomina);
                        oldIdNominaOfValorPagarNominaListNewValorPagarNomina = em.merge(oldIdNominaOfValorPagarNominaListNewValorPagarNomina);
                    }
                }
            }
            for (EstadoNomina estadoNominaListOldEstadoNomina : estadoNominaListOld) {
                if (!estadoNominaListNew.contains(estadoNominaListOldEstadoNomina)) {
                    estadoNominaListOldEstadoNomina.setIdNomina(null);
                    estadoNominaListOldEstadoNomina = em.merge(estadoNominaListOldEstadoNomina);
                }
            }
            for (EstadoNomina estadoNominaListNewEstadoNomina : estadoNominaListNew) {
                if (!estadoNominaListOld.contains(estadoNominaListNewEstadoNomina)) {
                    Nomina oldIdNominaOfEstadoNominaListNewEstadoNomina = estadoNominaListNewEstadoNomina.getIdNomina();
                    estadoNominaListNewEstadoNomina.setIdNomina(nomina);
                    estadoNominaListNewEstadoNomina = em.merge(estadoNominaListNewEstadoNomina);
                    if (oldIdNominaOfEstadoNominaListNewEstadoNomina != null && !oldIdNominaOfEstadoNominaListNewEstadoNomina.equals(nomina)) {
                        oldIdNominaOfEstadoNominaListNewEstadoNomina.getEstadoNominaList().remove(estadoNominaListNewEstadoNomina);
                        oldIdNominaOfEstadoNominaListNewEstadoNomina = em.merge(oldIdNominaOfEstadoNominaListNewEstadoNomina);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = nomina.getId();
                if (findNomina(id) == null) {
                    throw new NonexistentEntityException("The nomina with id " + id + " no longer exists.");
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
            Nomina nomina;
            try {
                nomina = em.getReference(Nomina.class, id);
                nomina.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nomina with id " + id + " no longer exists.", enfe);
            }
            GrupoNominal idGrupoNominal = nomina.getIdGrupoNominal();
            if (idGrupoNominal != null) {
                idGrupoNominal.getNominaList().remove(nomina);
                idGrupoNominal = em.merge(idGrupoNominal);
            }
            List<ValorPagarNomina> valorPagarNominaList = nomina.getValorPagarNominaList();
            for (ValorPagarNomina valorPagarNominaListValorPagarNomina : valorPagarNominaList) {
                valorPagarNominaListValorPagarNomina.setIdNomina(null);
                valorPagarNominaListValorPagarNomina = em.merge(valorPagarNominaListValorPagarNomina);
            }
            List<EstadoNomina> estadoNominaList = nomina.getEstadoNominaList();
            for (EstadoNomina estadoNominaListEstadoNomina : estadoNominaList) {
                estadoNominaListEstadoNomina.setIdNomina(null);
                estadoNominaListEstadoNomina = em.merge(estadoNominaListEstadoNomina);
            }
            em.remove(nomina);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Nomina> findNominaEntities() {
        return findNominaEntities(true, -1, -1);
    }

    public List<Nomina> findNominaEntities(int maxResults, int firstResult) {
        return findNominaEntities(false, maxResults, firstResult);
    }

    private List<Nomina> findNominaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Nomina.class));
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

    public Nomina findNomina(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Nomina.class, id);
        } finally {
            em.close();
        }
    }

    public int getNominaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Nomina> rt = cq.from(Nomina.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
