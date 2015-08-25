/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.Regimen;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.RegimenTipoTarifaIva;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.ValorRetencion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class RegimenJpaController implements Serializable {

    public RegimenJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Regimen regimen) throws PreexistingEntityException, Exception {
        if (regimen.getRegimenTipoTarifaIvaList() == null) {
            regimen.setRegimenTipoTarifaIvaList(new ArrayList<RegimenTipoTarifaIva>());
        }
        if (regimen.getValorRetencionList() == null) {
            regimen.setValorRetencionList(new ArrayList<ValorRetencion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<RegimenTipoTarifaIva> attachedRegimenTipoTarifaIvaList = new ArrayList<RegimenTipoTarifaIva>();
            for (RegimenTipoTarifaIva regimenTipoTarifaIvaListRegimenTipoTarifaIvaToAttach : regimen.getRegimenTipoTarifaIvaList()) {
                regimenTipoTarifaIvaListRegimenTipoTarifaIvaToAttach = em.getReference(regimenTipoTarifaIvaListRegimenTipoTarifaIvaToAttach.getClass(), regimenTipoTarifaIvaListRegimenTipoTarifaIvaToAttach.getId());
                attachedRegimenTipoTarifaIvaList.add(regimenTipoTarifaIvaListRegimenTipoTarifaIvaToAttach);
            }
            regimen.setRegimenTipoTarifaIvaList(attachedRegimenTipoTarifaIvaList);
            List<ValorRetencion> attachedValorRetencionList = new ArrayList<ValorRetencion>();
            for (ValorRetencion valorRetencionListValorRetencionToAttach : regimen.getValorRetencionList()) {
                valorRetencionListValorRetencionToAttach = em.getReference(valorRetencionListValorRetencionToAttach.getClass(), valorRetencionListValorRetencionToAttach.getId());
                attachedValorRetencionList.add(valorRetencionListValorRetencionToAttach);
            }
            regimen.setValorRetencionList(attachedValorRetencionList);
            em.persist(regimen);
            for (RegimenTipoTarifaIva regimenTipoTarifaIvaListRegimenTipoTarifaIva : regimen.getRegimenTipoTarifaIvaList()) {
                Regimen oldIdRegimenOfRegimenTipoTarifaIvaListRegimenTipoTarifaIva = regimenTipoTarifaIvaListRegimenTipoTarifaIva.getIdRegimen();
                regimenTipoTarifaIvaListRegimenTipoTarifaIva.setIdRegimen(regimen);
                regimenTipoTarifaIvaListRegimenTipoTarifaIva = em.merge(regimenTipoTarifaIvaListRegimenTipoTarifaIva);
                if (oldIdRegimenOfRegimenTipoTarifaIvaListRegimenTipoTarifaIva != null) {
                    oldIdRegimenOfRegimenTipoTarifaIvaListRegimenTipoTarifaIva.getRegimenTipoTarifaIvaList().remove(regimenTipoTarifaIvaListRegimenTipoTarifaIva);
                    oldIdRegimenOfRegimenTipoTarifaIvaListRegimenTipoTarifaIva = em.merge(oldIdRegimenOfRegimenTipoTarifaIvaListRegimenTipoTarifaIva);
                }
            }
            for (ValorRetencion valorRetencionListValorRetencion : regimen.getValorRetencionList()) {
                Regimen oldIdRegimenNodeclaranteOfValorRetencionListValorRetencion = valorRetencionListValorRetencion.getIdRegimenNodeclarante();
                valorRetencionListValorRetencion.setIdRegimenNodeclarante(regimen);
                valorRetencionListValorRetencion = em.merge(valorRetencionListValorRetencion);
                if (oldIdRegimenNodeclaranteOfValorRetencionListValorRetencion != null) {
                    oldIdRegimenNodeclaranteOfValorRetencionListValorRetencion.getValorRetencionList().remove(valorRetencionListValorRetencion);
                    oldIdRegimenNodeclaranteOfValorRetencionListValorRetencion = em.merge(oldIdRegimenNodeclaranteOfValorRetencionListValorRetencion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRegimen(regimen.getId()) != null) {
                throw new PreexistingEntityException("Regimen " + regimen + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Regimen regimen) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Regimen persistentRegimen = em.find(Regimen.class, regimen.getId());
            List<RegimenTipoTarifaIva> regimenTipoTarifaIvaListOld = persistentRegimen.getRegimenTipoTarifaIvaList();
            List<RegimenTipoTarifaIva> regimenTipoTarifaIvaListNew = regimen.getRegimenTipoTarifaIvaList();
            List<ValorRetencion> valorRetencionListOld = persistentRegimen.getValorRetencionList();
            List<ValorRetencion> valorRetencionListNew = regimen.getValorRetencionList();
            List<RegimenTipoTarifaIva> attachedRegimenTipoTarifaIvaListNew = new ArrayList<RegimenTipoTarifaIva>();
            for (RegimenTipoTarifaIva regimenTipoTarifaIvaListNewRegimenTipoTarifaIvaToAttach : regimenTipoTarifaIvaListNew) {
                regimenTipoTarifaIvaListNewRegimenTipoTarifaIvaToAttach = em.getReference(regimenTipoTarifaIvaListNewRegimenTipoTarifaIvaToAttach.getClass(), regimenTipoTarifaIvaListNewRegimenTipoTarifaIvaToAttach.getId());
                attachedRegimenTipoTarifaIvaListNew.add(regimenTipoTarifaIvaListNewRegimenTipoTarifaIvaToAttach);
            }
            regimenTipoTarifaIvaListNew = attachedRegimenTipoTarifaIvaListNew;
            regimen.setRegimenTipoTarifaIvaList(regimenTipoTarifaIvaListNew);
            List<ValorRetencion> attachedValorRetencionListNew = new ArrayList<ValorRetencion>();
            for (ValorRetencion valorRetencionListNewValorRetencionToAttach : valorRetencionListNew) {
                valorRetencionListNewValorRetencionToAttach = em.getReference(valorRetencionListNewValorRetencionToAttach.getClass(), valorRetencionListNewValorRetencionToAttach.getId());
                attachedValorRetencionListNew.add(valorRetencionListNewValorRetencionToAttach);
            }
            valorRetencionListNew = attachedValorRetencionListNew;
            regimen.setValorRetencionList(valorRetencionListNew);
            regimen = em.merge(regimen);
            for (RegimenTipoTarifaIva regimenTipoTarifaIvaListOldRegimenTipoTarifaIva : regimenTipoTarifaIvaListOld) {
                if (!regimenTipoTarifaIvaListNew.contains(regimenTipoTarifaIvaListOldRegimenTipoTarifaIva)) {
                    regimenTipoTarifaIvaListOldRegimenTipoTarifaIva.setIdRegimen(null);
                    regimenTipoTarifaIvaListOldRegimenTipoTarifaIva = em.merge(regimenTipoTarifaIvaListOldRegimenTipoTarifaIva);
                }
            }
            for (RegimenTipoTarifaIva regimenTipoTarifaIvaListNewRegimenTipoTarifaIva : regimenTipoTarifaIvaListNew) {
                if (!regimenTipoTarifaIvaListOld.contains(regimenTipoTarifaIvaListNewRegimenTipoTarifaIva)) {
                    Regimen oldIdRegimenOfRegimenTipoTarifaIvaListNewRegimenTipoTarifaIva = regimenTipoTarifaIvaListNewRegimenTipoTarifaIva.getIdRegimen();
                    regimenTipoTarifaIvaListNewRegimenTipoTarifaIva.setIdRegimen(regimen);
                    regimenTipoTarifaIvaListNewRegimenTipoTarifaIva = em.merge(regimenTipoTarifaIvaListNewRegimenTipoTarifaIva);
                    if (oldIdRegimenOfRegimenTipoTarifaIvaListNewRegimenTipoTarifaIva != null && !oldIdRegimenOfRegimenTipoTarifaIvaListNewRegimenTipoTarifaIva.equals(regimen)) {
                        oldIdRegimenOfRegimenTipoTarifaIvaListNewRegimenTipoTarifaIva.getRegimenTipoTarifaIvaList().remove(regimenTipoTarifaIvaListNewRegimenTipoTarifaIva);
                        oldIdRegimenOfRegimenTipoTarifaIvaListNewRegimenTipoTarifaIva = em.merge(oldIdRegimenOfRegimenTipoTarifaIvaListNewRegimenTipoTarifaIva);
                    }
                }
            }
            for (ValorRetencion valorRetencionListOldValorRetencion : valorRetencionListOld) {
                if (!valorRetencionListNew.contains(valorRetencionListOldValorRetencion)) {
                    valorRetencionListOldValorRetencion.setIdRegimenNodeclarante(null);
                    valorRetencionListOldValorRetencion = em.merge(valorRetencionListOldValorRetencion);
                }
            }
            for (ValorRetencion valorRetencionListNewValorRetencion : valorRetencionListNew) {
                if (!valorRetencionListOld.contains(valorRetencionListNewValorRetencion)) {
                    Regimen oldIdRegimenNodeclaranteOfValorRetencionListNewValorRetencion = valorRetencionListNewValorRetencion.getIdRegimenNodeclarante();
                    valorRetencionListNewValorRetencion.setIdRegimenNodeclarante(regimen);
                    valorRetencionListNewValorRetencion = em.merge(valorRetencionListNewValorRetencion);
                    if (oldIdRegimenNodeclaranteOfValorRetencionListNewValorRetencion != null && !oldIdRegimenNodeclaranteOfValorRetencionListNewValorRetencion.equals(regimen)) {
                        oldIdRegimenNodeclaranteOfValorRetencionListNewValorRetencion.getValorRetencionList().remove(valorRetencionListNewValorRetencion);
                        oldIdRegimenNodeclaranteOfValorRetencionListNewValorRetencion = em.merge(oldIdRegimenNodeclaranteOfValorRetencionListNewValorRetencion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = regimen.getId();
                if (findRegimen(id) == null) {
                    throw new NonexistentEntityException("The regimen with id " + id + " no longer exists.");
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
            Regimen regimen;
            try {
                regimen = em.getReference(Regimen.class, id);
                regimen.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The regimen with id " + id + " no longer exists.", enfe);
            }
            List<RegimenTipoTarifaIva> regimenTipoTarifaIvaList = regimen.getRegimenTipoTarifaIvaList();
            for (RegimenTipoTarifaIva regimenTipoTarifaIvaListRegimenTipoTarifaIva : regimenTipoTarifaIvaList) {
                regimenTipoTarifaIvaListRegimenTipoTarifaIva.setIdRegimen(null);
                regimenTipoTarifaIvaListRegimenTipoTarifaIva = em.merge(regimenTipoTarifaIvaListRegimenTipoTarifaIva);
            }
            List<ValorRetencion> valorRetencionList = regimen.getValorRetencionList();
            for (ValorRetencion valorRetencionListValorRetencion : valorRetencionList) {
                valorRetencionListValorRetencion.setIdRegimenNodeclarante(null);
                valorRetencionListValorRetencion = em.merge(valorRetencionListValorRetencion);
            }
            em.remove(regimen);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Regimen> findRegimenEntities() {
        return findRegimenEntities(true, -1, -1);
    }

    public List<Regimen> findRegimenEntities(int maxResults, int firstResult) {
        return findRegimenEntities(false, maxResults, firstResult);
    }

    private List<Regimen> findRegimenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Regimen.class));
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

    public Regimen findRegimen(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Regimen.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegimenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Regimen> rt = cq.from(Regimen.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
