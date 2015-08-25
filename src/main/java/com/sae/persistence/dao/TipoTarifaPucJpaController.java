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
import com.sae.persistence.domain.TipoIva;
import com.sae.persistence.domain.TarifaIva;
import com.sae.persistence.domain.Puc;
import com.sae.persistence.domain.RegimenTipoTarifaIva;
import com.sae.persistence.domain.TipoTarifaPuc;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoTarifaPucJpaController implements Serializable {

    public TipoTarifaPucJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoTarifaPuc tipoTarifaPuc) throws PreexistingEntityException, Exception {
        if (tipoTarifaPuc.getRegimenTipoTarifaIvaList() == null) {
            tipoTarifaPuc.setRegimenTipoTarifaIvaList(new ArrayList<RegimenTipoTarifaIva>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoIva idTipoIva = tipoTarifaPuc.getIdTipoIva();
            if (idTipoIva != null) {
                idTipoIva = em.getReference(idTipoIva.getClass(), idTipoIva.getId());
                tipoTarifaPuc.setIdTipoIva(idTipoIva);
            }
            TarifaIva idTarifaIva = tipoTarifaPuc.getIdTarifaIva();
            if (idTarifaIva != null) {
                idTarifaIva = em.getReference(idTarifaIva.getClass(), idTarifaIva.getId());
                tipoTarifaPuc.setIdTarifaIva(idTarifaIva);
            }
            Puc idPuc = tipoTarifaPuc.getIdPuc();
            if (idPuc != null) {
                idPuc = em.getReference(idPuc.getClass(), idPuc.getId());
                tipoTarifaPuc.setIdPuc(idPuc);
            }
            List<RegimenTipoTarifaIva> attachedRegimenTipoTarifaIvaList = new ArrayList<RegimenTipoTarifaIva>();
            for (RegimenTipoTarifaIva regimenTipoTarifaIvaListRegimenTipoTarifaIvaToAttach : tipoTarifaPuc.getRegimenTipoTarifaIvaList()) {
                regimenTipoTarifaIvaListRegimenTipoTarifaIvaToAttach = em.getReference(regimenTipoTarifaIvaListRegimenTipoTarifaIvaToAttach.getClass(), regimenTipoTarifaIvaListRegimenTipoTarifaIvaToAttach.getId());
                attachedRegimenTipoTarifaIvaList.add(regimenTipoTarifaIvaListRegimenTipoTarifaIvaToAttach);
            }
            tipoTarifaPuc.setRegimenTipoTarifaIvaList(attachedRegimenTipoTarifaIvaList);
            em.persist(tipoTarifaPuc);
            if (idTipoIva != null) {
                idTipoIva.getTipoTarifaPucList().add(tipoTarifaPuc);
                idTipoIva = em.merge(idTipoIva);
            }
            if (idTarifaIva != null) {
                idTarifaIva.getTipoTarifaPucList().add(tipoTarifaPuc);
                idTarifaIva = em.merge(idTarifaIva);
            }
            if (idPuc != null) {
                idPuc.getTipoTarifaPucList().add(tipoTarifaPuc);
                idPuc = em.merge(idPuc);
            }
            for (RegimenTipoTarifaIva regimenTipoTarifaIvaListRegimenTipoTarifaIva : tipoTarifaPuc.getRegimenTipoTarifaIvaList()) {
                TipoTarifaPuc oldIdTipoTarifaIvaOfRegimenTipoTarifaIvaListRegimenTipoTarifaIva = regimenTipoTarifaIvaListRegimenTipoTarifaIva.getIdTipoTarifaIva();
                regimenTipoTarifaIvaListRegimenTipoTarifaIva.setIdTipoTarifaIva(tipoTarifaPuc);
                regimenTipoTarifaIvaListRegimenTipoTarifaIva = em.merge(regimenTipoTarifaIvaListRegimenTipoTarifaIva);
                if (oldIdTipoTarifaIvaOfRegimenTipoTarifaIvaListRegimenTipoTarifaIva != null) {
                    oldIdTipoTarifaIvaOfRegimenTipoTarifaIvaListRegimenTipoTarifaIva.getRegimenTipoTarifaIvaList().remove(regimenTipoTarifaIvaListRegimenTipoTarifaIva);
                    oldIdTipoTarifaIvaOfRegimenTipoTarifaIvaListRegimenTipoTarifaIva = em.merge(oldIdTipoTarifaIvaOfRegimenTipoTarifaIvaListRegimenTipoTarifaIva);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoTarifaPuc(tipoTarifaPuc.getId()) != null) {
                throw new PreexistingEntityException("TipoTarifaPuc " + tipoTarifaPuc + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoTarifaPuc tipoTarifaPuc) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoTarifaPuc persistentTipoTarifaPuc = em.find(TipoTarifaPuc.class, tipoTarifaPuc.getId());
            TipoIva idTipoIvaOld = persistentTipoTarifaPuc.getIdTipoIva();
            TipoIva idTipoIvaNew = tipoTarifaPuc.getIdTipoIva();
            TarifaIva idTarifaIvaOld = persistentTipoTarifaPuc.getIdTarifaIva();
            TarifaIva idTarifaIvaNew = tipoTarifaPuc.getIdTarifaIva();
            Puc idPucOld = persistentTipoTarifaPuc.getIdPuc();
            Puc idPucNew = tipoTarifaPuc.getIdPuc();
            List<RegimenTipoTarifaIva> regimenTipoTarifaIvaListOld = persistentTipoTarifaPuc.getRegimenTipoTarifaIvaList();
            List<RegimenTipoTarifaIva> regimenTipoTarifaIvaListNew = tipoTarifaPuc.getRegimenTipoTarifaIvaList();
            if (idTipoIvaNew != null) {
                idTipoIvaNew = em.getReference(idTipoIvaNew.getClass(), idTipoIvaNew.getId());
                tipoTarifaPuc.setIdTipoIva(idTipoIvaNew);
            }
            if (idTarifaIvaNew != null) {
                idTarifaIvaNew = em.getReference(idTarifaIvaNew.getClass(), idTarifaIvaNew.getId());
                tipoTarifaPuc.setIdTarifaIva(idTarifaIvaNew);
            }
            if (idPucNew != null) {
                idPucNew = em.getReference(idPucNew.getClass(), idPucNew.getId());
                tipoTarifaPuc.setIdPuc(idPucNew);
            }
            List<RegimenTipoTarifaIva> attachedRegimenTipoTarifaIvaListNew = new ArrayList<RegimenTipoTarifaIva>();
            for (RegimenTipoTarifaIva regimenTipoTarifaIvaListNewRegimenTipoTarifaIvaToAttach : regimenTipoTarifaIvaListNew) {
                regimenTipoTarifaIvaListNewRegimenTipoTarifaIvaToAttach = em.getReference(regimenTipoTarifaIvaListNewRegimenTipoTarifaIvaToAttach.getClass(), regimenTipoTarifaIvaListNewRegimenTipoTarifaIvaToAttach.getId());
                attachedRegimenTipoTarifaIvaListNew.add(regimenTipoTarifaIvaListNewRegimenTipoTarifaIvaToAttach);
            }
            regimenTipoTarifaIvaListNew = attachedRegimenTipoTarifaIvaListNew;
            tipoTarifaPuc.setRegimenTipoTarifaIvaList(regimenTipoTarifaIvaListNew);
            tipoTarifaPuc = em.merge(tipoTarifaPuc);
            if (idTipoIvaOld != null && !idTipoIvaOld.equals(idTipoIvaNew)) {
                idTipoIvaOld.getTipoTarifaPucList().remove(tipoTarifaPuc);
                idTipoIvaOld = em.merge(idTipoIvaOld);
            }
            if (idTipoIvaNew != null && !idTipoIvaNew.equals(idTipoIvaOld)) {
                idTipoIvaNew.getTipoTarifaPucList().add(tipoTarifaPuc);
                idTipoIvaNew = em.merge(idTipoIvaNew);
            }
            if (idTarifaIvaOld != null && !idTarifaIvaOld.equals(idTarifaIvaNew)) {
                idTarifaIvaOld.getTipoTarifaPucList().remove(tipoTarifaPuc);
                idTarifaIvaOld = em.merge(idTarifaIvaOld);
            }
            if (idTarifaIvaNew != null && !idTarifaIvaNew.equals(idTarifaIvaOld)) {
                idTarifaIvaNew.getTipoTarifaPucList().add(tipoTarifaPuc);
                idTarifaIvaNew = em.merge(idTarifaIvaNew);
            }
            if (idPucOld != null && !idPucOld.equals(idPucNew)) {
                idPucOld.getTipoTarifaPucList().remove(tipoTarifaPuc);
                idPucOld = em.merge(idPucOld);
            }
            if (idPucNew != null && !idPucNew.equals(idPucOld)) {
                idPucNew.getTipoTarifaPucList().add(tipoTarifaPuc);
                idPucNew = em.merge(idPucNew);
            }
            for (RegimenTipoTarifaIva regimenTipoTarifaIvaListOldRegimenTipoTarifaIva : regimenTipoTarifaIvaListOld) {
                if (!regimenTipoTarifaIvaListNew.contains(regimenTipoTarifaIvaListOldRegimenTipoTarifaIva)) {
                    regimenTipoTarifaIvaListOldRegimenTipoTarifaIva.setIdTipoTarifaIva(null);
                    regimenTipoTarifaIvaListOldRegimenTipoTarifaIva = em.merge(regimenTipoTarifaIvaListOldRegimenTipoTarifaIva);
                }
            }
            for (RegimenTipoTarifaIva regimenTipoTarifaIvaListNewRegimenTipoTarifaIva : regimenTipoTarifaIvaListNew) {
                if (!regimenTipoTarifaIvaListOld.contains(regimenTipoTarifaIvaListNewRegimenTipoTarifaIva)) {
                    TipoTarifaPuc oldIdTipoTarifaIvaOfRegimenTipoTarifaIvaListNewRegimenTipoTarifaIva = regimenTipoTarifaIvaListNewRegimenTipoTarifaIva.getIdTipoTarifaIva();
                    regimenTipoTarifaIvaListNewRegimenTipoTarifaIva.setIdTipoTarifaIva(tipoTarifaPuc);
                    regimenTipoTarifaIvaListNewRegimenTipoTarifaIva = em.merge(regimenTipoTarifaIvaListNewRegimenTipoTarifaIva);
                    if (oldIdTipoTarifaIvaOfRegimenTipoTarifaIvaListNewRegimenTipoTarifaIva != null && !oldIdTipoTarifaIvaOfRegimenTipoTarifaIvaListNewRegimenTipoTarifaIva.equals(tipoTarifaPuc)) {
                        oldIdTipoTarifaIvaOfRegimenTipoTarifaIvaListNewRegimenTipoTarifaIva.getRegimenTipoTarifaIvaList().remove(regimenTipoTarifaIvaListNewRegimenTipoTarifaIva);
                        oldIdTipoTarifaIvaOfRegimenTipoTarifaIvaListNewRegimenTipoTarifaIva = em.merge(oldIdTipoTarifaIvaOfRegimenTipoTarifaIvaListNewRegimenTipoTarifaIva);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoTarifaPuc.getId();
                if (findTipoTarifaPuc(id) == null) {
                    throw new NonexistentEntityException("The tipoTarifaPuc with id " + id + " no longer exists.");
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
            TipoTarifaPuc tipoTarifaPuc;
            try {
                tipoTarifaPuc = em.getReference(TipoTarifaPuc.class, id);
                tipoTarifaPuc.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoTarifaPuc with id " + id + " no longer exists.", enfe);
            }
            TipoIva idTipoIva = tipoTarifaPuc.getIdTipoIva();
            if (idTipoIva != null) {
                idTipoIva.getTipoTarifaPucList().remove(tipoTarifaPuc);
                idTipoIva = em.merge(idTipoIva);
            }
            TarifaIva idTarifaIva = tipoTarifaPuc.getIdTarifaIva();
            if (idTarifaIva != null) {
                idTarifaIva.getTipoTarifaPucList().remove(tipoTarifaPuc);
                idTarifaIva = em.merge(idTarifaIva);
            }
            Puc idPuc = tipoTarifaPuc.getIdPuc();
            if (idPuc != null) {
                idPuc.getTipoTarifaPucList().remove(tipoTarifaPuc);
                idPuc = em.merge(idPuc);
            }
            List<RegimenTipoTarifaIva> regimenTipoTarifaIvaList = tipoTarifaPuc.getRegimenTipoTarifaIvaList();
            for (RegimenTipoTarifaIva regimenTipoTarifaIvaListRegimenTipoTarifaIva : regimenTipoTarifaIvaList) {
                regimenTipoTarifaIvaListRegimenTipoTarifaIva.setIdTipoTarifaIva(null);
                regimenTipoTarifaIvaListRegimenTipoTarifaIva = em.merge(regimenTipoTarifaIvaListRegimenTipoTarifaIva);
            }
            em.remove(tipoTarifaPuc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoTarifaPuc> findTipoTarifaPucEntities() {
        return findTipoTarifaPucEntities(true, -1, -1);
    }

    public List<TipoTarifaPuc> findTipoTarifaPucEntities(int maxResults, int firstResult) {
        return findTipoTarifaPucEntities(false, maxResults, firstResult);
    }

    private List<TipoTarifaPuc> findTipoTarifaPucEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoTarifaPuc.class));
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

    public TipoTarifaPuc findTipoTarifaPuc(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoTarifaPuc.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoTarifaPucCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoTarifaPuc> rt = cq.from(TipoTarifaPuc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
