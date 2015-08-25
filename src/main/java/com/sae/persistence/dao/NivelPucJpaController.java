/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.NivelPuc;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.ParametroContableProyecto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class NivelPucJpaController implements Serializable {

    public NivelPucJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NivelPuc nivelPuc) throws PreexistingEntityException, Exception {
        if (nivelPuc.getParametroContableProyectoList() == null) {
            nivelPuc.setParametroContableProyectoList(new ArrayList<ParametroContableProyecto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ParametroContableProyecto> attachedParametroContableProyectoList = new ArrayList<ParametroContableProyecto>();
            for (ParametroContableProyecto parametroContableProyectoListParametroContableProyectoToAttach : nivelPuc.getParametroContableProyectoList()) {
                parametroContableProyectoListParametroContableProyectoToAttach = em.getReference(parametroContableProyectoListParametroContableProyectoToAttach.getClass(), parametroContableProyectoListParametroContableProyectoToAttach.getId());
                attachedParametroContableProyectoList.add(parametroContableProyectoListParametroContableProyectoToAttach);
            }
            nivelPuc.setParametroContableProyectoList(attachedParametroContableProyectoList);
            em.persist(nivelPuc);
            for (ParametroContableProyecto parametroContableProyectoListParametroContableProyecto : nivelPuc.getParametroContableProyectoList()) {
                NivelPuc oldIdNivelPucOfParametroContableProyectoListParametroContableProyecto = parametroContableProyectoListParametroContableProyecto.getIdNivelPuc();
                parametroContableProyectoListParametroContableProyecto.setIdNivelPuc(nivelPuc);
                parametroContableProyectoListParametroContableProyecto = em.merge(parametroContableProyectoListParametroContableProyecto);
                if (oldIdNivelPucOfParametroContableProyectoListParametroContableProyecto != null) {
                    oldIdNivelPucOfParametroContableProyectoListParametroContableProyecto.getParametroContableProyectoList().remove(parametroContableProyectoListParametroContableProyecto);
                    oldIdNivelPucOfParametroContableProyectoListParametroContableProyecto = em.merge(oldIdNivelPucOfParametroContableProyectoListParametroContableProyecto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNivelPuc(nivelPuc.getId()) != null) {
                throw new PreexistingEntityException("NivelPuc " + nivelPuc + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NivelPuc nivelPuc) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NivelPuc persistentNivelPuc = em.find(NivelPuc.class, nivelPuc.getId());
            List<ParametroContableProyecto> parametroContableProyectoListOld = persistentNivelPuc.getParametroContableProyectoList();
            List<ParametroContableProyecto> parametroContableProyectoListNew = nivelPuc.getParametroContableProyectoList();
            List<ParametroContableProyecto> attachedParametroContableProyectoListNew = new ArrayList<ParametroContableProyecto>();
            for (ParametroContableProyecto parametroContableProyectoListNewParametroContableProyectoToAttach : parametroContableProyectoListNew) {
                parametroContableProyectoListNewParametroContableProyectoToAttach = em.getReference(parametroContableProyectoListNewParametroContableProyectoToAttach.getClass(), parametroContableProyectoListNewParametroContableProyectoToAttach.getId());
                attachedParametroContableProyectoListNew.add(parametroContableProyectoListNewParametroContableProyectoToAttach);
            }
            parametroContableProyectoListNew = attachedParametroContableProyectoListNew;
            nivelPuc.setParametroContableProyectoList(parametroContableProyectoListNew);
            nivelPuc = em.merge(nivelPuc);
            for (ParametroContableProyecto parametroContableProyectoListOldParametroContableProyecto : parametroContableProyectoListOld) {
                if (!parametroContableProyectoListNew.contains(parametroContableProyectoListOldParametroContableProyecto)) {
                    parametroContableProyectoListOldParametroContableProyecto.setIdNivelPuc(null);
                    parametroContableProyectoListOldParametroContableProyecto = em.merge(parametroContableProyectoListOldParametroContableProyecto);
                }
            }
            for (ParametroContableProyecto parametroContableProyectoListNewParametroContableProyecto : parametroContableProyectoListNew) {
                if (!parametroContableProyectoListOld.contains(parametroContableProyectoListNewParametroContableProyecto)) {
                    NivelPuc oldIdNivelPucOfParametroContableProyectoListNewParametroContableProyecto = parametroContableProyectoListNewParametroContableProyecto.getIdNivelPuc();
                    parametroContableProyectoListNewParametroContableProyecto.setIdNivelPuc(nivelPuc);
                    parametroContableProyectoListNewParametroContableProyecto = em.merge(parametroContableProyectoListNewParametroContableProyecto);
                    if (oldIdNivelPucOfParametroContableProyectoListNewParametroContableProyecto != null && !oldIdNivelPucOfParametroContableProyectoListNewParametroContableProyecto.equals(nivelPuc)) {
                        oldIdNivelPucOfParametroContableProyectoListNewParametroContableProyecto.getParametroContableProyectoList().remove(parametroContableProyectoListNewParametroContableProyecto);
                        oldIdNivelPucOfParametroContableProyectoListNewParametroContableProyecto = em.merge(oldIdNivelPucOfParametroContableProyectoListNewParametroContableProyecto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = nivelPuc.getId();
                if (findNivelPuc(id) == null) {
                    throw new NonexistentEntityException("The nivelPuc with id " + id + " no longer exists.");
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
            NivelPuc nivelPuc;
            try {
                nivelPuc = em.getReference(NivelPuc.class, id);
                nivelPuc.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nivelPuc with id " + id + " no longer exists.", enfe);
            }
            List<ParametroContableProyecto> parametroContableProyectoList = nivelPuc.getParametroContableProyectoList();
            for (ParametroContableProyecto parametroContableProyectoListParametroContableProyecto : parametroContableProyectoList) {
                parametroContableProyectoListParametroContableProyecto.setIdNivelPuc(null);
                parametroContableProyectoListParametroContableProyecto = em.merge(parametroContableProyectoListParametroContableProyecto);
            }
            em.remove(nivelPuc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NivelPuc> findNivelPucEntities() {
        return findNivelPucEntities(true, -1, -1);
    }

    public List<NivelPuc> findNivelPucEntities(int maxResults, int firstResult) {
        return findNivelPucEntities(false, maxResults, firstResult);
    }

    private List<NivelPuc> findNivelPucEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NivelPuc.class));
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

    public NivelPuc findNivelPuc(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NivelPuc.class, id);
        } finally {
            em.close();
        }
    }

    public int getNivelPucCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NivelPuc> rt = cq.from(NivelPuc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
