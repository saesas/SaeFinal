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
import com.sae.persistence.domain.ClausulaContrato;
import com.sae.persistence.domain.TipoClausulaContrato;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoClausulaContratoJpaController implements Serializable {

    public TipoClausulaContratoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoClausulaContrato tipoClausulaContrato) throws PreexistingEntityException, Exception {
        if (tipoClausulaContrato.getClausulaContratoList() == null) {
            tipoClausulaContrato.setClausulaContratoList(new ArrayList<ClausulaContrato>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ClausulaContrato> attachedClausulaContratoList = new ArrayList<ClausulaContrato>();
            for (ClausulaContrato clausulaContratoListClausulaContratoToAttach : tipoClausulaContrato.getClausulaContratoList()) {
                clausulaContratoListClausulaContratoToAttach = em.getReference(clausulaContratoListClausulaContratoToAttach.getClass(), clausulaContratoListClausulaContratoToAttach.getId());
                attachedClausulaContratoList.add(clausulaContratoListClausulaContratoToAttach);
            }
            tipoClausulaContrato.setClausulaContratoList(attachedClausulaContratoList);
            em.persist(tipoClausulaContrato);
            for (ClausulaContrato clausulaContratoListClausulaContrato : tipoClausulaContrato.getClausulaContratoList()) {
                TipoClausulaContrato oldIdClausulaOfClausulaContratoListClausulaContrato = clausulaContratoListClausulaContrato.getIdClausula();
                clausulaContratoListClausulaContrato.setIdClausula(tipoClausulaContrato);
                clausulaContratoListClausulaContrato = em.merge(clausulaContratoListClausulaContrato);
                if (oldIdClausulaOfClausulaContratoListClausulaContrato != null) {
                    oldIdClausulaOfClausulaContratoListClausulaContrato.getClausulaContratoList().remove(clausulaContratoListClausulaContrato);
                    oldIdClausulaOfClausulaContratoListClausulaContrato = em.merge(oldIdClausulaOfClausulaContratoListClausulaContrato);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoClausulaContrato(tipoClausulaContrato.getId()) != null) {
                throw new PreexistingEntityException("TipoClausulaContrato " + tipoClausulaContrato + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoClausulaContrato tipoClausulaContrato) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoClausulaContrato persistentTipoClausulaContrato = em.find(TipoClausulaContrato.class, tipoClausulaContrato.getId());
            List<ClausulaContrato> clausulaContratoListOld = persistentTipoClausulaContrato.getClausulaContratoList();
            List<ClausulaContrato> clausulaContratoListNew = tipoClausulaContrato.getClausulaContratoList();
            List<ClausulaContrato> attachedClausulaContratoListNew = new ArrayList<ClausulaContrato>();
            for (ClausulaContrato clausulaContratoListNewClausulaContratoToAttach : clausulaContratoListNew) {
                clausulaContratoListNewClausulaContratoToAttach = em.getReference(clausulaContratoListNewClausulaContratoToAttach.getClass(), clausulaContratoListNewClausulaContratoToAttach.getId());
                attachedClausulaContratoListNew.add(clausulaContratoListNewClausulaContratoToAttach);
            }
            clausulaContratoListNew = attachedClausulaContratoListNew;
            tipoClausulaContrato.setClausulaContratoList(clausulaContratoListNew);
            tipoClausulaContrato = em.merge(tipoClausulaContrato);
            for (ClausulaContrato clausulaContratoListOldClausulaContrato : clausulaContratoListOld) {
                if (!clausulaContratoListNew.contains(clausulaContratoListOldClausulaContrato)) {
                    clausulaContratoListOldClausulaContrato.setIdClausula(null);
                    clausulaContratoListOldClausulaContrato = em.merge(clausulaContratoListOldClausulaContrato);
                }
            }
            for (ClausulaContrato clausulaContratoListNewClausulaContrato : clausulaContratoListNew) {
                if (!clausulaContratoListOld.contains(clausulaContratoListNewClausulaContrato)) {
                    TipoClausulaContrato oldIdClausulaOfClausulaContratoListNewClausulaContrato = clausulaContratoListNewClausulaContrato.getIdClausula();
                    clausulaContratoListNewClausulaContrato.setIdClausula(tipoClausulaContrato);
                    clausulaContratoListNewClausulaContrato = em.merge(clausulaContratoListNewClausulaContrato);
                    if (oldIdClausulaOfClausulaContratoListNewClausulaContrato != null && !oldIdClausulaOfClausulaContratoListNewClausulaContrato.equals(tipoClausulaContrato)) {
                        oldIdClausulaOfClausulaContratoListNewClausulaContrato.getClausulaContratoList().remove(clausulaContratoListNewClausulaContrato);
                        oldIdClausulaOfClausulaContratoListNewClausulaContrato = em.merge(oldIdClausulaOfClausulaContratoListNewClausulaContrato);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoClausulaContrato.getId();
                if (findTipoClausulaContrato(id) == null) {
                    throw new NonexistentEntityException("The tipoClausulaContrato with id " + id + " no longer exists.");
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
            TipoClausulaContrato tipoClausulaContrato;
            try {
                tipoClausulaContrato = em.getReference(TipoClausulaContrato.class, id);
                tipoClausulaContrato.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoClausulaContrato with id " + id + " no longer exists.", enfe);
            }
            List<ClausulaContrato> clausulaContratoList = tipoClausulaContrato.getClausulaContratoList();
            for (ClausulaContrato clausulaContratoListClausulaContrato : clausulaContratoList) {
                clausulaContratoListClausulaContrato.setIdClausula(null);
                clausulaContratoListClausulaContrato = em.merge(clausulaContratoListClausulaContrato);
            }
            em.remove(tipoClausulaContrato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoClausulaContrato> findTipoClausulaContratoEntities() {
        return findTipoClausulaContratoEntities(true, -1, -1);
    }

    public List<TipoClausulaContrato> findTipoClausulaContratoEntities(int maxResults, int firstResult) {
        return findTipoClausulaContratoEntities(false, maxResults, firstResult);
    }

    private List<TipoClausulaContrato> findTipoClausulaContratoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoClausulaContrato.class));
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

    public TipoClausulaContrato findTipoClausulaContrato(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoClausulaContrato.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoClausulaContratoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoClausulaContrato> rt = cq.from(TipoClausulaContrato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
