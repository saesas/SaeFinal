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
import com.sae.persistence.domain.Convocatoria;
import com.sae.persistence.domain.EquipoRequeridoConvocatoria;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class EquipoRequeridoConvocatoriaJpaController implements Serializable {

    public EquipoRequeridoConvocatoriaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EquipoRequeridoConvocatoria equipoRequeridoConvocatoria) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Convocatoria idConvocatoria = equipoRequeridoConvocatoria.getIdConvocatoria();
            if (idConvocatoria != null) {
                idConvocatoria = em.getReference(idConvocatoria.getClass(), idConvocatoria.getId());
                equipoRequeridoConvocatoria.setIdConvocatoria(idConvocatoria);
            }
            em.persist(equipoRequeridoConvocatoria);
            if (idConvocatoria != null) {
                idConvocatoria.getEquipoRequeridoConvocatoriaList().add(equipoRequeridoConvocatoria);
                idConvocatoria = em.merge(idConvocatoria);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEquipoRequeridoConvocatoria(equipoRequeridoConvocatoria.getId()) != null) {
                throw new PreexistingEntityException("EquipoRequeridoConvocatoria " + equipoRequeridoConvocatoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EquipoRequeridoConvocatoria equipoRequeridoConvocatoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EquipoRequeridoConvocatoria persistentEquipoRequeridoConvocatoria = em.find(EquipoRequeridoConvocatoria.class, equipoRequeridoConvocatoria.getId());
            Convocatoria idConvocatoriaOld = persistentEquipoRequeridoConvocatoria.getIdConvocatoria();
            Convocatoria idConvocatoriaNew = equipoRequeridoConvocatoria.getIdConvocatoria();
            if (idConvocatoriaNew != null) {
                idConvocatoriaNew = em.getReference(idConvocatoriaNew.getClass(), idConvocatoriaNew.getId());
                equipoRequeridoConvocatoria.setIdConvocatoria(idConvocatoriaNew);
            }
            equipoRequeridoConvocatoria = em.merge(equipoRequeridoConvocatoria);
            if (idConvocatoriaOld != null && !idConvocatoriaOld.equals(idConvocatoriaNew)) {
                idConvocatoriaOld.getEquipoRequeridoConvocatoriaList().remove(equipoRequeridoConvocatoria);
                idConvocatoriaOld = em.merge(idConvocatoriaOld);
            }
            if (idConvocatoriaNew != null && !idConvocatoriaNew.equals(idConvocatoriaOld)) {
                idConvocatoriaNew.getEquipoRequeridoConvocatoriaList().add(equipoRequeridoConvocatoria);
                idConvocatoriaNew = em.merge(idConvocatoriaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = equipoRequeridoConvocatoria.getId();
                if (findEquipoRequeridoConvocatoria(id) == null) {
                    throw new NonexistentEntityException("The equipoRequeridoConvocatoria with id " + id + " no longer exists.");
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
            EquipoRequeridoConvocatoria equipoRequeridoConvocatoria;
            try {
                equipoRequeridoConvocatoria = em.getReference(EquipoRequeridoConvocatoria.class, id);
                equipoRequeridoConvocatoria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equipoRequeridoConvocatoria with id " + id + " no longer exists.", enfe);
            }
            Convocatoria idConvocatoria = equipoRequeridoConvocatoria.getIdConvocatoria();
            if (idConvocatoria != null) {
                idConvocatoria.getEquipoRequeridoConvocatoriaList().remove(equipoRequeridoConvocatoria);
                idConvocatoria = em.merge(idConvocatoria);
            }
            em.remove(equipoRequeridoConvocatoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EquipoRequeridoConvocatoria> findEquipoRequeridoConvocatoriaEntities() {
        return findEquipoRequeridoConvocatoriaEntities(true, -1, -1);
    }

    public List<EquipoRequeridoConvocatoria> findEquipoRequeridoConvocatoriaEntities(int maxResults, int firstResult) {
        return findEquipoRequeridoConvocatoriaEntities(false, maxResults, firstResult);
    }

    private List<EquipoRequeridoConvocatoria> findEquipoRequeridoConvocatoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EquipoRequeridoConvocatoria.class));
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

    public EquipoRequeridoConvocatoria findEquipoRequeridoConvocatoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EquipoRequeridoConvocatoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getEquipoRequeridoConvocatoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EquipoRequeridoConvocatoria> rt = cq.from(EquipoRequeridoConvocatoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
