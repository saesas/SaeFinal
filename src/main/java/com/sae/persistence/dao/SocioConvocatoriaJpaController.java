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
import com.sae.persistence.domain.RupEstadoFinancieroSocioConvocatoria;
import com.sae.persistence.domain.SocioConvocatoria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class SocioConvocatoriaJpaController implements Serializable {

    public SocioConvocatoriaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SocioConvocatoria socioConvocatoria) throws PreexistingEntityException, Exception {
        if (socioConvocatoria.getRupEstadoFinancieroSocioConvocatoriaList() == null) {
            socioConvocatoria.setRupEstadoFinancieroSocioConvocatoriaList(new ArrayList<RupEstadoFinancieroSocioConvocatoria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Convocatoria idConvocatoria = socioConvocatoria.getIdConvocatoria();
            if (idConvocatoria != null) {
                idConvocatoria = em.getReference(idConvocatoria.getClass(), idConvocatoria.getId());
                socioConvocatoria.setIdConvocatoria(idConvocatoria);
            }
            List<RupEstadoFinancieroSocioConvocatoria> attachedRupEstadoFinancieroSocioConvocatoriaList = new ArrayList<RupEstadoFinancieroSocioConvocatoria>();
            for (RupEstadoFinancieroSocioConvocatoria rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoriaToAttach : socioConvocatoria.getRupEstadoFinancieroSocioConvocatoriaList()) {
                rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoriaToAttach = em.getReference(rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoriaToAttach.getClass(), rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoriaToAttach.getId());
                attachedRupEstadoFinancieroSocioConvocatoriaList.add(rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoriaToAttach);
            }
            socioConvocatoria.setRupEstadoFinancieroSocioConvocatoriaList(attachedRupEstadoFinancieroSocioConvocatoriaList);
            em.persist(socioConvocatoria);
            if (idConvocatoria != null) {
                idConvocatoria.getSocioConvocatoriaList().add(socioConvocatoria);
                idConvocatoria = em.merge(idConvocatoria);
            }
            for (RupEstadoFinancieroSocioConvocatoria rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria : socioConvocatoria.getRupEstadoFinancieroSocioConvocatoriaList()) {
                SocioConvocatoria oldIdSocioConvocatoriaOfRupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria = rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria.getIdSocioConvocatoria();
                rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria.setIdSocioConvocatoria(socioConvocatoria);
                rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria = em.merge(rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria);
                if (oldIdSocioConvocatoriaOfRupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria != null) {
                    oldIdSocioConvocatoriaOfRupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria.getRupEstadoFinancieroSocioConvocatoriaList().remove(rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria);
                    oldIdSocioConvocatoriaOfRupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria = em.merge(oldIdSocioConvocatoriaOfRupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSocioConvocatoria(socioConvocatoria.getId()) != null) {
                throw new PreexistingEntityException("SocioConvocatoria " + socioConvocatoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SocioConvocatoria socioConvocatoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SocioConvocatoria persistentSocioConvocatoria = em.find(SocioConvocatoria.class, socioConvocatoria.getId());
            Convocatoria idConvocatoriaOld = persistentSocioConvocatoria.getIdConvocatoria();
            Convocatoria idConvocatoriaNew = socioConvocatoria.getIdConvocatoria();
            List<RupEstadoFinancieroSocioConvocatoria> rupEstadoFinancieroSocioConvocatoriaListOld = persistentSocioConvocatoria.getRupEstadoFinancieroSocioConvocatoriaList();
            List<RupEstadoFinancieroSocioConvocatoria> rupEstadoFinancieroSocioConvocatoriaListNew = socioConvocatoria.getRupEstadoFinancieroSocioConvocatoriaList();
            if (idConvocatoriaNew != null) {
                idConvocatoriaNew = em.getReference(idConvocatoriaNew.getClass(), idConvocatoriaNew.getId());
                socioConvocatoria.setIdConvocatoria(idConvocatoriaNew);
            }
            List<RupEstadoFinancieroSocioConvocatoria> attachedRupEstadoFinancieroSocioConvocatoriaListNew = new ArrayList<RupEstadoFinancieroSocioConvocatoria>();
            for (RupEstadoFinancieroSocioConvocatoria rupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoriaToAttach : rupEstadoFinancieroSocioConvocatoriaListNew) {
                rupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoriaToAttach = em.getReference(rupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoriaToAttach.getClass(), rupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoriaToAttach.getId());
                attachedRupEstadoFinancieroSocioConvocatoriaListNew.add(rupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoriaToAttach);
            }
            rupEstadoFinancieroSocioConvocatoriaListNew = attachedRupEstadoFinancieroSocioConvocatoriaListNew;
            socioConvocatoria.setRupEstadoFinancieroSocioConvocatoriaList(rupEstadoFinancieroSocioConvocatoriaListNew);
            socioConvocatoria = em.merge(socioConvocatoria);
            if (idConvocatoriaOld != null && !idConvocatoriaOld.equals(idConvocatoriaNew)) {
                idConvocatoriaOld.getSocioConvocatoriaList().remove(socioConvocatoria);
                idConvocatoriaOld = em.merge(idConvocatoriaOld);
            }
            if (idConvocatoriaNew != null && !idConvocatoriaNew.equals(idConvocatoriaOld)) {
                idConvocatoriaNew.getSocioConvocatoriaList().add(socioConvocatoria);
                idConvocatoriaNew = em.merge(idConvocatoriaNew);
            }
            for (RupEstadoFinancieroSocioConvocatoria rupEstadoFinancieroSocioConvocatoriaListOldRupEstadoFinancieroSocioConvocatoria : rupEstadoFinancieroSocioConvocatoriaListOld) {
                if (!rupEstadoFinancieroSocioConvocatoriaListNew.contains(rupEstadoFinancieroSocioConvocatoriaListOldRupEstadoFinancieroSocioConvocatoria)) {
                    rupEstadoFinancieroSocioConvocatoriaListOldRupEstadoFinancieroSocioConvocatoria.setIdSocioConvocatoria(null);
                    rupEstadoFinancieroSocioConvocatoriaListOldRupEstadoFinancieroSocioConvocatoria = em.merge(rupEstadoFinancieroSocioConvocatoriaListOldRupEstadoFinancieroSocioConvocatoria);
                }
            }
            for (RupEstadoFinancieroSocioConvocatoria rupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoria : rupEstadoFinancieroSocioConvocatoriaListNew) {
                if (!rupEstadoFinancieroSocioConvocatoriaListOld.contains(rupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoria)) {
                    SocioConvocatoria oldIdSocioConvocatoriaOfRupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoria = rupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoria.getIdSocioConvocatoria();
                    rupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoria.setIdSocioConvocatoria(socioConvocatoria);
                    rupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoria = em.merge(rupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoria);
                    if (oldIdSocioConvocatoriaOfRupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoria != null && !oldIdSocioConvocatoriaOfRupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoria.equals(socioConvocatoria)) {
                        oldIdSocioConvocatoriaOfRupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoria.getRupEstadoFinancieroSocioConvocatoriaList().remove(rupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoria);
                        oldIdSocioConvocatoriaOfRupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoria = em.merge(oldIdSocioConvocatoriaOfRupEstadoFinancieroSocioConvocatoriaListNewRupEstadoFinancieroSocioConvocatoria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = socioConvocatoria.getId();
                if (findSocioConvocatoria(id) == null) {
                    throw new NonexistentEntityException("The socioConvocatoria with id " + id + " no longer exists.");
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
            SocioConvocatoria socioConvocatoria;
            try {
                socioConvocatoria = em.getReference(SocioConvocatoria.class, id);
                socioConvocatoria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The socioConvocatoria with id " + id + " no longer exists.", enfe);
            }
            Convocatoria idConvocatoria = socioConvocatoria.getIdConvocatoria();
            if (idConvocatoria != null) {
                idConvocatoria.getSocioConvocatoriaList().remove(socioConvocatoria);
                idConvocatoria = em.merge(idConvocatoria);
            }
            List<RupEstadoFinancieroSocioConvocatoria> rupEstadoFinancieroSocioConvocatoriaList = socioConvocatoria.getRupEstadoFinancieroSocioConvocatoriaList();
            for (RupEstadoFinancieroSocioConvocatoria rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria : rupEstadoFinancieroSocioConvocatoriaList) {
                rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria.setIdSocioConvocatoria(null);
                rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria = em.merge(rupEstadoFinancieroSocioConvocatoriaListRupEstadoFinancieroSocioConvocatoria);
            }
            em.remove(socioConvocatoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SocioConvocatoria> findSocioConvocatoriaEntities() {
        return findSocioConvocatoriaEntities(true, -1, -1);
    }

    public List<SocioConvocatoria> findSocioConvocatoriaEntities(int maxResults, int firstResult) {
        return findSocioConvocatoriaEntities(false, maxResults, firstResult);
    }

    private List<SocioConvocatoria> findSocioConvocatoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SocioConvocatoria.class));
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

    public SocioConvocatoria findSocioConvocatoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SocioConvocatoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getSocioConvocatoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SocioConvocatoria> rt = cq.from(SocioConvocatoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
