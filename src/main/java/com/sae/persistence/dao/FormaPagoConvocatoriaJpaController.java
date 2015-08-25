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
import com.sae.persistence.domain.FormaPagoConvocatoria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class FormaPagoConvocatoriaJpaController implements Serializable {

    public FormaPagoConvocatoriaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FormaPagoConvocatoria formaPagoConvocatoria) throws PreexistingEntityException, Exception {
        if (formaPagoConvocatoria.getConvocatoriaList() == null) {
            formaPagoConvocatoria.setConvocatoriaList(new ArrayList<Convocatoria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Convocatoria> attachedConvocatoriaList = new ArrayList<Convocatoria>();
            for (Convocatoria convocatoriaListConvocatoriaToAttach : formaPagoConvocatoria.getConvocatoriaList()) {
                convocatoriaListConvocatoriaToAttach = em.getReference(convocatoriaListConvocatoriaToAttach.getClass(), convocatoriaListConvocatoriaToAttach.getId());
                attachedConvocatoriaList.add(convocatoriaListConvocatoriaToAttach);
            }
            formaPagoConvocatoria.setConvocatoriaList(attachedConvocatoriaList);
            em.persist(formaPagoConvocatoria);
            for (Convocatoria convocatoriaListConvocatoria : formaPagoConvocatoria.getConvocatoriaList()) {
                FormaPagoConvocatoria oldIdFormaPagoOfConvocatoriaListConvocatoria = convocatoriaListConvocatoria.getIdFormaPago();
                convocatoriaListConvocatoria.setIdFormaPago(formaPagoConvocatoria);
                convocatoriaListConvocatoria = em.merge(convocatoriaListConvocatoria);
                if (oldIdFormaPagoOfConvocatoriaListConvocatoria != null) {
                    oldIdFormaPagoOfConvocatoriaListConvocatoria.getConvocatoriaList().remove(convocatoriaListConvocatoria);
                    oldIdFormaPagoOfConvocatoriaListConvocatoria = em.merge(oldIdFormaPagoOfConvocatoriaListConvocatoria);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFormaPagoConvocatoria(formaPagoConvocatoria.getId()) != null) {
                throw new PreexistingEntityException("FormaPagoConvocatoria " + formaPagoConvocatoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FormaPagoConvocatoria formaPagoConvocatoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FormaPagoConvocatoria persistentFormaPagoConvocatoria = em.find(FormaPagoConvocatoria.class, formaPagoConvocatoria.getId());
            List<Convocatoria> convocatoriaListOld = persistentFormaPagoConvocatoria.getConvocatoriaList();
            List<Convocatoria> convocatoriaListNew = formaPagoConvocatoria.getConvocatoriaList();
            List<Convocatoria> attachedConvocatoriaListNew = new ArrayList<Convocatoria>();
            for (Convocatoria convocatoriaListNewConvocatoriaToAttach : convocatoriaListNew) {
                convocatoriaListNewConvocatoriaToAttach = em.getReference(convocatoriaListNewConvocatoriaToAttach.getClass(), convocatoriaListNewConvocatoriaToAttach.getId());
                attachedConvocatoriaListNew.add(convocatoriaListNewConvocatoriaToAttach);
            }
            convocatoriaListNew = attachedConvocatoriaListNew;
            formaPagoConvocatoria.setConvocatoriaList(convocatoriaListNew);
            formaPagoConvocatoria = em.merge(formaPagoConvocatoria);
            for (Convocatoria convocatoriaListOldConvocatoria : convocatoriaListOld) {
                if (!convocatoriaListNew.contains(convocatoriaListOldConvocatoria)) {
                    convocatoriaListOldConvocatoria.setIdFormaPago(null);
                    convocatoriaListOldConvocatoria = em.merge(convocatoriaListOldConvocatoria);
                }
            }
            for (Convocatoria convocatoriaListNewConvocatoria : convocatoriaListNew) {
                if (!convocatoriaListOld.contains(convocatoriaListNewConvocatoria)) {
                    FormaPagoConvocatoria oldIdFormaPagoOfConvocatoriaListNewConvocatoria = convocatoriaListNewConvocatoria.getIdFormaPago();
                    convocatoriaListNewConvocatoria.setIdFormaPago(formaPagoConvocatoria);
                    convocatoriaListNewConvocatoria = em.merge(convocatoriaListNewConvocatoria);
                    if (oldIdFormaPagoOfConvocatoriaListNewConvocatoria != null && !oldIdFormaPagoOfConvocatoriaListNewConvocatoria.equals(formaPagoConvocatoria)) {
                        oldIdFormaPagoOfConvocatoriaListNewConvocatoria.getConvocatoriaList().remove(convocatoriaListNewConvocatoria);
                        oldIdFormaPagoOfConvocatoriaListNewConvocatoria = em.merge(oldIdFormaPagoOfConvocatoriaListNewConvocatoria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = formaPagoConvocatoria.getId();
                if (findFormaPagoConvocatoria(id) == null) {
                    throw new NonexistentEntityException("The formaPagoConvocatoria with id " + id + " no longer exists.");
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
            FormaPagoConvocatoria formaPagoConvocatoria;
            try {
                formaPagoConvocatoria = em.getReference(FormaPagoConvocatoria.class, id);
                formaPagoConvocatoria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The formaPagoConvocatoria with id " + id + " no longer exists.", enfe);
            }
            List<Convocatoria> convocatoriaList = formaPagoConvocatoria.getConvocatoriaList();
            for (Convocatoria convocatoriaListConvocatoria : convocatoriaList) {
                convocatoriaListConvocatoria.setIdFormaPago(null);
                convocatoriaListConvocatoria = em.merge(convocatoriaListConvocatoria);
            }
            em.remove(formaPagoConvocatoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FormaPagoConvocatoria> findFormaPagoConvocatoriaEntities() {
        return findFormaPagoConvocatoriaEntities(true, -1, -1);
    }

    public List<FormaPagoConvocatoria> findFormaPagoConvocatoriaEntities(int maxResults, int firstResult) {
        return findFormaPagoConvocatoriaEntities(false, maxResults, firstResult);
    }

    private List<FormaPagoConvocatoria> findFormaPagoConvocatoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FormaPagoConvocatoria.class));
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

    public FormaPagoConvocatoria findFormaPagoConvocatoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FormaPagoConvocatoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getFormaPagoConvocatoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FormaPagoConvocatoria> rt = cq.from(FormaPagoConvocatoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
