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
import com.sae.persistence.domain.InformacionSobrePropuesta;
import com.sae.persistence.domain.SobrePropuesta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class SobrePropuestaJpaController implements Serializable {

    public SobrePropuestaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SobrePropuesta sobrePropuesta) throws PreexistingEntityException, Exception {
        if (sobrePropuesta.getInformacionSobrePropuestaList() == null) {
            sobrePropuesta.setInformacionSobrePropuestaList(new ArrayList<InformacionSobrePropuesta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<InformacionSobrePropuesta> attachedInformacionSobrePropuestaList = new ArrayList<InformacionSobrePropuesta>();
            for (InformacionSobrePropuesta informacionSobrePropuestaListInformacionSobrePropuestaToAttach : sobrePropuesta.getInformacionSobrePropuestaList()) {
                informacionSobrePropuestaListInformacionSobrePropuestaToAttach = em.getReference(informacionSobrePropuestaListInformacionSobrePropuestaToAttach.getClass(), informacionSobrePropuestaListInformacionSobrePropuestaToAttach.getId());
                attachedInformacionSobrePropuestaList.add(informacionSobrePropuestaListInformacionSobrePropuestaToAttach);
            }
            sobrePropuesta.setInformacionSobrePropuestaList(attachedInformacionSobrePropuestaList);
            em.persist(sobrePropuesta);
            for (InformacionSobrePropuesta informacionSobrePropuestaListInformacionSobrePropuesta : sobrePropuesta.getInformacionSobrePropuestaList()) {
                SobrePropuesta oldIdItemOfInformacionSobrePropuestaListInformacionSobrePropuesta = informacionSobrePropuestaListInformacionSobrePropuesta.getIdItem();
                informacionSobrePropuestaListInformacionSobrePropuesta.setIdItem(sobrePropuesta);
                informacionSobrePropuestaListInformacionSobrePropuesta = em.merge(informacionSobrePropuestaListInformacionSobrePropuesta);
                if (oldIdItemOfInformacionSobrePropuestaListInformacionSobrePropuesta != null) {
                    oldIdItemOfInformacionSobrePropuestaListInformacionSobrePropuesta.getInformacionSobrePropuestaList().remove(informacionSobrePropuestaListInformacionSobrePropuesta);
                    oldIdItemOfInformacionSobrePropuestaListInformacionSobrePropuesta = em.merge(oldIdItemOfInformacionSobrePropuestaListInformacionSobrePropuesta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSobrePropuesta(sobrePropuesta.getId()) != null) {
                throw new PreexistingEntityException("SobrePropuesta " + sobrePropuesta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SobrePropuesta sobrePropuesta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SobrePropuesta persistentSobrePropuesta = em.find(SobrePropuesta.class, sobrePropuesta.getId());
            List<InformacionSobrePropuesta> informacionSobrePropuestaListOld = persistentSobrePropuesta.getInformacionSobrePropuestaList();
            List<InformacionSobrePropuesta> informacionSobrePropuestaListNew = sobrePropuesta.getInformacionSobrePropuestaList();
            List<InformacionSobrePropuesta> attachedInformacionSobrePropuestaListNew = new ArrayList<InformacionSobrePropuesta>();
            for (InformacionSobrePropuesta informacionSobrePropuestaListNewInformacionSobrePropuestaToAttach : informacionSobrePropuestaListNew) {
                informacionSobrePropuestaListNewInformacionSobrePropuestaToAttach = em.getReference(informacionSobrePropuestaListNewInformacionSobrePropuestaToAttach.getClass(), informacionSobrePropuestaListNewInformacionSobrePropuestaToAttach.getId());
                attachedInformacionSobrePropuestaListNew.add(informacionSobrePropuestaListNewInformacionSobrePropuestaToAttach);
            }
            informacionSobrePropuestaListNew = attachedInformacionSobrePropuestaListNew;
            sobrePropuesta.setInformacionSobrePropuestaList(informacionSobrePropuestaListNew);
            sobrePropuesta = em.merge(sobrePropuesta);
            for (InformacionSobrePropuesta informacionSobrePropuestaListOldInformacionSobrePropuesta : informacionSobrePropuestaListOld) {
                if (!informacionSobrePropuestaListNew.contains(informacionSobrePropuestaListOldInformacionSobrePropuesta)) {
                    informacionSobrePropuestaListOldInformacionSobrePropuesta.setIdItem(null);
                    informacionSobrePropuestaListOldInformacionSobrePropuesta = em.merge(informacionSobrePropuestaListOldInformacionSobrePropuesta);
                }
            }
            for (InformacionSobrePropuesta informacionSobrePropuestaListNewInformacionSobrePropuesta : informacionSobrePropuestaListNew) {
                if (!informacionSobrePropuestaListOld.contains(informacionSobrePropuestaListNewInformacionSobrePropuesta)) {
                    SobrePropuesta oldIdItemOfInformacionSobrePropuestaListNewInformacionSobrePropuesta = informacionSobrePropuestaListNewInformacionSobrePropuesta.getIdItem();
                    informacionSobrePropuestaListNewInformacionSobrePropuesta.setIdItem(sobrePropuesta);
                    informacionSobrePropuestaListNewInformacionSobrePropuesta = em.merge(informacionSobrePropuestaListNewInformacionSobrePropuesta);
                    if (oldIdItemOfInformacionSobrePropuestaListNewInformacionSobrePropuesta != null && !oldIdItemOfInformacionSobrePropuestaListNewInformacionSobrePropuesta.equals(sobrePropuesta)) {
                        oldIdItemOfInformacionSobrePropuestaListNewInformacionSobrePropuesta.getInformacionSobrePropuestaList().remove(informacionSobrePropuestaListNewInformacionSobrePropuesta);
                        oldIdItemOfInformacionSobrePropuestaListNewInformacionSobrePropuesta = em.merge(oldIdItemOfInformacionSobrePropuestaListNewInformacionSobrePropuesta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sobrePropuesta.getId();
                if (findSobrePropuesta(id) == null) {
                    throw new NonexistentEntityException("The sobrePropuesta with id " + id + " no longer exists.");
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
            SobrePropuesta sobrePropuesta;
            try {
                sobrePropuesta = em.getReference(SobrePropuesta.class, id);
                sobrePropuesta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sobrePropuesta with id " + id + " no longer exists.", enfe);
            }
            List<InformacionSobrePropuesta> informacionSobrePropuestaList = sobrePropuesta.getInformacionSobrePropuestaList();
            for (InformacionSobrePropuesta informacionSobrePropuestaListInformacionSobrePropuesta : informacionSobrePropuestaList) {
                informacionSobrePropuestaListInformacionSobrePropuesta.setIdItem(null);
                informacionSobrePropuestaListInformacionSobrePropuesta = em.merge(informacionSobrePropuestaListInformacionSobrePropuesta);
            }
            em.remove(sobrePropuesta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SobrePropuesta> findSobrePropuestaEntities() {
        return findSobrePropuestaEntities(true, -1, -1);
    }

    public List<SobrePropuesta> findSobrePropuestaEntities(int maxResults, int firstResult) {
        return findSobrePropuestaEntities(false, maxResults, firstResult);
    }

    private List<SobrePropuesta> findSobrePropuestaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SobrePropuesta.class));
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

    public SobrePropuesta findSobrePropuesta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SobrePropuesta.class, id);
        } finally {
            em.close();
        }
    }

    public int getSobrePropuestaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SobrePropuesta> rt = cq.from(SobrePropuesta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
